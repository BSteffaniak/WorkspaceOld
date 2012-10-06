package net.neonseal.idk.maps.buildings;

import net.neonseal.idk.Idk;
import net.neonseal.idk.maps.Map;

public abstract class BuildingMap extends Map
{
	/**
	* Creates a new BuildingMap used for holding a map of the
	* inside of a building that it is linked with.
	* 
	* @param location The location of the map image.
	*/
	public BuildingMap(String prelocation, String location, boolean createForeground)//, boolean createActors)
	{
		super(prelocation, location, 0, 0, createForeground);//, createActors);
		
		initialize();
	}
	
	/**
	* An abstract method that is used whenever the map is created.
	*/
	public abstract void initialize();
}