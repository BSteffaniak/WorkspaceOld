package net.foxycorndog.presto2d.geometry;

import java.awt.Graphics2D;
import java.awt.Polygon;

/*********************************************************************
 * Used to be able to create free form polygons.
 * 
 * @author BradenSteffaniak
 *********************************************************************/
public class FreeForm extends Shape
{
	Polygon p;
	
	public FreeForm()
	{
		super(0, 0, 0, 0, Shape.FREEFORM);
		
		p = new Polygon();
	}
	
	public void reset()
	{
		p.reset();
	}
	
	public void addPoint(int x, int y)
	{
		p.addPoint(x, y);
		
		if (p.npoints >= 3)
		{
			createImage((int)getWidth(), (int)getHeight());
		}
	}
	
	public void removePoint(int index)
	{
		int[] xp = new int[p.xpoints.length - 1];
		int[] yp = new int[p.ypoints.length - 1];
		
		int d = 0;
		
		for (int i = 0; i < p.xpoints.length; i ++)
		{
			if (i == index)
			{
				d ++;
			}
			else
			{
				xp[i] = p.xpoints[i + d];
				yp[i] = p.ypoints[i + d];
			}
		}
	}
	
	public void removePoint(int x, int y)
	{	
		int[] xp = new int[p.xpoints.length - 1];
		int[] yp = new int[p.ypoints.length - 1];
		
		int d = 0;
		
		for (int i = 0; i < p.xpoints.length; i ++)
		{
			if (p.xpoints[i] == x && p.ypoints[i] == y)
			{
				d ++;
			}
			else
			{
				xp[i] = p.xpoints[i + d];
				yp[i] = p.ypoints[i + d];
			}
		}
	}
	
	@Override
	public void fill(Graphics2D g)
	{
		g.fillPolygon(p);
	}
	
	@Override
	public void fill(int x, int y, Graphics2D g)
	{
		g.translate(-(getLeftPoint()) + x, -(getTopPoint()) + y);
		g.fillPolygon(p);
	}
	
	@Override
	public void draw(Graphics2D g)
	{
		g.drawPolygon(p);
	}
	
	@Override
	public void draw(int x, int y, Graphics2D g)
	{
		g.translate(-(getLeftPoint()) + x, -(getTopPoint()) + y);
		g.drawPolygon(p);
	}
	
	public int getLeftPoint()
	{
		int d = 0, d2 = 0;
		
		for (int i = 0; i < p.xpoints.length; i ++)
		{
			d2 = p.xpoints[i];
			
			d = d2 < d ? d2 : d;
		}
		
		return d;
	}
	
	public int getRightPoint()
	{
		int d = 0, d2 = 0;
		
		for (int i = 0; i < p.xpoints.length; i ++)
		{
			d2 = p.xpoints[i];
			
			d = d2 > d ? d2 : d;
		}
		
		return d;
	}
	
	public int getTopPoint()
	{
		int d = 0, d2 = 0;
		
		for (int i = 0; i < p.ypoints.length; i ++)
		{
			d2 = p.ypoints[i];
			
			d = d2 < d ? d2 : d;
		}
		
		return d;
	}
	
	public int getBottomPoint()
	{
		int d = 0, d2 = 0;
		
		for (int i = 0; i < p.ypoints.length; i ++)
		{
			d2 = p.ypoints[i];
			
			d = d2 > d ? d2 : d;
		}
		
		return d;
	}
	
	@Override
	public void move(double dx, double dy)
	{
		super.move((int)dx, (int)dy);
		
		p.translate((int)dy, (int)dy);
	}
	
	@Override
	public double getWidth()
	{
		return getRightPoint() - getLeftPoint();
	}
	
	@Override
	public double getHeight()
	{
		return getBottomPoint() - getTopPoint();
	}
}
