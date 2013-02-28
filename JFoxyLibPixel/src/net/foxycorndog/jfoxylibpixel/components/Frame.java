package net.foxycorndog.jfoxylibpixel.components;

import java.awt.Graphics;

import javax.swing.JFrame;

import net.foxycorndog.jfoxylibpixel.util.Dimension;

/**
 * Class that is used to create a Frame on the screen.
 * 
 * @author	Braden Steffaniak
 * @since	Feb 27, 2013 at 5:18:39 PM
 * @since	v0.1
 * @version Feb 27, 2013 at 5:18:39 PM
 * @version	v0.1
 */
public class Frame extends Panel
{
	private JFrame	frame;
	
	private Panel	contentPanel;
	
	/**
	 * Constructs a plain Frame.
	 */
	public Frame()
	{
		super(null);
		
		frame = new JFrame()
		{
			public void paint(Graphics g)
			{
				System.out.println("pant");
			}
		};
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.setUndecorated(true);
		
		contentPanel = new Panel(this);
	}
	
	/**
	 * Method that centers the Frame in the middle of the screen.
	 */
	public void center()
	{
		frame.setLocationRelativeTo(null);
	}
	
	/**
	 * @return The minimum horizontal size of the Frame.
	 */
	public int getMinimumWidth()
	{
		return frame.getMinimumSize().width;
	}
	
	/**
	 * @return The minimum vertical size of the Frame.
	 */
	public int getMinimumHeight()
	{
		return frame.getMinimumSize().height;
	}
	
	/**
	 * @return The dimensions of the minimum width and height.
	 */
	public Dimension getMinimumSize()
	{
		return new Dimension(frame.getMinimumSize());
	}
	
	/**
	 * Sets the minimum horizontal and vertical sizes that the Frame
	 * can be sized to.
	 * 
	 * @param width The minimum horizontal size.
	 * @param height The minimum vertical size.
	 */
	public void setMinimumSize(int width, int height)
	{
		frame.setMinimumSize(new java.awt.Dimension(width, height));
	}
	
//	/**
//	 * @return The maximum horizontal size of the Frame.
//	 */
//	public int getMaximumWidth()
//	{
//		return frame.getMaximumSize().width;
//	}
//	
//	/**
//	 * @return The maximum vertical size of the Frame.
//	 */
//	public int getMaximumHeight()
//	{
//		return frame.getMaximumSize().height;
//	}
//	
//	/**
//	 * @return The dimensions of the maximum width and height.
//	 */
//	public Dimension getMaximumSize()
//	{
//		return new Dimension(frame.getMaximumSize());
//	}
//	
//	/**
//	 * Sets the maximum horizontal and vertical sizes that the Frame
//	 * can be sized to.
//	 * 
//	 * @param width The maximum horizontal size.
//	 * @param height The maximum vertical size.
//	 */
//	public void setMaximumSize(int width, int height)
//	{
//		frame.setMaximumSize(new java.awt.Dimension(width, height));
//	}
	
	/**
	 * @return The actual JFrame that is used as the Frame.
	 */
	public JFrame getJFrame()
	{
		return frame;
	}
	
	/**
	 * @return Whether or not this Frame can be resized.
	 */
	public boolean isResizable()
	{
		return frame.isResizable();
	}
	
	/**
	 * Method to set whether the Frame will be able to be resized or not.
	 * 
	 * @param resizable Whether or not to allow resizing.
	 */
	public void setResizable(boolean resizable)
	{
		frame.setResizable(resizable);
	}
	
	/**
	 * @return Whether or not the Frame is visible.
	 */
	public boolean isVisible()
	{
		return frame.isVisible();
	}
	
	/**
	 * Set the Frame visible on the screen or not.
	 * 
	 * @param visible Whether to set it visible or invisible.
	 */
	public void setVisible(boolean visible)
	{
		frame.setVisible(visible);
	}

	/**
	 * Sets the horizontal and vertical size of this Frame.
	 * 
	 * @param width The horizontal size.
	 * @param height The vertical size.
	 */
	public void setSize(int width, int height)
	{
		super.setSize(width, height);
		
		frame.setSize(getWidth(), getHeight());
	}

	/**
	 * Method that is called each time it is needed to be rendered.
	 * All of the drawing should be done in this method.
	 */
	public void render()
	{
		
	}
}