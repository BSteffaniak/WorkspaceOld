package net.foxycorndog.jdoutil;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_STENCIL_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;

import org.lwjgl.opengl.Display;

public class FrameLoop
{
	public void start(final int targetFps, final FrameTask task)
	{
		Display.setVSyncEnabled(false);
		
		while (!Display.isCloseRequested())
		{
			task.run();

			Display.sync(targetFps);
			Display.update();
		}
		
		Display.destroy();
		System.exit(0);
	}
}