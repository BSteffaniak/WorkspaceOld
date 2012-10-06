package net.foxycorndog.thedigginggame.handlers;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import net.foxycorndog.thedigginggame.Display;
import net.foxycorndog.thedigginggame.TheDiggingGame;
import net.foxycorndog.thedigginggame.tiles.Tile;

public class MouseMotionHandler implements MouseMotionListener
{
	private TheDiggingGame tdg;
	private Display        display;
	
	public MouseMotionHandler(TheDiggingGame tdg)
	{
		this.tdg     = tdg;
		this.display = tdg.getDisplay();
	}
	
	@Override
	public void mouseDragged(MouseEvent e)
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
	public void mouseMoved(MouseEvent e)
	{
		display.setMouseY(e.getY() + display.getYOffset());
		display.setMouseX(e.getX());
	}
}