package net.foxycorndog.jfoxylib.bundle;

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
import java.nio.ShortBuffer;

import org.lwjgl.BufferUtils;

public class IndicesBuffer
{
	private int			id;
	private int			size;
	
	private ShortBuffer	buffer;
	private ByteBuffer	mapBuffer;
	
	public IndicesBuffer(int size)
	{
		this.size = size;
		
		buffer = BufferUtils.createShortBuffer(size);
		
		id = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, id);
		glBufferData(GL_ARRAY_BUFFER, buffer, GL_DYNAMIC_DRAW);
		glBindBuffer(GL_ARRAY_BUFFER, 0);
	}
	
	public int getId()
	{
		return id;
	}
	
	public void setData(int index, short data)
	{
		beginEditing();
		
		buffer.position(index);
	
		buffer.put(data);
		
		buffer.rewind();
		
		endEditing();
	}
	
	public void setData(int index, short data[])
	{
		beginEditing();
		
		buffer.position(index);
	
		buffer.put(data);
		
		buffer.rewind();
		
		endEditing();
	}
	
	private void beginEditing()
	{
		glBindBuffer(GL_ARRAY_BUFFER, id);
		
		mapBuffer = glMapBuffer(GL_ARRAY_BUFFER, GL_WRITE_ONLY, null);
		
		buffer    = mapBuffer.order(ByteOrder.nativeOrder()).asShortBuffer();
	}
	
	private void endEditing()
	{
		glUnmapBuffer(GL_ARRAY_BUFFER);
		
		glBindBuffer(GL_ARRAY_BUFFER, 0);
	}
	
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