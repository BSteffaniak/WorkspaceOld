package net.foxycorndog.jfoxylib.components;

import java.util.ArrayList;

public class Component
{
	private int x, y;
	private int width, height;
	private int hAlignment, vAlignment;
	
	private ArrayList<Panel> parents;
	
	public static final int LEFT = 0, CENTER = 1, RIGHT = 2, BOTTOM = 0, TOP = 2;
	
	public Component()
	{
		parents = new ArrayList<Panel>();
	}
	
	public void setLocation(int x, int y)
	{
		this.x = x;
		this.y = y;
	}
	
	public int getWidth()
	{
		return width;
	}

	public int getHeight()
	{
		return height;
	}
	
	public void setSize(int width, int height)
	{
		this.width  = width;
		this.height = height;
	}
	
	public void setAlignment(int horizontal, int vertical)
	{
		this.hAlignment = horizontal;
		this.vAlignment = vertical;
	}
	
	public void addTo(Panel panel)
	{
		panel.add(this);
		parents.add(panel);
	}
	
	public void removeFrom(Panel panel)
	{
		panel.remove(this);
		parents.remove(panel);
	}
}