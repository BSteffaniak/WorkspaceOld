package net.foxycorndog.jfoxylib.components;

import java.util.ArrayList;

import net.foxycorndog.jfoxylib.events.MoveEvent;
import net.foxycorndog.jfoxylib.listeners.MoveListener;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.ControlListener;
import org.eclipse.swt.events.DragDetectEvent;
import org.eclipse.swt.events.DragDetectListener;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.GestureEvent;
import org.eclipse.swt.events.GestureListener;
import org.eclipse.swt.events.MenuDetectEvent;
import org.eclipse.swt.events.MenuDetectListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.events.ShellListener;
import org.eclipse.swt.events.TouchEvent;
import org.eclipse.swt.events.TouchListener;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;

/**
 * 
 * 
 * @author	Braden Steffaniak
 * @since	Feb 15, 2013 at 11:43:17 PM
 * @since	v0.1
 * @version	v0.1
 */
public class Frame extends Panel
{
	private int				style;
	
	private Panel			contentPanel;
	
	private Shell			shell;
	
	private Listener		moveListener;
	
	private ArrayList<MoveListener>	moveListeners;
	
	public Frame()
	{
		style = SWT.NONE;
		
		moveListeners = new ArrayList<MoveListener>();
		
		moveListener = new Listener()
		{
			public void handleEvent(Event event)
			{
				for (int i = moveListeners.size() - 1; i >= 0; i--)
				{
					MoveEvent e = new MoveEvent();
					
					moveListeners.get(i).moved(e);
				}
			}
		};
		
		recreateShell();
		
		contentPanel = new Panel(this);
	}
	
	private void recreateContentArea()
	{
		Rectangle area = shell.getClientArea();
		
		contentPanel.setLocation(area.x, area.y);
		contentPanel.setSize(area.width, area.height);
	}
	
	private void recreateShell()
	{
		shell = new Shell(style);
		
		create(shell);
		
		shell.addListener(SWT.Move, moveListener);
		
		if ((style & SWT.RESIZE) != 0)
		{
			shell.addListener(SWT.Resize, new Listener()
			{
				public void handleEvent(Event e)
				{
					setSize(shell.getSize().x, shell.getSize().y);
					recreateContentArea();
					update();
				}
			});
		}
	}
	
	public void setResizable(boolean resizable)
	{
		if (resizable && (style & SWT.RESIZE) == 0)
		{
			addStyle(SWT.RESIZE);
		}
		else
		{
			
		}
	}
	
	public void addMoveListener(MoveListener listener)
	{
		moveListeners.add(listener);
	}
	
	public void removeMoveListener(MoveListener listener)
	{
		moveListeners.remove(listener);
	}
	
	public Panel getContentPanel()
	{
		return contentPanel;
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
		
		recreateShell();
		
		shell.setSize(getWidth(), getHeight());
		
		recreateContentArea();
	}
	
	public boolean isOpen()
	{
		return !shell.isDisposed() && shell.isVisible();
	}
	
	public void setSize(int width, int height)
	{
		super.setSize(width, height);
		shell.setSize(width, height);
		
		recreateContentArea();
	}
	
	public void open()
	{
		shell.open();
	}
	
	public void close()
	{
		if (shell.isDisposed())
		{
			return;
		}
		
		shell.close();
	}
}