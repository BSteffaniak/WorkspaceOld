package net.foxycorndog.jdooglandroid.components;

import net.foxycorndog.jdooglandroid.GL;
import net.foxycorndog.jdooglandroid.image.imagemap.Texture;
import net.foxycorndog.jdooglandroid.input.TouchInput;
import net.foxycorndog.jdoutilandroid.LightBuffer;
import net.foxycorndog.jdoutilandroid.VerticesBuffer;

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
	
	public Joystick(Texture top, Texture bottom)
	{
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
		
		this.maxDistance   = 25;
		
		setWidth(bigWidth);
		setHeight(bigHeight);
	}
	
	public void render()
	{
		super.render();
		
		GL.beginManipulation();
		{
			float renderX = getX() + getOffsetX();
			float renderY = getY() + getOffsetY();
			
			GL.translatef(renderX, renderY, 0);
			
			float scaled[]     = GL.getAmountScaled();
			float translated[] = GL.getAmountTranslated();
			
			this.renderX = translated[0] * scaled[0];
			this.renderY = translated[1] * scaled[1];
			
			GL.beginManipulation();
			{
				GL.translatef(topX, topY, 0);
				
				GL.renderQuad(verticesBuffer, texturesBuffer, topTexture, 0);
			}
			GL.endManipulation();
			
			GL.renderQuad(verticesBuffer, texturesBuffer, bottomTexture, 1);
		}
		GL.endManipulation();
	}
	
	public void update()
	{
//		if (true)return;
		if (!TouchInput.isPressed())
		{
			topX     = 0;
			topY     = 0;
			
			distance = 0;
			degrees  = 0;
			
			return;
		}
		
		double renderX = this.renderX;// + ((bigWidth / 2f)  * getScaleWidth());
		double renderY = this.renderY;// + ((bigHeight / 2f) * getScaleHeight());
		
		System.out.println(renderX + ", " + renderY);
		
		double x = TouchInput.getX() * getScaleWidth();
		double y = TouchInput.getY() * getScaleHeight();
		
		double degrees  = 0;
		double distance = 0;
		
		distance = Math.sqrt(Math.pow(x - renderX, 2) + Math.pow(y - renderY, 2));
		
		x = renderX - x;
		y = renderY - y;
		
		int quadrant = 0;
		
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
		
		double opposite = Math.abs(x);
		double adjacent = Math.abs(y);
		
		degrees = Math.toDegrees(Math.atan(opposite / adjacent));
		
		degrees += 90 * quadrant;
		
		degrees %= 360;
		
		this.degrees  = degrees;
		this.distance = distance;
		
		topX = (float)-x;
		topY = (float)-y;
		
		System.out.println("topx and y: " + topX + ", " + topY);
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