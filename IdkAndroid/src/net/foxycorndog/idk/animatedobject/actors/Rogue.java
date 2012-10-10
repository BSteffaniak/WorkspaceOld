package net.foxycorndog.idk.animatedobject.actors;

import net.foxycorndog.idk.maps.Map;

public class Rogue extends Actor
{
	
	/**
	* Create a new rogue and add it to the specified map.
	* 
	* @param map The #Map to add the rogue to.
	*/
	public Rogue(Map map)
	{
		super((byte)5, (short)3, (short)5, (short)9, (short)2, 2.3f, (byte)9, (short)5, (short)5, (short)25, false, true, map);
	}
}