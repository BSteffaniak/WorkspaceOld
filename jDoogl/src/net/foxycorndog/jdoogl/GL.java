package net.foxycorndog.jdoogl;

import static net.foxycorndog.jdoogl.GL.beginTextureDraw;
import static net.foxycorndog.jdoogl.GL.beginVertexDraw;
import static net.foxycorndog.jdoogl.GL.endTextureDraw;
import static net.foxycorndog.jdoogl.GL.endVertexDraw;
import static org.lwjgl.opengl.GL11.GL_ALPHA_TEST;
import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.GL_FLOAT;
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
import static org.lwjgl.opengl.GL11.GL_OR_INVERTED;
import static org.lwjgl.opengl.GL11.glLogicOp;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MAG_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MIN_FILTER;
import static org.lwjgl.opengl.GL11.GL_VERTEX_ARRAY;
import static org.lwjgl.opengl.GL11.glAlphaFunc;
import static org.lwjgl.opengl.GL11.glBlendFunc;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glDisableClientState;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glIsEnabled;
import static org.lwjgl.opengl.GL11.glEnableClientState;
import static org.lwjgl.opengl.GL11.glHint;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glMatrixMode;
import static org.lwjgl.opengl.GL11.glNormalPointer;
import static org.lwjgl.opengl.GL11.glRotatef;
import static org.lwjgl.opengl.GL11.glRotated;
import static org.lwjgl.opengl.GL11.glOrtho;
import static org.lwjgl.opengl.GL11.glShadeModel;
import static org.lwjgl.opengl.GL11.glScissor;
import static org.lwjgl.opengl.GL11.glTexCoordPointer;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glTexParameteri;
import static org.lwjgl.opengl.GL11.glVertexPointer;
import static org.lwjgl.opengl.GL11.glViewport;
import static org.lwjgl.opengl.GL11.glTranslatef;
import static org.lwjgl.opengl.GL11.glTranslated;
import static org.lwjgl.opengl.GL11.GL_COLOR_LOGIC_OP;
import static org.lwjgl.opengl.GL11.glScalef;
//import static org.lwjgl.opengl.GL11.gl
import static org.lwjgl.opengl.GL11.glColor4f;
import static org.lwjgl.opengl.GL11.glScaled;
import static org.lwjgl.opengl.GL11.glDrawArrays;
import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.GL_SCISSOR_TEST;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.glBindBuffer;

import static org.lwjgl.opengl.GL11.*;

import java.awt.Canvas;
import java.io.File;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.util.ArrayList;

import net.foxycorndog.jdobase.Base;
import net.foxycorndog.jdoogl.components.Frame;
import net.foxycorndog.jdoogl.image.imagemap.ImageMap;
import net.foxycorndog.jdoogl.image.imagemap.SpriteSheet;
import net.foxycorndog.jdoogl.image.imagemap.Texture;
import net.foxycorndog.jdoutil.Buffer;
import net.foxycorndog.jdoutil.HeavyBuffer;
import net.foxycorndog.jdoutil.LightBuffer;
import net.foxycorndog.jdoutil.Task;

import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.openal.AL;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL14;

/**
* Class used for accessing many static methods that incorporate
* OpenGL.
* 
* @author Braden Steffaniak
* @since  Fri May 25 10:29:08 AM
*/

public class GL
{
	private static boolean flipped;
	
	private static double  offsets[] = new double[3];
	private static double  scale[]   = new double[] { 1, 1, 1 };
	
	private static ArrayList<double[]> tempOffsets = new ArrayList<double[]>();
	private static ArrayList<double[]> tempScale   = new ArrayList<double[]>();
	
	public  static Texture     white;
	
	private static int	       rectVerticesPosition, rectTexturesPosition;
	
	private static ArrayList<LightBuffer> rectVerticesBuffer, rectTexturesBuffer;
	
	public static final int QUADS = GL11.GL_QUADS;
	
	/**
	 * Gets the white texture that can be used to create any solid
	 * color if glSetColor(r, g, b, a) is called before render.
	 * 
	 * @return The white Texture.
	 */
	public static Texture getWhite()
	{
		return white;
	}
	
	public static void glBegin(int mode)
	{
		GL11.glBegin(mode);
	}
	
	public static void glEnd()
	{
		GL11.glEnd();
	}
	
	public static void glVertex3f(float x, float y, float z)
	{
		GL11.glVertex3f(x, y, z);
	}
	
	public static void glVertex3d(double x, double y, double z)
	{
		GL11.glVertex3d(x, y, z);
	}
	
	/**
	 * Create a rectangle at the specified position, with the specified
	 * dimensions;
	 * 
	 * @param x The x position of the rectangle.
	 * @param y The y position of the rectangle.
	 * @param z The z position of the rectangle.
	 * @param width The width of the rectangle.
	 * @param height The height of the rectangle.
	 * @return The id (position) of the rectangle.
	 */
	public static int createRect(float x, float y, float z, float width, float height)
	{
		if (rectVerticesBuffer == null)
		{
			throw new NullPointerException("You must use setAmountOfElements(int amount) before you can create any elements.");
		}
		
		rectVerticesBuffer.get(rectVerticesPosition % (1000 * 4 * 3)).addData(addRectVertexArrayf(x, y, z, width, height, 0, null));
		
		rectVerticesPosition += 1;
		
		return rectVerticesPosition - 1;
	}
	
	/**
	 * Set the texture offsets for the rectangle at the specified
	 * position. To get the offsets array, use the method
	 * ImageMap.getImageOffsetsf();
	 * 
	 * @param position The id (position) of the rectangle.
	 * @param offsets The offsets of the texture.
	 */
	public static void setRectTextureOffsets(int position, float offsets[])
	{
		float textures[] = addRectTextureArrayf(offsets, 0, null);
		
		System.out.println();
		
		rectTexturesBuffer.get(rectTexturesPosition % (1000 * 4 * 3)).setData(position * 4 * 2, textures);
	}
	
	/**
	 * Renders the rectangle at the specified position in the array.
	 * 
	 * @param position The id (position) of the rectangle.
	 */
	public static void renderRect(int position)
	{
		if (rectVerticesBuffer == null)
		{
			return;
		}
		
		beginVertexDraw(rectVerticesBuffer.get(rectVerticesPosition % (1000 * 4 * 3)), 3);
		beginTextureDraw(rectTexturesBuffer.get(rectVerticesPosition % (1000 * 4 * 3)), 2);
		
		glDrawArrays(QUADS, position * 4, 4);
		
		endTextureDraw();
		endVertexDraw();
	}
	
	/**
	 * Renders all of the rectangles to the screen.
	 */
	public static void renderRects()
	{
		if (rectVerticesBuffer == null)
		{
			return;
		}
		
		beginVertexDraw(rectVerticesBuffer.get(rectVerticesPosition % (1000 * 4 * 3)), 3);
		beginTextureDraw(rectTexturesBuffer.get(rectVerticesPosition % (1000 * 4 * 3)), 2);
		
		glDrawArrays(QUADS, 0, rectVerticesPosition * 4);
		
		endTextureDraw();
		endVertexDraw();
	}
	
	/**
	 * Set the color to the specified float values. Default color is
	 * (1, 1, 1, 1)
	 * 
	 * @param r The red value. (0.0f - 1.0f)
	 * @param g The green value. (0.0f - 1.0f)
	 * @param b The blue value. (0.0f - 1.0f)
	 * @param a The alpha value. (0.0f - 1.0f)
	 */
	public static void setColorf(float r, float g, float b, float a)
	{
		glColor4f(r, g, b, a);
	}
	
	/**
	 * Sets the color to the specified integer vales. Default color is
	 * (255, 255, 255, 255)
	 * This is the more known way of setting rgb colors.
	 * 
	 * @param r The red value (0 - 255)
	 * @param g The green value (0 - 255)
	 * @param b The blue value (0 - 255)
	 * @param a The alpha value (0 - 255)
	 */
	public static void setColori(int r, int g, int b, int a)
	{
		glColor4f(r / 255f, g / 255f, b / 255f, a / 255f);
	}
	
	/**
	 * Set the color in which the screen will clear to, to the specified
	 * float values. Default color is (1, 1, 1, 1).
	 * 
	 * @param r The red value. (0.0f - 1.0f)
	 * @param g The green value. (0.0f - 1.0f)
	 * @param b The blue value. (0.0f - 1.0f)
	 * @param a The alpha value. (0.0f - 1.0f)
	 */
	public static void setClearColorf(float r, float g, float b, float a)
	{
		glClearColor(r, g, b, a);
	}
	
	/**
	 * Set the color in which the screen will clear to, to the specified
	 * integer values. Default color is (255, 255, 255, 255).
	 * 
	 * @param r The red value (0 - 255)
	 * @param g The green value (0 - 255)
	 * @param b The blue value (0 - 255)
	 * @param a The alpha value (0 - 255)
	 */
	public static void setClearColori(int r, int g, int b, int a)
	{
		glClearColor(r / 255f, g / 255f, b / 255f, a / 255f);
	}
	
	/**
	 * Renders the specified amount of quads to the screen, starting
	 * at the specified start position.
	 * 
	 * @param start The offset to start at.
	 * @param amount The amount of quads to render to the screen.
	 */
	private static void renderQuads(int start, int amount)
	{
		glDrawArrays(QUADS, start * 4, amount * 4);
	}
	
	/**
	 * Renders all of the rectangles within the specified dimensions.
	 * 
	 * @param x The x offset to render.
	 * @param y The y offset to render.
	 * @param width The width of the amount of rectangles to render.
	 * @param height The height of the amount of rectangles to render.
	 * @param verticesBuffer The LightBuffer that holds the information
	 * 		for the vertices.
	 * @param texturesBuffer The LightBuffer that holds the information
	 * 		for the texture offsets.
	 * @param imageMap The ImageMap that will be bound and used for
	 * 		drawing the textures.
	 * @param arrayWidth The width of the buffer array to loop through.
	 * @param arrayHeight The height of the buffer array to loop through.
	 */
	public static void renderQuadRect(int x, int y, int width, int height, LightBuffer verticesBuffer, LightBuffer texturesBuffer, ImageMap imageMap, int arrayWidth, int arrayHeight)
	{
		beginVertexDraw(verticesBuffer, 2);
		beginTextureDraw(texturesBuffer, 2);
		
		imageMap.bind();
		
		renderRect(x, y, width, height, 4, arrayWidth, arrayHeight, QUADS);
		
		endTextureDraw();
		endVertexDraw();
	}
	
	/**
	 * Renders all of the rectangles within the specified dimensions.
	 * 
	 * @param x The x offset to render.
	 * @param y The y offset to render.
	 * @param width The width of the amount of rectangles to render.
	 * @param height The height of the amount of rectangles to render.
	 * @param arrayWidth The width of the buffer array to loop through.
	 * @param arrayHeight The height of the buffer array to loop through.
	 */
	public static void renderQuadRect(int x, int y, int width, int height, int arrayWidth, int arrayHeight)
	{
		renderRect(x, y, width, height, 4, arrayWidth, arrayHeight, QUADS);
	}
	
//	public static void drawQuad(int x, int y, int z, int width, int height)
//	{
//		glDrawArrays(GL_QUADS, 0, 1);
//	}
	
	/**
	 * Renders an array of rectangles within the specified dimensions.
	 * 
	 * @param x The x offset to render.
	 * @param y The y offset to render.
	 * @param width The width of the amount of rectangles to render.
	 * @param height The height of the amount of rectangles to render.
	 * @param stride The stride from one vertex to another.
	 * @param arrayWidth The width of the buffer array to loop through.
	 * @param arrayHeight The height of the buffer array to loop through.
	 * @param type The type of object that is being rendered.
	 */
	private static void renderRect(int x, int y, int width, int height, int stride, int arrayWidth, int arrayHeight, int type)
	{
		if (x + width >= arrayWidth)
		{
			width = arrayWidth - x;
		}
		if (y + height >= arrayHeight)
		{
			height = arrayHeight - y;
		}
		
		for (int i = y; i < height + y; i ++)
		{
			int dx = x * stride + (i * stride * arrayWidth);
			
			glDrawArrays(type, dx, width * stride);
		}
	}
	
	/**
	 * Begins the manipulation of the current matrix.
	 */
	public static void beginManipulation()
	{
		tempOffsets.add(offsets.clone());
		tempScale.add  (scale.clone());
		
		glPushMatrix();
	}
	
	/**
	 * Ends the manipulation of the current matrix and returns to
	 * the matrix before beginManipulation() was called.
	 */
	public static void endManipulation()
	{
		double temp[] = tempOffsets.get(tempOffsets.size() - 1);
		
		offsets[0] = temp[0];
		offsets[1] = temp[1];
		offsets[2] = temp[2];
		
		tempOffsets.remove(tempOffsets.size() - 1);
		
		
		temp = tempScale.get(tempScale.size() - 1);
		
		scale[0] = temp[0];
		scale[1] = temp[1];
		scale[2] = temp[2];
		
		tempScale.remove(tempScale.size() - 1);
		
		glPopMatrix();
	}
	
	/**
	 * Enables the ability to clip a part of the screen for rendering.
	 */
	public static void enableClipping()
	{
		glEnable(GL_SCISSOR_TEST);
	}
	
	/**
	 * Ends the ability to clip a part of the screen for rendering.
	 */
	public static void endClipping()
	{
		glDisable(GL_SCISSOR_TEST);
	}
	
	/**
	 * Enables the ability to invert anything rendered.
	 */
	public static void enableInverting()
	{
		glEnable(GL_COLOR_LOGIC_OP);
	}
	
	/**
	 * Ends the ability to invert anything rendered.
	 */
	public static void endInverting()
	{
		glDisable(GL_COLOR_LOGIC_OP);
	}
	
	/**
	 * Begin inverting everything rendered according to the opposite
	 * of their rgb values.
	 */
	public static void beginInverting()
	{
		enableInverting();
		
		/*
		 * GL_CLEAR
		 * GL_SET
		 * GL_COPY
		 * GL_COPY_INVERTED
		 * GL_NOOP
		 * GL_INVERT
		 * GL_AND
		 * GL_NAND
		 * GL_OR
		 * GL_NOR
		 * GL_XOR
		 * GL_EQUIV
		 * GL_AND_REVERSE
		 * GL_AND_INVERTED
		 * GL_OR_REVERSE
		 * GL_OR_INVERTED 
		 */
		
		glLogicOp(GL_COPY_INVERTED);
	}
	
	/**
	 * Begins inverting everything rendered according to whatever is
	 * being rendered behind it.
	 */
	public static void beginInvertingBackground()
	{
		enableInverting();
		
		/*
		 * GL_CLEAR
		 * GL_SET
		 * GL_COPY
		 * GL_COPY_INVERTED
		 * GL_NOOP
		 * GL_INVERT
		 * GL_AND
		 * GL_NAND
		 * GL_OR
		 * GL_NOR
		 * GL_XOR
		 * GL_EQUIV
		 * GL_AND_REVERSE
		 * GL_AND_INVERTED
		 * GL_OR_REVERSE
		 * GL_OR_INVERTED 
		 */
		
		glLogicOp(GL_INVERT);
	}
	
	/**
	 * Begins inverting everything by XORing them.
	 */
	public static void beginXOR()
	{
		enableInverting();
		
		/*
		 * GL_CLEAR
		 * GL_SET
		 * GL_COPY
		 * GL_COPY_INVERTED
		 * GL_NOOP
		 * GL_INVERT
		 * GL_AND
		 * GL_NAND
		 * GL_OR
		 * GL_NOR
		 * GL_XOR
		 * GL_EQUIV
		 * GL_AND_REVERSE
		 * GL_AND_INVERTED
		 * GL_OR_REVERSE
		 * GL_OR_INVERTED 
		 */
		
		glLogicOp(GL_NOR);
	}
	
	/**
	 * Begins clipping the render frame at the specified position with
	 * the specified dimensions.
	 * 
	 * @param x The x position on the Frame to start clipping.
	 * @param y The y position of the Frame to start clipping.
	 * @param width The width of the area to be not clipped out.
	 * @param height The height of the area to be not clipped out.
	 */
	public static void beginClipping(int x, int y, int width, int height)
	{
		enableClipping();
		
		glScissor(x, y, width, height);
	}
	
	/**
	 * Initialize the OpenGL lighting.
	 */
	public static void initLighting()
	{
		glLightModeli(GL_LIGHT_MODEL_LOCAL_VIEWER, GL_TRUE);
		
		glEnable(GL_COLOR_MATERIAL);
		glEnable(GL_LIGHTING);
		glEnable(GL_LIGHT0);
		glLightModeli(GL_LIGHT_MODEL_TWO_SIDE, 0);
		glLightModeli(GL_FRONT, GL_DIFFUSE);
		glEnable(GL_NORMALIZE);
	}
	
//	public static void startNormal()
//	{
//		glNormal3f(0, 0, 1);
//	}
	
	/**
	 * Set the GL_LOGHT0 location to the specified location.
	 * 
	 * @param x The x position in the scene.
	 * @param y The y position in the scene.
	 * @param z The z position in the scene.
	 */
	public static void setLightLocation(float x, float y, float z)
	{
		FloatBuffer pos = BufferUtils.createFloatBuffer(8);
		pos.put(new float[] { x, y, z, 1.0f }).flip();
		
		glLight(GL_LIGHT0, GL_POSITION, pos);
	}
	
	/**
	 * Create a new light to be rendered in the scene.
	 */
	public static void light()
	{
		FloatBuffer amb = BufferUtils.createFloatBuffer(8);
		amb.put(new float[] { -0.2f, -0.2f, -0.2f, 1.0f }).flip();
		
		FloatBuffer dif = BufferUtils.createFloatBuffer(8);
		dif.put(new float[] { 10f, 10f, 10f, 1.0f }).flip();
		
//		FloatBuffer spe = BufferUtils.createFloatBuffer(8);
//		spe.put(new float[] { 0.0f, 0.0f, 0.0f, 1.0f }).flip();
		
		glLight(GL_LIGHT0, GL_AMBIENT, amb);
		glLight(GL_LIGHT0, GL_DIFFUSE, dif);
//		glLight(GL_LIGHT0, GL_SPECULAR, spe);
	}
	
//	public static void setLightProperties()
//	{
//		FloatBuffer black = BufferUtils.createFloatBuffer(8);
//		black.put(new float[] { 0, 0, 0, 1 }).flip();
//		
//		FloatBuffer green = BufferUtils.createFloatBuffer(8);
//		green.put(new float[] { 0, 1, 0, 1 }).flip();
//		
//		FloatBuffer white = BufferUtils.createFloatBuffer(8);
//		white.put(new float[] { 1, 1, 1, 1 }).flip();
//		
//		glMaterial(GL_FRONT, GL_AMBIENT, white);
//		glMaterial(GL_FRONT, GL_DIFFUSE, white);
//		glMaterial(GL_FRONT, GL_SPECULAR, black);
//		
////		glMaterialf(GL_FRONT, GL_SHININESS, 60.0f);
//	}

	/**
	 * Rotates the scene the specified float amount for each axes.
	 * 
	 * @param x The amount to rotate along the x axis.
	 * @param y The amount to rotate along the y axis.
	 * @param z The amount to rotate along the z axis.
	 */
	public static void rotatef(float x, float y, float z)
	{
		if (x != 0)
		{
			glRotatef(x, 1, 0, 0);
		}
		if (y != 0)
		{
			glRotatef(y, 0, 1, 0);
		}
		if (z != 0)
		{
			glRotatef(z, 0, 0, 1);
		}
	}
	
	/**
	 * Rotates the scene the specified double amount for each axes.
	 * 
	 * @param x The amount to rotate along the x axis.
	 * @param y The amount to rotate along the y axis.
	 * @param z The amount to rotate along the z axis.
	 */
	public static void rotated(double x, double y, double z)
	{
		if (x != 0)
		{
			glRotated(x, 1, 0, 0);
		}
		if (y != 0)
		{
			glRotated(y, 0, 1, 0);
		}
		if (z != 0)
		{
			glRotated(z, 0, 0, 1);
		}
	}
	
	/**
	 * Scales everything rendered on the scene the specified float
	 * amount.
	 * 
	 * @param x The amount to scale the width.
	 * @param y The amount to scale the height.
	 * @param z The amount to scale the depth.
	 */
	public static void scalef(float x, float y, float z)
	{
		glScalef(x, y, z);
		
		scale[0] *= x;
		scale[1] *= y;
		scale[2] *= z;
	}
	
	/**
	 * Scales everything rendered on the scene the specified double
	 * amount.
	 * 
	 * @param x The amount to scale the width.
	 * @param y The amount to scale the height.
	 * @param z The amount to scale the depth.
	 */
	public static void scaled(double x, double y, double z)
	{
		glScaled(x, y, z);
		
		scale[0] *= x;
		scale[1] *= y;
		scale[2] *= z;
	}
	
	/**
	 * Translates the scene rendered on the scene to the specified float
	 * position.
	 * 
	 * @param x The amount to translate the x.
	 * @param y The amount to translate the y.
	 * @param z The amount to translate the z.
	 */
	public static void translatef(float x, float y, float z)
	{
		glTranslatef(x, y, z);
		
		offsets[0] += x;
		offsets[1] += y;
		offsets[2] += z;
	}
	
	/**
	 * Scales the scene rendered on the scene to the specified double
	 * position.
	 * 
	 * @param x The amount to translate the x.
	 * @param y The amount to translate the y.
	 * @param z The amount to translate the z.
	 */
	public static void translated(double x, double y, double z)
	{
		glTranslated(x, y, z);
		
		offsets[0] += x;
		offsets[1] += y;
		offsets[2] += z;
	}
	
	/**
	 * Returns the amount translated by the time that this method
	 * is called.
	 * 
	 * @return The amount translated so far.
	 */
	public static double[] getAmountTranslated()
	{
		return offsets;
	}
	
	/**
	 * Returns the amount scaled by the time that this method
	 * is called.
	 * 
	 * @return The amount scaled so far.
	 */
	public static double[] getAmountScaled()
	{
		return scale;
	}
	
//	public static void renderQuads(LightBuffer verticesBuffer, LightBuffer texturesBuffer, ImageMap imageMap, int start, int amount, RenderTask task)
//	{
//		renderBuffers(verticesBuffer, texturesBuffer, imageMap, start * 4, amount * 4, GL_QUADS, task);
//	}
	
//	public static void renderQuads(Buffer verticesBuffer, Buffer texturesBuffer, ImageMap imageMap, int start, int amount)
//	{
//		renderBuffers(verticesBuffer, texturesBuffer, imageMap, start * 4, amount * 4, GL_QUADS);
//	}
	
	public static void renderQuads(Buffer verticesBuffer, int start, int amount)
	{
		renderBuffers(verticesBuffer, start * 4, amount * 4, QUADS);
	}
	
	public static void renderQuads(Buffer verticesBuffer, Buffer texturesBuffer, Buffer normalsBuffer, ImageMap imageMap, int start, int amount)
	{
		renderBuffers(verticesBuffer, texturesBuffer, normalsBuffer, imageMap, start * 4, amount * 4, QUADS);
	}
	
	public static void renderQuads(Buffer verticesBuffer, Buffer texturesBuffer, ImageMap imageMap, int start, int amount, Task task)
	{
		renderBuffers(verticesBuffer, texturesBuffer, imageMap, start * 4, amount * 4, QUADS, task);
	}
	
	public static void renderQuads(Buffer verticesBuffer, Buffer texturesBuffer, ImageMap imageMap, int start, int amount)
	{
		renderBuffers(verticesBuffer, texturesBuffer, imageMap, 2, 2, start * 4, amount * 4, QUADS);
	}
	
	public static void renderQuad(Buffer verticesBuffer, Buffer texturesBuffer, ImageMap imageMap)
	{
		renderBuffers(verticesBuffer, texturesBuffer, imageMap, 2, 2, 0, 4, QUADS);
	}
	
	private static void renderBuffers(Buffer verticesBuffer, Buffer texturesBuffer, ImageMap imageMap, int start, int amount, int type, Task task)
	{
		beginTextureDraw(texturesBuffer, 2);
		beginVertexDraw (verticesBuffer, 2);
		
		if (imageMap != null)
		{
			imageMap.bind();
		}
		
		for (int i = 0; i < amount; i += 4)
		{
			if (!task.run(i / 4))
			{
				continue;
			}
			
			glDrawArrays(type, start + i, 4);
		}
		
		endVertexDraw();
		endTextureDraw();
	}
	
	private static void renderBuffers(Buffer verticesBuffer, Buffer texturesBuffer, ImageMap imageMap, int start, int amount, int type)
	{
		beginTextureDraw(texturesBuffer, 2);
		beginVertexDraw (verticesBuffer, 2);
		
		if (imageMap != null)
		{
			imageMap.bind();
		}
		
		glDrawArrays(type, start, amount);
		
		endVertexDraw();
		endTextureDraw();
	}
	
	private static void renderBuffers(Buffer verticesBuffer, int start, int amount, int type)
	{
		beginVertexDraw (verticesBuffer, 2);
		
		glDrawArrays(type, start, amount);
		
		endVertexDraw();
	}
	
	private static void renderBuffers(Buffer verticesBuffer, Buffer texturesBuffer, Buffer normalsBuffer, ImageMap imageMap, int start, int amount, int type)
	{
		beginTextureDraw(texturesBuffer, 2);
		beginVertexDraw (verticesBuffer, 2);
		beginNormalDraw(normalsBuffer);
		
		if (imageMap != null)
		{
			imageMap.bind();
		}
		
		glDrawArrays(type, start, amount);
		
		endNormalDraw();
		endVertexDraw();
		endTextureDraw();
	}
	
//	private static void renderBuffers(LightBuffer verticesBuffer, LightBuffer texturesBuffer, ImageMap imageMap, int start, int amount, int type, RenderTask task)
//	{
//		beginTextureDraw(texturesBuffer, 2);
//		beginVertexDraw (verticesBuffer, 2);
//		
//		if (imageMap != null)
//		{
//			imageMap.bind();
//		}
//		
//		for (int i = 0; i < amount; i += 4)
//		{
//			if (!task.run(i / 4))
//			{
//				continue;
//			}
//			
//			glDrawArrays(type, start + i, 4);
//		}
//		
//		endVertexDraw();
//		endTextureDraw();
//	}
	
	private static void renderBuffers(Buffer verticesBuffer, Buffer texturesBuffer,ImageMap imageMap, int textureSize, int vertexSize, int start, int amount, int type)
	{
		beginTextureDraw(texturesBuffer, textureSize);
		beginVertexDraw (verticesBuffer, vertexSize);
		
		if (imageMap != null)
		{
			imageMap.bind();
		}
		
		glDrawArrays(type, start, amount);
		
		endVertexDraw();
		endTextureDraw();
	}
	
	public static void beginVertexDraw(Buffer buffer, int vertexSize)
	{
		glEnableClientState(GL_VERTEX_ARRAY);
		
		if (buffer instanceof LightBuffer)
		{
			glVertexPointer(vertexSize, 0, (FloatBuffer)buffer.getBuffer());
		}
		else if (buffer instanceof HeavyBuffer)
		{
			glVertexPointer(vertexSize, 0, (DoubleBuffer)buffer.getBuffer());
		}
	}
	
	public static void begindVertexDraw(int id, int vertexSize)
	{
		glEnableClientState(GL_VERTEX_ARRAY);
		
		glBindBuffer(GL_ARRAY_BUFFER, id);
		glVertexPointer(vertexSize, GL_FLOAT, 0, 0);
	}
	
	public static void endVertexDraw()
	{
		glDisableClientState(GL_VERTEX_ARRAY);
	}
	
	public static void beginNormalDraw(FloatBuffer buffer)
	{
		glEnableClientState(GL_NORMAL_ARRAY);
		
		glNormalPointer(0, buffer);
	}
	
	public static void beginNormalDraw(Buffer buffer)
	{
		glEnableClientState(GL_NORMAL_ARRAY);
		
		if (buffer instanceof LightBuffer)
		{
			glNormalPointer(0, (FloatBuffer)buffer.getBuffer());
		}
		else if (buffer instanceof HeavyBuffer)
		{
			glNormalPointer(0, (DoubleBuffer)buffer.getBuffer());
		}
	}
	
	public static void begindNormalDraw(int id)
	{
		glEnableClientState(GL_NORMAL_ARRAY);
		
		glBindBuffer(GL_ARRAY_BUFFER, id);
		glNormalPointer(GL_FLOAT, 0, 0);
	}
	
	public static void endNormalDraw()
	{
		glDisableClientState(GL_NORMAL_ARRAY);
	}
	
	public static void beginColorDraw(Buffer buffer)
	{
		glEnableClientState(GL_COLOR_ARRAY);
		
		if (buffer instanceof LightBuffer)
		{
			glColorPointer(4, 0, (FloatBuffer)buffer.getBuffer());
		}
		else if (buffer instanceof HeavyBuffer)
		{
			glColorPointer(4, 0, (DoubleBuffer)buffer.getBuffer());
		}
	}
	
	public static void begindColorDraw(int id)
	{
		glEnableClientState(GL_COLOR_ARRAY);
		
		glBindBuffer(GL_ARRAY_BUFFER, id);
		glColorPointer(4, GL_FLOAT, 0, 0);
	}
	
	public static void endColorDraw()
	{
		glDisableClientState(GL_COLOR_ARRAY);
	}
	
	public static void beginTextureDraw(Buffer buffer, int vertexSize)
	{
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
		
		glEnableClientState(GL_TEXTURE_COORD_ARRAY);
		
		if (buffer instanceof LightBuffer)
		{
			glTexCoordPointer(vertexSize, 0, (FloatBuffer)buffer.getBuffer());
		}
		else if (buffer instanceof HeavyBuffer)
		{
			glTexCoordPointer(vertexSize, 0, (DoubleBuffer)buffer.getBuffer());
		}
	}
	
	public static void begindTextureDraw(int id, int vertexSize)
	{
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
		
		glEnableClientState(GL_TEXTURE_COORD_ARRAY);
		
		glBindBuffer(GL_ARRAY_BUFFER, id);
		glTexCoordPointer(vertexSize, GL_FLOAT, 0, 0);
	}
	
	public static void endTextureDraw()
	{
		glDisableClientState(GL_TEXTURE_COORD_ARRAY);
		
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
	}
	
	public static void initBasicView()
	{
		glEnable(GL_TEXTURE_2D);
		
//		glBlendFunc(GL_ONE, GL_ONE);
//		GL14.glBlendEquation(GL14.GL_MAX);
		
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
		
		glTexEnvi(GL_TEXTURE_ENV, GL_TEXTURE_ENV_MODE, GL_MODULATE);
		
		glEnable(GL_BLEND);
		glBlendFunc(GL_ONE, GL_ONE_MINUS_SRC_ALPHA);
//		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
//		glBlendFunc(GL_DST_COLOR, GL_ONE_MINUS_SRC_ALPHA);
//		glBlendEquation(GL_ADD);
//		GL14.glBlendEquation(GL14.GL_FUNC_ADD);
		
		glEnable(GL_ALPHA_TEST);
		glAlphaFunc(GL_GREATER, 0.1f); 
		
		glEnable(GL_DEPTH_TEST);
		
//		glEnable(GL_CULL_FACE);
//		glCullFace(GL_BACK);
		
//		glShadeModel(GL_SMOOTH); // Enable Smooth Shading
		glClearColor(0.0f, 0.3f, 0.6f, 0.0f); // Blue Background
		
		glViewport(0, 0, Display.getWidth(), Display.getHeight());
		glMatrixMode(GL_PROJECTION); // Select The Projection Matrix
		glLoadIdentity(); // Reset The Projection Matrix

		// Calculate The Aspect Ratio Of The Window
		glOrtho(0, Display.getWidth(), 0, Display.getHeight(), -99999, 99999);
		glMatrixMode(GL_MODELVIEW); // Select The Modelview Matrix

		// Really Nice Perspective Calculations
//		glHint(GL_PERSPECTIVE_CORRECTION_HINT, GL_NICEST);
		
//		initLighting();
		
		flipped = false;
	}
	
	public static void loadIdentity()
	{
		glLoadIdentity();
	}
	
	public static void resetBasicView()
	{
		glViewport(0, 0, Display.getWidth(), Display.getHeight());
		glMatrixMode(GL_PROJECTION); // Select The Projection Matrix
		glLoadIdentity(); // Reset The Projection Matrix

		// Calculate The Aspect Ratio Of The Window
		glOrtho(0, Display.getWidth(), 0, Display.getHeight(), -99999, 99999);
		glMatrixMode(GL_MODELVIEW); // Select The Modelview Matrix
		
		flipped = false;
	}
	
	public static void flipView()
	{
		glViewport(0, 0, Display.getWidth(), Display.getHeight());
		glMatrixMode(GL_PROJECTION); // Select The Projection Matrix
		glLoadIdentity(); // Reset The Projection Matrix
		
		if (flipped)
		{
			// Calculate The Aspect Ratio Of The Window
			glOrtho(0, Display.getWidth(), 0, Display.getHeight(), -99999, 99999);
		}
		else
		{
			// Calculate The Aspect Ratio Of The Window
			glOrtho(0, Display.getWidth(), Display.getHeight(), 0, -99999, 99999);
		}
		
		glMatrixMode(GL_MODELVIEW); // Select The Modelview Matrix
		
		flipped = !flipped;
	}
	
	public static void createFrame(int width, int height, String title, Canvas drawCanvas)
	{
//		initGL();
		
		boolean hasCanvas = drawCanvas != null;
		
		try
		{
			Base.includeNatives = true;
			
			if (hasCanvas)
			{
				Display.setParent(drawCanvas);
			}
			
			Display.setDisplayMode(new DisplayMode(width, height));
			
			Display.setTitle(title);
			
			if (!hasCanvas)
			{
				Display.setResizable(true);
			}
			
			Display.setFullscreen(false);
			
			Display.create();
		}
		catch (LWJGLException e)
		{
			e.printStackTrace();
		}
		
//		Display.setVSyncEnabled(true);
	}
	
	public static void createFrame(int width, int height, Canvas drawCanvas)
	{
		createFrame(width, height, "", drawCanvas);
	}
	
	public static float[] addRectColorArrayif(int r, int g, int b, int a, int offset, float[] array)
	{
		if (array == null)
		{
			array = new float[4 * 4];
			
			offset = 0;
		}
		
		int index = 0;
		
		array[offset + index ++] = r / 255f;
		array[offset + index ++] = g / 255f;
		array[offset + index ++] = b / 255f;
		array[offset + index ++] = a / 255f;
		
		array[offset + index ++] = r / 255f;
		array[offset + index ++] = g / 255f;
		array[offset + index ++] = b / 255f;
		array[offset + index ++] = a / 255f;
		
		array[offset + index ++] = r / 255f;
		array[offset + index ++] = g / 255f;
		array[offset + index ++] = b / 255f;
		array[offset + index ++] = a / 255f;
		
		array[offset + index ++] = r / 255f;
		array[offset + index ++] = g / 255f;
		array[offset + index ++] = b / 255f;
		array[offset + index ++] = a / 255f;
		
		return array;
	}
	
	public static double[] addRectColorArrayid(int r, int g, int b, int a, int offset, double[] array)
	{
		if (array == null)
		{
			array = new double[4 * 4];
			
			offset = 0;
		}
		
		int index = 0;
		
		array[offset + index ++] = r / 255f;
		array[offset + index ++] = g / 255f;
		array[offset + index ++] = b / 255f;
		array[offset + index ++] = a / 255f;
		
		array[offset + index ++] = r / 255f;
		array[offset + index ++] = g / 255f;
		array[offset + index ++] = b / 255f;
		array[offset + index ++] = a / 255f;
		
		array[offset + index ++] = r / 255f;
		array[offset + index ++] = g / 255f;
		array[offset + index ++] = b / 255f;
		array[offset + index ++] = a / 255f;
		
		array[offset + index ++] = r / 255f;
		array[offset + index ++] = g / 255f;
		array[offset + index ++] = b / 255f;
		array[offset + index ++] = a / 255f;
		
		return array;
	}
	
	public static float[] addRectColorArrayf(float r, float g, float b, float a, int offset, float[] array)
	{
		if (array == null)
		{
			array = new float[4 * 4];
			
			offset = 0;
		}
		
		int index = 0;
		
		array[offset + index ++] = r;
		array[offset + index ++] = g;
		array[offset + index ++] = b;
		array[offset + index ++] = a;
		
		array[offset + index ++] = r;
		array[offset + index ++] = g;
		array[offset + index ++] = b;
		array[offset + index ++] = a;
		
		array[offset + index ++] = r;
		array[offset + index ++] = g;
		array[offset + index ++] = b;
		array[offset + index ++] = a;
		
		array[offset + index ++] = r;
		array[offset + index ++] = g;
		array[offset + index ++] = b;
		array[offset + index ++] = a;
		
		return array;
	}
	
	public static float[] addRectVertexArrayf(float x, float y, float z, float width, float height, int offset, float[] array)
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
		
		array[offset + index ++] = x + width;
		array[offset + index ++] = y;
		array[offset + index ++] = z;
		
		array[offset + index ++] = x + width;
		array[offset + index ++] = y + height;
		array[offset + index ++] = z;
		
		array[offset + index ++] = x;
		array[offset + index ++] = y + height;
		array[offset + index ++] = z;
		
		return array;
	}
	
	public static float[] addRectVertexArrayf(float x, float y, float width, float height, int offset, float[] array)
	{
		if (array == null)
		{
			array = new float[2 * 4];
			
			offset = 0;
		}
		
		int index = 0;
		
		array[offset + index ++] = x;
		array[offset + index ++] = y;
		
		array[offset + index ++] = x + width;
		array[offset + index ++] = y;
		
		array[offset + index ++] = x + width;
		array[offset + index ++] = y + height;
		
		array[offset + index ++] = x;
		array[offset + index ++] = y + height;
		
		return array;
	}
	
	public static float[] addSquareVertexArrayf(float x, float y, float z, float size, int offset, float[] array)
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
		
		array[offset + index ++] = x + size;
		array[offset + index ++] = y;
		array[offset + index ++] = z;
		
		array[offset + index ++] = x + size;
		array[offset + index ++] = y + size;
		array[offset + index ++] = z;
		
		array[offset + index ++] = x;
		array[offset + index ++] = y + size;
		array[offset + index ++] = z;
		
		return array;
	}
	
	public static float[] addSquareVertexArrayf(float x, float y, int size, int offset, float[] array)
	{
		if (array == null)
		{
			array = new float[3 * 4];
			
			offset = 0;
		}
		
		int index = 0;
		
		array[offset + index ++] = x;
		array[offset + index ++] = y;
		
		array[offset + index ++] = x + size;
		array[offset + index ++] = y;
		
		array[offset + index ++] = x + size;
		array[offset + index ++] = y + size;
		
		array[offset + index ++] = x;
		array[offset + index ++] = y + size;
		
		return array;
	}
	
	public static float[] addRectTextureArrayf(SpriteSheet spriteSheet, int x, int y, int z, int width, int height, int offset, float[] array)
	{
		if (array == null)
		{
			array = new float[3 * 4];
			
			offset = 0;
		}
		
		int index = 0;
		
		float offsets[] = spriteSheet.getImageOffsetsf(x, y, width, height);
		
		array[offset + index ++] = offsets[0];
		array[offset + index ++] = offsets[1];
		array[offset + index ++] = 0;
		
		array[offset + index ++] = offsets[2];
		array[offset + index ++] = offsets[1];
		array[offset + index ++] = 0;
		
		array[offset + index ++] = offsets[2];
		array[offset + index ++] = offsets[3];
		array[offset + index ++] = 0;

		array[offset + index ++] = offsets[0];
		array[offset + index ++] = offsets[3];
		array[offset + index ++] = 0;
		
		return array;
	}
	
	public static float[] addRectTextureArrayf(SpriteSheet spriteSheet, int x, int y, int width, int height, int offset, float[] array)
	{
		if (array == null)
		{
			array = new float[2 * 4];
			
			offset = 0;
		}
		
		int index = 0;
		
		float offsets[] = spriteSheet.getImageOffsetsf(x, y, width, height);
		
		array[offset + index ++] = offsets[0];
		array[offset + index ++] = offsets[1];
		
		array[offset + index ++] = offsets[2];
		array[offset + index ++] = offsets[1];
		
		array[offset + index ++] = offsets[2];
		array[offset + index ++] = offsets[3];

		array[offset + index ++] = offsets[0];
		array[offset + index ++] = offsets[3];
		
		return array;
	}
	
	public static float[] addRectTextureArrayf(Texture texture, int offset, float[] array)
	{
		if (array == null)
		{
			array = new float[2 * 4];
			
			offset = 0;
		}
		
		int index = 0;
		
		float offsets[] = texture.getImageOffsetsf();
		
		array[offset + index ++] = offsets[0];
		array[offset + index ++] = offsets[1];
		
		array[offset + index ++] = offsets[2];
		array[offset + index ++] = offsets[1];
		
		array[offset + index ++] = offsets[2];
		array[offset + index ++] = offsets[3];

		array[offset + index ++] = offsets[0];
		array[offset + index ++] = offsets[3];
		
		return array;
	}
	
	public static float[] addRectTextureArrayf(float offsets[], int offset, float[] array)
	{
		return addRectTextureArrayf(offsets, offset, array, false);
	}
	
	public static float[] addRectTextureArrayf(float offsets[], int rx, int ry, int offset, float[] array)
	{
		return addRectTextureArrayf(offsets, rx, ry, offset, array, false);
	}
	
	public static float[] addRectTextureArrayf(float offsets[], int offset, float[] array, boolean mirror)
	{
		if (array == null)
		{
			array = new float[2 * 4];
			
			offset = 0;
		}
		
		int index = 0;
		
		if (mirror)
		{
			array[offset + index ++] = offsets[2];
			array[offset + index ++] = offsets[1];
			
			array[offset + index ++] = offsets[0];
			array[offset + index ++] = offsets[1];
			
			array[offset + index ++] = offsets[0];
			array[offset + index ++] = offsets[3];

			array[offset + index ++] = offsets[2];
			array[offset + index ++] = offsets[3];
		}
		else
		{
			array[offset + index ++] = offsets[0];
			array[offset + index ++] = offsets[1];
			
			array[offset + index ++] = offsets[2];
			array[offset + index ++] = offsets[1];
			
			array[offset + index ++] = offsets[2];
			array[offset + index ++] = offsets[3];
	
			array[offset + index ++] = offsets[0];
			array[offset + index ++] = offsets[3];
		}
		
		return array;
	}
	
	public static float[] addRectTextureArrayf(float offsets[], int rx, int ry, int offset, float[] array, boolean mirror)
	{
		if (array == null)
		{
			array = new float[2 * 4];
			
			offset = 0;
		}
		
		int index = 0;
		
		if (mirror)
		{
			array[offset + index ++] = rx * offsets[2];
			array[offset + index ++] = ry * offsets[1];
			
			array[offset + index ++] = rx * offsets[0];
			array[offset + index ++] = ry * offsets[1];
			
			array[offset + index ++] = rx * offsets[0];
			array[offset + index ++] = ry * offsets[3];

			array[offset + index ++] = rx * offsets[2];
			array[offset + index ++] = ry * offsets[3];
		}
		else
		{
			array[offset + index ++] = rx * offsets[0];
			array[offset + index ++] = ry * offsets[1];
			
			array[offset + index ++] = rx * offsets[2];
			array[offset + index ++] = ry * offsets[1];
			
			array[offset + index ++] = rx * offsets[2];
			array[offset + index ++] = ry * offsets[3];
	
			array[offset + index ++] = rx * offsets[0];
			array[offset + index ++] = ry * offsets[3];
		}
		
		return array;
	}
	
	public static double[] addRectVertexArrayd(double x, double y, double width, double height, int offset, double[] array)
	{
		if (array == null)
		{
			array = new double[2 * 4];
			
			offset = 0;
		}
		
		int index = 0;
		
		array[offset + index ++] = x;
		array[offset + index ++] = y;
		
		array[offset + index ++] = x + width;
		array[offset + index ++] = y;
		
		array[offset + index ++] = x + width;
		array[offset + index ++] = y + height;
		
		array[offset + index ++] = x;
		array[offset + index ++] = y + height;
		
		return array;
	}
	
	public static double[] addRectVertexArrayd(double x, double y, double z, double width, double height, int offset, double[] array)
	{
		if (array == null)
		{
			array = new double[3 * 4];
			
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
	
	public static double[] addSquareVertexArrayd(double x, double y, double z, double size, int offset, double[] array)
	{
		if (array == null)
		{
			array = new double[3 * 4];
			
			offset = 0;
		}
		
		int index = 0;
		
		array[offset + index ++] = x;
		array[offset + index ++] = y;
		array[offset + index ++] = z;
		
		array[offset + index ++] = x + size;
		array[offset + index ++] = y;
		array[offset + index ++] = z;
		
		array[offset + index ++] = x + size;
		array[offset + index ++] = y + size;
		array[offset + index ++] = z;
		
		array[offset + index ++] = x;
		array[offset + index ++] = y + size;
		array[offset + index ++] = z;
		
		return array;
	}
	
	public static float[] addRectNormalArrayf(int z, int offset, float[] array)
	{
		if (array == null)
		{
			array = new float[3 * 4];
			
			offset = 0;
		}
		
		int index = 0;
		
		array[offset + index ++] = 0;
		array[offset + index ++] = 0;
		array[offset + index ++] = z;
		
		array[offset + index ++] = 0;
		array[offset + index ++] = 0;
		array[offset + index ++] = z;
		
		array[offset + index ++] = 0;
		array[offset + index ++] = 0;
		array[offset + index ++] = z;
		
		array[offset + index ++] = 0;
		array[offset + index ++] = 0;
		array[offset + index ++] = z;
		
		return array;
	}
	
	public static double[] addSquareVertexArrayd(double x, double y, int size, int offset, double[] array)
	{
		if (array == null)
		{
			array = new double[3 * 4];
			
			offset = 0;
		}
		
		int index = 0;
		
		array[offset + index ++] = x;
		array[offset + index ++] = y;
		
		array[offset + index ++] = x + size;
		array[offset + index ++] = y;
		
		array[offset + index ++] = x + size;
		array[offset + index ++] = y + size;
		
		array[offset + index ++] = x;
		array[offset + index ++] = y + size;
		
		return array;
	}
	
	public static double[] addRectTextureArrayd(SpriteSheet spriteSheet, int x, int y, int z, int width, int height, int offset, double[] array)
	{
		if (array == null)
		{
			array = new double[3 * 4];
			
			offset = 0;
		}
		
		int index = 0;
		
		double offsets[] = spriteSheet.getImageOffsetsd(x, y, width, height);
		
		array[offset + index ++] = offsets[0];
		array[offset + index ++] = offsets[1];
		array[offset + index ++] = 0;
		
		array[offset + index ++] = offsets[2];
		array[offset + index ++] = offsets[1];
		array[offset + index ++] = 0;
		
		array[offset + index ++] = offsets[2];
		array[offset + index ++] = offsets[3];
		array[offset + index ++] = 0;

		array[offset + index ++] = offsets[0];
		array[offset + index ++] = offsets[3];
		array[offset + index ++] = 0;
		
		return array;
	}
	
	public static double[] addRectTextureArrayd(SpriteSheet spriteSheet, int x, int y, int width, int height, int offset, double[] array)
	{
		if (array == null)
		{
			array = new double[2 * 4];
			
			offset = 0;
		}
		
		int index = 0;
		
		double offsets[] = spriteSheet.getImageOffsetsd(x, y, width, height);
		
		array[offset + index ++] = offsets[0];
		array[offset + index ++] = offsets[1];
		
		array[offset + index ++] = offsets[2];
		array[offset + index ++] = offsets[1];
		
		array[offset + index ++] = offsets[2];
		array[offset + index ++] = offsets[3];

		array[offset + index ++] = offsets[0];
		array[offset + index ++] = offsets[3];
		
		return array;
	}
	
	public static double[] addRectTextureArrayd(double offsets[], int offset, double[] array)
	{
		if (array == null)
		{
			array = new double[2 * 4];
			
			offset = 0;
		}
		
		int index = 0;
		
		array[offset + index ++] = offsets[0];
		array[offset + index ++] = offsets[1];
		
		array[offset + index ++] = offsets[2];
		array[offset + index ++] = offsets[1];
		
		array[offset + index ++] = offsets[2];
		array[offset + index ++] = offsets[3];

		array[offset + index ++] = offsets[0];
		array[offset + index ++] = offsets[3];
		
		return array;
	}
	
	public static double[] addRectTextureArrayd(float offsets[], int offset, double[] array)
	{
		if (array == null)
		{
			array = new double[2 * 4];
			
			offset = 0;
		}
		
		int index = 0;
		
		array[offset + index ++] = offsets[0];
		array[offset + index ++] = offsets[1];
		
		array[offset + index ++] = offsets[2];
		array[offset + index ++] = offsets[1];
		
		array[offset + index ++] = offsets[2];
		array[offset + index ++] = offsets[3];

		array[offset + index ++] = offsets[0];
		array[offset + index ++] = offsets[3];
		
		return array;
	}
}