package net.foxycorndog.jdooglandroid.fonts;

import net.foxycorndog.jdooglandroid.GL;
import net.foxycorndog.jdooglandroid.image.imagemap.SpriteSheet;

public class CustomFont
{
	SpriteSheet image;
	
	public CustomFont(String location, int cols, int rows, String glyphs)
	{
		image = new SpriteSheet(null, 0, cols, rows);
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