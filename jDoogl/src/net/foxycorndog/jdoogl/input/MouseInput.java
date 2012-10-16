package net.foxycorndog.jdoogl.input;

import org.lwjgl.input.Mouse;

public class MouseInput
{
	public static final int BUTTON_0 = 0;
	public static final int BUTTON_1 = 1;
	public static final int BUTTON_2 = 2;
	
	public static final int LEFT_MOUSE_BUTTON  = BUTTON_0;
	public static final int RIGHT_MOUSE_BUTTON = BUTTON_1;
	public static final int MOUSE_WHEEL_BUTTON = BUTTON_2;
	
	public static int getX()
	{
		return Mouse.getX();
	}
	
	public static int getY()
	{
		return Mouse.getY();
	}
	
	public static int getDX()
	{
		return Mouse.getDX();
	}
	
	public static int getDY()
	{
		return Mouse.getDY();
	}
	
	public static boolean isButtonDown(int button)
	{
		return Mouse.isButtonDown(button);
	}
	
	public static boolean isGrabbed()
	{
		return Mouse.isGrabbed();
	}
	
	public static void setGrabbed(boolean grabbed)
	{
		Mouse.setGrabbed(grabbed);
	}
	
	public static int getDWheel()
	{
		return Mouse.getDWheel();
	}
	
	public static boolean[] getButtons()
	{
		return new boolean[] { Mouse.isButtonDown(BUTTON_0), Mouse.isButtonDown(BUTTON_1), Mouse.isButtonDown(BUTTON_2) };
	}
}