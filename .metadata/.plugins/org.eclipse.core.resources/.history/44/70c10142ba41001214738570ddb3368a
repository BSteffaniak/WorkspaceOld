package net.foxycorndog.nostalgia;

import net.foxycorndog.jdooal.audio.AL;
import net.foxycorndog.jdoogl.GL;
import net.foxycorndog.jdoogl.activity.GameComponent;
import net.foxycorndog.jdoogl.components.Frame;
import net.foxycorndog.jdoogl.fonts.Font;
import net.foxycorndog.jdoogl.geometry.Point;
import net.foxycorndog.jdoogl.geometry.Vector;
import net.foxycorndog.jdoogl.image.imagemap.SpriteSheet;
import net.foxycorndog.jdoogl.image.imagemap.Texture;
import net.foxycorndog.jdoogl.input.KeyboardInput;
import net.foxycorndog.jdoogl.input.MouseInput;
import net.foxycorndog.jdoogl.shader.ShaderUtils;
import net.foxycorndog.jdoutil.LightBuffer;
import net.foxycorndog.jdoutil.VerticesBuffer;
import net.foxycorndog.nostalgia.actor.Actor;
import net.foxycorndog.nostalgia.actor.Player;
import net.foxycorndog.nostalgia.actor.camera.Camera;
import net.foxycorndog.nostalgia.items.weapons.guns.Gun;
import net.foxycorndog.nostalgia.items.weapons.guns.MachineGun;
import net.foxycorndog.nostalgia.items.weapons.guns.Pistol;
import net.foxycorndog.nostalgia.map.Map;

public class Main extends GameComponent
{
	private boolean     released, pic;
	
	private int         releasedDelay;
	private int         frameBufferId;
	private int         shot;
	
	private float       offsetY, offsetZ;
	
	private Font        font;
	
	private Player      player;
	
	private Map         map;
	
	private Camera      camera;
	
	private Texture     grass, texture;
	
	private static Main m;
	
	private int brightness;
	
	public static void main(String args[])
	{
		m = new Main();
	}
	
	public Main()
	{
		super("Nostalgia", 640, 512, -60);
	}
	
	public void onCreate()
	{
		brightness = 150;
		
		released = true;
		
		GL.setRender3D(true);
		
		AL.setMasterVolume(0.1f);
		
		map    = new Map();
		
		camera = new Camera();
		camera.setMaxPitch(90);
		camera.setMinPitch(-90);
		camera.setCameraMode(Camera.XZ_ONLY);
		
		player = new Player(2, 2, 2, 1, 1, 1, map);
		player.attachCamera(camera);
		
		Gun pistol = new Pistol(map);
		pistol.addAmmo(2500);
		pistol.reload(false);
		
		player.addGun(pistol, true);
		
		player.move(0, 9, 0);
		
		font = new Font("res/images/font/font.png", 26, 4,
				new char[]
				{
					'A', 'B', 'C', 'D', 'E', 'F',  'G', 'H', 'I', 'J', 'K', 'L',  'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
					'a', 'b', 'c', 'd', 'e', 'f',  'g', 'h', 'i', 'j', 'k', 'l',  'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
					'0', '1', '2', '3', '4', '5',  '6', '7', '8', '9', '_', '-',  '+', '=', '~', '`', '!', '@', '#', '$', '%', '^', '&', '*', '(', ')',
					'?', '>', '<', ';', ':', '\'', '"', '{', '}', '[', ']', '\\', '|', ',', '.', '/', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '
				});
		
		grass = new Texture("res/images/grass.png");
		
		GL.initLighting();
		GL.setShadeModel(GL.SMOOTH);
		
		GL.setAmbientLighti(brightness, brightness, brightness);
		GL.setLightProperties();
		
		GL.addSpecular(GL.LIGHT0, new Point(0.35f, 0.35f, 0.35f));
//		GL.addSpecular(GL.LIGHT1, new Point(01.0f, 01.0f, 01.0f));
//		
//		GL.setShadeModel(GL.SMOOTH);
//		
//		GL.addLight(new Vector(0, 0, -1), 15);
		
//		frameBufferId = GL.genFrameBuffer();
		
		texture = new Texture(0);
		
		GL.enablePolygonAntialiasing();
//		GL.initBasicLights();
	}
	
	public void render2D(int dfps)
	{
//		GL.drawTexture(grass, 50, 50, 0, 11);
		
		GL.pushAttribute(GL.CURRENT_BIT);
		{
			GL.setColori(255 - brightness, 255 - brightness, 255 - brightness, 255);
			
			if (player.getActiveGun() != null)
			{
				String gunAmmo = player.getActiveGun().getClipAmmo() + "/" + player.getActiveGun().getIdleAmmo();
				
				String dots = "";
				
				for (int i = 1; i < 3 + 1; i ++)
				{
					if (dfps > (i * 10))
					{
						dots += ".";
					}
					else
					{
						dots += " ";
					}
				}
				
				font.render(gunAmmo, 0, 0, 0, 5f);
				
				if (player.getActiveGun().isReloading())
				{
					font.render("Reloading" + dots, font.getGlyphWidth() * (gunAmmo.length() + 1) * 5f, 0, 0, 5f);
				}
			}
			
			font.render("+", 0, 0, 0, 4, Font.CENTER, Font.CENTER);
			
			font.render("FPS: " + Frame.getFps(), 0, 0, 0, 2, Font.RIGHT, Font.TOP);
		}
		GL.popAttribute();
	}
	
	public void render3D(int dfps)
	{
		player.lookThrough();
//		camera.lookThrough();
		
		player.render();
		
		map.render(camera.getLocation(), dfps);
		
		GL.setLightLocation(GL.LIGHT0, player.getLocation());
//		GL.setLightLocation(GL.LIGHT1, new Point(-2, 2, 4));
//		GL.setLightLocation(player.getX() + player.getCenterX(), player.getY() + player.getCenterY(), player.getZ() + player.getCenterZ());
//		if (pic)
//		{
//			pic = false;
//			GL.endScreenCapture();
//		}
//		if (KeyboardInput.isKeyDown(KeyboardInput.KEY_H))
//		{
//			pic     = true;
//			
//			int id  = GL.beginScreenCapture();
//			
//			texture = new Texture(id);
//		}
	}
	
	public void loop(int dfps)
	{
		float delta = 60f / Frame.getFps();
		
		map.update(dfps, delta);
		
//		if (dfps == 0)
//		{
//			System.out.println(Frame.getFps());
//		}
		
		if (MouseInput.isGrabbed())
		{
			float dWheel = MouseInput.getDWheel();
			
			if (dWheel != 0)
			{
				dWheel /= 112;
				
				if (player.cameraAttached() && player.getPerspective() == Actor.THIRD)
				{
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
				
				brightness += dWheel * 2;
				
				brightness = brightness < 0 ? 0 : brightness;
				
				GL.setAmbientLighti(brightness, brightness, brightness);
				GL.setClearColori(brightness, brightness, brightness, 255);
			}
			
			float dx = MouseInput.getDX();
			float dy = MouseInput.getDY();
			
			player.setSprinting(KeyboardInput.isKeyDown(KeyboardInput.KEY_LEFT_SHIFT));
			
			if (KeyboardInput.isKeyDown(KeyboardInput.KEY_W))
			{
				player.move(0, 0, -0.1f * delta);
			}
			if (KeyboardInput.isKeyDown(KeyboardInput.KEY_A))
			{
				player.move(-0.1f * delta, 0, 0);
			}
			if (KeyboardInput.isKeyDown(KeyboardInput.KEY_S))
			{
				player.move(0, 0, 0.1f * delta);
			}
			if (KeyboardInput.isKeyDown(KeyboardInput.KEY_D))
			{
				player.move(0.1f * delta, 0, 0);
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
			
			if (KeyboardInput.next(KeyboardInput.KEY_L))
			{
				map.render = map.render == GL.POINTS ? GL.TRIANGLES : GL.POINTS;
			}
			
			if (KeyboardInput.next(KeyboardInput.KEY_O))
			{
				
				GL.setWireFrameMode(!GL.isWireFrame(), GL.isWireFrame(), true);
		//			GL.setShowColors(!GL.isShowingColors());
			}
			
			if (KeyboardInput.next(KeyboardInput.KEY_R))
			{
				if (player.getActiveGun() != null)
				{
					Gun gun = player.getActiveGun();
					
					if (gun.getIdleAmmo() > 0)
					{
						gun.reload();
					}
				}
			}
				
			if (MouseInput.isButtonDown(MouseInput.LEFT_MOUSE_BUTTON))
			{
				if (player.getActiveGun() != null)
				{
					if (releasedDelay > player.getActiveGun().getShotDelay() && (player.getActiveGun().getShotDelay() != -1 || released))
					{
						player.useActiveWeapon();
						
						released      = false;
						releasedDelay = 0;
					}
					else
					{
						releasedDelay ++;
					}
				}
			}
			else
			{
				released = true;
			}
		}
		
		if (MouseInput.isButtonDown(MouseInput.LEFT_MOUSE_BUTTON))
		{
			MouseInput.setGrabbed(true);
			
			released = false;
		}
		
		if (KeyboardInput.isKeyDown(KeyboardInput.KEY_ESCAPE))
		{
			MouseInput.setGrabbed(false);
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
		
//		System.out.println(player.getYaw() + ", " + player.getPitch() + ", " + player.getRoll());
			
		player.update(dfps, delta);
	}
}
