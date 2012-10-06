package net.foxycorndog.p1xelandroid;

import static net.foxycorndog.jdooglandroid.GL.scalef;
import static net.foxycorndog.jdooglandroid.GL.translatef;

// new comment,
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.microedition.khronos.egl.EGL;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;

//import net.foxycorndog.jdolua.Lua;
//import net.foxycorndog.jdolua.LuaException;
import net.foxycorndog.jdooglandroid.Color;
import net.foxycorndog.jdooglandroid.GL;
import net.foxycorndog.jdooglandroid.components.Component;
import net.foxycorndog.jdooglandroid.components.Frame;
import net.foxycorndog.jdooglandroid.components.ImageButton;
import net.foxycorndog.jdooglandroid.components.Joystick;
import net.foxycorndog.jdooglandroid.components.Menu;
import net.foxycorndog.jdooglandroid.connector.Connector;
import net.foxycorndog.jdooglandroid.connector.Result;
import net.foxycorndog.jdooglandroid.image.imagemap.SpriteSheet;
import net.foxycorndog.jdooglandroid.image.imagemap.Texture;
import net.foxycorndog.jdooglandroid.input.KeyboardInput;
import net.foxycorndog.jdooglandroid.input.TouchInput;
//import net.foxycorndog.jdooglandroid.input.MouseInput;
import net.foxycorndog.jdooglandroid.listeners.ActionListener;
import net.foxycorndog.jdooglandroid.listeners.MouseListener;
import net.foxycorndog.jdooglandroid.noise.LineNoise;
import net.foxycorndog.jdooglandroid.components.Frame.GameRenderer;
import net.foxycorndog.jdoutilandroid.ArrayUtil;
import net.foxycorndog.jdoutilandroid.Distance;
import net.foxycorndog.jdoutilandroid.FileUtil;
import net.foxycorndog.jdoutilandroid.GeneralCollection;
import net.foxycorndog.jdoutilandroid.LightBuffer;
import net.foxycorndog.jdoutilandroid.Task;
import net.foxycorndog.jdoutilandroid.Util;
import net.foxycorndog.jdoutilandroid.VerticesBuffer;
import net.foxycorndog.jdoutilandroid.zip.Unzipper;
import net.foxycorndog.jdoutilandroid.web.Downloader;
import net.foxycorndog.jdoutilandroid.web.WebPage;
import net.foxycorndog.p1xelandroid.actors.Inventory;
import net.foxycorndog.p1xelandroid.actors.Player;
import net.foxycorndog.p1xelandroid.actors.Actor.Direction;
import net.foxycorndog.p1xelandroid.items.Item;
import net.foxycorndog.p1xelandroid.items.tiles.Tile;
import net.foxycorndog.p1xelandroid.map.Map;
import net.foxycorndog.p1xelandroid.map.chunks.Chunk;
import net.foxycorndog.p1xelandroid.menus.MainMenu;

public class P1xeland
{
	private boolean         playingGame;
	private boolean         inventoryOpen = false;
	private boolean         frozen;
	private boolean         fullscreen;
	private boolean         luaRenderingFinished;
	
	private int             currentModId;
	
	private float           delta;
	private float           oldMapX, oldMapY;
	private float           oldCursorX, oldCursorY;
	private float           blockDamage;
	
	private long            oldTime;
	
	private Joystick        joystick;
	
	private Player          player;
	
	private Map             map;
	
	private VerticesBuffer  blockDamageVerticesBuffer;
	private LightBuffer     blockDamageTexturesBuffer, test1, test2;
	
	private Thread          playerThread;
	
	private Menu            mainMenu;
	
	private P1xeland        thisGame;
	
	private GLSurfaceView   mGLView;
	
	private Activity        activity;
	
	private boolean         mbAble[];
	private boolean         modCalled[], modReady[];
	
	private long            modWaitTime[];
	
	private String          modResources[];
	
	private Thread          modThreads[];
	
	private static P1xeland p;
	
	public  static boolean  next;
	
	public  static int      oldWidth;
	public  static int      oldTouchX, oldTouchY;
	
	public  static final int targetFps = 60;
	
	public  static int      textureSize;
	public  static float    scale;
	
	public  static final String GAME_TITLE = "P1XELAND";
	public  static final String VERSION    = "0.5";
	
	public P1xeland()
	{
		thisGame      = this;
	}
	
	public void init()
	{
		GL.setClearColori(80, 162, 216, 255);
		
//		mainMenu = new MainMenu(this, activity);
		startGame();
	}
	
	public void loop()
	{
		if (playingGame)
		{
//			Frame.renderText(0, 0, "FPS: " + Frame.getFps(), Color.WHITE, 1, Frame.Alignment.RIGHT, Frame.Alignment.TOP);
//			Frame.renderText(0, 16, "(" + (int)((player.getX() / textureSize) ) + ", " + (int)(player.getY() / textureSize) + ")", Color.WHITE, 1, Frame.Alignment.RIGHT, Frame.Alignment.TOP);
//			Frame.renderText(0, 0, "Editing: " + player.getEditing(), Color.WHITE, 1, Frame.Alignment.LEFT, Frame.Alignment.TOP);
			
			boolean zoomIn          = KeyboardInput.isKeyDown(KeyboardInput.KEY_I);
			boolean zoomOut         = KeyboardInput.isKeyDown(KeyboardInput.KEY_O);
			
			boolean inventory       = KeyboardInput.isKeyDown(KeyboardInput.KEY_TAB);
			
			boolean changeIndexUp   = KeyboardInput.isKeyDown(KeyboardInput.KEY_E);
			boolean changeIndexDown = KeyboardInput.isKeyDown(KeyboardInput.KEY_Q);
			
//			boolean fullscrn        = KeyboardInput.isKeyDown(KeyboardInput.KEY_F11);
			
			boolean changeCamera    = KeyboardInput.isKeyDown(KeyboardInput.KEY_C);
			
			if (Frame.wasResized())
			{
				player.center();
			}
			
//			next = KeyboardInput.next();
//			
//			if (next)
//			{
				if (inventory)
				{
					inventoryOpen = !inventoryOpen;
					
					frozen        = !frozen;
				}
				
				if (!frozen)
				{
					if (changeCamera)
					{
						player.setCentered(!player.isCentered());
					}
					
					if (changeIndexUp)
					{
						player.setEditing((player.getEditing() + 1) % 3);
					}
					else if (changeIndexDown)
					{
						player.setEditing((player.getEditing() - 1));
						
						player.setEditing(player.getEditing() < 0 ? 2 : player.getEditing());
					}
					
					if (zoomIn || zoomOut)
					{
						if (zoomIn)
						{
//								scale *= 1.1f;
							
							scale += .5f;
						}
						else if (zoomOut)
						{
//								scale /= 1.1f;
							
							scale = scale - 0.5f <= 0 ? scale : scale - 0.5f;
						}
						
						player.getQuickBar().refreshInventory();
						
						player.center();
					}
					
					for (int i = 0; i < 10; i ++)
					{
						if (KeyboardInput.isKeyDown(i + 8))
						{
							player.getQuickBar().setSelectedIndex(i);
						}
					}
					
					if (KeyboardInput.isKeyDown(7))
					{
						player.getQuickBar().setSelectedIndex(9);
					}
					
//					if (KeyboardInput.isKeyDown(KeyboardInput.KEY_M))
//					{
//						c = new Client();
//					}
//					if (KeyboardInput.isKeyDown(KeyboardInput.KEY_N))
//					{
//						c.send();
//					}
				}
//			}
			
			tick(targetFps * 2 / (float)Frame.getFps(), Frame.getDFps());
			
			if (frozen)
			{
				
			}
			else
			{
				
			}
		}
		
		oldTouchX = TouchInput.getX();
		oldTouchY = TouchInput.getY();
		
		oldWidth  = Frame.getWidth();
	}
	
	public void render()
	{
//		GL.loadIdentity();
		
//				map.finishLighting();
//				GL.setLightProperties();
		GL.beginManipulation();
		{
//					float cx = Frame.getCenterX();
//					float cy = Frame.getCenterY();
			
//					GL.translatef(cx, cy, 0);
			
			if (playingGame)
			{
				renderGame();
			}
			else
			{
				renderMenu();
			}
//					GL.translatef(-cx, -cy, 0);
		}
		GL.endManipulation();
		
//		new Thread()
//		{
//			public void run(){
//				if (currentMod == null)return;
//		synchronized (currentMod)
//		{
//		if (playingGame)
//		{
//			for (int i = 0; i < modThreads.length; i ++)
//			{
//				try
//				{
////					if (modThreads[i] == null)
////					{
////						continue;
////					}
//					
//					
//					synchronized (modThreads[i])
//					{
//						Lua.runFunction("render", i);
//						
//						modThreads[i].notify();
//					}
//				}
//				catch (ArrayIndexOutOfBoundsException ex)
//				{
////					ex.printStackTrace();
//					System.out.println("!!1") ;
//				}
//			}
//		}
//			currentMod.notify();
//		}}}.start();
	}
	
	public void tick(final float delta, final int dfps)
	{
		this.delta = delta;
		
		if (Double.isInfinite(delta) || Double.isNaN(delta) || delta > ((float)targetFps / ((float)targetFps / 12f)))
		{
			return;
		}
		
		if (!frozen)
		{
			boolean up        = KeyboardInput.isKeyDown(KeyboardInput.KEY_W) || KeyboardInput.isKeyDown(KeyboardInput.KEY_UP)    || KeyboardInput.isKeyDown(KeyboardInput.KEY_SPACE);
			boolean down      = KeyboardInput.isKeyDown(KeyboardInput.KEY_S) || KeyboardInput.isKeyDown(KeyboardInput.KEY_DOWN);
			boolean left      = KeyboardInput.isKeyDown(KeyboardInput.KEY_A) || KeyboardInput.isKeyDown(KeyboardInput.KEY_LEFT);
			boolean right     = KeyboardInput.isKeyDown(KeyboardInput.KEY_D) || KeyboardInput.isKeyDown(KeyboardInput.KEY_RIGHT);
			
//			boolean crouch    = KeyboardInput.isKeyDown(KeyboardInput.KEY_CONTROL_LEFT);
			boolean sprint    = KeyboardInput.isKeyDown(KeyboardInput.KEY_LEFT_SHIFT);
			
//			player.setCrouching(crouch);
			player.setSprinting(sprint);
			
			if (left)
			{
				player.setMove(Direction.LEFT);
			}
			else if (right)
			{
				player.setMove(Direction.RIGHT);
			}
			if (up)
			{
				player.setJump(true);
			}
			else if (down)
			{
				player.setMove(Direction.DOWN);
			}
			
//			int col[] = map.getDayCycle().getActualColor();
//			
//			int depth = (int)(-map.getY() / textureSize) * 2;
//			
//			if (depth < -30)
//			{
//				depth += 30;
//				
//				map.getDayCycle().setCurrentColor(col[0] + depth, col[1] + depth, col[2] + depth);
//			}
//			else
//			{
//				map.getDayCycle().setCurrentColor(col[0], col[1], col[2]);
//			}
			
			map.tick(delta);
			
			
			
			if (dfps % 3 == 0)
			{
				map.calculateLighting();
			}
			
//			boolean cursorMoved = (int)cursorX / (int)textureSize != (int)oldCursorX / (int)textureSize || (int)cursorY / (int)textureSize != (int)oldCursorY / (int)textureSize;
		//	cursorMoved = cursorMoved || (int)(map.getX() / textureSize) != (int)(oldMapX / textureSize) || (int)(map.getY() / textureSize) != (int)(oldMapY / textureSize);
			
			boolean cursorMoved = true;
			
			if (!TouchInput.isPressed())
			{
				mbAble[0]   = true;
				
//				blockDamage = 0;
			}
			
			if (TouchInput.isPressed())
			{
				float cursorX = (int)(TouchInput.getX() / scale / textureSize) * textureSize + (map.getX() % textureSize);
				float cursorY = (int)(TouchInput.getY() / scale / textureSize) * textureSize + (map.getY() % textureSize);
				
				cursorX = TouchInput.getX() / scale <  cursorX               ? cursorX - textureSize : cursorX;
				cursorX = TouchInput.getX() / scale >= cursorX + textureSize ? cursorX + textureSize : cursorX;
				cursorY = TouchInput.getY() / scale >= cursorY               ? cursorY + textureSize : cursorY;
				cursorY = TouchInput.getY() / scale <  cursorY               ? cursorY - textureSize : cursorY;
				
				int editX = (int)Math.round(((cursorX - map.getX()) / (float)textureSize));
				int editY = (int)Math.round(((cursorY - map.getY()) / (float)textureSize));
				
				if (cursorX != oldCursorX || cursorY != oldCursorY)
				{
					blockDamage = 0;
				}
				
				boolean breakable = true;
				
				if (map.isTile(editX, editY, player.getEditing()))
				{
					if (player.getEditing() == 0)
					{
						if (map.isTile(editX, editY, 1))
						{
							breakable = false;
						}
					}
					
					if (breakable)
					{
						Tile tile = map.getTile(editX, editY, player.getEditing());
					
						if (blockDamage >= tile.getBreakingPoint())
						{
							if (map.removeTile(editX, editY, player.getEditing(), player))
							{
								map.calculateLighting();
							}
							
							player.getQuickBar().refreshInventory();
							
							blockDamage = 0;							
							mbAble[0]   = false;
						}
						else
						{
							int offset = (int)(10 * (blockDamage / tile.getBreakingPoint()));
							
							blockDamageTexturesBuffer.setData(0, GL.addRectTextureArrayf(Tile.getTerrain(), 5 + offset, 17, 1, 1, 0, null));
							
							float power = 1;
							
							Item item = player.getItemHeld();
							
							if (item != null)
							{
								if (item.getType() == tile.getType())
								{
									power = item.getPower();
								}
							}
							
							blockDamage += power * delta;
						}
					}
				}
				else
				{
					Object obj = player.getInventory().getItem(player.getQuickBar().getSelectedIndex());
					
					Tile tile = null;
					
					if (obj instanceof Tile)
					{
						tile = (Tile)obj;
						
						if (map.addTile(editX, editY, player.getQuickBar().getSelectedIndex(), false, player))
						{
							map.calculateLighting();
						}
					}
					else
					{
						map.activate(editX, editY, player.getEditing());
					}
					
					player.getQuickBar().refreshInventory();
				}
				
				oldCursorX = cursorX;
				oldCursorY = cursorY;
			}
			
//			if (!MouseInput.isButtonDown(MouseInput.BUTTON_1) || cursorMoved)
//			{
//				mbAble[1] = true;
//			}
			
//			if (MouseInput.isButtonDown(MouseInput.BUTTON_0))
//			{
//				boolean breakable = true;
//				
//				if (map.isTile(editX, editY, player.getEditing()))
//				{
//					if (player.getEditing() == 0)
//					{
//						if (map.isTile(editX, editY, 1))
//						{
//							breakable = false;
//						}
//					}
//					
//					if (breakable)
//					{
//						Tile tile = map.getTile(editX, editY, player.getEditing());
//					
//						if (blockDamage >= tile.getBreakingPoint())
//						{
//							if (map.removeTile(editX, editY, player.getEditing(), player))
//							{
//								map.calculateLighting();
//							}
//							
//							player.getQuickBar().refreshInventory();
//							
//							blockDamage = 0;							
//							mbAble[0]   = false;
//						}
//						else
//						{
//							int offset = (int)(10 * (blockDamage / tile.getBreakingPoint()));
//							
//							blockDamageTexturesBuffer.setData(0, GL.addRectTextureArrayf(Tile.getTerrain(), 5 + offset, 17, 1, 1, 0, null));
//							
//							float power = 1;
//							
//							Item item = player.getItemHeld();
//							
//							if (item != null)
//							{
//								if (item.getType() == tile.getType())
//								{
//									power = item.getPower();
//								}
//							}
//							
//							blockDamage += power * delta;
//						}
//					}
//				}
//			}
////			else if (MouseInput.isButtonDown(MouseInput.BUTTON_1))
////			{
////				Object obj = player.getInventory().getItem(player.getQuickBar().getSelectedIndex());
////				
////				Tile tile = null;
////				
////				if (obj instanceof Tile)
////				{
////					tile = (Tile)obj;
////					
////					if (map.addTile(editX, editY, player.getQuickBar().getSelectedIndex(), false, player))
////					{
////						map.calculateLighting();
////					}
////				}
////				else
////				{
////					map.activate(editX, editY, player.getEditing());
////				}
////				
////				player.getQuickBar().refreshInventory();
////				
////				mbAble[1] = false;
////			}
//			
//			oldCursorX = cursorX;
//			oldCursorY = cursorY;
		}
		
//		synchronized (currentMod)
//		{
//			synchronized (modTicker)
//			{
//				modTicker.notify();
//			}
//		}
		
//		for (int i = 0; i < modResources.length; i ++)
//		{
//			if (modWaitTime[currentModId] <= 0)
//			{
//			new Thread()
//			{
//				public void run()
//				{
//					try
//					{
//						Lua.runFunction("tick", new Object[] { currentModId, delta, Frame.getDFps() });
//					}
//					catch(Exception ex)
//					{
//						ex.printStackTrace();
//					}
//				}
//			}.start();
//			}
//			else
//			{
//				modWaitTime[currentModId] -= Util.nanoTime - oldTime;
//				
//				modWaitTime[currentModId] = modWaitTime[currentModId] < 0 ? 0 : modWaitTime[currentModId];
//			}
//		}
		
		float cursorX = (int)(TouchInput.getX() / scale / textureSize) * textureSize + (map.getX() % textureSize);
		float cursorY = (int)(TouchInput.getY() / scale / textureSize) * textureSize + (map.getY() % textureSize);
		
		cursorX = TouchInput.getX() / scale <  cursorX               ? cursorX - textureSize : cursorX;
		cursorX = TouchInput.getX() / scale >= cursorX + textureSize ? cursorX + textureSize : cursorX;
		cursorY = TouchInput.getY() / scale >= cursorY               ? cursorY + textureSize : cursorY;
		cursorY = TouchInput.getY() / scale <  cursorY               ? cursorY - textureSize : cursorY;
		
		oldTime = Util.nanoTime;
	}
	
//	private void modTick(final int id, final float delta, final int dfps)
//	{
//		modThreads[id] = new Thread()
//		{
//			public void run()
//			{
//				while (true)
//				{
//					synchronized (modThreads[id])
//					{
//						try
//						{
//							Lua.runFunction("tick", new Object[] { id, delta, dfps });
//							
//							modThreads[id].wait();
//						}
////						catch (InterruptedException e)
////						{
////							e.printStackTrace();
////						}
//						catch (IllegalMonitorStateException e)
//						{
//							System.out.println("!!4");
//						}
//						catch (ArrayIndexOutOfBoundsException ex)
//						{
////							System.out.println("!!2 " + ex.toString());
//						}
//						catch(Exception ex)
//						{
//							ex.printStackTrace();
//						}
//					}
//				}
//			}
//		};
//		
//		modThreads[id].start();
//	}
	
	private void renderMenu()
	{
		GL.scalef(3, 3, 1);
		
		if (mainMenu != null)
		{
			mainMenu.render();
		}
	}
	
	public void startMainMenu()
	{
		mainMenu = new MainMenu(this, activity);
	}
	
	public void startGame()
	{
		mbAble      = new boolean[2];
		mbAble[0]   = true;
		mbAble[1]   = true;
		
		oldMapX     = .000001f;
		
		scale       = 4f;
		
		textureSize = 16;
		
		blockDamageVerticesBuffer = new VerticesBuffer(4 * 2, 2);
		blockDamageTexturesBuffer = new LightBuffer(4 * 2, 2);
		
		blockDamageVerticesBuffer.setData(0, GL.addRectVertexArrayf(0, 0, textureSize, textureSize, 0, null));
		
		Texture joyTop    = new Texture(activity.getResources(), R.drawable.joytop);
		Texture joyBottom = new Texture(activity.getResources(), R.drawable.joybottom);
		
		joystick = new Joystick(joyTop, joyBottom);
		Frame.add(joystick);
		
		map    = new Map();
		
		player = new Player(map, activity.getResources());
		
		for (int y = 0; y >= -1; y --)
		{
			for (int x = -1; x < 2; x ++)
			{
				map.addChunk(x, y);
				map.getChunk(x, y).generateTerrain(0, x >= 0 ? map.getChunk(x - 1, y).getRightY() : 6, y == 0 ? 0 : y < 0 ? -1 : 1, null);
			}
		}
		
		int brickX = 0;
		
		int brickY = 0;
		
		for (int i = 0;; i ++)
		{
			if (!map.isTile(brickX, i, 1))
			{
				brickY = (i) * textureSize;
				
				break;
			}
		}
		
		player.setLocation(brickX * textureSize, brickY + 100);
		
		player.center();
		
//		Lua.loadScript("res/scripts/lua/ModLoader.lua");
//		
//		Lua.runFunction("setPlayer", player);
//		Lua.runFunction("setMap", map);
//		Lua.runFunction("setP1xeland", this);
//		
//		File dir = new File("res/scripts/lua/");
//		
//		String fileNames[] = dir.list();
//		
//		ArrayList<String> res = new ArrayList<String>();
//		
//		for (String name : fileNames)
//		{
//			if (name.endsWith(".lua") && !name.equals("ModLoader.lua"))
//			{
//				res.add(name.substring(0, name.length() - 4));
//			}
//		}
//		
//		modResources = ArrayUtil.toStringArray(res);
//		
//		Lua.runFunction("start");
//		
//		Lua.runFunction("setResources", new Object[] { modResources, modResources.length });
//		
//		modCalled   = new boolean[modResources.length];
//		
//		modReady    = new boolean[modResources.length];
//		
//		modWaitTime = new long[modResources.length];
//		
//		modThreads  = new Thread[modResources.length];
		
		oldTime     = Util.nanoTime;
		
		map.pollChunks();
		
		map.calculateLighting();
		
		player.giveObject(Tile.TORCH, 640);
		player.giveObject(Item.DIAMOND_PICKAXE);
		player.giveObject(Item.DIAMOND_AXE);
		player.giveObject(Item.DIAMOND_SHOVEL);
		player.giveObject(Tile.GLASS, 640);
		player.giveObject(Tile.WOODEN_PLANKS, 640);
		player.giveObject(Tile.LADDER, 640);
		
		player.getQuickBar().refreshInventory();
		
		player.setEditing(1);
		
//		new SpriteSheet(activity.getResources(), R.drawable.minecraft, 36, 18);
		
		playingGame = true;
		
		if (mainMenu != null)
		{
			mainMenu.destroy();
		}
		
		mainMenu = null;
		
//		if (modResources.length > 0)
//		{
//			currentMod = new Thread()
//			{
//				public void run()
//				{
//					while (true)
//					{
//						currentModId ++;
//						
//						currentModId = currentModId % modResources.length;
//						
//						Lua.runFunction("tick", new Object[] { currentModId, delta, Frame.getDFps() });
//						
////						try
////						{
////							synchronized (currentMod)
////							{
////								currentMod.wait();
////							}
////						}
////						catch (InterruptedException ex)
////						{
////							ex.printStackTrace();
////						}
//					}
//				}
//			};
//			
//			currentMod.start();
//		}
//		
//		for (int i = 0; i < modThreads.length; i ++)
//		{
//			modTick(i, delta, Frame.getDFps());
//		}
	}
	
	private void renderGame()
	{
		GL.beginManipulation();
		{
			GL.translatef(35, 90, 0);
			
			GL.scalef(10, 10, 1);
			
			joystick.render();
		}
		GL.endManipulation();
		
		GL.scalef(scale, scale, 1);

		map.render();

		player.render();
		
//		if (Distance.points(cursor.x + textureSize / 2, cursor.y + textureSize / 2, player.getScreenX() + (player.getWidth() / 2f), player.getScreenY() + (player.getHeight() / 2f)) < player.getReach() * textureSize)
//		{
//			cursor.render();
//			
//			if (blockDamage > 0 && blockDamage < 9999)
//			{
//				GL.beginManipulation();
//				{
//					GL.translatef(cursor.x, cursor.y, player.getEditing() == 2 ? -5 : -9.9f);
//					
//					GL.renderQuads(blockDamageVerticesBuffer, blockDamageTexturesBuffer, Tile.getTerrain(), 0, 1);
//				}
//				GL.endManipulation();
//			}
//		}
		
		if (!inventoryOpen)
		{
			player.getQuickBar().render();
		}
		else
		{
			player.getInventory().render();
		}
		
//		if (map.getX() != oldMapX || map.getY() != oldMapY)
//		{
//			GL.setLightLocation(map.getX() + 48 * 5, map.getY() + 48, -6);
//		}
		
		oldMapX = map.getX();
		oldMapY = map.getY();
	}
	
//	public void runTask(final Task task)
//	{
////		synchronized (currentMod)
////		{
////			try
////			{
////				currentMod.wait();
////			}
////			catch (InterruptedException e)
////			{
////				e.printStackTrace();
////			}
//			
//			currentMod = new Thread()
//			{
//				public void run()
//				{
//	//				synchronized (modTicker)
//	//				{
//						task.run(0);
//	//				}
//	//				System.out.println("ASdf");
//				}
//			};
//			
//			currentMod.start();
////		}
//	}
//	
//	public void waitMod(int id, long time)
//	{
//		modWaitTime[id - 1] += time;
//	}
	
	public boolean isFrozen()
	{
		return frozen;
	}
	
	public void setFrozen(boolean frozen)
	{
		this.frozen = frozen;
	}
	
	public float getBlockDamage()
	{
		return blockDamage;
	}
	
	public void setBlockDamage(float blockDamage)
	{
		this.blockDamage = blockDamage;
	}
	
	public static final String getGameTitle()
	{
		return GAME_TITLE;
	}
	
	public static final String getVersion()
	{
		return VERSION;
	}
	
	public static float getScale()
	{
		return scale;
	}
	
	public static boolean nextKey()
	{
		return next;
	}
	
	public void setActivity(Activity activity)
	{
		this.activity = activity;
	}

//	@Override
//	public void onMouseMoved()
//	{
//		float cursorX = (int)(MouseInput.getX() / scale / textureSize) * textureSize + (map.getX() % textureSize);
//		float cursorY = (int)(MouseInput.getY() / scale / textureSize) * textureSize + (map.getY() % textureSize);
//		
//		cursorX = MouseInput.getX() / scale <  cursorX               ? cursorX - textureSize : cursorX;
//		cursorX = MouseInput.getX() / scale >= cursorX + textureSize ? cursorX + textureSize : cursorX;
//		cursorY = MouseInput.getY() / scale >= cursorY               ? cursorY + textureSize : cursorY;
//		cursorY = MouseInput.getY() / scale <  cursorY               ? cursorY - textureSize : cursorY;
//		
//		cursor.setLocation(cursorX, cursorY);
//		
//		if (cursorX != oldCursorX || cursorY != oldCursorY)
//		{
//			blockDamage = 0;
//		}
//	}
//
//	@Override
//	public void onMouseDragged(int bottonId)
//	{
//		
//	}
//
//	@Override
//	public void onMouseClicked(int buttonId)
//	{
//		
//	}
//
//	@Override
//	public void onMouseReleased(int buttonId)
//	{
//		if (buttonId == 0)
//		{
//			blockDamage = 0;
//		}
//	}
//
//	@Override
//	public void onMousePressed(int buttonId)
//	{
//		float cursorX = (int)(MouseInput.getX() / scale / textureSize) * textureSize + (map.getX() % textureSize);
//		float cursorY = (int)(MouseInput.getY() / scale / textureSize) * textureSize + (map.getY() % textureSize);
//		
//		cursorX = MouseInput.getX() / scale <  cursorX               ? cursorX - textureSize : cursorX;
//		cursorX = MouseInput.getX() / scale >= cursorX + textureSize ? cursorX + textureSize : cursorX;
//		cursorY = MouseInput.getY() / scale >= cursorY               ? cursorY + textureSize : cursorY;
//		cursorY = MouseInput.getY() / scale <  cursorY               ? cursorY - textureSize : cursorY;
//		
//		cursor.setLocation(cursorX, cursorY);
//	
//		int editX = (int)Math.round(((cursorX - map.getX()) / (float)textureSize));
//		int editY = (int)Math.round(((cursorY - map.getY()) / (float)textureSize));
//		
//		if (cursorX != oldCursorX || cursorY != oldCursorY)
//		{
//			blockDamage = 0;
//		}
//		
//		if (buttonId == 0)
//		{
//			boolean breakable = true;
//			
//			if (map.isTile(editX, editY, player.getEditing()))
//			{
//				if (player.getEditing() == 0)
//				{
//					if (map.isTile(editX, editY, 1))
//					{
//						breakable = false;
//					}
//				}
//				
//				if (breakable)
//				{
//					Tile tile = map.getTile(editX, editY, player.getEditing());
//				
//					if (blockDamage >= tile.getBreakingPoint())
//					{
//						if (map.removeTile(editX, editY, player.getEditing(), player))
//						{
//							map.calculateLighting();
//						}
//						
//						player.getQuickBar().refreshInventory();
//						
//						blockDamage = 0;							
//						mbAble[0]   = false;
//					}
//					else
//					{
//						int offset = (int)(10 * (blockDamage / tile.getBreakingPoint()));
//						
//						blockDamageTexturesBuffer.setData(0, GL.addRectTextureArrayf(Tile.getTerrain(), 5 + offset, 17, 1, 1, 0, null));
//						
//						float power = 1;
//						
//						Item item = player.getItemHeld();
//						
//						if (item != null)
//						{
//							if (item.getType() == tile.getType())
//							{
//								power = item.getPower();
//							}
//						}
//						
//						blockDamage += power * delta;
//					}
//				}
//			}
//		}
//		
//		if (buttonId == 1)
//		{
//			Object obj = player.getInventory().getItem(player.getQuickBar().getSelectedIndex());
//			
//			Tile tile = null;
//			
//			if (obj instanceof Tile)
//			{
//				tile = (Tile)obj;
//				
//				if (map.addTile(editX, editY, player.getQuickBar().getSelectedIndex(), false, player))
//				{
//					map.calculateLighting();
//				}
//			}
//			else
//			{
//				map.activate(editX, editY, player.getEditing());
//			}
//			
//			player.getQuickBar().refreshInventory();
//		}
//		
//		oldCursorX = cursorX;
//		oldCursorY = cursorY;
//	}
}
