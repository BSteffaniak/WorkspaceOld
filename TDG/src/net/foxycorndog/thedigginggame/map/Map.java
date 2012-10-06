package net.foxycorndog.thedigginggame.map;

import java.awt.Point;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.util.ArrayList;

import static net.foxycorndog.presto2d.PrestoGL2D.*;
import static net.foxycorndog.thedigginggame.TheDiggingGame.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import net.foxycorndog.presto2d.PrestoGL2D;
import net.foxycorndog.presto2d.util.ArrayUtil;
import net.foxycorndog.presto2d.util.Buffer;
import net.foxycorndog.presto2d.util.GeneralCollection;
import net.foxycorndog.presto2d.util.Intersection;
import net.foxycorndog.presto2d.util.Intersects;
import net.foxycorndog.presto2d.util.SpriteSheet;
import net.foxycorndog.presto3d.graphics.PrestoGL3D;
import net.foxycorndog.thedigginggame.TheDiggingGame;
import net.foxycorndog.thedigginggame.actors.Player;
import net.foxycorndog.thedigginggame.tiles.Tile;
import static net.foxycorndog.thedigginggame.tiles.Tile.*;

import org.lwjgl.BufferUtils;

public class Map
{
//	private int              /*verticesId, */normalsId;//, texturesId;
	
	private float            x, y;
	
	private short            width, height;
	
	private Buffer           vertices, textures, normals;
//	private FloatBuffer      /*verticesBuffer, */normalsBuffer;//, texturesBuffer;
//	private ByteBuffer       mapBuffer;
	
	private TheDiggingGame   tdg;
	
	private GeneralCollection<Chunk> chunks;
	
	private Point                    terrainRendering[];
	
	private static final int cu                 = 100000;
	
	private static final int AMOUNT_OF_VERTICES = 4;
	private static final int VERTEX_SIZE        = 3;
	private static final int BLOCK_SIZE         = AMOUNT_OF_VERTICES * VERTEX_SIZE;
	
	public static final float NO_COLLISION[] = { 0, 0 };
	
	public Map(TheDiggingGame tdg)
	{
		this.tdg = tdg;
	}
	
	public void init()
	{
		vertices = new Buffer(BLOCK_SIZE * cu);
		textures = new Buffer(BLOCK_SIZE * cu);
		normals  = new Buffer(BLOCK_SIZE * cu);
		
		chunks   = new GeneralCollection<Chunk>();
		
		x        = 0;
		y        = 0;
		
		vertices.setBuffer(BufferUtils.createFloatBuffer(BLOCK_SIZE * cu));
		normals.setBuffer (BufferUtils.createFloatBuffer(BLOCK_SIZE * cu));
		textures.setBuffer(BufferUtils.createFloatBuffer(BLOCK_SIZE * cu));
		
		width = 25;
		height = 10;
		
		for (int y = 0; y > -height; y --)
		{
			for (int x = 0; x < width; x ++)
			{
				chunks.add(x, y, new Chunk(x, y, tdg));
				generateTerrain(chunks.get(x, y), y == 0 ? (x > 0 ? chunks.get(x - 1, y).getRightY() : 10) : -1, -1, y == 0);
			}
		}

		textures.init();
		vertices.init();
//		normals.init();
		
		terrainRendering = new Point[5 * 5];
		
		addElementsRelativeToPlayer(tdg.getPlayer());
	}
	
	private void addElementsRelativeToPlayer(Player player)
	{
		ArrayList<Chunk> chu = chunks.getElements();
		
		for (Chunk chunk : chu)
		{
			try
			{
				int vp = chunk.getVerticesPosition();
				int tp = chunk.getTexturesPosition();
				
				vertices.setData(vp, chunk.getVertices());
				
//				normals.getBuffer().position(position);
//				
//				normals.getBuffer().put(chunk.getNormals());

				textures.setData(tp, chunk.getTextures());
			}
			catch (IndexOutOfBoundsException ex)
			{
				
			}
		}
	}
	
//	public void recalculateTerrain(Player player)
//	{
//		addElementsRelativeToPlayer(player);
//	}

	public void translate(float dx, float dy)
	{
		x += dx;
		y += dy;
	}
	
	public void render()
	{
		glPushMatrix();
		{
			glTranslatef(x, y, 0);
			
			beginTextureDraw(textures.getId(), 2);
			beginVertexDraw(vertices.getId(), VERTEX_SIZE);
			
			tdg.getTerrain().bind();
			
			glDrawArrays(GL_QUADS, 0, 4 * Chunk.CHUNK_WIDTH * Chunk.CHUNK_HEIGHT * width * height);//(int)(4 * 16 * 16 * (width * (-(int)tdg.getPlayer().getOriginRelativeY() / (16 * tdg.getBlockSize())))), 4 * Chunk.CHUNK_WIDTH * Chunk.CHUNK_HEIGHT * 3 * 3);//(Chunk.CHUNK_SIZE * 5 * 5) + ((Chunk.CHUNK_SIZE / 3) * 2 * 5 * 5));
			
			endVertexDraw();
			endTextureDraw();
		}
		glPopMatrix();
	}
	
	public void removeBlock(int x, int y)
	{
		int chunkX = x / Chunk.CHUNK_WIDTH;
		int chunkY = y / Chunk.CHUNK_HEIGHT;

		if (y < 0)
		{
			y = (Chunk.CHUNK_HEIGHT - (Math.abs(y) % 16)) % 16;
			
			if (y > 0) chunkY --;
		}
		
		if (!chunks.is(chunkX, chunkY)) return;
		
		Chunk chunk = chunks.get(chunkX, chunkY);
		
		int vp = chunk.getVerticesPosition();
		int tp = chunk.getTexturesPosition();
		
		chunk.removeBlock(x % 16, y % 16);
		
		vertices.setData(vp, chunk.getVertices());
		textures.setData(tp, chunk.getTextures());
	}
	
	public void addBlock(int x, int y, Tile tile, boolean override)
	{
		int chunkX = x / Chunk.CHUNK_WIDTH;
		int chunkY = y / Chunk.CHUNK_HEIGHT;
		
		Chunk chunk = chunks.get(chunkX, chunkY);
		
		if (y < 0)
		{
			chunkY --;
			y = Chunk.CHUNK_HEIGHT - Math.abs(y);
		}
		
		if (chunk.isBlockAt(x % 16, y % 16))
		{
			if (!override)
			{
				return;
			}
		}
		
		int vp = chunk.getVerticesPosition();
		int tp = chunk.getTexturesPosition();
		
		chunk.addBlock(x % 16, y % 16, tile);
		
		vertices.setData(vp, chunk.getVertices());
		textures.setData(tp, chunk.getTextures());
	}
	
	public void setBlock(int x, int y, Tile tile)
	{
		
		textures.getBuffer().position((x + y * Chunk.CHUNK_WIDTH) * BLOCK_SIZE);
		
		float array[] = new float[BLOCK_SIZE];
		
		array = addRectTextureArray(tdg.getTerrain(), tile.getX(), tile.getY(), tile.getWidth(), tile.getHeight(), 0, array);
		
		textures.getBuffer().put(array);
	}
	
	public void moveBlock(int x1, int y1, int x2, int y2)
	{
		int index1 = x1 + y1 * Chunk.CHUNK_WIDTH;
		int index2 = x2 + y2 * Chunk.CHUNK_WIDTH;
		
		float temp[] = floatBufferToFloatArray(textures.getBuffer(), index1 * BLOCK_SIZE, BLOCK_SIZE);
		
		textures.getBuffer().position((index1) * BLOCK_SIZE);
		textures.getBuffer().put(floatBufferToFloatArray(textures.getBuffer(), index2 * BLOCK_SIZE, BLOCK_SIZE));
		textures.getBuffer().position((index2) * BLOCK_SIZE);
		textures.getBuffer().put(temp);
	}
	
	public float[] checkCollisions(Object o, Chunk loc[])
	{
		if (o instanceof Player)
		{
			Player player = (Player)o;
			
			for (int i = 0; i < loc.length; i ++)
			{
				Chunk chunk = null;
				
				try
				{
					chunk = chunks.get(loc[i].getRelativeX(), loc[i].getRelativeY());
				}
				catch (IndexOutOfBoundsException ex)
				{
					break;
				}
				
				for (int j = 0; j < Chunk.CHUNK_WIDTH * Chunk.CHUNK_HEIGHT; j ++)
				{
					if (chunk.getTile(j % 16, j / 16) != null && !chunk.getTile(j % 16, j / 16).getCollidable())
					{
						continue;
					}
					
					float widthOffset = player.getWidth() / 4f;
					
					float location[]  = chunk.getBlockLocation(j);
					float size[]      = chunk.getBlockSize(j);
					
					float playerX     = player.getScreenRelativeX() + widthOffset;
					float playerY     = player.getScreenRelativeY();
					
					float playerWidth   = (player.getWidth() / 2f);
					float playerHeight  = (player.getHeight());
					
					if (collided((int)playerX, (int)playerY, (int)playerWidth, (int)playerHeight, (int)(location[0] + x), (int)(location[1] + y - tileSize), (int)size[0], (int)size[1]))
					{
						float array[] = Intersection.rectangles(playerX, playerY, playerWidth, playerHeight, (int)(location[0] + x), (int)(location[1] + y - tileSize), (int)size[0], (int)size[1]);
						
						float xo = array[2];
						float yo = array[3] + size[1];
						
						return new float[] { xo, yo };
					}
				}
			}
		}
		
		return new float[] { 0, 0 };
	}
	
	public boolean collided(int x1, int y1, int width1, int height1, int x2, int y2, int width2, int height2)
	{
		return Intersects.rectangles(x1, -y1 + tdg.getDisplay().getHeight(), width1, height1, x2, -y2 + tdg.getDisplay().getHeight(), width2, height2);
	}
	
	public static void generateTerrain(Chunk chunk, int leftY, int rightY, boolean surface)
	{
		int cw = Chunk.CHUNK_WIDTH;
		int ch = Chunk.CHUNK_HEIGHT;
		
		if (surface)
		{
			int rand    = 0;
			int createY = leftY;
			
			if (createY < 0)
			{
				createY = (int)(Math.random() * ch);
			}
			else
			{
				createY += (int)(Math.random() * 3) - 1;
			}
			
			createY = (createY > ch ? ch : (createY < 0 ? 0 : createY));
			
			chunk.setLeftY(createY);
			
			Tile randomTile = null;
			
			for (int x = 0; x < cw; x ++)
			{
				createY = createY < 0 ? 0 : (createY > ch ? ch : createY);
				
				for (int y = 0; y < ch; y ++)
				{
					randomTile = getRandomTile((int)(y + (chunk.getOriginRelativeY() / tileSize)));
					
					if (y == createY)
					{
						if ((int)(Math.random() * 20) == 0)
						{
							chunk.addBlock(x, y, randomTile);
						}
						else
						{
							chunk.addBlock(x, y, Tile.getTile(Tile.GRASS));
						}
					}
					else if (y <= createY && y > createY - 4)
					{
						if ((int)(Math.random() * 5) == 0)
						{
							chunk.addBlock(x, y, randomTile);
						}
						else
						{
							chunk.addBlock(x, y, Tile.getTile(Tile.DIRT));
						}
					}
					else if (y < createY)
					{
						chunk.addBlock(x, y, randomTile);
					}
				}
				
				rightY   = createY;
				
				rand     = (int)(Math.random() * 3) - 1;
				
				createY += rand;
			}
			
			chunk.setRightY(rightY);
		}
		else
		{
			for (int x = 0; x < cw; x ++)
			{
				for (int y = 0; y < ch; y ++)
				{
					chunk.addBlock(x, y, getRandomTile((int)(y + (chunk.getOriginRelativeY() / tileSize))));
				}
			}
			
			chunk.setLeftY (ch);
			chunk.setRightY(ch);
		}
		
		short notCopy[] = new short[] { Tile.STONE, Tile.GRASS };
		
		boolean notReplace[] = new boolean[cw * ch];
		
		Tile tile = null, tile2 = null;
		
		for (int x = 0; x < cw; x ++)
		{
			for (int y = 0; y < ch; y ++)
			{
				tile = chunk.getTile(x, y);
				
				if (tile != null && !ArrayUtil.contains(notCopy, tile.getType()) && !notReplace[x + y * cw])
				{
					chunk.addBlock((x + 1 >= cw ? x : x + 1),  y,                      tile);
					chunk.addBlock(x,                         (y - 1 < 0 ? y : y - 1), tile);
					chunk.addBlock((x - 1 < 0 ? x : x - 1),    y,                      tile);
					
					notReplace[x                           +  y                      * cw] = true;
					notReplace[(x + 1 >= cw ? x : x + 1)   +  y                      * cw] = true;
					notReplace[x                           + (y - 1 < 0 ? y : y - 1) * cw] = true;
					notReplace[(x - 1 < 0 ? x : x - 1)     +  y                      * cw] = true;
					
					if (y + 1 < ch)
					{
						tile2 = chunk.getTile(x, y + 1);
						
						if (tile2 != null && !ArrayUtil.contains(notCopy, tile2.getType()) && !notReplace[x + (y + 1) * cw])
						{
							chunk.addBlock(x,       (y + 1 >= ch ? y : y + 1),     tile);
							notReplace[x          + (y + 1 >= ch ? y : y + 1) * cw] = true;
						}
					}
				}
			}
		}
	}
	
	public static Tile getRandomTile(int depth)
	{
		Tile tile = null;
		
		while (true)
		{
			int rand = (int)(Math.random() * Tile.getTilesQuantity());
			tile = Tile.getTile(rand);
			
			if (tile.getLowestDepth() <= depth && tile.getHighestDepth() >= depth)
			{
				if (tile.getRarity() > 0)
				{
					rand = (int)(Math.random() * tile.getRarity());
					
					if (rand == 0)
					{
						return tile;
					}
				}
			}
		}
	}
	
	public float[] floatBufferToFloatArray(FloatBuffer buffer, int offset, int length)
	{
		float array[] = new float[length];
		
		for (int i = 0; i < length; i ++)
		{
			array[i] = buffer.get(offset + i);
		}
		
		return array;
	}
	
	public Chunk getChunk(int x, int y)
	{
		return chunks.get(x, y);
	}
	
	public float getX()
	{
		return x;
	}
	
	public float getY()
	{
		return y;
	}
	
	public ArrayList<Chunk> getChunkElements()
	{
		return chunks.getElements();
	}
}