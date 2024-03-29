package net.foxycorndog.jdoogl.activity;

import org.lwjgl.opengl.GL11;

import net.foxycorndog.jdobase.Base;
import net.foxycorndog.jdoogl.GL;
import net.foxycorndog.jdoogl.components.Frame;
import net.foxycorndog.jdoogl.components.Frame.GameRenderer;
import net.foxycorndog.jdoogl.input.KeyboardInput;
import net.foxycorndog.jdoutil.FrameLoop;
import net.foxycorndog.jdoutil.FrameTask;

public abstract class GameComponent
{
	private GameRenderer     gameRenderer;
	
	private GameComponent    thisGameComponent;
	
	public GameComponent(String title, int width, int height, int targetFps)
	{
		onCreate(title, width, height, targetFps);
	}
	
	private void onCreate(String title, int width, int height, int targetFps)
	{
		thisGameComponent = this;
		
		gameRenderer = new GameRenderer(title, width, height)
		{
			public void render(int dfps)
			{
				GL.loadIdentity();
				GL.viewPerspective();
				
				thisGameComponent.render3D(dfps);
				
				GL.loadIdentity();
				GL.viewOrtho();
				GL11.glPushAttrib(GL11.GL_LIGHTING_BIT);
				{
					GL11.glDisable(GL11.GL_LIGHTING);
					thisGameComponent.render2D(dfps);
				}
				GL11.glPopAttrib();
			}
			
			public void loop(int dfps)
			{
				thisGameComponent.loop(dfps);
			}
		};
		
		onCreate();
		
		FrameLoop frameLoop = new FrameLoop();
		
		frameLoop.start(targetFps, new FrameTask()
		{
			public void run()
			{
				gameRenderer.onDrawFrame();
			}
		});
	}
	
	public GameRenderer getGameRenderer()
	{
		return gameRenderer;
	}
	
	public abstract void onCreate();
	
	public abstract void render2D(int dfps);
	
	public abstract void render3D(int dfps);
	
	public abstract void loop(int dfps);
}