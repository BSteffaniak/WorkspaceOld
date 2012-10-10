package net.foxycorndog.jdoogl.listeners;

import net.foxycorndog.jdoogl.components.Component;

public interface ActionListener
{
	public abstract void onActionPerformed(Component source);
	
	public abstract void onHover(Component source);
}