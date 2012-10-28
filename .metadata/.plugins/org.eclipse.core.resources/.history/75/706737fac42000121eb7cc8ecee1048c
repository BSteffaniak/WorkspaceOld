package net.foxycorndog.nostalgia.actor;

import net.foxycorndog.jdoogl.GL;
import net.foxycorndog.jdoutil.VerticesBuffer;
import net.foxycorndog.nostalgia.actor.camera.Camera;
import net.foxycorndog.nostalgia.map.Map;

public class Actor
{
	private boolean sprinting;
	private boolean jumping, movingUp;
	private boolean cameraAttached;
	
	private int     perspective;
	
	private float   width, height, depth;
	private float   centerX, centerY, centerZ;
	private float   offsetX, offsetY, offsetZ;
	private float   startY;
	
	private Camera  camera, location;
	
	private Map     map;
	
	private VerticesBuffer verticesBuffer;
	
	public  static final int   THIRD = 3, FIRST = 1;
	
	private static final float TOLERANCE = 0.0001f;
	
	public Actor(float width, float height, float depth, float centerX, float centerY, float centerZ, Map map)
	{
		this.width     = width;
		this.height    = height;
		this.depth     = depth;
		
		this.centerX   = centerX;
		this.centerY   = centerY;
		this.centerZ   = centerZ;
		
		this.offsetX   = centerX;
		this.offsetY   = centerY;
		this.offsetZ   = centerZ;
		
		location       = new Camera();
		location.setActor(this);
		location.setCameraMode(Camera.XZ_ONLY);
		
		camera         = new Camera();
		camera.setActor(this);
		camera.moveDirection(centerX, centerY, centerZ);
		camera.setCameraMode(Camera.XZ_ONLY);
		
		this.map       = map;
		
		verticesBuffer = new VerticesBuffer(3 * 4 * 6, 3);
		
		verticesBuffer.addData(GL.addCubeVertexArrayf(0, 0, 0, width, height, depth, 0, null));
		
		perspective    = FIRST;
	}
	
	public boolean collided(Map map)
	{
		if (map.collided(this))
		{
			return true;
		}
		
		float cubes[] = map.getCubes();
		
		for (int i = 0; i < cubes.length; i += 6)
		{
			float minX1 = location.getX();
			float minY1 = location.getY();
			float minZ1 = location.getZ();
			
			float maxX1 = location.getX() + width;
			float maxY1 = location.getY() + height;
			float maxZ1 = location.getZ() + depth;
			

			float minX2 = cubes[i];
			float minY2 = cubes[i + 1];
			float minZ2 = cubes[i + 2];
			
			float maxX2 = cubes[i + 3];//12
			float maxY2 = cubes[i + 4];//4
			float maxZ2 = cubes[i + 5];//20
			
			if ((maxX1 >= minX2 && minX1 <= maxX2) && (maxY1 >= minY2 && minY1 <= maxY2) && (maxZ1 >= minZ2 && minZ1 <= maxZ2))
			{
				return true;
			}
		}
		
		return false;
	}
	
	public boolean move(int direction, float distance)
	{
		if (direction == Camera.FORWARD)
		{
			return move(0, 0, -distance);
		}
		else if (direction == Camera.BACKWARD)
		{
			return move(0, 0, distance);
		}
		else if (direction == Camera.LEFT)
		{
			return move(-distance, 0, 0);
		}
		else if (direction == Camera.RIGHT)
		{
			return move(distance, 0, 0);
		}
		else if (direction == Camera.UP)
		{
			return move(0, distance, 0);
		}
		else if (direction == Camera.DOWN)
		{
			return move(0, -distance, 0);
		}
		
		return false;
	}
	
	public boolean move(float dx, float dy, float dz)
	{
		dx = sprinting ? dx * 1.6f : dx;
		dy = sprinting ? dy * 1.6f : dy;
		dz = sprinting ? dz * 1.6f : dz;
		
		camera.moveDirection(dx, dy, dz);
		location.moveDirection(dx, dy, dz);
		
		if (collided(map))
		{
			camera.moveDirection(-dx, -dy, -dz);
			location.moveDirection(-dx, -dy, -dz);
			
			return false;
		}
		
		return true;
	}
	
	public void jump()
	{
		if (!jumping)
		{
			jumping  = true;
			
			movingUp = true;
			
			startY   = location.getY();
		}
	}
	
	public void update()
	{
		if (jumping)
		{
			if (location.getY() < startY + 2 && movingUp)
			{
				if (!move(0, 0.3f, 0))
				{
					jumping = false;
				}
				
				if (location.getY() >= startY + 2)
				{
					movingUp = false;
				}
			}
			else if (!movingUp && Math.abs(location.getY() - startY) > TOLERANCE)
			{
				
			}
			else
			{
				jumping = false;
			}
		}
	}
	
	public void attachCamera(Camera camera)
	{
		camera.setLocation(this.camera.getX(), this.camera.getY(), this.camera.getZ());
		
		this.camera = camera;
		
		camera.setActor(this);
		
		cameraAttached = true;
	}
	
	public void detachCamera()
	{
		camera.setActor(null);
		
		camera = (Camera)camera.clone();
//		
//		float x     = camera.getX();
//		float y     = camera.getY();
//		float z     = camera.getZ();
//		
//		float pitch = camera.getPitch();
//		float yaw   = camera.getYaw();
//		float roll  = camera.getRoll();
//		
//		camera      = null;
//		
//		camera      = new Camera();
//		camera.setLocation(x, y, z);
//		camera.rotate(pitch, yaw, roll);
		
		camera.setActor(this);
		
		cameraAttached = false;
	}
	
	public void lookThrough()
	{
		GL.translatef(-getOffsetX() + getCenterX(), -getOffsetY() + getCenterY(), -getOffsetZ() + getCenterZ());
		
		camera.lookThrough();

		GL.translatef(getOffsetX() - getCenterX(), getOffsetY() - getCenterY(), getOffsetZ() - getCenterZ());
	}
	
	public void pitch(float amount)
	{
		camera.pitch(amount);
		location.pitch(amount);
	}
	
	public void yaw(float amount)
	{
		camera.yaw(amount);
		location.yaw(amount);
	}
	
	public void setSprinting(boolean sprinting)
	{
		if (this.sprinting != sprinting)
		{
			this.sprinting = sprinting;
		}
	}
	
	public float getX()
	{
		return location.getX();
	}
	
	public float getY()
	{
		return location.getY();
	}
	
	public float getZ()
	{
		return location.getZ();
	}
	
	public float getOffsetX()
	{
		return offsetX;
	}
	
	public float getOffsetY()
	{
		return offsetY;
	}
	
	public float getOffsetZ()
	{
		return offsetZ;
	}
	
	public float getCenterX()
	{
		return centerX;
	}
	
	public float getCenterY()
	{
		return centerY;
	}
	
	public float getCenterZ()
	{
		return centerZ;
	}
	
	public void moveCenter(float dx, float dy, float dz)
	{
		centerX += dx;
		centerY += dy;
		centerZ += dz;
	}
	
	public void moveOffest(float dx, float dy, float dz)
	{
		offsetX += dx;
		offsetY += dy;
		offsetZ += dz;
	}
	
	public int getPerspective()
	{
		return perspective;
	}
	
	public void setPerspective(int perspective)
	{
		if (this.perspective == perspective)
		{
			return;
		}
		
		if (perspective == THIRD)
		{
			moveOffest(0, 2, 6);
			
			getCamera().move(0, 2, 6);
		}
		else if (perspective == FIRST)
		{
			moveOffest(0, -2, -6);

			getCamera().move(0, -2, -6);
		}
		
		this.perspective = perspective;
	}
	
	public boolean cameraAttached()
	{
		return cameraAttached;
	}
	
	public Map getMap()
	{
		return map;
	}
	
	public VerticesBuffer getVerticesBuffer()
	{
		return verticesBuffer;
	}
	
	public Camera getCamera()
	{
		return camera;
	}
	
	public String toString()
	{
		float location[] = this.location.getLocation();
		
		return "[ " + location[0] + ", " + location[1] + ", " + location[2] + " ]";
	}
	
	public void genIndices()
	{
		verticesBuffer.genIndices(GL.QUADS);
	}
	
	public void destroyIndices()
	{
		verticesBuffer.destroyIndices();
	}
	
	public float[] getVertices()
	{
		return verticesBuffer.getData();
	}
}
