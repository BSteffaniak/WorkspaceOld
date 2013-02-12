package net.foxycorndog.jfoxylib.components;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.ControlListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
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
		if (resizable && (style & SWT.RESIZE) == 0)
		{
			addStyle(SWT.RESIZE);
			
			shell.addControlListener(new ControlListener()
			{
				public void controlResized(ControlEvent e)
				{
					setSize(shell.getSize().x, shell.getSize().y);
					update();
				}
				
				public void controlMoved(ControlEvent e)
				{
					
				}
			});
		}
		else
		{
			
		}
	}
	
	public void setMaximizable(boolean maximizable)
	{
		if (maximizable)
		{
			addStyle(SWT.MAX);
		}
		else
		{
			
		}
	}
	
	public void addStyle(int style)
	{
		this.style |= style;
		
		shell = new Shell(this.style);
		
		create(shell);
	}
	
	public boolean isOpen()
	{
		return !shell.isDisposed() && shell.isVisible();
	}
	
	public void open()
	{
		int width  = getWidth();
		int height = getHeight();
		
		shell.setSize(width, height);
		
		shell.open();
	}
	
	public void close()
	{
		if (shell.isDisposed()) return;
		
		shell.close();
	}
}