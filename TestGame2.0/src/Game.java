import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import net.foxycorndog.jfoxylib.GL;
import net.foxycorndog.jfoxylib.GameEntry;
import net.foxycorndog.jfoxylib.bundle.Bundle;
import net.foxycorndog.jfoxylib.components.GLPanel;
import net.foxycorndog.jfoxylib.components.Window;
import net.foxycorndog.jfoxylib.graphics.Texture;
import net.foxycorndog.jfoxylib.model.Model;

public class Game extends GameEntry
{
	private GLPanel		panel;
	
	private Window		window;
	
	public static void main(String args[])
	{
		new Game();
		
		System.out.println("done.");
	}
	
	public Game()
	{
		window = new Window();
		window.setSize(800, 600);
		window.setResizable(true);
		window.setMaximizable(true);
		
		window.open();
		
		setMainFrame(window);
		
		startGame();
		
		start();
	}
	
	public void startGame()
	{
		createGame();
	}
	
	public void createGame()
	{
		panel = new GLPanel(window.getContentPanel())
		{
			private float	rot;
			
			private Texture	stone;
			
			private Model	bunny;
			
			private Bundle	world;
			
			public void init()
			{
				rot = 0;
				
				BufferedImage image = null;
				
				try
				{
					image = ImageIO.read(new File("res/images/stone.png"));
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
				
				stone = new Texture(image);
				
				bunny = new Model("res/models/bunny.obj", 2000);
				
				world = new Bundle(4 * 6 + bunny.verticesAmount(), 3);
				
				world.setVertices(0, GL.genCubeVerts(0, 0, 0, 200, 200, 200));
				world.setVertices(4 * 6 * 3, bunny.getVertices());
			}
			
			public void render2D()
			{
				GL.translate(100, 100, 0);
				GL.rotate(0, 0, rot++);
				GL.drawRect(0, 0, 100, 100, stone);
			}
			
			public void render3D()
			{
				GL.translate(0, 0, -500);
				GL.rotate(rot, 0, 0);
//				world.render(GL.QUADS, 0, 4 * 6 * 3, stone);
				world.render(GL.TRIANGLES, 4 * 6 * 3, bunny.verticesAmount() * 3, stone);
			}
		};
		
		panel.setSize(window.getContentPanel().getWidth(), window.getContentPanel().getHeight());
		panel.setFrame(window);
	}

	public void loop()
	{
		
	}
}