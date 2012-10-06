package net.foxycorndog.presto2d.graphics;

import static net.foxycorndog.presto2d.PrestoGL2D.beginTextureDraw;
import static net.foxycorndog.presto2d.PrestoGL2D.beginVertexDraw;
import static net.foxycorndog.presto2d.PrestoGL2D.endTextureDraw;
import static net.foxycorndog.presto2d.PrestoGL2D.endVertexDraw;
import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glDrawArrays;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glTranslatef;

import java.awt.Point;

import net.foxycorndog.presto2d.PrestoGL2D;
import net.foxycorndog.presto2d.util.Buffer;
import net.foxycorndog.presto2d.util.SpriteSheet;

import org.lwjgl.BufferUtils;

public class Font
{
	private byte        cols;//, rows;
	private byte        letterWidth, letterHeight;
	
	private float       x, y, z;
	
	private String      prototype;
	private String      text;
	
	private char        chars[];
	
	private SpriteSheet font;
	
	private Buffer      verticesBuffer, texturesBuffer;
	
	public Font(String location, byte cols, byte rows, String prototype, boolean flipped)
	{
		this.cols  = cols;
//		this.rows  = rows;
		
		this.prototype = prototype;
		
		chars = new char[prototype.length()];
		
		for (int i = 0; i < prototype.length(); i ++)
		{
			chars[i] = prototype.charAt(i);
		}
		
		font = new SpriteSheet(location, "PNG", cols, rows, flipped);
		
		letterWidth  = (byte)(font.getImageWidth()  / cols);
		letterHeight = (byte)(font.getImageHeight() / rows);
		
		init();
	}
	
	private void init()
	{
		int OBJECT_SIZE = 4 * 3 * 100;
		
		verticesBuffer = new Buffer(OBJECT_SIZE);
		verticesBuffer.setBuffer(BufferUtils.createFloatBuffer(OBJECT_SIZE));
		verticesBuffer.init();
		
		texturesBuffer = new Buffer(OBJECT_SIZE);
		texturesBuffer.setBuffer(BufferUtils.createFloatBuffer(OBJECT_SIZE));
		texturesBuffer.init();
	}
	
	public void render()
	{
		glPushMatrix();
		{
			glTranslatef(x, y, z);
			
			beginTextureDraw(texturesBuffer.getId(), 2);
			beginVertexDraw (verticesBuffer.getId(), 3);
			
			font.bind();
			
			glDrawArrays(GL_QUADS, 0, text.length() * 4);
			
			endVertexDraw();
			endTextureDraw();
		}
		glPopMatrix();
	}
	
	public void setText(String text, float scale)
	{
		if (this.text != null && this.text.equals(text))
		{
			return;
		}
		
		this.text = text;
			
		float vertices[] = verticesBuffer.getElements();
		float textures[] = texturesBuffer.getElements();
		
		for (int i = 0; i < text.length(); i ++)
		{
			Point loc = getLetterLocation(text.charAt(i));
			
			vertices = PrestoGL2D.addRectVertexArray(i * letterWidth * scale, 0, z, letterWidth * scale, letterHeight * scale, i * 3 * 4, vertices);
			textures = PrestoGL2D.addRectTextureArray(font, loc.x, loc.y, 1, 1, i * 2 * 4, textures);
		}
		
		verticesBuffer.refreshData();
		texturesBuffer.refreshData();
	}
	
	private Point getLetterLocation(char letter)
	{
		byte x = (byte)(prototype.indexOf(letter) % cols);
		byte y = (byte)(prototype.indexOf(letter) / cols);
		
		return new Point(x, y);
	}
	
	public String getText()
	{
		return text;
	}
	
	public void setLocation(float x, float y, float z)
	{
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public float getX()
	{
		return x;
	}
	
	public float getY()
	{
		return y;
	}
	
	public float getZ()
	{
		return z;
	}
	
	public int getWidth()
	{
		return text.length() * letterWidth + text.length() * 1;
	}
	
	public byte getLetterWidth()
	{
		return letterWidth;
	}
}