package net.foxycorndog.jdoogl.fonts;

import net.foxycorndog.jdoogl.GL;
import net.foxycorndog.jdoogl.image.imagemap.SpriteSheet;

public class CustomFont
{
	SpriteSheet image;
	
	public CustomFont(String location, int cols, int rows, String glyphs)
	{
		image = new SpriteSheet(location, "PNG", cols, rows, true, true);
	}
	
	public void drawString(float x, float y, String string)
	{
		GL.beginManipulation();
		{
			GL.translatef(x, y, 0);
			
			
		}
		GL.endManipulation();
	}
}