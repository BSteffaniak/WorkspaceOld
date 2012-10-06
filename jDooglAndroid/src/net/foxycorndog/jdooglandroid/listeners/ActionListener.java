package net.foxycorndog.jdooglandroid.listeners;

import net.foxycorndog.jdooglandroid.components.Component;

public interface ActionListener
{
	public abstract void onActionPerformed(Component source);
	
	public abstract void onHover(Component source);
}