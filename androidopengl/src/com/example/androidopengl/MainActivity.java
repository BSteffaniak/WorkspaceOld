package com.example.androidopengl;

import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.Window;

public class MainActivity extends Activity implements OnTouchListener, OnClickListener
{
	GLSurfaceView mGLView;
	
    @Override
    public void onCreate(Bundle savedInstanceState)
	{
        super.onCreate(savedInstanceState);
        
        AwesomeGameRenderer renderer = new AwesomeGameRenderer();
        
        mGLView = new GLSurfaceView(this);
        mGLView.setRenderer(renderer);
//        mGLView.setOnTouchListener(this);
        mGLView.setOnClickListener(this);
        
        renderer.setActivity(this);
		
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        
        setContentView(mGLView);
    }

	public boolean onTouch(View v, MotionEvent event)
	{
		System.out.println("touched");
		
		return false;
	}

	public void onClick(View v)
	{
		System.out.println("clicked");
	}
}
