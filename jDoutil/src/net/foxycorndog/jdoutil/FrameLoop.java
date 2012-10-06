package net.foxycorndog.jdoutil;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_STENCIL_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;

import org.lwjgl.opengl.Display;

public class FrameLoop
{
	private int targetFps;
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
		this.targetFps = targetFps;
		
		Display.setVSyncEnabled(false);
		
		while (!Display.isCloseRequested())
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
			

			Display.sync(targetFps);
			Display.update();
		}
		
		Display.destroy();
		System.exit(0);
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