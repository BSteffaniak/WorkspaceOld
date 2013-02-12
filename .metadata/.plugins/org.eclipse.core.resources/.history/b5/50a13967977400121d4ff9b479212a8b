import net.foxycorndog.jfoxylib.GameEntry;
import net.foxycorndog.jfoxylib.components.Button;
import net.foxycorndog.jfoxylib.components.Component;
import net.foxycorndog.jfoxylib.components.Window;

public class Game extends GameEntry
{
	private Window window;
	
	private Button play;
	
	public static void main(String args[])
	{
		new Game();
		
		System.out.println("done");
	}
	
	public Game()
	{
		window = new Window();
		window.setSize(800, 600);
		window.setMaximizable(true);
		window.setResizable(true);
		
		createMainMenu();
		
		window.open();
		
		final Game c = this;
		
		setMainFrame(window);
		
		start();
	}
	
	private void createMainMenu()
	{
		play = new Button();
		play.setText("Play Now!");
		play.setAlignment(Component.CENTER, Component.CENTER);
		play.setLocation(0, 0);
		play.addTo(window);
		
		
	}
	
	public void loop()
	{
//		stop();
	}
}