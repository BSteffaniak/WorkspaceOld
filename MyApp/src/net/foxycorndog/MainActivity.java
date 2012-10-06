package net.foxycorndog;

import android.R;
import android.app.Activity;
import android.os.Bundle;

/**
 * This is the main class that starts the whole android
 * application.
 */
public class MainActivity extends Activity
{
// 	private GLSurfaceView mGLView;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
	{
        super.onCreate(savedInstanceState);
        setContentView(R.layout_main);

//		AwesomeGameRenderer renderer = new AwesomeGameRenderer();

//		mGLView = new GLSurfaceView(this);
//		mGLView.setRenderer(renderer);
//
//		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
//
//		setContentView(mGLView);
    }

//	public void onPause()
//	{
//		super.onPause();
//
//		mGLView.onPause();
//	}
//
//	public void onResume()
//	{
//		super.onResume();
//		
//		mGLView.onResume();
//	}
}
