package net.foxycorndog.p1xeland.market;

import javax.xml.ws.Endpoint;

import net.foxycorndog.jdoogl.Color;
import net.foxycorndog.jdoogl.GL;
import net.foxycorndog.jdoogl.components.Frame;
import net.foxycorndog.jdoogl.input.KeyboardInput;

public class Publisher
{
	private ServerImplementation si;
	
	private Endpoint             endpoint;
	
	public Publisher()
	{
		si = new ServerImplementation();
		
		new Frame(200, 200, "Publisher Control Panel", null)
		{
			
			@Override
			public void render()
			{
				Frame.renderText(0, 0, endpoint != null && endpoint.isPublished() ? "Started" : "Not Started", endpoint != null && endpoint.isPublished() ? Color.BLACK : Color.WHITE, 1);
				Frame.renderText(0, 16, "s - Start server.", endpoint != null && endpoint.isPublished() ? Color.BLACK : Color.WHITE, 1);
				Frame.renderText(0, 16 * 2, "e - End server.", endpoint != null && endpoint.isPublished() ? Color.BLACK : Color.WHITE, 1);
			}
			
			@Override
			public void loop()
			{
				if (KeyboardInput.next())
				{
					if (KeyboardInput.isKeyDown(KeyboardInput.KEY_S))
					{
						endpoint = Endpoint.publish("http://crapbuntu:5754/market", si);
						
						if (endpoint.isPublished())
						{
							System.out.println("Started.");
							GL.setClearColorf(0, 1, 0, 1);
						}
						else
						{
							System.out.println("Unsuccessful.");
						}
					}
					if (endpoint != null && KeyboardInput.isKeyDown(KeyboardInput.KEY_E))
					{
						endpoint.stop();
						
						if (!endpoint.isPublished())
						{
							System.out.println("Stopped.");
							GL.setClearColorf(0, 0, 0, 1);
						}
						else
						{
							System.out.println("Unsuccessful.");
						}
					}
				}
			}
			
			@Override
			public void init()
			{
				GL.setClearColorf(0, 0, 0, 1);
			}
		}.startLoop(5);
	}
	
	public static void main(String args[])
	{
		new Publisher();
	}
}