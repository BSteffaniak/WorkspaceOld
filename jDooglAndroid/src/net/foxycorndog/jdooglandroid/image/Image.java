package net.foxycorndog.jdooglandroid.image;

import android.util.Log;
import net.foxycorndog.jdooglandroid.Color;
import net.foxycorndog.jdooglandroid.GL;
import net.foxycorndog.jdooglandroid.components.Component;
import net.foxycorndog.jdooglandroid.components.Frame;
import net.foxycorndog.jdooglandroid.image.imagemap.ImageMap;
import net.foxycorndog.jdooglandroid.image.imagemap.SpriteSheet;
import net.foxycorndog.jdooglandroid.image.imagemap.Texture;
import net.foxycorndog.jdoutilandroid.LightBuffer;
import net.foxycorndog.jdoutilandroid.Task;
import net.foxycorndog.jdoutilandroid.VerticesBuffer;

public class Image extends Component
{
	private int         col, row;
	
	private ImageMap    imageMap;
	
	private VerticesBuffer verticesBuffer;
	private LightBuffer    texturesBuffer;
	
	public Image(Texture texture)
	{
		this.imageMap  = texture;
		
		verticesBuffer = new VerticesBuffer(4 * 2, 2);
		texturesBuffer = new LightBuffer(4 * 2, 2);
		
		verticesBuffer.setData(0, GL.addRectVertexArrayf(0, 0, texture.getWidth(), texture.getHeight(), 0, null));
		texturesBuffer.setData(0, GL.addRectTextureArrayf(texture, 0, null));
		
		setWidth (texture.getWidth());
		setHeight(texture.getHeight());
	}
	
	public Image(Texture texture, int rx, int ry)
	{
		this.imageMap  = texture;
		
		verticesBuffer = new VerticesBuffer(4 * 2, 2);
		texturesBuffer = new LightBuffer(4 * 2, 2);
		
		verticesBuffer.setData(0, GL.addRectVertexArrayf(0, 0, texture.getWidth() * rx, texture.getHeight() * ry, 0, null));
		texturesBuffer.setData(0, GL.addRectTextureArrayf(texture, rx, ry, 0, null));
		
		setWidth (texture.getWidth() * rx);
		setHeight(texture.getHeight() * ry);
	}
	
	public Image(SpriteSheet spriteSheet, int col, int row, int width, int height)
	{
		this.imageMap  = spriteSheet;
		
		this.col       = col;
		this.row       = row;
		
		verticesBuffer = new VerticesBuffer(4 * 2, 2);
		texturesBuffer = new LightBuffer(4 * 2, 2);
		
		int wid = spriteSheet.getWidth()  / spriteSheet.getCols();
		int hei = spriteSheet.getHeight() / spriteSheet.getRows();
		
		verticesBuffer.setData(0, GL.addRectVertexArrayf(0, 0, wid, hei, 0, null));
		texturesBuffer.setData(0, GL.addRectTextureArrayf(spriteSheet.getImageOffsetsf(col, row, width, height), 0, null));
		
		setWidth (wid);
		setHeight(hei);
	}
	
	public Image(SpriteSheet spriteSheet, int col, int row, int width, int height, int rx, int ry)
	{
		this.imageMap  = spriteSheet;
		
		this.col       = col;
		this.row       = row;
		
		verticesBuffer = new VerticesBuffer(4 * 2, 2);
		texturesBuffer = new LightBuffer(4 * 2, 2);
		
		int wid = spriteSheet.getWidth()  / spriteSheet.getCols();
		int hei = spriteSheet.getHeight() / spriteSheet.getRows();
		
		verticesBuffer.setData(0, GL.addRectVertexArrayf(0, 0, wid * rx, hei * ry, 0, null));
		texturesBuffer.setData(0, GL.addRectTextureArrayf(spriteSheet.getImageOffsetsf(col, row, width, height), rx, ry, 0, null));
		
		setWidth (wid);
		setHeight(hei);
	}
	
	public void render()
	{
		super.render();
		
		GL.beginManipulation();
		{
			float renderX = getX() + getOffsetX();
			float renderY = getY() + getOffsetY();
			
			GL.translatef(renderX, renderY, 0);
			
//			GL.renderQuad(verticesBuffer, texturesBuffer, imageMap);
			GL.renderQuads(verticesBuffer, texturesBuffer, imageMap, 0, 1);
		}
		GL.endManipulation();
	}
}
