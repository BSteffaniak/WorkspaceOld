package net.foxycorndog.arrowide.components.tabmenu;

public interface TabMenuListener
{
	public boolean tabClosing(int tabId);
	
	public void tabSelected(int tabId);
}