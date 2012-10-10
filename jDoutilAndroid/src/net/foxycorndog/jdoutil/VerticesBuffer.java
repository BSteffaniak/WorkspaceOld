package net.foxycorndog.jdoutil;

import java.nio.ShortBuffer;

import net.foxycorndog.jdobase.Base;

import android.opengl.GLES10;
import android.opengl.GLES11;

public class VerticesBuffer extends LightBuffer
{
	private boolean     useIndices;
	
	private int         indicesId;
	
	private ShortBuffer indices;
	
	public VerticesBuffer(int size, int vertexSize)
	{
		super(Base.getDrawMode() == Base.ELEMENTS ? size : (Base.getDrawMode() == Base.ARRAYS ? (size / 4) * 3 * 2 : 0), vertexSize);
		
		useIndices = Base.getDrawMode() == Base.ELEMENTS;
		
		init();
	}
	
	private void init()
	{
		if (useIndices)
		{
			genIndices(GLES10.GL_TRIANGLES);
		}
		
		if (Base.usingVBO())
		{
			int ids[] = new int[1];
			
			GLES11.glGenBuffers(1, ids, 0);
			indicesId = ids[0];
			
			GLES11.glBindBuffer(GLES11.GL_ELEMENT_ARRAY_BUFFER, indicesId);
			GLES11.glBufferData(GLES11.GL_ELEMENT_ARRAY_BUFFER, indices.capacity(), indices, GLES11.GL_DYNAMIC_DRAW);
		}
	}
	
	public void setData(int position, float elements[])
	{
//		if (Base.getDrawMode() == Base.ARRAYS)
//		{
//			position = (position / 4) * 3 * 2;
//		}
		
		super.setData(position, elements);
		
		if (useIndices && position % (4 * getStride()) == 0)
		{
			setIndices(((position / getStride()) / 4) * 3 * 2, position / getStride(), (elements.length / getStride()) / 4);
		}
	}
	
	private void setIndices(int position, int offset, int amount)
	{
		if (useIndices && indices == null)
		{
			genIndices(GLES10.GL_TRIANGLES);
			
			//return;
		}
		
		indices.position(position);
		
		short ind[] = new short[3 * 2 * amount];
		
		int off2 = 0;
		
		for (int i = 0; i < amount; i ++)
		{
			ind[0 + off2] = (short)(0 + offset + off2);
			ind[1 + off2] = (short)(1 + offset + off2);
			ind[2 + off2] = (short)(2 + offset + off2);
			ind[3 + off2] = (short)(0 + offset + off2);
			ind[4 + off2] = (short)(2 + offset + off2);
			ind[5 + off2] = (short)(3 + offset + off2);
			
			off2 += 6;
		}
		
		indices.put(ind);
		
		indices.rewind();
	}
	
	private void beginEditing()
	{
		if (!Base.usingVBO())
		{
			return;
		}
		
		GLES11.glBindBuffer(GLES11.GL_ARRAY_BUFFER, indicesId);
		
//		mapBuffer = GLES11.glMapBufferOES(GLES11.GL_ARRAY_BUFFER, GLES11.GL_WRITE_ONLY, null);
//		
//		buffer    = mapBuffer.order(ByteOrder.nativeOrder()).asFloatBuffer();
	}
	
	private void endEditing()
	{
		if (!Base.usingVBO())
		{
			return;
		}
//		glUnmapBuffer(GL_ARRAY_BUFFER);
				
		GLES11.glBindBuffer(GLES11.GL_ARRAY_BUFFER, 0);
	}
	
	public void checkIndices(int shape)
	{
		if (useIndices && indices == null)
		{
			genIndices(shape);
		}
	}
	
	public int getIndicesId()
	{
		return indicesId;
	}
	
	private void genIndices(int shape)
	{
		if (shape != GLES10.GL_TRIANGLES)
		{
			return;
		}
		
		indices = ShortBuffer.allocate(((getBuffer().capacity() / getStride()) / 4) * 3 * 2);
		
		short ind[] = new short[indices.capacity()];
		
		int offset = 0;
		
		for (int i = 0; i < ind.length; i += 6)
		{
//			0 1 2
//			0 2 3
			ind[i + 0] = (short)(0 + offset);
			ind[i + 1] = (short)(1 + offset);
			ind[i + 2] = (short)(2 + offset);
			ind[i + 3] = (short)(0 + offset);
			ind[i + 4] = (short)(2 + offset);
			ind[i + 5] = (short)(3 + offset);
			
			offset += 4;
		}
		
		indices.put(ind);
		
		indices.rewind();
	}
	
	public ShortBuffer getIndices()
	{
		return indices;
	}
}