package net.foxycorndog.thedigginggame;

import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import net.foxycorndog.presto2d.util.SpriteSheet;
import net.foxycorndog.thedigginggame.actors.Player;
import net.foxycorndog.thedigginggame.map.Map;
import net.foxycorndog.thedigginggame.tiles.Tile;

public class TheDiggingGame
{
	private Display     display;
	
	private Map         map;
	
	private Player      p;
	
	public static float tileSize;
	public static float gravity;
	
	private static SpriteSheet terrain;
	
	public static final String GAME_TITLE = "The Digging Game";
	
	public static TheDiggingGame thisGame;
	
	public TheDiggingGame()
	{
		thisGame = this;
		
		tileSize = 32;
		
		gravity  = 1.2f;
	}
	
	public static void main(String args[])
	{
		TheDiggingGame tdg = new TheDiggingGame();
		
		int width  = 640;
		int height = 512;
		
		try
		{
			width  = Integer.valueOf(args[0]);
			height = Integer.valueOf(args[1]);
		}
		catch(NumberFormatException ex)
		{

		}
		catch(IndexOutOfBoundsException ex)
		{

		}
		
		tdg.init(width, height, false);
	}
	
	public void init(int width, int height, boolean applet)
	{
		
		
		display = new Display(width, height, this)
		{
			boolean b = false;
			
			public void render()
			{
				if (!b)
				{
					initTerrain();
					
					Tile.init();
					
					map = new Map(thisGame);
					map.init();
					
					Player.init();
					
					p = new Player(0, 32 * 12, display, map);
					
					b = true;
				}
				
				glPushMatrix();
				{
				//	glTranslatef((float)-getWidth() / 2f, (float)-getHeight() / 2f, (float)-getWidth());
					
					map.render();
					
					p.render();
					
					renderCursor();
				}
				glPopMatrix();
			}
		};
		
		display.init();
	}
	
	public static void initTerrain()
	{
		terrain = new SpriteSheet("res/images/texturepacks/16bit/Minecraft/16bitsprites.png", "PNG", 18, 18, true);
	}
	
	public SpriteSheet getTerrain()
	{
		return terrain;
	}
	
	public float getGravity()
	{
		return gravity;
	}
	
	public Display getDisplay()
	{
		return display;
	}
	
	public Map getMap()
	{
		return map;
	}
	
	public Player getPlayer()
	{
		return p;
	}
}