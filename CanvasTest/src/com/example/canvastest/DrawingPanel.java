package com.example.canvastest;

import java.util.ArrayList;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.opengl.GLSurfaceView;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnTouchListener;

public class DrawingPanel extends SurfaceView implements SurfaceHolder.Callback
{
	PanelThread thread;
	
	public DrawingPanel(Context context)
	{
		super(context);
		getHolder().addCallback(this);
	}
	
	public void onDraw(Canvas canvas)
	{
		
	}

	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height)
	{
		
	}

	public void surfaceCreated(SurfaceHolder holder)
	{
		setWillNotDraw(false);
		
		thread = new PanelThread(getHolder(), this);
		thread.setRunning(true);
		thread.start();
	}

	public void surfaceDestroyed(SurfaceHolder holder)
	{
		try
		{
			thread.setRunning(false);
			thread.join();
		}
		catch (InterruptedException e)
		{
			System.err.println("ADFASDF");
			e.printStackTrace();
		}
	}
}