package net.foxycorndog.thedigginggame.components;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.IOException;

import net.foxycorndog.jfoxylib.bundle.Bundle;
import net.foxycorndog.jfoxylib.graphics.Texture;
import net.foxycorndog.jfoxylib.graphics.opengl.GL;

/**
 * 
 * 
 * @author	Braden Steffaniak
 * @since	Feb 24, 2013 at 12:27:58 AM
 * @since	v
 * @version Feb 24, 2013 at 12:27:58 AM
 * @version	v
 */
public class Cursor
{
	private int		size;
	private int		x, y;
	
	private Texture	texture;
	
	private Bundle	bundle;
	
	public Cursor(int size)
	{
		this.size = size;
		
		BufferedImage img = new BufferedImage(size, size, BufferedImage.BITMASK);
		
		Graphics2D g = img.createGraphics();
		g.setColor(Color.BLACK);
		g.drawRect(0, 0, size - 1, size - 1);
		g.dispose();
		
		texture = new Texture(img, false);
		
		bundle = new Bundle(4, 2, true, false);
		
		bundle.beginEditingVertices();
		{
			bundle.addVertices(GL.genRectVerts(0, 0, size, size));
		}
		bundle.endEditingVertices();
		
		bundle.beginEditingTextures();
		{
			bundle.addTextures(GL.genRectTextures(texture.getImageOffsetsf()));
		}
		bundle.endEditingTextures();
	}
	
	public void render(float x, float y, float z)
	{
		GL.pushMatrix();
		{
			GL.translate(x, y, z);
			
			bundle.render(GL.QUADS, texture);
		}
		GL.popMatrix();
	}
	
	/**
	 * @return The absolute horizontal position of the Cursor relative to
	 * 		Tiles.
	 */
	public int getX()
	{
		return x;
	}
	
	/**
	 * @return The absolute vertical position of the Cursor relative to
	 * 		Tiles.
	 */
	public int getY()
	{
		return y;
	}
	
	/**
	 * Set the location of the Cursor relative to the blocks.
	 * 
	 * @param x The horizontal location of the Cursor.
	 * @param y The vertical location of the Cursor.
	 */
	public void setLocation(int x, int y)
	{
		this.x = x;
		this.y = y;
	}
}