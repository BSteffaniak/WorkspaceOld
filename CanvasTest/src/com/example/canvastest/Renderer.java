package com.example.canvastest;

import java.util.ArrayList;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.graphics.Paint;
import android.graphics.Path;
import android.opengl.GLSurfaceView;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

public class Renderer implements GLSurfaceView.Renderer
{
	private ArrayList<Path> _graphics;
	private Paint     mPaint;
	
	public Renderer(ArrayList g, Paint p)
	{
		this._graphics = g;
		this.mPaint    = p;
	}
	
	public void onDrawFrame(GL10 gl)
	{

	  for (Path path : _graphics) {
	    //canvas.drawPoint(graphic.x, graphic.y, mPaint);
	    canvas.drawPath(path, mPaint);
	  }
	}

	public void onSurfaceChanged(GL10 gl, int width, int height)
	{
		
	}

	public void onSurfaceCreated(GL10 gl, EGLConfig config)
	{
		
	}
}