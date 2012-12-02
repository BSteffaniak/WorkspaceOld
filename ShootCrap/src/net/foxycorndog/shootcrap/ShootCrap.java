package net.foxycorndog.shootcrap;

import net.foxycorndog.jdoogl.GL;
import net.foxycorndog.jdoogl.activity.GameComponent;
import net.foxycorndog.jdoogl.fonts.Font;

public class ShootCrap extends GameComponent
{
	private Font font;
	
	public static final String GAME_TITLE = "Shoot Crap";
	
	public static void main(String args[])
	{
		new ShootCrap();
	}
	
	public ShootCrap()
	{
		super(GAME_TITLE, 800, 600, -1);
	}

	public void onCreate()
	{
		font = new Font("res/images/font/font.png", 26, 4,
				new char[]
				{
					'A', 'B', 'C', 'D', 'E',  'F', 'G', 'H', 'I',  'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
					'a', 'b', 'c', 'd', 'e',  'f', 'g', 'h', 'i',  'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
					'1', '2', '3', '4', '5',  '6', '7', '8', '9',  '0', '`', '~', '!', '@', '#', '$', '%', '^', '&', '*', '(', ')', '-', '_', '=', '+',
					'[', '{', ']', '}', '\\', '|', ';', ':', '\'', '"', ',', '<', '.', '>', '/', '?', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '
				});
		
		font.setLetterMargin(0);
		
		GL.setClearColori(125, 125, 125, 255);
	}

	public void render2D(int dfps)
	{
		font.render("ASDF", 0, 0, 0, 5);
	}

	public void render3D(int dfps)
	{
		
	}

	public void loop(int dfps)
	{
		
	}
}