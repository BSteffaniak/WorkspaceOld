package net.foxycorndog.jdoogl.fonts;

import java.awt.Point;
import java.util.HashMap;

import org.lwjgl.opengl.GL11;

import net.foxycorndog.jdoogl.GL;
import net.foxycorndog.jdoogl.components.Frame;
import net.foxycorndog.jdoogl.image.imagemap.SpriteSheet;

public class Font
{
	private int cols, rows;
	private int yOff, xOff;
	private int width, height;
	private int glyphWidth, glyphHeight;
	
	private SpriteSheet characters;
	
	private HashMap<Character, Point> charSequence;
	private HashMap<String, int[]>  history;
	
	private int ids;
	
	public static final int LEFT = 0, CENTER = 1, RIGHT = 2, TOP = 2, BOTTOM = 0;
	
	public Font(String location, int cols, int rows, char charSequence[])
	{
		this.cols         = cols;
		this.rows         = rows;
		this.xOff         = xOff;
		this.yOff         = yOff;
		
		characters        = new SpriteSheet(location, cols, rows);
		
		this.width        = characters.getWidth();
		this.height       = characters.getHeight();
		
		this.glyphWidth   = width / cols;
		this.glyphHeight  = height / rows;
		
		this.charSequence = new HashMap<Character, Point>();
		this.history      = new HashMap<String, int[]>();
		
		for (int y = 0; y < rows; y ++)
		{
			for (int x = 0; x < cols; x ++)
			{
				if (x + y * cols >= charSequence.length)
				{
					break;
				}
				
				this.charSequence.put(charSequence[x + y * cols], new Point(x, y));
			}
		}
	}
	
	public void render(String text, float x, float y, float z)
	{
		render(text, x, y, z, 1);
	}
	
	public void render(String text, float x, float y, float z, float scale)
	{
		render(text, x, y, z, scale, LEFT, BOTTOM);
	}
	
	public void render(String text, float x, float y, float z, int horizontalAlignment, int verticalAlignment)
	{
		render(text, x, y, z, 1, horizontalAlignment, verticalAlignment);
	}
	
	public void render(String text, float x, float y, float z, float scale, int horizontalAlignment, int verticalAlignment)
	{
		characters.bind();
		
		int id = 0;
		
		if (history.containsKey(text))
		{
			if (horizontalAlignment == CENTER)
			{
				x += Frame.getCenterX();
				x -= text.length() * scale * glyphWidth / 2;
			}
			else if (horizontalAlignment == RIGHT)
			{
				x += Frame.getWidth();
				x -= text.length() * scale * glyphWidth;
			}
			if (verticalAlignment == CENTER)
			{
				y += Frame.getCenterY();
				y -= glyphHeight * scale / 2;
			}
			else if (verticalAlignment == TOP)
			{
				y += Frame.getHeight();
				y -= glyphHeight * scale;
			}
			
			id = history.get(text)[1];
		}
		else
		{
			System.out.println("created");
			id = GL11.glGenLists(1);
			
			GL11.glNewList(id, GL11.GL_COMPILE);
			{
				char chars[] = text.toCharArray();
				
				for (int i = 0; i < chars.length; i ++)
				{
					if (chars[i] == '\n')
					{
						render(text.substring(0, i), x, y /*+ ((glyphHeight + 1) / 2) * scale*/ , z, scale, horizontalAlignment, verticalAlignment);
						render(text.substring(i + 1), x, y - (glyphHeight + 1) * scale/*((glyphHeight + 1) / 2) * scale*/, z, scale, horizontalAlignment, verticalAlignment);
						
						GL11.glEndList();
						addToHistory(text, id);
	//					y += ((glyphHeight + 1) / 2) * scale;
						return;
					}
				}
				
				if (horizontalAlignment == CENTER)
				{
					x += Frame.getCenterX();
					x -= text.length() * scale * glyphWidth / 2;
				}
				else if (horizontalAlignment == RIGHT)
				{
					x += Frame.getWidth();
					x -= text.length() * scale * glyphWidth;
				}
				if (verticalAlignment == CENTER)
				{
					y += Frame.getCenterY();
					y -= glyphHeight * scale / 2;
				}
				else if (verticalAlignment == TOP)
				{
					y += Frame.getHeight();
					y -= glyphHeight * scale;
				}
				
				for (int i = 0; i < chars.length; i ++)
				{
					try
					{
						int charX       = charSequence.get(chars[i]).x;
						int charY       = charSequence.get(chars[i]).y;
						
						float offsets[] = characters.getImageOffsetsf(charX, charY, 1, 1);
					
						GL.beginManipulation();
						{
//							GL.translatef(x, y, z);
//							GL.scalef(scale, scale, 1);
							
							GL.glBegin(GL.QUADS);
							{
			//					Correct View
								
								GL.glTexCoord2f(offsets[2], offsets[1]);
								GL.glVertex2f(i * glyphWidth + glyphWidth, 0);
								
								GL.glTexCoord2f(offsets[2], offsets[3]);
								GL.glVertex2f(i * glyphWidth + glyphWidth, glyphHeight);
								
								GL.glTexCoord2f(offsets[0], offsets[3]);
								GL.glVertex2f(i * glyphWidth, glyphHeight);
								
								GL.glTexCoord2f(offsets[0], offsets[1]);
								GL.glVertex2f(i * glyphWidth, 0);
								
			//					Reversed View
			//					
			//					GL.glTexCoord2f(offsets[2], offsets[1]);
			//					GL.glVertex2f(- i * glyphWidth, 0);
			//					
			//					GL.glTexCoord2f(offsets[2], offsets[3]);
			//					GL.glVertex2f(- i * glyphWidth, glyphHeight);
			//					
			//					GL.glTexCoord2f(offsets[0], offsets[3]);
			//					GL.glVertex2f(- i * glyphWidth + glyphWidth, glyphHeight);
			//					
			//					GL.glTexCoord2f(offsets[0], offsets[1]);
			//					GL.glVertex2f(- i * glyphWidth + glyphWidth, 0);
							}
							GL.glEnd();
						}
						GL.endManipulation();
					}
					catch (NullPointerException e)
					{
						if (chars[i] == ' ')
						{
							
						}
						else
						{
							GL11.glEndList();
							
							addToHistory(text, id);
							
							return;
						}
					}
				}
			}
			GL11.glEndList();
			
			addToHistory(text, id);
		}
			
		GL.beginManipulation();
		{
			GL.translatef(x, y, z);
			GL.scalef(scale, scale, 1);
			
			GL11.glCallList(id);
		}
		GL.endManipulation();
	}
	
	private void addToHistory(String text, int id)
	{
		ids = ids + 1 <= 100 ? ids + 1 : 0;
		
		history.put(text, new int[] { ids, id });
	}
	
	public int getGlyphWidth()
	{
		return glyphWidth;
	}
	
	public int getGlyphHeight()
	{
		return glyphHeight;
	}
}