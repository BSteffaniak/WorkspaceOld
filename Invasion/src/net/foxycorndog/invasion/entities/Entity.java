package net.foxycorndog.invasion.entities;

import net.foxycorndog.invasion.Invasion;
import net.foxycorndog.jdoogl.GL;
import net.foxycorndog.jdoogl.image.imagemap.Texture;
import net.foxycorndog.jdoutil.LightBuffer;
import net.foxycorndog.jdoutil.VerticesBuffer;

public abstract class Entity
{
	private boolean renderable; 
	
	private float   x, y;
	private float   renderX, renderY;
	
	private Texture texture;
	
	private LightBuffer  texturesBuffer;
	
	private VerticesBuffer verticesBuffer;
	
	public Entity(Texture texture)
	{
		this.texture = texture;
	}
	
	public void move(float dx, float dy)
	{
		x += dx;
		y += dy;
		
		renderX = ((int)x / (int)Invasion.getScale()) * (int)Invasion.getScale();
		renderY = ((int)y / (int)Invasion.getScale()) * (int)Invasion.getScale();
	}
	
	public void render()
	{
		if (!renderable)
		{
			return;
		}
		
		GL.beginManipulation();
		{
			GL.translatef(renderX, renderY, 0);
			GL.scalef(Invasion.getScale(), Invasion.getScale(), 1);
			
			GL.renderQuad(verticesBuffer, texturesBuffer, texture);
		}
		GL.endManipulation();
	}
	
	public abstract void onCollision(Entity entity);
	
	public float getScaledWidth()
	{
		return texture.getWidth() * Invasion.getScale();
	}
	
	public float getScaledHeight()
	{
		return texture.getHeight() * Invasion.getScale();
	}
	
	public float getWidth()
	{
		return texture.getWidth();
	}
	
	public float getHeight()
	{
		return texture.getHeight();
	}
	
	public float getX()
	{
		return x;
	}
	
	public float getY()
	{
		return y;
	}
	
	public void setLocation(float x, float y)
	{
		this.x = x;
		this.y = y;
		
		renderX = ((int)x / (int)Invasion.getScale()) * (int)Invasion.getScale();
		renderY = ((int)y / (int)Invasion.getScale()) * (int)Invasion.getScale();
	}
	
	public void setRenderable(boolean renderable)
	{
		this.renderable = renderable;
	}
	
	public Texture getTexture()
	{
		return texture;
	}
	
	public LightBuffer getVerticesBuffer()
	{
		return verticesBuffer;
	}
	
	public void setVerticesBuffer(VerticesBuffer buffer)
	{
		this.verticesBuffer = buffer;
	}
	
	public LightBuffer getTexturesBuffer()
	{
		return texturesBuffer;
	}
	
	public void setTexturesBuffer(LightBuffer buffer)
	{
		this.texturesBuffer = buffer;
	}
}