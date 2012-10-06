package net.foxycorndog.presto2d.geometry;

import java.awt.Graphics2D;

/*********************************************************************
 * Used to create a circle that can be used and tested in many ways.
 * 
 * @author BradenSteffaniak
 *********************************************************************/
public class Circle extends Shape
{
	private double radius;
	
	/*****************************************************************
	 * Used for creating a 2D geometrical circle. Can be used for 
	 * checking intersections, or simply keeping variables and 
	 * values such as x, y, and radius.
	 * 
	 * @param x The x position of the circle.
	 * @param y The y position of the circle.
	 * @param radius The radius of the circle.
	 *****************************************************************/
	public Circle(int x, int y, int radius)
	{
		super(x, y, radius * 2, radius * 2, Shape.CIRCLE);
		
		this.radius = radius;
	}
	
	/*****************************************************************
	 * Get the radius of the circle.
	 * 
	 * @return The radius.
	 *****************************************************************/
	public double getRadius()
	{
		return radius;
	}
	
	/*****************************************************************
	 * Draw the circle to the Graphics object passed in the 
	 * parameters.
	 * 
	 * @param g The Graphics object to draw to.
	 *****************************************************************/
	@Override
	public void draw(Graphics2D g)
	{
		g.drawOval((int)getX(), (int)getY(), (int)getWidth(), (int)getHeight());
	}
	
	/*****************************************************************
	 * Draw a filled in circle to the Graphics object passed in the 
	 * parameters.
	 * 
	 * @param g The Graphics object to draw to.
	 *****************************************************************/
	@Override
	public void fill(Graphics2D g)
	{
		g.fillOval((int)getX(), (int)getY(), (int)getWidth(), (int)getHeight());
	}
	
	/*****************************************************************
	 * Draw the circle to the Graphics object passed in the 
	 * parameters at the specified position.
	 * 
	 * @param g The Graphics object to draw to.
	 *****************************************************************/
	@Override
	public void draw(int x, int y, Graphics2D g)
	{
		g.drawOval(x, y, (int)getWidth(), (int)getHeight());
	}
	
	/*****************************************************************
	 * Draw a filled in circle to the Graphics object passed in the 
	 * parameters at the specified position.
	 * 
	 * @param g The Graphics object to draw to.
	 *****************************************************************/
	@Override
	public void fill(int x, int y, Graphics2D g)
	{
		g.fillOval(x, y, (int)getWidth(), (int)getHeight());
	}
}
