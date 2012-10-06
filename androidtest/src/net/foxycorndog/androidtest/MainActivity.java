package net.foxycorndog.androidtest;

import android.R;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.Window;
import android.view.*;
import android.view.View.OnTouchListener;
import android.util.*;

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
        
		mGLView.setOnTouchListener(new TouchListener());
		
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        
        setContentView(mGLView);
    }
	
	private class TouchListener implements OnTouchListener
	{
		private float oldX, oldY;
		
		public TouchListener()
		{
			oldX = -1;
			oldY = -1;
		}
		
		public boolean onTouch(View v, MotionEvent e)
		{
			if (oldX == e.getX() && oldY == e.getY())
			{
				return false;
			}
			
			Log.v("location", "(" + e.getX() + ", " + e.getY() + ")");
			
			oldX = e.getX();
			oldY = e.getY();
			
			return true;
		}
	}
}
