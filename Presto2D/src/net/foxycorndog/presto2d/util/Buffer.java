package net.foxycorndog.presto2d.util;

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

public class Buffer
{
	private int         id;
	
	private float       floats[];
	
	private FloatBuffer buffer;
	private ByteBuffer  mapBuffer;
	
	public Buffer()
	{
		
	}
	
	public Buffer(int size)
	{
		floats = new float[size];
	}
	
	public void init()
	{
		id = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, id);
		glBufferData(GL_ARRAY_BUFFER, buffer, GL_DYNAMIC_DRAW);
		//glBufferSubData(GL_ARRAY_BUFFER, 0, buffer);
		//glBindBuffer(GL_ARRAY_BUFFER, 0);
	}
	
	public void setData(int position, float elements[])
	{
		glBindBuffer(GL_ARRAY_BUFFER, id);
		
		mapBuffer = glMapBuffer(GL_ARRAY_BUFFER, GL_WRITE_ONLY, null);
		
		buffer = mapBuffer.order(ByteOrder.nativeOrder()).asFloatBuffer();
		
		buffer.position(position);
		
		buffer.put(elements);
		
		buffer.rewind();
		
		glUnmapBuffer(GL_ARRAY_BUFFER);
		
		glBindBuffer(GL_ARRAY_BUFFER, 0);
		
		ArrayUtil.setData(position, elements, floats);
	}
	
	public void refreshData()
	{
		setData(0, floats);
	}
	
	public FloatBuffer getBuffer()
	{
		return buffer;
	}
	
	public void setBuffer(FloatBuffer buffer)
	{
		this.buffer = buffer;
	}
	
	public int getId()
	{
		return id;
	}
	
	public float[] getElements()
	{
		return floats;
	}
	
	public int getSize()
	{
		return floats.length;
	}
}