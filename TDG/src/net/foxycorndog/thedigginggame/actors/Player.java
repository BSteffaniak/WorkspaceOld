package net.foxycorndog.thedigginggame.actors;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;

import net.foxycorndog.presto2d.PrestoGL2D;
import net.foxycorndog.presto2d.util.ArrayUtil;
import net.foxycorndog.presto2d.util.Buffer;
import net.foxycorndog.presto2d.util.SpriteSheet;
import net.foxycorndog.presto3d.graphics.PrestoGL3D;
import net.foxycorndog.thedigginggame.Display;
import net.foxycorndog.thedigginggame.TheDiggingGame;
import net.foxycorndog.thedigginggame.map.Chunk;
import net.foxycorndog.thedigginggame.map.Map;

import static net.foxycorndog.thedigginggame.TheDiggingGame.*;

public class Player extends Actor
{
	//private float            vertices[], normals[], textures[];
	
	//private int              verticesId,     normalsId,     texturesId;
	
	//private FloatBuffer      verticesBuffer, normalsBuffer, texturesBuffer;
	//private ByteBuffer       mapBuffer;
	
	private Buffer  vertices, normals, textures;
	
	private Display display;
	
	private static final int AMOUNT_OF_VERTICES = 4;
	private static final int VERTEX_SIZE        = 3;
	private static final int RECT_SIZE          = AMOUNT_OF_VERTICES * VERTEX_SIZE;
	
	private static SpriteSheet playerSprites;
	
	public Player(float x, float y, Display display, Map map)
	{
		super(x, y, 16, 32, .75f, 1.4f, tileSize, map);
		
		init(display);
	
		
	}
	
	public Player(TheDiggingGame tdg, Display display, Map map)
	{
		super(0, 0, 16, 32, .75f, 1.4f, tileSize, map);
		
		init(display);
	}
	
	public void init(Display display)
	{
		this.display = display;
		
		vertices = new Buffer(RECT_SIZE * 6);
		normals  = new Buffer(RECT_SIZE * 6);
		textures = new Buffer(RECT_SIZE * 6);
		
		vertices.setBuffer(BufferUtils.createFloatBuffer(RECT_SIZE * 6));
		vertices.getBuffer().put(vertices.getElements());
		vertices.getBuffer().flip();
		
		normals.setBuffer(BufferUtils.createFloatBuffer(RECT_SIZE * 6));
		normals.getBuffer().put(normals.getElements());
		normals.getBuffer().flip();
		
		textures.setBuffer(BufferUtils.createFloatBuffer(RECT_SIZE * 6));
		textures.getBuffer().put(textures.getElements());
		textures.getBuffer().flip();
		
		vertices.init();
		normals.init();
		textures.init();
		
		setFacing(FRONT);
	}
	
	public static void init()
	{
		playerSprites = new SpriteSheet("res/images/character/skins/Default.png", "PNG", 16, 8, true);
	}
	
	public void addVertices(int direction)
	{
		int index = 0;
		
		int z = -1;
		
		float array[] = vertices.getElements();
		
		if (direction == FRONT || direction == BACK)
		{
			// Left leg
			array[index ++] = 4;  array[index ++] = 0; array[index ++] = z;
			
			array[index ++] = 4;  array[index ++] = 12; array[index ++] = z;
			
			array[index ++] = 8;  array[index ++] = 12; array[index ++] = z;
			
			array[index ++] = 8;  array[index ++] = 0; array[index ++] = z;
			
			
			// Right leg
			array[index ++] = 8;  array[index ++] = 0; array[index ++] = z;
			
			array[index ++] = 8;  array[index ++] = 12; array[index ++] = z;
			
			array[index ++] = 12; array[index ++] = 12; array[index ++] = z;
			
			array[index ++] = 12; array[index ++] = 0; array[index ++] = z;
			
			
			// Left arm
			array[index ++] = 0;  array[index ++] = 12; array[index ++] = z;
			
			array[index ++] = 0;  array[index ++] = 24; array[index ++] = z;
			
			array[index ++] = 4;  array[index ++] = 24; array[index ++] = z;
			
			array[index ++] = 4;  array[index ++] = 12; array[index ++] = z;
			
			
			// Torso
			array[index ++] = 4;  array[index ++] = 12; array[index ++] = z;
			
			array[index ++] = 4;  array[index ++] = 24; array[index ++] = z;
			
			array[index ++] = 12; array[index ++] = 24; array[index ++] = z;
			
			array[index ++] = 12; array[index ++] = 12; array[index ++] = z;
			
			
			// Right arm
			array[index ++] = 12; array[index ++] = 12; array[index ++] = z;
			
			array[index ++] = 12; array[index ++] = 24; array[index ++] = z;
			
			array[index ++] = 16; array[index ++] = 24; array[index ++] = z;
			
			array[index ++] = 16; array[index ++] = 12; array[index ++] = z;
			
			
			// Head
			array[index ++] = 4;  array[index ++] = 24; array[index ++] = z;
			
			array[index ++] = 4;  array[index ++] = 32; array[index ++] = z;
			
			array[index ++] = 12; array[index ++] = 32; array[index ++] = z;
			
			array[index ++] = 12; array[index ++] = 24; array[index ++] = z;
		}
		else if (direction == RIGHT || direction == LEFT)
		{
			// Left leg
			array[index ++] = 6;  array[index ++] = 0; array[index ++] = z;
			
			array[index ++] = 6;  array[index ++] = 12; array[index ++] = z;
			
			array[index ++] = 10; array[index ++] = 12; array[index ++] = z;
			
			array[index ++] = 10; array[index ++] = 0; array[index ++] = z;
			
			
			// Right leg
			array[index ++] = 6;  array[index ++] = 0; array[index ++] = z;
			
			array[index ++] = 6;  array[index ++] = 12; array[index ++] = z;
			
			array[index ++] = 10; array[index ++] = 12; array[index ++] = z;
			
			array[index ++] = 10; array[index ++] = 0; array[index ++] = z;
			
			
			// Left arm
			array[index ++] = 6;  array[index ++] = 12; array[index ++] = z;
			
			array[index ++] = 6;  array[index ++] = 24; array[index ++] = z;
			
			array[index ++] = 10; array[index ++] = 24; array[index ++] = z;
			
			array[index ++] = 10; array[index ++] = 12; array[index ++] = z;
			
			
			// Torso
			array[index ++] = 6;  array[index ++] = 12; array[index ++] = z;
			
			array[index ++] = 6;  array[index ++] = 24; array[index ++] = z;
			
			array[index ++] = 10; array[index ++] = 24; array[index ++] = z;
			
			array[index ++] = 10; array[index ++] = 12; array[index ++] = z;
			
			
			// Right arm
			array[index ++] = 6; array[index ++] = 12; array[index ++] = z;
			
			array[index ++] = 6; array[index ++] = 24; array[index ++] = z;
			
			array[index ++] = 10; array[index ++] = 24; array[index ++] = z;
			
			array[index ++] = 10; array[index ++] = 12; array[index ++] = z;
			
			
			// Head
			array[index ++] = 4;  array[index ++] = 24; array[index ++] = z;
			
			array[index ++] = 4;  array[index ++] = 32; array[index ++] = z;
			
			array[index ++] = 12; array[index ++] = 32; array[index ++] = z;
			
			array[index ++] = 12; array[index ++] = 24; array[index ++] = z;
		}
		
		for (int i = 0; i < vertices.getElements().length; i ++)
		{
			vertices.getElements()[i] *= getScaleFactor();
		}
	}
	
	public void repaintCharacter()
	{
		vertices.refreshData();
		textures.refreshData();
	}
	
	public void addTextures(int direction)
	{
		float array[] = textures.getElements();
		
		int z = 0;
		
		if (direction == FRONT)
		{
			array = PrestoGL2D.addRectTextureArray(playerSprites,  1, 5, z, 1, 3, RECT_SIZE * 0, array);
			array = PrestoGL2D.addRectTextureArray(playerSprites,  1, 5, z, 1, 3, RECT_SIZE * 1, array);
			array = PrestoGL2D.addRectTextureArray(playerSprites, 11, 5, z, 1, 3, RECT_SIZE * 2, array);
			array = PrestoGL2D.addRectTextureArray(playerSprites,  5, 5, z, 2, 3, RECT_SIZE * 3, array);
			array = PrestoGL2D.addRectTextureArray(playerSprites, 11, 5, z, 1, 3, RECT_SIZE * 4, array);
			array = PrestoGL2D.addRectTextureArray(playerSprites,  2, 2, z, 2, 2, RECT_SIZE * 5, array);
		}
		else if (direction == BACK)
		{
			array = PrestoGL2D.addRectTextureArray(playerSprites,  3, 5, z, 1, 3, RECT_SIZE * 0, array);
			array = PrestoGL2D.addRectTextureArray(playerSprites,  3, 5, z, 1, 3, RECT_SIZE * 1, array);
			array = PrestoGL2D.addRectTextureArray(playerSprites, 13, 5, z, 1, 3, RECT_SIZE * 2, array);
			array = PrestoGL2D.addRectTextureArray(playerSprites,  8, 5, z, 2, 3, RECT_SIZE * 3, array);
			array = PrestoGL2D.addRectTextureArray(playerSprites, 13, 5, z, 1, 3, RECT_SIZE * 4, array);
			array = PrestoGL2D.addRectTextureArray(playerSprites,  6, 2, z, 2, 2, RECT_SIZE * 5, array);
		}
		else if (direction == LEFT)
		{
			array = PrestoGL2D.addRectTextureArray(playerSprites,  2, 5, z, 1, 3, RECT_SIZE * 0, array);
			array = PrestoGL2D.addRectTextureArray(playerSprites,  2, 5, z, 1, 3, RECT_SIZE * 1, array);
			array = PrestoGL2D.addRectTextureArray(playerSprites, 12, 5, z, 1, 3, RECT_SIZE * 2, array);
			array = PrestoGL2D.addRectTextureArray(playerSprites,  7, 5, z, 2, 3, RECT_SIZE * 3, array);
			array = PrestoGL2D.addRectTextureArray(playerSprites, 12, 5, z, 1, 3, RECT_SIZE * 4, array);
			array = PrestoGL2D.addRectTextureArray(playerSprites,  4, 2, z, 2, 2, RECT_SIZE * 5, array);
		}
		else if (direction == RIGHT)
		{
			array = PrestoGL2D.addRectTextureArray(playerSprites,  0, 5, z, 1, 3, RECT_SIZE * 0, array);
			array = PrestoGL2D.addRectTextureArray(playerSprites,  0, 5, z, 1, 3, RECT_SIZE * 1, array);
			array = PrestoGL2D.addRectTextureArray(playerSprites, 10, 5, z, 1, 3, RECT_SIZE * 2, array);
			array = PrestoGL2D.addRectTextureArray(playerSprites,  3, 5, z, 2, 3, RECT_SIZE * 3, array);
			array = PrestoGL2D.addRectTextureArray(playerSprites, 10, 5, z, 1, 3, RECT_SIZE * 4, array);
			array = PrestoGL2D.addRectTextureArray(playerSprites,  0, 2, z, 2, 2, RECT_SIZE * 5, array);
		}
		
		repaintCharacter();
	}
	
	public void jump()
	{
		if (getAlreadyJumped())
		{
			return;
		}
		else if (getJumping() || getAirborn())
		{
			if (!move(UP) || getOriginRelativeY() > getStartJumpY() + (tileSize * 1.8))
			{
				setJumping(false);
				setAlreadyJumped(true);
			}
		}
		else
		{
			setJumping(true);
			setAirborn(true);
			
			setStartJumpY((int)getOriginRelativeY());
		}
	}
	
	public boolean move(int direction)
	{
		super.move(direction);
		
		float collision[] = getMap().checkCollisions(this, getTouchableChunks());
		
		if (!ArrayUtil.equals(collision, Map.NO_COLLISION) && !ArrayUtil.equals(collision, new float[] { getWidth() / 2, getHeight() }))
		{
			if (direction == UP)
			{
				move(direction, -collision[1] + tileSize);
			}
			else if (direction == DOWN)
			{
				move(direction, -collision[1]);
			}
			else if (direction == LEFT)
			{
				move(direction, -collision[0]);
			}
			else if (direction == RIGHT)
			{
				move(direction, -collision[0]);
			}
			
			return false;
		}
		
		if (direction == RIGHT && getScreenRelativeX() + getWidth() > display.getWidth() - 150)
		{
			super.move(direction, -getSpeed(direction));
			
			getMap().translate(-getSpeed(direction), 0);
			
			setOriginRelativeX(getOriginRelativeX() + getSpeed(direction));
		}
		else if (direction == LEFT && getScreenRelativeX() < 150)
		{
			super.move(direction, getSpeed(direction));
			
			getMap().translate(-getSpeed(direction), 0);
			
			setOriginRelativeX(getOriginRelativeX() - getSpeed(direction));
		}
		
		if (direction == UP && getScreenRelativeY() + getHeight() > display.getHeight() - 150)
		{
			super.move(direction, -getSpeed(direction));
			
			getMap().translate(0, -getSpeed(direction));
			
			setOriginRelativeY(getOriginRelativeY() + getSpeed(direction));
		}
		else if (direction == DOWN && getScreenRelativeY() < 150)
		{
			super.move(direction, getSpeed(direction));
			
			getMap().translate(0, -getSpeed(direction));
			
			setOriginRelativeY(getOriginRelativeY() - -getSpeed(direction));
		}
		
		return true;
	}
	
	public void setFacing(int direction)
	{
		super.setFacing(direction);
		
		addVertices(direction);
		addTextures(direction);
	}
	
	public void render()
	{
		glPushMatrix();
		
		glTranslatef(getScreenRelativeX(), getScreenRelativeY(), 0);
		
		PrestoGL3D.beginVertexDraw(vertices.getId(), VERTEX_SIZE);
		PrestoGL3D.beginTextureDraw(textures.getId());
		
		playerSprites.bind();
		
		glDrawArrays(GL_QUADS, 0, 6 * 4);
		
		PrestoGL3D.endTextureDraw();
		PrestoGL3D.endVertexDraw();
		
		glPopMatrix();
	}
}