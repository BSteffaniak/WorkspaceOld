package net.foxycorndog.idk.animatedobject.actors;

import net.foxycorndog.idk.maps.Map;

public class Slime extends Actor
{

	/**
	* Create a new slime and add it to the specified map.
	* 
	* @param map The #Map to add the slime to.
	*/
	public Slime(Map map)
	{
		super((byte)10, (short)2, (short)2, (short)4, (short)7, 1, (byte)15, (short)2, (short)10, (short)30, true, true, map);
	}
	
}