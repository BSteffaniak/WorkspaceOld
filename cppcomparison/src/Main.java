import net.foxycorndog.jdoogl.GL;
import net.foxycorndog.jdoogl.components.Frame;
import net.foxycorndog.jdoutil.HeavyBuffer;
import net.foxycorndog.jdoutil.LightBuffer;

public class Main
{
	final int amount    = 30000;
	
	int    lifeSpan[]   = new int[amount];
	
	double x[]          = new double[amount];
	double y[]          = new double[amount];
	double randOffset[] = new double[amount];
	
	long   oldTime;
	
	int    fps;
	
	HeavyBuffer vertices, textures;
	
	public static void main(String args[])
	{
		new Main();
	}
	
	public Main()
	{
		new Frame(640, 512, "asdf", null)
		{
			@Override
			public void render()
			{
				for (int i = 0; i < amount; i ++)
				{
		//			x[i] = x[i] + (getRand() - 0.5);
		//			y[i] = y[i] + 0.1 * (randOffset[i]);
		//			
		//			if (y[i] >= lifeSpan[i] * (1.5-randOffset[i]))
		//			{
		//				x[i] = 0;
		//				y[i] = 0;
		//
		//				randOffset[i] = getRand();
		//			}
		
					x[i] = x[i] + (getRand() -  0.5) * 35;
					y[i] = y[i] + 1 + Math.abs(randOffset[i]);
					
					if (y[i] >= lifeSpan[i] * (randOffset[i]))
					{
						x[i] = 0;
						y[i] = 0;
						
						randOffset[i] = getRand() - 0.5;
					}
		
//					GL.glBegin(GL.QUADS);
//					{
//						GL.glVertex3d(x[i],     y[i],     0.0);
//						GL.glVertex3d(x[i] + 1, y[i], 0.0);
//						GL.glVertex3d(x[i] + 1, y[i] + 1, 0.0);
//						GL.glVertex3d(x[i]    , y[i] + 1, 0.0);
//					}
//					GL.glEnd();
					
					vertices.setData(i * 4 * 2, GL.addRectVertexArrayd(x[i], y[i], 1, 1, 0, null));
					
//					System.out.println(x[i] + ", " + y[i]);
				}
				
				GL.beginManipulation();
				{
					GL.translatef(320, 100, 0);
					
					GL.renderQuads(vertices, 0, amount);
				}
				GL.endManipulation();
			
				long cur = System.currentTimeMillis();
				
				if (cur >= oldTime + 1000)
				{
					System.out.println("FPS: " + fps);
			
					oldTime = cur;
			
					fps = 0;
				}
			
				fps ++;
			}
			
			@Override
			public void loop()
			{
				
			}
			
			@Override
			public void init()
			{
				GL.setColorf(1, 1, 1, 1);
				
				initialize();
			}
		}.startLoop(-1);
	}
	
	public void initialize()
	{
		vertices = new HeavyBuffer(amount * 4 * 2);
		
		oldTime = System.currentTimeMillis();
	
		for (int i = 0; i < amount; i ++)
		{
			lifeSpan[i] = 1000;
			randOffset[i] = getRand();
		}
	
		GL.setClearColorf(0.0f, 0.0f, 0.0f, 0.0f);
	}
	
	public double getRand()
	{
		return (double)Math.random();
	}
}