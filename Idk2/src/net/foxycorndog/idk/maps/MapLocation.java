package net.foxycorndog.idk.maps;

public class MapLocation extends Location
{
	private byte x, y;
	
	/**
	* Creates a new MapLocation that is used for holding the
	* location of a Map.
	* 
	* @param x The relative x position of the Map.
	* @param y The relative y position of the Map.
	*/
	public MapLocation(byte x, byte y)
	{
		this.x = x;
		this.y = y;
	}
	
	/**
	* Method used for getting the x position variable.
	* 
	* @return The x position.
	*/
	public byte getX()
	{
		return x;
	}
	
	/**
	* Method used for getting the y position variable.
	* 
	* @return The y position.
	*/
	public byte getY()
	{
		return y;
	}
}