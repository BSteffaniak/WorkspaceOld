package net.foxycorndog;

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
import android.util.*;

//import android.opengl;

public class AwesomeGameRenderer implements GLSurfaceView.Renderer
{
	FloatBuffer tr;

	public void onSurfaceCreated(GL10 gl, EGLConfig config)
	{
		GLES20.glClearColor(0.5f, 0.5f, 0.5f, 1.0f);
	}
	
	public void onSurfaceChanged(GL10 gl, int width, int height)
	{
		GLES20.glViewport(0, 0, width, height);
		
		gl.glOrthof(0, width, 0, height, -100, 100);
		
		tr = FloatBuffer.allocate(4 * 2);
		
		ByteBuffer bb = ByteBuffer.allocateDirect(4 * 2 * 4);
		
		bb.order(ByteOrder.nativeOrder());
		
		tr = bb.asFloatBuffer();
		
		float x = 20, y = 20, wid = 100, hei = 100;
		
		tr.put(new float[]
		{
			x, x,
			x, y + hei,
			x + wid, y,
			x + wid, y + hei
		});
		
		tr.rewind();
	}
	
	public void onDrawFrame(GL10 gl)
	{
		GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);
		
		gl.glPushMatrix();
		{
			gl.glEnableClientState(gl.GL_VERTEX_ARRAY);
			
			gl.glVertexPointer(2, gl.GL_FLOAT, 0, tr);
			gl.glDrawArrays(gl.GL_TRIANGLE_STRIP, 0, 4);
			
			gl.glDisableClientState(gl.GL_VERTEX_ARRAY);
		}
		gl.glPopMatrix();
	}
}
