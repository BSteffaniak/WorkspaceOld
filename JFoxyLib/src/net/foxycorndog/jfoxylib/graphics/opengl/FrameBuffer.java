package net.foxycorndog.jfoxylib.graphics.opengl;

public class FrameBuffer
{
	public FrameBuffer()
	{
		if (!isSupported())
		{
			throw new UnsupportedOperationException("You must have OpenGL version 3.0 or greater to use FrameBuffers. (You have " + GL.getVersion() + ")");
		}
	}
	
	public boolean isSupported()
	{
		return GL.getVersion().compareTo("3.0") >= 0;
	}
}