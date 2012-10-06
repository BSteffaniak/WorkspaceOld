package net.foxycorndog.invasion.levels;

import net.foxycorndog.invasion.Invasion;
import net.foxycorndog.invasion.entities.Alien;
import net.foxycorndog.invasion.entities.Bullet;
import net.foxycorndog.invasion.entities.Ship;
import net.foxycorndog.jdoogl.components.Frame;

public class Level
{
	private boolean finished;
	
	private int     cols, rows;
	
	public Level(int cols, int rows)
	{
		this.cols = cols;
		this.rows = rows;
	}
	
	public void load()
	{
		synchronized (Invasion.entities)
		{
			for (int i = Invasion.entities.size() - 1; i >= 0; i --)
			{
				synchronized (Invasion.entities)
				{
					if (Invasion.entities.get(i) instanceof Bullet)
					{
						Invasion.removeEntity(Invasion.entities.get(i));
					}
					else if (Invasion.entities.get(i) instanceof Alien)
					{
						Invasion.removeEntity(Invasion.entities.get(i));
					}
				}
			}
		}
		
		Invasion.aliens = new Ship[cols * rows];
		
		int groupWidth = 0;
		
		for (int i = 0; i < rows; i ++)
		{
			for (int j = 0; j < cols; j ++)
			{
				Alien alien = new Alien();
				
				Invasion.aliens[j + i * cols] = alien;
				
				groupWidth = (int)((alien.getScaledWidth() + Invasion.getScale()) * cols);
				
				alien.setLocation(((int)(((Frame.getWidth() - groupWidth) / 2) + j * (alien.getScaledWidth() + Invasion.getScale())) / (int)Invasion.getScale()) * (int)Invasion.getScale(), Frame.getHeight() - ((i + 1) * (alien.getScaledHeight() + Invasion.getScale())));
				
				Invasion.entities.add(alien);
			}
		}
	}
	
	public boolean isFinished()
	{
		return finished;
	}
	
	public void setFinished(boolean finished)
	{
		this.finished = finished;
	}
	
	public int getCols()
	{
		return cols;
	}
	
	public int getRows()
	{
		return rows;
	}
	
	public int getGroupWidth()
	{
		Ship aliens[] = Invasion.aliens;
		
		int lengths[] = new int[rows];
		
		Ship alien    = null;
		
		for (int yy = 0; yy < rows; yy ++)
		{
			int start = -1;
			int end   = -1;
			
			for (int xx = 0; xx < cols; xx ++)
			{
				if (aliens[xx + yy * cols] != null)
				{
					alien = aliens[xx + yy * cols];
					
					if (start == -1)
					{
						start = xx;
					}
					else
					{
						end   = xx;
					}
				}
			}
			
			if (end == -1)
			{
				end = start;
			}
			
			lengths[yy] = end - start + 1;
		}
		
		int length = 0;
		
		for (int i = 0; i < lengths.length; i ++)
		{
			if (lengths[i] > length)
			{
				length = lengths[i];
			}
		}
		
		return alien == null ? 0 : (int)(length * alien.getScaledWidth() + ((length - 1) * Invasion.getScale()));
	}
	
	public int getGroupX()
	{
		Ship aliens[] = Invasion.aliens;
		
		Ship alien    = null;
		
		int starts[]  = new int[rows];
		
		for (int i = 0; i < starts.length; i ++)
		{
			starts[i] = Integer.MAX_VALUE;
		}
		
		for (int yy = 0; yy < rows; yy ++)
		{
			for (int xx = 0; xx < cols; xx ++)
			{
				if (aliens[xx + yy * cols] != null)
				{
					alien = aliens[xx + yy * cols];
					
					if (starts[yy] == Integer.MAX_VALUE)
					{
						starts[yy] = (int)(alien.getX());
						
						break;
					}
				}
			}
		}
		
		int start = Integer.MAX_VALUE;
		
		for (int i = 0; i < starts.length; i ++)
		{
			if (starts[i] < start)
			{
				start = starts[i];
			}
		}
		
		return start;
	}
}