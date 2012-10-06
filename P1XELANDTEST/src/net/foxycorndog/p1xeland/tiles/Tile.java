package net.foxycorndog.p1xeland.tiles;

import net.foxycorndog.jdoogl.image.imagemap.SpriteSheet;

public enum Tile
{
	STONE        (0, 0, 1, 1,     0, true),
	GRASS        (1, 0, 1, 1,    -1, true),
	DIRT         (2, 0, 1, 1,    25, true),
	COAL_ORE     (3, 0, 1, 1,   250, true),
	IRON_ORE     (4, 0, 1, 1,  1000, true),
	DIAMOND_ORE  (5, 0, 1, 1, 27800, true),
	GOLD_ORE     (6, 0, 1, 1, 25600, true),
	GRAVEL       (7, 1, 1, 1,    35, true),
	RUBY_ORE     (8, 0, 1, 1,  4400, true),
	EMERALD_ORE  (9, 0, 1, 1,  4600, true),
	
	WOODEN_PLANKS(0, 1, 1, 1,    -1, true),
	COBBLESTONE  (1, 1, 1, 1,    -1, true),
	
	LOG          (3, 0, 1, 1,    -1, true);
	
	private int                 x, y;
	private int                 width, height;
	private int                 rarity;
	private int                 id;
	
	private boolean             collidable;
	
	
	private static boolean      initialized;
	
	private static int          staticId = 0;
	
	private static  SpriteSheet terrain;
	
	private Tile(int x, int y, int width, int height, int rarity, boolean collidable)
	{
		this.x          = x;
		this.y          = y;
		this.width      = width;
		this.height     = height;
		this.rarity     = rarity;
		
		this.collidable = collidable;
		
		init();
	}
	
	private void init()
	{
		if (!initialized)
		{
			terrain = new SpriteSheet("res/images/texturepacks/16bit/Minecraft/minecraft.png", "PNG", 18, 18, true);
			
			initialized = true;
		}
		
		this.id         = staticId ++;
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
	
	public boolean isCollidable()
	{
		return collidable;
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
}