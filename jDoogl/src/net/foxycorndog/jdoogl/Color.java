package net.foxycorndog.jdoogl;

public class Color extends org.newdawn.slick.Color
{
	public static final Color BLACK      = new Color(0, 0, 0);
	public static final Color WHITE      = new Color(255, 255, 255);
	public static final Color CYAN       = new Color(0, 255, 255);
	public static final Color YELLOW     = new Color(255, 255, 0);
	public static final Color RED        = new Color(255, 0, 0);
	public static final Color BLUE       = new Color(0, 0, 255);
	public static final Color GREEN      = new Color(0, 255, 0);
	public static final Color DARK_GRAY  = new Color(80, 80, 80);
	public static final Color GRAY       = new Color(125, 125, 125);
	public static final Color LIGHT_GRAY = new Color(200, 200, 200);
	
	public Color(float r, float g, float b)
	{
		super(r / 255f, g / 255f, b / 255f);
	}
	
	public static Color getColor(String name)
	{
		name = name.toLowerCase();
		
		if (name.equals("black"))
		{
			return BLACK;
		}
		else if (name.equals("white"))
		{
			return WHITE;
		}
		else if (name.equals("cyan"))
		{
			return CYAN;
		}
		else if (name.equals("yellow"))
		{
			return YELLOW;
		}
		else if (name.equals("red"))
		{
			return RED;
		}
		else if (name.equals("blue"))
		{
			return BLUE;
		}
		else if (name.equals("green"))
		{
			return GREEN;
		}
		else if (name.equals("dark gray"))
		{
			return DARK_GRAY;
		}
		else if (name.equals("gray"))
		{
			return GRAY;
		}
		else if (name.equals("light gray"))
		{
			return LIGHT_GRAY;
		}
		
		return null;
	}
}