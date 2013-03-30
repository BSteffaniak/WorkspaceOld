import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import net.foxycorndog.jfoxylib.Frame;
import net.foxycorndog.jfoxylib.GameStarter;
import net.foxycorndog.jfoxylib.bundle.Bundle;
import net.foxycorndog.jfoxylib.components.Component;
import net.foxycorndog.jfoxylib.font.Font;
import net.foxycorndog.jfoxylib.graphics.Texture;
import net.foxycorndog.jfoxylib.graphics.opengl.GL;

/**
 * 
 * 
 * @author	Braden Steffaniak
 * @since	Feb 15, 2013 at 11:45:08 PM
 * @since	v1.0
 * @version	v1.0
 */
public class Game extends GameStarter
{
	private int		fps, flash;
	private int		length;
	
	private float	rot;
	
	private long	startTime;
	
	private Font	font;
	
	private Texture	stone;
	
	private Bundle	crazyStuff;
	
	public static void main(String args[])
	{
		new Game();
		
		System.out.println("done");
	}
	
	public Game()
	{
		Frame.create(800, 600);
		Frame.setResizable(true);
		Frame.setTargetFPS(100);
		
		start();
	}
	
	private void createMainMenu()
	{
		
	}
	
	private void createGame()
	{
		BufferedImage image = null;
		
		try
		{
			image = ImageIO.read(new File("res/images/background.png"));
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
		stone = new Texture(image);
		
		crazyStuff = new Bundle(4, 2, true, false);
		
		crazyStuff.beginEditingVertices();
		{
			crazyStuff.getVerticesBuffer().setData(0, GL.genRectVerts(0, 0, 100, 40));
		}
		crazyStuff.endEditingVertices();

		float offsets[] = stone.getImageOffsets();
		
		crazyStuff.beginEditingTextures();
		{
			crazyStuff.getTexturesBuffer().setData(0,
					new float[]
					{
						offsets[0], offsets[1],
						offsets[2], offsets[1],
						offsets[2], offsets[3],
						offsets[0], offsets[3],
					});
		}
		crazyStuff.endEditingTextures();
		
		font = new Font("res/images/fonts/font.png", 26, 4,
				new char[]
				{
					'A', 'B', 'C', 'D', 'E', 'F',  'G', 'H', 'I', 'J', 'K', 'L',  'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
					'a', 'b', 'c', 'd', 'e', 'f',  'g', 'h', 'i', 'j', 'k', 'l',  'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
					'0', '1', '2', '3', '4', '5',  '6', '7', '8', '9', '_', '-',  '+', '=', '~', '`', '!', '@', '#', '$', '%', '^', '&', '*', '(', ')',
					'?', '>', '<', ';', ':', '\'', '"', '{', '}', '[', ']', '\\', '|', ',', '.', '/', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '
				});
		
		GL.setClearColor(0, 0, 0, 1);
		
		length = 5000;
		
		startTime = System.currentTimeMillis();
	}
	
	public void init()
	{
		createGame();
	}
	
	public void render2D()
	{
		long newTime = System.currentTimeMillis();
		
		if (newTime > startTime + length)
		{
			GL.translate(Frame.getWidth() / 2, Frame.getHeight() / 2, 0);
			
	//		crazyStuff.render(GL.QUADS, stone);
			
			for (int y = 0; y < 50; y++)
			{
				GL.translate(0, 5, 0);
				GL.rotate(0, 0, rot);
				
				for (int x = 0; x < 50; x++)
				{
					GL.translate(5, 0, 0);
					crazyStuff.render(GL.QUADS, stone);
				}
				
				GL.translate(-50 * 5, 0, 0);
			}
			
	//		if (flash % 2 == 0)
	//		{
	//			GL.setClearColor(1, 1, 0, 1);
	//		}
	//		else
	//		{
	//			GL.setClearColor(0, 0, 0, 1);
	//		}
			
			GL.setClearColor((float)Math.random(), (float)Math.random(), (float)Math.random(), 1);
			
			flash++;
			
			rot += 0.19;
		}
		else
		{
//			System.out.println(Frame.getWidth());
			font.render("CAUTION: Flashing Lights. Those with epilepsy may die.", 0, 0, 0, 2, Font.CENTER, Font.CENTER, null);
		}
	}
	
	public void render3D()
	{
		
	}
	
	public void loop()
	{
		if (fps != Frame.getFPS())
		{
			fps = Frame.getFPS();
			
			Frame.setTitle("FPS: " + fps);
		}
	}
}