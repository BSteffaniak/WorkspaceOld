package net.foxycorndog.thedigginggame.map;

import net.foxycorndog.presto2d.graphics.PixelPanel;
import net.foxycorndog.thedigginggame.Display;
import net.foxycorndog.thedigginggame.TheDiggingGame;
import net.foxycorndog.thedigginggame.actors.Actor;
import net.foxycorndog.thedigginggame.tiles.Tile;

public class Map
{
	private Tile    tiles[];
	private int     width, height;
	private float   x, y;
	private boolean rendered;
	
	private TheDiggingGame tdg;
	private Display        display;
	
	public Map(int width, int height, float x, float y, TheDiggingGame tdg)
	{
		this.width  = width;
		this.height = height;
		
		tiles = new Tile[width * height];
		
		this.x = x;
		this.y = y;
		
		this.tdg     = tdg;
		this.display = tdg.getDisplay();
	}
	
	public void setTile(int x, int y, int type)
	{
		getTiles()[x + y * width] = Tile.getTile(type);
	}
	
	public void setTiles(int x, int y, int width, int height, int type)
	{
		int tx;
		int ty;
		
		for (int yy = 0; yy < height; yy ++)
		{
			for (int xx = 0; xx < width; xx ++)
			{
				tx = x + xx;
				ty = y + yy;
				
				if (tx + ty * width >= tiles.length || tx + ty * width < 0) continue;
				
				tiles[tx + ty * this.width] = Tile.getTile(type);
			}
		}
	}
	
	public void render(Display display)
	{
		if (!rendered)
		{
			PixelPanel pixels   = display.getTerrainPanel();
			
			int frameScaleWidth  = display.getWidth()  / display.getScale();
			int frameScaleHeight = display.getHeight() / display.getScale();
			
			int w   = frameScaleWidth  / display.getBlockSize();
			int h   = frameScaleHeight / display.getBlockSize();
			
			int frw = display.getWidth()  % display.getBlockShowSize();
			int frh = display.getHeight() % display.getBlockShowSize();
			
			frw = frw > 0 ? 1 : 0;
			frh = frh > 0 ? 1 : 0;
			
			int mapX = (int)(x / display.getScale());
			int mapY = (int)(y / display.getScale());
			
			int mrw  = mapX % display.getBlockSize();
			int mrh  = mapY % display.getBlockSize();
			
			int xo = 0;
			int yo = 0;
			
			int index = 0;
			Tile tile = null;
			
			for (int yy = h + frh; yy >= 0; yy --)
			{
				for (int xx = w + frw; xx >= 0; xx --)
				{
					xo = xx - (mapX / display.getBlockSize());
					yo = yy - (mapY / display.getBlockSize());
					
					index = xo + yo * width;
					
					if (xo < 0 || xo >= width || yo < 0 || yo >= height) continue;
					
					tile = tiles[index];
					
					if (tile == null) continue;
					
					pixels.getPixelGraphics().drawPixels(tile.getPixels(), tile.getBlockSize(), mrw + xx * display.getBlockSize(), mrh + yy * display.getBlockSize());
				}
			}
			
			rendered = true;
		}
	}
	
	public void clear(PixelPanel pixels)
	{
		if (!rendered)
		{
			pixels.getPixelGraphics().fillRect(0, 0, pixels.getWidth(), pixels.getHeight(), 0x00000000);
		}
	}
	
	public void createBlock(int x, int y, int type, boolean overwrite)
	{
		if (x < 0 || x >= width || y < 0 || y >= height) return;
		
		if (overwrite || tiles[x + y * width] == null)
		{
			tiles[x + y * width] = Tile.getTile(type);
		}
		
		rendered = false;
	}
	
	public void removeBlock(int x, int y)
	{
		if (x < 0 || x >= width || y < 0 || y >= height) return;
		
		tiles[x + y * width] = null;
		
		rendered = false;
	}
	
	public boolean collided(Actor a, float dx, float dy)
	{
		boolean collided = false;
		
		int mapX = (int)(x / a.getTdg().getDisplay().getScale());
		int mapY = (int)(y / a.getTdg().getDisplay().getScale());
		
		int mrw  = mapX % a.getTdg().getDisplay().getBlockSize();
		int mrh  = mapY % a.getTdg().getDisplay().getBlockSize();
		
		int rx   = getDisplayX() % a.getTdg().getDisplay().getBlockShowSize();
		int ry   = getDisplayY() % a.getTdg().getDisplay().getBlockShowSize();
		
		// x pos and y pos of map
		int xp = (int)(x * a.getTdg().getDisplay().getScale());
		int yp = (int)(y * a.getTdg().getDisplay().getScale());
		
		// actor bottom, actor top, actor left, actor right sides.
		int abs = (int)(a.getY() + dy) + a.getHeight() - getDisplayY();
		int ats = (int)(a.getY() + dy) - getDisplayY();
		int als = (int)(a.getX() + dx) - getDisplayX();
		int ars = (int)(a.getX() + dx) + a.getWidth() - getDisplayX();
		
		// block bottom, block top, block left, block right sides.
		int   bbs = 0;
		int   bts = 0;
		int   bls = 0;
		int   brs = 0;
		
		boolean onGround = false;
		
		int xo = 0;
		int yo = 0;
		
		for (int yy = height - 1; yy >= 0; yy --)
		{
			for (int xx = width - 1; xx >= 0; xx --)
			{
				xo = xx - (mapX / a.getTdg().getDisplay().getBlockSize());
				yo = yy - (mapY / a.getTdg().getDisplay().getBlockSize());
				
				if (xo >= width || xo < 0 || yo >= height || yo < 0 || tiles[xo + yo * width] == null) continue;
				
				bbs = yy * a.getTdg().getDisplay().getBlockShowSize() + a.getTdg().getDisplay().getBlockShowSize() - ry - getDisplayY();
				bts = yy * a.getTdg().getDisplay().getBlockShowSize() - ry - getDisplayY();
				bls = xx * a.getTdg().getDisplay().getBlockShowSize() + rx - getDisplayX();
				brs = xx * a.getTdg().getDisplay().getBlockShowSize() + a.getTdg().getDisplay().getBlockShowSize() + rx - getDisplayX();
				
				// ((abs > bts && abs <= bbs) || (ats < bbs && bts <= ats) || (bts > ats && bbs < abs)) && ((ars > bls && brs > ars) || (als < brs && bls < ars))
				
				if (((abs > bts && abs <= bbs) || (ats < bbs && bts <= ats) || (bts > ats && bbs < abs)) && ((ars > bls && brs > ars) || (als < brs && bls < ars)))
				{
					collided = true;
				}
				
				// hit bottom
				if ((abs > bts && abs <= bbs) && ((ars > bls && brs > ars) || (als < brs && bls < ars)))
				{
					onGround = true;
				}
				
				// hit bottom
				if ((ats < bbs && bts <= ats) && ((ars > bls && brs > ars) || (als < brs && bls < ars)))
				{
					a.setJumping(false);
				}
			}
		}
		
		a.setOnGround(onGround);
		
		if (!a.getJumpingUp() && onGround)
		{
			a.setJumping(false);
		}
		
//		if (!collided)
//		{
//			a.setLocation(a.getFx(), a.getFy());
//		}
//		else
//		{
//			a.setFx(a.getX());
//			a.setFy(a.getY());
//		}
		return collided;
	}
	
	public void setLoacation(float x, float y)
	{
		this.x = x;
		this.y = y;
		
		rendered = false;
	}
	
	public void move(float dx, float dy)
	{
		x += dx;
		y += dy;
		
		rendered = false;
	}
	
	public int getWidth()
	{
		return width;
	}
	
	public int getHeight()
	{
		return height;
	}

	public float getX()
	{
		return x;
	}
	
	public float getY()
	{
		return y;
	}
	
	public boolean getRendered()
	{
		return rendered;
	}
	
	public void setRendered(boolean r)
	{
		rendered = r;
	}
	
	/**
	* @return the tiles
	*/
	public Tile[] getTiles()
	{
		return tiles;
	}

	/**
	* @param t the tiles to set
	*/
	public void setTiles(Tile t[])
	{
		this.tiles = t;
	}
	
	public int getDisplayX()
	{
		return ((int)x / display.getScale()) * display.getScale();
	}
	
	public int getDisplayY()
	{
		return ((int)y / display.getScale()) * display.getScale();
	}
}