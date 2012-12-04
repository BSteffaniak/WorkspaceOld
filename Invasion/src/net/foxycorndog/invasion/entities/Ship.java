package net.foxycorndog.invasion.entities;

import java.util.ArrayList;

import net.foxycorndog.invasion.Invasion;
import net.foxycorndog.jdoogl.GL;
import net.foxycorndog.jdoogl.components.Frame;
import net.foxycorndog.jdoogl.image.imagemap.Texture;
import net.foxycorndog.jdoutil.Intersects;
import net.foxycorndog.jdoutil.LightBuffer;
import net.foxycorndog.jdoogl.geometry.Point;
import net.foxycorndog.jdoutil.VerticesBuffer;

public class Ship extends Entity
{
	private boolean powerUps[];
	
//	private ArrayList<Bullet> bullets;
	
	public static class PowerUp
	{
		private static int id;
		
		public static final int RAPID_SHOOT = id ++, DOUBLE_FORCE = id ++, SUPER_SPEED = id ++;
		
		public static int getLength()
		{
			return id;
		}
	}
	
	public Ship(String location)
	{
		super(new Texture(location));
		
		float width  = getWidth();
		float height = getHeight();
		
		setVerticesBuffer(new VerticesBuffer(4 * 2, 2));
		setTexturesBuffer(new LightBuffer(4 * 2));
		
		LightBuffer verticesBuffer = getVerticesBuffer();
		LightBuffer texturesBuffer = getTexturesBuffer();
		
		verticesBuffer.setData(0, GL.addRectVertexArrayf(0, 0, width, height, 0, null));
		texturesBuffer.setData(0, GL.addRectTextureArrayf(getTexture(), 0, null));
		
		setRenderable(true);
		
		powerUps = new boolean[PowerUp.getLength()];
		
//		bullets  = new ArrayList<Bullet>();
	}
	
	public void move(float dx, float dy)
	{
		if (powerUps[PowerUp.SUPER_SPEED])
		{
			dx *= 2;
			dy *= 2;
		}
		
		super.move(dx, dy);
		
		float x = getX();
		float y = getY();
		
		if (x < 0)
		{
			setLocation(0, y);
		}
		else if (x + getScaledWidth() > Frame.getWidth())
		{
			setLocation(Frame.getWidth() - getScaledWidth(), y);
		}
		
		if (y < 0)
		{
			setLocation(x, 0);
		}
		else if (y + getScaledHeight() > Frame.getHeight())
		{
			setLocation(x, Frame.getHeight() - getScaledHeight());
		}
	}
	
	public void shoot()
	{
		if (powerUps[PowerUp.RAPID_SHOOT])
		{
			Invasion.shotsPerSecond = 5;
		}
		else
		{
			Invasion.shotsPerSecond = 1;
		}
		
		Bullet bullet = null;
		
		synchronized (Invasion.entities)
		{
			bullet = new Bullet("res/images/bullets/bullet.png", this);
			
			float x = getX();
			float y = getY();
			
			if (powerUps[PowerUp.DOUBLE_FORCE])
			{
				Bullet bullet2 = bullet.clone();
				
				Point start1       = new Point(x, y + getScaledHeight() - bullet.getScaledHeight(), 0);
				Point destination1 = new Point(x, 512, 0);
				
				Point start2       = new Point(x + getScaledWidth() - bullet.getScaledWidth(), y + getScaledHeight() - bullet.getScaledHeight(), 0);
				Point destination2 = new Point(x + getScaledWidth() - bullet.getScaledWidth(), 512, 0);
				
				bullet.shoot(start1, destination1, this);
				bullet2.shoot(start2, destination2, this);
				
				Invasion.entities.add(bullet);
				Invasion.entities.add(bullet2);
			}
			else
			{
				Point start       = new Point(x + getScaledWidth() / 2 - bullet.getScaledWidth() / 2, y + getScaledHeight() - bullet.getScaledHeight(), 0);
				Point destination = new Point(x + getScaledWidth() / 2 - bullet.getScaledWidth() / 2, 512, 0);
				
				bullet.shoot(start, destination, this);
			
				Invasion.entities.add(bullet);
			}
		}
	}
	
	public boolean intersects(Bullet bullet)
	{
		float x = getX();
		float y = getY();
		
		return Intersects.rectangles((int)bullet.getX(), (int)bullet.getY(), (int)bullet.getScaledWidth(), (int)bullet.getScaledHeight(), (int)x, (int)y, (int)getScaledWidth(), (int)getScaledHeight());
	}
	
	public void onCollision(Entity entity)
	{
		if (entity instanceof Bullet)
		{
			if (((Bullet)entity).getShotFrom() != this)
			{
				Invasion.removeEntity(this);
			}
		}
	}
	
	public boolean hasAnyPowerUps()
	{
		for (boolean powerUp : powerUps)
		{
			if (powerUp)
			{
				return true;
			}
		}
		
		return false;
	}
	
	public boolean inFrontRow()
	{
		if (this instanceof Alien == false)
		{
			return false;
		}
		
		int row = 0;
		int col = 0;
		
		for (int i = 0; i < Invasion.aliens.length; i ++)
		{
			if (Invasion.aliens[i] == this)
			{
				row = i / Invasion.currentLevel.getCols();
				col = i % Invasion.currentLevel.getCols();
				
				break;
			}
		}
		
		for (int i = row + 1; i < Invasion.currentLevel.getRows(); i ++)
		{
			if (Invasion.aliens[col + i * Invasion.currentLevel.getCols()] != null)
			{
				return false;
			}
		}
		
		return true;
	}
	
	public void addPowerUp(int id)
	{
		powerUps[id] = true;
	}
	
	public void removePowerUp(int id)
	{
		powerUps[id] = false;
	}
}