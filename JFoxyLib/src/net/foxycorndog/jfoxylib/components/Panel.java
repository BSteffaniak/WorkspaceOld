package net.foxycorndog.jfoxylib.components;

import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

public class Panel extends Component
{
	private Composite				composite;
	
	private ArrayList<Component>	components;
	
	public Panel()
	{
		components = new ArrayList<Component>();
	}
	
	public void create(Panel parent)
	{
		composite  = new Composite(parent.getComposite(), SWT.NONE);
	}
	
	public void create(Composite parent)
	{
		composite  = new Composite(parent, SWT.NONE);
	}
	
	public Composite getComposite()
	{
		return composite;
	}
	
	public void add(Component component)
	{
		if (components.contains(component))
		{
			
		}
		else
		{
			components.add(component);
			component.addTo(this);
		}
	}
	
	public void remove(Component component)
	{
		if (components.contains(component))
		{
			components.remove(component);
			component.removeFrom(this);
		}
		else
		{
			
		}
	}
}