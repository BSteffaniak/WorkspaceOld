package net.foxycorndog.presto3d.util;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;

import org.lwjgl.opengl.Display;

public class FrameLoop
{
//	private int targetFps;
	private int dfps, fps;
	
	private long newTime, oldTime;
	
	/**
	 * Blank constructor method.
	 */
	public FrameLoop()
	{
		newTime = System.currentTimeMillis();
		oldTime = System.currentTimeMillis();
	}
	
	public void start(final int targetFps, final FrameTask task)
	{
//		new Thread()
//		{
//			public void run()
//			{
				while (!Display.isCloseRequested())
				{
					glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
					
					task.run();
					
					newTime = System.currentTimeMillis();
					
					dfps ++;
					
					if (newTime > oldTime + 1000)
					{
						fps = dfps;
						
						dfps = 0;
						
						oldTime = newTime;
					}
					
					Display.update();
					Display.sync(targetFps);
				}
				
				Display.destroy();
				System.exit(0);
//			}
//		}.start();
	}
	
	public int getFps()
	{
		return fps;
	}
}