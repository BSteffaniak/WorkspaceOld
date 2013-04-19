package net.foxycorndog.jfoxylibpixel.components;

import java.util.ArrayList;

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
	private ArrayList<Component>	children;
	
	/**
	 * Create a new Panel with the specified parent.
	 * 
	 * @param parent The Panel that contains this Panel.
	 */
	public Panel()
	{
		children = new ArrayList<Component>();
	}

//	/**
//	 * Method that is called each time it is needed to be rendered.
//	 * All of the drawing should be done in this method.
//	 */
//	public void render()
//	{
//		for (Component child : children)
//		{
//			child.render();
//		}
//	}
	
	/**
	 * Get the child Components that belong to this Panel.
	 * 
	 * @return The child Components that belong to this Panel.
	 */
	public ArrayList<Component> getChildren()
	{
		return children;
	}
	
	/**
	 * Add a Component as a child to this Panel.
	 * 
	 * @param child The Component to add as a child to this Panel.
	 * @return Whether or not the Component was successfully added.
	 */
	public boolean add(Component child)
	{
		if (children.contains(child))
		{
			return false;
		}
		
		children.add(child);
		
		return true;
	}
}