import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import net.foxycorndog.jfoxylib.Frame;
import net.foxycorndog.jfoxylib.GameStarter;
import net.foxycorndog.jfoxylib.bundle.Bundle;
import net.foxycorndog.jfoxylib.components.Component;
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
	private int		fps;
	
	private float	rot;
	
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
			crazyStuff.getVerticesBuffer().setData(0,
					new float[]
					{
						0, 0,
						100, 0,
						100, 100,
						0, 100
					});
		}
		crazyStuff.endEditingVertices();

		float offsets[] = stone.getImageOffsetsf();
		
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
	}
	
	public void init()
	{
		createGame();
	}
	
	public void render2D(int dfps)
	{
		GL.translate(Frame.getWidth() / 2, Frame.getHeight() / 2, 0);
		
//		crazyStuff.render(GL.QUADS, stone);
		
		for (int y = 0; y < 100; y++)
		{
			GL.translate(0, 5, 0);
			GL.rotate(0, 0, rot);
			
			for (int x = 0; x < 100; x++)
			{
				GL.translate(5, 0, 0);
				crazyStuff.render(GL.QUADS, stone);
			}
			
			GL.translate(-100 * 5, 0, 0);
		}
		
		rot += 0.09;
	}
	
	public void render3D(int dfps)
	{
		
	}
	
	public void loop(int dfps)
	{
		if (fps != Frame.getFPS())
		{
			fps = Frame.getFPS();
			
			Frame.setTitle("FPS: " + fps);
		}
	}
}