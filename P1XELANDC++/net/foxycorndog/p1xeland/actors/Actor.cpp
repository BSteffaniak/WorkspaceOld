#include <iostream>

class Actor
{
	private:
		bool       onGround;
		bool       jumping;
		bool       sprinting, startedSprinting;
		bool       mining;
		bool       onLadder;
		bool       flying;
		bool       crouching, startedCrouching;
		bool       centered;
		bool       collidable;

		int        darkness;
		int        width, height;
		int        jumpHeight;
		int        reach;
		int        editing;

		float      x, y, screenX, screenY, oldX, oldY;
		float      speed;
		float      startY;

		Direction  facing, oldFacing;

		Map        map;

		Inventory  inventory;

		static:
			final int RECT_SIZE = 4 * 2;

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

	public Actor(float x, float y, int width, int height, int jumpHeight, float speed, int reach, Map map)
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

		this.inventory  = new Inventory();

		this.collidable = true;

		map.addActor(this);

		init();

		getVerticesBuffer().setData(0, getVertices());
		getTexturesBuffer().setData(0, getTextures());

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

	public boolean move(float dx, float dy)
	{
		dx *= speed;

//		float oldX2 = x;
//		float oldY2 = y;
//
//		x          += dx;
//		y          += dy;
//
//		if (getMap().collided(this))
//		{
//			x -= dx;
//			y -= dy;
//
//			return false;
//		}
//		else
//		{
//			screenX += dx;
//			screenY += dy;
//
//			oldX = oldX2;
//			oldY = oldY2;
//		}
//
//		return true;

		oldX = getX();
		oldY = getY();

		if (dx != 0 && dy != 0)
		{
			move(dx, 0);
			move(0, dy);
		}

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

				return move(dx * 1.6f, dy);

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
};
