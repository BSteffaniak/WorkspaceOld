import net.foxycorndog.jdoogl.GL;
import net.foxycorndog.jdoogl.camera.Camera;
import net.foxycorndog.jdoogl.components.Frame;
import net.foxycorndog.jdoogl.input.KeyboardInput;
import net.foxycorndog.jdoogl.input.MouseInput;
import net.foxycorndog.jdoutil.LightBuffer;
import net.foxycorndog.jdoutil.VerticesBuffer;

public class Main
{
	private static Main    m;
	
	private LightBuffer    textures;
	
	private VerticesBuffer vertices;
	
	private Player         player;
	
	private float lastTime;
//	private static final float _headSens = 0.002f;
//	private static final float _pitchSens = 0.002f;
//	private static final float _walk = 10.0f;
//	private static final float _strafe = 8.0f;
	
	public static void main(String args[])
	{
		m = new Main();
		
		new Frame(640, 512, "ASDF", null)
		{
			@Override
			public void init()
			{
				GL.setRender3D(true);
				
				GL.initBasicLights();
				
				m.init();
			}
			
			@Override
			public void render()
			{
				m.render();
			}
			
			@Override
			public void loop()
			{
				m.loop();
			}
		}.startLoop(60);
	}
	
	public Main()
	{
		
	}
	
	public void init()
	{
		vertices = new VerticesBuffer(4 * 3 * 6);
		
		vertices.addData(GL.addCubeVertexArrayf(0, 2.5f, -10, 2, 2, 2, 0, null));
		
		player = new Player();
		
		player.move(Camera.UP, 3);
	}
	
	public void render()
	{
		
		player.lookThrough();
//		p.draw();
		
		GL.renderCubes(vertices, 0, 1);

		GL.glBegin(GL.QUADS);
		GL.glColor3f(0.9f, 0.2f, 0.1f);
		GL.glVertex3f(-100.0f, 1.0f, 100.0f);
		GL.glColor3f(0.4f, 0.8f, 0.4f);
		GL.glVertex3f(-100.0f, 1.0f, -100.0f);
		GL.glColor3f(0.2f, 0.9f, 0.1f);
		GL.glVertex3f(100.0f, 1.0f, -100.0f);
		GL.glColor3f(0.5f, 0.2f, 0.9f);
		GL.glVertex3f(100.0f, 1.0f, 100.0f);
		GL.glEnd();
	}
	
	public void loop()
	{
		pollEvents();
	}
	
	private void pollEvents()
	{
		if (MouseInput.isButtonDown(MouseInput.LEFT_MOUSE_BUTTON))
		{
			MouseInput.setGrabbed(true);
		}
		
		if (KeyboardInput.isKeyDown(KeyboardInput.KEY_ESCAPE))
		{
			MouseInput.setGrabbed(false);
		}
		
//		// update timer
//		float now = (float)(((System.nanoTime() & 0xFFFFFFFF) / 1000000)/* - 1350000000l*/);
//		System.out.println(now);
//		float period = (now - lastTime) / 1000;
//		lastTime = now;
//		// get mouse alterations
		float dx = MouseInput.getDX();
		float dy = MouseInput.getDY();
//		// set heading and pitch
//		p.setHeading(dx * _headSens);
//		p.setPitch(dy * _pitchSens);
//		// handle camera translations
////		KeyboardInput.poll();
//		if (KeyboardInput.isKeyDown(KeyboardInput.KEY_A))
//		{System.out.println(period);
//			p.setlStrafe(_strafe * period);
//		}
//		if (KeyboardInput.isKeyDown(KeyboardInput.KEY_D))
//		{
//			p.setrStrafe(_strafe * period);
//		}
//		if (KeyboardInput.isKeyDown(KeyboardInput.KEY_W))
//		{
//			p.setFord(_walk * period);
//		}
//		if (KeyboardInput.isKeyDown(KeyboardInput.KEY_S))
//		{
//			p.setBack(_walk * period);
//		}
//		
//		if (KeyboardInput.isKeyDown(KeyboardInput.KEY_SPACE))
//		{
//			p.y -= 0.03f;
//		}
//		if (KeyboardInput.isKeyDown(KeyboardInput.KEY_LEFT_SHIFT))
//		{
//			p.y += 0.03f;
//		}
//		
//		// after keys are polled we build modelview matrix
//		p.set();
		
		if (KeyboardInput.isKeyDown(KeyboardInput.KEY_W))
		{
			player.move(Camera.FORWARD, 0.1f);
		}
		if (KeyboardInput.isKeyDown(KeyboardInput.KEY_A))
		{
			player.move(Camera.LEFT, 0.1f);
		}
		if (KeyboardInput.isKeyDown(KeyboardInput.KEY_S))
		{
			player.move(Camera.BACKWARD, 0.1f);
		}
		if (KeyboardInput.isKeyDown(KeyboardInput.KEY_D))
		{
			player.move(Camera.RIGHT, 0.1f);
		}
		if (KeyboardInput.isKeyDown(KeyboardInput.KEY_SPACE))
		{
			player.move(Camera.UP, 0.1f);
		}
		if (KeyboardInput.isKeyDown(KeyboardInput.KEY_LEFT_SHIFT))
		{
			player.move(Camera.DOWN, 0.1f);
		}
		
		player.yaw(dx * 0.07f);
		player.pitch(-dy * 0.07f);
	}
}