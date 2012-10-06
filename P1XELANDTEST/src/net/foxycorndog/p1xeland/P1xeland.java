package net.foxycorndog.p1xeland;

import net.foxycorndog.jdoogl.GL;
import net.foxycorndog.jdoogl.audio.AudioManager;
import net.foxycorndog.jdoogl.components.Frame;
import net.foxycorndog.jdoogl.connector.Connector;
import net.foxycorndog.jdoogl.connector.Result;
import net.foxycorndog.jdoogl.image.imagemap.SpriteSheet;
import net.foxycorndog.jdoogl.image.imagemap.Texture;
import net.foxycorndog.jdoogl.input.KeyboardInput;
import net.foxycorndog.jdoogl.input.MouseInput;
import net.foxycorndog.jdoogl.util.LightBuffer;
import net.foxycorndog.p1xeland.actors.Player;
import net.foxycorndog.p1xeland.chunks.Chunk;
import net.foxycorndog.p1xeland.map.Map;
import net.foxycorndog.p1xeland.tiles.Tile;

public class P1xeland
{
	private int             targetFps;
	
	private Cursor          cursor;
	
	private Player          player;
	
	private Map             map;
	
	AudioManager            soundm;
	
	private static P1xeland p;
	
	public  static int      textureSize;
	public  static int      scale;
	
	public  static String   GAME_TITLE;
	
	Connector conn;
	
	private class Cursor
	{
		private float   x, y;
		
		private Texture texture;
		
		private LightBuffer  verticesBuffer, texturesBuffer;
		
		public Cursor(String location)
		{
			texture = new Texture(location, "PNG", true);
			
			verticesBuffer = new LightBuffer(4 * 2);
			texturesBuffer = new LightBuffer(4 * 2);
			
			verticesBuffer.addData(GL.addRectVertexArrayf(0, 0, 16, 16, 0, null));
			texturesBuffer.addData(GL.addRectTextureArrayf(texture, 0, null));
			
			verticesBuffer.refreshData();
			texturesBuffer.refreshData();
		}
		
		public void setLocation(float x, float y)
		{
			this.x = x;
			this.y = y;
		}
		
		public void render()
		{
			GL.beginManipulation();
			{
				GL.translatef(x, y, 1f);
				
				GL.beginXOR();
				
				GL.renderQuad(verticesBuffer, texturesBuffer, texture);
				
				GL.endInverting();
			}
			GL.endManipulation();
		}
	}
	
	public P1xeland()
	{
		
	}
	
	public static void main(String args[])
	{
		p = new P1xeland();
		
		p.start();
	}
	
	public void start()
	{
		targetFps = 60;
		
		new Thread()
		{
			public void run()
			{
				conn = new Connector();
				
//				conn.connect("", , "", "", "");
			}
		}.start();
		
		new Frame(640, 512, GAME_TITLE, null)
		{
			@Override
			public void init()
			{
				soundm = new AudioManager();
				
				GL.light();
				
				scale       = 1;
				
				textureSize = 16;
				
				cursor      = new P1xeland.Cursor("res/images/Cursor.png");
				
				map         = new Map();
		
				for (int y = 0; y >= -0; y --)
				{
					for (int x = 0; x < 1; x ++)
					{
						map.addChunk(x, y);
						map.getChunk(x, y).generateTerrain(x > 0 ? map.getChunk(x - 1, y).getRightY() : 6, 0, y == 0);
					}
				}
				
				
				player = new Player(map);
				
				player.setLocation(Frame.getCenterX() / scale - player.getWidth() / 2, Frame.getHeight() / scale - player.getHeight());
				
				new SpriteSheet("res/images/texturepacks/16bit/Minecraft/minecraft.png", "PNG", 18, 18, true);
			}
			
			@Override
			public void loop()
			{
				setTitle("FPS: " + getFps() + " (" + player.getX() + ", " + player.getY() + ")" + " (" + MouseInput.getX() + ", " + MouseInput.getY() + ")");
				
				tick(60f / (float)getFps());
				
				if (KeyboardInput.next() && conn.isConnected())
				{
					if (KeyboardInput.isKeyDown(KeyboardInput.KEY_M))
					{
						Result res = conn.getResult("SELECT id, name, age FROM testtable;");
						
						String results[][] = res.getData();
						
						for (String row[] : results)
						{
							System.out.println("ID: " + row[0] + ", Name: " + row[1] + ", Age: " + row[2]);
						}
					}
					if (KeyboardInput.isKeyDown(KeyboardInput.KEY_N))
					{
						Result res = conn.getResult("SELECT id FROM testtable;");
						
						String results[][] = res.getData();
						
						conn.sendQuery("INSERT INTO testtable VALUES('" + (results.length + 1) + "', 'Jet', '23');");
					}
				}
			}
			
			@Override
			public void render()
			{
				GL.setLightProperties();
				
				GL.beginManipulation();
				{
//					GL.scalef(scale, scale, 1);
					
					map.render();
					
					player.render();
					
					cursor.render();
				}
				GL.endManipulation();
			}
		}.startLoop(targetFps);
	}
	
	public void tick(float delta)
	{
		if (delta > ((float)targetFps / ((float)targetFps / 2f)))
		{
			return;
		}
		else if (targetFps <= 0 && delta <= 0)
		{
			return;
		}
		
		boolean up       = KeyboardInput.isKeyDown(KeyboardInput.KEY_W) || KeyboardInput.isKeyDown(KeyboardInput.KEY_UP)    || KeyboardInput.isKeyDown(KeyboardInput.KEY_SPACE);
		boolean down     = KeyboardInput.isKeyDown(KeyboardInput.KEY_S) || KeyboardInput.isKeyDown(KeyboardInput.KEY_DOWN);
		boolean left     = KeyboardInput.isKeyDown(KeyboardInput.KEY_A) || KeyboardInput.isKeyDown(KeyboardInput.KEY_LEFT);
		boolean right    = KeyboardInput.isKeyDown(KeyboardInput.KEY_D) || KeyboardInput.isKeyDown(KeyboardInput.KEY_RIGHT);
		
		boolean sprint   = KeyboardInput.isKeyDown(KeyboardInput.KEY_LEFT_SHIFT);
		
		boolean movedY   = false;
		boolean movedX   = false;
		
		player.setSprinting(sprint);
		
		if (!player.isJumping())
		{
			boolean onGround = !player.move(0, -1 * delta) || !player.move(0, -1 * delta);
			
			player.setOnGround(onGround);
		}
		else
		{
			player.jump(delta);
			
			player.setOnGround(false);
		}
		
		if (up)
		{
			player.jump(delta);
		}
		else if (down)
		{
			movedY = player.move(0, -1.1f * delta);
		}
		if (left)
		{
			movedX = player.move(-1.1f * delta, 0);
		}
		else if (right)
		{
			movedX = player.move(1.1f * delta, 0);
		}
		
		boolean moved = movedY || movedX;
		
		if (!movedX)
		{
			player.onStoppedMoving();
		}
		
		float cursorX = (MouseInput.getX() / scale / textureSize) * textureSize + (map.getX() % textureSize);
		float cursorY = (MouseInput.getY() / scale / textureSize) * textureSize + (map.getY() % textureSize);
		
		cursorX = MouseInput.getX() / scale <  cursorX               ? cursorX - textureSize : cursorX;
		cursorX = MouseInput.getX() / scale >= cursorX + textureSize ? cursorX + textureSize : cursorX;
		cursorY = MouseInput.getY() / scale >= cursorY               ? cursorY + textureSize : cursorY;
		cursorY = MouseInput.getY() / scale <  cursorY               ? cursorY - textureSize : cursorY;
		
		cursor.setLocation(cursorX, cursorY);
		
		int editX = (int)Math.round(((cursorX - map.getX()) / (float)textureSize));
		int editY = (int)Math.round(((cursorY - map.getY()) / (float)textureSize));
		
		if (MouseInput.isButtonDown(MouseInput.BUTTON_0))
		{
			map.removeTile(editX, editY);
		}
		else if (MouseInput.isButtonDown(MouseInput.BUTTON_1))
		{
			map.addTile(editX, editY, Tile.DIRT, false);
		}
	}
}