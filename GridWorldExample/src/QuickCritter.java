import java.util.ArrayList;

import info.gridworld.grid.Grid;
import info.gridworld.grid.Location;

public class QuickCritter extends CrabCritter
{
	/**
	 * Finds the valid adjacent locations of this critter in different
	 * directions.
	 * @param directions - an array of directions (which are relative to the
	 * current direction)
	 * @return a set of valid locations that are neighbors of the current
	 * location in the given directions
	 */
	public ArrayList<Location> getLocationsInDirections(int[] directions)
	{
		ArrayList<Location> locs = new ArrayList<Location>();
		Grid gr = getGrid();
		Location loc = getLocation();

		for (int d : directions)
		{
			Location neighborLoc = loc.getAdjacentLocation(getDirection() + d).getAdjacentLocation(getDirection() + d);
			if (gr.isValid(neighborLoc) && gr.get(neighborLoc) == null)
			{
				locs.add(neighborLoc);
			}
			else
			{
				neighborLoc = loc.getAdjacentLocation(getDirection() + d);
				
				if (gr.isValid(neighborLoc) && gr.get(neighborLoc) == null)
				{
					locs.add(neighborLoc);
				}
				else
				{
					super.getLocationsInDirections(directions);
				}
			}
		}
		return locs;
	}
}