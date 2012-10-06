package net.foxycorndog.p1xelandroid.map;

import java.util.ArrayList;

import net.foxycorndog.jdooglandroid.GL;
import net.foxycorndog.jdooglandroid.components.Frame;
import net.foxycorndog.jdooglandroid.input.KeyboardInput;
import net.foxycorndog.jdooglandroid.input.TouchInput;
import net.foxycorndog.jdoutilandroid.ArrayUtil;
import net.foxycorndog.jdoutilandroid.Distance;
import net.foxycorndog.jdoutilandroid.GeneralCollection;
import net.foxycorndog.jdoutilandroid.Intersects;
import net.foxycorndog.jdoutilandroid.Util;
import net.foxycorndog.p1xelandroid.P1xeland;
import net.foxycorndog.p1xelandroid.actors.Actor;
import net.foxycorndog.p1xelandroid.actors.Player;
import net.foxycorndog.p1xelandroid.actors.Actor.Direction;
import net.foxycorndog.p1xelandroid.items.tiles.Tile;
import net.foxycorndog.p1xelandroid.map.chunks.Chunk;


public class Map
{
	private boolean                  activatable;
	
	private float                    x, y;
	
	private Player                   player;
	
//	private DayCycle                 dayCycle;
	
	private ArrayList<Point>         visibleChunks;
	private ArrayList<Object>        doors;
	private ArrayList<Integer>       lightSources;
	private ArrayList<Object>        explosives;
	private ArrayList<Actor>         actors;
	
	private GeneralCollection<Chunk> chunks;
	
	private class Point
	{
		int x, y;
		
		public Point(int x, int y)
		{
			this.x = x;
			this.y = y;
		}
	}
	
	public Map()
	{
//		dayCycle      = new DayCycle();
		
//		dayCycle.setTime(12 + 4, 0, 0);
		
		visibleChunks = new ArrayList<Point>();
		lightSources  = new ArrayList<Integer>();
		explosives    = new ArrayList<Object>();
		actors        = new ArrayList<Actor>();
		
		chunks        = new GeneralCollection<Chunk>();
	}
	
	public ArrayList<Actor> getActors()
	{
		return actors;
	}
	
	public void addActor(Actor actor)
	{
		actors.add(actor);
		
		if (actor instanceof Player)
		{
			this.player = (Player)actor;
		}
	}
	
	public boolean collided(Actor actor)
	{
		if (!actor.isCollidable())
		{
			return false;
		}
		
		actor.setOnLadder(false);
		
		ArrayList<Chunk> elements = chunks.getElements();
		
		synchronized (elements)
		{
			for (int i = elements.size() - 1; i >= 0; i --)
			{
				synchronized (elements)
				{
					Chunk chunk = elements.get(i);
					
					if (chunk.inRange(actor))
					{
						if (chunk.collided(actor))
						{
							return true;
						}
					}
				}
			}
		}
		
		return false;
	}
	
	public boolean addTile(int x, int y, int index, boolean replace, Actor actor)
	{
		Tile tile = null;
		
		if (actor.getInventory().getItem(index) instanceof Tile)
		{
			tile = (Tile)actor.getInventory().getItem(index);
			
			if (addTile(x, y, tile, replace, actor))
			{
				actor.getInventory().removeObject(index, 1);
				
				return true;
			}
		}
		
		return false;
	}
	
	public boolean addTile(int x, int y, Tile tile, boolean replace, Actor actor)
	{
		int ts = P1xeland.textureSize;
		
		if (Distance.points((x * ts + (ts / 2)), (y * ts + (ts / 2)), (actor.getX() + actor.getWidth() / 2f), (actor.getY() + actor.getHeight() / 2f)) < actor.getReach() * ts)
		{
			if (addTile(x, y, actor.getEditing(), tile, replace))
			{
//				actor.getInventory().removeItem(tile, 1);
				
				return true;
			}
		}
		
		return false;
	}
	
	public boolean addTile(int x, int y, int index, Tile tile, boolean replace)
	{
		for (Actor actor : actors)
		{
			if (tile.isCollidable() && index == 1 && Intersects.rectangles((int)actor.getX(), (int)actor.getY(), actor.getWidth(), actor.getHeight(), x * P1xeland.textureSize, y * P1xeland.textureSize, P1xeland.textureSize, P1xeland.textureSize))
			{
				return false;
			}
		}
		
//		int relativeX = x / Chunk.WIDTH;
//		int relativeY = y / Chunk.HEIGHT;
//		
//		relativeX = x < 0 ? relativeX - 1 : relativeX;
//		relativeY = y < 0 ? relativeY - 1 : relativeY;
//		
//		x = x < 0 ? (Chunk.WIDTH  - Math.abs(x % Chunk.WIDTH))  % 16 : Math.abs(x % Chunk.WIDTH);
//		y = y < 0 ? (Chunk.HEIGHT - Math.abs(y % Chunk.HEIGHT)) % 16 : Math.abs(y % Chunk.HEIGHT);
//		
//		relativeX = relativeX < 0 && x == 0 ? relativeX + 1 : relativeX;
//		relativeY = relativeY < 0 && y == 0 ? relativeY + 1 : relativeY;
		
		int locations[] = getLocations(x, y);
		
		x               = locations[0];
		y               = locations[1];
		
		int relativeX   = locations[2];
		int relativeY   = locations[3];
		
		if (chunks.is(relativeX, relativeY))
		{
			
		}
		else
		{
			Chunk chunk = new Chunk(relativeX, relativeY, this);
			
			chunk.generateTerrain(0, 0, 1, null);
			
			chunks.add(relativeX, relativeY, chunk);
		}
		
		if (chunks.get(relativeX, relativeY).addTile(x, y, index, tile, replace))
		{
//				calculateLighting();
			
			if (tile == Tile.TORCH)
			{
				lightSources.add(x + (relativeX * Chunk.WIDTH));
				lightSources.add(y + (relativeY * Chunk.HEIGHT));
				lightSources.add(40);
			}
			
			activatable = false;
			
			return true;
		}
		else if (chunks.get(relativeX, relativeY).isTile(x, y, index))
		{
			activate(x, y, index, chunks.get(relativeX, relativeY));
		}
		
		return false;
	}
	
	public boolean removeTile(int x, int y, int index, Actor actor)
	{
		int ts = P1xeland.textureSize;
		
		if (Distance.points((x * ts + (ts / 2)), (y * ts + (ts / 2)), (actor.getX() + actor.getWidth() / 2f), (actor.getY() + actor.getHeight() / 2f)) < actor.getReach() * ts)
		{
			if (isTile(x, y, index))
			{
				try
				{
					actor.giveObject(getTile(x, y, index));
				}
				catch (Exception ex)
				{
					ex.printStackTrace();
				}
				
				if (removeTile(x, y, actor.getEditing()))
				{
//					calculateLighting();
					
					return true;
				}
			}
		}
		
		return false;
	}
	
	public boolean removeTile(int x, int y, int index)
	{
//		int relativeX = x / Chunk.WIDTH;
//		int relativeY = y / Chunk.HEIGHT;
//		
//		relativeX = x < 0 ? relativeX - 1 : relativeX;
//		relativeY = y < 0 ? relativeY - 1 : relativeY;
//		
//		x = x < 0 ? (Chunk.WIDTH  - Math.abs(x % Chunk.WIDTH))  % 16 : Math.abs(x % Chunk.WIDTH);
//		y = y < 0 ? (Chunk.HEIGHT - Math.abs(y % Chunk.HEIGHT)) % 16 : Math.abs(y % Chunk.HEIGHT);
//		
//		relativeX = relativeX < 0 && x == 0 ? relativeX + 1 : relativeX;
//		relativeY = relativeY < 0 && y == 0 ? relativeY + 1 : relativeY;
		
		int locations[] = getLocations(x, y);
		
		x               = locations[0];
		y               = locations[1];
		
		int relativeX   = locations[2];
		int relativeY   = locations[3];
		
		checkActorCollisions();
		
		try
		{
			if (!chunks.get(relativeX, relativeY).isTile(x, y, index))
			{
				return false;
			}
			
			Tile tile = chunks.get(relativeX, relativeY).getTile(x, y, index);
			
			if (chunks.get(relativeX, relativeY).removeTile(x, y, index))
			{
				if (tile == Tile.TORCH)
				{
					synchronized (lightSources)
					{
						for (int i = lightSources.size() - 3; i >= 0; i -= 3)
						{
							synchronized (lightSources)
							{
								if (lightSources.get(i) == x + (relativeX * Chunk.WIDTH) && lightSources.get(i + 1) == y + (relativeY * Chunk.HEIGHT))
								{
									lightSources.remove(i);
									lightSources.remove(i);
									lightSources.remove(i);
								}
							}
						}
					}
				}
				
				return true;
			}
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			//System.out.println(ex + " 3");
		}
		
		return false;
	}
	
	public void pollChunks()
	{
//		for (int yy = 0; yy < Frame.getHeight() / Chunk.HEIGHT + 1; yy ++)
//		{
//			for (int xx = 0; xx < Frame.getWidth() / Chunk.WIDTH + 1; xx ++)
//			{
//				if (!chunks.is(xx, yy))
//				{
//					int rx = xx;
//					int ry = yy;
//					
//					Chunk chunk = new Chunk(rx, ry, this);
//					
//					if (ry == 0)
//					{
//						chunk.generateTerrain(0, 0, 0, null);
//					}
//					else
//					{
//						chunk.generateTerrain(0, 0, ry > 0 ? 1 : -1, null);
//					}
//					
//					chunks.add(rx, ry, chunk);
//				}
//			}
//		}
	}
	
	public void tick(float delta)
	{
//		dayCycle.tick(delta);
		
		synchronized (actors)
		{
			for (int i = actors.size() - 1; i >= 0; i --)
			{
				synchronized (actors)
				{
					Actor actor = actors.get(i);
					
					actor.tick(delta);
				}
			}
		}
		
		synchronized (explosives)
		{
			for (int i = explosives.size() - 5; i >= 0; i -= 5)
			{
				synchronized (explosives)
				{
					long timeLeft = (Long)explosives.get(i + 4);
					
					if (Util.nanoTime - timeLeft >= 1000000000l)
					{
						int x = (Integer)explosives.get(i);
						int y = (Integer)explosives.get(i + 1);
						
						int index = (Integer)explosives.get(i + 2);
						
						int radius = (Integer)explosives.get(i + 3);
						
						removeTile(x, y, index);
						
						for (int yy = -radius; yy < radius; yy ++)
						{
							for (int xx = -radius; xx < radius; xx ++)
							{
								if (Distance.points(x, y, xx + x, yy + y) <= radius)
								{
									int xxx = xx + x;
									int yyy = yy + y;
									
									if (isTile(xxx, yyy, index))
									{
										if (getTile(xxx, yyy, index) == Tile.TNT)// && xxx != orig[0] && yyy != orig[1])
										{
											activate(xxx, yyy, index, true);
										}
										else
										{
											removeTile(xxx, yyy, index);
										}
									}
								}
							}
						}
						
						explosives.remove(i);
						explosives.remove(i);
						explosives.remove(i);
						explosives.remove(i);
						explosives.remove(i);
					}
				}
			}
		}
		
		if (!TouchInput.isPressed())
		{
			activatable = true;
		}
	}
	
	public void checkActorCollisions()
	{
		synchronized (actors)
		{
			for (int i = actors.size() - 1; i >= 0; i --)
			{
				synchronized (actors)
				{
					collided(actors.get(i));
				}
			}
		}
	}
	
	private boolean checkIfActivatable()
	{
		if (activatable)
		{
			activatable = false;
			
			return true;
		}
		
		return false;
	}
	
	public void activate(int x, int y, int index)
	{
		activate(x, y, index, false);
	}
	
	public void activate(int x, int y, int index, boolean force)
	{
		if (!force)
		{
			if (!checkIfActivatable())
			{
				return;
			}
		}
		
		int locations[] = getLocations(x, y);
		
		activate(locations[0], locations[1], index, locations[2], locations[3], true);
	}
	
	public void activate(int x, int y, int index, int relativeX, int relativeY)
	{
		activate(x, y, index, relativeX, relativeY, false);
	}
	
	public void activate(int x, int y, int index, int relativeX, int relativeY, boolean force)
	{
		if (!force)
		{
			if (!checkIfActivatable())
			{
				return;
			}
		}
		
		if (!isChunk(relativeX, relativeY))
		{
			return;
		}
		
		activate(x, y, index, chunks.get(relativeX, relativeY), true);
	}
	
	public void activate(int x, int y, int index, Chunk chunk)
	{
		activate(x, y, index, chunk, false);
	}
	
	public void activate(int x, int y, int index, Chunk chunk, boolean force)
	{
		if (!force)
		{
			if (!checkIfActivatable())
			{
				return;
			}
		}
		
		Tile tile = chunk.getTile(x, y, 1);
		
		if (tile == Tile.TNT)
		{
			explode(x, y, index, chunk, 5);
		}
	}
	
	public void explode(final int x, final int y, final int index, final Chunk chunk, final int radius)
	{
		int orig[] = getOriginalLocation(x, y, chunk);
		
		explosives.add(orig[0]);
		explosives.add(orig[1]);
		explosives.add(index);
		explosives.add(radius);
		explosives.add(Util.nanoTime);
		
		calculateLighting();
	}
	
	public int[] getLocations(int x, int y)
	{
		int relativeX = x / Chunk.WIDTH;
		int relativeY = y / Chunk.HEIGHT;
		
		relativeX = x < 0 ? relativeX - 1 : relativeX;
		relativeY = y < 0 ? relativeY - 1 : relativeY;
		
		x = x < 0 ? (Chunk.WIDTH  - Math.abs(x % Chunk.WIDTH))  % 16 : Math.abs(x % Chunk.WIDTH);
		y = y < 0 ? (Chunk.HEIGHT - Math.abs(y % Chunk.HEIGHT)) % 16 : Math.abs(y % Chunk.HEIGHT);
		
		relativeX = relativeX < 0 && x == 0 ? relativeX + 1 : relativeX;
		relativeY = relativeY < 0 && y == 0 ? relativeY + 1 : relativeY;
		
		return new int[] { x, y, relativeX, relativeY };
	}
	
	public int[] getOriginalLocation(int x, int y, Chunk chunk)
	{
		x +=  chunk.getOriginRelativeX();
		y +=  chunk.getOriginRelativeY();
		
		return new int[] { x, y };
	}
	
	public ArrayList<Integer> getLightSources()
	{
		return lightSources;
	}
	
	public void calculateLighting()
	{
		ArrayList<Chunk> elements = chunks.getElements();
		
		synchronized (elements)
		{
			for (int i = elements.size() - 1; i >= 0; i --)
			{
				synchronized (elements)
				{
					Chunk chunk = elements.get(i);
					
					if (Intersects.rectangles((int)(chunk.getOriginRelativeX() * P1xeland.scale * P1xeland.textureSize + (getX() * P1xeland.scale)), (int)(chunk.getOriginRelativeY() * P1xeland.scale * P1xeland.textureSize + (getY() * P1xeland.scale)), (int)(Chunk.WIDTH * P1xeland.textureSize * P1xeland.scale), (int)(Chunk.HEIGHT * P1xeland.textureSize * P1xeland.scale), 0, 0, Frame.getWidth(), Frame.getHeight()))
					{
						chunk.calculateLighting();
					}
				}
			}
		}
	}
	
	public void finishLighting()
	{
		ArrayList<Chunk> elements = chunks.getElements();
		
		synchronized (elements)
		{
			for (int i = elements.size() - 1; i >= 0; i --)
			{
				synchronized (elements)
				{
					Chunk chunk = elements.get(i);
					
					if (Intersects.rectangles((int)(chunk.getOriginRelativeX() * P1xeland.scale * P1xeland.textureSize + (getX() * P1xeland.scale)), (int)(chunk.getOriginRelativeY() * P1xeland.scale * P1xeland.textureSize + (getY() * P1xeland.scale)), (int)(Chunk.WIDTH * P1xeland.textureSize * P1xeland.scale), (int)(Chunk.HEIGHT * P1xeland.textureSize * P1xeland.scale), 0, 0, Frame.getWidth(), Frame.getHeight()))
					{
//						chunk.finishLighting();
					}
				}
			}
		}
	}
	
	public void render()
	{
//		dayCycle.render(y);
		
		ArrayList<Chunk> elements = chunks.getElements();
		
		GL.beginManipulation();
		{
			GL.translatef(x, y, 0f);
			
			synchronized (elements)
			{
				for (int i = elements.size() - 1; i >= 0; i --)
				{
					synchronized (elements)
					{
						Chunk chunk = elements.get(i);
						
						if (Intersects.rectangles((int)(chunk.getOriginRelativeX() * P1xeland.scale * P1xeland.textureSize + (getX() * P1xeland.scale)), (int)(chunk.getOriginRelativeY() * P1xeland.scale * P1xeland.textureSize + (getY() * P1xeland.scale)), (int)(Chunk.WIDTH * P1xeland.textureSize * P1xeland.scale), (int)(Chunk.HEIGHT * P1xeland.textureSize * P1xeland.scale), 0, 0, Frame.getWidth(), Frame.getHeight()))
						{
							if (!ArrayUtil.contains(visibleChunks, new Point(chunk.getRelativeX(), chunk.getRelativeY())))
							{
								visibleChunks.add(new Point(chunk.getRelativeX(), chunk.getRelativeY()));
								
								chunk.calculateLighting();
							}

							chunk.render();
						}
						else
						{
							if (ArrayUtil.contains(visibleChunks, new Point(chunk.getRelativeX(), chunk.getRelativeY())))
							{
								visibleChunks.remove(new Point(chunk.getRelativeX(), chunk.getRelativeY()));
							}
						}
					}
				}
			}
		}
		GL.endManipulation();
	}
	
	public boolean isChunk(int relativeX, int relativeY)
	{
		return chunks.is(relativeX, relativeY);
	}
	
//	public DayCycle getDayCycle()
//	{
//		return dayCycle;
//	}
	
	public float getX()
	{
		return x;
	}
	
	public float getY()
	{
		return y;
	}
	
	public void setLocation(float x, float y)
	{
		this.x = x;
		this.y = y;
	}
	
	public void move(float dx, float dy)
	{
		this.x += dx;
		this.y += dy;
	}
	
	public Tile getTile(int x, int y, int index)
	{
		int relativeX = x / Chunk.WIDTH;
		int relativeY = y / Chunk.HEIGHT;
		
		relativeX = x < 0 ? relativeX - 1 : relativeX;
		relativeY = y < 0 ? relativeY - 1 : relativeY;
		
		x = x < 0 ? (Chunk.WIDTH  - Math.abs(x % Chunk.WIDTH))  % 16 : Math.abs(x % Chunk.WIDTH);
		y = y < 0 ? (Chunk.HEIGHT - Math.abs(y % Chunk.HEIGHT)) % 16 : Math.abs(y % Chunk.HEIGHT);
		
		relativeX = relativeX < 0 && x == 0 ? relativeX + 1 : relativeX;
		relativeY = relativeY < 0 && y == 0 ? relativeY + 1 : relativeY;
		
		if (chunks.is(relativeX, relativeY))
		{
			return chunks.get(relativeX, relativeY).getTile(x, y, index);
		}
		
		return null;
	}
	
	public boolean isTile(int x, int y, int index)
	{
		int relativeX = x / Chunk.WIDTH;
		int relativeY = y / Chunk.HEIGHT;
		
		relativeX = x < 0 ? relativeX - 1 : relativeX;
		relativeY = y < 0 ? relativeY - 1 : relativeY;
		
		x = x < 0 ? (Chunk.WIDTH  - Math.abs(x % Chunk.WIDTH))  % 16 : Math.abs(x % Chunk.WIDTH);
		y = y < 0 ? (Chunk.HEIGHT - Math.abs(y % Chunk.HEIGHT)) % 16 : Math.abs(y % Chunk.HEIGHT);
		
		relativeX = relativeX < 0 && x == 0 ? relativeX + 1 : relativeX;
		relativeY = relativeY < 0 && y == 0 ? relativeY + 1 : relativeY;
		
//		try
//		{
		if (chunks.is(relativeX, relativeY))
		{
			return chunks.get(relativeX, relativeY).isTile(x, y, index);
		}
//		}
//		catch (RuntimeException ex)
//		{
//			System.out.println(ex + " 1");
//			
//			throw ex;
//		}
		
		return false;
	}
	
	public void generateChunk(int relativeX, int relativeY, int startX, int startY, int level, int noise[])
	{
		Chunk chunk = null;
		
		chunks.add(relativeX, relativeY, chunk = new Chunk(relativeX, relativeY, this));
		
		chunk.generateTerrain(startX, startY, level, noise);
	}
	
	public Chunk getChunk(int relativeX, int relativeY)
	{
		return chunks.get(relativeX, relativeY);
	}
	
	public Player getPlayer()
	{
		return player;
	}
	
	public void addChunk(int relativeX, int relativeY)
	{
		chunks.add(relativeX, relativeY, new Chunk(relativeX, relativeY, this));
	}
}