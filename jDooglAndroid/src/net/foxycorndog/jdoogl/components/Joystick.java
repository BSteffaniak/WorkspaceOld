package net.foxycorndog.jdoogl.components;

import net.foxycorndog.jdoogl.GL;
import net.foxycorndog.jdoogl.image.imagemap.Texture;
import net.foxycorndog.jdoogl.input.TouchInput;
import net.foxycorndog.jdoogl.listeners.ActionListener;
import net.foxycorndog.jdoutil.LightBuffer;
import net.foxycorndog.jdoutil.VerticesBuffer;

public class Joystick extends Component
{
	private int            bigWidth, bigHeight;
	
	private float          topX, topY;
	private float          renderX, renderY;
	
	private double         degrees;
	private double         distance, maxDistance;
	
	private VerticesBuffer verticesBuffer;
	private LightBuffer    texturesBuffer;
	
	private Texture        topTexture, bottomTexture;
	
	private ActionListener listener;
	
	private Joystick       thisJoystick;
	
	public Joystick(Texture top, Texture bottom)
	{
		thisJoystick = this;
		
		verticesBuffer = new VerticesBuffer(4 * 2 * 2, 2);
		texturesBuffer = new LightBuffer(4 * 2 * 2, 2);
		
		int bigWidth  = bottom.getWidth() > top.getWidth() ? bottom.getWidth() : top.getWidth();
		int bigHeight = bottom.getHeight() > top.getHeight() ? bottom.getHeight() : top.getHeight();

		verticesBuffer.setData(4 * 2 * 0, GL.addRectVertexArrayf(0, 0, bottom.getWidth(), bottom.getHeight(), 0, null));
		verticesBuffer.setData(4 * 2 * 1, GL.addRectVertexArrayf(0, 0, top.getWidth(), top.getHeight(), 0, null));

		texturesBuffer.setData(4 * 2 * 0, GL.addRectTextureArrayf(top, 0, null));
		texturesBuffer.setData(4 * 2 * 1, GL.addRectTextureArrayf(bottom, 0, null));
		
		this.topTexture    = top;
		this.bottomTexture = bottom;
		
		this.bigWidth      = bigWidth;
		this.bigHeight     = bigHeight;
		
		this.maxDistance   = 100;
		
		setWidth(bigWidth);
		setHeight(bigHeight);
		
		listener = new ActionListener()
		{
			public void onActionPerformed(Component source)
			{
				if (source == thisJoystick)
				{
					
				}
			}

			public void onHover(Component source)
			{
				
			}
		};
		
		addActionListener(listener);
	}
	
	public void render()
	{
		render(1, 1, 1);
	}
	
	public void render(float x, float y, float z)
	{
		GL.beginManipulation();
		{
			GL.scalef(x, y, z);
			
			super.render();
		}
		GL.endManipulation();
		
		GL.beginManipulation();
		{
			float renderX = getX() + getOffsetX();
			float renderY = getY() + getOffsetY();
			
			GL.translatef(renderX, renderY, 0);
			
			float renderLoc[] = GL.getRenderLocation();
			
			this.renderX = renderLoc[0];
			this.renderY = renderLoc[1];
			
			GL.beginManipulation();
			{
				GL.translatef(topX, topY, 0);
				
				GL.scalef(x, y, z);
				
				GL.renderQuad(verticesBuffer, texturesBuffer, topTexture, 0);
			}
			GL.endManipulation();
			
			GL.beginManipulation();
			{
				GL.scalef(x, y, z);
				
				GL.renderQuad(verticesBuffer, texturesBuffer, bottomTexture, 1);
			}
			GL.endManipulation();
		}
		GL.endManipulation();
	}
	
	public void update()
	{
		if (!TouchInput.isPressed() || !isFocused())
		{
			topX     = 0;
			topY     = 0;
			
			distance = 0;
			degrees  = 0;
			
			return;
		}
		
		double renderX = this.renderX + ((bigWidth / 2f)  * getScaleWidth());
		double renderY = this.renderY + ((bigHeight / 2f) * getScaleHeight());
		
		double x = TouchInput.getX();
		double y = TouchInput.getY();
		
		double degrees  = 0;
		double distance = 0;
		
		distance = Math.sqrt(Math.pow(x - renderX, 2) + Math.pow(y - renderY, 2));
		
		x = -(renderX - x);
		y = -(renderY - y);
		
		int quadrant = 0;
		
		double opposite = Math.abs(x);
		double adjacent = Math.abs(y);
		
		degrees = Math.toDegrees(Math.atan(opposite / adjacent));
		
		if (x < 0)
		{
			if (y < 0)
			{
				quadrant = 2;
			}
			else
			{
				quadrant = 1;
			}
		}
		else
		{
			if (y < 0)
			{
				quadrant = 3;
			}
			else
			{
				quadrant = 0;
			}
		}
		
		degrees += 90 * quadrant;
		
		degrees %= 360;
		
		if (quadrant == 0)
		{
			degrees = 90 - degrees;
		}
		else if (quadrant == 2)
		{
			degrees = 270 - degrees;
			degrees = 180 + degrees;
		}
		
		this.degrees  = degrees;
		this.distance = distance;
		
		topX = (float)x;
		topY = (float)y;
		
		if (distance > maxDistance)
		{
			double scale = maxDistance / distance;
			
			x *= scale;
			y *= scale;
			
			topX = (float)x;
			topY = (float)y;
			
			distance = maxDistance;
		}
	}
	
	public float getHorizontalDistance()
	{
		return topX;
	}
	
	public float getVerticalDistance()
	{
		return topY;
	}
	
	public double getDegrees()
	{
		return degrees;
	}
	
	public double getDistance()
	{
		return distance;
	}
	
	public double getMaxDistance()
	{
		return maxDistance;
	}
	
	public void setMaxDistance(double maxDistance)
	{
		this.maxDistance = maxDistance;
	}
}