package net.foxycorndog.jdoogl.animation;

import org.lwjgl.opengl.GL30;

import net.foxycorndog.jdoogl.GL;
import net.foxycorndog.jdoogl.model.ModelLoader;
import net.foxycorndog.jdoutil.VerticesBuffer;

public class Animation
{
	private boolean playing;
	
	private int     size;
	
	private int     vertexIds[], vertexIndicesIds[];
	
	public Animation(int vertexIds[], int vertexIndicesIds[], int size)
	{
		this.vertexIds        = vertexIds;
		this.vertexIndicesIds = vertexIndicesIds;
		
		this.size             = size;
	}
	
	public void play(int frame)
	{
		GL.renderTriangles(vertexIds[frame - 1], vertexIndicesIds[frame - 1], 0, size);
	}
	
	public static Animation loadAnimation(String location, String prefix, int digitCount, int frameCount)
	{
		if (location.endsWith("/") || location.endsWith("\\"))
		{
			
		}
		else
		{
			location += "/";
		}
		
		int size               = 0;
		
		int vertexIds[]        = new int[frameCount];
		int vertexIndicesIds[] = new int[frameCount];
		
		for (int i = 1; i <= frameCount; i ++)
		{
			String postfix = "" + i;
			
			while (postfix.length() < digitCount)
			{
				postfix = "0" + postfix;
			}
			
			Object arrays[] = ModelLoader.loadModel(location + prefix + postfix + ".obj", true, false, true, false, 10);
			
			float vertsArray[] = (float[])arrays[0];
			
			size = vertsArray.length;
			
			VerticesBuffer verts = new VerticesBuffer(vertsArray.length, 3);
			
			verts.genIndices(GL.TRIANGLES);
			
			vertexIds[i - 1]        = verts.getId();
			vertexIndicesIds[i - 1] = verts.getIndicesId(0);
		}
		
		Animation a = new Animation(vertexIds, vertexIndicesIds, size);
		
		return a;
	}
}