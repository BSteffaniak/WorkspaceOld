package net.foxycorndog.idk;

public class Idk
{
	Display display;
	
	public static final String GAME_TITLE = "Idk";
	
	public Idk()
	{
		
	}
	
	public static void main(String args[])
	{
		Idk idk = new Idk();
		
		idk.init();
	}
	
	private void init()
	{
		display = new Display(640, 512);
	}
}