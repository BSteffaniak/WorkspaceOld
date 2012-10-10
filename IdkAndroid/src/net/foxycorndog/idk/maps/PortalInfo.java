package net.foxycorndog.idk.maps;

import net.foxycorndog.idk.animatedobject.actors.Actor.Direction;

public class PortalInfo
{
	private Location  location;
	
	private short     dx, dy;
	
	private Direction direction;
	
	/**
	* Creates a new PortalInfo that holds a location of what map
	* the location leads, where the second portal is in the next map,
	* and the direction you exit the second portal.
	* 
	* @param location The location of the next Map.
	* @param dx The x position you exit in the next Map.
	* @param dy The y position you exit in the next Map.
	* @param direction The direction you exit the second portal.
	*/
	public PortalInfo(Location location, short dx, short dy, Direction direction)
	{
		this.location  = location;
		
		this.dx        = dx;
		this.dy        = dy;
		
		this.direction = direction;
	}

	/**
	* Method used for getting the location of the map that
	* the portal leads you to.
	* 
	* @return The location of the Map.
	*/
	public Location getLocation()
	{
		return location;
	}
	
	/**
	* Returns the x position where the player will appear after
	* entering the portal.
	* 
	* @return The dx.
	*/
	public short getDx()
	{
		return dx;
	}

	/**
	* Returns the y position where the player will appear after
	* entering the portal.
	* 
	* @return The dy.
	*/
	public short getDy()
	{
		return dy;
	}
	
	/**
	* Returns the direction in which the player will exit out of the
	* portal.
	* 
	* @return The direction.
	*/
	public Direction getDirection()
	{
		return direction;
	}
}