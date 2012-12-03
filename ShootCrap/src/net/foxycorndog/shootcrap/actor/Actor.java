package net.foxycorndog.shootcrap.actor;

import net.foxycorndog.jdoogl.GL;
import net.foxycorndog.jdoogl.image.imagemap.SpriteSheet;

public class Actor
{
	private int   width, height;
	private int   spriteWidth, spriteHeight;
	private int   spriteRow;
	
	private float x, y;
	
	private static SpriteSheet sprites;
	
	static
	{
		sprites = new SpriteSheet("res/images/actors.png", 10, 10);
	}
	
	public Actor(int spriteRow, int spriteWidth, int spriteHeight)
	{
		this.spriteRow    = spriteRow;
		this.spriteWidth  = spriteWidth;
		this.spriteHeight = spriteHeight;
	}
	
	public static float[] getVertices()
	{
		return GL.addRectVertexArrayf(0, 0, width, height, offset, array)
	}
	
	public static SpriteSheet getSprites()
	{
		return sprites;
	}
}