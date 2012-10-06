package net.neonseal.idk.animatedobject;

import static net.neonseal.idk.Idk.scale;
import net.neonseal.idk.maps.Map;

public class AnimatedObject
{
	private boolean enabled;
	
	private float   x, y, z;
	private float   absoluteX, absoluteY;
	
	private int     width, height;
	private int     xo, yo;
	
	private Map     map;
	
	public AnimatedObject(float x, float y, float z, int width, int height, int xo, int yo, Map map)
	{
		enabled = true;
		
		this.x         = x;
		this.y         = y;
		this.z         = z;
		
		this.absoluteX = x;
		this.absoluteY = y;
		
		this.width     = width;
		this.height    = height;
		
		this.xo        = xo;
		this.yo        = yo;
		
		this.map       = map;
	}
	
	/**
	* Fore moves the actor even if there is an object in front of him.
	* 
	* @param dx The x offset.
	* @param dy The y offset.
	* @return Whether the move was successful.
	*/
	public boolean forceMove(float dx, float dy)
	{
		if (!enabled)
		{
			return false;
		}
		
		x += dx;
		y += dy;
		
		this.absoluteX += dx;
		this.absoluteY += dy;
		
		return true;
	}
	
	/**
	* Set the location to the specified location.
	* 
	* @param x The x position.
	* @param y The y position.
	*/
	public void setLocation(float x, float y)
	{
		if (!enabled)
		{
			return;
		}
		
		this.absoluteX = x;
		this.absoluteY = y;
		
		this.x = x;
		this.y = y;
	}
	
	/**
	* Get the absolute x of the actor.
	* 
	* @return The absolute x relative to the origin of the map.
	*/
	public float getAbsoluteX()
	{
		return absoluteX;
	}
	
	/**
	* Set the absolute x of the actor.
	* 
	* @param x The absolute x relative to the origin of the map.
	*/
	public void setAbsoluteX(float x)
	{
		if (!enabled)
		{
			return;
		}
		
		absoluteX = x;
	}
	
	/**
	* Get the absolute y of the actor.
	* 
	* @return The absolute y relative to the origin of the map.
	*/
	public float getAbsoluteY()
	{
		return absoluteY;
	}
	
	/**
	* Set the absolute y of the actor.
	* 
	* @param y The absolute y relative to the origin of the map.
	*/
	public void setAbsoluteY(float y)
	{
		if (!enabled)
		{
			return;
		}
		
		absoluteY = y;
	}
	
	/**
	* Get the absolute x of the animated object's center.
	* 
	* @return The absolute x relative to the origin of the map in the
	* 		center of the animated object.
	*/
	public float getScaledAbsoluteCenterX()
	{
		return absoluteX + (getScaledWidth() * 2);
	}
	
	/**
	* Get the absolute y of the animated object's center.
	* 
	* @return The absolute y relative to the origin of the map in the
	* 		center of the animated object.
	*/
	public float getScaledAbsoluteCenterY()
	{
		return absoluteY + (getScaledHeight());
	}
	
	/**
	* Return the x value that the actor shows up on the screen.
	* 
	* @return The x value relative to the screen.
	*/
	public float getX()
	{
		return x;
	}
	
	/**
	* Sets the x value at which the actor shows up on the screen.
	* 
	* @param x The x value relative to the screen.
	*/
	public void setX(float x)
	{
		if (!enabled)
		{
			return;
		}
		
		this.x = x;
	}
	
	/**
	* Return the y value that the actor shows up on the screen.
	* 
	* @return The y value relative to the screen.
	*/
	public float getY()
	{
		return y;
	}
	
	/**
	* Sets the y value at which the actor shows up on the screen.
	* 
	* @param y The y value relative to the screen.
	*/
	public void setY(float y)
	{
		if (!enabled)
		{
			return;
		}
		
		this.y = y;
	}
	
	/**
	* Return the z value that the actor shows up on the screen.
	* 
	* @return The z value relative to the screen.
	*/
	public float getZ()
	{
		return z;
	}
	
	/**
	* Get the width of the actor.
	* 
	* @return The width of the sprite divided by 10.
	*/
	public int getWidth()
	{
		return width;
	}
	
	/**
	* Get the height of the actor.
	* 
	* @return The height of the sprite divided by 10.
	*/
	public int getHeight()
	{
		return height;
	}
	
	public int getXo()
	{
		return xo;
	}
	
	public int getYo()
	{
		return yo;
	}
	
	/**
	* Get the scaled width of the actor with the xo accounted for.
	* 
	* @return The width of the actor that is displayed, in pixels.
	*/
	public float getScaledWidth()
	{
		return width * scale * 10 - xo;
	}
	
	/**
	* Get the scaled height of the actor with the yo accounted for.
	* 
	* @return The height of the actor that is displayed, in pixels.
	*/
	public float getScaledHeight()
	{
		return height * scale * 10 - yo;
	}
	
	public float getScreenX()
	{
		return map.getX() + absoluteX;
	}
	
	public float getScreenY()
	{
		return map.getY() + absoluteY;
	}
	
	/**
	* Get the #net.foxycorndog.idk.maps.Map that the AnimatedObject
	* belongs to.
	* 
	* @return The Map that it is in.
	*/
	public Map getMap()
	{
		return map;
	}
	
	/**
	* Set the map that the AnimatedObject belongs to.
	* 
	* @param map The #net.foxycorndog.idk.maps.Map.
	*/
	public void setMap(Map map)
	{
		this.map = map;
	}
	
	public void enable()
	{
		enabled = true;
	}
	
	public void disable()
	{
		enabled = false;
	}
	
	public boolean isEnabled()
	{
		return enabled;
	}
}