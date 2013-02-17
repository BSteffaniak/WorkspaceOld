package net.foxycorndog.jfoxylib.bundle;

import static org.lwjgl.opengl.GL11.GL_NEAREST;
import static org.lwjgl.opengl.GL11.GL_REPEAT;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_COORD_ARRAY;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MAG_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MIN_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_WRAP_S;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_WRAP_T;
import static org.lwjgl.opengl.GL11.GL_VERTEX_ARRAY;
import static org.lwjgl.opengl.GL11.glDisableClientState;
import static org.lwjgl.opengl.GL11.glDrawArrays;
import static org.lwjgl.opengl.GL11.glEnableClientState;
import static org.lwjgl.opengl.GL11.glTexCoordPointer;
import static org.lwjgl.opengl.GL11.glTexParameteri;
import static org.lwjgl.opengl.GL11.glVertexPointer;

import net.foxycorndog.jfoxylib.GL;
import net.foxycorndog.jfoxylib.graphics.Texture;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;

/**
 * 
 * 
 * @author	Braden Steffaniak
 * @since	Feb 16, 2013 at 3:23:29 AM
 * @since	v0.1
 * @version	v0.1
 */
public class Bundle
{
	private int				size;
	private int				vertexSize, textureSize;
	
	private Buffer			verticesBuffer, texturesBuffer;
	private IndicesBuffer	vertexIndices, normalIndices;
	
	public Bundle(int numVertices, int vertexSize)
	{
		size = numVertices * vertexSize;
		
		this.vertexSize  = vertexSize;
		this.textureSize = 2;
		
		verticesBuffer = new Buffer(size);
		texturesBuffer = new Buffer(size);
	}
	
	public Buffer getVerticesBuffer()
	{
		return verticesBuffer;
	}
	
	public void setVerticesBuffer(Buffer buffer)
	{
		this.verticesBuffer = buffer;
	}
	
	public void setVertices(int index, float verts[])
	{
		verticesBuffer.setData(index, verts);
	}
	
	public Buffer getTexturesBuffer()
	{
		return texturesBuffer;
	}
	
	public void setTexturesBuffer(Buffer buffer)
	{
		this.texturesBuffer = buffer;
	}
	
	public void render(int shape, Texture texture)
	{
		render(shape, 0, size, texture);
	}
	
	public void render(int shape, int start, int amount, Texture texture)
	{
		GL.pushAttrib(GL.ALL_ATTRIB_BITS);
		
		beginVertexDraw();
		beginTexturesDraw();
		
		texture.bind();
		
		glDrawArrays(shape, start, amount);
		
		endTexturesDraw();
		endVertexDraw();
		
		GL.popAttrib();
	}
	
	private void beginVertexDraw()
	{
		glEnableClientState(GL_VERTEX_ARRAY);
		
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, verticesBuffer.getId());

		glVertexPointer(vertexSize, GL11.GL_FLOAT, 0, 0);
		
//		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, vertexIndicesId);
	}
	
	private void endVertexDraw()
	{
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
		
		glDisableClientState(GL_VERTEX_ARRAY);
	}
	
	private void beginTexturesDraw()
	{
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
		
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
		
		glEnableClientState(GL_TEXTURE_COORD_ARRAY);
		
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, texturesBuffer.getId());

		glTexCoordPointer(textureSize, GL11.GL_FLOAT, 0, 0);
	}
	
	private void endTexturesDraw()
	{
		glDisableClientState(GL_TEXTURE_COORD_ARRAY);
		
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
	}
	
	public String toString()
	{
		return verticesBuffer.toString();
	}
}