package net.foxycorndog.tetris.board;

/**
 * Class used to hold information for each Piece in the Tetris
 * game. There are also methods to manipulate the data and
 * render the data to the screen.
 * 
 * @author	Braden Steffaniak
 * @since	Apr 23, 2013 at 6:59:15 PM
 * @since	v0.1
 * @version	Apr 23, 2013 at 6:59:15 PM
 * @version	v0.1
 */
public class Color
{
	private	int	red, green, blue;
	
	public Color(int r, int g, int b)
	{
		this.red   = r;
		this.green = g;
		this.blue  = b;
	}
	
	public int getRed()
	{
		return red;
	}
	
	public int getGreen()
	{
		return green;
	}
	
	public int getBlue()
	{
		return blue;
	}
	
	public float getRedf()
	{
		return red;
	}
	
	public float getGreenf()
	{
		return green;
	}
	
	public float getBluef()
	{
		return blue;
	}
}