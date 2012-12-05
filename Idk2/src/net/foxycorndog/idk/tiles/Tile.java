package net.foxycorndog.idk.tiles;

import static net.foxycorndog.idk.Idk.OBJECT_SIZE;
import static net.foxycorndog.idk.Idk.TEXTURE_SIZE;
import static net.foxycorndog.idk.Idk.scale;
import static net.foxycorndog.idk.Idk.tileSize;
import net.foxycorndog.jdoogl.GL;
import net.foxycorndog.jdoogl.image.imagemap.SpriteSheet;

public class Tile
{
	private int         x, y, z;
	private int         relativeX, relativeY;
	private int         width, height;
	private int         color;
	
	private boolean     collidable;
	
	private String      names[];
	
	private Tile        subTiles[];
	
	private static byte tilesQuantity;
	
	private static Tile tiles[];
	
	private byte        id;
	
	private static int staticIndex;

	public static enum TileId
	{
		BLANK, GRASS, DIRT, SIDE_WALK, PORTAL, RED_FLOWER, YELLOW_FLOWER,
				WATER, WOOD_TILE, TREE, TREE_TOP, TREE_BOTTOM, CONCRETE,
				SAND;
		
		private int index;
		
		private TileId()
		{
			this.index = ++ staticIndex;
		}
		
		public int getIndex()
		{
			return index;
		}
	}
	
	/**
	* The static initialization method that initializes and allocates
	* space for the static tiles array.
	* 
	* This method must be called before initTiles() is called.
	*/
	static
	{
		tilesQuantity = 0;
		
		tiles         = new Tile[256];
		
		initTiles();
	}
	
	/**
	* Creates a new Tile used for adding to maps to walk or collide
	* into. They hold several variables, including position on
	* the sprite-sheet, width, height, color, whether its collidable, 
	* and a few names that it is called.
	* 
	* @param x The x position it is found on the sprite-sheet in pixels
	* 		divided by 10.
	* @param y The y position it is found on the sprite-sheet in pixels
	* 		divided by 10.
	* @param z The z position that the tile is to be rendered to the
	* 		frame with.
	* @param width The width of the tile in pixels divided by 10.
	* @param height The height of the tile in pixels divided by 10.
	* @param color The color that is connected to the tile that is
	* 		used to identify it when it is read off of a map.
	* @param collidable Whether the tile should be collidable or not.
	* @param names Some names that it can be called.
	*/
	public Tile(int x, int y, int z, int relativeX, int relativeY, int width, int height, int color, boolean collidable, String names[])
	{
		this.x            = x;
		this.y            = y;
		this.z            = z;
		
		this.relativeX    = relativeX;
		this.relativeY    = relativeY;
		
		this.width        = width;
		this.height       = height;
		
		this.collidable   = collidable;
		
		this.names        = names;
		
		this.color        = color;
		
		this.id           = ++ tilesQuantity;
	}
	
	public void initTile()
	{
		
	}
	
	public static void setTile(int index, Tile tile)
	{
		tiles[index] = tile;
	}
	
	public static void setTile(TileId index, Tile tile)
	{
		tiles[index.getIndex()] = tile;
	}
	
	/**
	* Static method used for instantiating new Tiles and assigning
	* them to their index of the static tiles array.
	*/
	private static void initTiles()
	{
		(tiles[TileId.BLANK.getIndex()]         = new Tile(0, 0, -10, 0, 0, 1, 1,  0x000000, true,  new String[] { "Blank", "Black" })).initTile();
		(tiles[TileId.GRASS.getIndex()]         = new Tile(1, 0, -10, 0, 0, 1, 1,  0x009305, false, new String[] { "Grass" })).initTile();
		(tiles[TileId.DIRT.getIndex()]          = new Tile(2, 0, -10, 0, 0, 1, 1,  0x5C3C28, false, new String[] { "Dirt" })).initTile();
		(tiles[TileId.SIDE_WALK.getIndex()]     = new Tile(3, 0, -10, 0, 0, 1, 1,  0xA5A3A8, false, new String[] { "Sidewalk", "Side walk" })).initTile();
		(tiles[TileId.PORTAL.getIndex()]        = new Tile(4, 0, -10, 0, 0, 1, 1,  0xAD2929, true,  new String[] { "Portal" })).initTile();
		(tiles[TileId.RED_FLOWER.getIndex()]    = new Tile(0, 1, -9,  0, 0, 1, 1, -1,        false, new String[] { "Red Flower", "RedFlower", "Rose" })).initTile();
		(tiles[TileId.YELLOW_FLOWER.getIndex()] = new Tile(1, 1, -9,  0, 0, 1, 1, -1,        false, new String[] { "Yellow Flower", "YellowFlower", "Daisy" })).initTile();
		(tiles[TileId.WATER.getIndex()]         = new Tile(6, 0, -10, 0, 0, 1, 1,  0x007A85, false, new String[] { "Water" })).initTile();
		(tiles[TileId.WOOD_TILE.getIndex()]     = new Tile(5, 0, -10, 0, 0, 1, 1,  0x945E20, false, new String[] { "Wood Tile", "Wooden Tile" })).initTile();
		(tiles[TileId.TREE.getIndex()]          = new Tree(0, 2, -3,        1, 2, -1,        false, new String[] { "Tree" })).initTile();
		(tiles[TileId.CONCRETE.getIndex()]      = new Tile(7, 0, -10, 0, 0, 1, 1,  0xB0B0B0, false, new String[] { "Concrete", "Cement" })).initTile();
		(tiles[TileId.SAND.getIndex()]          = new Tile(8, 0, -10, 0, 0, 1, 1,  0xE2DBA3, false, new String[] { "Sand" })).initTile();
//		tiles[TREE_BOTTOM]   = new Tile(0, 3, -9,  1, 1, -1,        true,  new String[] { "Tree Bottom" });
	}
	
	public Tile[] getSubTiles()
	{
		return subTiles == null ? new Tile[] { this } : subTiles;
	}
	
	public void setSubTiles(Tile tiles[])
	{
		this.subTiles = tiles;
	}
	
	public float[] addVerticesToArray(int x, int y, int offsetX, int offsetY, int arrayWidth)
	{
		return GL.addRectVertexArrayf((x * tileSize) + (offsetX * scale), (y * tileSize) + (offsetY * scale), z, width * tileSize, height * tileSize, (x + y * arrayWidth) * OBJECT_SIZE, null);
	}
	
	public float[] addTextureToArray(int x, int y, int arrayWidth, SpriteSheet sprites)
	{
		return GL.addRectTextureArrayf(sprites, getX(), getY(), width, height, (x + y * arrayWidth) * TEXTURE_SIZE, null);
	}
	
	public void addIdToArray(int x, int y, byte array[], int arrayWidth)
	{
		array[x + y * arrayWidth] = id;
	}
	
	public void addTileOffsets(int x, int y, byte offsetX, byte offsetY, byte array[][], int arrayWidth)
	{
		array[x + y * arrayWidth] = new byte[] { offsetX, offsetY };
	}
	
	public int getRelativeX()
	{
		return relativeX;
	}
	
	public int getRelativeY()
	{
		return relativeY;
	}
	
	/**
	* Method used for retrieving a Tile by its integer id.
	* 
	* @param id The id of the Tile.
	* @return The Tile with the specified id.
	*/
	public static Tile getTileById(int id)
	{
		return tiles[id];
	}
	
	/**
	* Method used for getting the Tile id by sending a
	* color integer to match with each of the Tile's colors.
	* 
	* @param color The color of the Tile that you want the id from.
	* @return The id of Tile with the specified color.
	*/
	public static byte getTileIdByColor(int color)
	{
		for (Tile tile : tiles)
		{
			if (tile != null && tile.getColor() == color)
			{
				return tile.getId();
			}
		}
		
		return 0;
	}
	
	/**
	* Get the amount of different Tiles in the tiles array.
	* 
	* @return The amount of Tiles in the static tiles array.
	*/
	public static int getTilesQuantity()
	{
		return tilesQuantity;
	}
	
	/**
	* Method used for getting the x position of the Tile on the
	* sprite-sheet.
	* 
	* @return The x position of the Tile in pixels divided by 10.
	*/
	public int getX()
	{
		return x;
	}
	
	/**
	* Method used for getting the y position of the Tile on the
	* sprite-sheet.
	* 
	* @return The y position of the Tile in pixels divided by 10.
	*/
	public int getY()
	{
		return y;
	}
	
	/**
	* Method used for retrieving the z position that the Tile is
	* rendered at.
	* 
	* @return The z position that the Tile is rendered at.
	*/
	public int getZ()
	{
		return z;
	}
	
	/**
	* Method used for retrieving the width of the Tile on the
	* sprite-sheet.
	* 
	* @return The width of the Tile in pixels divided by 10.
	*/
	public int getWidth()
	{
		return width;
	}
	
	/**
	* Method used for retrieving the height of the Tile on the
	* sprite-sheet.
	* 
	* @return The height of the Tile in pixels divided by 10.
	*/
	public int getHeight()
	{
		return height;
	}
	
	/**
	* Method used for getting the scaled width of the Tile. The scaled
	* width of the Tile is the amount of pixels that the screen shows
	* a tile as on the display.
	* 
	* @return The scaled width of the Tile in pixels.
	*/
	public float getScaledWidth()
	{
		return width * tileSize;
	}
	
	/**
	* Method used for getting the scaled height of the Tile. The scaled
	* height of the Tile is the amount of pixels that the screen shows
	* a tile as on the display.
	* 
	* @return The scaled height of the Tile in pixels.
	*/
	public float getScaledHeight()
	{
		return height * tileSize;
	}
	
	/**
	* Method used for getting the common names that the Tile may
	* be called.
	* 
	* @return The names in the form of an array of Strings.
	*/
	public String[] getNames()
	{
		return names;
	}
	
	/**
	* Get the id of the Tile object.
	* 
	* @return The byte format id of the Tile.
	*/
	public byte getId()
	{
		return id;
	}
	
	/**
	* Method used for getting the integer color associated with
	* the Tile.
	* 
	* @return The color in hexadecimal integer form.
	*/
	public int getColor()
	{
		return color;
	}
	
	/**
	* Method used for getting whether the Tile is collidable with
	* an Actor not not.
	* 
	* @return Whether the Tile is collidable with an Actor.
	*/
	public boolean isCollidable()
	{
		return collidable;
	}
}