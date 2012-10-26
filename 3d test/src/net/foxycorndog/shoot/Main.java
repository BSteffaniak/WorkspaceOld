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
		
		float dx = MouseInput.getDX() + MouseInput.getDraggedDX();
		float dy = MouseInput.getDY() + MouseInput.getDraggedDY();
		
		if (KeyboardInput.isKeyDown(KeyboardInput.KEY_W))
		{
			player.move(0, 0, -0.1f);
		}
		if (KeyboardInput.isKeyDown(KeyboardInput.KEY_A))
		{
			player.move(-0.1f, 0, 0);
		}
		if (KeyboardInput.isKeyDown(KeyboardInput.KEY_S))
		{
			player.move(0, 0, 0.1f);
		}
		if (KeyboardInput.isKeyDown(KeyboardInput.KEY_D))
		{
			player.move(0.1f, 0, 0);
		}
		if (KeyboardInput.isKeyDown(KeyboardInput.KEY_SPACE))
		{
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
		
		System.out.println(player.getYaw() + ", " + player.getPitch());
	}
}