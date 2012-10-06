package net.foxycorndog.presto2d.geometry;

import java.awt.Graphics2D;

/*********************************************************************
 * Used to create an oval that can be used in several ways.
 * 
 * @author BradenSteffaniak
 *********************************************************************/
public class Oval extends Shape
{
	
	public Oval(int x, int y, int width, int height)
	{
		super(x, y, width, height, Shape.OVAL);
	}
	
	@Override
	public void fill(Graphics2D g)
	{
		g.fillOval((int)getX(), (int)getY(), (int)getWidth(), (int)getHeight());
	}
	
	@Override
	public void fill(int x, int y, Graphics2D g)
	{
		g.fillOval(x, y, (int)getWidth(), (int)getHeight());
	}
	
	@Override
	public void draw(Graphics2D g)
	{
		g.drawOval((int)getX(), (int)getY(), (int)getWidth(), (int)getHeight());
	}
	
	@Override
	public void draw(int x, int y, Graphics2D g)
	{
		g.drawOval(x, y, (int)getWidth(), (int)getHeight());
	}
}
