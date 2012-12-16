package net.foxycorndog.glshaderide.menubar;

import java.util.ArrayList;
import java.util.HashMap;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;

public class Menubar
{
	private Menu  menu;
	
	private Shell shell;
	
	private HashMap<String, MenuItem> headers;
	private HashMap<String, Menu>     headerMenus;
	private HashMap<String, HashMap<String, MenuItem>> subItems;
	
	public Menubar(Shell shell)
	{
		menu       = new Menu(shell, SWT.BAR);
		
		this.shell = shell;
		
		shell.setMenuBar(menu);
		
		headers      = new HashMap<String, MenuItem>();
		headerMenus  = new HashMap<String, Menu>();
		subItems     = new HashMap<String, HashMap<String, MenuItem>>();
	}
	
	public void addMenuHeader(String name)
	{
		MenuItem item = new MenuItem(menu, SWT.CASCADE);
		item.setText(name);
		
		Menu headerMenu = new Menu(shell, SWT.DROP_DOWN);
		item.setMenu(headerMenu);
		
		headers.put(name, item);
		headerMenus.put(name, headerMenu);
		subItems.put(name, new HashMap<String, MenuItem>());
	}
	
	public void addMenuSubItem(String subItemName, String headerName)
	{
		Menu headerMenu = getHeaderMenu(headerName);
		
		MenuItem item   = new MenuItem(headerMenu, SWT.PUSH);
		item.setText(subItemName);
		
		subItems.get(headerName).put(subItemName, item);
	}
	
	public MenuItem getHeaderItem(String name)
	{
		return headers.get(name);
	}
	
	public Menu getHeaderMenu(String name)
	{
		return headerMenus.get(name);
	}
	
	public MenuItem getSubItem(String headerName, String subItemName)
	{
		return subItems.get(headerName).get(subItemName);
	}
	
	public void addSelectionListener(String headerName, String subItemName, SelectionListener listener)
	{
		getSubItem(headerName, subItemName).addSelectionListener(listener);
	}
}