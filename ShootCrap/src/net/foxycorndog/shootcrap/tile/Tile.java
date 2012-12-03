package net.foxycorndog.shootcrap.tile;

import net.foxycorndog.jdoogl.GL;
import net.foxycorndog.jdoogl.image.imagemap.SpriteSheet;

public class Tile
{
	private int                color;
	private int                x, y, width, height;
	
	private static SpriteSheet sprites;
	
	private static Tile        tiles[];
	
	private static final int   tileSize = 16;
	
	static
	{
		tiles = new Tile[]
		{
			new Tile(0, 0, 1, 1, 0x065C1C), // Grass
			new Tile(1, 0, 1, 1, 0x9B9B9B), // Sidewalk
			new Tile(2, 0, 1, 1, 0x323232)  // Road
		};
		
		sprites = new SpriteSheet("res/images/tiles.png", 10, 10);
	}
	
	public Tile(int x, int y, int width, int height, int color)
	{
		this.x      = x;
		this.y      = y;
		this.width  = width;
		this.height = height;
		this.color  = color;
	}
	
	public float[] getVertices(int xOff, int yOff)
	{
		return GL.addRectVertexArrayf(xOff * tileSize, yOff * tileSize, tileSize, tileSize, 0, null);
	}
	
	public float[] getTextureCoords()
	{
		return GL.addRectTextureArrayf(sprites, x, y, width, height, 0, null);
	}
	
	public int getColor()
	{
		return color;
	}
	
	public static Tile getTile(int color)
	{
		for (Tile tile : tiles)
		{
			if (tile.getColor() == color)
			{
				return tile;
			}
		}
		
		return null;
	}
	
	public static SpriteSheet getSprites()
	{
		return sprites;
	}
	
	public static int getTileSize()
	{
		return tileSize;
	}
}