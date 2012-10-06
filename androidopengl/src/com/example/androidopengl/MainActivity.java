package com.example.androidopengl;

import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.Window;

public class MainActivity extends Activity
{
	GLSurfaceView mGLView;
	
    @Override
    public void onCreate(Bundle savedInstanceState)
	{
        super.onCreate(savedInstanceState);
        
        AwesomeGameRenderer renderer = new AwesomeGameRenderer();
        
        mGLView = new GLSurfaceView(this);
        mGLView.setRenderer(renderer);
        
        renderer.setActivity(this);
		
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        
        setContentView(mGLView);
    }
}
