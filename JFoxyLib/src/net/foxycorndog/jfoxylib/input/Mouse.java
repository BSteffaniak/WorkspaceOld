package net.foxycorndog.jfoxylib.input;

import static org.lwjgl.input.Mouse.*;

import java.awt.AWTException;
import java.awt.MouseInfo;
import java.awt.PointerInfo;
import java.awt.Robot;
import java.util.ArrayList;

import net.foxycorndog.jfoxylib.Frame;
import net.foxycorndog.jfoxylib.components.Panel;
import net.foxycorndog.jfoxylib.events.MouseEvent;
import net.foxycorndog.jfoxylib.listeners.MouseListener;
import net.foxycorndog.jfoxylib.util.Intersects;

import org.lwjgl.LWJGLException;

public class Mouse
{	
	private static org.lwjgl.input.Mouse	mouse;
	
	static
	{
		try
		{
			mouse.create();
		}
		catch (LWJGLException e)
		{
			e.printStackTrace();
		}
	}
	
	private static boolean			grabbed;
	
	private static int				oldX, oldY;
	private static int				dx, dy;
	private static int				dWheel;
	private static int				forcedX, forcedY;
	
	private static PointerInfo		pointerInfo;
	
	private static Robot			robot;
	
	private static MouseListener	grabListener;
	
	private static boolean			able[];
	
	private static ArrayList<MouseListener> listeners;
	
	public  static final int BUTTON_0 = 0;
	public  static final int BUTTON_1 = 1;
	public  static final int BUTTON_2 = 2;
	
	public  static final int LEFT_MOUSE_BUTTON  = BUTTON_0;
	public  static final int RIGHT_MOUSE_BUTTON = BUTTON_1;
	public  static final int MOUSE_WHEEL_BUTTON = BUTTON_2;
	
	static
	{
		listeners = new ArrayList<MouseListener>();
		
		try
		{
			robot = new Robot();
		}
		catch (AWTException e)
		{
			e.printStackTrace();
		}
		
		grabListener = new MouseListener()
		{
			public void mousePressed(MouseEvent event)
			{
				
			}

			public void mouseReleased(MouseEvent event)
			{
				
			}

			public void mouseMoved(MouseEvent event)
			{
//				oldX = event.getX();
//				oldY = event.getY();

				int x = oldX;
				int y = oldY;
				
				setLocation(Frame.getX() + Frame.getWidth() / 2 + 3, Frame.getY() + Frame.getHeight() / 2 + 25);
				
				int offX = x - getX();
				int offY = y - getY();
				
//				dx -= offX;
//				dy -= offY;
				
				oldX -= offX;
				oldY -= offY;
			}

			public void mouseDown(MouseEvent event)
			{
				
			}

			public void mouseUp(MouseEvent event)
			{
				
			}
		};
		
		able = new boolean[3];
		
		for (int i = 0; i < able.length; i++)
		{
			able[i] = true;
		}
		
		pointerInfo = MouseInfo.getPointerInfo();
	}
	
	public static void setLocation(int x, int y)
	{
		robot.mouseMove(x, y);
		
		int offX = x - getX();
		int offY = y - getY();
		
		pointerInfo = MouseInfo.getPointerInfo();
		
		System.out.println(getX() + ", " + getY());
		
		forcedX = offX;
		forcedY = offY;
		
		System.out.println(offX + ", " + offY);
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
//		System.out.println("dx: " + dx);
		return dx;
	}
	
	public static int getDY()
	{
//		System.out.println("dy: " + dy);
		return dy;
	}
	
	public static boolean isButtonDown(int button)
	{
		return mouse.isButtonDown(button);
	}
	
	public static boolean isGrabbed()
	{
		return grabbed;
	}
	
	public static void setGrabbed(boolean grabbed)
	{
		if (grabbed != mouse.isGrabbed())
		{
			if (grabbed)
			{
				listeners.add(grabListener);
			}
			else
			{
				listeners.remove(grabListener);
			}
			
			Mouse.grabbed = grabbed;
		}
	}
	
	public static int getDWheel()
	{
		return dWheel;
	}
	
	public static boolean[] getButtons()
	{
		return new boolean[] { mouse.isButtonDown(BUTTON_0), mouse.isButtonDown(BUTTON_1), mouse.isButtonDown(BUTTON_2) };
	}
	
	public static void update()
	{
		pointerInfo = MouseInfo.getPointerInfo();
		
		dx = mouse.getX() - oldX + forcedX;
		dy = mouse.getY() - oldY + forcedY;
		
		dWheel = mouse.getDWheel();
		
		if (dx != 0 || dy != 0)
		{
			for (int i = listeners.size() - 1; i >= 0; i--)
			{
				MouseEvent event = new MouseEvent(getX(), getY(), -1);
				
				listeners.get(i).mouseMoved(event);
			}
		}
		
		forcedX = 0;
		forcedY = 0;
		
		for (int button = 0; button < 3; button++)
		{
			if (Mouse.isButtonDown(button))
			{
				if (able[button])
				{
					for (int i = listeners.size() - 1; i >= 0; i--)
					{
						MouseEvent event = new MouseEvent(getX(), getY(), button);
						
						listeners.get(i).mousePressed(event);
					}
					
					able[button] = false;
				}
				
				for (int i = listeners.size() - 1; i >= 0; i--)
				{
					MouseEvent event = new MouseEvent(getX(), getY(), button);
					
					listeners.get(i).mouseDown(event);
				}
			}
			else
			{
				if (!able[button])
				{
					for (int i = listeners.size() - 1; i >= 0; i--)
					{
						MouseEvent event = new MouseEvent(getX(), getY(), button);
						
						listeners.get(i).mouseReleased(event);
					}
				}
				
				able[button] = true;
				
				for (int i = listeners.size() - 1; i >= 0; i--)
				{
					MouseEvent event = new MouseEvent(getX(), getY(), button);
					
					listeners.get(i).mouseUp(event);
				}
			}
		}
		
		oldX = mouse.getX();
		oldY = mouse.getY();
		
		mouse.next();
	}
	
	public static void addMouseListener(MouseListener listener)
	{
		listeners.add(listener);
	}
}