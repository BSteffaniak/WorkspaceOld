package net.foxycorndog.thedigginggame.launcher;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

import net.foxycorndog.jfoxylib.Frame;
import net.foxycorndog.jfoxylib.GameStarter;
import net.foxycorndog.jfoxylib.font.Font;
import net.foxycorndog.jfoxylib.graphics.opengl.GL;
import net.foxycorndog.thedigginggame.GameInterface;
import net.foxycorndog.thedigginggame.launcher.menu.MainMenu;

/**
 * 
 * 
 * @author	Braden Steffaniak
 * @since	Mar 11, 2013 at 8:00:20 PM
 * @since	v0.1
 * @version Mar 11, 2013 at 8:00:20 PM
 * @version	v0.1
 */
public class Launcher extends GameStarter
{
	private Font			font;
	
	private MainMenu		mainMenu;
	
	private Method			init, loop, render;
	
	private GameInterface	gameInterface;
	
	/**
	 * The main entry point for the launcher. First method ran.
	 * Creates a launcher instance.
	 * 
	 * @param args The command line arguments.
	 */
	public static void main(String args[])
	{
		Launcher launch = new Launcher();
		
		System.out.println("done.");
	}
	
	/**
	 * Construct and start the launcher for the game.
	 */
	public Launcher()
	{
		Frame.create(800, 600);
		Frame.setVSyncEnabled(true);
		Frame.setResizable(true);
		
		start();
	}
	
	/**
	 * Locate the jar file and create an instance of it. Then start the
	 * game using it.
	 */
	public void startGame()
	{
		try
		{
			boolean debug = true;
			
			String parentDir = null;
			String jarName = null;
			
			jarName = "TDG.jar";
			
			if (debug)
			{
				parentDir = "../thedigginggame/";
//				parentDir = "../thedigginggame/bin/";
			}
			
			URL urls[] = new URL[] { new File(parentDir + jarName).toURI().toURL() };
			
			URLClassLoader loader = new URLClassLoader(urls);
			
			Class clazz = loader.loadClass("net.foxycorndog.thedigginggame.TheDiggingGame");
			
			Constructor<?> constr = clazz.getConstructor();
			
			gameInterface = (GameInterface)constr.newInstance();
			
			System.setProperty("game.resources.location", parentDir);
			
			gameInterface.init();
			
			gameInterface.startGame();
			
			mainMenu.dispose();
		
			mainMenu = null;
		}
		catch (InstantiationException ex)
		{
			ex.printStackTrace();
		}
		catch (IllegalAccessException ex)
		{
			ex.printStackTrace();
		}
		catch (InvocationTargetException ex)
		{
			ex.printStackTrace();
		}
		catch (NoSuchMethodException ex)
		{
			ex.printStackTrace();
		}
		catch (ClassNotFoundException ex)
		{
			ex.printStackTrace();
		}
		catch (MalformedURLException ex)
		{
			ex.printStackTrace();
		}
	}
	
	/**
	 * Initialize the data.
	 */
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

	/**
	 * Method that renders using the Ortho method.
	 */
	public void render2D()
	{
		if (mainMenu != null)
		{
			GL.scale(3, 3, 1);
		
			mainMenu.render();
		}
		else
		{
			gameInterface.render2D();
		}
	}

	/**
	 * Method that renders in the 3D mode.
	 */
	public void render3D()
	{
		if (mainMenu != null)
		{
			
		}
		else
		{
			gameInterface.render3D();
		}
	}
	
	/**
	 * Method that is called each time before the render methods.
	 */
	public void loop()
	{
		if (mainMenu != null)
		{
			
		}
		else
		{
			gameInterface.loop();
			
//			try
//			{
//				loop.invoke(obj, null);
//			}
//			catch (IllegalAccessException e)
//			{
//				e.printStackTrace();
//			}
//			catch (IllegalArgumentException e)
//			{
//				e.printStackTrace();
//			}
//			catch (InvocationTargetException e)
//			{
//				e.printStackTrace();
//			}
		}
	}
}