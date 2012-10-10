package net.foxycorndog.idk.menus;

import net.foxycorndog.jdoogl.components.Component;
import net.foxycorndog.jdoogl.components.TextButton;

public abstract class InventoryMenu extends Menu
{
	public abstract void onActionPerformed(Component source);
}