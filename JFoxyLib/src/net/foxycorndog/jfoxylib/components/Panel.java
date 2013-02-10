package net.foxycorndog.jfoxylib.components;

import java.util.ArrayList;

public class Panel extends Component
{
	private ArrayList<Component> components;
	
	public Panel()
	{
		components = new ArrayList<Component>();
	}
	
	public void add(Component component)
	{
		components.add(component);
	}
	
	public void remove(Component component)
	{
		components.remove(component);
	}
}