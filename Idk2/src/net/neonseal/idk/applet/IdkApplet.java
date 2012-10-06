package net.neonseal.idk.applet;

import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.Canvas;

import net.neonseal.idk.Idk;

public class IdkApplet extends Applet
{
	Canvas	drawCanvas;
	
	Idk     idk;
	
//	/** Thread which runs the main game loop */
//	Thread	gameThread;
	
	/** is the game loop running */
	boolean	running	= false;
	
	public void startLWJGL()
	{
//		gameThread = new Thread()
//		{
//			public void run()
//			{
//				running = true;
//				try
//				{
//					Display.setParent(drawCanvas);
//					Display.create();
//					initGL();
//				}
//				catch (LWJGLException e)
//				{
//					e.printStackTrace();
//					return;
//				}
//				gameLoop();
//			}
//		};
//		gameThread.start();
		
		idk.start(drawCanvas);
	}
	
	/**
	 * Tell game loop to stop running, after which the LWJGL Display will be
	 * destroyed. The main thread will wait for the Display.destroy().
	 */
	private void stopLWJGL()
	{
		running = false;
		
		try
		{
			idk.getGameThread().join();
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
	}
	
	public void start()
	{
		
	}
	
	public void stop()
	{
		
	}
	
	/**
	 * Applet Destroy method will remove the canvas, before canvas is destroyed
	 * it will notify stopLWJGL() to stop the main game loop and to destroy the
	 * Display
	 */
	public void destroy()
	{
//		remove(drawCanvas);
		super.destroy();
		System.exit(0);
	}
	
	public void init()
	{
//		System.setProperty("org.lwjgl.librarypath", System.getProperty("user.dir") + "/native");
//		System.setProperty("org.lwjgl.librarypath", this.getCodeBase().toExternalForm() + "" + "native");
//		
//		System.out.println(System.getProperty("user.dir") + " ANNNNND " + System.getProperty("org.lwjgl.librarypath"));
		
		Idk.prefix = "";
		
		Idk.init();
		
		idk = new Idk();
		
		
		setLayout(new BorderLayout());
		try
		{
			drawCanvas = new Canvas()
			{
				public final void addNotify()
				{
					super.addNotify();
					startLWJGL();
				}
				
				public final void removeNotify()
				{
					stopLWJGL();
					super.removeNotify();
				}
			};
			
			setSize(640, 512);
			
			drawCanvas.setSize(getWidth(), getHeight());
			add(drawCanvas);
			drawCanvas.setFocusable(true);
			drawCanvas.requestFocus();
			drawCanvas.setIgnoreRepaint(true);
			setVisible(true);
		}
		catch (Exception e)
		{
			System.err.println(e);
			throw new RuntimeException("Unable to create display");
		}
	}
	
	protected void initGL()
	{
		
	}
	
//	public void gameLoop()
//	{
//		while (running)
//		{
//			Display.sync(60);
//			
//			
//			
//			Display.update();
//		}
//		
//		Display.destroy();
//	}
}