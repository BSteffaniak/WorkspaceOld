package net.foxycorndog.p1xeland.launcher;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLConnection;
import java.util.ArrayList;

import net.foxycorndog.jdobase.Base;
import net.foxycorndog.jdoogl.GL;
import net.foxycorndog.jdoogl.components.Component;
import net.foxycorndog.jdoogl.components.Frame;
import net.foxycorndog.jdoogl.components.ImageButton;
import net.foxycorndog.jdoogl.components.Frame.Alignment;
import net.foxycorndog.jdoogl.components.Menu;
import net.foxycorndog.jdoogl.image.imagemap.Texture;
import net.foxycorndog.jdoogl.input.KeyboardInput;
import net.foxycorndog.jdoogl.listeners.ActionListener;
import net.foxycorndog.jdoutil.FileUtil;
import net.foxycorndog.jdoutil.web.Downloader;
import net.foxycorndog.jdoutil.web.WebPage;
import net.foxycorndog.jdoutil.zip.Unzipper;
import net.foxycorndog.p1xeland.launcher.menus.LoginMenu;

public class Launcher implements ActionListener
{
	private boolean     ready;
	
	private Class<?>    clazz;
	
	private Object      obj;
	
	private Method      init, loop, render;
	
	private ImageButton play;
	
	private Menu        loginMenu;
	
	private Launcher    thisLauncher;
	
	public static void main(String args[])
	{
		new Launcher();
	}
	
	public Launcher()
	{
		thisLauncher = this;
		
		new Frame(640, 512, "P1XELAND Launcher", null)
		{
			@Override
			public void init()
			{
				loginMenu = new LoginMenu(thisLauncher);
				
				Frame.setIcon("res/images/favicon/16s.png", "res/images/favicon/32s.png");
			}
			
			@Override
			public void render()
			{
				if (ready)
				{
					try
					{
						render.invoke(obj, new Object[] {});
					}
					catch (IllegalArgumentException e)
					{
						e.printStackTrace();
					}
					catch (IllegalAccessException e)
					{
						e.printStackTrace();
					}
					catch (InvocationTargetException e)
					{
						e.printStackTrace();
					}
				}
				else
				{
					GL.beginManipulation();
					{
						GL.scalef(3, 3, 1);
						
						if (loginMenu != null)
						{
							loginMenu.render();
						}
					}
					GL.endManipulation();
				}
			}
			
			@Override
			public void loop()
			{
				if (ready)
				{
					try
					{
						loop.invoke(obj, new Object[] {});
					}
					catch (IllegalArgumentException e)
					{
						e.printStackTrace();
					}
					catch (IllegalAccessException e)
					{
						e.printStackTrace();
					}
					catch (InvocationTargetException e)
					{
						e.printStackTrace();
					}
				}
				else
				{
					
				}
			}
		}.startLoop(60);
	}
	
	/**
	 * Create an instance of P1xeland.class and allow the usage its init,
	 * loop, and render methods.
	 */
	public void launch()
	{
		try
		{
			
			URL urls[] = null;
			
//			if (Base.insideJar(Launcher.class))
//			{
//				System.out.println("in jar");
//				urls = new URL[] { new File("P1XELAND.jar").toURI().toURL() };
//			}
//			else
//			{
//				System.out.println("not in jar");
				urls = new URL[] { new File(Base.APPLICATION_DIR + "P1XELAND/bin/P1XELAND.jar").toURI().toURL() };
//			}
			
			URLClassLoader loader = new URLClassLoader(urls);
			
			clazz  = loader.loadClass("net.foxycorndog.p1xeland.P1xeland");
			
			Constructor<?> constr = clazz.getConstructor();
			
			obj    = constr.newInstance();
			
			init   = clazz.getDeclaredMethod("init", new Class<?>[] {});
			
			init.invoke(obj, new Object[] {});
			
			loop   = clazz.getDeclaredMethod("loop", new Class<?>[] {});
			
			render = clazz.getDeclaredMethod("render", new Class<?>[] {});
			
			ready  = true;
			
			loginMenu.destroy();
			
			loginMenu = null;
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
	
	public void checkUpdates()
	{
		boolean isLatest = false;
		
		String ver = WebPage.getFirstOutput("http://crapbuntu/braden/P1XELAND/Latest/Version.txt");
		
		try
		{
			String currentVer = FileUtil.getFirstOutput(Base.APPLICATION_DIR + "P1XELAND/bin/Version.txt");
			
			isLatest = currentVer.equals(ver);
		}
		catch (Exception ex)
		{
			isLatest = false;
		}
		
		if (!isLatest)
		{
			update();
		}
	}
	
	public void update()
	{
		Downloader.downloadFile("http://crapbuntu/braden/P1XELAND/Latest/P1XELAND.jar", Base.APPLICATION_DIR + "P1XELAND/bin", "P1XELAND.jar");
		
		Downloader.downloadFile("http://crapbuntu/braden/P1XELAND/Latest/res.zip", Base.APPLICATION_DIR + "P1XELAND/bin", "res.zip");
		
		Unzipper.unzip(Base.APPLICATION_DIR + "P1XELAND/bin/res.zip", Base.APPLICATION_DIR + "P1XELAND/bin");
		
		FileUtil.delete(Base.APPLICATION_DIR + "P1XELAND/bin/res.zip");
		
		Downloader.downloadFile("http://crapbuntu/braden/P1XELAND/Latest/natives.zip", Base.APPLICATION_DIR + "P1XELAND/bin", "natives.zip");
		
		Unzipper.unzip(Base.APPLICATION_DIR + "P1XELAND/bin/natives.zip", Base.APPLICATION_DIR + "P1XELAND/bin");
		
		FileUtil.delete(Base.APPLICATION_DIR + "P1XELAND/bin/natives.zip");
		
		Downloader.downloadFile("http://crapbuntu/braden/P1XELAND/Latest/Launch.bat", Base.APPLICATION_DIR + "P1XELAND/bin", "Launch.bat");
		
		Downloader.downloadFile("http://crapbuntu/braden/P1XELAND/Latest/Version.txt", Base.APPLICATION_DIR + "P1XELAND/bin", "Version.txt");
	}

	@Override
	public void onActionPerformed(Component source)
	{
		if (source == play)
		{
			launch();
		}
	}

	@Override
	public void onHover(Component source)
	{
		
	}
}