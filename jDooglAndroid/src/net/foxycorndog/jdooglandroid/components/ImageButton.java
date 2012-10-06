package net.foxycorndog.jdooglandroid.components;

import net.foxycorndog.jdooglandroid.Color;
import net.foxycorndog.jdooglandroid.GL;
import net.foxycorndog.jdooglandroid.image.imagemap.ImageMap;
import net.foxycorndog.jdooglandroid.image.imagemap.Texture;
import net.foxycorndog.jdoutilandroid.LightBuffer;
import net.foxycorndog.jdoutilandroid.VerticesBuffer;

public class ImageButton extends Component
{
	private int            textureWidth, textureHeight;
	
	private String         text;
	
	private Texture        texture, hoverTexture;
	
	private VerticesBuffer verticesBuffer;
	private LightBuffer    texturesBuffer, hoverTexturesBuffer;
	
	public ImageButton(Texture texture)
	{
		super();
		
		this.texture = texture;
		
		verticesBuffer = new VerticesBuffer(4 * 2, 2);
		texturesBuffer = new LightBuffer(4 * 2, 2);
		
		verticesBuffer.setData(0, GL.addRectVertexArrayf(0, 0, texture.getWidth(), texture.getHeight(), 0, null));
		
		texturesBuffer.setData(0, GL.addRectTextureArrayf(texture.getImageOffsetsf(), 0, null));
		
		setWidth(texture.getWidth());
		setHeight(texture.getHeight());
		
		textureWidth  = texture.getWidth();
		textureHeight = texture.getHeight();
	}
	
	@Override
	public void render()
	{
		render(1, 1, 1);
	}
	
	public void render(float x, float y, float z)
	{
		super.render();
		
		GL.beginManipulation();
		{
			float renderX = getX() + getOffsetX();
			float renderY = getY() + getOffsetY();
			
			if (text != null && !text.equals(""))
			{
//				Frame.renderText(renderX + getWidth() / 2 - Frame.getFont().getLegitWidth(text) / 2, Frame.getHeight() / getScaleHeight() - 1 - renderY - getHeight() / 2 - Frame.getFont().getHeight(text) / 2, text, Color.WHITE, 1);
			}
			
			GL.translatef(renderX, renderY, 0);
			
			GL.scalef(x, y, z);
			
			if (isHovering() && hoverTexture != null)
			{
				GL.renderQuad(verticesBuffer, hoverTexturesBuffer, hoverTexture);
			}
			else
			{
				GL.renderQuad(verticesBuffer, texturesBuffer, texture);
			}
		}
		GL.endManipulation();
	}
	
	public void setHoverTexture(Texture texture)
	{
		hoverTexturesBuffer = new LightBuffer(4 * 2, 2);
			
			if (texture.getWidth() != ((Texture)this.texture).getWidth() || texture.getHeight() != ((Texture)this.texture).getHeight())
			{
				
				throw new IllegalArgumentException("Texture width and height must be the same as the original ImageMap.");
			}
			
			hoverTexturesBuffer.setData(0, GL.addRectTextureArrayf(texture, 0, null));
			
		this.hoverTexture = texture;
	}
	
	public String getText()
	{
		return text;
	}
	
	public void setText(String text)
	{
		this.text = text;
	}
	
	public int getTextureWidth()
	{
		return textureWidth;
	}
	
	public int getTextureHeight()
	{
		return textureHeight;
	}
}
