package net.foxycorndog.thedigginggame.map;

import static net.foxycorndog.presto2d.PrestoGL2D.beginTextureDraw;
import static net.foxycorndog.presto2d.PrestoGL2D.beginVertexDraw;
import static net.foxycorndog.presto2d.PrestoGL2D.endTextureDraw;
import static net.foxycorndog.presto2d.PrestoGL2D.endVertexDraw;
import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glDrawArrays;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glTranslatef;

import java.nio.FloatBuffer;

import net.foxycorndog.presto2d.PrestoGL2D;
import net.foxycorndog.presto2d.util.SpriteSheet;
import net.foxycorndog.thedigginggame.TheDiggingGame;
import net.foxycorndog.thedigginggame.actors.Player;
import net.foxycorndog.thedigginggame.tiles.Tile;

import static net.foxycorndog.thedigginggame.TheDiggingGame.*;

public class Chunk
{
	private float          vertices[], normals[], textures[];
	
	private int            rx, ry;
	private int            leftY, rightY;
	private int            amountOfBlocks;
	private int            verticesPosition, texturesPosition;
	
	private short blocks[];
	
	private TheDiggingGame   tdg;
	
	public static  final int CHUNK_WIDTH         = 16;
	public static  final int CHUNK_HEIGHT        = 16;
	
	private static final int AMOUNT_OF_VERTICES  = 4;
	private static final int VERTEX_SIZE         = 3;
	private static final int BLOCK_SIZE          = AMOUNT_OF_VERTICES * VERTEX_SIZE;
	private static final int TEXTURES_SIZE       = AMOUNT_OF_VERTICES * 2;
	
	private static final float EMPTY_SPACE       = -12.131531f;
	
	private static int         currentPosition   = 0;
	
	public  static final int CHUNK_SIZE          = BLOCK_SIZE    * CHUNK_WIDTH * CHUNK_HEIGHT;
	public  static final int TEXTURES_CHUNK_SIZE = TEXTURES_SIZE * CHUNK_WIDTH * CHUNK_HEIGHT;
	
	public Chunk(int rx, int ry, TheDiggingGame tdg)
	{
		init(rx, ry, tdg);
	}
	
	public void init(int rx, int ry, TheDiggingGame tdg)
	{
		this.rx  = rx;
		this.ry  = ry;
		
		this.tdg = tdg;
		
		leftY    = -1;
		rightY   = -1;
		
		vertices = new float[CHUNK_SIZE];
		normals  = new float[CHUNK_SIZE];
		textures = new float[TEXTURES_CHUNK_SIZE];
		
		blocks   = new short[CHUNK_WIDTH * CHUNK_HEIGHT];
		
		initBlocks();
		
		verticesPosition = currentPosition;
		texturesPosition = (currentPosition / 3) * 2;
		currentPosition += CHUNK_SIZE;
	}
	
	private void initBlocks()
	{
		for (int i = 0; i < vertices.length; i ++)
		{
			vertices[i] = EMPTY_SPACE;
		}
		
		for (int i = 0; i < blocks.length; i ++)
		{
			blocks  [i] = -1;
		}
	}
	
	public void render()
	{
//		glPushMatrix();
//		{
//			glTranslatef(x, y, 0);
//			
//			beginTextureDraw(textures.getId(), 2);
//			beginVertexDraw(vertices.getId(), VERTEX_SIZE);
//			
//			tdg.getTerrain().bind();
//			
//			glDrawArrays(GL_QUADS, 0, 4 * Chunk.CHUNK_WIDTH * Chunk.CHUNK_HEIGHT * width * height);//(int)(4 * 16 * 16 * (width * (-(int)tdg.getPlayer().getOriginRelativeY() / (16 * tdg.getBlockSize())))), 4 * Chunk.CHUNK_WIDTH * Chunk.CHUNK_HEIGHT * 3 * 3);//(Chunk.CHUNK_SIZE * 5 * 5) + ((Chunk.CHUNK_SIZE / 3) * 2 * 5 * 5));
//			
//			endVertexDraw();
//			endTextureDraw();
//		}
//		glPopMatrix();
	}
	
	public void addBlock(int x, int y, Tile tile)
	{
		float bs = tileSize;
		
		int offset = x + y * CHUNK_WIDTH;
		
		vertices = PrestoGL2D.addSquareVertexArray(getOriginRelativeX() + (x * bs), getOriginRelativeY() + (y * bs), tile.getZ(), bs, offset * BLOCK_SIZE, vertices);
		normals  = PrestoGL2D.addSquareNormalArray(getOriginRelativeX() + (x * bs), getOriginRelativeY() + (y * bs), tile.getZ(), offset * BLOCK_SIZE, normals);
		textures = PrestoGL2D.addRectTextureArray (tdg.getTerrain(), tile.getX(), tile.getY(), tile.getWidth(), tile.getHeight(), offset * TEXTURES_SIZE, textures);
		
		blocks[offset] = tile.getType();
		
		amountOfBlocks ++;
	}
	
	public void removeBlock(int x, int y)
	{
		int offset = (x + y * CHUNK_WIDTH) * BLOCK_SIZE;
		
		for (int i = 0; i < BLOCK_SIZE && i + offset >= 0; i ++)
		{
			vertices[i + offset] = EMPTY_SPACE;
			normals [i + offset] = 0;
		}
		
		offset = (x + y * CHUNK_WIDTH) * TEXTURES_SIZE;
		
		for (int i = offset; i < offset + TEXTURES_SIZE; i ++)
		{
			textures[i] = 0;
		}
		
		blocks[offset / BLOCK_SIZE] = -1;
		
		amountOfBlocks --;
	}
	
	public boolean collided(Object o)
	{
		if (o instanceof Player)
		{
			Player p = (Player)o;
			
			for (int i = 0; i < vertices.length / BLOCK_SIZE; i ++)
			{
				if (collided(vertices[i * BLOCK_SIZE + 0], vertices[i * BLOCK_SIZE + 1], p.getScreenRelativeX(), p.getScreenRelativeY(), 10, 10, p.getWidth(), p.getHeight()))
				{
					return true;
				}
			}
		}
		
		return false;
	}
	
	private boolean collided(float x1, float y1, float x2, float y2, float width1, float height1, float width2, float height2)
	{
		if (((x1 > x2 && x1 < x2 + width2) || x2 > x1 && x2 < x1 + width1) &&
				((y1 > y2 && y1 < y2 + height2) || y2 > y1 && y2 < y1 + height1))
		{
			return true;
		}
		
		return false;
	}
	
	public boolean isBlockAt(int x, int y)
	{
		return vertices[(x + y * CHUNK_WIDTH) * BLOCK_SIZE] != EMPTY_SPACE;
	}
	
	public float[] getBlockLocation(int index)
	{
		return new float[] { vertices[BLOCK_SIZE * index], vertices[BLOCK_SIZE * index + 1] };
	}
	
	public float[] getBlockSize(int index)
	{
		return new float[] { (vertices[BLOCK_SIZE * (index + 1) - 3] - vertices[BLOCK_SIZE * index]), (vertices[BLOCK_SIZE * index + 4] - vertices[BLOCK_SIZE * index + 1]) };
	}
	
	public float getOriginRelativeX()
	{
		return rx * CHUNK_WIDTH * tileSize;
	}
	
	public float getOriginRelativeY()
	{
		return ry * CHUNK_HEIGHT * tileSize;
	}
	
	public int getAmountOfBlocks()
	{
		return amountOfBlocks;
	}
	
	public float[] getVertices()
	{
		return vertices;
	}
	
	public void setVertices(float vertices[])
	{
		this.vertices = vertices;
	}
	
	public float[] getNormals()
	{
		return normals;
	}
	
	public void setNormals(float normals[])
	{
		this.normals = normals;
	}
	
	public float[] getTextures()
	{
		return textures;
	}
	
	public void setTextures(float textures[])
	{
		this.textures = textures;
	}
	
	public int getLeftY()
	{
		return leftY;
	}
	
	public void setLeftY(int y)
	{
		this.leftY = y;
	}
	
	public int getRightY()
	{
		return rightY;
	}
	
	public void setRightY(int y)
	{
		this.rightY = y;
	}
	
	public TheDiggingGame getTdg()
	{
		return tdg;
	}
	
	public void addVerticesToFloatBuffer(FloatBuffer buffer)
	{
		buffer.position(verticesPosition);
		
		buffer.put(vertices);
	}
	
	public void addNormalsToFloatBuffer(FloatBuffer buffer)
	{
		buffer.position(verticesPosition);
		
		buffer.put(normals);
	}
	
	public void addTexturesToFloatBuffer(FloatBuffer buffer)
	{
		buffer.position(texturesPosition);
		
		buffer.put(textures);
	}
	
	public int getVerticesPosition()
	{
		return verticesPosition;
	}
	
	public int getTexturesPosition()
	{
		return texturesPosition;
	}
	
	public int getRelativeX()
	{
		return rx;
	}
	
	public int getRelativeY()
	{
		return ry;
	}
	
	public Tile getTile(int x, int y)
	{
		return blocks[x + y * CHUNK_WIDTH] == -1 ? null : Tile.getTile(blocks[x + y * CHUNK_WIDTH]);
	}
	
	public Tile getTile(int index)
	{
		return blocks[index] == -1 ? null : Tile.getTile(blocks[index]);
	}
}