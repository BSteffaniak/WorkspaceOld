package net.foxycorndog.nostalgia.actor.camera;

import net.foxycorndog.nostalgia.actor.Actor;

public class Camera extends net.foxycorndog.jdoogl.camera.Camera
{
	private Actor actor;
	
	public void setActor(Actor actor)
	{
		if (this.actor != null && actor != null)
		{
			this.actor.detachCamera();
		}
		
		this.actor = actor;
		
		if (actor.getCamera() != this)
		{
			actor.attachCamera(this);
		}
	}
}
