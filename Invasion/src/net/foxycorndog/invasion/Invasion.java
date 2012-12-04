package net.foxycorndog.invasion;

import java.util.ArrayList;

import net.foxycorndog.invasion.entities.Alien;
import net.foxycorndog.invasion.entities.Entity;
import net.foxycorndog.invasion.entities.Player;
import net.foxycorndog.invasion.entities.Ship;
import net.foxycorndog.invasion.entities.Ship.PowerUp;
import net.foxycorndog.invasion.levels.Level;
import net.foxycorndog.jdoogl.Color;
import net.foxycorndog.jdoogl.GL;
import net.foxycorndog.jdoogl.components.Frame;
import net.foxycorndog.jdoogl.input.KeyboardInput;
import net.foxycorndog.jdoutil.Intersects;
import net.foxycorndog.jdoogl.geometry.Point;

public class Invasion
{
	private static long lastShot         = System.nanoTime();
	private static long lastOpponentShot = System.nanoTime();
	private static long lastMove         = System.nanoTime();
	
	private static int  lastMoveCount = 0;
	private static int  scale;
	
	private static Player player;
	
	private static int score, level;
	
	private static boolean movingLeft;
	
	public  static int               fire;
	
	public  static int               shotsPerSecond;
	
	public  static ArrayList<Entity> entities;
	
	public  static Ship aliens[];
	
	public  static Level             currentLevel;
	
	public  static void main(String args[])
	{
//		Invasion invasion = new Invasion();
		
		entities = new ArrayList<Entity>();
		
		Invasion.createGame();
		
//		soundManager = new SoundManager();
//		
//		fire = soundManager.addSound("res/audio/test.wav");
		
//		invasion.startGame();
		
//		soundManager.playSound(fire);
	}
	
	public static void createGame()
	{
		scale = 5;
		
		new Frame((int)(640f * (scale / 5f)), (int)(512f * (scale / 5f)), "Invasion", null)
		{
			@Override
			public void init()
			{
				player = new Player();
				
				startGame();
				
				entities.add(player);
			}
			
			@Override
			public void loop()
			{
				if (getFps() == 0)
				{
					return;
				}
				
				tick(KeyboardInput.getKeys(), 60f / (float)(getFps()));
			}

			@Override
			public void render()
			{
				renderText(0, 0, "FPS: " + getFps(), Color.BLACK, 2 * (scale / 5f), Alignment.RIGHT, Alignment.TOP);
				renderText(0, 0, "Score: " + score,  Color.BLACK, 2 * (scale / 5f), Alignment.LEFT,  Alignment.TOP);
				
				synchronized (entities)
				{
					for (int i = entities.size() - 1; i >= 0; i --)
					{
						synchronized (entities)
						{
							entities.get(i).render();
						}
					}
				}
				
			}
		}.startLoop(60);
	}
	
	public static void startGame()
	{
		score = 0;
		
		player.setLocation((Frame.getWidth() / 2) - (player.getScaledWidth() / 2), 0);
		
//		player.addPowerUp(PowerUp.RAPID_SHOOT);
//		player.addPowerUp(PowerUp.SUPER_SPEED);
//		player.addPowerUp(PowerUp.DOUBLE_FORCE);
		
		level = 0;
		
		currentLevel = Levels.level[level];
		currentLevel.load();
		
		shotsPerSecond = 1;
	}
	
	public static void tick(boolean keys[], float delta)
	{
		boolean up         = keys[KeyboardInput.KEY_W] || keys[KeyboardInput.KEY_UP];
		boolean down       = keys[KeyboardInput.KEY_S] || keys[KeyboardInput.KEY_DOWN];
		boolean left       = keys[KeyboardInput.KEY_A] || keys[KeyboardInput.KEY_LEFT];
		boolean right      = keys[KeyboardInput.KEY_D] || keys[KeyboardInput.KEY_RIGHT];
		
		boolean shoot      = keys[KeyboardInput.KEY_SPACE];
		
		if (shoot && System.nanoTime() > lastShot + 1000000000l / shotsPerSecond)
		{
			player.shoot();
			
			lastShot = System.nanoTime();
		}
		
		if (up)
		{
			player.move(0, .8f * delta * scale);
		}
		if (left)
		{
			player.move(-.8f * delta * scale, 0);
		}
		if (down)
		{
			player.move(0, -.8f * delta * scale);
		}
		if (right)
		{
			player.move(.8f * delta * scale, 0);
		}
		
		for (int i = 0; i < entities.size(); i ++)
		{
			for (int j = i + 1; j < entities.size(); j ++)
			{
				Entity e1 = entities.get(i);
				Entity e2 = entities.get(j);
				
				int x1      = (int)e1.getX();
				int y1      = (int)e1.getY();
				int width1  = (int)e1.getScaledWidth();
				int height1 = (int)e1.getScaledHeight();
				
				int x2      = (int)e2.getX();
				int y2      = (int)e2.getY();
				int width2  = (int)e2.getScaledWidth();
				int height2 = (int)e2.getScaledHeight();
				
				if (Intersects.rectangles(x1, y1, width1, height1, x2, y2, width2, height2))
				{
					e1.onCollision(e2);
					e2.onCollision(e1);
				}
			}
		}
		
		boolean finished = true;
		
//		if (System.nanoTime() > lastOpponentShot + 1000000000l / (level + 1))
//		{
			for (Ship alien : aliens)
			{
				if (alien != null)
				{
					finished = false;
					
					if (alien.inFrontRow() && (int)(Math.random() *(800 - level * 150 < 0 ? 0 : (800 - level * 150))) == 0)
					{
						alien.shoot();
					}
				}
			}
			
			lastOpponentShot = System.nanoTime();
//		}
//		else
//		{
//			finished = false;
//		}
		
		if (System.nanoTime() > lastMove + 1000000000l / 12)
		{
			if (movingLeft)
			{
				if (currentLevel.getGroupX() <= 0)
				{
					movingLeft = false;
					
					moveAliens(0, -player.getScaledHeight() / 2);
				}
				else
				{
					moveAliens(-Invasion.getScale(), 0);
				}
			}
			else
			{
				if (currentLevel.getGroupX() + currentLevel.getGroupWidth() >= Frame.getWidth())
				{
					movingLeft = true;
					
					moveAliens(0, -player.getScaledHeight() / 2);
				}
				else
				{
					moveAliens(Invasion.getScale(), 0);
				}
			}
			
			lastMove = System.nanoTime();
		}
		
		if (finished)
		{
			if (level + 1 >= Levels.level.length)
			{
				currentLevel = Levels.level[Levels.level.length - 1];
				
				level ++;
			}
			else
			{
				currentLevel = Levels.level[++ level];
			}
			
			currentLevel.load();
		}
		
		if (KeyboardInput.next() && KeyboardInput.isKeyDown(KeyboardInput.KEY_M))
		{
			System.out.println(currentLevel.getGroupX() + ", " + currentLevel.getGroupWidth());
		}
	}
	
	public static void addScore(int amount)
	{
		score += amount;
	}
	
	static class Levels
	{
		public static Level level[] = new Level[]
		{
			new Level(8, 1),
			new Level(9, 2),
			new Level(10, 3),
			new Level(11, 4)
		};
	}
	
	public static void removeEntity(Entity entity)
	{
		entity.setRenderable(false);
		
		entities.remove(entity);
		
		if (entity instanceof Ship)
		{
			for (int i = 0; i < aliens.length; i ++)
			{
				if (aliens[i] == entity)
				{
					aliens[i] = null;
				}
			}
		}
		
		entity = null;
	}
	
	public static void removeEntity(int index)
	{
		entities.get(index).setRenderable(false);
		
		entities.remove(index);
		
		if (entities.get(index) instanceof Ship)
		{
			for (int i = 0; i < aliens.length; i ++)
			{
				if (aliens[i] == entities.get(index))
				{
					aliens[i] = null;
				}
			}
		}
		
//		entity = null;
	}
	
	public static void moveAliens(float dx, float dy)
	{
		for (int i = 0; i < aliens.length; i ++)
		{
			Ship alien = aliens[i];
			
			if (alien != null)
			{
				alien.move(dx, dy);
			}
		}
	}
	
	public static float getScale()
	{
		return scale;
	}
}