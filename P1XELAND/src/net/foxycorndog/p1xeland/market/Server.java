package net.foxycorndog.p1xeland.market;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;

@WebService

@SOAPBinding(style = Style.RPC)

public interface Server
{
	@WebMethod
	public int getAmount(String tile);
	
	@WebMethod
	public boolean isConnected();
	
	@WebMethod
	public void connect();
	
	@WebMethod
	public void sellTile(int tileId, int quantity, int price, int sellerId);
}