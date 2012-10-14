package net.foxycorndog.jdoutil;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;

public abstract class Buffer
{
	public abstract java.nio.Buffer getBuffer();
	
	public static FloatBuffer createFloatBuffer(int size)
	{
		return BufferUtils.createFloatBuffer(size);
	}
}