package net.foxycorndog.thedigginggame.launcher;

import net.foxycorndog.jfoxylib.Frame;
import net.foxycorndog.jfoxylib.GameStarter;
import net.foxycorndog.jfoxylib.font.Font;
import net.foxycorndog.jfoxylib.graphics.opengl.GL;
import net.foxycorndog.thedigginggame.launcher.menu.MainMenu;

public class Launcher extends GameStarter
{
	private Font		font;
	
	private MainMenu	mainMenu;
	
	public static void main(String args[])
	{
		Launcher launch = new Launcher();
		
		System.out.println("done.");
	}
	
	public Launcher()
	{
		Frame.create(800, 600);
		
		start();
	}
	
	public void startGame()
	{
		mainMenu.dispose();
	}

	public void init()
	{
		font = new Font("res/images/fonts/font.png", 26, 4,
				new char[]
				{
					'A', 'B', 'C', 'D', 'E', 'F',  'G', 'H', 'I', 'J', 'K', 'L',  'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
					'a', 'b', 'c', 'd', 'e', 'f',  'g', 'h', 'i', 'j', 'k', 'l',  'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
					'0', '1', '2', '3', '4', '5',  '6', '7', '8', '9', '_', '-',  '+', '=', '~', '`', '!', '@', '#', '$', '%', '^', '&', '*', '(', ')',
					'?', '>', '<', ';', ':', '\'', '"', '{', '}', '[', ']', '\\', '|', ',', '.', '/', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '
				});
		
		mainMenu = new MainMenu(font, this, null);
	}

	public void render2D()
	{
		GL.scale(3, 3, 1);
		mainMenu.render();
	}

	public void render3D()
	{
		
	}

	public void loop()
	{
		
	}
}