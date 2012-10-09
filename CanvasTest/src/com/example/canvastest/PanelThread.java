package com.example.canvastest;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class PanelThread extends Thread
{
	private SurfaceHolder surfaceHolder;
	
	private DrawingPanel panel;
	
	private boolean run;
	
	public PanelThread(SurfaceHolder surfaceHolder, DrawingPanel panel)
	{
		this.surfaceHolder = surfaceHolder;
		this.panel         = panel;
	}
	
	public void setRunning(boolean run)
	{
		this.run = run;
	}
	
	public void run()
	{
		Canvas c;
		
		while (run)
		{
			c = null;
			
			try
			{
				c = surfaceHolder.lockCanvas(null);
				
				synchronized (surfaceHolder)
				{
					// insert methods to modify positions of items in ondraw..
//					postInvalidate();
				}
			}
			finally
			{
				if (c != null)
				{
					surfaceHolder.unlockCanvasAndPost(c);
				}
			}
		}
	}
}