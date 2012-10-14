import net.foxycorndog.jdoogl.GL;
import net.foxycorndog.jdoogl.components.Frame;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.util.glu.GLU;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

/*
 * Sets up the Display, the GL context, and runs the main game loop.
 */
public class Main
{
	private Game game;
	private boolean done = false;// game runs until done is set to true

	public Main()
	{
		new Frame(800, 600, "ASDF", null)
		{

			@Override
			public void init()
			{
				inits();
				game = new Game();
			}

			@Override
			public void loop()
			{
				game.loop();
			}

			@Override
			public void render()
			{
				game.render();
			}
			
		}.startLoop(60);
		
//		inits();
//		game = new Game();
//		
//		// game loop
//		while (!done)
//		{
//			if (Display.isCloseRequested() || Keyboard.isKeyDown(Keyboard.KEY_ESCAPE))
//			{
//				done = true;
//			}
//			
//			game.tick();
//			
//			Display.update();// do the render
//		}
//		
//		Keyboard.destroy();// cleanup
//		Mouse.destroy();
//		Display.destroy();

	}

	private void inits()
	{
//		int w = 800;
//		int h = 600;
//		try
//		{
//			Display.setDisplayMode(new DisplayMode(w, h));
//			Display.setVSyncEnabled(true);
//			Display.setTitle("Loading Animation from Blender 2.5");
//			Display.create();
//		}
//		catch (Exception e)
//		{
//			System.out.println("Error setting up display");
//			System.exit(0);
//		}
//
//		GL11.glEnable(GL11.GL_TEXTURE_2D);
//		
//		GL11.glViewport(0, 0, w, h);
//		GL11.glMatrixMode(GL11.GL_PROJECTION);
//		GL11.glLoadIdentity();
//		GLU.gluPerspective(45.0f, ((float) w / (float) h), 0.1f, 100.0f);
//		GL11.glMatrixMode(GL11.GL_MODELVIEW);
//		GL11.glLoadIdentity();
//
//		GL11.glShadeModel(GL11.GL_SMOOTH);
//		GL11.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
//		GL11.glClearDepth(1.0f);
//		GL11.glEnable(GL11.GL_DEPTH_TEST);
//		GL11.glDepthFunc(GL11.GL_LEQUAL);
//		GL11.glHint(GL11.GL_PERSPECTIVE_CORRECTION_HINT, GL11.GL_NICEST);
		
		GL.setRender3D(true);
	}

	public void quit()
	{
		done = true;
	}

	public static void main(String[] args)
	{
		Main game = new Main();
	}

}