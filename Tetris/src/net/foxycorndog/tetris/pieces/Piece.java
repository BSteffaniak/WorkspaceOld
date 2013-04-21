package net.foxycorndog.tetris.pieces;

import java.io.IOException;

import net.foxycorndog.jfoxylib.bundle.Bundle;
import net.foxycorndog.jfoxylib.graphics.Texture;
import net.foxycorndog.jfoxylib.graphics.opengl.GL;

/**
 * 
 * 
 * @author	Braden Steffaniak
 * @since	Apr 20, 2013 at 11:17:15 PM
 * @since	v0.1
 * @version	Apr 20, 2013 at 11:17:15 PM
 * @version	v0.1
 */
public class Piece
{
	private int				rotation;
	
	private Bundle			bundle;
	
	private	static	Texture	square;
	
	static
	{
		try
		{
			square = new Texture("res/images/square.png");
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * 
	 * @param matrix
	 * @param width
	 */
	public Piece(float matrix[], int width)
	{
		bundle = new Bundle(1, 2, true, false);
		
		int height = matrix.length / width;
		
		int num = 0;
		
		for (int i = 0; i < matrix.length; i++)
		{
			if (matrix[i] >= 0)
			{
				num++;
			}
		}
		
		bundle = new Bundle(num * 4, 2, true, false);
		
		int wid = square.getWidth();
		int hei = square.getHeight();
		
		bundle.beginEditingVertices();
		{
			for (int x = 0; x < width; x++)
			{
				for (int y = 0; y < height; y++)
				{
					if (matrix[x + y * width] >= 0)
					{
						bundle.addVertices(GL.genRectVerts(x * wid, y * hei, wid, hei));
					}
				}
			}
		}
		bundle.endEditingVertices();
		
		bundle.beginEditingTextures();
		{
			for (int x = 0; x < width; x++)
			{
				for (int y = 0; y < height; y++)
				{
					if (matrix[x + y * width] >= 0)
					{
						bundle.addTextures(GL.genRectTextures(square.getImageOffsets()));
					}
				}
			}
		}
		bundle.endEditingTextures();
	}
	
	/**
	 * 
	 */
	public void render()
	{
		GL.setTextureScaleMinMethod(GL.NEAREST);
		GL.setTextureScaleMagMethod(GL.NEAREST);
		
		bundle.render(GL.QUADS, square);
	}
	
	/**
	 * 
	 */
	public void rotateClockwise()
	{
		rotation += 45;
		
		rotation %= 360;
	}
	
	/**
	 * 
	 */
	public void rotateCounterClockwise()
	{
		rotateClockwise();
		rotateClockwise();
		rotateClockwise();
	}
}