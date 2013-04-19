package net.foxycorndog.jfoxylibpixel.components;

/**
 * Class that is the base for all of the other Components.
 * Keeps all of the essential information such as the x, y,
 * width, and height of the Component. As well as other
 * important information.
 * 
 * @author	Braden Steffaniak
 * @since	Feb 27, 2013 at 3:26:41 PM
 * @since	v0.1
 * @version Feb 27, 2013 at 3:26:41 PM
 * @version	v0.1
 */
public abstract class Component
{
	private int		x, y;
	private int		width, height;
	
	/**
	 * Create a new Component with the specified parent.
	 * 
	 * @param parent The Panel that contains this Component.
	 */
	public Component()
	{
		
	}
	
	/**
	 * @return The horizontal location of this Component.
	 */
	public int getX()
	{
		return x;
	}
	
	/**
	 * Sets the horizontal location of this Component.
	 * 
	 * @param x The horizontal location.
	 */
	public void setX(int x)
	{
		this.x = x;
	}

	/**
	 * @return The vertical location of this Component.
	 */
	public int getY()
	{
		return y;
	}

	/**
	 * Sets the vertical location of this Component.
	 * 
	 * @param y The vertical location.
	 */
	public void setY(int y)
	{
		this.y = y;
	}

	/**
	 * Sets the horizontal and vertical location of this Component.
	 * 
	 * @param x The horizontal location.
	 * @param y The vertical location.
	 */
	public void setLocation(int x, int y)
	{
		this.x = x;
		this.y = y;
	}

	/**
	 * @return The horizontal size of this Component.
	 */
	public int getWidth()
	{
		return width;
	}
	
	/**
	 * Sets the horizontal size of this Component.
	 * 
	 * @param width The horizontal size.
	 */
	public void setWidth(int width)
	{
		setSize(width, getHeight());
	}

	/**
	 * @return The vertical size of this Component.
	 */
	public int getHeight()
	{
		return height;
	}

	/**
	 * Sets the vertical size of this Component.
	 * 
	 * @param height The vertical size.
	 */
	public void setHeight(int height)
	{
		setSize(getWidth(), height);
	}

	/**
	 * Sets the horizontal and vertical size of this Component.
	 * 
	 * @param width The horizontal size.
	 * @param height The vertical size.
	 */
	public void setSize(int width, int height)
	{
		this.width  = width;
		this.height = height;
	}
	
//	/**
//	 * Method that is called each time it is needed to be rendered.
//	 * All of the drawing should be done in this method.
//	 */
//	public abstract void render();
}