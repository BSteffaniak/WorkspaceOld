package net.foxycorndog.jfoxylib.components;

import java.util.ArrayList;

import org.eclipse.swt.widgets.Control;

import net.foxycorndog.jfoxylib.util.Point;

/**
 * 
 * 
 * @author	Braden Steffaniak
 * @since	Feb 15, 2013 at 11:45:20 PM
 * @since	v0.1
 * @version	v0.1
 */
public class Component
{
	private int		x, y, alignX, alignY;
	private int		width, height;
	private int		hAlignment, vAlignment;
	
	private Panel	parent;
	
	private Frame	frame;
	
	private Control	control;
//	private ArrayList<Panel> parents;
	
	public static final int LEFT = 0, CENTER = 1, RIGHT = 2, BOTTOM = 2, TOP = 0;
	
	public Component(Panel parent)
	{
		this.parent = parent;
		
		setFrame();
	}
	
	public void setControl(Control control)
	{
		this.control = control;
	}
	
	public int getX()
	{
		return x + alignX;
	}

	public int getY()
	{
		return y + alignY;
	}
	
	public void setLocation(int x, int y)
	{
		this.x = x;
		this.y = y;
		
		if (control != null)
		{
			control.setLocation(x + alignX, y + alignY);
		}
	}
	
	public Panel getParent()
	{
		return parent;
	}
	
	public void setParent(Panel parent)
	{
		this.parent = parent;
		
		setFrame();
	}
	
	public Frame getFrame()
	{
		return frame;
	}
	
	private void setFrame()
	{
		Component comp = parent;
		
		while (comp != null)
		{
			if (comp instanceof Frame)
			{
				frame = (Frame)comp;
				
				break;
			}
			
			comp = parent.getParent();
		}
	}
	
	public int getDisplayX()
	{
		int x = 0;
		
		Component comp = this;
		
		while (comp != null)
		{
			if (comp instanceof Frame)
			{
				x += ((Frame)comp).getViewBounds().getX();
			}
			else
			{
				x += comp.getX();
			}
			
			comp = comp.getParent();
		}
		
		return x;
	}
	
	public int getDisplayY()
	{
		int y = 0;
		
		Component comp = this;
		
		while (comp != null)
		{
			if (comp instanceof Frame)
			{
				y += ((Frame)comp).getViewBounds().getY();
			}
			else
			{
				y += comp.getY();
			}
			
			comp = comp.getParent();
		}
		
		return y;
	}
	
	private void align()
	{
		if (hAlignment == CENTER)
		{
			alignX = (parent.getWidth() / 2 - getWidth() / 2);
		}
		else if (hAlignment == RIGHT)
		{
			alignX = (parent.getWidth() - getWidth());
		}
		
		if (vAlignment == CENTER)
		{
			alignY = (parent.getHeight() / 2 - getHeight() / 2);
		}
		else if (vAlignment == BOTTOM)
		{
			alignY = (parent.getHeight() - getHeight());
		}
		
		if (control != null)
		{
			control.setLocation(x + alignX, y + alignY);
		}
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
		
		if (control != null)
		{
			control.setSize(width, height);
		}
		
		align();
	}
	
	public void setAlignment(int horizontal, int vertical)
	{
		this.hAlignment = horizontal;
		this.vAlignment = vertical;
		
		align();
	}
	
	public void setVisible(boolean visible)
	{
		if (control != null)
		{
			control.setVisible(false);
		}
	}
	
	public void update()
	{
		
	}
}