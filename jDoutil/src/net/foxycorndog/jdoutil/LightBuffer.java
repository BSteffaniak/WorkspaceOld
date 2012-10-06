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
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;

public class LightBuffer extends Buffer
{
//	private boolean     wrapped;
	private boolean     editing;
	
	private int         id;
	private int         position;
	
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
		
		init();
	}
	
	private void init()
	{
//		id = glGenBuffers();
//		glBindBuffer(GL_ARRAY_BUFFER, id);
//		glBufferData(GL_ARRAY_BUFFER, buffer, GL_DYNAMIC_DRAW);
		//glBufferSubData(GL_ARRAY_BUFFER, 0, buffer);
		//glBindBuffer(GL_ARRAY_BUFFER, 0);
	}
	
	public void addData(float elements[])
	{
		try
		{
//			glBindBuffer(GL_ARRAY_BUFFER, id);
//			
//			mapBuffer = glMapBuffer(GL_ARRAY_BUFFER, GL_WRITE_ONLY, null);
//			
//			buffer = mapBuffer.order(ByteOrder.nativeOrder()).asFloatBuffer();
//			
//			beginEditing();
			
			buffer.position(position);
			
			
			
			buffer.put(elements);
			
			buffer.rewind();
			
//			endEditing();
			
//			glUnmapBuffer(GL_ARRAY_BUFFER);
//			
//			glBindBuffer(GL_ARRAY_BUFFER, 0);
			
			this.position = position + elements.length;
		}
		catch(BufferOverflowException ex)
		{
			ex.printStackTrace();
		}
	}
	
	public void setData(int position, float elements[])
	{
//		this.position = position;
		
//		beginEditing();
		
		buffer.position(position);
		
		buffer.put(elements);
		
		buffer.rewind();
		
//		endEditing();
	}
	
	public void setData(int position, float element)
	{
//		this.position = position;
		
//		beginEditing();
		
		buffer.position(position);
		
		buffer.put(element);
		
		buffer.rewind();
		
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
		float dst[] = new float[this.capacity()];
		
		buffer.get(dst);
		
		return dst;
	}
	
//	public void beginEditing()
//	{
////		if (!editing)
////		{
////			glBindBuffer(GL_ARRAY_BUFFER, id);
////			
////			mapBuffer = glMapBuffer(GL_ARRAY_BUFFER, GL_WRITE_ONLY, null);
////			
////			buffer    = mapBuffer.order(ByteOrder.nativeOrder()).asFloatBuffer();
//			
////			editing   = true;
////		}
//	}
//	
//	public void endEditing()
//	{
////		if (editing)
////		{
////			glUnmapBuffer(GL_ARRAY_BUFFER);
////			
////			glBindBuffer(GL_ARRAY_BUFFER, 0);
////			
////			editing = false;
////		}
//	}
	
	public float get(int index)
	{
		return buffer.get(index);
	}
	
	public FloatBuffer getBuffer()
	{
		return buffer;
	}
	
	public void setBuffer(FloatBuffer buffer)
	{
		this.buffer = buffer;
	}
	
//	public int getId()
//	{
//		return id;
//	}
	
	public int capacity()
	{
		return buffer.capacity();
	}
}