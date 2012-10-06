package net.foxycorndog.p1xeland.items;

import net.foxycorndog.jdoutil.StringUtil;
import net.foxycorndog.p1xeland.items.tiles.Tile;

public enum Item
{
	WOODEN_HOE     (20, 8, 1, 1, 1, 45f, 2f, 0,     ItemType.HOE),
	STONE_HOE      (21, 8, 1, 1, 1, 45f, 2f, 0,     ItemType.HOE),
	IRON_HOE       (22, 8, 1, 1, 1, 45f, 2f, 0,     ItemType.HOE),
	DIAMOND_HOE    (23, 8, 1, 1, 1, 45f, 2f, 0,     ItemType.HOE),
	GOLD_HOE       (24, 8, 1, 1, 1, 45f, 2f, 0,     ItemType.HOE),
	
	WOODEN_AXE     (20, 7, 1, 1, 1, 45f, 2f, 1.75f, ItemType.AXE),
	STONE_AXE      (21, 7, 1, 1, 1, 45f, 2f, 2.5f,  ItemType.AXE),
	IRON_AXE       (22, 7, 1, 1, 1, 45f, 2f, 3f,    ItemType.AXE),
	DIAMOND_AXE    (23, 7, 1, 1, 1, 45f, 2f, 4f,    ItemType.AXE),
	GOLD_AXE       (24, 7, 1, 1, 1, 45f, 2f, 1.75f, ItemType.AXE),
	
	WOODEN_PICKAXE (20, 6, 1, 1, 1, 45f, 2f, 3.5f,  ItemType.PICKAXE),
	STONE_PICKAXE  (21, 6, 1, 1, 1, 45f, 2f, 6,     ItemType.PICKAXE),
	IRON_PICKAXE   (22, 6, 1, 1, 1, 45f, 2f, 8f,    ItemType.PICKAXE),
	DIAMOND_PICKAXE(23, 6, 1, 1, 1, 45f, 2f, 10f,   ItemType.PICKAXE),
	GOLD_PICKAXE   (24, 6, 1, 1, 1, 45f, 2f, 3.5f,  ItemType.PICKAXE),
	
	WOODEN_SHOVEL  (20, 5, 1, 1, 1, 45f, 2f, 1.5f,  ItemType.SHOVEL),
	STONE_SHOVEL   (21, 5, 1, 1, 1, 45f, 2f, 2,     ItemType.SHOVEL),
	IRON_SHOVEL    (22, 5, 1, 1, 1, 45f, 2f, 2.5f,  ItemType.SHOVEL),
	DIAMOND_SHOVEL (23, 5, 1, 1, 1, 45f, 2f, 3f,    ItemType.SHOVEL),
	GOLD_SHOVEL    (24, 5, 1, 1, 1, 45f, 2f, 1.5f,  ItemType.SHOVEL),
	
	WOODEN_SWORD   (20, 4, 1, 1, 1, 45f, 2f, 1.5f,  ItemType.SWORD),
	STONE_SWORD    (21, 4, 1, 1, 1, 45f, 2f, 2,     ItemType.SWORD),
	IRON_SWORD     (22, 4, 1, 1, 1, 45f, 2f, 2.5f,  ItemType.SWORD),
	DIAMOND_SWORD  (23, 4, 1, 1, 1, 45f, 2f, 3f,    ItemType.SWORD),
	GOLD_SWORD     (24, 4, 1, 1, 1, 45f, 2f, 1.5f,  ItemType.SWORD);
	
	private int      x, y;
	private int      width, height;
	
	private float    rotation;
	private float    scale;
	private float    power;
	
	private ItemType type;
	
	private String   itemName;
	
	public enum ItemType
	{
		PICKAXE, SHOVEL, AXE, SWORD, HOE;
	}
	
	private Item(int x, int y, int width, int height, int stackLimit, float rotation, float scale, float power, ItemType type)
	{
		this.x        = x;
		this.y        = y;
		this.width    = width;
		this.height   = height;
		
		this.rotation = rotation;
		this.scale    = scale;
		this.power    = power;
		
		this.type     = type;
		
		this.itemName = StringUtil.upperCaseEachWord(toString().replace("_", " "));
	}
	
	public int getWidth()
	{
		return width;
	}
	
	public int getHeight()
	{
		return height;
	}
	
	public float getPower()
	{
		return power;
	}
	
	public float getRotation()
	{
		return rotation;
	}
	public float getScale()
	{
		return scale;
	}
	
	public ItemType getType()
	{
		return type;
	}
	
	public float[] getOffsets()
	{
		return Tile.getTerrain().getImageOffsetsf(x, y, width, height);
	}
	
	public String getName()
	{
		return itemName;
	}
	
	public static Item getItem(String name)
	{
		for (Item item : Item.values())
		{
			if (item.getName().equalsIgnoreCase(name))
			{
				return item;
			}
		}
		
		return null;
	}
}