package net.foxycorndog.jfoxylibpixel;

import java.awt.Graphics2D;
import java.util.Timer;
import java.util.TimerTask;

/**
 * This class serves as the entry point for all of the
 * game functionality. Once the start() method is
 * called, the loop will run until the Frame is closed
 * or the stop() method is called.
 * 
 * @author	Braden Steffaniak
 * @since	Feb 27, 2013 at 8:31:40 PM
 * @since	v0.1
 * @version Feb 27, 2013 at 8:31:40 PM
 * @version	v0.1
 */
public abstract class GameStarter
{
	/**
	 * Initializes anything that is needed for the library.
	 */
	public GameStarter()
	{
		
	}
	
	/**
	 * Start the main game loop that renders and everything else.
	 * 
	 * @param targetFps The fps to try to achieve each frame.
	 */
	public void start(int targetFps)
	{
		int delay = 1000 / targetFps;
		
		init();
		
		new Timer().schedule(new TimerTask()
		{
			public void run()
			{
				loop();
				
				
			}
		}, delay, 1);
	}
	
	/**
	 * Method that is implemented to initialize all the game data.
	 * Called right before the main game loop is started.
	 */
	public abstract void init();
	
	/**
	 * Method that is called during each iteration of the main game
	 * loop right after the loop() call. You are supposed to do all
	 * of the rendering to PixelPanels in here.
	 */
	public abstract void render(Graphics2D g);
	
	/**
	 * Method that is called during each iteration of the main game
	 * loop right before the render() call. You are supposed to do
	 * all calculations for the game in here.
	 */
	public abstract void loop();
}