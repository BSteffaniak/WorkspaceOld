package net.foxycorndog.thedigginggame.handlers;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import net.foxycorndog.thedigginggame.Display;
import net.foxycorndog.thedigginggame.TheDiggingGame;
import net.foxycorndog.thedigginggame.tiles.Tile;

public class MouseHandler implements MouseListener
{
	private TheDiggingGame tdg;
	private Display        display;
	
	public MouseHandler(TheDiggingGame tdg)
	{
		this.tdg     = tdg;
		this.display = tdg.getDisplay();
	}
	
	@Override
	public void mouseClicked(MouseEvent e)
	{
		display.setMouseY(e.getY() + display.getYOffset());
		display.setMouseX(e.getX());
	}

	@Override
	public void mousePressed(MouseEvent e)
	{
		display.setMouseY(e.getY() + display.getYOffset());
		display.setMouseX(e.getX());
		
		display.setMouseButtonPressed(e.getButton());
	}

	@Override
	public void mouseReleased(MouseEvent e)
	{
		display.setMouseY(e.getY() + display.getYOffset());
		display.setMouseX(e.getX());
		
		if (display.getMouseButtonPressed() == MouseEvent.BUTTON1)
		{
			tdg.getMap().removeBlock((int)display.getGameCursor().getOriginRelativeX() / display.getBlockShowSize(), (int)display.getGameCursor().getOriginRelativeY() / display.getBlockShowSize());
		}
		else if (display.getMouseButtonPressed() == MouseEvent.BUTTON3)
		{
			tdg.getMap().createBlock((int)display.getGameCursor().getOriginRelativeX() / display.getBlockShowSize(), (int)display.getGameCursor().getOriginRelativeY() / display.getBlockShowSize(), Tile.STONE, false);
		}
	}

	@Override
	public void mouseEntered(MouseEvent e)
	{
		display.setMouseY(e.getY() + display.getYOffset());
		display.setMouseX(e.getX());
	}

	@Override
	public void mouseExited(MouseEvent e)
	{
		display.setMouseY(e.getY() + display.getYOffset());
		display.setMouseX(e.getX());
	}
}
