package net.foxycorndog.thedigginggame.tiles;

public class Tile
{
	private int         x, y, z, width, height;
	private int         rarity, highestDepth, lowestDepth;
	
	private boolean     collidable;
	
	private String      names[];
	
	private static int  tilesQuantity;
	
	private static Tile tiles[];
	
	private short       type;
	
	public static final short STONE = 0, DIRT = 1, GRASS = 2, COAL_ORE = 3,
			IRON_ORE = 4, DIAMOND_ORE = 5, GOLD_ORE = 6, GRAVEL = 7,
			BRICKS = 8, WOOD_PLANKS = 9, CHEST = 10, LADDER = 11;
	
	public Tile(int x, int y, int z, int width, int height, int rarity, int highestDepth, int lowestDepth, boolean collidable, String names[])
	{
		this.x            = x;
		this.y            = y;
		this.z            = z;
		
		this.width        = width;
		this.height       = height;
		
		this.rarity       = rarity;
		this.highestDepth = highestDepth;
		this.lowestDepth  = lowestDepth;
		
		this.collidable   = collidable;
		
		this.names        = names;
		
		this.type = (short)tilesQuantity;
		
		tilesQuantity ++;
	}
	
	public static void init()
	{
		tilesQuantity = 0;
		
		tiles         = new Tile[256];
		
		initTiles();
	}
	
	private static void initTiles()
	{
		tiles[STONE      ] = new Stone     (0, 0,  0, 1, 1,     1,  500, -9000, true,  new String[] { "Stone", "Smooth Stone" });
		tiles[DIRT       ] = new Dirt      (2, 0,  0, 1, 1,    15,  500, -9000, true,  new String[] { "Dirt" });
		tiles[GRASS      ] = new Grass     (1, 0,  0, 1, 1,    -1,    0,     0, true,  new String[] { "Grass" });
		tiles[COAL_ORE   ] = new CoalOre   (3, 0,  0, 1, 1,    80,    0, -9000, true,  new String[] { "Coal Ore" });
		tiles[IRON_ORE   ] = new IronOre   (4, 0,  0, 1, 1,    80,  500, -9000, true,  new String[] { "Iron Ore" });
		tiles[DIAMOND_ORE] = new DiamondOre(5, 0,  0, 1, 1, 10000, -250, -9000, true,  new String[] { "Diamond Ore" });
		tiles[GOLD_ORE   ] = new GoldOre   (6, 0,  0, 1, 1,  5000, -200, -9000, true,  new String[] { "Gold Ore" });
		tiles[GRAVEL     ] = new Gravel    (7, 1,  0, 1, 1,    15,  500, -9000, true,  new String[] { "Gravel" });
		tiles[BRICKS     ] = new Bricks    (1, 2,  0, 1, 1,    -1,    0,     0, true,  new String[] { "Bricks", "Brick", "Brick Wall" });
		tiles[WOOD_PLANKS] = new WoodPlanks(0, 1,  0, 1, 1,    -1,    0,     0, true,  new String[] { "Wood Planks", "Wooden Planks" });
		tiles[CHEST      ] = new Chest     (6, 2,  0, 1, 1,    -1,    0,     0, true,  new String[] { "Chest", "Wood Chest", "Wooden Chest" });
		tiles[LADDER     ] = new Ladder    (2, 3, -3, 1, 1,    -1,    0,     0, false, new String[] { "Ladder", "Latter" });
	}
	
	public static Tile getTile(int id)
	{
		return tiles[id];
	}
	
	public static int getTilesQuantity()
	{
		return tilesQuantity;
	}
	
	public int getX()
	{
		return x;
	}
	
	public int getY()
	{
		return y;
	}
	
	public int getZ()
	{
		return z;
	}
	
	public int getWidth()
	{
		return width;
	}
	
	public int getHeight()
	{
		return height;
	}
	
	public int getRarity()
	{
		return rarity;
	}
	
	public int getHighestDepth()
	{
		return highestDepth;
	}
	
	public int getLowestDepth()
	{
		return lowestDepth;
	}
	
	public String[] getNames()
	{
		return names;
	}
	
	public short getType()
	{
		return type;
	}
	
	public boolean getCollidable()
	{
		return collidable;
	}
}