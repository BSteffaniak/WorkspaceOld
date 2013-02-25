package net.foxycorndog.jfoxylib.bundle;

import static org.lwjgl.opengl.GL11.GL_VERTEX_ARRAY;
import static org.lwjgl.opengl.GL11.glDisableClientState;
import static org.lwjgl.opengl.GL11.glDrawArrays;
import static org.lwjgl.opengl.GL11.glEnableClientState;
import static org.lwjgl.opengl.GL11.glVertexPointer;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_DYNAMIC_DRAW;
import static org.lwjgl.opengl.GL15.GL_WRITE_ONLY;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glBufferData;
import static org.lwjgl.opengl.GL15.glGenBuffers;
import static org.lwjgl.opengl.GL15.glMapBuffer;
import static org.lwjgl.opengl.GL15.glUnmapBuffer;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;

/**
 * 
 * 
 * @author	Braden Steffaniak
 * @since	Feb 16, 2013 at 3:23:16 AM
 * @since	v0.1
 * @version	Feb 16, 2013 at 3:23:16 AM
 * @version	v0.1
 */
public class Buffer
{
	private int			id;
	private int			size;
	
	private FloatBuffer	buffer;
	private ByteBuffer	mapBuffer;
	
	/**
	 * 
	 * 
	 * @param size
	 */
	public Buffer(int size)
	{
		this.size = size;
		
		buffer = BufferUtils.createFloatBuffer(size);
		
		id = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, id);
		glBufferData(GL_ARRAY_BUFFER, buffer, GL_DYNAMIC_DRAW);
		glBindBuffer(GL_ARRAY_BUFFER, 0);
	}
	
	/**
	 * @return
	 */
	public int getId()
	{
		return id;
	}
	
	/**
	 * @return
	 */
	public int getSize()
	{
		return size;
	}
	
	/**
	 * @return
	 */
	public int getPosition()
	{
		return buffer.position();
	}
	
	/**
	 * 
	 * 
	 * @param position
	 */
	public void setPosition(int position)
	{
		buffer.position(position);
	}
	
	/**
	 * 
	 * 
	 * @param index
	 * @param data
	 */
	public void setData(int index, float data)
	{
		buffer.position(index);
	
		buffer.put(data);
		
		buffer.rewind();
	}
	
	/**
	 * 
	 * 
	 * @param index
	 * @param data
	 */
	public void setData(int index, float data[])
	{
		buffer.position(index);
	
		buffer.put(data);
		
		buffer.rewind();
	}
	
	/**
	 * 
	 * 
	 * @param offset
	 * @param size
	 * @param dx
	 * @param dy
	 * @param dz
	 */
	public void translate(int offset, int size, float dx, float dy, float dz)
	{
		buffer.position(offset);
		
		for (int i = 0; i < size; i += 3)
		{
			buffer.put(buffer.get(offset + i + 0) + dx);
			buffer.put(buffer.get(offset + i + 1) + dy);
			buffer.put(buffer.get(offset + i + 2) + dz);
		}
		
		buffer.rewind();
	}
	
	/**
	 * 
	 */
	public void beginEditing()
	{
		glBindBuffer(GL_ARRAY_BUFFER, id);
		
		mapBuffer = glMapBuffer(GL_ARRAY_BUFFER, GL_WRITE_ONLY, null);
		
		buffer    = mapBuffer.order(ByteOrder.nativeOrder()).asFloatBuffer();
	}
	
	/**
	 * 
	 */
	public void endEditing()
	{
		glUnmapBuffer(GL_ARRAY_BUFFER);
		
		glBindBuffer(GL_ARRAY_BUFFER, 0);
	}
	
	/**
	 * @return 
	 */
	public String toString()
	{
		StringBuilder builder = new StringBuilder();
		
		for (int i = 0; i < size; i++)
		{
			builder.append(buffer.get(i) + ", ");
		}
		
		return builder.toString();
	}
}