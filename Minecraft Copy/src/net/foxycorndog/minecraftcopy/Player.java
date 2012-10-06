package net.foxycorndog.minecraftcopy;

import static org.lwjgl.opengl.GL11.*;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector3f;

import net.foxycorndog.presto3d.components.Camera;
import net.foxycorndog.presto3d.graphics.PrestoGL3D;
import net.foxycorndog.presto3d.util.volume.Volume3f;

public class Player
{
	private float  speed;

	private Vector3f cameraViewLocation;
	
	private Volume3f volume;
	
	private Camera camera;
	
	private MinecraftCopy mc;
	
	public static final int FORWARD = 0, BACKWARD = 1, LEFT = 2, RIGHT = 3, UP = 4, DOWN = 5;
	
	public Player(MinecraftCopy mc)
	{
		camera = new Camera();
		
		init(mc);
	}
	
	public Player(float x, float y, float z, MinecraftCopy mc)
	{
		camera = new Camera(-x, -y, -z);
		
		init(mc);
	}
	
	private void init(MinecraftCopy mc)
	{
		volume = new Volume3f();
		
		speed = 1;
		
		this.mc = mc;
		
		cameraViewLocation = new Vector3f();
	}
	
	public void lookThrough()
	{
		byte r = 200 / 2;
		byte g = 00 / 2;
		byte b = 00 / 2;
		byte a = 0 / 2;
		
		glBegin(GL_QUADS);
		
		glColor4b((byte)(r), (byte)g, (byte)b, (byte)a);

		glVertex3f(-0.004f, -0.02f, -1);
		glVertex3f(-0.004f,  0.02f, -1);
		glVertex3f( 0.004f,  0.02f, -1);
		glVertex3f( 0.004f, -0.02f, -1);
		
		glVertex3f(-0.02f, -0.004f, -1);
		glVertex3f(-0.02f,  0.004f, -1);
		glVertex3f( 0.02f,  0.004f, -1);
		glVertex3f( 0.02f, -0.004f, -1);
		
		glEnd();
		
		glColor3f(1, 1, 1);
		
		camera.lookThrough();
		
		glColor4b((byte)(100 / 2), (byte)(100 / 2), (byte)(100 / 2), (byte)(255 / 2));
		
		PrestoGL3D.drawCube(getX(), getY(), getZ(), volume.width);
		
		glColor4b((byte)(0 / 2), (byte)(255 / 2), (byte)(0 / 2), (byte)(255 / 2));
		
		glColor3f(1, 1, 1);
	}
	
	public void move(int direction)
	{
		if (direction == FORWARD)
		{
			camera.move(Camera.FORWARD, speed);
			
			if (mc.getChunk(0).collided(this))
			{
				camera.move(Camera.FORWARD, -speed);
			}
		}
		else if (direction == BACKWARD)
		{
			camera.move(Camera.BACKWARD, speed);
			
			if (mc.getChunk(0).collided(this))
			{
				camera.move(Camera.BACKWARD, -speed);
			}
		}
		else if (direction == LEFT)
		{
			camera.move(Camera.LEFT, speed);
			
			if (mc.getChunk(0).collided(this))
			{
				camera.move(Camera.LEFT, -speed);
			}
		}
		else if (direction == RIGHT)
		{
			camera.move(Camera.RIGHT, speed);
			
			if (mc.getChunk(0).collided(this))
			{
				camera.move(Camera.RIGHT, -speed);
			}
		}
		else if (direction == UP)
		{
			camera.move(Camera.UP, speed);
			
			if (mc.getChunk(0).collided(this))
			{
				camera.move(Camera.UP, -speed);
			}
		}
		else if (direction == DOWN)
		{
			camera.move(Camera.DOWN, speed);
			
			if (mc.getChunk(0).collided(this))
			{
				camera.move(Camera.DOWN, -speed);
			}
		}
	}
	
	public void move(int direction, float speed)
	{
		if (direction == FORWARD)
		{
			camera.move(Camera.FORWARD, speed);
			
			if (mc.getChunk(0).collided(this))
			{
				camera.move(Camera.FORWARD, -speed);
			}
		}
		else if (direction == BACKWARD)
		{
			camera.move(Camera.BACKWARD, speed);
			
			if (mc.getChunk(0).collided(this))
			{
				camera.move(Camera.BACKWARD, -speed);
			}
		}
		else if (direction == LEFT)
		{
			camera.move(Camera.LEFT, speed);
			
			if (mc.getChunk(0).collided(this))
			{
				camera.move(Camera.LEFT, -speed);
			}
		}
		else if (direction == RIGHT)
		{
			camera.move(Camera.RIGHT, speed);
			
			if (mc.getChunk(0).collided(this))
			{
				camera.move(Camera.RIGHT, -speed);
			}
		}
		else if (direction == UP)
		{
			camera.move(Camera.UP, speed);
			
			if (mc.getChunk(0).collided(this))
			{
				camera.move(Camera.UP, -speed);
			}
		}
		else if (direction == DOWN)
		{
			camera.move(Camera.DOWN, speed);
			
			if (mc.getChunk(0).collided(this))
			{
				camera.move(Camera.DOWN, -speed);
			}
		}
	}
	
	/**
	 * increment the camera's current yaw rotation
	 * 
	 * @param amount amount of degrees
	 */
	public void yaw(float amount)
	{
		//increment the yaw by the amount param
		camera.yaw(amount);
	}
	 
	/**
	 * increment the camera's current yaw rotation
	 * 
	 * @param amount amount of degrees
	 */
	public void pitch(float amount)
	{
		camera.pitch(amount);
	}
	
	public void setCameraViewLocationX(float cameraViewLocationX)
	{
		this.cameraViewLocation.x = cameraViewLocationX;
	}
	
	public void setCameraViewLocations(float x, float y, float z)
	{
		this.cameraViewLocation.x = x;
		this.cameraViewLocation.y = y;
		this.cameraViewLocation.z = z;
	}
	
	public float getCameraViewLocationX()
	{
		return this.cameraViewLocation.x;
	}
	
	public void setCameraViewLocationY(float cameraViewLocationY)
	{
		this.cameraViewLocation.y = cameraViewLocationY;
	}
	
	public float getCameraViewLocationY()
	{
		return this.cameraViewLocation.y;
	}
	
	public void setCameraViewLocationZ(float cameraViewLocationZ)
	{
		this.cameraViewLocation.z = cameraViewLocationZ;
	}
	
	public float getCameraViewLocationZ()
	{
		return this.cameraViewLocation.z;
	}
	
	public void setSpeed(float speed)
	{
		this.speed = speed;
	}
	
	public float getWidth()
	{
		return volume.width;
	}
	
	public void setWidth(float width)
	{
		this.volume.width = width;
	}
	
	public float getHeight()
	{
		return volume.height;
	}
	
	public void setHeight(float height)
	{
		this.volume.height = height;
	}
	
	public float getLength()
	{
		return volume.length;
	}
	
	public void setLength(float length)
	{
		this.volume.length = length;
	}
	
	public void setVolume(Volume3f volume)
	{
		this.volume = volume;
	}
	
	public Volume3f getVolume()
	{
		return volume;
	}
	
	public float getSpeed()
	{
		return speed;
	}
	
	public Vector3f getLocation()
	{
		Vector3f loc = new Vector3f(camera.getLocation());
		
		loc.x += (this.cameraViewLocation.x);
		loc.y += (this.cameraViewLocation.y);
		loc.z += (this.cameraViewLocation.z);
		
		return loc;
	}
	
	public float getX()
	{
		return -(camera.getLocation().x + (this.cameraViewLocation.x));
	}
	
	public float getY()
	{
		return -(camera.getLocation().y + (this.cameraViewLocation.y));
	}
	
	public float getZ()
	{
		return -(camera.getLocation().z + (this.cameraViewLocation.z));
	}
	
	public Camera getCamera()
	{
		return camera;
	}
}