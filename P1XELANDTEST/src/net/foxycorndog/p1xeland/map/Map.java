package net.foxycorndog.p1xeland.map;

import java.util.ArrayList;

import net.foxycorndog.jdoogl.GL;
import net.foxycorndog.jdoogl.util.GeneralCollection;
import net.foxycorndog.p1xeland.P1xeland;
import net.foxycorndog.p1xeland.actors.Actor;
import net.foxycorndog.p1xeland.chunks.Chunk;
import net.foxycorndog.p1xeland.tiles.Tile;


public class Map
{
	private float                    x, y;
	
	private GeneralCollection<Chunk> chunks;
	
	public Map()
	{
		chunks = new GeneralCollection<Chunk>();
	}
	
	public boolean collided(Actor actor)
	{
		ArrayList<Chunk> elements = chunks.getElements();
		
		for (int i = 0; i < elements.size(); i ++)
		{
			Chunk chunk = elements.get(i);
			
			if (chunk.inRange(actor))
			{
				if (chunk.collided(actor))
				{
					return true;
				}
			}
		}
		
		return false;
	}
	
	public boolean addTile(int x, int y, Tile tile, boolean replace)
	{
		int relativeX = x / Chunk.WIDTH;
		int relativeY = y / Chunk.HEIGHT;
		
		relativeX = x < 0 ? relativeX - 1 : relativeX;
		relativeY = y < 0 ? relativeY - 1 : relativeY;
		
		x = x < 0 ? (Chunk.WIDTH  - Math.abs(x % Chunk.WIDTH))  % 16 : Math.abs(x % Chunk.WIDTH);
		y = y < 0 ? (Chunk.HEIGHT - Math.abs(y % Chunk.HEIGHT)) % 16 : Math.abs(y % Chunk.HEIGHT);
		
		relativeX = relativeX < 0 && x == 0 ? relativeX + 1 : relativeX;
		relativeY = relativeY < 0 && y == 0 ? relativeY + 1 : relativeY;
		
		try
		{
			return chunks.get(relativeX, relativeY).addTile(x, y, tile, replace);
		}
		catch (Exception ex)
		{
			System.out.println(ex);
		}
		
		return false;
	}
	
	public boolean removeTile(int x, int y)
	{
		int relativeX = x / Chunk.WIDTH;
		int relativeY = y / Chunk.HEIGHT;
		
		relativeX = x < 0 ? relativeX - 1 : relativeX;
		relativeY = y < 0 ? relativeY - 1 : relativeY;
		
		x = x < 0 ? (Chunk.WIDTH  - Math.abs(x % Chunk.WIDTH))  % 16 : Math.abs(x % Chunk.WIDTH);
		y = y < 0 ? (Chunk.HEIGHT - Math.abs(y % Chunk.HEIGHT)) % 16 : Math.abs(y % Chunk.HEIGHT);
		
		relativeX = relativeX < 0 && x == 0 ? relativeX + 1 : relativeX;
		relativeY = relativeY < 0 && y == 0 ? relativeY + 1 : relativeY;
		
		try
		{
			return chunks.get(relativeX, relativeY).removeTile(x, y);
		}
		catch (Exception ex)
		{
			System.out.println(ex);
		}
		
		return false;
	}
	
	public void render()
	{
		ArrayList<Chunk> elements = chunks.getElements();
		
		GL.beginManipulation();
		{
			GL.translatef(x, y, 0f);
			
			synchronized (elements)
			{
				for (int i = 0; i < elements.size(); i ++)
				{
					Chunk chunk = elements.get(i);
					
					chunk.render();
				}
			}
		}
		GL.endManipulation();
	}
	
	public float getX()
	{
		return x;
	}
	
	public float getY()
	{
		return y;
	}
	
	public void setLocation(float x, float y)
	{
		this.x = x;
		this.y = y;
	}
	
	public void move(float dx, float dy)
	{
		this.x += dx;
		this.y += dy;
	}
	
	public Chunk getChunk(int relativeX, int relativeY)
	{
		return chunks.get(relativeX, relativeY);
	}
	
	public void addChunk(int relativeX, int relativeY)
	{
		chunks.add(relativeX, relativeY, new Chunk(relativeX, relativeY));
	}
}