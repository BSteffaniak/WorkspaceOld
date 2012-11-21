package net.foxycorndog.nostalgia.items.weapons;

import net.foxycorndog.jdoogl.GL;
import net.foxycorndog.jdoogl.geometry.Point;
import net.foxycorndog.jdoogl.geometry.Vector;
import net.foxycorndog.nostalgia.map.Map;

public class Bullet
{
	private int        id;
	
	private int        times = 0;
	
	private float      velocity;
	private float      x, y, z;
	private float      yaw, pitch, roll;
	
	private Map        map;
	
	private static int curId = 0;
	
	public Bullet(Point location, float velocity, float yaw, float pitch, float roll, Map map)
	{
		this.x        = location.getX();
		this.y        = location.getY();
		this.z        = location.getZ();
		
		this.yaw      = yaw;
		this.pitch    = pitch;
		this.roll     = roll;
		
		this.velocity = velocity;
		
		this.map      = map;
		
		this.id = curId;
		
		curId ++;
		
		if (curId >= map.getMaxBullets())
		{
			curId = 0;
			
			map.removeBullet(curId);
		}
	}
	
	public void update(int dfps)
	{
		float slope = (float)Math.cos(Math.toRadians(pitch));
		
		x += velocity * (float)Math.sin(Math.toRadians(yaw)) * slope;
		y -= velocity * (float)Math.sin(Math.toRadians(pitch));
		z -= velocity * (float)Math.cos(Math.toRadians(yaw)) * slope;
		
		times ++;
		
		if (times >= 1000000)
		{
			map.removeBullet(id);
		}
		
//		y -= 0.005f;
	}
	
	public float getX()
	{
		return x;
	}
	
	public float getY()
	{
		return y;
	}
	
	public float getZ()
	{
		return z;
	}
	
	public int getId()
	{
		return id;
	}
}