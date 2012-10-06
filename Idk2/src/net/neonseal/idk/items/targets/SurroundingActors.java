package net.neonseal.idk.items.targets;

import net.neonseal.idk.animatedobject.actors.Actor;
import net.neonseal.idk.items.targets.Target;

public class SurroundingActors extends Target
{
	private int radius;
	
	private Actor actor;
	
	public SurroundingActors(int radius)
	{
		this.radius = radius;
	}
	
	public void setActor(Actor actor)
	{
		this.actor = actor;
	}
	
	public Actor getActor()
	{
		return actor;
	}
	
	public int getRadius()
	{
		return radius;
	}
	
	public String toString()
	{
		return "Surrounding actors";
	}
}