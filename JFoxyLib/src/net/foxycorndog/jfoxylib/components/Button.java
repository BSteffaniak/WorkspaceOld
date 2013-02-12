package net.foxycorndog.jfoxylib.components;

import java.util.ArrayList;

import net.foxycorndog.jfoxylib.events.ButtonEvent;
import net.foxycorndog.jfoxylib.listeners.ButtonListener;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;

public class Button extends Component
{
	private String		text;
	
	private Label		label;
	
	private Listener	listener;
	
	private ArrayList<ButtonListener>	buttonListeners;
	
	public Button()
	{
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
	}
	
	public void setText(String text)
	{
		this.text = text;
	}
	
	public void addTo(Panel panel)
	{
		super.addTo(panel);
		System.out.println(getX() + ", " + getY());
		if (label == null)
		{
			label = new Label(panel.getComposite(), SWT.NONE);
			label.setText(text);
			label.setLocation(getX(), getY());
			label.setSize(getWidth(), getHeight());
			
			label.addListener(SWT.MouseUp, listener);
		}
		
		
	}
	
	public void addButtonListener(ButtonListener listener)
	{
		buttonListeners.add(listener);
	}
}