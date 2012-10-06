package net.foxycorndog.presto2d.directors;

import java.awt.event.KeyEvent;

/*********************************************************************
 * Used for creating a more user friendly KeyListener.
 * 
 * @author BradenSteffaniak
 *********************************************************************/
public abstract class KeyDirector implements java.awt.event.KeyListener
{
	
	KeyEvent event;
	
	/*****************************************************************
	 * The default blank constructor.
	 *****************************************************************/
	public KeyDirector()
	{
		
	}

	/*****************************************************************
	 * Called whenever a key is pressed and also then released.
	 * 
	 * @param key The String representation of the key typed.
	 *****************************************************************/
	public abstract void typed(String key);
	
	/*****************************************************************
	 * Called whenever a key is pressed.
	 * 
	 * @param key The String representation of the key pressed.
	 *****************************************************************/
	public abstract void pressed(String key);
	
	/*****************************************************************
	 * Called whenever a key is released.
	 * 
	 * @param key The String representation of the key released.
	 *****************************************************************/
	public abstract void released(String key);
	
	@Override
	public void keyTyped(KeyEvent e)
	{
		event = e;
		String s = "";
		s += KeyEvent.getKeyText(e.getKeyCode());
		typed(s);
	}

	@Override
	public void keyPressed(KeyEvent e)
	{
		event = e;
		String s = "";
		s += KeyEvent.getKeyText(e.getKeyCode());
		pressed(s);
	}

	@Override
	public void keyReleased(KeyEvent e)
	{
		event = e;
		String s = "";
		s += KeyEvent.getKeyText(e.getKeyCode());
		released(s);
	}
	
	/*****************************************************************
	 * Gets the KeyEvent of the button activated.
	 * 
	 * @return The KeyEvent.
	 *****************************************************************/
	public KeyEvent getKey()
	{
		return event;
	}
}
