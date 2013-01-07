package net.foxycorndog.arrowide.dialog;

import java.util.ArrayList;

import net.foxycorndog.arrowide.treemenu.TreeMenu;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class PreferencesDialog implements Dialog
{
	private Shell								window;

	private TreeMenu							treeMenu;

	private ArrayList<PreferencesDialogPanel>	panels;

	public PreferencesDialog(Shell parent)
	{
		panels = new ArrayList<PreferencesDialogPanel>();
		
		treeMenu = new TreeMenu(parent);
		
		window = new Shell(Display.getDefault());
		window.setSize(600, 450);
	}
	
	public void addPreferencesDialogPanel(PreferencesDialogPanel panel)
	{
		panels.add(panel);
		
		treeMenu.addItem(panel.getTitle());
	}

	public String open()
	{
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