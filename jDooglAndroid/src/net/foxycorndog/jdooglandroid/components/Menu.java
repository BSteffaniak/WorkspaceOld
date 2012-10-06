package net.foxycorndog.jdooglandroid.components;

import net.foxycorndog.jdooglandroid.listeners.ActionListener;

public abstract class Menu implements ActionListener
{
	public void onHover(Component source)
	{
		
	}
	
	public abstract void render();
	
	public abstract void destroy();
}