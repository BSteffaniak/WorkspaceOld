import java.awt.Graphics2D;

import net.foxycorndog.jfoxylibpixel.GameStarter;
import net.foxycorndog.jfoxylibpixel.components.Frame;
import net.foxycorndog.jfoxylibpixel.components.PixelPanel;

public class Tetris
{
	private PixelPanel	panel;
	
	private Frame		frame;
	
	private Thread		renderThread;
	
	public Tetris()
	{
		frame = new Frame();
		frame.setSize(800, 600);
		frame.center();
		
		frame.setVisible(true);
		
		renderThread = new Thread("Render Thread")
		{
			public void run()
			{
				while (frame.isVisible())
				{
					frame.render();
					
					try
					{
						Thread.sleep(1000 / 60);
					}
					catch (InterruptedException e)
					{
						e.printStackTrace();
					}
				}
			}
		};
		
		renderThread.start();
	}
	
	public static void main(String args[])
	{
		Tetris t = new Tetris();
		
		t.loadMainMenu();
	}
	
	public void loadMainMenu()
	{
		frame.fillRect(0, 0, frame.getWidth(), frame.getHeight(), 0xff000000);
		frame.fillRect(50, 50, 100, 100, 0x77ff0000);
	}
}