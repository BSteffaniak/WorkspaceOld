import net.foxycorndog.jfoxylib.GameEntry;
import net.foxycorndog.jfoxylib.components.Button;
import net.foxycorndog.jfoxylib.components.Component;
import net.foxycorndog.jfoxylib.components.Window;

public class Nothinng extends GameEntry
{
	private Window window;
	
	private Button play;
	
	public static void main(String args[])
	{
		Nothinng game = new Nothinng();
		
		game.start();
		
		System.out.println("done");
	}
	
	public Nothinng()
	{
		window = new Window();
		window.setSize(800, 600);
		window.setMaximizable(true);
		window.setResizable(true);
		
		createMainMenu();
		
		window.open();
		
		final Nothinng c = this;
		
		setMainFrame(window);
	}
	
	private void createMainMenu()
	{
		
		
	}
	
	public void loop()
	{
//		stop();
	}
}