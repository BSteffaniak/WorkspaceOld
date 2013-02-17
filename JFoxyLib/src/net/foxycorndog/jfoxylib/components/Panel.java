package net.foxycorndog.jfoxylib.components;

import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

/**
 * 
 * 
 * @author	Braden Steffaniak
 * @since	Feb 15, 2013 at 11:44:58 PM
 * @since	v0.1
 * @version	v0.1
 */
public class Panel extends Component
{
	private Composite	composite;
	
	public Panel()
	{
		super(null);
	}
	
	public Panel(Panel parent)
	{
		super(parent);
		
		create(parent);
	}
	
	public Panel(Composite parent)
	{
		super(null);
		
		create(parent);
	}
	
	public void create(Panel parent)
	{
		composite = new Composite(parent.getComposite(), SWT.NONE);
	}
	
	public void create(Composite parent)
	{
		if (composite == null)
		{
			composite = new Composite(parent, SWT.NONE);
		}
		else
		{
			composite.setParent(parent);
		}
	}
	
	public Composite getComposite()
	{
		return composite;
	}
	
	public void setSize(int width, int height)
	{
		super.setSize(width, height);
		
		composite.setSize(width, height);
	}
	
	public void setLocation(int x, int y)
	{
		super.setLocation(x, y);
		
		composite.setLocation(x, y);
	}
	
//	public void add(Component component)
//	{
//		if (!components.contains(component))
//		{
//			components.add(component);
//			component.addTo(this);
//		}
//		else
//		{
//		}
//	}
//	
//	public void remove(Component component)
//	{
//		if (components.contains(component))
//		{
//			components.remove(component);
//			component.removeFrom(this);
//		}
//	}
	
	public void update()
	{
//		if (getWidth() != composite.getSize().x || getHeight() != composite.getSize().y)
//		{
//			composite.setSize(getWidth(), getHeight());
//		}
	}
}