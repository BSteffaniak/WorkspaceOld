package net.foxycorndog.jfoxylib.components;

import java.util.ArrayList;

import net.foxycorndog.jfoxylib.events.ButtonEvent;
import net.foxycorndog.jfoxylib.listeners.ButtonListener;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;

/**
 * 
 * 
 * @author	Braden Steffaniak
 * @since	Feb 15, 2013 at 11:45:27 PM
 * @since	v0.1
 * @version	v0.1
 */
public class Button extends Component
{
	private String		text;
	
	private Label		label;
	
	private Listener	listener;
	
	private ArrayList<ButtonListener>	buttonListeners;
	
	public Button(Panel parent)
	{
		super(parent);
		
		buttonListeners = new ArrayList<ButtonListener>();
		
		final Button thisButton = this;
		
		listener = new Listener()
		{
			public void handleEvent(Event event)
			{
				if (event.x >= 0 && event.x < getWidth() && event.y >= 0 && event.y < getHeight())
				{
					ButtonEvent buttonEvent = new ButtonEvent(thisButton);
					
					for (int i = buttonListeners.size() - 1; i >= 0; i--)
					{
						buttonListeners.get(i).buttonPressed(buttonEvent);
					}
				}
			}
		};
		
		label = new Label(parent.getComposite(), SWT.NONE);
		
		label.setLocation(getX(), getY());
		label.setSize(getWidth(), getHeight());
		
		label.addListener(SWT.MouseUp, listener);
		
		setControl(label);
	}
	
	public void setText(String text)
	{
		this.text = text;
		label.setText(text);
	}
	
	public void addButtonListener(ButtonListener listener)
	{
		buttonListeners.add(listener);
	}
}