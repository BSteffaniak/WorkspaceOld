package net.foxycorndog.arrowide.menubar;

import java.util.ArrayList;
import java.util.HashMap;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
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
	
	private ArrayList<MenubarListener> listeners;
	
	private HashMap<String, MenuItem>  headers;
	private HashMap<String, Menu>      headerMenus;
	private HashMap<String, HashMap<String, MenuItem>> subItems;
	private HashMap<String, HashMap<String, Menu>>     subMenus;
	
	private static int staticId;
	
	public Menubar(Shell shell)
	{
		menu       = new Menu(shell, SWT.BAR);
		
		this.shell = shell;
		
		shell.setMenuBar(menu);
		
		headers      = new HashMap<String, MenuItem>();
		headerMenus  = new HashMap<String, Menu>();
		subItems     = new HashMap<String, HashMap<String, MenuItem>>();
		subMenus     = new HashMap<String, HashMap<String, Menu>>();
		
		listeners    = new ArrayList<MenubarListener>();
	}
	
	public int addMenuHeader(String name)
	{
		int id = staticId++;
		
		MenuItem item = new MenuItem(menu, SWT.CASCADE);
		item.setText(name);
		
		Menu headerMenu = new Menu(shell, SWT.DROP_DOWN);
		item.setMenu(headerMenu);
		
		headers.put(name, item);
		headerMenus.put(name, headerMenu);
		subItems.put(name, new HashMap<String, MenuItem>());
		subMenus.put(name, new HashMap<String, Menu>());
		
		return id;
	}
	
	public int addMenuSubItem(final String subItemName, String headerName)
	{
		int id = staticId++;
		
		Menu headerMenu = getHeaderMenu(headerName);
		
		MenuItem item   = new MenuItem(headerMenu, SWT.CASCADE);
		item.setText(subItemName);
		
		Menu menu = new Menu(headerMenu);
		
//		MenuItem item2 = new MenuItem(menu, SWT.PUSH);
//		item2.setText("asdf");
		
		subItems.get(headerName).put(subItemName, item);
		subMenus.get(headerName).put(subItemName, menu);
		
		item.addSelectionListener(new SelectionListener()
		{
			@Override
			public void widgetDefaultSelected(SelectionEvent e)
			{
				for (int i = listeners.size() - 1; i >= 0; i --)
				{
					listeners.get(i).subItemPressed(subItemName);
				}
			}

			@Override
			public void widgetSelected(SelectionEvent e)
			{
				widgetDefaultSelected(e);
			}
		});
		
		return id;
	}
	
	public void addSeparator(String headerName)
	{
		Menu headerMenu = getHeaderMenu(headerName);
		
		MenuItem item   = new MenuItem(headerMenu, SWT.SEPARATOR);
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
	
//	public void addSelectionListener(String headerName, String subItemName, SelectionListener listener)
//	{
//		getSubItem(headerName, subItemName).addSelectionListener(listener);
//	}
	
	public void addListener(MenubarListener listener)
	{
		listeners.add(listener);
	}
}