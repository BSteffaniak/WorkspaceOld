package net.foxycorndog.jfoxylib.input;

import java.util.HashMap;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;

public class Keyboard
{
	private static boolean pressed[];
	private static boolean next[];
	
	public static final int ESCAPE = 27;
	
	static
	{
		pressed = new boolean[256];
		next = new boolean[pressed.length];
		
		Listener listener = new Listener()
		{
			public void handleEvent(Event event)
			{
				if (event.type == SWT.KeyDown)
				{
					pressed[(int)event.character] = true;
				}
				else if (event.type == SWT.KeyUp)
				{
					pressed[(int)event.character] = false;
				}
			}
		};
		
		Display.getDefault().addFilter(SWT.KeyDown, listener);
		Display.getDefault().addFilter(SWT.KeyUp, listener);
	}
	
	public static boolean keyPressed(int keyId)
	{
		return pressed[keyId];
	}
	
	public static boolean next(int keyId)
	{
		boolean nxt = pressed[keyId] && next[keyId];
		
		return nxt;
	}
	
	public static void update()
	{
		for (int i = 0; i < pressed.length; i ++)
		{
			if (!pressed[i])
			{
				next[i] = true;
			}
			else
			{
				next[i] = false;
			}
		}
	}
}