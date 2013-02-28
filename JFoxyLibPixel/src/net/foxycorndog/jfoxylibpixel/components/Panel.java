package net.foxycorndog.jfoxylibpixel.components;

/**
 * Class that is used to hold other Components. Must have a parent
 * Panel.
 * 
 * @author	Braden Steffaniak
 * @since	Feb 27, 2013 at 3:26:33 PM
 * @since	v0.1
 * @version Feb 27, 2013 at 3:26:33 PM
 * @version	v0.1
 */
public class Panel extends Component
{
	/**
	 * Create a new Panel with the specified parent.
	 * 
	 * @param parent The Panel that contains this Panel.
	 */
	public Panel(Panel parent)
	{
		super(parent);
	}

	/**
	 * Method that is called each time it is needed to be rendered.
	 * All of the drawing should be done in this method.
	 */
	public void render()
	{
		
	}
}