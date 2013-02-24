package net.foxycorndog.thedigginggame.entities;

import net.foxycorndog.presto2d.graphics.PixelPanel;
import net.foxycorndog.thedigginggame.map.Map;

public class Cursor
{
	private float   x, y;
	private float   ax, ay;
	private int     width, height;
	private float   oldRenderX, oldRenderY;
	private boolean rendered;
	private int     color;
	
	private Map map;
	
	public Cursor(int width, int height, int color, Map map)
	{
		this.width  = width;
		this.height = height;
		
		this.color  = color;
		
		x = 0;
		y = 0;
		
		this.map = map;
	}
	
	public void clear(PixelPanel pixels)
	{
		if (!rendered)
		{
			pixels.getPixelGraphics().drawRect((int)oldRenderX, (int)oldRenderY, width, height, 0x00000000);
		}
	}
	
	public void render(PixelPanel pixels)
	{
		render(pixels, x, y);
	}
	
	public void render(PixelPanel pixels, float x, float y)
	{
		if (!rendered)
		{
			pixels.getPixelGraphics().drawRect((int)x, (int)y, width, height, color);
			
			oldRenderX = x;
			oldRenderY = y;
			
			rendered   = true;
		}
	}
	
	public void move(int dx, int dy)
	{
		if (dx == 0 && dy == 0) return;
		
		x += dx;
		y += dy;
		
		rendered = false;
	}
	
	public void setX(float x)
	{
		if (this.x == x) return;
		
		this.x = x;
		this.ax = x - map.getX();
		
		rendered = false;
	}
	
	public float getX()
	{
		return x;
	}
	
	public void setY(float y)
	{
		if (this.y == y) return;
		
		this.y = y;
		this.ay = y - map.getY();
		
		rendered = false;
	}
	
	public float getY()
	{
		return y;
	}
	
	public float getOriginRelativeX()
	{
		return ax;
	}
	
	public float getOriginRelativeY()
	{
		return ay;
	}
	
	public int getWidth()
	{
		return width;
	}
	
	public int getHeight()
	{
		return width;
	}
}