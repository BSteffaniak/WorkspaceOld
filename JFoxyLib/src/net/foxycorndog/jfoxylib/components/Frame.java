package net.foxycorndog.jfoxylib.components;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class Frame extends Panel
{
	private int		style;
	
	private Shell	shell;
	
	public Frame()
	{
		shell = new Shell(style);
		
		create(shell);
	}
	
	public void setResizable(boolean resizable)
	{
		if (resizable)
		{
			style |= SWT.RESIZE;
		}
		else
		{
			
		}
	}
	
	public void setMaximizable(boolean maximizable)
	{
		if (maximizable)
		{
			style |= SWT.MAX;
		}
		else
		{
			
		}
	}
	
	public void addStyle(int style)
	{
		this.style |= style;
	}
	
	public boolean isOpen()
	{
		return !shell.isDisposed() && shell.isVisible();
	}
	
	public void open()
	{
		shell = new Shell(style);
		shell.setSize(getWidth(), getHeight());
		
		shell.open();
	}
	
	public void close()
	{
		if (shell.isDisposed()) return;
		
		shell.close();
	}
}