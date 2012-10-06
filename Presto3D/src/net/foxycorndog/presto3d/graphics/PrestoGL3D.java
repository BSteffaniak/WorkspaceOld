package net.foxycorndog.presto3d.graphics;

import static org.lwjgl.opengl.GL11.GL_AMBIENT_AND_DIFFUSE;
import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_COLOR_MATERIAL;
import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.GL_DIFFUSE;
import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL11.GL_FRONT;
import static org.lwjgl.opengl.GL11.GL_LEQUAL;
import static org.lwjgl.opengl.GL11.GL_LIGHT0;
import static org.lwjgl.opengl.GL11.GL_LIGHTING;
import static org.lwjgl.opengl.GL11.GL_LIGHT_MODEL_AMBIENT;
import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.GL_NEAREST;
import static org.lwjgl.opengl.GL11.GL_NICEST;
import static org.lwjgl.opengl.GL11.GL_NORMAL_ARRAY;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_PERSPECTIVE_CORRECTION_HINT;
import static org.lwjgl.opengl.GL11.GL_POSITION;
import static org.lwjgl.opengl.GL11.GL_PROJECTION;
import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.GL_REPLACE;
import static org.lwjgl.opengl.GL11.GL_SHININESS;
import static org.lwjgl.opengl.GL11.GL_SMOOTH;
import static org.lwjgl.opengl.GL11.GL_SPECULAR;
import static org.lwjgl.opengl.GL11.GL_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_COORD_ARRAY;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_ENV;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_ENV_MODE;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MAG_FILTER;
import static org.lwjgl.opengl.GL11.GL_VERTEX_ARRAY;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glBlendFunc;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glClearDepth;
import static org.lwjgl.opengl.GL11.glColorMaterial;
import static org.lwjgl.opengl.GL11.glDepthFunc;
import static org.lwjgl.opengl.GL11.glDisableClientState;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glEnableClientState;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glHint;
import static org.lwjgl.opengl.GL11.glLight;
import static org.lwjgl.opengl.GL11.glLightModel;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glMaterial;
import static org.lwjgl.opengl.GL11.glMaterialf;
import static org.lwjgl.opengl.GL11.glMatrixMode;
import static org.lwjgl.opengl.GL11.glNormal3f;
import static org.lwjgl.opengl.GL11.glNormalPointer;
import static org.lwjgl.opengl.GL11.glShadeModel;
import static org.lwjgl.opengl.GL11.glTexCoordPointer;
import static org.lwjgl.opengl.GL11.glTexEnvf;
import static org.lwjgl.opengl.GL11.glTexParameteri;
import static org.lwjgl.opengl.GL11.glVertex3f;
import static org.lwjgl.opengl.GL11.glVertexPointer;
import static org.lwjgl.opengl.GL11.glViewport;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.util.glu.GLU.gluPerspective;

import java.nio.FloatBuffer;
import java.util.ArrayList;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.Display;
import org.newdawn.slick.opengl.Texture;

public class PrestoGL3D
{
	
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
	
	public static void beginTextureDraw(int id)
	{
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
		
		glEnableClientState(GL_TEXTURE_COORD_ARRAY);
		
		glBindBuffer(GL_ARRAY_BUFFER, id);
		glTexCoordPointer(3, GL_FLOAT, 0, 0);
	}
	
	public static void endTextureDraw()
	{
		glDisableClientState(GL_TEXTURE_COORD_ARRAY);
	}
	
	public static void initBasicLights()
	{
		//----------- Variables & method calls added for Lighting Test -----------//
		FloatBuffer matSpecular = BufferUtils.createFloatBuffer(4);
		matSpecular.put(1.0f).put(1.0f).put(1.0f).put(1.0f).flip();
		
		FloatBuffer lightPosition = BufferUtils.createFloatBuffer(4);
		lightPosition.put(1.0f).put(1.0f).put(1.0f).put(0.0f).flip();
		
		FloatBuffer whiteLight = BufferUtils.createFloatBuffer(4);
		whiteLight.put(1.0f).put(1.0f).put(1.0f).put(1.0f).flip();
		
		FloatBuffer lModelAmbient = BufferUtils.createFloatBuffer(4);
		lModelAmbient.put(0.5f).put(0.5f).put(0.5f).put(1.0f).flip();
		
		glShadeModel(GL_SMOOTH);
		glMaterial(GL_FRONT, GL_SPECULAR, matSpecular);				// sets specular material color
		glMaterialf(GL_FRONT, GL_SHININESS, 50.0f);					// sets shininess
		
		glLight(GL_LIGHT0, GL_POSITION, lightPosition);				// sets light position
		glLight(GL_LIGHT0, GL_SPECULAR, whiteLight);				// sets specular light to white
		glLight(GL_LIGHT0, GL_DIFFUSE, whiteLight);					// sets diffuse light to white
		glLightModel(GL_LIGHT_MODEL_AMBIENT, lModelAmbient);		// global ambient light 
		
		glEnable(GL_LIGHTING);										// enables lighting
		glEnable(GL_LIGHT0);										// enables light0
		
		glEnable(GL_COLOR_MATERIAL);								// enables opengl to use glColor3f to define material color
		glColorMaterial(GL_FRONT, GL_AMBIENT_AND_DIFFUSE);			// tell opengl glColor3f effects the ambient and diffuse properties of material
		//----------- END: Variables & method calls added for Lighting Test -----------//
	}
	
	public static void initBasicView(float zClose, float zFar)
	{
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
		
		glEnable(GL_TEXTURE_2D);
		
		glShadeModel(GL_SMOOTH); // Enable Smooth Shading
		glClearColor(0.0f, 0.3f, 0.6f, 0.0f); // Blue Background
		glClearDepth(1.0); // Depth Buffer Setup
		glEnable(GL_DEPTH_TEST); // Enables Depth Testing
		glDepthFunc(GL_LEQUAL); // The Type Of Depth Testing To Do
		
		glViewport(0, 0, Display.getWidth(), Display.getHeight());
		glMatrixMode(GL_PROJECTION); // Select The Projection Matrix
		glLoadIdentity(); // Reset The Projection Matrix

		// Calculate The Aspect Ratio Of The Window
		gluPerspective(45.0f, (float)Display.getWidth() / (float)Display.getHeight(), zClose, zFar);
//		glOrtho(1, 1, 1, 1, -1, 1);
		glMatrixMode(GL_MODELVIEW); // Select The Modelview Matrix

		// Really Nice Perspective Calculations
		glHint(GL_PERSPECTIVE_CORRECTION_HINT, GL_NICEST);
		
		glTexEnvf(GL_TEXTURE_ENV, GL_TEXTURE_ENV_MODE, GL_REPLACE);
	}
	
	public static void resetBasicView(float zClose, float zFar)
	{
		glViewport(0, 0, Display.getWidth(), Display.getHeight());
		glMatrixMode(GL_PROJECTION); // Select The Projection Matrix
		glLoadIdentity(); // Reset The Projection Matrix

		// Calculate The Aspect Ratio Of The Window
		gluPerspective(45.0f, (float)Display.getWidth() / (float)Display.getHeight(), zClose, zFar);
//		glOrtho(1, 1, 1, 1, -1, 1);
		glMatrixMode(GL_MODELVIEW); // Select The Modelview Matrix
	}
	
	public static void drawCube(float x, float y, float z, float size)
	{
		
		glBegin(GL_QUADS);
		
			// Front face
			glNormal3f(0       , 0       , 1       );
			glVertex3f(x       , y       , z       );
			glNormal3f(0       , 0       , 1       );
			glVertex3f(x + size, y       , z       );
			glNormal3f(0       , 0       , 1       );
			glVertex3f(x + size, y + size, z       );
			glNormal3f(0       , 0       , 1       );
			glVertex3f(x       , y + size, z       );
			
			// Back face
			glNormal3f(0       , 0       , -1      );
			glVertex3f(x       , y       , z - size);
			glNormal3f(0       , 0       , -1      );
			glVertex3f(x       , y + size, z - size);
			glNormal3f(0       , 0       , -1      );
			glVertex3f(x + size, y + size, z - size);
			glNormal3f(0       , 0       , -1      );
			glVertex3f(x + size, y       , z - size);
			
			// Bottom face
			glNormal3f(0       , -1       , 0      );
			glVertex3f(x       , y       , z - size);
			glNormal3f(0       , -1       , 0      );
			glVertex3f(x + size, y       , z - size);
			glNormal3f(0       , -1       , 0      );
			glVertex3f(x + size, y       , z       );
			glNormal3f(0       , -1       , 0      );
			glVertex3f(x       , y       , z       );
			
			// Top face
			glNormal3f(0       , 1       , 0       );
			glVertex3f(x       , y + size, z - size);
			glNormal3f(0       , 1       , 0       );
			glVertex3f(x       , y + size, z       );
			glNormal3f(0       , 1       , 0       );
			glVertex3f(x + size, y + size, z       );
			glNormal3f(0       , 1       , 0       );
			glVertex3f(x + size, y + size, z - size);
			
			// Left face
			glNormal3f(-1      , 0       , 0       );
			glVertex3f(x       , y       , z       );
			glNormal3f(-1      , 0       , 0       );
			glVertex3f(x       , y + size, z       );
			glNormal3f(-1      , 0       , 0       );
			glVertex3f(x       , y + size, z - size);
			glNormal3f(-1      , 0       , 0       );
			glVertex3f(x       , y       , z - size);
			
			// Right face
			glNormal3f(1       , 0       , 0       );
			glVertex3f(x + size, y       , z       );
			glNormal3f(1       , 0       , 0       );
			glVertex3f(x + size, y       , z - size);
			glNormal3f(1       , 0       , 0       );
			glVertex3f(x + size, y + size, z - size);
			glNormal3f(1       , 0       , 0       );
			glVertex3f(x + size, y + size, z       );
			
		glEnd();
	}
	
	public static ArrayList<Float> addCubeArray(float x, float y, float z, int size, int offset, ArrayList<Float> array)
	{
		if (array == null)
		{
			array = new ArrayList<Float>(6 * 3 * 4);
		}
		try
		{
		// Front side
		array.add(x);
		array.add(y);
		array.add(z); 
		array.add(x + size);
		array.add(y);
		array.add(z);
		array.add(x + size);
		array.add(y + size);
		array.add(z);
		array.add(x);
		array.add(y + size);
		array.add(z);
		
		// Back side
		array.add(x);
		array.add(y);
		array.add(z - size);
		array.add(x);
		array.add(y + size);
		array.add(z - size);
		array.add(x + size);
		array.add(y + size);
		array.add(z - size);
		array.add(x + size);
		array.add(y);
		array.add(z - size);
		
		// Bottom face 
		array.add(x);
		array.add(y);
		array.add(z - size);
		array.add(x + size);
		array.add(y);
		array.add(z - size);
		array.add(x + size);
		array.add(y);
		array.add(z);
		array.add(x);
		array.add(y);
		array.add(z);
		
		// Top face
		array.add(x);
		array.add(y + size);
		array.add(z - size);
		array.add(x);
		array.add(y + size);
		array.add(z);
		array.add(x + size);
		array.add(y + size);
		array.add(z);
		array.add(x + size);
		array.add(y + size);
		array.add(z - size);
		
		// Left face
		array.add(x);
		array.add(y);
		array.add(z);
		array.add(x);
		array.add(y + size);
		array.add(z);
		array.add(x);
		array.add(y + size);
		array.add(z - size);
		array.add(x);
		array.add(y);
		array.add(z - size);
		
		// Right face
		array.add(x + size);
		array.add(y);
		array.add(z);
		array.add(x + size);
		array.add(y);
		array.add(z - size);
		array.add(x + size);
		array.add(y + size);
		array.add(z - size);
		array.add(x + size);
		array.add(y + size);
		array.add(z);
		}
		catch (OutOfMemoryError ex)
		{
		}
		return array;
	}
	
	public static ArrayList<Float> moveCubeArray(float x, float y, float z, int size, int offset, ArrayList<Float> array)
	{
		if (array == null)
		{
			array = new ArrayList<Float>(6 * 3 * 4);
		}
		
		// Front side
		array.set(offset + 0, + x);
		array.set(offset + 1, + y);
		array.set(offset + 2, + z); 
		array.set(offset + 3, + x + size);
		array.set(offset + 4, + y);
		array.set(offset + 5, + z);
		array.set(offset + 6, + x + size);
		array.set(offset + 7, + y + size);
		array.set(offset + 8, + z);
		array.set(offset + 9, + x);
		array.set(offset + 10, + y + size);
		array.set(offset + 11, + z);
		
		// Back side
		array.set(offset + 12, + x);
		array.set(offset + 13, + y);
		array.set(offset + 14, + z - size);
		array.set(offset + 15, + x);
		array.set(offset + 16, + y + size);
		array.set(offset + 17, + z - size);
		array.set(offset + 18, + x + size);
		array.set(offset + 19, + y + size);
		array.set(offset + 20, + z - size);
		array.set(offset + 21, + x + size);
		array.set(offset + 22, + y);
		array.set(offset + 23, + z - size);
		
		// Bottom face 
		array.set(offset + 24, + x);
		array.set(offset + 25, + y);
		array.set(offset + 26, + z - size);
		array.set(offset + 27, + x + size);
		array.set(offset + 28, + y);
		array.set(offset + 29, + z - size);
		array.set(offset + 30, + x + size);
		array.set(offset + 31, + y);
		array.set(offset + 32, + z);
		array.set(offset + 33, + x);
		array.set(offset + 34, + y);
		array.set(offset + 35, + z);
		
		// Top face
		array.set(offset + 36, + x);
		array.set(offset + 37, + y + size);
		array.set(offset + 38, + z - size);
		array.set(offset + 39, + x);
		array.set(offset + 40, + y + size);
		array.set(offset + 41, + z);
		array.set(offset + 42, + x + size);
		array.set(offset + 43, + y + size);
		array.set(offset + 44, + z);
		array.set(offset + 45, + x + size);
		array.set(offset + 46, + y + size);
		array.set(offset + 47, + z - size);
		
		// Left face
		array.set(offset + 48, + x);
		array.set(offset + 49, + y);
		array.set(offset + 50, + z);
		array.set(offset + 51, + x);
		array.set(offset + 52, + y + size);
		array.set(offset + 53, + z);
		array.set(offset + 54, + x);
		array.set(offset + 55, + y + size);
		array.set(offset + 56, + z - size);
		array.set(offset + 57, + x);
		array.set(offset + 58, + y);
		array.set(offset + 59, + z - size);
		
		// Right face
		array.set(offset + 60, + x + size);
		array.set(offset + 61, + y);
		array.set(offset + 62, + z);
		array.set(offset + 63, + x + size);
		array.set(offset + 64, + y);
		array.set(offset + 65, + z - size);
		array.set(offset + 66, + x + size);
		array.set(offset + 67, + y + size);
		array.set(offset + 68, + z - size);
		array.set(offset + 69, + x + size);
		array.set(offset + 70, + y + size);
		array.set(offset + 71, + z);
		
		return array;
	}
	
	public static float[] addCubeVertexArray(float x, float y, float z, int size, int offset, float array[])
	{
		if (array == null)
		{
			array = new float[6 * 4 * 3];
		}
		
		// Front side
		array[offset + 0] = x;
		array[offset + 1] = y;
		array[offset + 2] = z; 
		array[offset + 3] = x + size;
		array[offset + 4] = y;
		array[offset + 5] = z;
		array[offset + 6] = x + size;
		array[offset + 7] = y + size;
		array[offset + 8] = z;
		array[offset + 9] = x;
		array[offset + 10] = y + size;
		array[offset + 11] = z;
		
		// Back side
		array[offset + 12] = x;
		array[offset + 13] = y;
		array[offset + 14] = z - size;
		array[offset + 15] = x;
		array[offset + 16] = y + size;
		array[offset + 17] = z - size;
		array[offset + 18] = x + size;
		array[offset + 19] = y + size;
		array[offset + 20] = z - size;
		array[offset + 21] = x + size;
		array[offset + 22] = y;
		array[offset + 23] = z - size;
		
		// Bottom face 
		array[offset + 24] = x;
		array[offset + 25] = y;
		array[offset + 26] = z - size;
		array[offset + 27] = x + size;
		array[offset + 28] = y;
		array[offset + 29] = z - size;
		array[offset + 30] = x + size;
		array[offset + 31] = y;
		array[offset + 32] = z;
		array[offset + 33] = x;
		array[offset + 34] = y;
		array[offset + 35] = z;
		
		// Top face
		array[offset + 36] = x;
		array[offset + 37] = y + size;
		array[offset + 38] = z - size;
		array[offset + 39] = x;
		array[offset + 40] = y + size;
		array[offset + 41] = z;
		array[offset + 42] = x + size;
		array[offset + 43] = y + size;
		array[offset + 44] = z;
		array[offset + 45] = x + size;
		array[offset + 46] = y + size;
		array[offset + 47] = z - size;
		
		// Left face
		array[offset + 48] = x;
		array[offset + 49] = y;
		array[offset + 50] = z;
		array[offset + 51] = x;
		array[offset + 52] = y + size;
		array[offset + 53] = z;
		array[offset + 54] = x;
		array[offset + 55] = y + size;
		array[offset + 56] = z - size;
		array[offset + 57] = x;
		array[offset + 58] = y;
		array[offset + 59] = z - size;
		
		// Right face
		array[offset + 60] = x + size;
		array[offset + 61] = y;
		array[offset + 62] = z;
		array[offset + 63] = x + size;
		array[offset + 64] = y;
		array[offset + 65] = z - size;
		array[offset + 66] = x + size;
		array[offset + 67] = y + size;
		array[offset + 68] = z - size;
		array[offset + 69] = x + size;
		array[offset + 70] = y + size;
		array[offset + 71] = z;
		
		return array;
	}
	
	public static float[] addCubeTextureArray(Texture texture, int offset, float array[])
	{
		if (array == null)
		{
			array = new float[6 * 4 * 2];
		}
		
		int index = 0;
		
		for (int i = 0; i < 6; i ++)
		{
			// Front side
			array[offset + index ++] = 0;
			array[offset + index ++] = 0;
	//		array[offset + 2] = z; 
			array[offset + index ++] = texture.getWidth();
			array[offset + index ++] = 0;
	//		array[offset + 5] = z;
			array[offset + index ++] = texture.getWidth();
			array[offset + index ++] = texture.getHeight();
	//		array[offset + 8] = z;
			array[offset + index ++] = 0;
			array[offset + index ++] = texture.getHeight();
	//		array[offset + 11] = z;
		}
		
//		// Back side
//		array[offset + index ++] = x;
//		array[offset + index ++] = y;
////		array[offset + 14] = z - size;
//		array[offset + index ++] = x;
//		array[offset + index ++] = y + size;
////		array[offset + 17] = z - size;
//		array[offset + index ++] = texture.getWidth();
//		array[offset + index ++] = y + size;
////		array[offset + 20] = z - size;
//		array[offset + index ++] = texture.getWidth();
//		array[offset + index ++] = y;
////		array[offset + 23] = z - size;
//		
//		// Bottom face 
//		array[offset + index ++] = x;
//		array[offset + index ++] = y;
////		array[offset + 26] = z - size;
//		array[offset + index ++] = x + size;
//		array[offset + index ++] = y;
////		array[offset + 29] = z - size;
//		array[offset + index ++] = x + size;
//		array[offset + index ++] = y;
////		array[offset + 32] = z;
//		array[offset + index ++] = x;
//		array[offset + index ++] = y;
////		array[offset + 35] = z;
//		
//		// Top face
//		array[offset + index ++] = x;
//		array[offset + index ++] = y + size;
////		array[offset + 38] = z - size;
//		array[offset + index ++] = x;
//		array[offset + index ++] = y + size;
////		array[offset + 41] = z;
//		array[offset + index ++] = x + size;
//		array[offset + index ++] = y + size;
////		array[offset + 44] = z;
//		array[offset + index ++] = x + size;
//		array[offset + index ++] = y + size;
////		array[offset + 47] = z - size;
//		
//		// Left face
//		array[offset + index ++] = x;
//		array[offset + index ++] = y;
////		array[offset + 50] = z;
//		array[offset + index ++] = x;
//		array[offset + index ++] = y + size;
////		array[offset + 53] = z;
//		array[offset + index ++] = x;
//		array[offset + index ++] = y + size;
////		array[offset + 56] = z - size;
//		array[offset + index ++] = x;
//		array[offset + index ++] = y;
////		array[offset + 59] = z - size;
//		
//		// Right face
//		array[offset + index ++] = x + size;
//		array[offset + index ++] = y;
////		array[offset + 62] = z;
//		array[offset + index ++] = x + size;
//		array[offset + index ++] = y;
////		array[offset + 65] = z - size;
//		array[offset + index ++] = x + size;
//		array[offset + index ++] = y + size;
////		array[offset + 68] = z - size;
//		array[offset + index ++] = x + size;
//		array[offset + index ++] = y + size;
////		array[offset + 71] = z;
		
		return array;
	}
	
	public static float[] addCubeNormalArray(float x, float y, float z, int size, int offset, float array[])
	{
		if (array == null)
		{
			array = new float[6 * 4 * 3];
		}
		
		// Front side
		array[offset + 0] = 0;
		array[offset + 1] = 0;
		array[offset + 2] = 1; 
		
		array[offset + 3] = 0;
		array[offset + 4] = 0;
		array[offset + 5] = 1;
		
		array[offset + 6] = 0;
		array[offset + 7] = 0;
		array[offset + 8] = 1;
		
		array[offset + 9] = 0;
		array[offset + 10] = 0;
		array[offset + 11] = 1;
		
		
		// Back side
		array[offset + 12] = 0;
		array[offset + 13] = 0;
		array[offset + 14] = -1;
		
		array[offset + 15] = 0;
		array[offset + 16] = 0;
		array[offset + 17] = -1;
		
		array[offset + 18] = 0;
		array[offset + 19] = 0;
		array[offset + 20] = -1;
		
		array[offset + 21] = 0;
		array[offset + 22] = 0;
		array[offset + 23] = -1;
		
		
		// Bottom face 
		array[offset + 24] = 0;
		array[offset + 25] = -1;
		array[offset + 26] = 0;
		
		array[offset + 27] = 0;
		array[offset + 28] = -1;
		array[offset + 29] = 0;
		
		array[offset + 30] = 0;
		array[offset + 31] = -1;
		array[offset + 32] = 0;
		
		array[offset + 33] = 0;
		array[offset + 34] = -1;
		array[offset + 35] = 0;
		
		
		// Top face
		array[offset + 36] = 0;
		array[offset + 37] = 1;
		array[offset + 38] = 0;
		
		array[offset + 39] = 0;
		array[offset + 40] = 1;
		array[offset + 41] = 0;
		
		array[offset + 42] = 0;
		array[offset + 43] = 1;
		array[offset + 44] = 0;
		
		array[offset + 45] = 0;
		array[offset + 46] = 1;
		array[offset + 47] = 0;
		
		
		// Left face
		array[offset + 48] = -1;
		array[offset + 49] = 0;
		array[offset + 50] = 0;
		
		array[offset + 51] = -1;
		array[offset + 52] = 0;
		array[offset + 53] = 0;
		
		array[offset + 54] = -1;
		array[offset + 55] = 0;
		array[offset + 56] = 0;
		
		array[offset + 57] = -1;
		array[offset + 58] = 0;
		array[offset + 59] = 0;
		
		
		// Right face
		array[offset + 60] = 1;
		array[offset + 61] = 0;
		array[offset + 62] = 0;
		
		array[offset + 63] = 1;
		array[offset + 64] = 0;
		array[offset + 65] = 0;
		
		array[offset + 66] = 1;
		array[offset + 67] = 0;
		array[offset + 68] = 0;
		
		array[offset + 69] = 1;
		array[offset + 70] = 0;
		array[offset + 71] = 0;
		
		
		
		
		
		
		
		
		// Front side
//				array[offset + 0] = 0;
//				array[offset + 1] = 0;
//				array[offset + 2] = 0; 
//				
//				array[offset + 3] = 1;
//				array[offset + 4] = 0;
//				array[offset + 5] = 0;
//				
//				array[offset + 6] = 1;
//				array[offset + 7] = 1;
//				array[offset + 8] = 0;
//				
//				array[offset + 9] = 0;
//				array[offset + 10] = 1;
//				array[offset + 11] = 0;
//				
//				
//				// Back side
//				array[offset + 12] = 0;
//				array[offset + 13] = 0;
//				array[offset + 14] = -1;
//				
//				array[offset + 15] = 0;
//				array[offset + 16] = 1;
//				array[offset + 17] = -1;
//				
//				array[offset + 18] = 1;
//				array[offset + 19] = 1;
//				array[offset + 20] = -1;
//				
//				array[offset + 21] = 1;
//				array[offset + 22] = 0;
//				array[offset + 23] = -1;
//				
//				
//				// Bottom face 
//				array[offset + 24] = 0;
//				array[offset + 25] = 0;
//				array[offset + 26] = -1;
//				
//				array[offset + 27] = 1;
//				array[offset + 28] = 0;
//				array[offset + 29] = -1;
//				
//				array[offset + 30] = 1;
//				array[offset + 31] = 0;
//				array[offset + 32] = 0;
//				
//				array[offset + 33] = 0;
//				array[offset + 34] = 0;
//				array[offset + 35] = 0;
//				
//				
//				// Top face
//				array[offset + 36] = 0;
//				array[offset + 37] = 1;
//				array[offset + 38] = -1;
//				
//				array[offset + 39] = 0;
//				array[offset + 40] = 1;
//				array[offset + 41] = 0;
//				
//				array[offset + 42] = 1;
//				array[offset + 43] = 1;
//				array[offset + 44] = 0;
//				
//				array[offset + 45] = 1;
//				array[offset + 46] = 1;
//				array[offset + 47] = -1;
//				
//				
//				// Left face
//				array[offset + 48] = 0;
//				array[offset + 49] = 0;
//				array[offset + 50] = 0;
//				
//				array[offset + 51] = 0;
//				array[offset + 52] = 1;
//				array[offset + 53] = 0;
//				
//				array[offset + 54] = 0;
//				array[offset + 55] = 1;
//				array[offset + 56] = -1;
//				
//				array[offset + 57] = 0;
//				array[offset + 58] = 0;
//				array[offset + 59] = -1;
//				
//				
//				// Right face
//				array[offset + 60] = 1;
//				array[offset + 61] = 0;
//				array[offset + 62] = 0;
//				
//				array[offset + 63] = 1;
//				array[offset + 64] = 0;
//				array[offset + 65] = -1;
//				
//				array[offset + 66] = 1;
//				array[offset + 67] = 1;
//				array[offset + 68] = -1;
//				
//				array[offset + 69] = 1;
//				array[offset + 70] = 1;
//				array[offset + 71] = 0;
		
		
		
		return array;
	}
	
	public static float[] moveCubeArray(float x, float y, float z, int size, int offset, float array[])
	{
		if (array == null)
		{
			array = new float[6 * 4 * 3];
		}
		
		// Front side
		array[offset + 0] += x;
		array[offset + 1] += y;
		array[offset + 2] += z; 
		array[offset + 3] += x + size;
		array[offset + 4] += y;
		array[offset + 5] += z;
		array[offset + 6] += x + size;
		array[offset + 7] += y + size;
		array[offset + 8] += z;
		array[offset + 9] += x;
		array[offset + 10] += y + size;
		array[offset + 11] += z;
		
		// Back side
		array[offset + 12] += x;
		array[offset + 13] += y;
		array[offset + 14] += z - size;
		array[offset + 15] += x;
		array[offset + 16] += y + size;
		array[offset + 17] += z - size;
		array[offset + 18] += x + size;
		array[offset + 19] += y + size;
		array[offset + 20] += z - size;
		array[offset + 21] += x + size;
		array[offset + 22] += y;
		array[offset + 23] += z - size;
		
		// Bottom face 
		array[offset + 24] += x;
		array[offset + 25] += y;
		array[offset + 26] += z - size;
		array[offset + 27] += x + size;
		array[offset + 28] += y;
		array[offset + 29] += z - size;
		array[offset + 30] += x + size;
		array[offset + 31] += y;
		array[offset + 32] += z;
		array[offset + 33] += x;
		array[offset + 34] += y;
		array[offset + 35] += z;
		
		// Top face
		array[offset + 36] += x;
		array[offset + 37] += y + size;
		array[offset + 38] += z - size;
		array[offset + 39] += x;
		array[offset + 40] += y + size;
		array[offset + 41] += z;
		array[offset + 42] += x + size;
		array[offset + 43] += y + size;
		array[offset + 44] += z;
		array[offset + 45] += x + size;
		array[offset + 46] += y + size;
		array[offset + 47] += z - size;
		
		// Left face
		array[offset + 48] += x;
		array[offset + 49] += y;
		array[offset + 50] += z;
		array[offset + 51] += x;
		array[offset + 52] += y + size;
		array[offset + 53] += z;
		array[offset + 54] += x;
		array[offset + 55] += y + size;
		array[offset + 56] += z - size;
		array[offset + 57] += x;
		array[offset + 58] += y;
		array[offset + 59] += z - size;
		
		// Right face
		array[offset + 60] += x + size;
		array[offset + 61] += y;
		array[offset + 62] += z;
		array[offset + 63] += x + size;
		array[offset + 64] += y;
		array[offset + 65] += z - size;
		array[offset + 66] += x + size;
		array[offset + 67] += y + size;
		array[offset + 68] += z - size;
		array[offset + 69] += x + size;
		array[offset + 70] += y + size;
		array[offset + 71] += z;
		
		return array;
	}
	
	public static float[] getCubeArray(float x, float y, float z, int size)
	{
		float array[] = null;
		array = new float[6 * 4];
		
		array = new float[]
		{
			// Front side
			x       , y       , z,        
			x + size, y       , z,       
			x + size, y + size, z,       
			x       , y + size, z,       
			
			// Back side
			x       , y       , z - size,
			x       , y + size, z - size,
			x + size, y + size, z - size,
			x + size, y       , z - size,
			
			// Bottom face 
			x       , y       , z - size,
			x + size, y       , z - size,
			x + size, y       , z,       
			x       , y       , z,       
			
			// Top face
			x       , y + size, z - size,
			x       , y + size, z,       
			x + size, y + size, z,       
			x + size, y + size, z - size,
			
			// Left face
			x       , y       , z,       
			x       , y + size, z,       
			x       , y + size, z - size,
			x       , y       , z - size,
			
			// Right face
			x + size, y       , z       ,
			x + size, y       , z - size,
			x + size, y + size, z - size,
			x + size, y + size, z
		};
		
		return array;
	}
}