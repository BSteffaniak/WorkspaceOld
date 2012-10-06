package net.foxycorndog.p1xeland.items.tiles;

import net.foxycorndog.jdoogl.image.imagemap.SpriteSheet;
import net.foxycorndog.jdoutil.ArrayUtil;
import net.foxycorndog.jdoutil.StringUtil;
import net.foxycorndog.p1xeland.items.Item;
import net.foxycorndog.p1xeland.items.Item.ItemType;

public enum Tile
{
	STONE        (0,  0, 1, 1,     0, 250, 64, true,  false, ItemType.PICKAXE, new int[] { 0, 1, 2 }),
	GRASS        (1,  0, 1, 1,    -1,  50, 64, true,  false, ItemType.SHOVEL,  new int[] { 0, 1, 2 }),
	DIRT         (2,  0, 1, 1,    25,  50, 64, true,  false, ItemType.SHOVEL,  new int[] { 0, 1, 2 }),
	COAL_ORE     (3,  0, 1, 1,   250, 350, 64, true,  false, ItemType.PICKAXE, new int[] { 0, 1, 2 }),
	IRON_ORE     (4,  0, 1, 1,  1000, 400, 64, true,  false, ItemType.PICKAXE, new int[] { 0, 1, 2 }),
	DIAMOND_ORE  (5,  0, 1, 1, 27800, 900, 64, true,  false, ItemType.PICKAXE, new int[] { 0, 1, 2 }),
	GOLD_ORE     (6,  0, 1, 1, 25600, 650, 64, true,  false, ItemType.PICKAXE, new int[] { 0, 1, 2 }),
	GRAVEL       (7,  1, 1, 1,    35,  45, 64, true,  false, ItemType.SHOVEL,  new int[] { 0, 1, 2 }),
	RUBY_ORE     (8,  0, 1, 1,  4400, 500, 64, true,  false, ItemType.PICKAXE, new int[] { 0, 1, 2 }),
	EMERALD_ORE  (9,  0, 1, 1,  4600, 550, 64, true,  false, ItemType.PICKAXE, new int[] { 0, 1, 2 }),
	
	WOODEN_PLANKS(0,  1, 1, 1,    -1, 100, 64, true,  false, ItemType.AXE,     new int[] { 0, 1, 2 }),
	BRICKS       (1,  2, 1, 1,    -1, 550, 64, true,  false, ItemType.PICKAXE, new int[] { 0, 1, 2 }),
	GLASS        (0,  2, 1, 1,    -1,  10, 64, true,  true,  ItemType.PICKAXE, new int[] { 0, 1, 2 }),
	COBBLESTONE  (1,  1, 1, 1,    -1, 200, 64, true,  false, ItemType.PICKAXE, new int[] { 0, 1, 2 }),
	
	LOG          (3,  2, 1, 1,    -1, 150, 64, true,  false, ItemType.AXE,     new int[] { 0, 1, 2 }),
	TNT          (4,  2, 1, 1,    -1,  40, 64, true,  false, null,             new int[] { 0, 1, 2 }),
	
	WOODEN_DOOR  (0,  3, 1, 2,    -1,  60, 4,  false, true, ItemType.AXE,      new int[] { 1 }),
	LADDER       (2,  3, 1, 1,    -1,  30, 64, false, true,  ItemType.AXE,     new int[] { 1 }),
	TORCH        (13, 1, 1, 1,    -1,   0, 64, false, true,  null,             new int[] { 1 });
	
	private boolean             collidable;
	private boolean             translucent;
	
	private int                 x, y;
	private int                 width, height;
	private int                 rarity;
	private int                 id;
	private int                 breakingPoint;
	private int                 stackLimit;
	
	private ItemType            type;
	
	private String              tileName;
	
	private int                 layers[];
	
	private static boolean      initialized;
	
	private static int          staticId = 0;
	
	private static  SpriteSheet terrain;
	
	private Tile(int x, int y, int width, int height, int rarity, int breakingPoint, int stackLimit, boolean collidable, boolean translucent, ItemType type, int layers[])
	{
		this.x             = x;
		this.y             = y;
		this.width         = width;
		this.height        = height;
		this.rarity        = rarity;
		this.breakingPoint = breakingPoint;
		this.stackLimit    = stackLimit;
		
		this.collidable    = collidable;
		this.translucent   = translucent;
		
		this.type          = type;
		
		this.tileName      = StringUtil.upperCaseEachWord(toString().replace("_", " ").toLowerCase());
		
		this.layers        = layers;
		
		init();
	}
	
	private void init()
	{
		if (!initialized)
		{
//			terrain = new SpriteSheet("res/images/texturepacks/8bit/Default/default.png", "PNG", 36, 18, true, false);
			terrain = new SpriteSheet("res/images/texturepacks/16bit/Minecraft/minecraft.png", "PNG", 36, 18, true, false);
			
			initialized = true;
		}
		
		this.id         = staticId ++;
	}
	
	public int getX()
	{
		return x;
	}
	
	public int getY()
	{
		return y;
	}
	
	public int getWidth()
	{
		return width;
	}
	
	public int getHeight()
	{
		return height;
	}
	
	public ItemType getType()
	{
		return type;
	}
	
	public float[] getOffsets()
	{
		return terrain.getImageOffsetsf(x, y, width, height);
	}
	
	public static SpriteSheet getTerrain()
	{
		return terrain;
	}
	
	public static void setTerrain(SpriteSheet terrain)
	{
		Tile.terrain = terrain;
	}
	
	public static void bindTerrain()
	{
		terrain.bind();
	}
	
	public static Tile getTile(int id)
	{
		for (Tile tile : Tile.values())
		{
			if (tile.getId() == id)
			{
				return tile;
			}
		}
		
		return null;
	}
	
	public int getId()
	{
		return id;
	}
	
	public int getRarity()
	{
		return rarity;
	}
	
	public int getBreakingPoint()
	{
		return breakingPoint;
	}
	
	public boolean isCollidable()
	{
		return collidable;
	}
	
	public boolean isTranslucent()
	{
		return translucent;
	}
	
	public boolean layerCompatible(int layer)
	{
		return ArrayUtil.contains(layers, layer);
	}
	
	public static Tile getRandomTile(int depth)
	{
		Tile tile;
		
		while (true)
		{
			int rand = (int)(Math.random() * Tile.values().length);
			tile = Tile.getTile(rand);
			
//			if (tile.getLowestDepth() <= depth && tile.getHighestDepth() >= depth)
//			{
				if (tile.getRarity() >= 0)
				{
					rand = (int)(Math.random() * (tile.getRarity() - depth));
					
					rand = rand < 0 ? 0 : rand;
					
					if (rand == 0)
					{
						return tile;
					}
				}
//			}
		}
		
//		for (Tile tile : Tile.values())
//		{
//			
//		}
		
//		return null;
	}
	
	public String getName()
	{
		return tileName;
	}
	
	public static Tile getTile(String name)
	{
		for (Tile tile : Tile.values())
		{
			if (tile.getName().equalsIgnoreCase(name))
			{
				return tile;
			}
		}
		
		return null;
	}
}