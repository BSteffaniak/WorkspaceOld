package net.foxycorndog.p1xeland.map.chunks;

import java.util.ArrayList;

import net.foxycorndog.jdoogl.GL;
import net.foxycorndog.jdoogl.noise.LineNoise;
import net.foxycorndog.jdoogl.noise.Noise2D;
import net.foxycorndog.jdoutil.ArrayUtil;
import net.foxycorndog.jdoutil.Distance;
import net.foxycorndog.jdoutil.Intersection;
import net.foxycorndog.jdoutil.Intersects;
import net.foxycorndog.jdoutil.LightBuffer;
import net.foxycorndog.jdoutil.Task;
import net.foxycorndog.jdoutil.VerticesBuffer;
import net.foxycorndog.p1xeland.P1xeland;
import net.foxycorndog.p1xeland.actors.Actor;
import net.foxycorndog.p1xeland.actors.Player;
import net.foxycorndog.p1xeland.items.tiles.Tile;
import net.foxycorndog.p1xeland.map.Map;

public class Chunk
{
	private boolean         finishedCalculatingLighting;
	
	private int             relativeX, relativeY;
	private int             leftY, rightY;
	
	private Map             map;
	
	private Thread          lightThread;
	
	private VerticesBuffer  verticesBuffer[];
	
	private LightBuffer     texturesBuffer[], normalsBuffer, colorBuffer[];
	
	private int             lightSources[];
	private int             colors[], alphas[], actorColors[];
	
	private Tile            tiles[][];
	
	public static final int WIDTH  = 16;
	public static final int HEIGHT = 16;
	
	public Chunk(int relativeX, int relativeY, Map map)
	{
		this.relativeX = relativeX;
		this.relativeY = relativeY;
		
		this.map       = map;
		
		if (map == null)
		{System.out.println("isnull");
			System.exit(1);
		}
		
		verticesBuffer = new VerticesBuffer[3];
		texturesBuffer = new LightBuffer[3];
		colorBuffer    = new LightBuffer[3];
		
		for (int i = 0; i < verticesBuffer.length; i ++)
		{
			verticesBuffer[i] = new VerticesBuffer(WIDTH * HEIGHT * 4 * 2, 2);
			texturesBuffer[i] = new LightBuffer(WIDTH * HEIGHT * 4 * 2);
			colorBuffer[i]    = new LightBuffer(WIDTH * HEIGHT * 4 * 4);
		}
//		normalsBuffer  = new Buffer(WIDTH * HEIGHT * 4 * 3);
		
		tiles        = new Tile[3][WIDTH * HEIGHT];
		
		lightSources = new int[WIDTH * HEIGHT];
		
		colors = new int[WIDTH * HEIGHT];
		alphas = new int[WIDTH * HEIGHT];
		
		init();
	}
	
	private void init()
	{
		lightThread = new Thread()
		{
			public void run()
			{
				while (true)
				{
					finishedCalculatingLighting = false;
					
					int torch = 999;
					
					Player player = map.getPlayer();
					
					ArrayList<Integer> lightSources = map.getLightSources();
					
					int time = map.getDayCycle().getTime();
					
					if (time <= 850)
					{
						time = 850 - time;
					}
					else if (time >= 2200)
					{
						time -= 2200;
					}
					else
					{
						time = 0;
					}
					
					int darkness = (int)(time / 2f);
					
					darkness = darkness > 230 ? 230 : darkness;
					
	//				colorBuffer[0].beginEditing();
	//				colorBuffer[1].beginEditing();
					
					boolean rand = (int)(Math.random() * 25) == 0 ? true : false;
					
					for (int index = 0; index < WIDTH * HEIGHT; index ++)
					{
						int x = index % WIDTH;
						int y = index / HEIGHT;
						
						x += getOriginRelativeX();
						y += getOriginRelativeY();
						
						int depth = 0;
						
						for (int i = 1;; i ++)
						{
							if (map != null && map.getTile(x, y + i, 1) != null && !map.getTile(x, y + i, 1).isTranslucent())
							{
								depth += 65;
								
								if (depth >= 255)
								{
									break;
								}
							}
							else
							{
								int loc[] = map.getLocations(x, y + i);
								
								if (!map.isChunk(loc[2], loc[3]))
								{
									break;
								}
							}
						}
						
						depth += darkness;
						
						int dist[] = new int[lightSources.size() / 3];
						
						synchronized (lightSources)
						{
							for (int i = lightSources.size() - 3; i >= 0; i -= 3)
							{
								synchronized (lightSources)
								{
									int indx = i / 3;
									
									try
									{
										dist[indx] = 999;
										
										if (lightSources.get(i + 2) == 0)
										{
											if (i < lightSources.size() - 3)
											{
												dist[indx] = dist[indx] < dist[indx + 1] ? dist[indx] : dist[indx + 1];
											}
											
											continue;
										}
										
										dist[indx] = (int)(Distance.points(lightSources.get(i) * 40, lightSources.get(i + 1) * 40, x * 40, y * 40));
										
										if (i < lightSources.size() - 3)
										{
											dist[indx] = dist[indx] < dist[indx + 1] ? dist[indx] : dist[indx + 1];
										}
									}
									catch (IndexOutOfBoundsException ex)
									{
										//ex.printStackTrace();
										continue;
									}
								}
							}
						}
						
						int blockDarkness = 999;
						
						blockDarkness = blockDarkness < depth ? blockDarkness : depth;
						
						int distance = 999;//(int)(Distance.points(x * 40, y * 40, (map.getPlayer().getX() / P1xeland.textureSize) * 40, (map.getPlayer().getY() / P1xeland.textureSize) * 40));
						
						Object item = map.getPlayer().getQuickBar().getSelectedObject();
						
						if (item instanceof Tile && item == Tile.TORCH)
						{
							distance = (int)(Distance.points(x * 40, y * 40, (player.getX() / P1xeland.textureSize) * 40, (player.getY() / P1xeland.textureSize) * 40));
							
							torch = 0;
							
							blockDarkness = distance < blockDarkness ? distance : blockDarkness;
						}
						
	//					if (rand)
	//					{
	//						distance += 45;//(int)(Math.random() * 6);
	//					}
						
						// 2200 - 650
						
						if (dist.length > 0)
						{
							blockDarkness = blockDarkness < dist[0] ? blockDarkness : dist[0];
						}
						
						if (map.getPlayer().getEditing() == 2)
						{
							alphas[index] = 255;
						}
						else
						{
							alphas[index] = (int)(Distance.points(x * 8, y * 8, (player.getX() / P1xeland.textureSize) * 8, (player.getY() / P1xeland.textureSize) * 8)) / 3;
							
							alphas[index] = alphas[index] <= 10 ? 0 : 255;
						}
	//					GL.setColori(255 - distance , 255 - distance, 255 - distance, 255);
	//					colorBuffer[0].setData(index * 4 * 4, GL.addRectColorArrayi(255 - distance - 120 , 255 - distance - 120, 255 - distance - 120, 255, 0, null));
	//					colorBuffer[1].difSetData(index * 4 * 4, GL.addRectColorArrayi(255 - distance , 255 - distance, 255 - distance, 255, 0, null));
						
						colors[index] = 255 - blockDarkness;
					}
					
	//				colorBuffer[0].endEditing();
	//				colorBuffer[1].endEditing();
					
					ArrayList<Actor> actors = map.getActors();
					
					actorColors = new int[actors.size()];
					
					synchronized (actors)
					{
						for (int ind = actors.size() - 1; ind >= 0; ind --)
						{
							synchronized (actors)
							{
//								actorColors[ind] = 999;
								
								Actor actor = actors.get(ind);
								
								int dist[] = new int[2];
								
								dist[0] = 999;
								dist[1] = 999;
								
								synchronized (lightSources)
								{
									for (int i = lightSources.size() - 3; i >= 0; i -= 3)
									{
										synchronized (lightSources)
										{
											dist[0] = (int)(Distance.points(lightSources.get(i) * 40, lightSources.get(i + 1) * 40, (actor.getX() / P1xeland.textureSize) * 40, (actor.getY() / P1xeland.textureSize) * 40));
											
											if (i > 0)
											{
												dist[1] = dist[0] < dist[1] ? dist[0] : dist[1];
											}
										}
									}
								}
								
								dist[1] = dist[0] < dist[1] ? dist[0] : dist[1];
								
								int depth = 0;
								
								for (int i = 1;; i ++)
								{
									int x = 0 + (int)((actor.getX() + actor.getWidth() / 2) / P1xeland.textureSize);
									int y = 1 + (int)(actor.getY() / P1xeland.textureSize);
									
									if (map.getTile(x, y + i, 1) != null && !map.getTile(x, y + i, 1).isTranslucent())
									{
										depth += 65;
										
										if (depth >= 255)
										{
											break;
										}
									}
									else
									{
										int loc[] = map.getLocations(x, y + i);
										
										if (!map.isChunk(loc[2], loc[3]))
										{
											break;
										}
									}
								}
								
//								actor.setDarkness(darkness < actor.getDarkness() ? darkness : actor.getDarkness());
//								actor.setDarkness(depth > actor.getDarkness() ? depth : actor.getDarkness());
//								actor.setDarkness(dist[1] < actor.getDarkness() ? dist[1] : actor.getDarkness());
								
								actorColors[ind] = darkness > actorColors[ind] ? darkness : actorColors[ind];
								actorColors[ind] = depth > actorColors[ind] ? depth : actorColors[ind];
								actorColors[ind] = dist[1] < actorColors[ind] ? dist[1] : actorColors[ind];
								
								if (actor instanceof Player)
								{
									actorColors[ind] = torch < actorColors[ind] ? torch : actorColors[ind];
								}
							}
						}
					}
					
//					try
//					{
//						Thread.sleep(50);
//					}
//					catch (InterruptedException e)
//					{
//						e.printStackTrace();
//					}
					
					synchronized (lightThread)
					{
						try
						{
					
							finishedCalculatingLighting = true;
							lightThread.wait();
						}
						catch (InterruptedException e)
						{
							e.printStackTrace();
						}
					}
				}
			}
		};
		
		lightThread.setPriority(Thread.MIN_PRIORITY);
		
		lightThread.start();
	}
	
	public Tile getTile(int x, int y, int index)
	{
		return tiles[index][x + y * WIDTH];
	}
	
	public boolean addTile(int x, int y, int index, Tile tile, boolean replace)
	{
		if (!replace && tiles[index][x + y * WIDTH] != null)
		{
			return false;
		}
		else if (tiles[index][x + y * WIDTH] == tile)
		{
			return false;
		}
		else if (!tile.layerCompatible(index))
		{
			return false;
		}
		
		int tileX   = tile.getX();
		int tileY   = tile.getY();
		int tileWid = tile.getWidth();
		int tileHei = tile.getHeight();
		
		for (int yy = 0; yy < tileHei; yy ++)
		{
			for (int xx = 0; xx < tileWid; xx ++)
			{
				verticesBuffer[index].setData(((x + xx) + (y + yy) * WIDTH) * 4 * 2, GL.addRectVertexArrayf((x + xx) * P1xeland.textureSize, (y + yy) * P1xeland.textureSize, P1xeland.textureSize, P1xeland.textureSize, 0, null));
//				verticesBuffer.refreshData();
				
				texturesBuffer[index].setData(((x + xx) + (y + yy) * WIDTH) * 4 * 2, GL.addRectTextureArrayf(Tile.getTerrain().getImageOffsetsf(tileX + xx, tileY + (tileHei - yy - 1), 1, 1), 0, null));
//				texturesBuffer.refreshData();
				
//				normalsBuffer.setData((x + y * WIDTH) * 4 * 3, GL.addRectNormalArrayf(1, 0, null));
//				normalsBuffer.refreshData();
				
//				colorBuffer[index].setData(((x + xx) + (y + yy) * WIDTH) * 4 * 2, GL.addRectColorArrayf(1, 1, 1, 1, 0, null));
//				colorBuffer.refreshData();
				
				tiles[index][(x + xx) + (y + yy) * WIDTH] = tile;
			}
		}
		
		return true;
	}
	
	public boolean removeTile(int x, int y, int index)
	{
		boolean removed = false;
		
		if (tiles[index][x + y * WIDTH] == null)
		{
			return false;
		}
		
		verticesBuffer[index].setData((x + y * WIDTH) * 4 * 2, new float[] { 0, 0, 0, 0, 0, 0, 0, 0 });
//		verticesBuffer.refreshData();
		
		texturesBuffer[index].setData((x + y * WIDTH) * 4 * 2, new float[] { 0, 0, 0, 0, 0, 0, 0, 0 });
//		texturesBuffer.refreshData();
		
//		normalsBuffer.setData((x + y * WIDTH) * 4 * 3, GL.addRectNormalArrayf(0, 0, null));
//		normalsBuffer.refreshData();
		
		if (tiles[index][x + y * WIDTH] != null)
		{
			removed = true;
		}
		
		tiles[index][x + y * WIDTH] = null;
		
		return removed;
	}
	
	public void calculateLighting()
	{
		updateColorBuffers();
	}
	
	private void updateColorBuffers()
	{
		if (colors == null || !finishedCalculatingLighting)
		{
			return;
		}
		
		synchronized (lightThread)
		{
			int col = 0;
			
//			colorBuffer[0].beginEditing();
			
			for (int i = 0; i < colors.length; i ++)
			{
				col = colors[i];
				
				colorBuffer[0].setData(i * 4 * 4, GL.addRectColorArrayif(col - 150, col - 150, col - 150, 255, 0, null));
			}
			
//			colorBuffer[0].endEditing();
			
//			colorBuffer[1].beginEditing();
			
			for (int i = 0; i < colors.length; i ++)
			{
				col = colors[i];
				
				colorBuffer[1].setData(i * 4 * 4, GL.addRectColorArrayif(col, col, col, 255, 0, null));
			}
			
//			colorBuffer[1].endEditing();
			
//			colorBuffer[2].beginEditing();
			
			for (int i = 0; i < colors.length; i ++)
			{
				col = colors[i];
				
				colorBuffer[2].setData(i * 4 * 4, GL.addRectColorArrayif(col, col, col, alphas[i], 0, null));
			}
			
//			colorBuffer[2].endEditing();
			
			for (int i = 0; i < actorColors.length; i ++)
			{
				map.getActors().get(i).setDarkness(actorColors[i]);
			}
			
			lightThread.notify();
		}
	}
	
	public void render()
	{
		GL.beginManipulation();
		{
			GL.translatef(WIDTH * P1xeland.textureSize * relativeX, HEIGHT * P1xeland.textureSize * relativeY, -15);
			
//			GL.drawQuadRect(0, 0, 10, 10, verticesBuffer, texturesBuffer, Tile.getTerrain(), 16, 16);
			
//			GL.beginColorDraw(colorBuffer.getId());
			
			renderBackground();
			
			renderMiddleground();
			
			renderForeground();
			
//			GL.endColorDraw();
		}
		GL.endManipulation();
	}
	
	public void renderBackground()
	{
		GL.setColori(0, 0, 0, 255);
		
		GL.beginManipulation();
		{
			GL.translatef(0, 0, -1f);
			
			GL.beginColorDraw(colorBuffer[0]);
		
			GL.renderQuads(verticesBuffer[0], texturesBuffer[0], Tile.getTerrain(), 0, WIDTH * HEIGHT);
			
			GL.endColorDraw();
		}
		GL.endManipulation();
		
		GL.setColori(255, 255, 255, 255);
	}
	
	public void renderMiddleground()
	{
		GL.beginManipulation();
		{
			GL.beginColorDraw(colorBuffer[1]);
			
			GL.renderQuads(verticesBuffer[1], texturesBuffer[1], Tile.getTerrain(), 0, WIDTH * HEIGHT);
			
			GL.endColorDraw();
		}
		GL.endManipulation();
	}
	
	public void renderForeground()
	{
		GL.beginManipulation();
		{
			GL.translatef(0, 0, 8f);
			
			GL.beginColorDraw(colorBuffer[2]);
			
			GL.renderQuads(verticesBuffer[2], texturesBuffer[2], Tile.getTerrain(), 0, WIDTH * HEIGHT);
			
			GL.endColorDraw();
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
			ah = actor.getHeight() - 1;
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
		
		boolean collided = false;
		
		for (int yy = 0; yy < HEIGHT; yy ++)
		{
			for (int xx = 0; xx < WIDTH; xx ++)
			{
				if (tiles[1][xx + yy * WIDTH] != null && Intersects.rectangles(ax, ay, aw, ah, xx * P1xeland.textureSize + relativeX * width, yy * P1xeland.textureSize + relativeY * height, P1xeland.textureSize, P1xeland.textureSize))
				{
					if (tiles[1][xx + yy * WIDTH].isCollidable() == true)
					{
						if (!collided)
						{
//							float offsets[] = Intersection.rectangles(ax, ay, aw, ah, xx * P1xeland.textureSize + relativeX * width, yy * P1xeland.textureSize + relativeY * height, P1xeland.textureSize, P1xeland.textureSize);
//							
//							System.out.println(offsets[0] + ", " + offsets[1] + ", " + offsets[2] + ", " + offsets[3]);
//							
//							actor.setLocation(actor.getX(), actor.getY() + offsets[3]);
//							
//							ay += offsets[3];
						}
						
						collided = true;
					}
					else if (tiles[1][xx + yy * WIDTH] == Tile.LADDER)
					{
						actor.setOnLadder(true);
					}
				}
			}
		}
		
		return collided;
	}
	
	public void generateTerrain(int startX, int startY, int level, int noise[])
	{
//		int ys[] = LineNoise.generateCircleHill(9, 10, WIDTH, HEIGHT);
		
		int offsetY = 0;
		
		if (noise == null)
		{
			noise = Noise2D.getNoise(WIDTH, 800);
		
			offsetY = startY - noise[0];
		}
		else
		{
			offsetY = startY - noise[startX];
		}
		
		if (level == 0)
		{
			int createY = startY;
			
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
			
			boolean higher;
			
			for (int x = startX; x < WIDTH; x ++)
			{
				int rawY = noise[x] + offsetY;
				
				createY = noise[x] + offsetY;
				
				higher = rawY >= HEIGHT;
				
				createY = createY < 0 ? 0 : (createY >= HEIGHT ? HEIGHT - 1 : createY);
				
				for (int y = createY; y >= 0; y --)
				{
					randomTile = Tile.getRandomTile((int)(y + getOriginRelativeY()));
					
					if (y == createY && !higher)
					{
						if ((int)(Math.random() * 20) == 0)
						{
							addTile(x, y, 0, randomTile, true);
							addTile(x, y, 1, randomTile, true);
						}
						else
						{
							addTile(x, y, 0, Tile.GRASS, true);
							addTile(x, y, 1, Tile.GRASS, true);
						}
					}
					else if (y <= createY && y > createY - 4 && (!higher || (y > rawY - 4)))
					{
						if ((int)(Math.random() * 5) == 0)
						{
							addTile(x, y, 0, randomTile, true);
							addTile(x, y, 1, randomTile, true);
						}
						else
						{
							addTile(x, y, 0, Tile.DIRT, true);
							addTile(x, y, 1, Tile.DIRT, true);
						}
					}
					else if (y < createY)
					{
						addTile(x, y, 0, randomTile, true);
						addTile(x, y, 1, randomTile, true);
					}
					else
					{
						addTile(x, y, 0, randomTile, true);
						addTile(x, y, 1, randomTile, true);
					}
				}
				
				if (rawY == HEIGHT)
				{
					map.generateChunk(relativeX, relativeY + 1, x, 0, 0, noise);
				}
				
//				this.rightY   = createY;
//				
//				rand     = (int)(Math.random() * 3) - 1;
//				
//				createY += rand;
			}
			
			this.rightY = createY;//rightY;
		}
		else if (level == -1)
		{
			for (int x = 0; x < WIDTH; x ++)
			{
				for (int y = 0; y < HEIGHT; y ++)
				{
					addTile(x, y, 0, Tile.getRandomTile((int)(y + getOriginRelativeY())), true);
					addTile(x, y, 1, Tile.getRandomTile((int)(y + getOriginRelativeY())), true);
				}
			}
			
			this.leftY  = HEIGHT;
			this.rightY = HEIGHT;
		}
		else if (level == 1)
		{
			
		}
		
		Tile notCopy[] = new Tile[] { Tile.STONE, Tile.GRASS };
		
		boolean notReplace[] = new boolean[WIDTH * HEIGHT];
		
		Tile tile = null, tile2 = null;
		
//		for (int x = 0; x < WIDTH; x ++)
//		{
//			for (int y = 0; y < HEIGHT; y ++)
//			{
//				tile = getTile(x, y, 1);
//				
//				if (tile != null && !ArrayUtil.contains(notCopy, tile) && !notReplace[x + y * WIDTH])
//				{
//					addTile((x + 1 >= WIDTH ? x : x + 1),  y, 0,                   tile, true);
//					addTile(x,       (y - 1 < 0 ? y : y - 1), 0,                   tile, true);
//					addTile((x - 1 < 0 ? x : x - 1),       y, 0,                   tile, true);
//					
//					addTile((x + 1 >= WIDTH ? x : x + 1),  y, 1,                   tile, true);
//					addTile(x,       (y - 1 < 0 ? y : y - 1), 1,                   tile, true);
//					addTile((x - 1 < 0 ? x : x - 1),       y, 1,                   tile, true);
//					
//					notReplace[x                            +  y                      * WIDTH] = true;
//					notReplace[(x + 1 >= WIDTH ? x : x + 1) +  y                      * WIDTH] = true;
//					notReplace[x                            + (y - 1 < 0 ? y : y - 1) * WIDTH] = true;
//					notReplace[(x - 1 < 0 ? x : x - 1)      +  y                      * WIDTH] = true;
//					
//					if (y + 1 < HEIGHT)
//					{
//						tile2 = getTile(x, y + 1, 1);
//						
//						if (tile2 != null && !ArrayUtil.contains(notCopy, tile2) && !notReplace[x + (y + 1) * WIDTH])
//						{
//							addTile(x,       (y + 1 >= HEIGHT ? y : y + 1), 0,     tile, true);
//							addTile(x,       (y + 1 >= HEIGHT ? y : y + 1), 1,     tile, true);
//							notReplace[x          + (y + 1 >= HEIGHT ? y : y + 1) * HEIGHT] = true;
//						}
//					}
//				}
//			}
//		}
	}
	
	public void addLightSource(int x, int y, Tile tile)
	{
		if (tile == Tile.TORCH)
		{
			lightSources[x + y * WIDTH] = 40;
		}
	}
	
	public int[] getLightSources()
	{
		return lightSources;
	}
	
	public boolean isTile(int x, int y, int index)
	{
		return tiles[index][x + y * WIDTH] != null;
	}
	
	public int getRelativeX()
	{
		return relativeX;
	}
	
	public int getRelativeY()
	{
		return relativeY;
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