import net.foxycorndog.jdoogl.GL;
import net.foxycorndog.jdoogl.camera.Camera;
import net.foxycorndog.jdoogl.components.Frame;
import net.foxycorndog.jdoogl.image.imagemap.SpriteSheet;
import net.foxycorndog.jdoogl.image.imagemap.Texture;
import net.foxycorndog.jdoogl.input.KeyboardInput;
import net.foxycorndog.jdoogl.input.MouseInput;
import net.foxycorndog.jdoutil.LightBuffer;
import net.foxycorndog.jdoutil.VerticesBuffer;

public class Main
{
	private static Main    m;
	
//	private Texture        texture;
	private SpriteSheet    sprites;
	
	private LightBuffer    textures, colors;
	
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
			public void init()
			{
				GL.setRender3D(true);
				
//				GL.initBasicLights();
				
				m.init();
			}
			
			public void render()
			{
				m.render();
			}
			
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
//		texture = new Texture("res/images/grass.png");
//		texture2 = new Texture("");
		sprites = new SpriteSheet("res/images/sprites.png", 36, 18);
		
		vertices = new VerticesBuffer(4 * 3 * 6 * 2);
		textures = new LightBuffer(2 * 4 * 6 * 2);
		colors   = new LightBuffer(4 * 4 * 6 * 2);
		
		vertices.addData(GL.addCubeVertexArrayf(0, 2.5f, -10, 2, 2, 2, 0, null));
		textures.addData(GL.addCubeTextureArrayf(new float[][] { sprites.getImageOffsetsf(1, 0, 1, 1), sprites.getImageOffsetsf(1, 0, 1, 1), sprites.getImageOffsetsf(1, 0, 1, 1), sprites.getImageOffsetsf(1, 0, 1, 1), sprites.getImageOffsetsf(7, 2, 1, 1), sprites.getImageOffsetsf(2, 0, 1, 1),  }, 0, null));

		int r = 200;
		int g = 200;
		int b = 200;
		int a = 200;
		colors.addData(GL.addCubeColorArrayif(
				new int[][]
				{
					new int[] { 255, 255, 255, a },
					new int[] { 255, 255, 255, a },
					new int[] { 255, 255, 255, a }, 
					new int[] { 255, 255, 255, a },
					new int[] { 151, 255, 100, a },
					new int[] { 255, 255, 255, a }
				} , 0, null));
		
		vertices.addData(GL.addCubeVertexArrayf(-100, -2, -100, 200, 2, 200, 0, null));
		textures.addData(GL.addCubeTextureArrayf(GL.white, 0, null));
		
		r = 200;
		g = 200;
		b = 200;
		a = 255;
		colors.addData(GL.addCubeColorArrayif(
				new int[][]
				{
					new int[] { r, g, b, a },
					new int[] { r, g, b, a },
					new int[] { r, g, b, a }, 
					new int[] { r, g, b, a },
					new int[] { r, g, b, a },
					new int[] { r, g, b, a }
				} , 0, null));
		
		player = new Player();
		
		player.move(Camera.UP, 3);
	}
	
	public void render()
	{
		player.lookThrough();
//		p.draw();
		GL.beginColorDraw(colors);
		
		GL.renderCubes(vertices, textures, sprites, 0, 1);
		
		GL.renderCubes(vertices, textures, GL.white, 1, 1);

		GL.endColorDraw();
		
		
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
		
		if (MouseInput.isGrabbed())
		{
			player.yaw(dx * 0.10f);
			player.pitch(-dy * 0.10f);
		}
	}
}