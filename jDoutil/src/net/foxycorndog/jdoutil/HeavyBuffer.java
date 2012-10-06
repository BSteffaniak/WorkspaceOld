package net.foxycorndog.jdoutil;

import java.nio.DoubleBuffer;

import org.lwjgl.BufferUtils;

public class HeavyBuffer extends Buffer
{
	private int          id;
	
//	private double       doubles[];
	
	private DoubleBuffer buffer;
//	private ByteBuffer   mapBuffer;
	
	public HeavyBuffer(int size)
	{
//		doubles = new double[size];
		
		setBuffer(BufferUtils.createDoubleBuffer(size));
		
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
	
	public void setData(int position, double elements[])
	{
//		glBindBuffer(GL_ARRAY_BUFFER, id);
//		
//		mapBuffer = glMapBuffer(GL_ARRAY_BUFFER, GL_WRITE_ONLY, null);
//		
//		buffer = mapBuffer.order(ByteOrder.nativeOrder()).asDoubleBuffer();
		
		buffer.position(position);
		
		buffer.put(elements);
		
		buffer.rewind();
		
//		glUnmapBuffer(GL_ARRAY_BUFFER);
//		
//		glBindBuffer(GL_ARRAY_BUFFER, 0);
//		
//		ArrayUtil.setData(position, elements, doubles);
	}
	
//	public void refreshData()
//	{
//		setData(0, doubles);
//	}
	
	public DoubleBuffer getBuffer()
	{
		return buffer;
	}
	
	public void setBuffer(DoubleBuffer buffer)
	{
		this.buffer = buffer;
	}
	
//	public int getId()
//	{
//		return id;
//	}
	
//	public double[] getElements()
//	{
//		return doubles;
//	}
	
	public int getSize()
	{
		return buffer.capacity();
	}
}