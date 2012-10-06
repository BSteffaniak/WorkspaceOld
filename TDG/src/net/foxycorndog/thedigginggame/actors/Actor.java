package net.foxycorndog.thedigginggame.actors;

import java.awt.Point;
import java.util.ArrayList;

import static net.foxycorndog.thedigginggame.TheDiggingGame.*;

import net.foxycorndog.thedigginggame.TheDiggingGame;
import net.foxycorndog.thedigginggame.map.Chunk;
import net.foxycorndog.thedigginggame.map.Map;

public class Actor
{
	private float   screenRelativeX, screenRelativeY, originRelativeX, originRelativeY;
	private float   scaleFactor;
	private float   moveSpeed, jumpSpeed;
	private float   scale;
	
	private int     width, height;
	private int     startJumpY;
	private int     facing;
	
	private boolean jumping, airborn, alreadyJumped;
	
	private Map     map;
	
	private Chunk[] touchableChunks;
	
	public static final int UP    = 0, DOWN = 1, LEFT = 2, RIGHT = 3;
	public static final int FRONT = 0, BACK = 1;
	
	public Actor(float x, float y, int width, int height, float moveSpeed, float jumpSpeed, float scale, Map map)
	{
		this.screenRelativeX = x;
		this.screenRelativeY = y;
		
		this.width           = width;
		this.height          = height;
		
		this.moveSpeed       = moveSpeed;
		this.jumpSpeed       = jumpSpeed;
		
		this.scale           = scale;
		
		this.map             = map;
		
		this.width           = getWidthFromScale (scale);
		this.height          = getHeightFromScale(scale);
		
		touchableChunks      = new Chunk[] { map.getChunk(0, 0) };
		
		this.scaleFactor     = scale / 16;
		
		this.moveSpeed      *= scaleFactor / 2f;
		this.jumpSpeed      *= scaleFactor / 2f;
	}
	
	public void calculateTouchableChunks()
	{
	}
	
	public float getScreenRelativeX()
	{
		return screenRelativeX;
	}
	
	public void setScreenRelativeX(float x)
	{
		this.screenRelativeX = x;
	}
	
	public float getScreenRelativeY()
	{
		return screenRelativeY;
	}
	
	public void setScreenRelativeY(float y)
	{
		this.screenRelativeY = y;
	}
	
	public float getOriginRelativeX()
	{
		return originRelativeX;
	}
	
	public void setOriginRelativeX(float x)
	{
		this.originRelativeX = x;
	}
	
	public float getOriginRelativeY()
	{
		return originRelativeY;
	}
	
	public void setOriginRelativeY(float y)
	{
		this.originRelativeY = y;
	}
	
	public float[] getLocation()
	{
		return new float[] { screenRelativeX, screenRelativeY };
	}
	
	public void setLocation(float location[])
	{
		try
		{
			this.screenRelativeX = location[0];
			this.screenRelativeY = location[1];
		}
		catch(IndexOutOfBoundsException ex)
		{
			System.err.println("Float array is too small!");
			ex.printStackTrace();
			System.exit(1);
		}
	}
	
	public boolean move(int direction)
	{
		if (direction == UP)
		{
			return move(0f, jumpSpeed);
		}
		else if (direction == DOWN)
		{
			return move(0f, -gravity);
		}
		else if (direction == LEFT)
		{
			return move(-moveSpeed, 0f);
		}
		else if (direction == RIGHT)
		{
			return move(moveSpeed, 0f);
		}
		
		return move(0f, 0f);
	}
	
	public boolean move(int direction, float amount)
	{
		if (direction == UP)
		{
			return move(0f, amount);
		}
		else if (direction == DOWN)
		{
			return move(0f, -amount);
		}
		else if (direction == LEFT)
		{
			return move(-amount, 0f);
		}
		else if (direction == RIGHT)
		{
			return move(amount, 0f);
		}
		
		return move(0f, 0f);
	}
	
	public boolean move(float dx, float dy)
	{
		screenRelativeX += dx;
		screenRelativeY += dy;
		
		originRelativeX += dx;
		originRelativeY += dy;
		
		calculateTouchableChunks();
		
		return dx != 0 && dy != 0;
	}
	
	public int getWidth()
	{
		return width;
	}
	
	public void setWidth(int width)
	{
		this.width = width;
	}
	
	public int getHeight()
	{
		return height;
	}
	
	public void setHeight(int height)
	{
		this.height = height;
	}
	
	public int[] getSize()
	{
		return new int[] { width, height };
	}
	
	public void setSize(int location[])
	{
		try
		{
			this.width = location[0];
			this.height = location[1];
		}
		catch(IndexOutOfBoundsException ex)
		{
			System.err.println("Float array is too small!");
			ex.printStackTrace();
			System.exit(1);
		}
	}
	
	private int getWidthFromScale(float scale)
	{
		return (int)((scale / 16f) * width);
	}
	
	private int getHeightFromScale(float scale)
	{
		return (int)((scale / 16f) * height);
	}
	
	public float getScaleFactor()
	{
		return scaleFactor;
	}
	
	public Map getMap()
	{
		return map;
	}
	
	public Chunk[] getTouchableChunks()
	{
		return touchableChunks;
	}
	
	public void setStartJumpY(int startY)
	{
		startJumpY = startY;
	}
	
	public int getStartJumpY()
	{
		return startJumpY;
	}
	
	public void setJumping(boolean jumping)
	{
		this.jumping = jumping;
	}
	
	public boolean getJumping()
	{
		return jumping;
	}
	
	public boolean getAirborn()
	{
		return airborn;
	}
	
	public void setAirborn(boolean airborn)
	{
		this.airborn = airborn;
	}
	
	public boolean getAlreadyJumped()
	{
		return alreadyJumped;
	}
	
	public void setAlreadyJumped(boolean alreadyJumped)
	{
		this.alreadyJumped = alreadyJumped;
	}
	
	public float getJumpSpeed()
	{
		return jumpSpeed;
	}
	
	public float getMoveSpeed()
	{
		return moveSpeed;
	}
	
	public float getSpeed(int direction)
	{
		if (direction == UP)
		{
			return jumpSpeed;
		}
		else if (direction == DOWN)
		{
			return -gravity;
		}
		else if (direction == LEFT)
		{
			return -moveSpeed;
		}
		else if (direction == RIGHT)
		{
			return moveSpeed;
		}
		
		return 0;
	}
	
	public int getFacing()
	{
		return facing;
	}
	
	public void setFacing(int direction)
	{
		this.facing = direction;
	}
}