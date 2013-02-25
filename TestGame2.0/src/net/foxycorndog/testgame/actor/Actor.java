package net.foxycorndog.testgame.actor;

import net.foxycorndog.jfoxylib.util.Camera;

public class Actor
{
	private Camera camera;
	
	public Actor()
	{
		camera = new Camera();
		camera.setMaxPitch(90);
		camera.setMinPitch(-90);
	}
	
	public void setLocation(float x, float y, float z)
	{
		camera.setLocation(x, y, z);
	}
	
	public void rotate(float pitch, float yaw, float roll)
	{
		camera.rotate(pitch, yaw, roll);
	}
	
	public void moveForeward(float scale)
	{
		camera.moveDirection(0, 0, -1 * scale);
	}
	
	public void moveBackward(float scale)
	{
		camera.moveDirection(0, 0, 1 * scale);
	}
	
	public void moveLeft(float scale)
	{
		camera.moveDirection(-1 * scale, 0, 0);
	}
	
	public void moveRight(float scale)
	{
		camera.moveDirection(1 * scale, 0, 0);
	}
	
	public void moveUp(float scale)
	{
		camera.move(0, 1 * scale, 0);
	}
	
	public void moveDown(float scale)
	{
		camera.move(0, -1 * scale, 0);
	}
	
	public void lookThrough()
	{
		camera.lookThrough();
	}
	
	public float getX()
	{
		return camera.getX();
	}
	
	public float getY()
	{
		return camera.getY();
	}
	
	public float getZ()
	{
		return camera.getZ();
	}
}