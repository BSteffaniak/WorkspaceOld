package net.foxycorndog.presto3d.components;

import static org.lwjgl.opengl.GL11.glRotatef;
import static org.lwjgl.opengl.GL11.glTranslatef;

import org.lwjgl.util.vector.Vector3f;
 
// First Person Camera Controller
public class Camera
{
	// 3d vector to store the camera's position in
	private Vector3f location;
	
	// the rotation around the Y axis of the camera
	private float	yaw;
	
	// the rotation around the X axis of the camera
	private float	pitch;
	
	public static final int FORWARD = 0, BACKWARD = 1, LEFT = 2, RIGHT = 3, UP = 4, DOWN = 5;
	
	// Default constructor
	public Camera()
	{
		// instantiate position Vector3f to the x y z params.
		location = new Vector3f(0, 0, 0);
	}
	
	// Constructor that takes the starting x, y, z location of the camera
	public Camera(float x, float y, float z)
	{
		// instantiate position Vector3f to the x y z params.
		location = new Vector3f(x, y, z);
	}
	
	// increment the camera's current yaw rotation
	public void yaw(float amount)
	{
		//increment the yaw by the amount param
		yaw += amount;
	}
	 
	// increment the camera's current yaw rotation
	public void pitch(float amount)
	{
		// increment the pitch by the amount param
		pitch += amount;
	}
	
	public void move(int direction, float distance)
	{
		if (direction == FORWARD)
		{
			location.x -= distance * (float)Math.sin(Math.toRadians(yaw));
			location.z += distance * (float)Math.cos(Math.toRadians(yaw));
		}
		else if (direction == BACKWARD)
		{
			location.x += distance * (float)Math.sin(Math.toRadians(yaw));
			location.z -= distance * (float)Math.cos(Math.toRadians(yaw));
		}
		else if (direction == LEFT)
		{
			location.x -= distance * (float)Math.sin(Math.toRadians(yaw - 90));
			location.z += distance * (float)Math.cos(Math.toRadians(yaw - 90));
		}
		else if (direction == RIGHT)
		{
			location.x -= distance * (float)Math.sin(Math.toRadians(yaw + 90));
			location.z += distance * (float)Math.cos(Math.toRadians(yaw + 90));
		}
		else if (direction == UP)
		{
			location.y -= distance;
		}
		else if (direction == DOWN)
		{
			location.y += distance;
		}
	}
	
	// translates and rotate the matrix so that it looks through the camera
	// this dose basic what gluLookAt() does
	public void lookThrough()
	{
		// rotate the pitch around the X axis
		glRotatef(pitch, 1.0f, 0.0f, 0.0f);
		// rotate the yaw around the Y axis
		glRotatef(yaw, 0.0f, 1.0f, 0.0f);
		// translate to the position vector's location
		glTranslatef(location.x, location.y, location.z);
	}
	
	public Vector3f getLocation()
	{
		return location;
	}
}