package net.foxycorndog.invasion.entities;

import net.foxycorndog.invasion.Invasion;

public class Player extends Ship
{
	public Player()
	{
		super("res/images/ships/ship.png");
	}
	
	public void onCollision(Entity entity)
	{
		if (entity instanceof Bullet)
		{
			if (((Bullet)entity).getShotFrom() != this)
			{
				Invasion.startGame();
			}
		}
	}
}