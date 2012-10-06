package net.foxycorndog.p1xeland.market;

import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.ws.Service;

import javax.xml.namespace.QName;

import net.foxycorndog.p1xeland.items.tiles.Tile;

public class Client
{
	private Server server;
	
	public Client()
	{
		URL url = null;
		
		try
		{
			url = new URL("http://191.168.1.12:5754/market?wsdl");
		}
		catch (MalformedURLException e)
		{
			e.printStackTrace();
		}
		
		QName qName = new QName("http://market.p1xeland.foxycorndog.net/", "ServerImplementationService");
		
		Service service = Service.create(url, qName);
		
		server          = service.getPort(Server.class);
		
		int amount      = server.getAmount("dirt");
		
		System.out.println(amount);
		
		server.connect();
		
		System.out.println(server.isConnected());
	}
	
	public void send()
	{
		server.sellTile(Tile.IRON_ORE.getId(), 4, 10, 1);
	}
}