package net.foxycorndog.thedigginggame;

import net.foxycorndog.thedigginggame.actors.Player;
import net.foxycorndog.thedigginggame.map.Map;
import net.foxycorndog.thedigginggame.tiles.Tile;

public class TheDiggingGame
{
	private Display display;
	
	public static TheDiggingGame tdg;
	
	public static final String GAME_TITLE = "The Digging Game";
	
	private Map           map;
	private Player        player;
	
	public static boolean applet;
	
	public void init(int w, int h, boolean applet)
	{
		display = new Display(w, h, applet, this);
		display.init();
	}
	
	public static void main(String args[])
	{
		tdg = new TheDiggingGame();
		
		int width  = 640;
		int height = 512;
		
		try
		{
			width = Integer.valueOf(args[0]);
			height = Integer.valueOf(args[1]);
		}
		catch(NumberFormatException e)
		{
			System.out.println("hmm " + e);
		}
		catch(IndexOutOfBoundsException e)
		{
			e.printStackTrace();
		}
		
		tdg.init(width, height, false);
	}
	
	public Display getDisplay()
	{
		return display;
	}
	
	public void setMap(Map m)
	{
		map = m;
	}
	
	public Map getMap()
	{
		return map;
	}
	
	public void setPlayer(Player p)
	{
		player = p;
	}
	
	public Player getPlayer()
	{
		return player;
	}
}