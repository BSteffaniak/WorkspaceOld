package net.foxycorndog.jfoxylib;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

public class Frame
{
	private static int		fps, targetFPS;
	
	private static org.lwjgl.opengl.Display	display;
	
	public static void create()
	{
		Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
		
		create((int)(size.width * 0.7f), (int)(size.height * 0.7f));
	}
	
	public static void create(int width, int height)
	{
		try
		{
			DisplayMode d = new DisplayMode(width, height);
			display.setDisplayMode(d);
			
			display.create();
		}
		catch (LWJGLException e)
		{
			e.printStackTrace();
		}
	}
	
	public static void setResizable(boolean resizable)
	{
		display.setResizable(resizable);
	}
	
	public static void setVSyncEnabled(boolean vSync)
	{
		display.setVSyncEnabled(vSync);
	}

	public static int getFPS()
	{
		return fps;
	}

	public static void setFPS(int fps)
	{
		Frame.fps = fps;
	}
	
	public static int getTargetFPS()
	{
		return targetFPS;
	}
	
	public static void setTargetFPS(int targetFPS)
	{
		Frame.targetFPS = targetFPS;
	}
	
	public static String getTitle()
	{
		return display.getTitle();
	}
	
	public static void setTitle(String title)
	{
		display.setTitle(title);
	}
	
	public static int getX()
	{
		return display.getX();
	}
	
	public static int getY()
	{
		return display.getY();
	}
	
	public static int getWidth()
	{
		return display.getWidth();
	}
	
	public static int getHeight()
	{
		return display.getHeight();
	}
	
	public static void setIcon(String image16Location, String image32Location) throws IOException
	{
//		InputStream in;
//		
//		in = new FileInputStream(image16Location);
//	
//		PNGDecoder decoder = new PNGDecoder(in);
//		
//		ByteBuffer buf16 = BufferUtils.createByteBuffer(4 * decoder.getWidth() * decoder.getHeight());
//		decoder.decode(buf16, decoder.getWidth() * 4, Format.RGBA);
//		buf16.flip();
//		
//		in.close();
//		
//		
//		in = new FileInputStream(image32Location);
//		
//		decoder = new PNGDecoder(in);
//		
//		ByteBuffer buf32 = BufferUtils.createByteBuffer(4 * decoder.getWidth() * decoder.getHeight());
//		decoder.decode(buf32, decoder.getWidth() * 4, Format.RGBA);
//		buf32.flip();
//		
//		in.close();
//		
//		
//		Display.setIcon(new ByteBuffer[] { buf16, buf32 });
	}
}