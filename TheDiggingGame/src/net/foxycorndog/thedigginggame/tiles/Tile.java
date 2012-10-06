package net.foxycorndog.thedigginggame.tiles;

import net.foxycorndog.thedigginggame.Display;
import net.foxycorndog.thedigginggame.TheDiggingGame;
import net.foxycorndog.thedigginggame.map.SpriteTemplate;

public class Tile
{
	private int     width, height;
	private int     color;
	private boolean collidable;
	private int     pixels[];
	
	private static int blockSize;
	
	private static SpriteTemplate st;
	
	private static Tile tiles[] = new Tile[256];
	
	public static final int STONE = 0, DIRT = 1, GRASS = 2, GRAVEL = 3, SAND = 4;
	
	public Tile(int blockSize)
	{
		Tile.blockSize = blockSize;
		
		init();
	}
	
	public Tile(int xo, int yo, int w, int h, boolean collidable, int color)
	{
		this.width  = w;
		this.height = h;
		
		this.collidable = collidable;
		
		this.color = color;
		
		pixels = st.getSubimage(xo * blockSize, yo * blockSize, w * blockSize, h * blockSize);
	}
	
	private void init()
	{
		st = new SpriteTemplate("res/images/texturepacks/8bit/8bit/8bitsprites.png");
		
		tiles[STONE]  = new Stone();
		tiles[DIRT]   = new Dirt();
		tiles[GRASS]  = new Grass();
		tiles[GRAVEL] = new Gravel();
		tiles[SAND]   = new Sand();
	}
	
	public void render(Display display)
	{
		for (int yy = 0; yy < display.getHeight(); yy ++)
		{
			for (int xx = 0; xx < display.getWidth(); xx ++)
			{
				
			}
		}
	}
	
	public int getWidth()
	{
		return width;
	}
	
	public int getHeight()
	{
		return height;
	}
	
	public static Tile getTile(int type)
	{
		return tiles[type];
	}
	
	public int[] getPixels()
	{
		return pixels;
	}
	
	public int getBlockSize()
	{
		return blockSize;
	}
}