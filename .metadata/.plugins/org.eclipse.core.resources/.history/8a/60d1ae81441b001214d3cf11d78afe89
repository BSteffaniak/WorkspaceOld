package net.foxycorndog.p1xeland.actors;

import java.util.ArrayList;

import net.foxycorndog.jdoogl.Color;
import net.foxycorndog.jdoogl.GL;
import net.foxycorndog.jdoogl.components.Frame;
import net.foxycorndog.jdoogl.image.imagemap.Texture;
import net.foxycorndog.jdoutil.LightBuffer;
import net.foxycorndog.jdoutil.Task;
import net.foxycorndog.p1xeland.P1xeland;
import net.foxycorndog.p1xeland.items.Item;
import net.foxycorndog.p1xeland.items.tiles.Tile;

public class Inventory
{
	private int               quantities[];
	
	private LightBuffer       itemsTexturesBuffer;
	
	private ArrayList<Object> items;
	
	private static boolean    initialized;
	
	private static Texture    inventoryGUI;
	
	private static LightBuffer     verticesBuffer, texturesBuffer, itemsVerticesBuffer;
	
	public Inventory()
	{
		items = new ArrayList<Object>(40);
		
		for (int i = 0; i < 40; i ++)
		{
			items.add(null);
		}
		
		quantities = new int[items.size()];
		
		init();
		
		itemsTexturesBuffer = new LightBuffer(4 * 2 * 40);
		
		for (int i = 0; i < 40; i ++)
		{
			itemsTexturesBuffer.setData(i * 4 * 2, GL.addRectVertexArrayf(0, 0, 0, 0, 0, null));
		}
	}
	
	public void init()
	{
		if (!initialized)
		{
			inventoryGUI   = new Texture("res/images/GUI/Inventory.png");
			
			verticesBuffer = new LightBuffer(4 * 2);
			texturesBuffer = new LightBuffer(4 * 2);
			itemsVerticesBuffer = new LightBuffer(4 * 2 * 40);
			
			verticesBuffer.setData(0, GL.addRectVertexArrayf(0, 0, 115, 80, 0, null));
			texturesBuffer.setData(0, GL.addRectTextureArrayf(inventoryGUI, 0, null));
			
			for (int i = 0; i < 40; i ++)
			{
				itemsVerticesBuffer.setData(i * 4 * 2, GL.addRectVertexArrayf((i % 10) * (8 + 3), (i / 10) * (8 + 4), 8, 8, 0, null));
			}
			
			initialized = true;
		}
	}
	
	public void addObject(Object item)
	{
		addObject(item, 1);
	}
	
	public void addObject(Object item, int quantity)
	{
		if (item == null)
		{
			return;
		}
		
		int index = items.indexOf(item);
		
		if (index == -1)
		{
			for (int i = 0; i < items.size(); i ++)
			{
				if (items.get(i) == null)
				{
					items.set(i, item);
					quantities[i] = quantity;
					
					break;
				}
			}
		}
		else
		{
			if (quantities[index] >= 640)
			{
				boolean found = false;
				
				for (int i = 0; i < items.size(); i ++)
				{
					if (items.get(i) != null && items.get(i).equals(item) && i != index && quantities[i] + quantity - 1 < 640)
					{
						quantities[i] += quantity;
						
						found = true;
						
						break;
					}
				}
				
				if (!found)
				{
					for (int i = 0; i < items.size(); i ++)
					{
						if (items.get(i) == null)
						{
							items.set(i, item);
							
							quantities[i] += quantity;
							
							break;
						}
					}
				}
			}
			else
			{
				quantities[index] += quantity;
			}
		}
	}
	
	public void removeObject(Object item)
	{
		removeObject(item, 1);
	}
	
//	public void removeItem(Object item, int quantity)
//	{
//		int index = items.indexOf(item);
//		
//		if (index > -1)
//		{
//			items.set(index, null);
//			quantities[index] = 0;
//		}
//	}
	
//	public void removeItem(int index, int quantity)
//	{
//		if (index >= items.size() || index < 0 || items.get(index) == null)
//		{
//			return;
//		}
//		
//		quantities[index] -= quantity;
//		
//		if (quantities[index] <= 0)
//		{
//			items.set(index, null);
//			
//			quantities[index] = 0;
//		}
//	}
	
	public void removeObject(Object item, int quantity)
	{
		if (quantity <= 0 || item == null)
		{
			return;
		}
		
		int index    = items.indexOf(item);
		
		int leftOver = 0;
		
		if (index > -1)
		{
			int quan = quantities[index];
			
			if (quantity >= quan)
			{
				leftOver = quantity - quan;
				
				items.set(index, null);
				quantities[index] = 0;
			}
			else
			{
				quantities[index] -= quantity;
			}
		}
		
		if (leftOver > 0)
		{
			removeObject(item, leftOver);
		}
	}
	
	public void removeObject(int index, int quantity)
	{
		if (quantity <= 0 || index < 0 || index >= items.size())
		{
			return;
		}
		
		int quan = quantities[index];
		
		if (quantity >= quan)
		{
			items.set(index, null);
			quantities[index] = 0;
		}
		else
		{
			quantities[index] -= quantity;
		}
	}
	
	public static int getWidth()
	{
		return inventoryGUI.getWidth();
	}
	
	public static int getHeight()
	{
		return inventoryGUI.getHeight();
	}
	
	public Object getItem(int index)
	{
		return items.get(index);
	}
	
	public int getQuantity(int index)
	{
		return quantities[index];
	}
	
	public void refreshInventory()
	{
		for (int i = 0; i < items.size(); i ++)
		{
			Object obj = getItem(i);
			
			float offsets[] = null;
			
			if (obj instanceof Tile)
			{
				Tile tile = (Tile)obj;
				
				offsets = tile.getOffsets();
				
				itemsTexturesBuffer.setData(i * 4 * 2, GL.addRectTextureArrayf(offsets, 0, null));
			}
			else if (obj instanceof Item)
			{
				Item item = (Item)obj;
				
				offsets = item.getOffsets();
				
				itemsTexturesBuffer.setData(i * 4 * 2, GL.addRectTextureArrayf(offsets, 0, null));
			}
		}
	}
	
	public void render()
	{
		GL.beginManipulation();
		{
			GL.scalef(1 / P1xeland.scale, 1 / P1xeland.scale, 1);
			GL.scalef(4, 4, 1);
			
			GL.translatef(Frame.getCenterX() / 4 - (Inventory.getWidth() / 2), Frame.getCenterY() / 4 - (Inventory.getHeight() / 2), 3);
			
			GL.renderQuad(verticesBuffer, texturesBuffer, inventoryGUI);
			
			GL.translatef(4, 4, 0.1f);
			
			GL.renderQuads(itemsVerticesBuffer, itemsTexturesBuffer, Tile.getTerrain(), 0, 40, new Task()
			{
				public boolean run(int index)
				{
					return getItem(index) != null;
				}
			});
			
			GL.translatef(0, 0, 0.1f);
			
			for (int i = 0; i < items.size(); i ++)
			{
				Object item = getItem(i);
				
				if (item != null)
				{
					Frame.renderText((i % 10) * (8 + 3) - 1, (getHeight()) - ((i / 10) + 1) * (8 + 4) - 4 - 1, "" + quantities[i], Color.WHITE, .25f);
				}
			}
		}
		GL.endManipulation();
	}
}