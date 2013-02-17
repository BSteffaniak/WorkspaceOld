package net.foxycorndog.jfoxylib.components;

import org.eclipse.swt.SWT;

/**
 * 
 * 
 * @author	Braden Steffaniak
 * @since	Feb 15, 2013 at 11:44:40 PM
 * @since	v0.1
 * @version	v0.1
 */
public class Window extends Frame
{
	private String title;
	
	public Window()
	{
		addStyle(SWT.CLOSE);
		addStyle(SWT.MIN);
	}
	
	public void setTitle(String title)
	{
		this.title = title;
	}
}