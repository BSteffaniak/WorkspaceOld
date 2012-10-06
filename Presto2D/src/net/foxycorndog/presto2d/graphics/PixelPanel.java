package net.foxycorndog.presto2d.graphics;

import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

public class PixelPanel
{
	private int           pixels[];
	private BufferedImage image;
	private PixelGraphics pg;
	private int           width, height;
	
	public PixelPanel(int width, int height)
	{
		init(width, height);
	}
	
	public void init(int width, int height)
	{
		image = new BufferedImage(width, height, Transparency.BITMASK);
		
		pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();
		
		pg = new PixelGraphics(pixels, width);
		
		this.width  = width;
		this.height = height;
	}
	
	public void setSize(int width, int height)
	{
		init(width, height);
	}
	
	public PixelGraphics getPixelGraphics()
	{
		return pg;
	}
	
	public BufferedImage getBufferedImage()
	{
		return image;
	}
	
	public void setBufferedImage(BufferedImage image)
	{
		this.image = image;
		
		pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();
		
		pg = new PixelGraphics(pixels, width);
	}
	
	public int getWidth()
	{
		return width;
	}
	
	public int getHeight()
	{
		return height;
	}
	
	public int[] getPixels()
	{
		return pixels;
	}
}