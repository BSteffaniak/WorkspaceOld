package net.foxycorndog.nostalgia.weapons;

import net.foxycorndog.jdoogl.GL;
import net.foxycorndog.jdoogl.geometry.Point;
import net.foxycorndog.jdoogl.geometry.Vector;

public class Bullet
{
	private float  velocity;
	private float  yaw, pitch, roll;
	
	private Point  location;
	
	public Bullet(Point location, float velocity, float yaw, float pitch, float roll)
	{
		this.location = location;
	}
	
	public void update()
	{
		float x =  velocity * (float)Math.sin(Math.toRadians(yaw));
		float z = -velocity * (float)Math.cos(Math.toRadians(yaw));
		
		float y =  velocity * (float)Math.tan(Math.toRadians(pitch));

		x      -=  velocity * (float)Math.sin(Math.toRadians(yaw + 90));
		x      +=  velocity * (float)Math.cos(Math.toRadians(yaw + 90));
	}
	
	public void render()
	{
		GL.beginManipulation();
		{
			GL.translatef(location);
			GL.rotatef(yaw, pitch, roll);
			
		}
		GL.endManipulation();
	}
}