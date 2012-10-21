package net.foxycorndog.jdoogl.components;

import net.foxycorndog.jdoogl.Color;
import net.foxycorndog.jdoogl.GL;
import net.foxycorndog.jdoogl.image.imagemap.ImageMap;
import net.foxycorndog.jdoogl.image.imagemap.Texture;
import net.foxycorndog.jdoutil.LightBuffer;

public class ImageButton extends Component
{
	private int         imageMapWidth, imageMapHeight;
	
	private String      text;
	
	private ImageMap    imageMap, hoverImageMap;
	
	private LightBuffer verticesBuffer, texturesBuffer;
	private LightBuffer hoverTexturesBuffer;
	
	public ImageButton(ImageMap imageMap)
	{
		super();
		
		this.imageMap = imageMap;
		
		verticesBuffer = new LightBuffer(4 * 2);
		texturesBuffer = new LightBuffer(4 * 2);
		
		if (imageMap instanceof Texture)
		{
			Texture texture = (Texture)imageMap;
			
			verticesBuffer.setData(0, GL.addRectVertexArrayf(0, 0, texture.getWidth(), texture.getHeight(), 0, null));
			
			texturesBuffer.setData(0, GL.addRectTextureArrayf(texture.getImageOffsetsf(), 0, null));
			
			setWidth(texture.getWidth());
			setHeight(texture.getHeight());
			
			imageMapWidth  = texture.getWidth();
			imageMapHeight = texture.getHeight();
		}
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
				Frame.renderText(renderX + getWidth() / 2 - Frame.getFont().getLegitWidth(text) / 2, Frame.getHeight() / getScaleHeight() - 1 - renderY - getHeight() / 2 - Frame.getFont().getHeight(text) / 2, text, Color.WHITE, 1);
			}
			
			GL.translatef(renderX, renderY, 0);
			
			GL.scalef(x, y, z);
			if (isHovering() && hoverImageMap != null)
			{
				GL.renderQuad(verticesBuffer, hoverTexturesBuffer, hoverImageMap);
			}
			else
			{
				GL.renderQuad(verticesBuffer, texturesBuffer, imageMap);
			}
		}
		GL.endManipulation();
	}
	
	public void setHoverImageMap(ImageMap imageMap)
	{
		hoverTexturesBuffer = new LightBuffer(4 * 2);
		
		if (imageMap instanceof Texture)
		{
			Texture texture = (Texture)imageMap;
			
			if (texture.getWidth() != ((Texture)this.imageMap).getWidth() || texture.getHeight() != ((Texture)this.imageMap).getHeight())
			{
				
				throw new IllegalArgumentException("ImageMap width and height must be the same as the original ImageMap.");
			}
			
			hoverTexturesBuffer.setData(0, GL.addRectTextureArrayf(texture, 0, null));
		}
		
		this.hoverImageMap = imageMap;
	}
	
	public String getText()
	{
		return text;
	}
	
	public void setText(String text)
	{
		this.text = text;
	}
	
	public int getImageMapWidth()
	{
		return imageMapWidth;
	}
	
	public int getImageMapHeight()
	{
		return imageMapHeight;
	}
}