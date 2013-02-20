package net.foxycorndog.jfoxylib.input;

import java.awt.DefaultKeyboardFocusManager;
import java.awt.KeyEventDispatcher;
import java.awt.KeyEventPostProcessor;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Queue;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.lwjgl.LWJGLException;

public class Keyboard
{
	private static boolean pressed[];
	private static boolean next[];
	
	public static final int ESCAPE = 27;
	
	private static ArrayList<Integer>	queue;
	
	static
	{
		queue = new ArrayList<Integer>();
		
		pressed = new boolean[256];
		next = new boolean[pressed.length];
		
		Listener listener = new Listener()
		{
			public void handleEvent(Event event)
			{
				if (event.type == SWT.KeyDown)
				{
					int code = (int)event.character;
					
					pressed[code] = true;
					
					if (!queue.contains(code))
					{
//						System.out.println("press " + event.character);
						queue.add(code);
					}
				}
				else if (event.type == SWT.KeyUp)
				{
					int code = queue.remove(0);//queue.size() - 1);
					
					pressed[code] = false;
					
//					System.out.println("release " + (char)code);
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