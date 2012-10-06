package net.foxycorndog.presto2d.geometry;

import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.Stroke;

/*********************************************************************
 * Used to create a line that can be used and tested in many ways.
 * 
 * @author BradenSteffaniak
 *********************************************************************/
public class Line extends Shape
{
	private double x1, x2, y1, y2;
	private int[] xs, ys;
	private int thickness;
	
	/*****************************************************************
	 * Used for creating a 2D geometrical line. Can be used for 
	 * checking intersections, or simply keeping variables and 
	 * values such as x, y.
	 * 
	 * @param x1 X position of the first point on the line.
	 * @param y1 Y position of the first point on the line.
	 * @param x2 X position of the second point on the line.
	 * @param y2 Y position of the second point on the line.
	 *****************************************************************/
	public Line(int x1, int y1, int x2, int y2, int thickness)
	{
		super(x1 < x2 ? x1 : x2, y1 < y2 ? y1 : y2, (x1 < x2 ? x2 : x1) - (x1 < x2 ? x1 : x2), (y1 < y2 ? y2 : y1) - (y1 < y2 ? y1 : y2), Shape.LINE);
		
		this.x1 = x1;
		this.x2 = x2;
		this.y1 = y1;
		this.y2 = y2;
		
		xs = new int[thickness * 4];
		ys = new int[thickness * 4];
		
		for (int i = 0; i < thickness; i ++)
		{
			xs[i + i	] = ((int)getX1() - i);
			ys[i + i	] = ((int)getY1() + i);
			
			xs[i + 1 + i] = ((int)getX2() - i);
			ys[i + 1 + i] = ((int)getX2() + i);
			
			xs[i + 2 + i] = ((int)getX1() - i);
			ys[i + 2 + i] = ((int)getY1() + i);
			
			xs[i + 3 + i] = ((int)getX2() - i);
			ys[i + 3 + i] = ((int)getX2() + i);
		}
		
		this.thickness = thickness;
	}
	
	/*****************************************************************
	 * Get the X position of the first point on the line.
	 * 
	 * @return The X position value.
	 *****************************************************************/
	public double getX1()
	{
		return x1;
	}
	
	/*****************************************************************
	 * Get the X position of the second point on the line.
	 * 
	 * @return The X position value.
	 *****************************************************************/
	public double getX2()
	{
		return x2;
	}
	
	/*****************************************************************
	 * Get the Y position of the first point on the line.
	 * 
	 * @return The Y position value.
	 *****************************************************************/
	public double getY1()
	{
		return y1;
	}
	
	/*****************************************************************
	 * Get the Y position of the second point on the line.
	 * 
	 * @return The Y position value.
	 *****************************************************************/
	public double getY2()
	{
		return y2;
	}
	
	/*****************************************************************
	 * Get the thickness of the line.
	 * 
	 * @return The thickness of the line. (int)
	 *****************************************************************/
	public int getThickness()
	{
		return thickness;
	}
	
	/*****************************************************************
	 * Draw the line to the Graphics object passed in the 
	 * parameters.
	 * 
	 * @param g The Graphics object to draw to.
	 *****************************************************************/
	@Override
	public void draw(Graphics2D g)
	{
//		for (int i = 0; i < getThickness(); i ++)
//		{
//			g.drawPolyline(xs, ys, xs.length + ys.length + 2);
//			
//			g.drawLine((int)getX1() - i, (int)getY1() + i, (int)getX2() - i, (int)getY2() + i);
//			g.drawLine((int)getX1() - i - 1, (int)getY1() + i, (int)getX2() - i - 1, (int)getY2() + i);
//		}
		
		Stroke s = g.getStroke();
		
		g.setStroke(new BasicStroke(getThickness()));
		
		g.drawLine((int)getX(), (int)getY(), (int)getX2(), (int)getY2());
		
		g.setStroke(s);
	}
	
	/*****************************************************************
	 * Draw a filled in line to the Graphics object passed in 
	 * the parameters.
	 * 
	 * @param g The Graphics object to draw to.
	 *****************************************************************/
	@Override
	public void fill(Graphics2D g)
	{
		draw(g);
	}
	
	/*****************************************************************
	 * Draw the line to the Graphics object passed in the 
	 * parameters at the specified position.
	 * 
	 * @param g The Graphics object to draw to.
	 *****************************************************************/
	@Override
	public void draw(int x, int y, Graphics2D g)
	{
		Stroke s = g.getStroke();
		
		int x22 = (int)getX2() - ((int)getX() - x);
		int y22 = (int)getY2() - ((int)getY() - y);
		
		g.setStroke(new BasicStroke(getThickness()));
		
		g.drawLine(x, y, x22, y22);
		
		g.setStroke(s);
//		int x22 = (int)getX2() - ((int)getX() - x);
//		int y22 = (int)getY2() - ((int)getY() - y);
//		
//		for (int i = 0; i < getThickness(); i ++)
//		{
//			g.drawLine(x - i, y + i, x22 - i, y22 + i);
//			g.drawLine(x - i - 1, y + i, x22 - i - 1, y22 + i);
//		}
	}
	
	/*****************************************************************
	 * Draw a filled in line to the Graphics object passed in 
	 * the parameters at the specified position.
	 * 
	 * @param g The Graphics object to draw to.
	 *****************************************************************/
	@Override
	public void fill(int x, int y, Graphics2D g)
	{
		draw(x, y, g);
	}
}
