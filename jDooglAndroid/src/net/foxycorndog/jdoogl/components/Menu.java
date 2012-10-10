package net.foxycorndog.jdoogl.components;

import net.foxycorndog.jdoogl.listeners.ActionListener;

public abstract class Menu implements ActionListener
{
	public void onHover(Component source)
	{
		
	}
	
	public abstract void render();
	
	public abstract void destroy();
}