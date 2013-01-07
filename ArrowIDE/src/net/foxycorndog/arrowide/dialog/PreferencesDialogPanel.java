package net.foxycorndog.arrowide.dialog;

import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Widget;

public class PreferencesDialogPanel extends Composite
{
	private String title;
	
	private ArrayList<Widget> widgets;

	public PreferencesDialogPanel(Composite parent, String title)
	{
		super(parent, SWT.NONE);
		
		this.title = title;
	}
	
	public String getTitle()
	{
		return title;
	}
	
	public void addWidget(Widget widget)
	{
		widgets.add(widget);
	}
}