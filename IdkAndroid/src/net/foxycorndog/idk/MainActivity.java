package net.foxycorndog.idk;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.app.Activity;

import net.foxycorndog.idk.tiles.Tile;
import net.foxycorndog.jdoogl.activity.AdvancedActivity;
import net.foxycorndog.jdoogl.components.Frame;

public class MainActivity extends AdvancedActivity
{
	private Activity   activity;
	
	private static Idk idk;
	
	public void onCreate()
	{
		this.activity = this;
		
		idk           = new Idk(this);
	}
	
	public void render(GL10 gl)
	{
		idk.render();
	}
	
	public void loop()
	{
		idk.loop();
	}
	
	public void onSurfaceChanged(GL10 gl)
	{
		Frame.init("ASDF", getGameRenderer(), activity);
		
		idk.start();
	}

	public void onSurfaceCreated(GL10 gl, EGLConfig config)
	{
		gl.glClearColor(0.5f, 0.5f, 0.5f, 1.0f);
	}
}