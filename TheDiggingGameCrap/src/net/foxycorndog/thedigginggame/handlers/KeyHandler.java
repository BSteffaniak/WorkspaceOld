package net.foxycorndog.thedigginggame.handlers;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import net.foxycorndog.thedigginggame.Display;
import net.foxycorndog.thedigginggame.TheDiggingGame;
import net.foxycorndog.thedigginggame.actors.Actor;
import net.foxycorndog.thedigginggame.actors.Player;

public class KeyHandler implements KeyListener
{
	private TheDiggingGame tdg;
	
	public KeyHandler(TheDiggingGame tdg)
	{
		this.tdg = tdg;
	}

	@Override
	public void keyPressed(KeyEvent e)
	{
		int code = e.getKeyCode();
		
		Player player = tdg.getPlayer();
		
		if (player != null)
		{
			if (code == KeyEvent.VK_LEFT || code == KeyEvent.VK_A)
			{
				player.setMoving(Actor.LEFT, true);
			}
			else if (code == KeyEvent.VK_RIGHT || code == KeyEvent.VK_D)
			{
				player.setMoving(Actor.RIGHT, true);
			}
			else if (code == KeyEvent.VK_UP || code == KeyEvent.VK_W)
			{
				//player.setMoving(Actor.BACK, true);
				player.jump();
			}
			else if (code == KeyEvent.VK_DOWN || code == KeyEvent.VK_S)
			{
				player.setMoving(Actor.FRONT, true);
			}
			else if (code == KeyEvent.VK_SHIFT)
			{
				player.setSprinting(true);
			}
			
			else if (code == KeyEvent.VK_F1)
			{
				tdg.getDisplay().saveImage();
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e)
	{
		int code = e.getKeyCode();
		
		Player player = tdg.getPlayer();
		
		if (player != null)
		{
			if (code == KeyEvent.VK_LEFT || code == KeyEvent.VK_A)
			{
				player.setFacing(Actor.FRONT);
				player.setMoving(Actor.LEFT, false);
			}
			else if (code == KeyEvent.VK_RIGHT || code == KeyEvent.VK_D)
			{
				player.setFacing(Actor.FRONT);
				player.setMoving(Actor.RIGHT, false);
			}
			else if (code == KeyEvent.VK_UP || code == KeyEvent.VK_W)
			{
				player.setMoving(Actor.BACK, false);
			}
			else if (code == KeyEvent.VK_DOWN || code == KeyEvent.VK_S)
			{
				player.setMoving(Actor.FRONT, false);
			}
			else if (code == KeyEvent.VK_SHIFT)
			{
				player.setSprinting(false);
			}
		}
	}

	@Override
	public void keyTyped(KeyEvent e)
	{
		
	}
	
}