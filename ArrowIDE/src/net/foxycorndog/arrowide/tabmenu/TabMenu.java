package net.foxycorndog.arrowide.tabmenu;

import java.util.HashMap;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;

public class TabMenu
{
	private int                       maxCharacters;
	
	private TabFolder                 tabFolder;
	
	private HashMap<Integer, TabItem> tabs;
	
	private static int                staticId;
	
	public TabMenu(Composite composite)
	{
		tabFolder = new TabFolder(composite, SWT.NONE);
		
		TabItem tab = new TabItem(tabFolder, SWT.NONE);
		tabFolder.setSize(0, tab.getBounds().height + 3);
		
		tabs = new HashMap<Integer, TabItem>();
		
		tabs.put(staticId++, tab);
		
		maxCharacters = 15;
	}
	
	public void setTabText(int id, String text)
	{
		if (text.length() > maxCharacters)
		{
			text = text.substring(0, maxCharacters - 3) + "...";
		}
		
		TabItem item = tabs.get(id);
		
		item.setText(text);
	}
	
	public int addTab(String text)
	{
		int id = staticId++;
		
		TabItem item = new TabItem(tabFolder, SWT.NONE);
		item.setText(text);
		
		tabs.put(id, item);
		
		return id;
	}
	
	public Color getBackground()
	{
		return tabFolder.getBackground();
	}
	
	public int getHeight()
	{
		return tabFolder.getSize().y;
	}
	
	public void setWidth(int width)
	{
		tabFolder.setSize(width, tabFolder.getSize().y);
	}
	
	public void setLocation(int x, int y)
	{
		tabFolder.setLocation(x, y);
	}
	
	public int getX()
	{
		return tabFolder.getBounds().x;
	}
	
	public int getY()
	{
		return tabFolder.getBounds().y;
	}
	
	public void setMaxCharacters(int maxCharacters)
	{
		this.maxCharacters = maxCharacters;
	}
}