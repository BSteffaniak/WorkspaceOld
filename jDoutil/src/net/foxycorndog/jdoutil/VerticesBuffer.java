package net.foxycorndog.jdoutil;

import java.nio.IntBuffer;
import java.nio.ShortBuffer;

import net.foxycorndog.jdobase.Base;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;

public class VerticesBuffer extends LightBuffer
{
	private boolean     useIndices;
	
	private int         vertexSize;
	private int         indicesId;
	
	private ShortBuffer indices;
	
	public VerticesBuffer(int size, int vertexSize)
	{
		super(size);
		
		this.vertexSize = vertexSize;
		
		useIndices = Base.getDrawMode() == Base.ELEMENTS;
		
		init();
	}
	
	public int getVertexSize()
	{
		return vertexSize;
	}
	
	private void init()
	{
		if (useIndices)
		{
			genIndices(GL11.GL_QUADS);
		}
		
		if (Base.isUsingVBO())
		{
			indicesId = GL15.glGenBuffers();
		}
	}
	
	public void setData(int position, float elements[])
	{
//		if (Base.getDrawMode() == Base.ARRAYS)
//		{
//			position = (position / 4) * 3 * 2;
//		}
		
		super.setData(position, elements);
		
		if (useIndices && position % (4 * vertexSize) == 0)
		{
			setIndices(position / vertexSize, position / vertexSize, (elements.length / vertexSize) / 4);
		}
	}
	
	private void setIndices(int position, int offset, int amount)
	{
		if (useIndices && indices == null)
		{
			genIndices(GL11.GL_QUADS);
			
			//return;
		}
		
		indices.position(position);
		
		short ind[] = new short[4 * amount];
		
		for (int i = 0; i < amount; i += 4)
		{
			ind[0 + i] = (short)(0 + offset + i);
			ind[1 + i] = (short)(1 + offset + i);
			ind[2 + i] = (short)(2 + offset + i);
			ind[3 + i] = (short)(3 + offset + i);
		}
		
		indices.put(ind);
		
		indices.rewind();
	}
	
//	public void beginEditing()
//	{
//		if (!Base.isUsingVBO())
//		{
//			return;
//		}
//		
//		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, indicesId);
//		
////		mapBuffer = GLES11.glMapBufferOES(GLES11.GL_ARRAY_BUFFER, GLES11.GL_WRITE_ONLY, null);
////		
////		buffer    = mapBuffer.order(ByteOrder.nativeOrder()).asFloatBuffer();
//	}
//	
//	public void endEditing()
//	{
//		if (!Base.isUsingVBO())
//		{
//			return;
//		}
////		glUnmapBuffer(GL_ARRAY_BUFFER);
//				
//		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
//	}
	
	public int getIndicesId()
	{
		return indicesId;
	}
	
	public void setIndices(short indicesArray[])
	{
		indices = BufferUtils.createShortBuffer(indicesArray.length);
		
		indices.position(0);
		
		indices.put(indicesArray);
		
		indices.rewind();
		
		if (Base.isUsingVBO())
		{
//			GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, indicesId);
			
//			GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, indices, GL15.GL_DYNAMIC_DRAW);
		}
	}
	
	public void genIndices(int shape)
	{
		short ind[] = null;
		
		if (shape == GL11.GL_QUADS)
		{
			indices = BufferUtils.createShortBuffer(size() / vertexSize);
			
			ind = new short[indices.capacity()];
			
			for (int i = 0; i < ind.length; i += 4)
			{
		//			0 1 2
		//			0 2 3
				ind[i + 0] = (short)(0 + i);
				ind[i + 1] = (short)(1 + i);
				ind[i + 2] = (short)(2 + i);
				ind[i + 3] = (short)(3 + i);
			}
		}
		else if (shape == GL11.GL_TRIANGLES)
		{
			indices = BufferUtils.createShortBuffer(size() / vertexSize);
			
			ind = new short[indices.capacity()];
			
			for (int i = 0; i < ind.length; i += 3)
			{
		//			0 1 2
		//			0 2 3
				ind[i + 0] = (short)(0 + i);
				ind[i + 1] = (short)(1 + i);
				ind[i + 2] = (short)(2 + i);
			}
		}
		else
		{
			return;
		}
		
		indices.put(ind);
		
		indices.rewind();
		
		if (Base.isUsingVBO())
		{
			GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, indicesId);
			
			GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, indices, GL15.GL_DYNAMIC_DRAW);
			
			GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
		}
	}
	
	public void destroyIndices()
	{
		indices = null;
	}
	
	public ShortBuffer getIndices()
	{
		indices.position(0);
		
		return indices;
	}
	
	public ShortBuffer getIndices(int position)
	{
		indices.position(position);
		
		return indices;
	}
	
	public String toString()
	{
		String str = "";
		
		float data[] = super.getData();
		
		for (float f : data)
		{
			str += f + ", ";
		}
		
		return str;
	}
}