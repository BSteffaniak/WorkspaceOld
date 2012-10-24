import java.awt.Point;

import net.foxycorndog.jdoutil.network.Client;
import net.foxycorndog.jdoutil.network.Packet;

public class Clie
{
	private Client client;
	
	public static void main(String args[])
	{
		new Clie();
	}
	
	public Clie()
	{
		client = new Client("0.0.0.0", 5565)
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
		
		client.connect();
		
		System.out.println(client.isConnected());
	}
}