package net.foxycorndog.glshaderide.toolbar;

import java.util.HashMap;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.swt.widgets.ToolBar;

public class Toolbar
{
	private Composite composite;
	
	private ToolBar toolbar;
	
	private Shell shell;
	
	private HashMap<String, ToolItem>     toolItems;
	private HashMap<String, HashMap<String, ToolItem>> subItems;
	
	public Toolbar(Shell shell)
	{
		this.shell   = shell;
		
		composite    = new Composite(shell, SWT.NONE | SWT.RIGHT);
		composite.setLayout(new GridLayout(4, false));
		composite.setSize(shell.getClientArea().width, 25);
		
		toolbar      = new ToolBar(composite, SWT.HORIZONTAL | SWT.RIGHT);
		toolbar.setLayoutData(new GridData(SWT.RIGHT, SWT.TOP, true, true, 1, 1));
		toolbar.setSize(100, 250);
		
		toolItems    = new HashMap<String, ToolItem>();
		subItems     = new HashMap<String, HashMap<String, ToolItem>>();
	}
	
	public void addToolItem(String name)
	{
		ToolItem item = new ToolItem(toolbar, SWT.PUSH | SWT.RIGHT);
		item.setText(name);
		
		
		toolItems.put(name, item);
		subItems.put(name, new HashMap<String, ToolItem>());
	}
	
	public void addToolItem(String name, Image image)
	{
		ToolItem item = new ToolItem(toolbar, SWT.PUSH | SWT.RIGHT);
		item.setImage(image);
		
		
		toolItems.put(name, item);
		subItems.put(name, new HashMap<String, ToolItem>());
	}
	
	public void addSeparator()
	{
		new ToolItem(toolbar, SWT.SEPARATOR);
	}
	
//	public void addMenuSubItem(String subItemName, String headerName)
//	{
//		Menu headerMenu = getHeaderMenu(headerName);
//		
//		MenuItem item   = new MenuItem(headerMenu, SWT.PUSH);
//		item.setText(subItemName);
//		
//		subItems.get(headerName).put(subItemName, item);
//	}
	
	public void setToolItemImage(String itemName, Image image)
	{
//		Image i = new Image
		
		getToolItem(itemName).setImage(image);
	}
	
	public ToolItem getToolItem(String itemName)
	{
		return toolItems.get(itemName);
	}
	
	public void addSelectionListener(String itemName, SelectionListener listener)
	{
		getToolItem(itemName).addSelectionListener(listener);
	}
	
	public void setSize(int width, int height)
	{
		composite.setSize(width, height);
	}
	
	public int getWidth()
	{
		return composite.getBounds().width;
	}
	
	public void setWidth(int width)
	{
		composite.setSize(width, getHeight());
	}

	public int getHeight()
	{
		return composite.getBounds().height;
	}
	
	public void setHeight(int height)
	{
		composite.setSize(getWidth(), height);
	}
	
	public void setLocation(int x, int y)
	{
		composite.setLocation(x, y);
	}
}