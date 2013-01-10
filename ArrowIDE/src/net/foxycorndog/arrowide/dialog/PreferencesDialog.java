package net.foxycorndog.arrowide.dialog;

import java.util.ArrayList;
import java.util.HashMap;

import net.foxycorndog.arrowide.treemenu.TreeMenu;
import net.foxycorndog.arrowide.treemenu.TreeMenuListener;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Widget;

public class PreferencesDialog implements Dialog
{
	private int											currentPanelId;

	private Button										apply, ok, cancel;

	private Shell										window;

	private TreeMenu									treeMenu;

	private HashMap<Integer, PreferencesDialogPanel>	panels;

	public PreferencesDialog(Shell parent)
	{
		panels = new HashMap<Integer, PreferencesDialogPanel>();
		
		createWindow();
		
		currentPanelId = 1;
	}
	
	public void addPreferencesDialogPanel(PreferencesDialogPanel panel)
	{
		panels.put(treeMenu.addItem(panel.getTitle()), panel);
	}
	
	private void createWindow()
	{
		Rectangle bounds = Display.getDefault().getPrimaryMonitor().getBounds();
		
		window = new Shell(Display.getDefault());
		window.setSize(750, 540);
		window.setLocation(bounds.width / 2 - window.getSize().x / 2, bounds.height / 2 - window.getSize().y / 2);
		
		int width  = window.getClientArea().width;
		int height = window.getClientArea().height;
		
		window.addListener(SWT.Close, new Listener()
		{
			public void handleEvent(Event event)
			{
				window.setVisible(false);
				
				event.doit = false;
			}
		});
		
		treeMenu = new TreeMenu(window);
		treeMenu.setSize(150, 440);
		
		treeMenu.addListener(new TreeMenuListener()
		{
			public void treeItemRightClicked(int id)
			{
				
			}
			
			public void treeItemDoubleClicked(int id)
			{
				
			}

			public void treeItemSelected(int id)
			{
				panels.get(id).update();
				setActivePanel(id);
			}
		});
		
		Listener buttonListener = new Listener()
		{
			public void handleEvent(Event event)
			{
				if (event.widget == apply)
				{
					PreferencesDialogPanel panels[] = getPanels();
					
					for (int i = 0; i < panels.length; i ++)
					{
						panels[i].apply();
					}
				}
				else if (event.widget == ok)
				{
					PreferencesDialogPanel panels[] = getPanels();
					
					for (int i = 0; i < panels.length; i ++)
					{
						panels[i].apply();
					}
					
					window.setVisible(false);
				}
				else if (event.widget == cancel)
				{
					PreferencesDialogPanel panels[] = getPanels();
					
					for (int i = 0; i < panels.length; i ++)
					{
						panels[i].revert();
					}
					
					window.setVisible(false);
				}
			}
		};
		
		cancel = new Button(window, SWT.PUSH);
		cancel.setText("Cancel");
		cancel.setSize(100, 25);
		cancel.setLocation(width - cancel.getSize().x - 10, height - 50);
		cancel.addListener(SWT.Selection, buttonListener);
		
		ok = new Button(window, SWT.PUSH);
		ok.setText("OK");
		ok.setSize(100, 25);
		ok.setLocation(cancel.getLocation().x - ok.getSize().x - 10, height - 50);
		ok.addListener(SWT.Selection, buttonListener);
		
		apply = new Button(window, SWT.PUSH);
		apply.setText("Apply");
		apply.setSize(100, 25);
		apply.setLocation(ok.getLocation().x - ok.getSize().x - 10, height - 50);
		apply.addListener(SWT.Selection, buttonListener);
	}
	
	private void setActivePanel(int id)
	{
		PreferencesDialogPanel panels[] = getPanels();
		
		for (int i = 0; i < panels.length; i++)
		{
			panels[i].setVisible(false);
		}
		
		PreferencesDialogPanel panel = this.panels.get(id);
		
		panel.update();
		panel.open();
		panel.setVisible(true);
		
		currentPanelId = id;
	}
	
	private PreferencesDialogPanel[] getPanels()
	{
		return this.panels.values().toArray(new PreferencesDialogPanel[0]);
	}

	public String open()
	{
		window.setSize(750, 540);

		setActivePanel(currentPanelId);
		
		window.open();
		
		return null;
	}

	public void setText(String text)
	{

	}

	public Shell getWindow()
	{
		return window;
	}
}