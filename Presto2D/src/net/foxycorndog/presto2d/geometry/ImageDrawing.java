package net.foxycorndog.presto2d.geometry;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.awt.image.PixelGrabber;
import java.awt.image.WritableRaster;

import javax.swing.ImageIcon;

/*********************************************************************
 * 
 * 
 * @author BradenSteffaniak
 *********************************************************************/
public class ImageDrawing extends Shape
{
	public BufferedImage blueimage = null, greenimage = null;
	BufferedImage bimage = null;
	Image image = null;
	int x, y;
	
	public ImageDrawing(int x, int y, Image img)
	{
		super(x, y, img.getWidth(null), img.getHeight(null), Shape.IMAGE);
		
		this.x = x;
		this.y = y;
		
		image = img;
		
		bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), Transparency.BITMASK);
		blueimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_4BYTE_ABGR);
		greenimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_4BYTE_ABGR);
		
		Graphics g = bimage.getGraphics();
		g.drawImage(img, 0, 0, null);
		g.dispose();
		
		createImage();
	}
	
	private void createImage()
	{
		// Create an array to hold the pixel data.
		int[] rawPixels = new int[bimage.getWidth() * bimage.getHeight()];
		
		// Create the PixelGrabber to extract the information from the BufferedImage.
		PixelGrabber pg = new PixelGrabber(bimage, 0, 0, -1, -1, rawPixels, 0, bimage.getWidth());
		
		// Try to grab the pixel data.
		try
		{
			// Grab that daTAA!!!
			pg.grabPixels();
		}
		catch (InterruptedException e)
		{
			System.out.println("Error: " + e);
		}
		
		// Variables used for counting.
		int blues = 0;
		int offset = 0;
		
		WritableRaster wr = blueimage.getRaster();
		WritableRaster wr2 = greenimage.getRaster();
		
		// blue
		int[] blue = new int[4];
		blue[0] = 0;
		blue[1] = 0;
		blue[2] = 255;
		blue[3] = 255;
		
		// green
		int[] green = new int[4];
		green[0] = 0;
		green[1] = 255;
		green[2] = 0;
		green[3] = 255;
		
		// transparent
		int[] transparent = new int[4];
		transparent[0] = 0;
		transparent[1] = 0;
		transparent[2] = 0;
		transparent[3] = 0;
		
		// Cycle through all columns in the BufferedImage.
		for (int j = 0; j < bimage.getHeight(); j++)
		{
			// Cycle through all of the rows in the BufferedImage.
			for (int i = 0; i < bimage.getWidth(); i++)
			{
				// If the blue value of the pixel is 255 (Symbolizing blue blue pixel).
				if (((rawPixels[offset ++] >> 24)  & 0xff) == 255)
				{
					wr.setPixel(i, j, blue);
					wr2.setPixel(i, j, green);
					
					// Add one to the amount of blue pixels.
					blues++;
				}
				else
				{
					wr.setPixel(i, j, transparent);
					wr2.setPixel(i, j, transparent);
				}
			}
		}
		
		setPixelQuantity(blues);
	}
	
	public ImageIcon getIcon()
	{
		return new ImageIcon(image);
	}
	
	public Image getImage()
	{
		return image;
	}
	
	public BufferedImage getBufferedImage()
	{
		return bimage;
	}
	
	/*****************************************************************
	 * Draw the circle to the Graphics object passed in the 
	 * parameters.
	 * 
	 * @param g The Graphics object to draw to.
	 *****************************************************************/
	@Override
	public void draw(Graphics2D g)
	{
		g.drawImage(bimage, (int)getX(), (int)getY(), null);
	}
	
	/*****************************************************************
	 * Draw blue filled in circle to the Graphics object passed in the 
	 * parameters.
	 * 
	 * @param g The Graphics object to draw to.
	 *****************************************************************/
	@Override
	public void fill(Graphics2D g)
	{
		draw(g);
	}
	
	/*****************************************************************
	 * Draw the circle to the Graphics object passed in the 
	 * parameters at the specified position.
	 * 
	 * @param g The Graphics object to draw to.
	 *****************************************************************/
	@Override
	public void draw(int x, int y, Graphics2D g)
	{
		g.drawImage(bimage, x, y, null);
	}
	
	/*****************************************************************
	 * Draw blue filled in circle to the Graphics object passed in the 
	 * parameters at the specified position.
	 * 
	 * @param g The Graphics object to draw to.
	 *****************************************************************/
	@Override
	public void fill(int x, int y, Graphics2D g)
	{
		draw(x, y, g);
	}
	
	protected void drawBlue(int x, int y, Graphics2D g)
	{
		g.drawImage(blueimage, x, y, null);
	}
	
	protected void fillBlue(int x, int y, Graphics2D g)
	{
		drawBlue(x, y, g);
	}
	
	protected void drawBlue(Graphics2D g)
	{
		g.drawImage(blueimage, (int)getX(), (int)getY(), null);
	}
	
	protected void fillBlue(Graphics2D g)
	{
		drawBlue(g);
	}
	
	protected void drawGreen(int x, int y, Graphics2D g)
	{
		g.drawImage(greenimage, x, y, null);
	}
	
	protected void fillGreen(int x, int y, Graphics2D g)
	{
		drawGreen(x, y, g);
	}
	
	protected void drawGreen(Graphics2D g)
	{
		g.drawImage(greenimage, (int)getX(), (int)getY(), null);
	}
	
	protected void fillGreen(Graphics2D g)
	{
		drawGreen(g);
	}
}