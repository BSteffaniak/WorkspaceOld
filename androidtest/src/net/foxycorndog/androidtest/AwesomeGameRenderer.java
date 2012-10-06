package net.foxycorndog.androidtest;

import android.opengl.GLSurfaceView;
import android.opengl.GLES10;
import android.opengl.GLES11;
import android.opengl.GLES20;
import javax.microedition.khronos.opengles.GL10;
//import javax.microedition.khronos.opengles.GL;
import javax.microedition.khronos.egl.EGLConfig;
import java.nio.FloatBuffer;
import java.nio.ByteOrder;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import android.util.*;
import java.nio.*;

//import android.opengl;

public class AwesomeGameRenderer implements GLSurfaceView.Renderer
{
	IntBuffer   textures;
	
	ShortBuffer in;
	
	FloatBuffer tr;

	public void onSurfaceCreated(GL10 gl, EGLConfig config)
	{
		GLES20.glClearColor(0.5f, 0.5f, 0.5f, 1.0f);
	}
	
	public void onSurfaceChanged(GL10 gl, int width, int height)
	{
		GLES20.glViewport(0, 0, width, height);
		
		gl.glOrthof(0, width, 0, height, -100, 100);
		
		ByteBuffer bb = ByteBuffer.allocateDirect(4 * 2 * 4 * 2);
		
		bb.order(ByteOrder.nativeOrder());
		
		tr = bb.asFloatBuffer();
		
//		tr = FloatBuffer.allocate(4 * 2 * 2);
		
		float x = 20, y = 20, wid = 100, hei = 100;
		
		tr.put(new float[]
		{
			x,       y,      
			x,       y + hei,
			x + wid, y + hei,
			x + wid, y,      
			
			x + 150,       y,      
			x + 150,       y + hei,
			x + 150 + wid, y + hei,
			x + 150 + wid, y
		});
		
		tr.rewind();
		
		
		
//		ByteBuffer b = ByteBuffer.allocateDirect(3 * 2 * 4 * 2);
//		
//		b.order(ByteOrder.nativeOrder());
//		
//		in = b.asShortBuffer();
		
		in = ShortBuffer.allocate(3 * 2 * 2);
		
		in.put(new short[]
		{
			0, 1, 2,
			0, 2, 3,
			
			0 + 4, 1 + 4, 2 + 4,
			0 + 4, 2 + 4, 3 + 4
		});
		
		in.rewind();
		
//		ByteBuffer bytes = ByteBuffer.allocate(data.length);
//		bytes.put(data, 0, data.length);
//		bytes.rewind();
//		
//		textures = IntBuffer.allocate(1);
//		gl.glGenTextures(1, textures);
//		gl.glBindTexture(gl.GL_TEXTURE_2D, textures.get(0));
//		
//		gl.glTexParameterf(gl.GL_TEXTURE_2D, gl.GL_TEXTURE_MIN_FILTER, gl.GL_NEAREST);
//		gl.glTexParameterf(gl.GL_TEXTURE_2D, gl.GL_TEXTURE_MIN_FILTER, gl.GL_NEAREST);
//		
//		gl.glTexImage2D(gl.GL_TEXTURE_2D, 0, gl.GL_RGB, width, height, 0, gl.GL_RGB, gl.GL_UNSIGNED_BYTE, bytes);
	}
	
	public void onDrawFrame(GL10 gl)
	{
		GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);
		
		gl.glPushMatrix();
		{
			gl.glEnableClientState(gl.GL_VERTEX_ARRAY);
			
			gl.glVertexPointer(2, gl.GL_FLOAT, 0, tr);
//			gl.glDrawArrays(gl.GL_TRIANGLE_STRIP, 0, 4);
			gl.glDrawElements(gl.GL_TRIANGLES, 6 * 2, gl.GL_UNSIGNED_SHORT, in);
			
			gl.glDisableClientState(gl.GL_VERTEX_ARRAY);
		}
		gl.glPopMatrix();
	}
}
