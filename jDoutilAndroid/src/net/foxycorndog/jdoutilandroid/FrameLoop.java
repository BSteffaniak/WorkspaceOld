package net.foxycorndog.jdoutilandroid;

import android.opengl.*;

import static android.opengl.GLES10.*;

public class FrameLoop
{
	private int targetFps;
	private int dfps, fps;
	
	public boolean running;
	
	private long newTime, oldTime;
	
	/**
	 * Blank constructor method.
	 */
	public FrameLoop()
	{
		running = true;
		
		newTime = System.currentTimeMillis();
		oldTime = System.currentTimeMillis();
	}
	
	public void start(final int targetFps, final FrameTask task)
	{
		this.targetFps = targetFps;
		
		while (!running)
		{
			glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT | GL_STENCIL_BUFFER_BIT);
			
			task.run();
			
			newTime = System.currentTimeMillis();
			
			dfps ++;
			
			if (newTime > oldTime + 1000)
			{
				fps = dfps;
				
				dfps = 0;
				
				oldTime = newTime;
			}
		}
	}
	
	public int getFps()
	{
		return fps;
	}
	
	public int getTargetFps()
	{
		return targetFps;
	}
}