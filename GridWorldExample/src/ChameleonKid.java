/**
 * Description:		A ChameleonKid changes color to the color of one of the actors
 * 	immediately in front or behind. If there is no actor in either of
 * 	these locations, then the ChameleonKid darkens like the modified
 * 	ChameleonCritter.
 */
public class ChameleonKid extends ChameleonCritter
{
	public void makeMove()
	{
		super.makeMove()
		
		Grid grid = getGrid();
		
		ArrayList<Location> occupied = grid.getOccupiedAdjacentLocations();
		
		if (occupied.size() > 0)
		{
			
		}
		else
		{
			Color col = getColor();
			
			int r = col.getR();
			int g = col.getG();
			int b = col.getB();
			
			int amount = 5;
			
			r -= amount;
			g -= amount;
			b -= amount;
			
			if (r < 0)
				r = 0;

			if (g < 0)
				g = 0;

			if (b < 0)
				b = 0;
			
			Color newCol = newColor(r, g, b);
			
			setColor(newCol);
		}
	}
}
