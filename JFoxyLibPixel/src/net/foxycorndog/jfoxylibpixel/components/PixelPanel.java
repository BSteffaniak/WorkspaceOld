package net.foxycorndog.jfoxylibpixel.components;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import net.foxycorndog.jfoxylibpixel.GL;
import net.foxycorndog.jfoxylibpixel.util.Bounds;

/**
 * Class that is used to paint and edit pixels of an image easily.
 * 
 * @author	Braden Steffaniak
 * @since	Feb 27, 2013 at 6:12:19 PM
 * @since	v0.1
 * @version Feb 27, 2013 at 6:12:19 PM
 * @version	v0.1
 */
public class PixelPanel extends Panel
{
	private int				pixelSize;
	
	private BufferedImage	image;
	
	private int				pixels[];
	
	/**
	 * Construct a Panel in which an image will be held.
	 * 
	 * @param parent The parent Panel to this PixelPanel.
	 */
	public PixelPanel(Panel parent)
	{
		super(parent);
		
		create(0, 0);
	}
	
	/**
	 * Method that instantiates the image field with the given size.
	 * 
	 * @param width The horizontal size of the new BufferedImage.
	 * @param height The vertical size of the new BufferedImage.
	 */
	private void create(int width, int height)
	{
		image = new BufferedImage(width, height, BufferedImage.BITMASK);
		
		pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();
	}
	
	/**
	 * @return The integer pixel array for the color data of this
	 * 		PixelPanel.
	 */
	public int[] getPixels()
	{
		return pixels;
	}
	
	/**
	 * Method that fills the integer pixel array with a rectangle at the
	 * specified location with the specified size and color.
	 * 
	 * @param x The x location to fill the rectangle.
	 * @param y The y location to fill the rectangle.
	 * @param width The width of the rectangle.
	 * @param height The height of the rectangle.
	 * @param color The color of the rectangle.
	 */
	public void fillRect(int x, int y, int width, int height, int color)
	{
		GL.fillRect(x, y, width, height, color, pixels, image.getWidth());
	}

	/**
	 * Sets the horizontal and vertical size of this PixelPanel.
	 * 
	 * @param width The horizontal size.
	 * @param height The vertical size.
	 */
	public void setSize(int width, int height)
	{
		super.setSize(width, height);
		
		create(width, height);
	}
}