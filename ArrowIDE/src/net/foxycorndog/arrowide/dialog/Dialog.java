package net.foxycorndog.arrowide.dialog;

import org.eclipse.swt.widgets.Shell;

public interface Dialog
{
	public String open();
	
	public void setText(String text);
	
	public Shell getWindow();
}