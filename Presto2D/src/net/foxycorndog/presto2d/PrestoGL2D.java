package net.foxycorndog.presto2d;

import static org.lwjgl.opengl.GL11.GL_ALPHA_TEST;
import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_CULL_FACE;
import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL11.GL_FRONT;
import static org.lwjgl.opengl.GL11.GL_GREATER;
import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.GL_NEAREST;
import static org.lwjgl.opengl.GL11.GL_NICEST;
import static org.lwjgl.opengl.GL11.GL_NORMAL_ARRAY;
import static org.lwjgl.opengl.GL11.GL_ONE;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_PERSPECTIVE_CORRECTION_HINT;
import static org.lwjgl.opengl.GL11.GL_PROJECTION;
import static org.lwjgl.opengl.GL11.GL_SMOOTH;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_COORD_ARRAY;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MAG_FILTER;
import static org.lwjgl.opengl.GL11.GL_VERTEX_ARRAY;
import static org.lwjgl.opengl.GL11.glAlphaFunc;
import static org.lwjgl.opengl.GL11.glBlendFunc;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glCullFace;
import static org.lwjgl.opengl.GL11.glDisableClientState;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glEnableClientState;
import static org.lwjgl.opengl.GL11.glHint;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glMatrixMode;
import static org.lwjgl.opengl.GL11.glNormalPointer;
import static org.lwjgl.opengl.GL11.glOrtho;
import static org.lwjgl.opengl.GL11.glShadeModel;
import static org.lwjgl.opengl.GL11.glTexCoordPointer;
import static org.lwjgl.opengl.GL11.glTexParameteri;
import static org.lwjgl.opengl.GL11.glVertexPointer;
import static org.lwjgl.opengl.GL11.glViewport;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import net.foxycorndog.presto2d.util.SpriteSheet;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

public class PrestoGL2D
{
	private static boolean flipped;
	
	public static void beginVertexDraw(int id, int vertexSize)
	{
		glEnableClientState(GL_VERTEX_ARRAY);
		
		glBindBuffer(GL_ARRAY_BUFFER, id);
        glVertexPointer(vertexSize, GL_FLOAT, 0, 0);
	}
	
	public static void endVertexDraw()
	{
		glDisableClientState(GL_VERTEX_ARRAY);
	}
	
	public static void beginNormalDraw(int id)
	{
		glEnableClientState(GL_NORMAL_ARRAY);
		
		glBindBuffer(GL_ARRAY_BUFFER, id);
        glNormalPointer(GL_FLOAT, 0, 0);
	}
	
	public static void endNormalDraw()
	{
		glDisableClientState(GL_NORMAL_ARRAY);
	}
	
	public static void beginTextureDraw(int id, int vertexSize)
	{
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
		
		glEnableClientState(GL_TEXTURE_COORD_ARRAY);
		
		glBindBuffer(GL_ARRAY_BUFFER, id);
		glTexCoordPointer(vertexSize, GL_FLOAT, 0, 0);
	}
	
	public static void endTextureDraw()
	{
		glDisableClientState(GL_TEXTURE_COORD_ARRAY);
	}
	
	public static void initBasicView()
	{
		glEnable(GL_TEXTURE_2D);
		
		glEnable(GL_BLEND);
		glBlendFunc(GL_ONE, GL_ONE_MINUS_SRC_ALPHA);
		
		
		glEnable(GL_ALPHA_TEST);
		glAlphaFunc(GL_GREATER, 0.1f);
		
		glEnable(GL_DEPTH_TEST);
		
//		glEnable(GL_CULL_FACE);
//		glCullFace(GL_FRONT);
		
		glShadeModel(GL_SMOOTH); // Enable Smooth Shading
		glClearColor(0.0f, 0.3f, 0.6f, 0.0f); // Black Background
		
		glViewport(0, 0, Display.getWidth(), Display.getHeight());
		glMatrixMode(GL_PROJECTION); // Select The Projection Matrix
		glLoadIdentity(); // Reset The Projection Matrix

		// Calculate The Aspect Ratio Of The Window
		glOrtho(0, Display.getWidth(), 0, Display.getHeight(), -20, 20);
		glMatrixMode(GL_MODELVIEW); // Select The Modelview Matrix

		// Really Nice Perspective Calculations
		glHint(GL_PERSPECTIVE_CORRECTION_HINT, GL_NICEST);
		
		flipped = false;
	}
	
	public static void resetBasicView()
	{
		glViewport(0, 0, Display.getWidth(), Display.getHeight());
		glMatrixMode(GL_PROJECTION); // Select The Projection Matrix
		glLoadIdentity(); // Reset The Projection Matrix

		// Calculate The Aspect Ratio Of The Window
		glOrtho(0, Display.getWidth(), 0, Display.getHeight(), -20, 20);
		glMatrixMode(GL_MODELVIEW); // Select The Modelview Matrix
		
		flipped = false;
	}
	
	public static void flipView()
	{
		if (flipped)
		{
			glViewport(0, 0, Display.getWidth(), Display.getHeight());
			glMatrixMode(GL_PROJECTION); // Select The Projection Matrix
			glLoadIdentity(); // Reset The Projection Matrix

			// Calculate The Aspect Ratio Of The Window
			glOrtho(0, Display.getWidth(), 0, Display.getHeight(), -20, 20);
			glMatrixMode(GL_MODELVIEW); // Select The Modelview Matrix
		}
		else
		{
			glViewport(0, 0, Display.getWidth(), Display.getHeight());
			glMatrixMode(GL_PROJECTION); // Select The Projection Matrix
			glLoadIdentity(); // Reset The Projection Matrix

			// Calculate The Aspect Ratio Of The Window
			glOrtho(0, Display.getWidth(), Display.getHeight(), 0, -20, 20);
			glMatrixMode(GL_MODELVIEW); // Select The Modelview Matrix
		}
		
		flipped = !flipped;
	}
	
	public static void createFrame(int width, int height, String title)
	{
		try
		{
			Display.create();
			Display.setDisplayMode(new DisplayMode(width, height));
		}
		catch (LWJGLException e)
		{
			e.printStackTrace();
		}
		
		Display.setTitle(title);
		
		Display.setResizable(true);
		
		Display.setVSyncEnabled(true);
	}
	
	public static void createFrame(int width, int height)
	{
		createFrame(width, height, "");
	}
	
	public static float[] addRectVertexArray(float x, float y, float z, float width, float height, int offset, float[] array)
	{
		if (array == null)
		{
			array = new float[3 * 4];
			
			offset = 0;
		}
		
		int index = 0;
		
		array[offset + index ++] = x;
		array[offset + index ++] = y;
		array[offset + index ++] = z;
		
		array[offset + index ++] = x;
		array[offset + index ++] = y + height;
		array[offset + index ++] = z;
		
		array[offset + index ++] = x + width;
		array[offset + index ++] = y + height;
		array[offset + index ++] = z;
		
		array[offset + index ++] = x + width;
		array[offset + index ++] = y;
		array[offset + index ++] = z;
		
		return array;
	}
	
	public static float[] addSquareVertexArray(float x, float y, float z, float size, int offset, float[] array)
	{
		if (array == null)
		{
			array = new float[3 * 4];
			
			offset = 0;
		}
		
		int index = 0;
		
		array[offset + index ++] = x;
		array[offset + index ++] = y;
		array[offset + index ++] = z;
		
		array[offset + index ++] = x;
		array[offset + index ++] = y + size;
		array[offset + index ++] = z;
		
		array[offset + index ++] = x + size;
		array[offset + index ++] = y + size;
		array[offset + index ++] = z;
		
		array[offset + index ++] = x + size;
		array[offset + index ++] = y;
		array[offset + index ++] = z;
		
		return array;
	}
	
	public static float[] addSquareNormalArray(float x, float y, float z, int offset, float[] array)
	{
		if (array == null)
		{
			array = new float[2 * 4];
			
			offset = 0;
		}
		
		int index = 0;
		
		array[offset + index ++] = 0;
		array[offset + index ++] = 0;
		array[offset + index ++] = 0;
		
		array[offset + index ++] = 0;
		array[offset + index ++] = 0;
		array[offset + index ++] = 0;
		
		array[offset + index ++] = 0;
		array[offset + index ++] = 0;
		array[offset + index ++] = 0;
		
		array[offset + index ++] = 0;
		array[offset + index ++] = 0;
		array[offset + index ++] = 0;
		
		return array;
	}
	
	public static float[] addSquareVertexArray(float x, float y, int size, int offset, float[] array)
	{
		if (array == null)
		{
			array = new float[3 * 4];
			
			offset = 0;
		}
		
		int index = 0;
		
		array[offset + index ++] = x;
		array[offset + index ++] = y;
		
		array[offset + index ++] = x;
		array[offset + index ++] = y + size;
		
		array[offset + index ++] = x + size;
		array[offset + index ++] = y + size;
		
		array[offset + index ++] = x + size;
		array[offset + index ++] = y;
		
		return array;
	}
	
	public static float[] addSquareNormalArray(float x, float y, int offset, float[] array)
	{
		if (array == null)
		{
			array = new float[2 * 4];
			
			offset = 0;
		}
		
		int index = 0;
		
		array[offset + index ++] = 0;
		array[offset + index ++] = 0;
		
		array[offset + index ++] = 0;
		array[offset + index ++] = 0;
		
		array[offset + index ++] = 0;
		array[offset + index ++] = 0;
		
		array[offset + index ++] = 0;
		array[offset + index ++] = 0;
		
		return array;
	}
	
	public static float[] addRectTextureArray(SpriteSheet spriteSheet, int x, int y, int z, int width, int height, int offset, float[] array)
	{
		if (array == null)
		{
			array = new float[3 * 4];
			
			offset = 0;
		}
		
		int index = 0;
		
		float offsets[] = spriteSheet.getImageOffsets(x, y, width, height);
		
		array[offset + index ++] = offsets[0];
		array[offset + index ++] = offsets[1];
		array[offset + index ++] = 0;

		array[offset + index ++] = offsets[0];
		array[offset + index ++] = offsets[3];
		array[offset + index ++] = 0;
		
		array[offset + index ++] = offsets[2];
		array[offset + index ++] = offsets[3];
		array[offset + index ++] = 0;
		
		array[offset + index ++] = offsets[2];
		array[offset + index ++] = offsets[1];
		array[offset + index ++] = 0;
		
		return array;
	}
	
	public static float[] addRectTextureArray(SpriteSheet spriteSheet, int x, int y, int width, int height, int offset, float[] array)
	{
		if (array == null)
		{
			array = new float[2 * 4];
			
			offset = 0;
		}
		
		int index = 0;
		
		float offsets[] = spriteSheet.getImageOffsets(x, y, width, height);
		
		array[offset + index ++] = offsets[0];
		array[offset + index ++] = offsets[1];

		array[offset + index ++] = offsets[0];
		array[offset + index ++] = offsets[3];
		
		array[offset + index ++] = offsets[2];
		array[offset + index ++] = offsets[3];
		
		array[offset + index ++] = offsets[2];
		array[offset + index ++] = offsets[1];
		
		return array;
	}
}