package net.foxycorndog.jfoxylib.components;

import net.foxycorndog.jfoxylib.GameEntry;
import net.foxycorndog.jfoxylib.events.MoveEvent;
import net.foxycorndog.jfoxylib.graphics.opengl.GL;
import net.foxycorndog.jfoxylib.input.Keyboard;
import net.foxycorndog.jfoxylib.input.Mouse;
import net.foxycorndog.jfoxylib.listeners.MoveListener;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.ControlListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.opengl.GLCanvas;
import org.eclipse.swt.opengl.GLData;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GLContext;

/**
 * 
 * 
 * @author	Braden Steffaniak
 * @since	Feb 15, 2013 at 11:43:57 PM
 * @since	v0.1
 * @version	v0.1
 */
public abstract class GLPanel extends Panel
{
	private boolean			render;
	
	private int				fps, dfps;
	
	private long			startTime, waitStart;
	
	private GLCanvas		canvas;
	
	private Frame			frame;
	
	private MoveListener	moveListener;
	
	public GLPanel(Panel parent)
	{
		this(parent, Display.getDefault());
	}
	
	public GLPanel(final Panel parent, final Display display)
	{
		super(parent);
		
		GLData data = new GLData();
		data.doubleBuffer = true;
		
		GL.init();
		canvas = new GLCanvas(getComposite(), SWT.NONE, data);
		
		canvas.setCurrent();
		
		render = true;
		
		try
		{
			GLContext.useContext(canvas);
		}
		catch (LWJGLException e)
		{
			e.printStackTrace();
		}
		
		final Runnable run = new Runnable()
		{
			public void run()
			{
				long newTime = System.currentTimeMillis();
				
				if (render)
				{
					GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
					
					GL.resetMatrix();
					GL.initPerspective(getWidth(), getHeight(), 0.01f, 999f);
					
					render3D();
					
					GL.resetMatrix();
					GL.initOrtho(getWidth(), getHeight());
//					GL11.glPushAttrib(GL11.GL_LIGHTING_BIT);
//					{
//						GL11.glDisable(GL11.GL_LIGHTING);
						render2D();
//					}
//					GL11.glPopAttrib();
					
					loop();
					
					dfps++;
					
					if (startTime + 1000 <= newTime)
					{
						fps  = dfps;
						dfps = 0;
						
						startTime = newTime;
					}
					
					canvas.swapBuffers();
					Mouse.update();
					Keyboard.update();
				}
//				else
//				{
//					if (waitStart + 200 <= newTime)
//					{
//						render = true;
//					}
//				}
				
				GameEntry.notifyGameListeners();
				
				display.asyncExec(this);
			}
		};
		
		canvas.addListener(SWT.Paint, new Listener()
		{
			public void handleEvent(Event e)
			{
				run.run();
			}
		});
		
		init();
		
		startTime = System.currentTimeMillis();
		display.asyncExec(run);
		setControl(canvas);
		
		parent.getComposite().addControlListener(new ControlListener()
		{
			public void controlResized(ControlEvent e)
			{
				setSize(parent.getWidth(), parent.getHeight());
			}
			
			public void controlMoved(ControlEvent e)
			{
				
			}
		});
		
		moveListener = new MoveListener()
		{
			public void moved(MoveEvent event)
			{
//				waitStart = System.currentTimeMillis();
//				render = false;
			}
		};
	}
	
	public int getFps()
	{
		return fps;
	}
	
	public void setSize(int width, int height)
	{
		super.setSize(width, height);
		
		GL.initOrtho(getWidth(), getHeight());
	}
	
	public void setFrame(Frame frame)
	{
		if (this.frame != null)
		{
			this.frame.removeMoveListener(moveListener);
		}
		
		this.frame = frame;
		
		frame.addMoveListener(moveListener);
	}

	public abstract void loop();
	public abstract void init();
	public abstract void render2D();
	public abstract void render3D();
}