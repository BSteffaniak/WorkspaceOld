package net.foxycorndog.p1xeland.actors;

import static net.foxycorndog.p1xeland.actors.Actor.Direction.*;
import net.foxycorndog.jdoogl.GL;
import net.foxycorndog.jdoogl.components.Frame;
import net.foxycorndog.jdoogl.image.imagemap.SpriteSheet;
import net.foxycorndog.jdoogl.util.LightBuffer;
import net.foxycorndog.p1xeland.map.Map;

public abstract class Actor
{
	private boolean       onGround;
	private boolean       jumping;
	private boolean       sprinting, startedSprinting;
	
	private int           width, height;
	private int           jumpHeight;
	
	private float         x, y, screenX, screenY;
	private float         speed;
	private float         startY;
	
	private Direction     facing;
	
	private Map           map;
	
	private static final int   RECT_SIZE = 4 * 2;
	
	public static enum Direction
	{
		LEFT, RIGHT, UP, DOWN,
		FRONT, BACK;
		
		public static Direction getOpposite(Direction direction)
		{
			if (direction == LEFT)
			{
				return RIGHT;
			}
			else if (direction == RIGHT)
			{
				return LEFT;
			}
			else if (direction == UP)
			{
				return DOWN;
			}
			else if (direction == DOWN)
			{
				return UP;
			}
			else if (direction == FRONT)
			{
				return BACK;
			}
			else if (direction == BACK)
			{
				return FRONT;
			}
			
			return null;
		}
		
		public static int getIndex(Direction direction)
		{
			if (direction == LEFT)
			{
				return 2;
			}
			else if (direction == RIGHT)
			{
				return 3;
			}
			else if (direction == UP)
			{
				return 0;
			}
			else if (direction == DOWN)
			{
				return 1;
			}
			else if (direction == FRONT)
			{
				return 0;
			}
			else if (direction == BACK)
			{
				return 1;
			}
			
			return 0;
		}
	}
	
	public Actor(float x, float y, int width, int height, int jumpHeight, float speed, Map map)
	{
		this.x          = x;
		this.y          = y;
		this.width      = width;
		this.height     = height;
		
		this.jumpHeight = jumpHeight;
		
		this.speed      = speed;
		
		this.facing     = FRONT;
		
		this.map        = map;
		
		init();
		
		getVerticesBuffer().setData(0, getVertices());
		getTexturesBuffer().setData(0, getTextures());
		
		refreshActor();
	}
	
	public void refreshActor()
	{
		getVerticesBuffer().refreshData();
		getTexturesBuffer().refreshData();
	}
	
	public Map getMap()
	{
		return map;
	}
	
	public Direction getFacing()
	{
		return facing;
	}
	
	public int getWidth()
	{
		return width;
	}
	
	public int getHeight()
	{
		return height;
	}
	
	public float getScreenX()
	{
		return screenX;
	}
	
	public void setScreenX(float screenX)
	{
		this.screenX = screenX;
	}
	
	public float getScreenY()
	{
		return screenY;
	}
	
	public void setScreenY(float screenY)
	{
		this.screenY = screenY;
	}
	
	public float getX()
	{
		return x;
	}
	
	public float getY()
	{
		return y;
	}
	
	public void setLocation(float x, float y)
	{
		this.x = x;
		this.y = y;
		
		this.screenX = x;
		this.screenY = y;
	}
	
	public boolean isOnGround()
	{
		return onGround;
	}
	
	public void setOnGround(boolean onGround)
	{
		this.onGround = onGround;
	}
	
	public boolean isJumping()
	{
		return jumping;
	}
	
	public void setJumping(boolean jumping)
	{
		this.jumping = jumping;
	}
	
	public boolean isSprinting()
	{
		return sprinting;
	}
	
	public void setSprinting(boolean sprinting)
	{
		this.sprinting = sprinting;
	}
	
	public boolean move(float dx, float dy)
	{
		if (dx != 0 && dy != 0)
		{
			move(dx, 0);
			move(0, dy);
		}
		
		if (sprinting)
		{
			if (!startedSprinting)
			{
				startedSprinting = true;
				
				move(dx * 0.25f, dy * 0.f);
				move(dx * 0.25f, dy * 0.f);
				
				startedSprinting = false;
			}
		}
		
		this.x += dx;
		this.y += dy;
		
		this.screenX += dx;
		this.screenY += dy;
		
		if (dx > 0)
		{
			facing = RIGHT;
		}
		else if (dx < 0)
		{
			facing = LEFT;
		}
		else
		{
			facing = FRONT;
		}
		
		boolean moved = true;
		
		if (getMap().collided(this))
		{
			this.x -= dx;
			this.y -= dy;
			
			this.screenX -= dx;
			this.screenY -= dy;
			
			moved = false;
		}
		
		return moved;
	}
	
	public void jump(final float delta)
	{
		if (!jumping)
		{
			if (onGround)
			{
				jumping = true;
				
				startY = y;
			}
			else
			{
				return;
			}
		}
		
		if (y < startY + jumpHeight)
		{
			boolean collided = false;
			
			collided = !move(0, 0.8f * delta) || !move(0, 0.8f * delta);
			
			if (collided)
			{
				jumping = false;
			}
		}
		else
		{
			jumping = false;
		}
	}
	
	public abstract void init();
	
	public abstract void render();
	
	public abstract SpriteSheet getSprites();
	
	public abstract LightBuffer getVerticesBuffer();
	
	public abstract LightBuffer getTexturesBuffer();
	
//	public abstract int[] getRenderPoints();
	
	public abstract float[] getVertices();
	
	public abstract float[] getTextures();
}