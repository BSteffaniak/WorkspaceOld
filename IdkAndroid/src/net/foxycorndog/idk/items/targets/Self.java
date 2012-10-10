package net.foxycorndog.idk.items.targets;

import net.foxycorndog.idk.animatedobject.actors.Actor;
import net.foxycorndog.idk.items.targets.Target;

public class Self extends Target
{
	private Actor actor;
	
	public void setActor(Actor actor)
	{
		this.actor = actor;
	}
	
	public Actor getActor()
	{
		return actor;
	}
	
	public String toString()
	{
		return "Self";
	}
}