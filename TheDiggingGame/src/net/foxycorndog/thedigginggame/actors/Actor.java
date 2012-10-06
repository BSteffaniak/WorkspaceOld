package net.foxycorndog.thedigginggame.actors;

import java.util.Timer;
import java.util.TimerTask;

import net.foxycorndog.presto2d.graphics.PixelPanel;
import net.foxycorndog.thedigginggame.Display;
import net.foxycorndog.thedigginggame.TheDiggingGame;
import net.foxycorndog.thedigginggame.map.Map;

public abstract class Actor
{
	private int     width, height;
	private int     facing, oldFacing;
	private int     scale;
	private int     jumpHeight;
	private float   x, y, fx, fy;
	private float   oldRenderX, oldRenderY;
	private boolean movingLeft, movingRight, movingUp, movingDown;
	private boolean sprinting;
	private boolean rendered;
	
	private boolean jumping;
	private boolean jumpingUp;
	private boolean onGround;
	private int     jumpStartY;
	
	private TheDiggingGame tdg;
	
	public static final int UP = 2, DOWN = 3;
	
	public static final int FRONT = 0, TOP = 1, BOTTOM = 2,
			LEFT = 3, RIGHT = 4, BACK = 5;
	
	public Actor(int x, int y, int width, int height, int jumpHeight, int scale, TheDiggingGame tdg)
	{
		this.scale = scale;
		
		this.tdg = tdg;
		
		this.x = x * scale;
		this.y = y * scale;
		
		this.fx = x * scale;
		this.fy = y * scale;
		
		this.width  = width * scale;
		this.height = height * scale;
		
		this.jumpHeight = jumpHeight;
		
		jumping = false;
	}
	
	public void jump()
	{
		if (onGround && !jumping)
		{
			jumpingUp  = true;
			jumping    = true;
			jumpStartY = (int)getY();
		}
		
		if (jumpingUp)
		{
			move(0, -1);
			
			if (getY() <= jumpStartY - jumpHeight)
			{
				jumpingUp = false;
			}
		}
		else
		{
			move(0, 1);
			
			if (getY() >= jumpStartY)
			{
				jumping = false;
			}
		}
	}
	
	public void move()
	{
		if (movingLeft)
		{
			setFacing(LEFT);
			move(-1, 0);
		}
		else if (movingRight)
		{
			setFacing(RIGHT);
			move(1, 0);
		}
		else if (movingUp)
		{
			move(0, -1);
		}
		else if (movingDown)
		{
			move(0, 1);
		}
	}
	
	public void setMoving(int direction, boolean b)
	{
		if (direction == LEFT)
		{
			movingLeft = b;
		}
		else if (direction == RIGHT)
		{
			movingRight = b;
		}
		else if (direction == UP)
		{
			movingUp = b;
		}
		else if (direction == DOWN)
		{
			movingDown = b;
		}
	}
	
	public void setFacing(int f)
	{
		facing = f;
	}
	
	public int getFacing()
	{
		return facing;
	}
	
	public void setLocation(float x, float y)
	{
		if (x == this.x && y == this.y) return;
		
		this.x = x;
		this.y = y;
		
		rendered = false;
	}
	
	public void move(float dx, float dy)
	{
		if (dx == 0 && dy == 0) return;
		
		dx = calculateXOffset(dx);
		dy = calculateYOffset(dy);
		
		if (!tdg.getMap().collided(this, dx, dy))
		{
			x += dx;
			y += dy;
		}
		
		rendered = false;
	}
	
	public abstract void clear(PixelPanel pixels);
	
	public abstract void render(PixelPanel pixels);
	
	public float calculateXOffset(float dx)
	{
		return dx * (sprinting ? 2 : 1);
	}
	
	public float calculateYOffset(float dy)
	{
		return dy;
	}
	
	public float getX()
	{
		return x;
	}
	
	public float getY()
	{
		return y;
	}
	
	public float getFx()
	{
		return fx;
	}
	
	public float getFy()
	{
		return fy;
	}
	
	public void setFx(float f)
	{
		this.fx = f;
	}
	
	public void setFy(float f)
	{
		this.fy = f;
	}
	
	public int getWidth()
	{
		return width;
	}
	
	public int getHeight()
	{
		return height;
	}
	
	public void setOldFacing(int o)
	{
		oldFacing = o;
	}
	
	public int getOldFacing()
	{
		return oldFacing;
	}
	
	public TheDiggingGame getTdg()
	{
		return tdg;
	}
	
	public int getScale()
	{
		return scale;
	}
	
	public boolean getJumping()
	{
		return jumping;
	}
	
	public boolean getJumpingUp()
	{
		return jumpingUp;
	}
	
	public void setJumping(boolean j)
	{
		jumping = j;
	}
	
	public boolean getOnGround()
	{
		return onGround;
	}
	
	public void setOnGround(boolean o)
	{
		onGround = o;
	}
	
	public float getOriginRelativeX()
	{
		return x - tdg.getMap().getX();
	}
	
	public float getOriginRelativeY()
	{
		return y - tdg.getMap().getY();
	}
	
	public boolean getSprinting()
	{
		return sprinting;
	}
	
	public void setSprinting(boolean s)
	{
		this.sprinting = s;
	}
	
	public void setRendered(boolean r)
	{
		rendered = r;
	}
	
	public boolean getRendered()
	{
		return rendered;
	}
	
	public void setOldRenderX(float o)
	{
		oldRenderX = o;
	}
	
	public float getOldRenderX()
	{
		return oldRenderX;
	}
	
	public void setOldRenderY(float o)
	{
		oldRenderY = o;
	}
	
	public float getOldRenderY()
	{
		return oldRenderY;
	}
}