package net.foxycorndog.presto2d.geometry;

import java.awt.Graphics2D;

/*********************************************************************
 * Used to create a rectangle that can be used and tested in many 
 * ways.
 * 
 * @author BradenSteffaniak
 *********************************************************************/
public class Rectangle extends Shape
{
	/*****************************************************************
	 * Used for creating a 2D geometrical circle. Can be used for 
	 * checking intersections, or simply keeping variables and 
	 * values such as x, y, and radius.
	 * 
	 * @param x
	 * @param y
	 * @param width
	 * @param height 
	 *****************************************************************/
	public Rectangle(int x, int y, int width, int height)
	{
		super(x, y, width, height, Shape.RECTANGLE);
	}
	
	/*****************************************************************
	 * Draw the rectangle to the Graphics object passed in the 
	 * parameters.
	 * 
	 * @param g The Graphics object to draw to.
	 *****************************************************************/
	@Override
	public void draw(Graphics2D g)
	{
		g.drawRect((int)getX(), (int)getY(), (int)getWidth(), (int)getHeight());
	}
	
	/*****************************************************************
	 * Draw the rectangle to the Graphics object passed in the 
	 * parameters at the specified location.
	 * 
	 * @param g The Graphics object to draw to.
	 *****************************************************************/
	@Override
	public void draw(int x, int y, Graphics2D g)
	{
		g.drawRect(x, y, (int)getWidth(), (int)getHeight());
	}
	
	/*****************************************************************
	 * Draw a filled in rectangle to the Graphics object passed in 
	 * the parameters.
	 * 
	 * @param g The Graphics object to draw to.
	 *****************************************************************/
	@Override
	public void fill(Graphics2D g)
	{
		g.fillRect((int)getX(), (int)getY(), (int)getWidth(), (int)getHeight());
	}
	
	/*****************************************************************
	 * Draw a filled in rectangle to the Graphics object passed in 
	 * the parameters at the specified location.
	 * 
	 * @param g The Graphics object to draw to.
	 *****************************************************************/
	@Override
	public void fill(int x, int y, Graphics2D g)
	{
		g.fillRect(x, y, (int)getWidth(), (int)getHeight());
	}
}
