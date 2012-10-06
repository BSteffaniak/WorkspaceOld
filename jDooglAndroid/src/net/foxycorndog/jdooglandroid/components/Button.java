package net.foxycorndog.jdooglandroid.components;

import java.util.ArrayList;

import net.foxycorndog.jdooglandroid.listeners.ActionListener;

public abstract class Button extends Component
{
	private boolean     mouseOver;
	
	private ArrayList<ActionListener> actionListeners;
	
	public Button()
	{
		actionListeners = new ArrayList<ActionListener>();
	}
	
	public ArrayList<ActionListener> getActionListeners()
	{
		return actionListeners;
	}
	
	public void addActionListener(ActionListener actionListener)
	{
		actionListeners.add(actionListener);
	}
	
	public boolean isMouseOver()
	{
		return mouseOver;
	}
	
	public void setMouseOver(boolean mouseOver)
	{
		this.mouseOver = mouseOver;
	}
}