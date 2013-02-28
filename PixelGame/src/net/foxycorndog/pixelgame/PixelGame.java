package net.foxycorndog.pixelgame;

import net.foxycorndog.jfoxylibpixel.GameStarter;
import net.foxycorndog.jfoxylibpixel.components.PixelPanel;
import net.foxycorndog.jfoxylibpixel.components.Window;

public class PixelGame extends GameStarter
{
	private PixelPanel	panel;
	
	public static void main(String args[])
	{
		new PixelGame();
		
		System.out.println("done.");
	}
	
	public PixelGame()
	{
		start(60);
	}

	public void init()
	{
		Window window = new Window();
		window.setSize(800, 600);
		window.center();
		
		panel = new PixelPanel(window);
		panel.setSize(400, 300);
		
		window.setVisible(true);
	}

	public void render(Graphics2D g)
	{
		panel.render();
	}

	public void loop()
	{
		
	}
}