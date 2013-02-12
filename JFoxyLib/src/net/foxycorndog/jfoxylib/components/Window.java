package net.foxycorndog.jfoxylib.components;

import org.eclipse.swt.SWT;

public class Window extends Frame
{
	private String title;
	
	public Window()
	{
		addStyle(SWT.CLOSE);
		addStyle(SWT.MIN);
	}
	
	public void setTitle(String title)
	{
		this.title = title;
	}
}