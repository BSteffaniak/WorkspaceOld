package net.foxycorndog.invasion.entities;

import net.foxycorndog.invasion.Invasion;
import net.foxycorndog.jdoogl.geometry.Point;

public class Alien extends Ship
{
	public Alien()
	{
		super("res/images/ships/alien.png");
	}
	
	public void onCollision(Entity entity)
	{
		super.onCollision(entity);
		
		if (entity instanceof Bullet && ((Bullet)entity).getShotFrom() != this)
		{
			Invasion.addScore(10);
		}
	}
	
	public void shoot()
	{
		Bullet bullet = null;
		
		synchronized (Invasion.entities)
		{
			bullet = new Bullet("res/images/bullets/alienBullet.png", this);
			
			float x = getX();
			float y = getY();
		
			Point start       = new Point(x + getScaledWidth() / 2 - bullet.getScaledWidth() / 2, y, 0);
			Point destination = new Point(x + getScaledWidth() / 2 - bullet.getScaledWidth() / 2, 0 - bullet.getScaledHeight(), 0);
			
			bullet.shoot(start, destination, this);
		
			Invasion.entities.add(bullet);
		}
	}
}