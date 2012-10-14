import net.foxycorndog.jdoogl.input.KeyboardInput;
import net.foxycorndog.jdoogl.input.MouseInput;

import org.lwjgl.opengl.GL11;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.Sys;

/*
 * Game is the hub handling and responding to events and the main
 * loop's tick() method.
 * @Author Stephen Jones
 */
public class Game {
	// variables for modifying the amount to affect viewport
	// rotations/transformations
	private static final float _headSens = 0.02f;
	private static final float _pitchSens = 0.01f;
	private static final float _walk = 10.0f;
	private static final float _strafe = 8.0f;
	private float lastTime;
	private Floor floor;// a simple floor
	private Box box;// the box to be positioned
	private Player player;// the player and weapon placement
	/*
	 * Game initializes a basic floor, a box on it, and the moveable player.
	 * Keys and mouse events are handled in the Game class.
	 */

	public Game() {
		floor = new Floor();
		box = new Box();
		player = new Player();
		// initialize keyboard and mouse events
		try {
			Keyboard.create();
			Mouse.create();
			Mouse.setGrabbed(true);
		} catch (LWJGLException e) {
			System.out.println("error setting up event hardware");
			System.exit(0);
		}
		lastTime = Sys.getTime();// initialize timer
	}

	/*
	 * Every game loop we poll the keyboard, setup the player/camera based upon
	 * keyboard input, then draw the objects. The order of the drawing is
	 * important.
	 */
	public void tick()
	{
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
		GL11.glLoadIdentity();
		loop();
		render();
	}
	
	public void render()
	{
		player.draw();
		floor.draw();
		box.draw();
	}
	
	public void loop()
	{
		pollEvents();
	}

	/*
	 * If the mouse is moved left or right the player's heading is altered. If
	 * the mouse is moved up or down, the player looks up or down. Keys W and S
	 * move the player forward or back. Keys A and D strafe the player left or
	 * right. All values handed to the player are modified. Rotations (heading
	 * and pitch) are mouse movement during this loop multiplied by a float
	 * variable. Motions (forward/back and strafe) are a constant values
	 * multiplied by the time period taken for this loop.
	 */
	private void pollEvents() {
		// update timer
		float now = Sys.getTime();
		float period = (now - lastTime) / 1000;
		lastTime = now;
		// get mouse alterations
		float dx = Mouse.getDX();
		float dy = Mouse.getDY();
		// set heading and pitch
		player.setHeading(dx * _headSens);
		player.setPitch(dy * _pitchSens);
		// handle camera translations
		Keyboard.poll();
		if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
			player.setlStrafe(_strafe * period);
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
			player.setrStrafe(_strafe * period);
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
			player.setFord(_walk * period);
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
			player.setBack(_walk * period);
		}
		// after keys are polled we build modelview matrix
		player.set();
		
		if (Keyboard.isKeyDown(KeyboardInput.KEY_ESCAPE))
		{
			MouseInput.setGrabbed(false);
		}
		
		if(KeyboardInput.next() && KeyboardInput.isKeyDown(KeyboardInput.KEY_K))
		{
			if (GL11.glIsEnabled(GL11.GL_TEXTURE_2D))
			{
				GL11.glDisable(GL11.GL_TEXTURE_2D);
			}
			else
			{
				GL11.glEnable(GL11.GL_TEXTURE_2D);
			}
		}
	}
}