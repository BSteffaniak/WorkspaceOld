package net.foxycorndog.pixelgame;

import net.foxycorndog.jfoxylibpixel.GameStarter;
import net.foxycorndog.jfoxylibpixel.components.Window;

public class PixelGame extends GameStarter
{
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
		
		window.setVisible(true);
	}

	public void render()
	{
		
	}

	public void loop()
	{
		
	}
}