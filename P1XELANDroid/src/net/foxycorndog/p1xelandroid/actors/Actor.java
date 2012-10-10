package net.foxycorndog.p1xelandroid.actors;

import static net.foxycorndog.p1xelandroid.actors.Actor.Direction.*;

import java.util.ArrayList;

import android.content.res.Resources;
import net.foxycorndog.jdoogl.GL;
import net.foxycorndog.jdoogl.components.Frame;
import net.foxycorndog.jdoogl.image.imagemap.SpriteSheet;
import net.foxycorndog.jdoogl.input.TouchInput;
import net.foxycorndog.jdoutil.LightBuffer;
import net.foxycorndog.jdoutil.VerticesBuffer;
import net.foxycorndog.p1xelandroid.P1xeland;
import net.foxycorndog.p1xelandroid.map.Map;

public abstract class Actor
{
	private boolean            onGround;
	private boolean            jumping;
	private boolean            sprinting, startedSprinting;
	private boolean            mining;
	private boolean            onLadder;
	private boolean            flying;
	private boolean            crouching, startedCrouching;
	private boolean            centered;
	private boolean            collidable;
	private boolean            jump;
	
	private int                darkness;
	private int                width, height;
	private int                jumpHeight;
	private int                reach;
	private int                editing;
	
	private float              x, y, screenX, screenY, oldX, oldY;
	private float              speed;
	private float              startY;
	
	private double             velocity;
	
	private Direction          facing, oldFacing;
	
	private Map                map;
	
	private Inventory          inventory;
	
	private int                moves[];
	
	private ArrayList<Float>[] speeds;
	
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
	
	public Actor(float x, float y, int width, int height, int jumpHeight, float speed, int reach, Map map, Resources res)
	{
		this.x          = x;
		this.y          = y;
		this.width      = width;
		this.height     = height;
		
		this.reach      = reach;
		
		this.jumpHeight = jumpHeight;
		
		this.speed      = speed;
		
		this.facing     = FRONT;
		
		this.map        = map;
		
		this.inventory  = new Inventory(res);
		
		this.collidable = true;
		
		this.moves      = new int[4];
		
		this.speeds     = new ArrayList[4];
		
		this.speeds[0]  = new ArrayList<Float>();
		this.speeds[1]  = new ArrayList<Float>();
		this.speeds[2]  = new ArrayList<Float>();
		this.speeds[3]  = new ArrayList<Float>();
		
		this.velocity   = 1;
		
		map.addActor(this);
		
		init(res);
		
		setVerticesBuffer(getVertices());
		setTexturesBuffer(getTextures());
		
//		getVerticesBuffer().endEditing();
//		getTexturesBuffer().endEditing();
		
//		refreshActor();
	}
	
//	public void refreshActor()
//	{
//		getVerticesBuffer().refreshData();
//		getTexturesBuffer().refreshData();
//	}
//	
//	public Object getItem(int index)
//	{
//		return inventory.getItem(index);
//	}
	
	public Inventory getInventory()
	{
		return inventory;
	}
	
	public void giveObject(Object obj)
	{
		inventory.addObject(obj);
	}
	
	public void giveObject(Object obj, int quantity)
	{
		inventory.addObject(obj, quantity);
	}
	
	public void dropObject(Object obj)
	{
		inventory.removeObject(obj);
	}
	
	public void dropObject(Object obj, int quantity)
	{
		inventory.removeObject(obj, quantity);
	}
	
	public Map getMap()
	{
		return map;
	}
	
	public Direction getFacing()
	{
		return facing;
	}
	
	public void setFacing(Direction facing)
	{
		this.facing = facing;
	}
	
	public Direction getOldFacing()
	{
		return oldFacing;
	}
	
	public void setOldFacing(Direction oldFacing)
	{
		this.oldFacing = oldFacing;
	}
	
	public int getReach()
	{
		return reach;
	}
	
	public void setReach(int reach)
	{
		this.reach = reach;
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
	
	public float getOldX()
	{
		return oldX;
	}
	
	public float getOldY()
	{
		return oldY;
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
		this.oldX = getX();
		this.oldY = getY();
		
		this.x = x;
		this.y = y;
		
		this.screenX = x;
		this.screenY = y;
	}
	
	public boolean isCentered()
	{
		return centered;
	}
	
	public void center()
	{
		float newX = Frame.getCenterX() / P1xeland.scale;
		float newY = Frame.getCenterY() / P1xeland.scale;
		
		float dx = newX - getScreenX();
		float dy = newY - getScreenY();
		
		setScreenX(newX);
		setScreenY(newY);
		
		getMap().move(dx, dy);
	}
	
	public void setCentered(boolean centered)
	{
		if (centered)
		{
			center();
		}
		
		this.centered = centered;
	}
	
	public boolean isOnGround()
	{
		return onGround;
	}
	
	public void setOnGround(boolean onGround)
	{
		this.onGround = onGround;
	}
	
	public boolean isMining()
	{
		return mining;
	}
	
	public void setMining(boolean mining)
	{
		this.mining = mining;
	}
	
	public boolean isOnLadder()
	{
		return onLadder;
	}
	
	public void setOnLadder(boolean onLadder)
	{
		this.onLadder = onLadder;
	}
	
	public boolean isFlying()
	{
		return flying;
	}
	
	public void setFlying(boolean flying)
	{
		this.flying = flying;
	}

	public boolean isCollidable()
	{
		return collidable;
	}
	
	public void setCollidable(boolean collidable)
	{
		this.collidable = collidable;
	}
	
	public boolean isCrouching()
	{
		return crouching;
	}
	
	public void setCrouching(boolean crouching)
	{
		this.crouching = crouching;
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
	
	public int getDarkness()
	{
		return darkness;
	}
	
	public void setDarkness(int darkness)
	{
		this.darkness = darkness;
	}
	
	public int getEditing()
	{
		return editing;
	}
	
	public void setEditing(int editing)
	{
		this.editing = editing;
	}
	
	public float getSpeed()
	{
		return speed;
	}
	
	public void setSpeed(float speed)
	{
		this.speed = speed;
		
		this.speed = speed >= P1xeland.textureSize ? P1xeland.textureSize - 1 : speed;
	}
	
	public int getJumpHeight()
	{
		return jumpHeight;
	}
	
	public void setJumpHeight(int jumpHeight)
	{
		this.jumpHeight = jumpHeight;
	}
	
	public boolean getMove(Direction direction)
	{
		if (direction == Direction.LEFT)
		{
			return moves[Direction.getIndex(LEFT)] > 0;
		}
		else if (direction == Direction.RIGHT)
		{
			return moves[Direction.getIndex(RIGHT)] > 0;
		}
		else if (direction == Direction.UP)
		{
			return moves[Direction.getIndex(UP)] > 0;
		}
		else if (direction == Direction.DOWN)
		{
			return moves[Direction.getIndex(DOWN)] > 0;
		}
		
		return false;
	}
	
	public void addMove(Direction direction)
	{
		if (direction == Direction.LEFT)
		{
			moves[Direction.getIndex(LEFT)] ++;
			speeds[Direction.getIndex(LEFT)].add(1f);
		}
		else if (direction == Direction.RIGHT)
		{
			moves[Direction.getIndex(RIGHT)] ++;
			speeds[Direction.getIndex(RIGHT)].add(1f);
		}
		else if (direction == Direction.UP)
		{
			moves[Direction.getIndex(UP)] ++;
			speeds[Direction.getIndex(UP)].add(1f);
		}
		else if (direction == Direction.DOWN)
		{
			moves[Direction.getIndex(DOWN)] ++;
			speeds[Direction.getIndex(DOWN)].add(1f);
		}
	}
	
	public void addMove(Direction direction, int amount)
	{
		if (amount <= 0)
		{
			return;
		}
		
		if (direction == Direction.LEFT)
		{
			moves[Direction.getIndex(LEFT)] += amount;
			
			for (int i = 0; i < amount; i ++)
			{
				speeds[Direction.getIndex(LEFT)].add(1f);
			}
		}
		else if (direction == Direction.RIGHT)
		{
			moves[Direction.getIndex(RIGHT)] += amount;
			
			for (int i = 0; i < amount; i ++)
			{
				speeds[Direction.getIndex(RIGHT)].add(1f);
			}
		}
		else if (direction == Direction.UP)
		{
			moves[Direction.getIndex(UP)] += amount;
			
			for (int i = 0; i < amount; i ++)
			{
				speeds[Direction.getIndex(UP)].add(1f);
			}
		}
		else if (direction == Direction.DOWN)
		{
			moves[Direction.getIndex(DOWN)] += amount;
			
			for (int i = 0; i < amount; i ++)
			{
				speeds[Direction.getIndex(DOWN)].add(1f);
			}
		}
	}
	
	public void addMove(int index, int amount)
	{
		if (amount <= 0)
		{
			return;
		}
		
		moves[index] += amount;
		
		for (int i = 0; i < amount; i ++)
		{
			speeds[index].add(1f);
		}
	}
	
	public boolean getJump()
	{
		return jump;
	}
	
	public void setJump(boolean jump)
	{
		this.jump = jump;
	}
	
	public void tick(float delta)
	{
		boolean up       = getJump();
		boolean down     = getMove(Direction.DOWN);
		boolean left     = getMove(Direction.LEFT);
		boolean right    = getMove(Direction.RIGHT);
		
		int index        = 0;
		
		float speedLeft  = speeds[(index = Direction.getIndex(Direction.LEFT))].size() > 0 ? speeds[index].get(speeds[index].size() - 1) : 1f;
		float speedRight = speeds[(index = Direction.getIndex(Direction.RIGHT))].size() > 0 ? speeds[index].get(speeds[index].size() - 1) : 1f;
		
		boolean movedY   = false;
		boolean movedX   = false;
		
		if (isFlying())
		{
			if (left)
			{
				move(-1.1f * delta * speedLeft, 0);
				setFacing(Direction.LEFT);
			}
			else if (right)
			{
				move(1.1f * delta * speedRight, 0);
				setFacing(Direction.RIGHT);
			}
			if (up)
			{
				move(0, 1.1f * delta);
			}
			else if (down)
			{
				move(0, -1.1f * delta);
			}
		}
		else
		{
			if (!isJumping() && !isOnLadder())
			{
				float v = (float)velocity;
				
				boolean onGround = !move(0, -v * delta) || !move(0, -v * delta);
				
				setOnGround(onGround);
				
				if (onGround)
				{
					velocity = 1;
				}
			}
			else if (isJumping())
			{
				jump(delta);
				
				setOnGround(false);
			}
			else if (isOnLadder() && !up && !isCrouching())
			{
				move(0, -0.7f * delta);
				
				velocity = 1;
			}
			
			if (up)
			{
				if (isOnLadder())
				{
					move(0, 1.1f * delta);
					
					velocity = 1;
				}
				else
				{
					jump(delta);
				}
			}
			else if (down && isOnLadder())
			{
				float v = (float)velocity;
				
				movedY = move(0, -v * delta);
			}
			if (left)
			{
				movedX = move(-1.1f * delta * speedLeft, 0);
				setFacing(Direction.LEFT);
			}
			else if (right)
			{
				movedX = move(1.1f * delta * speedRight, 0);
				setFacing(Direction.RIGHT);
			}
			
			boolean moved = movedY || movedX;
			
			if (!movedX)
			{
				onStoppedMoving();
			}
			
			if (!jumping)
			{
				velocity *= 1.015;
				
				velocity = velocity >= 5 ? 5 : velocity;
			}
		}
		
		moves[Direction.getIndex(LEFT)]  --;
		moves[Direction.getIndex(RIGHT)] --;
		moves[Direction.getIndex(UP)]    --;
		moves[Direction.getIndex(DOWN)]  --;
		
		for (int i = 0; i < 4; i ++)
		{
			if (speeds[i].size() >= 1)
			{
				speeds[i].remove(speeds[i].size() - 1);
			}
		}
		
		moves[Direction.getIndex(LEFT)]  = moves[Direction.getIndex(LEFT)]  < 0 ? 0 : moves[Direction.getIndex(LEFT)];
		moves[Direction.getIndex(RIGHT)] = moves[Direction.getIndex(RIGHT)] < 0 ? 0 : moves[Direction.getIndex(RIGHT)];
		moves[Direction.getIndex(UP)]    = moves[Direction.getIndex(UP)]    < 0 ? 0 : moves[Direction.getIndex(UP)];
		moves[Direction.getIndex(DOWN)]  = moves[Direction.getIndex(DOWN)]  < 0 ? 0 : moves[Direction.getIndex(DOWN)];
		
		jump = false;
	}
	
	public boolean move(float dx, float dy)
	{
		dx *= speed;
		
		if (dx != 0 && dy != 0)
		{
			return (move(dx, 0) | move(0, dy));
		}
		
		oldX = getX();
		oldY = getY();
		
		if (crouching)
		{
			if (!startedCrouching)
			{
				startedCrouching = true;
				
				return move(dx * .35f, dy);
				
//				startedCrouching = false;
			}
			else
			{
				startedCrouching = false;
			}
		}
		else if (sprinting)
		{
			if (!startedSprinting)
			{
				startedSprinting = true;
				
//				move(dx * 0.27f, dy * 0.f);
//				move(dx * 0.27f, dy * 0.f);
				
				move(dx / 1.95f, 0);
				
//				startedSprinting = false;
			}
			else
			{
				startedSprinting = false;
			}
		}
		
		this.x += dx;
		this.y += dy;
		
		if (centered)
		{
			map.move(-dx, -dy);
		}
		else
		{
			this.screenX += dx;
			this.screenY += dy;
		}
		
		boolean moved = true;
		
		if (getMap().collided(this))
		{
			this.x -= dx;
			this.y -= dy;
			
			if (centered)
			{
				map.move(dx, dy);
			}
			else
			{
				this.screenX -= dx;
				this.screenY -= dy;
			}
			
			moved = false;
		}
		
		return moved;
	}
	
	public void jump(float delta)
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
		else
		{
			velocity = 1;
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
	
	public abstract void init(Resources res);
	
	public abstract void render();
	
	public abstract SpriteSheet getSprites();
	
	public abstract VerticesBuffer getVerticesBuffer();
	
	public abstract void setVerticesBuffer(VerticesBuffer buffer);
	
	public abstract LightBuffer getTexturesBuffer();
	
	public abstract void setTexturesBuffer(LightBuffer buffer);
	
//	public abstract int[] getRenderPoints();
	
	public abstract VerticesBuffer getVertices();
	
	public abstract void onStoppedMoving();
	
	public abstract LightBuffer getTextures();
}