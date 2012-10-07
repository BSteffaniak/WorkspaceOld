package net.foxycorndog.jdooglandroid.input;

import net.foxycorndog.jdooglandroid.components.Frame;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;

public class TouchInput
{
	private static final TouchListener touchListener = new TouchListener();
	
	private static class TouchListener implements OnTouchListener, OnClickListener
	{
		private boolean pressed;
		
		private float oldX, oldY;
		private float x, y;
		private float dx, dy;
		
		private int actionMasked, pointerIndex;
		
		public boolean onTouch(View v, MotionEvent e)
		{
			pressed = true;
			
			if (oldX == e.getX() && oldY == e.getY())
			{
				return false;
			}

			x = e.getX();
			y = Frame.getHeight() - e.getY() - 1;
			
			dx = x - oldX;
			dy = y - oldY;
			
			oldX = x;
			oldY = y;
			
			return false;
		}

		public void onClick(View v)
		{
			pressed = false;
		}
	}
	
	public static boolean isPressed()
	{
		return touchListener.pressed;
	}
	
//	public static boolean next()
//	{
//		if (touchListener.pressed)
//		{
//			touchListener.pressed = false;
//			
//			return true;
//		}
//		else
//		{
//			return false;
//		}
//	}
	
	public static int getX()
	{
		return (int)touchListener.x;
	}
	
	public static int getY()
	{
		return (int)touchListener.y;
	}
	
	public static int getDX()
	{
		return (int)touchListener.dx;
	}
	
	public static int getDY()
	{
		return (int)touchListener.dy;
	}
	
	public static OnTouchListener getTouchListener()
	{
		return touchListener;
	}
	
	public static OnClickListener getClickListener()
	{
		return touchListener;
	}
}