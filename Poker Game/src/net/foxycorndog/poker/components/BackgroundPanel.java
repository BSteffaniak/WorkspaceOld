package net.foxycorndog.poker.components;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JPanel;

public class BackgroundPanel extends JPanel
{
	private boolean imageVisible;
	private boolean backgroundImageAutoResize;
	
	private int     displayWidth, displayHeight;
	private int     x, y;
	private int     backgroundImageAlignmentX;
	private int     backgroundImageAlignmentY;
	
	private Image   img;
	
	private Component parentComponent;
	
	private static final long serialVersionUID = 5433286717492456689L;
	
	public  static final int  LEFT = 0, RIGHT = 1, TOP = 0, BOTTOM = 1,
			CENTER = 2;
	
	/**
	* Constructor method used for initialization of the image and
	* its size.
	* 
	* @param image The Image to set as the background of the panel.
	*/
	public BackgroundPanel(Image image)
	{
		img = image;
		
		if (image == null)
		{
			imageVisible = false;
		}
		else
		{
			Dimension size = new Dimension(img.getWidth(null), img.getHeight(null));
			setPreferredSize(size);
			setMinimumSize(size);
			setMaximumSize(size);
			setSize(size);
			
			displayWidth  = size.width;
			displayHeight = size.height;
			
			imageVisible = true;
		}
		
		init();
	}
	
	/**
	* The initialization method that sets the backgroundImageAutoResize
	* to true so that when the panel is resized, then so is the image.
	*/
	private void init()
	{
		backgroundImageAutoResize = true;
		
		x = 0;
		y = 0;
	}
	
	/**
	* Sets the backgroundImageAutoResize to whatever is passed
	* through the parameters. If set to true, then as the parent
	* panel is resized, then the image is display accordingly. If set
	* to false, then as the panel is resized, then the background color
	* of the panel will fill the rest of the screen that the image
	* doesn't fill up.
	* 
	* @param autoResize Whether the image should auto resize on the
	* 		panel resize or not.
	*/
	public void setBackgroundImageAutoResize(boolean autoResize)
	{
		backgroundImageAutoResize = autoResize;
		
		recalculatePosition();
	}
	
	/**
	* The method used for painting the image to the background of the
	* panel. 
	*/
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		
		if (imageVisible)
		{
			if (backgroundImageAutoResize)
			{
				if (parentComponent != null)
				{
					displayWidth  = parentComponent.getWidth();
					displayHeight = parentComponent.getHeight();
				}

				g.drawImage(img, x, y, displayWidth, displayHeight, null);
			}
			else
			{
				g.drawImage(img, x, y, null);
			}
		}
	}
	
	/**
	* The method used for recalculating the position of the image.
	* If the image is auto resized, then it will always be 0, 0.
	* If not, then it will be placed accordingly to what makes it
	* in the center of the panel.
	*/
	private void recalculatePosition()
	{
		if (backgroundImageAutoResize)
		{
			x = 0;
			y = 0;
		}
		else
		{
			if (backgroundImageAlignmentX == CENTER)
			{
				x = getWidth() - img.getWidth(null);
				x /= 2;
			}
			else if (backgroundImageAlignmentX == LEFT)
			{
				x = 0;
			}
			else if (backgroundImageAlignmentX == RIGHT)
			{
				x = getWidth() - img.getWidth(null);
			}
			
			if (backgroundImageAlignmentY == CENTER)
			{
				y = getHeight() - img.getHeight(null) + -23;
				y /= 2;
			}
			else if (backgroundImageAlignmentY == TOP)
			{
				y = 0;
			}
			else if (backgroundImageAlignmentY == BOTTOM)
			{
				y = getHeight() - img.getHeight(null) + -23;
			}
		}
	}
	
	/**
	* The method that sets the parent component of the panel.
	* The image will be sized and placed accordingly to the parent
	* components size.
	* 
	* @param component The component to set as the parent of the panel.
	*/
	public void setParentComponent(Component component)
	{
		parentComponent = component;
	}
	
	/**
	* Set the backgroundImageAlignmentX to the specified alignment.
	* The options are left, right, and center, being self explanatory.
	* 
	* @param alignment The alignment to set the Image as.
	*/
	public void setBackgroundImageAlignmentX(int alignment)
	{
		backgroundImageAlignmentX = alignment;
	}
	
	/**
	* Set the backgroundImageAlignmentY to the specified alignment.
	* The options are top, bottom, and center, being self explanatory.
	* 
	* @param alignment The alignment to set the Image as.
	*/
	public void setBackgroundImageAlignmentY(int alignment)
	{
		backgroundImageAlignmentY = alignment;
	}
	
	/**
	* Set the size of the panel to the specified width and height.
	* 
	* @param width The desired width in pixels of the panel.
	* @param height The desired height in pixels of the panel.
	*/
	public void setSize(int width, int height)
	{
		super.setSize(width, height);
		
		displayWidth  = width;
		displayHeight = height;
		
		recalculatePosition();
	}
	
	/**
	* Set the size of the panel to the specified dimension.
	* 
	* @param size The dimension in pixels to set the size as.
	*/
	public void setSize(Dimension size)
	{
		setSize(size.width, size.height);
	}
}