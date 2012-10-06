package net.foxycorndog.invasion.entities;

import net.foxycorndog.invasion.Invasion;
import net.foxycorndog.jdoogl.GL;
import net.foxycorndog.jdoogl.image.imagemap.Texture;
import net.foxycorndog.jdoogl.util.LightBuffer;
import net.foxycorndog.jdoogl.util.Point;

public class Bullet extends Entity implements Cloneable
{
//	private boolean remove;
	
	private Bullet  thisBullet;
	
	private Ship    shotFrom;
	
	private String  location;
	
	public Bullet(String location, Ship shotFrom)
	{
		super(new Texture(location, "PNG", true, false));
		
		thisBullet    = this;
		
		this.shotFrom = shotFrom;
		
		this.location = location;
		
		float width   = getWidth();
		float height  = getHeight();
		
		setVerticesBuffer(new LightBuffer(4 * 2));
		setTexturesBuffer(new LightBuffer(4 * 2));
		
		LightBuffer verticesBuffer = getVerticesBuffer();
		LightBuffer texturesBuffer = getTexturesBuffer();
		
		verticesBuffer.setData(0, GL.addRectVertexArrayf(0, 0, width, height, 0, null));
		texturesBuffer.setData(0, GL.addRectTextureArrayf(getTexture(), 0, null));
	}
	
	public void shoot(final Point start, final Point destination, final Ship ship)
	{
		new Thread()
		{
			public void run()
			{
				setLocation(start.x, start.y);
				
				setRenderable(true);
				
				float distanceTravelled = 0;
				float totalDistance     = (float)Math.sqrt(Math.pow(start.x - destination.x, 2) + Math.pow(start.y - destination.y, 2));
				
				while (distanceTravelled < totalDistance)
				{
					distanceTravelled += (Invasion.getScale() / 5f);
					
					move(0, (start.y > destination.y ? -1 : 1) * (Invasion.getScale() / 5f));
					
					try
					{
						Thread.sleep(3);
					}
					catch (InterruptedException ex)
					{
						
					}
				}
				
				Invasion.entities.remove(thisBullet);
			}
		}.start();
	}
	
	public Ship getShotFrom()
	{
		return shotFrom;
	}
	
	public void onCollision(Entity entity)
	{
		if (entity != shotFrom && entity instanceof Bullet == false)
		{
			Invasion.entities.remove(this);
		}
	}
	
	public Bullet clone()
	{
		try
		{
			return (Bullet)super.clone();
		}
		catch (CloneNotSupportedException e)
		{
			e.printStackTrace();
			System.exit(1);
		}
		
		return null;
	}
}