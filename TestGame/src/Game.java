public class Game
{
	private Window window;
	
	private Button play;
	
	public static void main(String args[])
	{
		new Game();
	}
	
	public Game()
	{
		window = new Window(800, 600, "title");
		
		createMainMenu();
	}
	
	private void createMainMenu()
	{
		play = new Button();
		play.setText("Play Now!");
		play.setLocation(x, y);
		play.addTo(window);
		
		
	}
}