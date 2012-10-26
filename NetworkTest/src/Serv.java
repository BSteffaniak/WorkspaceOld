import java.awt.Point;

import net.foxycorndog.jdoutil.network.Packet;
import net.foxycorndog.jdoutil.network.Server;

public class Serv
{
	Server server;
	
	public static void main(String args[])
	{
		new Serv();
	}
	
	public Serv()
	{
		server = new Server(5565)
		{
			public void onReceivedPacket(Packet packet)
			{
				System.out.println("Packet received!");
				
				if (packet.getObject() instanceof Point)
				{
					Point p = (Point)packet.getObject();
					
					System.out.println(p.x + ", " + p.y);
				}
			}
		};
		
		server.create();
	}
}