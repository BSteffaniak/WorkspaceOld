package net.foxycorndog.p1xeland.market;

import javax.jws.WebService;

import net.foxycorndog.jdoogl.connector.Connector;

@WebService(endpointInterface = "net.foxycorndog.p1xeland.market.Server")

public class ServerImplementation implements Server
{
	private Connector conn;
	
	@Override
	public int getAmount(String tile)
	{
		return 20;
	}
	
	@Override
	public void connect()
	{
		conn = null;
		
		new Thread()
		{
			public void run()
			{
				conn = new Connector();
				
				conn.connect("localhost", 3306, "data", "root", "server");
				
				System.out.println(conn.isConnected());
			}
		}.start();
	}
	
	@Override
	public boolean isConnected()
	{
		return conn.isConnected();
	}
	
	@Override
	public void sellTile(int tileId, int quantity, int price, int sellerId)
	{
		conn.sendQuery("INSERT INTO market VALUES('" + tileId + "', '" + quantity + "', '" + price + "', '" + sellerId + "');");
	}
}