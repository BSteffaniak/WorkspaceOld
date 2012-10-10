package net.foxycorndog.idk.animatedobject.actors.player;

import net.foxycorndog.idk.animatedobject.actors.Actor;
import net.foxycorndog.idk.items.Item;
import net.foxycorndog.idk.items.Apparel.ApparelId;
import net.foxycorndog.idk.items.Potion.PotionId;
import net.foxycorndog.idk.maps.Map;
import net.foxycorndog.jdoogl.components.Frame;

public class Player extends Actor
{
	private boolean focused;
	private boolean viewingInventory;
	
	private short    level;
	private short    xp;
	private short    nextXp;
//	private short    points;
	
	/**
	* Create a new player and add it to the specified map.
	* 
	* @param map The #Map to add the player to.
	* @param display The #Display in which it displays.
	*/
	public Player(Map map, boolean focused, boolean init)
	{
		super((byte)0, (short)3, (short)5, (short)9, (short)2, 2, (byte)5, (short)5, (short)5, (short)100, false, init, map);
		
		level        = 1;
		nextXp       = 10;
		
		this.focused = focused;
		
		Frame.setTopBuffer   ((short)(Frame.getHeight() / 4));
		Frame.setBottomBuffer((short)(Frame.getHeight() / 4));
		Frame.setLeftBuffer  ((short)(Frame.getWidth()  / 4));
		Frame.setRightBuffer ((short)(Frame.getWidth()  / 4));
		
		getInventory().addItem(Item.getItemById(ApparelId.MAGES_ROBE.getId()));
		getInventory().addItem(Item.getItemById(PotionId.MINOR_HEALING_POTION.getId()));
		getInventory().addItem(Item.getItemById(PotionId.MINOR_HEALING_POTION.getId()));
		getInventory().addItem(Item.getItemById(PotionId.MINOR_HEALING_POTION.getId()));
		getInventory().addItem(Item.getItemById(PotionId.MINOR_HEALING_POTION.getId()));
		getInventory().addItem(Item.getItemById(PotionId.MINOR_HEALING_POTION.getId()));
		getInventory().addItem(Item.getItemById(PotionId.MINOR_HEALING_POTION.getId()));
		getInventory().addItem(Item.getItemById(PotionId.MINOR_HEALING_POTION.getId()));
		getInventory().addItem(Item.getItemById(PotionId.MINOR_HEALING_POTION.getId()));
	}
	
	/**
	* Try to move the actor the dx amount and dy amount.
	* 
	* @param dx The x amount to move.
	* @param dy The y amount to move.
	* @return Whether the move was successful or not.
	*/
	public boolean move(float dx, float dy)
	{
		if (!isEnabled())
		{
			return false;
		}
		
		if (!focused)
		{
			return super.move(dx, dy);
		}
		
		int frameWidth  = Frame.getWidth();
		int frameHeight = Frame.getHeight();
		
		boolean moved = false;
		
		int j = 3;
		
		if (j == 0)
		{
			if (canMove(dx, 0))
			{
				if (dx > 0 && getX() + dx > frameWidth - Frame.getRightBuffer())
				{
					moved = getMap().move(-dx, 0);//move((frameWidth - (frameWidth / 4)) - (getX() + dx), 0);//super.move((frameWidth - (frameWidth / 4)) - (getX() + dx), 0);
				}
				if (dx < 0 && getX() + dx < Frame.getLeftBuffer())
				{
					moved = getMap().move(-dx, 0);//move((frameWidth / 4) - (getX() + dx), 0);//super.move((frameWidth / 4) - (getX() + dx), 0);
				}
				
				if (moved)
				{
					setAbsoluteX(getAbsoluteX() + dx);
				}
			}
			if (canMove(0, dy))
			{
				if (dy > 0 && getY() + dy > frameHeight - Frame.getBottomBuffer())
				{
					moved = getMap().move(0, -dy);//move(0, (frameHeight - (frameHeight / 4)) - (getY() + dy));//super.move(0, (frameHeight - (frameHeight / 4)) - (getY() + dy));
				}
				if (dy < 0 && getY() + dy < Frame.getTopBuffer())
				{
					moved = getMap().move(0, -dy);//move(0, (frameHeight / 4) - (getY() + dy));//super.move(0, (frameHeight / 4) - (getY() + dy));
				}
				
				if (moved)
				{
					setAbsoluteY(getAbsoluteY() + dy);
				}
			}
			
			if (!moved)
			{
				moved = super.move(dx, dy);
			}
		}
		else
		{
			if (canMove(dx, 0))
			{
				moved = getMap().move(-dx, 0);
				
				setAbsoluteX(getAbsoluteX() + dx);
			}
			if (canMove(0, dy))
			{
				moved = getMap().move(0, -dy);
				
				setAbsoluteY(getAbsoluteY() + dy);
			}
		}
		
		return moved;
	}
	
	/**
	* Remove a specified amount of health from the actor.
	* 
	* @param amount The amount of health to remove.
	* @param enemyX If the actor was attacked by another actor,
	* 		then the enemy who attacked him's x position.
	* @param attacked Whether the health removed was in result of
	* 		an attack.
	*/
	public void removeHealth(int amount, float enemyX, boolean attacked)
	{
		super.removeHealth(amount, enemyX, false);
	}
	
	/**
	* Attack the nearest enemies.
	*/
	public short attack()
	{
		short xpAdd = super.attack();
		
		addXp(xpAdd);
		
		if (xpAdd > 0)
		{
			System.out.println(xp + "/" + nextXp + " xp");
		}
		
		return getExperienceRelease();
	}
	
	/**
	* Add experience to the player.
	* 
	* @param amount The amount of experience to add.
	*/
	public void addXp(short amount)
	{
		xp += amount;
		
		calculateXp();
	}
	
	/**
	* Calculate the experience that the player has gained and
	* level up if necessary.
	*/
	private void calculateXp()
	{
		if (xp >= nextXp)
		{
			level  ++;
			
			xp     %= nextXp;
			
			nextXp += nextXp / 4;
			
			System.out.println("Level: " + level);
		}
	}
	
	/**
	* Set the location of the user. Moves the display with it too
	* if necessary.
	*/
	public void setLocation(float x, float y)
	{
		if (!isEnabled())
		{
			return;
		}
		
		if (!focused)
		{
			super.setLocation(x, y);
		}
		
		setAbsoluteX(x);
		setAbsoluteY(y);

		float centerX = (Frame.getWidth()  / 2) - (getScaledWidth()  / 2);
		float centerY = (Frame.getHeight() / 2) - (getScaledHeight() / 2);
		
		setX(centerX);
		setY(centerY);
		
		float mapOffsetX = -x + (centerX);
		float mapOffsetY = -y + (centerY);
		
		getMap().setLocation(mapOffsetX, mapOffsetY);
	}
	
	public boolean isViewingInventory()
	{
		return viewingInventory;
	}
	
	public void viewInventory()
	{
		viewingInventory = true;
	}
	
	public void closeInventory()
	{
		viewingInventory = false;
	}
}
