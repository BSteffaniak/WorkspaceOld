package com.example.canvastest;

import java.util.ArrayList;

import android.os.Bundle;
import android.app.Activity;
import android.graphics.Paint;
import android.graphics.Path;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

public class MainActivity extends Activity implements OnTouchListener
{
	private ArrayList<Path> _graphics = new ArrayList<Path>();
	private Paint mPaint;
	private Path path;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	  super.onCreate(savedInstanceState);
	  setContentView(new View(this));
	  mPaint = new Paint();
	  mPaint.setDither(true);
	  mPaint.setColor(0xFFFFFF00);
	  mPaint.setStyle(Paint.Style.STROKE);
	  mPaint.setStrokeJoin(Paint.Join.ROUND);
	  mPaint.setStrokeCap(Paint.Cap.ROUND);
	  mPaint.setStrokeWidth(3);
	  
	  Renderer r = new Renderer(_graphics, mPaint);
	}

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
    public boolean onTouch(View v, MotionEvent event)
    {
//	  synchronized (_thread.getSurfaceHolder()) {
	    if(event.getAction() == MotionEvent.ACTION_DOWN){
	      path = new Path();
	      path.moveTo(event.getX(), event.getY());
	      path.lineTo(event.getX(), event.getY());
	    }else if(event.getAction() == MotionEvent.ACTION_MOVE){
	      path.lineTo(event.getX(), event.getY());
	    }else if(event.getAction() == MotionEvent.ACTION_UP){
	      path.lineTo(event.getX(), event.getY());
	      _graphics.add(path);
	    }
	    return true;
//	  }
	}
}
