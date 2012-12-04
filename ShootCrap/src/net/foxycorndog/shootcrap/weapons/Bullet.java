package net.foxycorndog.shootcrap.weapons;

import net.foxycorndog.jdoogl.GL;

public class Bullet
{
	private float x, y;
	private float deltaX, deltaY;

	public Bullet(float x, float y, float destX, float destY)
	{
		this.x      = x;
		this.y      = y;
		this.deltaX = destX / x;
		this.deltaY = destY / y;
	}
	
	public void update()
	{
		x += deltaX;
		y += deltaY;
	}
	
	public float getX()
	{
		return x;
	}

	public float getY()
	{
		return y;
	}
}
