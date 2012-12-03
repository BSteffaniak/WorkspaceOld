package net.foxycorndog.shootcrap.actor;

import net.foxycorndog.jdoogl.GL;
import net.foxycorndog.jdoogl.image.imagemap.SpriteSheet;

public class Actor
{
	private int   width, height;
	private int   spriteWidth, spriteHeight;
	private int   spriteRow;
	private int   id;
	
	private float x, y;
	
	private static final int spriteSheetColWidth = 9, spriteSheetRowHeight = 16;
	
	private static SpriteSheet sprites;
	
	static
	{
		sprites = new SpriteSheet("res/images/actors.png", 10, 10);
	}
	
	public Actor(int spriteRow, int spriteWidth, int spriteHeight, int id)
	{
		this.spriteRow    = spriteRow;
		this.spriteWidth  = spriteWidth;
		this.spriteHeight = spriteHeight;
		this.id           = id;
	}
	
	public static float[] getVertices()
	{
		return GL.addRectVertexArrayf(0, 0, spriteSheetColWidth, spriteSheetRowHeight, 0, null);
	}
	
	public static SpriteSheet getSprites()
	{
		return sprites;
	}
	
	public int getId()
	{
		return id;
	}
}
