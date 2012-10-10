package net.foxycorndog.idk.network.packet;

public class PlayerLocationPacket extends Packet
{
	float location[];
	
	public PlayerLocationPacket(float location[])
	{
		super(0);
		
		this.location = location;
	}
	
	public float[] getLocation()
	{
		return location;
	}
}