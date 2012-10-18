package net.foxycorndog.shoot;

import net.foxycorndog.jdoogl.GL;
import net.foxycorndog.jdoogl.camera.Camera;
import net.foxycorndog.jdoogl.components.Frame;
import net.foxycorndog.jdoogl.image.imagemap.SpriteSheet;
import net.foxycorndog.jdoogl.image.imagemap.Texture;
import net.foxycorndog.jdoogl.input.KeyboardInput;
import net.foxycorndog.jdoogl.input.MouseInput;
import net.foxycorndog.jdoutil.LightBuffer;
import net.foxycorndog.jdoutil.VerticesBuffer;
import net.foxycorndog.shoot.actor.Player;
import net.foxycorndog.shoot.map.Map;

public class Main
{
	private static Main    m;
	
	private Player         player;
	
	private Map            map;
	
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
		
		map = new Map();
		
		player = new Player(2, 2, 2, 1, 1, 1, map);
		
		player.move(0, 9, 0);
	}
	
	public void render()
	{
		player.lookThrough();
		
		player.render();
		
		map.render();
	}
	
	public void loop()
	{
		player.move(0, -0.1f, 0);
		
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
		float dx = MouseInput.getDX() + MouseInput.getDraggedDX();
		float dy = MouseInput.getDY() + MouseInput.getDraggedDY();
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
//			player.move(Camera.FORWARD, 0.1f);
			player.move(0, 0, -0.1f);
		}
		if (KeyboardInput.isKeyDown(KeyboardInput.KEY_A))
		{
//			player.move(Camera.LEFT, 0.1f);
			player.move(-0.1f, 0, 0);
		}
		if (KeyboardInput.isKeyDown(KeyboardInput.KEY_S))
		{
//			player.move(Camera.BACKWARD, 0.1f);
			player.move(0, 0, 0.1f);
		}
		if (KeyboardInput.isKeyDown(KeyboardInput.KEY_D))
		{
//			player.move(Camera.RIGHT, 0.1f);
			player.move(0.1f, 0, 0);
		}
		if (KeyboardInput.isKeyDown(KeyboardInput.KEY_SPACE))
		{
//			player.move(Camera.UP, 0.1f);
			player.jump();
		}
		if (KeyboardInput.isKeyDown(KeyboardInput.KEY_LEFT_SHIFT))
		{
			player.move(0, -0.1f, 0);
		}
		
		if (MouseInput.isGrabbed())
		{
			player.yaw(dx * 0.10f);
			player.pitch(-dy * 0.10f);
		}
		
		if (KeyboardInput.next())
		{
			if (KeyboardInput.isKeyDown(KeyboardInput.KEY_C))
			{
				if (player.getPerspective() == Player.THIRD)
				{
					player.setPerspective(Player.FIRST);
				}
				else if (player.getPerspective() == Player.FIRST)
				{
					player.setPerspective(Player.THIRD);
				}
			}
		}
		
		player.update();
		
		System.out.println(player.getX() + ", " + player.getY() + ", " + player.getZ());
	}
}