package net.foxycorndog.jfoxylib.graphics.opengl;

public class FrameBuffer
{
	public FrameBuffer()
	{
		if (GL.getVersion().compareTo("3.0") < 0)
		{
			throw new UnsupportedOperationException("You must have OpenGL version 3.0 or greater to use FrameBuffers. (You have " + GL.getVersion() + ")");
		}
	}
}