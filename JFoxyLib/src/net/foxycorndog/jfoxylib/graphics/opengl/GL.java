package net.foxycorndog.jfoxylib.graphics.opengl;

import static org.lwjgl.opengl.GL11.GL_ALL_ATTRIB_BITS;
import static org.lwjgl.opengl.GL11.GL_ALPHA_TEST;
import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_CULL_FACE;
import static org.lwjgl.opengl.GL11.GL_CURRENT_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.GL_ENABLE_BIT;
import static org.lwjgl.opengl.GL11.GL_FOG_BIT;
import static org.lwjgl.opengl.GL11.GL_GREATER;
import static org.lwjgl.opengl.GL11.GL_LEQUAL;
import static org.lwjgl.opengl.GL11.GL_LIGHTING_BIT;
import static org.lwjgl.opengl.GL11.GL_LINE_BIT;
import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.GL_MODULATE;
import static org.lwjgl.opengl.GL11.GL_NICEST;
import static org.lwjgl.opengl.GL11.GL_ONE;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_PERSPECTIVE_CORRECTION_HINT;
import static org.lwjgl.opengl.GL11.GL_POINT_BIT;
import static org.lwjgl.opengl.GL11.GL_POLYGON_BIT;
import static org.lwjgl.opengl.GL11.GL_PROJECTION;
import static org.lwjgl.opengl.GL11.GL_REPEAT;
import static org.lwjgl.opengl.GL11.GL_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_BACK;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_BIT;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_ENV;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_ENV_MODE;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_WRAP_S;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_WRAP_T;
import static org.lwjgl.opengl.GL11.glAlphaFunc;
import static org.lwjgl.opengl.GL11.glBlendFunc;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glClearDepth;
import static org.lwjgl.opengl.GL11.glCullFace;
import static org.lwjgl.opengl.GL11.glDepthFunc;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glHint;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glMatrixMode;
import static org.lwjgl.opengl.GL11.glOrtho;
import static org.lwjgl.opengl.GL11.glPixelStorei;
import static org.lwjgl.opengl.GL11.glTexEnvi;
import static org.lwjgl.opengl.GL11.glTexParameteri;
import static org.lwjgl.opengl.GL11.glViewport;
import static org.lwjgl.util.glu.GLU.gluPerspective;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.FloatBuffer;
import java.util.ArrayList;

import net.foxycorndog.jfoxylib.graphics.Texture;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;

/**
 * 
 * 
 * @author	Braden Steffaniak
 * @since	Feb 15, 2013 at 11:43:44 PM
 * @since	v0.1
 * @version	v0.1
 */
public class GL
{
	private static boolean	inited;
	
	private static float	zClose, zFar;
	private static float	fov;
	
	private static int		textureScaleMinMethod, textureScaleMagMethod, textureWrapSMethod, textureWrapTMethod;
	
	public static final int	POINTS = GL11.GL_POINTS, LINES = GL11.GL_LINES, TRIANGLES = GL11.GL_TRIANGLES, QUADS = GL11.GL_QUADS;
	
	public static final int ALL_ATTRIB_BITS = GL_ALL_ATTRIB_BITS, ENABLE_BIT = GL_ENABLE_BIT, FOG_BIT = GL_FOG_BIT,
			LIGHTING_BIT = GL_LIGHTING_BIT, LINE_BIT = GL_LINE_BIT, POINT_BIT = GL_POINT_BIT,
			POLYGON_BIT = GL_POLYGON_BIT, TEXTURE_BIT = GL_TEXTURE_BIT,
			COLOR_BUFFER_BIT = GL_COLOR_BUFFER_BIT, CURRENT_BIT = GL_CURRENT_BIT;
	
	public static final int LINEAR = GL11.GL_LINEAR, NEAREST = GL11.GL_NEAREST, REPEAT = GL11.GL_REPEAT, CLAMP = GL11.GL_CLAMP;
	
	public static void initOrtho(int width, int height)
	{
		glEnable(GL_TEXTURE_2D);
		
		glTexEnvi(GL_TEXTURE_ENV, GL_TEXTURE_ENV_MODE, GL_MODULATE);
		
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
//		GL11.glDisable(GL_BLEND);
//		GL11.glDepthMask(true);
		
		glEnable(GL_ALPHA_TEST);
		glAlphaFunc(GL_GREATER, 0.1f); 
		
		glEnable(GL_DEPTH_TEST);
		glDepthFunc(GL_LEQUAL); // The Type Of Depth Testing To Do
		
		glViewport(0, 0, width, height);
		glMatrixMode(GL_PROJECTION); // Select The Projection Matrix
		glLoadIdentity(); // Reset The Projection Matrix

		// Calculate The Aspect Ratio Of The Window
		glOrtho(0, width, 0, height, -99999, 99999);
		glMatrixMode(GL_MODELVIEW); // Select The Modelview Matrix
	    
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
	}
	
	public static void initPerspective(int width, int height, float zClose, float zFar)
	{
		fov = 55.0f;
		
		glEnable(GL_TEXTURE_2D);
		
//		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
		
//		glEnable(GL_TEXTURE_2D);
		
//		glEnable(GL_CULL_FACE);
		
//		glShadeModel(GL_SMOOTH); // Enable Smooth Shading
		glClearDepth(1.0); // Depth Buffer Setup
		glEnable(GL_DEPTH_TEST); // Enables Depth Testing
		glDepthFunc(GL_LEQUAL); // The Type Of Depth Testing To Do
		
		glEnable(GL_CULL_FACE);
		glCullFace(GL_BACK);
		
		glViewport(0, 0, width, height);
		glMatrixMode(GL_PROJECTION); // Select The Projection Matrix
		glLoadIdentity(); // Reset The Projection Matrix

		// Calculate The Aspect Ratio Of The Window
		gluPerspective(fov, (float)width / height, zClose, zFar);
//		glOrtho(1, 1, 1, 1, -1, 1);
		glMatrixMode(GL_MODELVIEW); // Select The Modelview Matrix

		// Really Nice Perspective Calculations
		glHint(GL_PERSPECTIVE_CORRECTION_HINT, GL_NICEST);
		
		glTexEnvi(GL_TEXTURE_ENV, GL_TEXTURE_ENV_MODE, GL_MODULATE);//GL_DECAL);
	}
	
	public static int getTextureScaleMinMethod()
	{
		return textureScaleMinMethod;
	}
	
	public static int getTextureScaleMagMethod()
	{
		return textureScaleMagMethod;
	}
	
	public static void setTextureScaleMinMethod(int method)
	{
		GL.textureScaleMinMethod = method;
		
	    GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, method);
	}
	
	public static void setTextureScaleMagMethod(int method)
	{
		GL.textureScaleMagMethod = method;
		
	    GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, method);
	}
	
	public static int getTextureWrapSMethod()
	{
		return textureWrapSMethod;
	}
	
	public static int getTextureWrapTMethod()
	{
		return textureWrapTMethod;
	}
	
	public static void setTextureWrapSMethod(int method)
	{
		GL.textureWrapSMethod = method;
		
	    GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, method);
	}
	
	public static void setTextureWrapTMethod(int method)
	{
		GL.textureWrapTMethod = method;
		
	    GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, method);
	}
	
	public static String getVersion()
	{
		return GL11.glGetString(GL11.GL_VERSION);
	}
	
	public static void pushAttrib(int bit)
	{
		GL11.glPushAttrib(bit);
	}
	
	public static void popAttrib()
	{
		GL11.glPopAttrib();
	}
	
	public static void pushMatrix()
	{
		GL11.glPushMatrix();
	}
	
	public static void popMatrix()
	{
		GL11.glPopMatrix();
	}
	
	public static void rotate(float x, float y, float z)
	{
		GL11.glRotatef(x, 1, 0, 0);
		GL11.glRotatef(y, 0, 1, 0);
		GL11.glRotatef(z, 0, 0, 1);
	}
	
	public static void translate(float x, float y, float z)
	{
		GL11.glTranslatef(x, y, z);
	}
	
	public static void scale(float x, float y, float z)
	{
		GL11.glScalef(x, y, z);
	}
	
	public static float[] getAmountScaled()
	{
		float scaled[] = new float[3];
		
		FloatBuffer buffer = BufferUtils.createFloatBuffer(4 * 4);
		
		GL11.glGetFloat(GL11.GL_MODELVIEW_MATRIX, buffer);
		
		scaled[0] = buffer.get(0);
		scaled[1] = buffer.get(1 + 1 * 4);
		scaled[2] = buffer.get(2 + 2 * 4);
		
		return scaled;
	}
	
	public static float[] getAmountTranslated()
	{
		float translated[] = new float[3];
		
		FloatBuffer buffer = BufferUtils.createFloatBuffer(4 * 4);
		
		GL11.glGetFloat(GL11.GL_MODELVIEW_MATRIX, buffer);
		
		translated[0] = buffer.get(0 + 3 * 4);
		translated[1] = buffer.get(1 + 3 * 4);
		translated[2] = buffer.get(2 + 3 * 4);
		
		return translated;
	}
	
	public static void drawRect(int x, int y, int width, int height)
	{
		GL11.glBegin(GL11.GL_QUADS);
		{
			GL11.glVertex2f(x, y);
			GL11.glVertex2f(x + width, y);
			GL11.glVertex2f(x + width, y + height);
			GL11.glVertex2f(x, y + height);
		}
		GL11.glEnd();
	}
	
	public static void drawRect(int x, int y, int width, int height, Texture texture)
	{
		texture.bind();
		float offsets[] = texture.getImageOffsets();
		
		GL11.glBegin(GL11.GL_QUADS);
		{
			GL11.glVertex2f(x, y);
			GL11.glTexCoord2f(offsets[0], offsets[1]);
			GL11.glVertex2f(x + width, y);
			GL11.glTexCoord2f(offsets[0], offsets[3]);
			GL11.glVertex2f(x + width, y + height);
			GL11.glTexCoord2f(offsets[2], offsets[3]);
			GL11.glVertex2f(x, y + height);
			GL11.glTexCoord2f(offsets[2], offsets[1]);
		}
		GL11.glEnd();
	}
	
	public static void setColor(float r, float g, float b, float a)
	{
		GL11.glColor4f(r, g, b, a);
	}
	
	public static void setClearColor(float r, float g, float b, float a)
	{
		GL11.glClearColor(r, g, b, a);
	}
	
	public static void resetMatrix()
	{
		GL11.glLoadIdentity();
	}
	
	public static float getFOV()
	{
		return fov;
	}
	
	public static void setFOV(float FOV)
	{
		GL.fov = FOV;
	}
	
	public static float[] genRectVerts(float x, float y, float width, float height)
	{
		float array[] = new float[4 * 2];
		
		int index = 0;
		
		x      -= 0.001f;
		width  += 0.002f;
		y      -= 0.001f;
		height += 0.002f;
		
		// Front
		array[index++] = x;
		array[index++] = y;
		
		array[index++] = x + width;
		array[index++] = y;
		
		array[index++] = x + width;
		array[index++] = y + height;
		
		array[index++] = x;
		array[index++] = y + height;
		
		return array;
	}
	
	public static float[] genRectVerts(float x, float y, float z, float width, float height)
	{
		float array[] = new float[4 * 3];
		
		int index = 0;
		
		// Front
		array[index++] = x;
		array[index++] = y;
		array[index++] = z;
		
		array[index++] = x + width;
		array[index++] = y;
		array[index++] = z;
		
		array[index++] = x + width;
		array[index++] = y + height;
		array[index++] = z;
		
		array[index++] = x;
		array[index++] = y + height;
		array[index++] = z;
		
		return array;
	}
	
	public static float[] genRectTextures(Texture texture)
	{
		return genRectTextures(texture.getImageOffsets());
	}
	
	public static float[] genRectTextures(Texture texture, int rx, int ry)
	{
		return genRectTextures(texture.getImageOffsets(), rx, ry);
	}
	
	public static float[] genRectTextures(Texture texture, boolean mirrorHorizontal, boolean mirrorVertical)
	{
		return genRectTextures(texture.getImageOffsets(), mirrorHorizontal, mirrorVertical);
	}
	
	public static float[] genRectTextures(float offsets[])
	{
		return genRectTextures(offsets, 1, 1, false, false);
	}
	
	public static float[] genRectTextures(float offsets[], int rx, int ry)
	{
		return genRectTextures(offsets, rx, ry, false, false);
	}
	
	public static float[] genRectTextures(float offsets[], boolean mirrorHorizontal, boolean mirrorVertical)
	{
		return genRectTextures(offsets, 1, 1, mirrorHorizontal, mirrorVertical);
	}
	
	public static float[] genRectTextures(float offsets[], int rx, int ry, boolean mirrorHorizontal, boolean mirrorVertical)
	{
		float array[] = new float[4 * 2];
		
		float tolerance = -0.00001f;
		
		if (mirrorHorizontal)
		{
			array[0] = rx * offsets[2] + tolerance * 2;
			
			array[2] = rx * offsets[0] - tolerance;
			
			array[4] = rx * offsets[0] - tolerance;

			array[6] = rx * offsets[2] + tolerance * 2;
		}
		else
		{
			array[0] = rx * offsets[0] - tolerance;
			
			array[2] = rx * offsets[2] + tolerance * 2;
			
			array[4] = rx * offsets[2] + tolerance * 2;
			
			array[6] = rx * offsets[0] - tolerance;
		}
		
		if (mirrorVertical)
		{
			array[1] = ry * offsets[3] + tolerance * 2;
			
			array[3] = ry * offsets[3] + tolerance * 2;
			
			array[5] = ry * offsets[1] - tolerance;
			
			array[7] = ry * offsets[1] - tolerance;
		}
		else
		{
			array[1] = ry * offsets[1] - tolerance;
			
			array[3] = ry * offsets[1] - tolerance;
			
			array[5] = ry * offsets[3] + tolerance * 2;
			
			array[7] = ry * offsets[3] + tolerance * 2;
		}
		
		return array;
	}
	
	public static float[] genCubeVerts(float x, float y, float z, float width, float height, float depth)
	{
		float array[] = new float[4 * 6 * 3];
		
		int index = 0;
		
		
		// Back
		array[index++] = x;
		array[index++] = y;
		array[index++] = z + depth;
		
		array[index++] = x + width;
		array[index++] = y;
		array[index++] = z + depth;
		
		array[index++] = x + width;
		array[index++] = y + height;
		array[index++] = z + depth;
		
		array[index++] = x;
		array[index++] = y + height;
		array[index++] = z + depth;
		
		
		// Right
		array[index++] = x + width;
		array[index++] = y;
		array[index++] = z + depth;
		
		array[index++] = x + width;
		array[index++] = y;
		array[index++] = z;
		
		array[index++] = x + width;
		array[index++] = y + height;
		array[index++] = z;
		
		array[index++] = x + width;
		array[index++] = y + height;
		array[index++] = z + depth;
		
		// Front
		array[index++] = x + width;
		array[index++] = y;
		array[index++] = z;
		
		array[index++] = x;
		array[index++] = y;
		array[index++] = z;
		
		array[index++] = x;
		array[index++] = y + height;
		array[index++] = z;
		
		array[index++] = x + width;
		array[index++] = y + height;
		array[index++] = z;
		
		
		// Left
		array[index++] = x;
		array[index++] = y;
		array[index++] = z;
		
		array[index++] = x;
		array[index++] = y;
		array[index++] = z + depth;
		
		array[index++] = x;
		array[index++] = y + height;
		array[index++] = z + depth;
		
		array[index++] = x;
		array[index++] = y + height;
		array[index++] = z;
		
		
		// Top
		array[index++] = x + width;
		array[index++] = y + height;
		array[index++] = z;
		
		array[index++] = x;
		array[index++] = y + height;
		array[index++] = z;
		
		array[index++] = x;
		array[index++] = y + height;
		array[index++] = z + depth;
		
		array[index++] = x + width;
		array[index++] = y + height;
		array[index++] = z + depth;
		
		
		// Bottom
		array[index++] = x;
		array[index++] = y;
		array[index++] = z + depth;
		
		array[index++] = x;
		array[index++] = y;
		array[index++] = z;
		
		array[index++] = x + width;
		array[index++] = y;
		array[index++] = z;
		
		array[index++] = x + width;
		array[index++] = y;
		array[index++] = z + depth;
		
		return array;
	}
	
	public static float[] genRectColors(float r, float g, float b, float a)
	{
		float array[] = new float[4 * 4];
		
		int index = 0;
		
		array[index++] = r;
		array[index++] = g;
		array[index++] = b;
		array[index++] = a;
		
		array[index++] = r;
		array[index++] = g;
		array[index++] = b;
		array[index++] = a;
		
		array[index++] = r;
		array[index++] = g;
		array[index++] = b;
		array[index++] = a;
		
		array[index++] = r;
		array[index++] = g;
		array[index++] = b;
		array[index++] = a;
		
		return array;
	}
	
//	public static void initBasicView(float zClose, float zFar)
//	{
//		glPixelStorei(GL11.GL_UNPACK_ALIGNMENT, 1);
//		
//		GL.zClose = zClose;
//		GL.zFar   = zFar;
//		
//		glEnable(GL_BLEND);
//		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
//		
//		if (render3D)
//		{
//			glEnable(GL11.GL_TEXTURE_2D);
//			
//			glEnable(GL_CULL_FACE);
//			
//			glClearColor(0.0f, 0.3f, 0.6f, 0.0f); // Blue Background
//			glClearDepth(1.0); // Depth Buffer Setup
//			glEnable(GL_DEPTH_TEST); // Enables Depth Testing
//			glDepthFunc(GL_LEQUAL); // The Type Of Depth Testing To Do
//			
//			glViewport(0, 0, Display.getWidth(), Display.getHeight());
//			glMatrixMode(GL_PROJECTION); // Select The Projection Matrix
//			glLoadIdentity(); // Reset The Projection Matrix
//			
//			FOV = 55.0f;
//
//			// Calculate The Aspect Ratio Of The Window
//			gluPerspective(FOV, (float)Display.getWidth() / (float)Display.getHeight(), zClose, zFar);
//			glMatrixMode(GL_MODELVIEW);
//
//			// Really Nice Perspective Calculations
//			glHint(GL_PERSPECTIVE_CORRECTION_HINT, GL_NICEST);
//			
//			glTexEnvi(GL_TEXTURE_ENV, GL_TEXTURE_ENV_MODE, GL_MODULATE);
//		}
//		else
//		{
//			glEnable(GL_TEXTURE_2D);
//			
//			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
//			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
//			
//			glTexEnvi(GL_TEXTURE_ENV, GL_TEXTURE_ENV_MODE, GL_MODULATE);
//			
//			glEnable(GL_BLEND);
//			glBlendFunc(GL_ONE, GL_ONE_MINUS_SRC_ALPHA);
//			
//			glEnable(GL_ALPHA_TEST);
//			glAlphaFunc(GL_GREATER, 0.1f); 
//			
//			glEnable(GL_DEPTH_TEST);
//			
//			glClearColor(0.0f, 0.3f, 0.6f, 0.0f); // Blue Background
//		}
//	}
}