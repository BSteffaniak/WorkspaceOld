package net.foxycorndog.p1xeland.chunks;

import net.foxycorndog.jdoogl.GL;
import net.foxycorndog.jdoogl.util.ArrayUtil;
import net.foxycorndog.jdoogl.util.Intersects;
import net.foxycorndog.jdoogl.util.LightBuffer;
import net.foxycorndog.p1xeland.P1xeland;
import net.foxycorndog.p1xeland.actors.Actor;
import net.foxycorndog.p1xeland.actors.Player;
import net.foxycorndog.p1xeland.tiles.Tile;

public class Chunk
{
	private int             relativeX, relativeY;
	private int             leftY, rightY;
	
	private LightBuffer          verticesBuffer, texturesBuffer, normalsBuffer;
	
	private Tile            tiles[];
	
	public static final int WIDTH  = 16;
	public static final int HEIGHT = 16;
	
	public Chunk(int relativeX, int relativeY)
	{
		this.relativeX = relativeX;
		this.relativeY = relativeY;
		
		verticesBuffer = new LightBuffer(WIDTH * HEIGHT * 4 * 3);
		texturesBuffer = new LightBuffer(WIDTH * HEIGHT * 4 * 2);
		normalsBuffer  = new LightBuffer(WIDTH * HEIGHT * 4 * 3);
		
		tiles = new Tile[WIDTH * HEIGHT];
	}
	
	public Tile getTile(int x, int y)
	{
		return tiles[x + y * WIDTH];
	}
	
	public boolean addTile(int x, int y, Tile tile, boolean replace)
	{
		if (!replace && tiles[x + y * WIDTH] != null)
		{
			return false;
		}
		else if (tiles[x + y * WIDTH] == tile)
		{
			return false;
		}
		
		verticesBuffer.setData((x + y * WIDTH) * 4 * 3, GL.addRectVertexArrayf(x * P1xeland.textureSize, y * P1xeland.textureSize, 0, P1xeland.textureSize, P1xeland.textureSize, 0, null));
		verticesBuffer.refreshData();
		
		texturesBuffer.setData((x + y * WIDTH) * 4 * 2, GL.addRectTextureArrayf(tile.getOffsets(), 0, null));
		texturesBuffer.refreshData();
		
		normalsBuffer.setData((x + y * WIDTH) * 4 * 3, GL.addRectNormalArrayf(1, 0, null));
		normalsBuffer.refreshData();
		
		tiles[x + y * WIDTH] = tile;
		
		return true;
	}
	
	public boolean removeTile(int x, int y)
	{
		boolean removed = false;
		
		if (tiles[x + y * WIDTH] == null)
		{
			return false;
		}
		
		verticesBuffer.setData((x + y * WIDTH) * 4 * 3, new float[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 });
		verticesBuffer.refreshData();
		
		texturesBuffer.setData((x + y * WIDTH) * 4 * 2, new float[] { 0, 0, 0, 0, 0, 0, 0, 0 });
		texturesBuffer.refreshData();
		
		normalsBuffer.setData((x + y * WIDTH) * 4 * 3, GL.addRectNormalArrayf(0, 0, null));
		normalsBuffer.refreshData();
		
		if (tiles[x + y * WIDTH] != null)
		{
			removed = true;
		}
		
		tiles[x + y * WIDTH] = null;
		
		return removed;
	}
	
	public void render()
	{
		GL.beginManipulation();
		{
			GL.translatef(WIDTH * P1xeland.textureSize * relativeX, HEIGHT * P1xeland.textureSize * relativeY, -10);
			
			GL.renderQuads(verticesBuffer, texturesBuffer, Tile.getTerrain(), 0, WIDTH * HEIGHT);
		}
		GL.endManipulation();
	}
	
	public boolean inRange(Actor actor)
	{
		int width  = WIDTH  * P1xeland.textureSize;
		int height = HEIGHT * P1xeland.textureSize;
		
		return (Intersects.rectangles((int)actor.getX() - actor.getWidth(), (int)actor.getY() - actor.getHeight(), actor.getWidth() * 3, actor.getHeight() * 3, relativeX * width, relativeY * height, width, height));
	}
	
	public boolean collided(Actor actor)
	{
		int ax = 0;
		int ay = 0;
		int aw = 0;
		int ah = 0;
		
		if (actor instanceof Player)
		{
			ax = (int)(actor.getX() + actor.getWidth() / 4);
			ay = (int)actor.getY();
			aw = actor.getWidth() / 2;
			ah = actor.getHeight();
		}
		else
		{
			ax = (int)actor.getX();
			ay = (int)actor.getY();
			aw = actor.getWidth();
			ah = actor.getHeight();
		}
		
		int width  = WIDTH  * P1xeland.textureSize;
		int height = HEIGHT * P1xeland.textureSize;
		
		for (int yy = 0; yy < HEIGHT; yy ++)
		{
			for (int xx = 0; xx < WIDTH; xx ++)
			{
				if (tiles[xx + yy * WIDTH] != null && Intersects.rectangles(ax, ay, aw, ah, xx * P1xeland.textureSize + relativeX * width, yy * P1xeland.textureSize + relativeY * height, P1xeland.textureSize, P1xeland.textureSize))
				{
					return true;
				}
			}
		}
		
		return false;
	}
	
	public void generateTerrain(int leftY, int rightY, boolean surface)
	{
		if (surface)
		{
			int rand    = 0;
			int createY = leftY;
			
			if (createY < 0)
			{
				createY = (int)(Math.random() * HEIGHT);
			}
			else
			{
				createY += (int)(Math.random() * 3) - 1;
			}
			
			createY = (createY > HEIGHT ? HEIGHT : (createY < 0 ? 0 : createY));
			
			this.leftY = createY;
			
			Tile randomTile = null;
			
			for (int x = 0; x < WIDTH; x ++)
			{
				createY = createY < 0 ? 0 : (createY > HEIGHT ? HEIGHT : createY);
				
				for (int y = 0; y < HEIGHT; y ++)
				{
					randomTile = Tile.getRandomTile((int)(y + getOriginRelativeY()));
					
					if (y == createY)
					{
						if ((int)(Math.random() * 20) == 0)
						{
							addTile(x, y, randomTile, true);
						}
						else
						{
							addTile(x, y, Tile.GRASS, true);
						}
					}
					else if (y <= createY && y > createY - 4)
					{
						if ((int)(Math.random() * 5) == 0)
						{
							addTile(x, y, randomTile, true);
						}
						else
						{
							addTile(x, y, Tile.DIRT, true);
						}
					}
					else if (y < createY)
					{
						addTile(x, y, randomTile, true);
					}
				}
				
				this.rightY   = createY;
				
				rand     = (int)(Math.random() * 3) - 1;
				
				createY += rand;
			}
			
			this.rightY = createY;//rightY;
		}
		else
		{
			for (int x = 0; x < WIDTH; x ++)
			{
				for (int y = 0; y < HEIGHT; y ++)
				{
					addTile(x, y, Tile.getRandomTile((int)(y + getOriginRelativeY())), true);
				}
			}
			
			this.leftY  = HEIGHT;
			this.rightY = HEIGHT;
		}
		
		Tile notCopy[] = new Tile[] { Tile.STONE, Tile.GRASS };
		
		boolean notReplace[] = new boolean[WIDTH * HEIGHT];
		
		Tile tile = null, tile2 = null;
		
		for (int x = 0; x < WIDTH; x ++)
		{
			for (int y = 0; y < HEIGHT; y ++)
			{
				tile = getTile(x, y);
				
				if (tile != null && !containsTile(notCopy, tile) && !notReplace[x + y * WIDTH])
				{
					addTile((x + 1 >= WIDTH ? x : x + 1),  y,                   tile, true);
					addTile(x,                         (y - 1 < 0 ? y : y - 1), tile, true);
					addTile((x - 1 < 0 ? x : x - 1),    y,                      tile, true);
					
					notReplace[x                            +  y                      * WIDTH] = true;
					notReplace[(x + 1 >= WIDTH ? x : x + 1) +  y                      * WIDTH] = true;
					notReplace[x                            + (y - 1 < 0 ? y : y - 1) * WIDTH] = true;
					notReplace[(x - 1 < 0 ? x : x - 1)      +  y                      * WIDTH] = true;
					
					if (y + 1 < HEIGHT)
					{
						tile2 = getTile(x, y + 1);
						
						if (tile2 != null && !containsTile(notCopy, tile2) && !notReplace[x + (y + 1) * WIDTH])
						{
							addTile(x,       (y + 1 >= HEIGHT ? y : y + 1),     tile, true);
							notReplace[x          + (y + 1 >= HEIGHT ? y : y + 1) * HEIGHT] = true;
						}
					}
				}
			}
		}
	}
	
	private boolean containsTile(Tile tiles[], Tile tile)
	{
		for (Tile tile2 : tiles)
		{
			if (tile2 == tile)
			{
				return true;
			}
		}
		
		return false;
	}
	
	public int getOriginRelativeX()
	{
		return relativeX * WIDTH;
	}
	
	public int getOriginRelativeY()
	{
		return relativeY * HEIGHT;
	}
	
	public int getLeftY()
	{
		return leftY;
	}
	
	public int getRightY()
	{
		return rightY;
	}
}