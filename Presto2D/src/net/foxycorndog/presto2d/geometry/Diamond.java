package net.foxycorndog.presto2d.geometry;

import java.awt.Graphics2D;
import java.awt.Polygon;

/*********************************************************************
 * Used for creating a diamond shaped shape.
 * 
 * @author BradenSteffaniak
 *********************************************************************/
public class Diamond extends Shape
{
	
	public Diamond(int x, int y, int width, int height)
	{
		super(x, y, width, height, Shape.DIAMOND);
	}
	
	@Override
	public void fill(Graphics2D g)
	{
		Polygon p = new Polygon();
		
		p.addPoint((int)getX() + ((int)getWidth() / 2), (int)getY());
		p.addPoint((int)getX(), (int)getY() + ((int)getHeight() / 2));
		p.addPoint((int)getX() + ((int)getWidth() / 2), (int)getY() + (int)getHeight());
		p.addPoint((int)getX() + (int)getWidth(), (int)getY() + ((int)getHeight() / 2));
		
		g.fillPolygon(p);
	}
	
	@Override
	public void fill(int x, int y, Graphics2D g)
	{
		Polygon p = new Polygon();
		
		p.addPoint(x + ((int)getWidth() / 2), y);
		p.addPoint(x, y + ((int)getHeight() / 2));
		p.addPoint(x + ((int)getWidth() / 2), y + (int)getHeight());
		p.addPoint(x + (int)getWidth(), y + ((int)getHeight() / 2));
		
		g.fillPolygon(p);
	}
	
	@Override
	public void draw(Graphics2D g)
	{
		Polygon p = new Polygon();
		
		p.addPoint((int)getX() + ((int)getWidth() / 2), (int)getY());
		p.addPoint((int)getX(), (int)getY() + ((int)getHeight() / 2));
		p.addPoint((int)getX() + ((int)getWidth() / 2), (int)getY() + (int)getHeight());
		p.addPoint((int)getX() + (int)getWidth(), (int)getY() + ((int)getHeight() / 2));
		
		g.drawPolygon(p);
	}
	
	@Override
	public void draw(int x, int y, Graphics2D g)
	{
		Polygon p = new Polygon();
		
		p.addPoint(x + ((int)getWidth() / 2), y);
		p.addPoint(x, y + ((int)getHeight() / 2));
		p.addPoint(x + ((int)getWidth() / 2), y + (int)getHeight());
		p.addPoint(x + (int)getWidth(), y + ((int)getHeight() / 2));
		
		g.drawPolygon(p);
	}
}
