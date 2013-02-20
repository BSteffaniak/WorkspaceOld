package net.foxycorndog.jfoxylib.input;

import static org.lwjgl.input.Mouse.*;

import java.awt.AWTException;
import java.awt.MouseInfo;
import java.awt.PointerInfo;
import java.awt.Robot;

import net.foxycorndog.jfoxylib.components.Panel;
import net.foxycorndog.jfoxylib.util.Intersects;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.lwjgl.LWJGLException;

public class Mouse
{
	private static boolean		pressed[], next[];
	
	private static int			oldX, oldY, forcedX, forcedY;
	private static int			mouseWheel;
	
	private static PointerInfo	pointerInfo;
	
	private static Robot		robot;
	
	static
	{
		pressed = new boolean[MouseInfo.getNumberOfButtons()];
		
		next = new boolean[pressed.length];
		
		pointerInfo = MouseInfo.getPointerInfo();
		
		Listener listener = new Listener()
		{
			public void handleEvent(Event event)
			{
				if (event.type == SWT.MouseDown)
				{
					pressed[event.button - 1] = true;
				}
				else if (event.type == SWT.MouseUp)
				{
					pressed[event.button - 1] = false;
				}
				else if (event.type == SWT.MouseWheel)
				{
					mouseWheel = event.count;
				}
			}
		};
		
		Display.getDefault().addFilter(SWT.MouseDown, listener);
		Display.getDefault().addFilter(SWT.MouseUp, listener);
		Display.getDefault().addFilter(SWT.MouseWheel, listener);
		
		try
		{
			robot = new Robot();
		}
		catch (AWTException e)
		{
			e.printStackTrace();
		}
		
		update();
	}
	
	public static boolean buttonPressed(int buttonId)
	{
		return pressed[buttonId];
	}
	
	public static boolean next(int keyId)
	{
		boolean nxt = pressed[keyId] && next[keyId];
		
		return nxt;
	}
	
	public static int getX()
	{
		return pointerInfo.getLocation().x;
	}
	
	public static int getY()
	{
		return pointerInfo.getLocation().y;
	}
	
	public static int getDX()
	{
		return pointerInfo.getLocation().x - oldX;
	}
	
	public static int getDY()
	{
		return pointerInfo.getLocation().y - oldY;
	}
	
	public static int getDWheel()
	{
		return mouseWheel;
	}
	
	public static void setLocation(int x, int y)
	{
		forcedX = x - getX();
		forcedY = y - getY();
		
		robot.mouseMove(x, y);
	}
	
	public static boolean inside(Panel panel)
	{
		int x = panel.getDisplayX();
		int y = panel.getDisplayY();
		
		return Intersects.rectangles(pointerInfo.getLocation().x, pointerInfo.getLocation().y, 1, 1, x, y, panel.getWidth(), panel.getHeight());
	}
	
	public static void update()
	{
		oldX        = pointerInfo.getLocation().x + forcedX;
		oldY        = pointerInfo.getLocation().y + forcedY;
		
		forcedX     = 0;
		forcedY     = 0;
		
		mouseWheel  = 0;
		
		pointerInfo = MouseInfo.getPointerInfo();
		
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