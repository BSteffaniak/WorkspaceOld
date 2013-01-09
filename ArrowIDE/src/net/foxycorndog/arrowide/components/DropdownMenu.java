package net.foxycorndog.arrowide.components;

import java.util.HashMap;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;

public class DropdownMenu
{
	private Combo dropdown;
	
	private HashMap<String, Integer> ids;
	
	private static int staticId;
	
	public DropdownMenu(Composite parent)
	{
		dropdown = new Combo(parent, SWT.DROP_DOWN | SWT.BORDER | SWT.READ_ONLY);
		
		ids = new HashMap<String, Integer>();
	}
	
	public void setLocation(int x, int y)
	{
		dropdown.setLocation(x, y);
	}
	
	public void setLocation(Point location)
	{
		dropdown.setLocation(location.x, location.y);
	}
	
	public void setSize(int width, int height)
	{
		dropdown.setSize(width, height);
	}
	
	public void setSize(Point size)
	{
		dropdown.setSize(size.x, size.y);
	}
	
	public void setSelection(String s)
	{
		dropdown.select(ids.get(s));
	}
	
	public int addItem(String name)
	{
		int id = ++staticId;
		
		dropdown.add(name);
		
		ids.put(name, id);
		
		return id;
	}
}