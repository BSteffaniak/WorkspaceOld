package net.foxycorndog.shootcrap.actor;

import java.util.ArrayList;

import net.foxycorndog.jdoogl.GL;
import net.foxycorndog.jdoogl.image.imagemap.SpriteSheet;
import net.foxycorndog.jdoutil.LightBuffer;
import net.foxycorndog.jdoutil.VerticesBuffer;
import net.foxycorndog.shootcrap.tile.Tile;
import net.foxycorndog.shootcrap.weapons.Gun;

public abstract class Actor
{
	private int                width, height;
	private int                spriteWidth, spriteHeight;
	private int                spriteRow;
	private int                walkCycle;
	private int                id;
	
	private float              x, y;
	
	private Gun                gun;
	
	private static int         staticId;
	
	private static SpriteSheet sprites;
	
	private static final int   spriteSheetColWidth = 9, spriteSheetRowHeight = 16;
	
	static
	{
		sprites = new SpriteSheet("res/images/actors.png", 10, 10);
	}
	
	public Actor(int spriteRow, int spriteWidth, int spriteHeight)
	{
		this.spriteRow    = spriteRow;
		this.spriteWidth  = spriteWidth;
		this.spriteHeight = spriteHeight;
		this.id           = staticId ++;
	}
	
	public float[] getVertices()
	{
		return GL.addRectVertexArrayf(0, 0, spriteSheetColWidth * spriteWidth, spriteSheetRowHeight * spriteHeight, 0, null);
	}
	
	public float[] getTextures()
	{
		return GL.addRectTextureArrayf(sprites, 0, spriteRow, spriteWidth, spriteHeight, 0, null);
	}
	
	public static SpriteSheet getSprites()
	{
		return sprites;
	}
	
	public int getId()
	{
		return id;
	}
	
	public void render()
	{
		GL.beginManipulation();
		{
			GL.translatef(x, y, 0);
			
			GL.renderQuads(getVerticesBuffer(), getTexturesBuffer(), null, null, Actor.getSprites(), walkCycle, walkCycle + 1, null);
		}
		GL.endManipulation();
	}
	
	public void delete(ArrayList<Actor> actors)
	{
		synchronized (actors)
		{
			for (int i = actors.size() - 1; i >= 0; i --)
			{
				synchronized (actors)
				{
					if (actors.get(i) == this)
					{
						actors.set(i, null);
					}
				}
			}
		}
	}
	
	public void move(float dx, float dy)
	{
		x += dx;
		y += dy;
	}
	
	public void setLocation(float x, float y)
	{
		this.x = x;
		this.y = y;
	}
	
	public float getX()
	{
		return x;
	}
	
	public void setX(float x)
	{
		this.x = x;
	}
	
	public float getY()
	{
		return x;
	}
	
	public void setY(float y)
	{
		this.y = y;
	}
	
	public int getSpriteRow()
	{
		return spriteRow;
	}
	
	public int getSpriteWidth()
	{
		return spriteWidth;
	}
	
	public int getSpriteHeight()
	{
		return spriteHeight;
	}
	
	public int getWidth()
	{
		return spriteWidth * spriteSheetColWidth;
	}
	
	public int getHeight()
	{
		return spriteHeight * spriteSheetRowHeight;
	}
	
	public int getSpriteSheetColWidth()
	{
		return spriteSheetColWidth;
	}
	
	public int getSpriteSheetRowHeight()
	{
		return spriteSheetRowHeight;
	}
	
	public Gun getGun()
	{
		return gun;
	}
	
	public void setGun(Gun gun)
	{
		this.gun = gun;
	}
	
	public abstract VerticesBuffer getVerticesBuffer();
	
	public abstract LightBuffer getTexturesBuffer();
}
