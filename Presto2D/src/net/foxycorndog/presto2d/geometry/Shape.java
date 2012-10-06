package net.foxycorndog.presto2d.geometry;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.awt.image.PixelGrabber;

/*********************************************************************
 * Used for the base of all of the shapes that will extend this 
 * class.
 * 
 * @author BradenSteffaniak
 *********************************************************************/
public class Shape extends ShapeTemplate
{
	public static final int CIRCLE = 0, RECTANGLE = 1, LINE = 2, DIAMOND = 3, OVAL = 4, FREEFORM = 5, IMAGE = 6;
	private double x, y, width, height, type;
	private int pixelQuantity;
	private BufferedImage image, image2;
	
	/*****************************************************************
	 * Used for setting the basic properties for all of the shapes.
	 * This is the first thing called when a new shape is created.
	 * 
	 * @param x The X position of the shape.
	 * @param y The Y position of the shape.
	 * @param width The width of the shape.
	 * @param height The height of the shape.
	 * @param type The type of the shape. e.g. Shape.CIRCLE.
	 *****************************************************************/
	protected Shape(int x, int y, int width, int height, int type)
	{
		// Set the object variables equal to the parameters passed.
		this.x = x;
		this.y = y;
		
		this.width = width;
		this.height = height;
		
		this.type = type;
		
		if (type != Shape.FREEFORM && type != Shape.IMAGE)
		{
			createImage(width, height);
		}
	}
	
	/*****************************************************************
	 * Get the X position of the shape.
	 * 
	 * @return The X position value.
	 *****************************************************************/
	@Override
	public double getX()
	{
		return x;
	}
	
	public BufferedImage getimg()
	{
		return image;
	}
	
	/*****************************************************************
	 * Get the Y position of the shape.
	 * 
	 * @return The Y position value.
	 *****************************************************************/
	@Override
	public double getY()
	{
		return y;
	}
	
	@Override
	public void setX(double x)
	{
		this.x = x;
	}
	
	@Override
	public void setY(double y)
	{
		this.y = y;
	}
	
	@Override
	public void move(double dx, double dy)
	{
		this.x += dx;
		this.y += dy;
	}
	
	/*****************************************************************
	 * Get the width of the shape.
	 * 
	 * @return The width of the shape.
	 *****************************************************************/
	@Override
	public double getWidth()
	{
		return width;
	}
	
	/*****************************************************************
	 * Get the height of the shape.
	 * 
	 * @return The height of the shape.
	 *****************************************************************/
	@Override
	public double getHeight()
	{
		return height;
	}
	
	/*****************************************************************
	 * Get the type of the shape it is.
	 * 
	 * @return The type of shape.
	 *****************************************************************/
	public double getType()
	{
		return type;
	}
	
	/*****************************************************************
	 * Get the number of pixels in the filled in shape.
	 * 
	 * @return The number of pixels.
	 *****************************************************************/
	public int getPixelQuantity()
	{
		return pixelQuantity;
	}
	
	/*****************************************************************
	 * Set the number of pixels that are in the shape.
	 * 
	 * @param p The number of pixels.
	 *****************************************************************/
	protected void setPixelQuantity(int p)
	{
		pixelQuantity = p;
	}
	
	public BufferedImage getShapeImage()
	{
		return image;
	}
	
	/*****************************************************************
	 * A gimmick fill method to fool the shape sub-objects.
	 * 
	 * @param g The graphics object.
	 *****************************************************************/
	@Override
	public void fill(Graphics2D g)
	{
		
	}
	
	/*****************************************************************
	 * A gimmick fill method to fool the shape sub-objects.
	 * 
	 * @param g The graphics object.
	 *****************************************************************/
	@Override
	public void fill(int x, int y, Graphics2D g)
	{
		
	}
	
	/*****************************************************************
	 * A gimmick draw method to fool the shape sub-objects.
	 * 
	 * @param g The graphics object.
	 *****************************************************************/
	@Override
	public void draw(Graphics2D g)
	{
		
	}
	
	/*****************************************************************
	 * A gimmick draw method to fool the shape sub-objects.
	 * 
	 * @param g The graphics object.
	 *****************************************************************/
	@Override
	public void draw(int x, int y, Graphics2D g)
	{
		
	}
	
	/*****************************************************************
	 * Used to tell whether a shape is intersecting another shape 
	 * sub-object.
	 * 
	 * @param shape The shape sub-object.
	 * @return Whether they intersect.
	 *****************************************************************/
	public boolean intersects(Shape shape)
	{
		// Check whether the shapes are even near eachother.
		if (getX() + getWidth() > shape.getX() &&
				shape.getX() + shape.getWidth() > getX() &&
				getY() + getHeight() > shape.getY() &&
				shape.getY() + shape.getHeight() > getY())
		{
			if (shape.type == Shape.IMAGE)
			{
				ImageDrawing shp = (ImageDrawing)shape;
				
				BufferedImage d = null;
				
				if (this.type == Shape.IMAGE)
				{
					ImageDrawing shpj = (ImageDrawing)this;
					
					// Create a reference to a BufferedImage that we will add two shapes to, to see if they overlap at all.
					d = shpj.blueimage;
				}
				else
				{
					// Create a reference to a BufferedImage that we will add two shapes to, to see if they overlap at all.
					d = image2;
				}

				// Create a java.awt.Graphics2D object to draw to the BufferedImage.
				Graphics2D g = d.createGraphics();

				// Draw a filled in representation of the second shape.
				shp.fillGreen((int)shape.getX() - (int)this.getX(), (int)shape.getY() - (int)this.getY(), g);

				// Dispose of the graphics object.
				g.dispose();
				
				// Return whether there is a difference in the number of pixels
				// from before to after. If there is, then there was a collision.
				return getPixelQuantity() > getBlues(d);
			}
			else
			{
				BufferedImage d = null;
				
				if (this.type == Shape.IMAGE)
				{
					ImageDrawing shpj = (ImageDrawing)this;
					
					// Create a BufferedImage that we will add two shapes to, to see if they overlap at all.
					d = shpj.blueimage;
				}
				else
				{
					d = image2;
				}

				// Create a java.awt.Graphics2D object to draw to the BufferedImage.
				Graphics2D g = d.createGraphics();

				// Set the color to green.
				g.setColor(Color.GREEN);

				// Draw a filled in representation of the second shape.
				shape.fill((int)shape.getX() - (int)this.getX() , (int)shape.getY() - (int)this.getY(), g);

				// Dispose of the graphics object.
				g.dispose();

				// Return whether there is a difference in the number of pixels
				// from before to after. If there is, then there was a collision.
				return getPixelQuantity() > getBlues(d);
			}
		}
		
		// Return that nothing intersects because the if statement was false.
		return false;
	}
	
	/*****************************************************************
	 * Gets the number of blue (0, 0, 255) pixels in a BufferedImage.
	 * 
	 * @param image The BufferedImage that will be tested for blue 
	 * pixels.
	 * @return The number of blue pixels.
	 *****************************************************************/
	public int getBlues(BufferedImage image)
	{
		// Create an array to hold the pixel data.
		int[] rawPixels = new int[image.getWidth() * image.getHeight()];
		
		// Create the PixelGrabber to extract the information from the BufferedImage.
		PixelGrabber pg = new PixelGrabber(image, 0, 0, -1, -1, rawPixels, 0, image.getWidth());
		
		// Try to grab the pixel data.
		try
		{
			// Grab that daTAA!!!
			pg.grabPixels();
		}
		catch (InterruptedException e)
		{
			System.out.println("Error: " + e);
			return 0;
		}
		
		// Variables used for counting.
		int blues = 0;
		int offset = 0;
		
		// Cycle through all columns in the BufferedImage.
		for (int j = 0; j < image.getHeight(); j++)
		{
			// Cycle through all of the rows in the BufferedImage.
			for (int i = 0; i < image.getWidth(); i++)
			{
				// If the blue value of the pixel is 255 (Symbolizing a blue pixel).
				if ((rawPixels[offset ++]  & 0xff) == 255)
				{
					// Add one to the amount of blue pixels.
					blues ++;
				}
			}
		}
		
		// The final amount of blue pixels.
		return blues;
	}
	
	public void createImage(int width, int height)
	{
		// Create a new image with the shape drawn onto it.
		image = new BufferedImage(width, height, Transparency.BITMASK);

		Graphics2D g = image.createGraphics();

		g.setColor(Color.BLUE);

		fill(0, 0, g);
		
		g.dispose();

		setPixelQuantity(getBlues(image));
		
		image2 = new BufferedImage(width, height, Transparency.BITMASK);

		Graphics2D g2 = image2.createGraphics();

		g2.setColor(Color.BLUE);

		fill(0, 0, g2);
		
		g2.dispose();
	}
}