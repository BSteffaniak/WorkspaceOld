package net.foxycorndog.shootcrap.actor;

public class Actor
{
	private int   width, height;
	private int   spriteWidth, spriteHeight;
	private int   spriteX, spriteY;
	
	private float x, y;
	
	public Actor(int spriteX, int spriteY, int spriteWidth, int spriteHeight)
	{
		this.spriteX      = spriteX;
		this.spriteY      = spriteY;
		this.spriteWidth  = spriteWidth;
		this.spriteHeight = spriteHeight;
	}
	
	public void render()
	{
		
	}
}