package net.neonseal.idk.maps;

import net.neonseal.idk.maps.buildings.Building;

public class BuildingLocation extends Location
{
	private Building building;
	
	/**
	* Create a new building location.
	* 
	* @param building The building to tie this object with.
	*/
	public BuildingLocation(Building building)
	{
		this.building = building;
	}
	
	/**
	* Get the building that this class is tied with.
	* 
	* @return The building object.
	*/
	public Building getBuilding()
	{
		return building;
	}
}