package net.foxycorndog.jdoutil;

public abstract class Buffer
{
	public abstract java.nio.Buffer getBuffer();
	
	public abstract int getStride();
}