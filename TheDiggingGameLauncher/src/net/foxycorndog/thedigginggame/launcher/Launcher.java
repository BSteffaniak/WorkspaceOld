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
import net.foxycorndog.thedigginggame.launcher.menu.MainMenu;

public class Launcher extends GameStarter
{
	private Font		font;
	
	private MainMenu	mainMenu;
	
	private Method		init, loop, render;
	
	public static void main(String args[])
	{
		Launcher launch = new Launcher();
		
		System.out.println("done.");
	}
	
	public Launcher()
	{
		Frame.create(800, 600);
		Frame.setVSyncEnabled(true);
		Frame.setResizable(true);
		
		start();
	}
	
	public void startGame()
	{
		try
		{
			
			URL urls[] = null;
			
			String jarLoc = "";
			
//			if (Base.insideJar(Launcher.class))
//			{
//				System.out.println("in jar");
//				urls = new URL[] { new File("P1XELAND.jar").toURI().toURL() };
//			}
//			else
//			{
//				System.out.println("not in jar");
				urls = new URL[] { new File(jarLoc).toURI().toURL() };
//			}
			
			URLClassLoader loader = new URLClassLoader(urls);
			
			Class clazz  = loader.loadClass("net.foxycorndog.p1xeland.P1xeland");
			
			Constructor<?> constr = clazz.getConstructor();
			
			Object obj    = constr.newInstance();
			
			init   = clazz.getDeclaredMethod("init", new Class<?>[] {});
			
			init.invoke(obj, new Object[] {});
			
			loop   = clazz.getDeclaredMethod("loop", new Class<?>[] {});
			
			render = clazz.getDeclaredMethod("render", new Class<?>[] {});
			
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