package net.foxycorndog.nostalgia;

import net.foxycorndog.jdoogl.GL;
import net.foxycorndog.jdoogl.activity.GameComponent;
import net.foxycorndog.jdoogl.components.Frame;
import net.foxycorndog.jdoogl.image.imagemap.SpriteSheet;
import net.foxycorndog.jdoogl.image.imagemap.Texture;
import net.foxycorndog.jdoogl.input.KeyboardInput;
import net.foxycorndog.jdoogl.input.MouseInput;
import net.foxycorndog.jdoutil.LightBuffer;
import net.foxycorndog.jdoutil.VerticesBuffer;
import net.foxycorndog.nostalgia.actor.Actor;
import net.foxycorndog.nostalgia.actor.Player;
import net.foxycorndog.nostalgia.actor.camera.Camera;
import net.foxycorndog.nostalgia.map.Map;

public class Main extends GameComponent
{
	private float          offsetY, offsetZ;
	
	private Player         player;
	
	private Map            map;
	
	private Camera         camera;
	
	private static Main    m;
//	private static final float _headSens = 0.002f;
//	private static final float _pitchSens = 0.002f;
//	private static final float _walk = 10.0f;
//	private static final float _strafe = 8.0f;
	
	public static void main(String args[])
	{
		m = new Main();
		
//		new Frame(640, 512, "ASDF", null)
//		{
//			public void init()
//			{
//				GL.setRender3D(true);
//				
////				GL.initBasicLights();
//				
//				m.init();
//			}
//			
//			public void render()
//			{
//				m.render();
//			}
//			
//			public void loop()
//			{
//				m.loop();
//			}
//		}.startLoop(60);
	}
	
	public Main()
	{
		super("Nostalgia", 640, 512);
	}
	
	public void onCreate()
	{
		GL.setRender3D(true);
		
//		texture = new Texture("res/images/grass.png");
//		texture2 = new Texture("");
		
		map    = new Map();
		
		camera = new Camera();
		camera.setMaxPitch(90);
		camera.setMinPitch(-90);
		camera.setCameraMode(Camera.XZ_ONLY);
		
		player = new Player(2, 2, 2, 1, 1, 1, map);
		player.attachCamera(camera);
		
		player.move(0, 9, 0);
	}
	
	public void render()
	{
		player.lookThrough();
//		camera.lookThrough();
		
		player.render();
		
		map.render();
	}
	
	public void loop(int dfps)
	{
		if (MouseInput.isButtonDown(MouseInput.LEFT_MOUSE_BUTTON))
		{
			MouseInput.setGrabbed(true);
		}
		
		if (KeyboardInput.isKeyDown(KeyboardInput.KEY_ESCAPE))
		{
			MouseInput.setGrabbed(false);
		}
		
		if (MouseInput.isGrabbed())
		{
			float dWheel = MouseInput.getDWheel();
			
			if (dWheel != 0 && player.cameraAttached() && player.getPerspective() == Actor.THIRD)
			{
				dWheel /= 100;
				
				offsetY += -1/5f * dWheel;
				offsetZ += -5/5f * dWheel;
				
				if (offsetY > 0 && offsetZ > 0)
				{
					camera.move(0, -1/5f * dWheel, -5/5f * dWheel);
				}
				else
				{
					offsetY -= -1/5f * dWheel;
					offsetZ -= -5/5f * dWheel;
				}
			}
			
			float dx = MouseInput.getDX();
			float dy = MouseInput.getDY();
			
			player.setSprinting(KeyboardInput.isKeyDown(KeyboardInput.KEY_LEFT_SHIFT));
			
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
			
			player.yaw(dx * 0.10f);
			player.pitch(-dy * 0.10f);
			
			if (KeyboardInput.next(KeyboardInput.KEY_C))
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
			
			if (KeyboardInput.next(KeyboardInput.KEY_P))
			{
				if (GL.getDrawMode() == GL.ARRAYS)
				{
					GL.setDrawMode(GL.IMMEDIATE);
				}
				else if (GL.getDrawMode() == GL.IMMEDIATE)
				{
					map.genIndices();
					
					GL.setDrawMode(GL.ELEMENTS);
				}
				else if (GL.getDrawMode() == GL.ELEMENTS)
				{
					map.destroyIndices();
					
					GL.setDrawMode(GL.ARRAYS);
				}
			}
		
			if (KeyboardInput.next(KeyboardInput.KEY_L))
			{
				map.render = map.render == GL.POINTS ? GL.TRIANGLES : GL.POINTS;
			}
			
			if (KeyboardInput.next(KeyboardInput.KEY_O))
			{
				GL.setWireFrameMode(!GL.isWireFrame(), GL.isWireFrame(), true);
		//			GL.setShowColors(!GL.isShowingColors());
			}
		}
		
//			if (KeyboardInput.isKeyDown(KeyboardInput.KEY_R))
//			{
//				if (player.cameraAttached())
//				{
//					player.detachCamera();
//				}
//				else
//				{
//					player.attachCamera(camera);
//				}
//			}
		
		if (KeyboardInput.next(KeyboardInput.KEY_F11))
		{
			Frame.setFullscreen(!Frame.isFullscreen());
		}
		
//			if (KeyboardInput.isKeyDown(KeyboardInput.KEY_K))
//			{
//				player.deta
//			}
		
		System.out.println(player.getX() + ", " + player.getY() + ", " + player.getZ());
			
		player.update(dfps);
	}
}
