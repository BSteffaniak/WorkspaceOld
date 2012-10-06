package net.foxycorndog.presto2d.geometry;

import java.awt.Graphics2D;

/*********************************************************************
 * 
 * 
 * @author BradenSteffaniak
 *********************************************************************/
public abstract class ShapeTemplate
{
	
	public ShapeTemplate()
	{
		
	}
	
	public abstract void fill(Graphics2D g);
	
	public abstract void fill(int x, int y, Graphics2D g);
	
	public abstract void draw(Graphics2D g);
	
	public abstract void draw(int x, int y, Graphics2D g);
	
	public abstract double getX();
	
	public abstract double getY();
	
	public abstract void setX(double x);
	
	public abstract void setY(double y);
	
	public abstract void move(double dx, double dy);
	
	public abstract double getWidth();
	
	public abstract double getHeight();
}
