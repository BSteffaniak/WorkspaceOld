package net.foxycorndog.jdoutil;

import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_DYNAMIC_DRAW;
import static org.lwjgl.opengl.GL15.GL_WRITE_ONLY;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glBufferData;
import static org.lwjgl.opengl.GL15.glGenBuffers;
import static org.lwjgl.opengl.GL15.glMapBuffer;
import static org.lwjgl.opengl.GL15.glUnmapBuffer;

import java.nio.BufferOverflowException;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import net.foxycorndog.jdobase.Base;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL15;

public class LightBuffer extends Buffer
{
//	private boolean     wrapped;
	private boolean     editing;
	
	private int         id;
	private int         position;
	private int         size;
	
	private FloatBuffer buffer;
	private ByteBuffer  mapBuffer;
	
//	public LightBuffer(float array[])
//	{
//		setBuffer(FloatBuffer.wrap(array));
//	}
	
	public LightBuffer(int size)
	{
//		floats = new float[size];
		
		setBuffer(BufferUtils.createFloatBuffer(size));
		
		this.size = size;
		
		init();
		
		buffer.rewind();
	}
	
	private void init()
	{
		if (!Base.isUsingVBO())
		{
			return;
		}
		
		id = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, id);
		glBufferData(GL_ARRAY_BUFFER, buffer, GL_DYNAMIC_DRAW);
		//glBufferSubData(GL_ARRAY_BUFFER, 0, buffer);
		glBindBuffer(GL_ARRAY_BUFFER, 0);
	}
	
	public void addData(float elements[])
	{
//			glBindBuffer(GL_ARRAY_BUFFER, id);
//			
//			mapBuffer = glMapBuffer(GL_ARRAY_BUFFER, GL_WRITE_ONLY, null);
//			
//			buffer = mapBuffer.order(ByteOrder.nativeOrder()).asFloatBuffer();
//			
			setData(position, elements);
			
//			glUnmapBuffer(GL_ARRAY_BUFFER);
//			
//			glBindBuffer(GL_ARRAY_BUFFER, 0);
			
			this.position = position + elements.length;
	}
	
	public void setData(int position, float elements[])
	{
//		this.position = position;
		
		beginEditing();
		
		buffer.position(position);
		
		buffer.put(elements);
		
		buffer.rewind();
		
		endEditing();
	}
	
	public void setData(int position, float element)
	{
		setData(position, new float[] { element });
		
//		this.position = position;
		
//		beginEditing();
		
//		buffer.position(position);
//		
//		buffer.put(element);
//		
//		buffer.rewind();
		
//		endEditing();
	}
	
//	public void difSetData(int position, float elements[])
//	{
////		this.position = position;
//		
//		buffer.position(position);
//		
//		buffer.put(elements);
//		
//		buffer.rewind();
//	}
	
//	public void setData(int position, FloatBuffer elements)
//	{
////		glBindBuffer(GL_ARRAY_BUFFER, id);
////		
////		mapBuffer = glMapBuffer(GL_ARRAY_BUFFER, GL_WRITE_ONLY, null);
////		
////		buffer = mapBuffer.order(ByteOrder.nativeOrder()).asFloatBuffer();
//		
////		beginEditing();
//		buffer.position(position);
//		
//		buffer.put(elements);
//		
//		buffer.rewind();
//		
////		endEditing();
//		
////		glUnmapBuffer(GL_ARRAY_BUFFER);
////		
////		glBindBuffer(GL_ARRAY_BUFFER, 0);
//	}
	
//	public void refreshData()
//	{
//		setData(0, buffer);
//	}
	
	public float[] getData()
	{
		buffer.position(0);
		
		float dst[] = new float[size()];
		
		buffer.get(dst, 0, dst.length);
		
		return dst;
	}
	
	public void beginEditing()
	{
		if (!Base.isUsingVBO())
		{
			return;
		}
//		if (!editing)
//		{
			glBindBuffer(GL_ARRAY_BUFFER, id);
//			
			mapBuffer = glMapBuffer(GL_ARRAY_BUFFER, GL_WRITE_ONLY, null);
//			
			buffer    = mapBuffer.order(ByteOrder.nativeOrder()).asFloatBuffer();
			
//			GL15.glBufferSubData(GL_ARRAY_BUFFER, 0, buffer);
			
//			editing   = true;
//		}
	}
//	
	public void endEditing()
	{
		if (!Base.isUsingVBO())
		{
			return;
		}
//		if (editing)
//		{
			glUnmapBuffer(GL_ARRAY_BUFFER);
//			
			glBindBuffer(GL_ARRAY_BUFFER, 0);
//			
//			editing = false;
//		}
	}
	
	public float get(int index)
	{
		return buffer.get(index);
	}
	
	public FloatBuffer getBuffer()
	{
		buffer.position(0);
		
		return buffer;
	}
	
	public void setBuffer(FloatBuffer buffer)
	{
		this.buffer = buffer;
	}
	
	public int getId()
	{
		buffer.position(0);
		
		return id;
	}
	
	public int size()
	{
		return size;
	}
	
	public int capacity()
	{
		return size;
	}
}