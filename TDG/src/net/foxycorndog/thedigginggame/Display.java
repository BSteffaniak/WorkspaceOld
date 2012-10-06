package net.foxycorndog.thedigginggame;

import java.io.IOException;
import java.nio.ByteOrder;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.newdawn.slick.Color;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

import net.foxycorndog.presto2d.PrestoGL2D;
import net.foxycorndog.presto3d.graphics.PrestoGL3D;
import net.foxycorndog.presto2d.util.SpriteSheet;
import net.foxycorndog.presto3d.util.FrameLoop;
import net.foxycorndog.presto3d.util.FrameTask;
import net.foxycorndog.thedigginggame.actors.Actor;
import net.foxycorndog.thedigginggame.actors.Player;
import net.foxycorndog.thedigginggame.map.Map;
import net.foxycorndog.thedigginggame.tiles.Tile;

import static net.foxycorndog.thedigginggame.TheDiggingGame.*;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_READ_WRITE;
import static org.lwjgl.opengl.GL15.glMapBuffer;
import static org.lwjgl.opengl.GL15.glUnmapBuffer;

public abstract class Display
{
	private int            width, height;
	private int            dfps,  fps;
	private int            cursorX, cursorY, cursorZ;
	
	private long           newTime, oldTime;
	
	private TheDiggingGame tdg;
	
	Texture                texture;
	
	SpriteSheet            sprites;
	
	public Display(int width, int height, TheDiggingGame tdg)
	{
		this.width  = width;
		this.height = height;
		
		this.tdg = tdg;
		
//		createFrame(width, height, TheDiggingGame.GAME_TITLE);
	}
	
	public void init()
	{
		PrestoGL2D.initBasicView();
		
		newTime = System.currentTimeMillis();
		oldTime = newTime;
		
		new FrameLoop().start(-1, new FrameTask()
			{
				public void run()
				{
					updateFps();
					
					org.lwjgl.opengl.Display.setTitle(TheDiggingGame.GAME_TITLE + " FPS: " + fps + " : " + String.format("%.1f", tdg.getPlayer().getOriginRelativeX() / tileSize) + ", " + String.format("%.1f", tdg.getPlayer().getOriginRelativeY() / tileSize));
					
					render();
					
					keyboardInput();
					keyboardInput();
					keyboardInput();
					keyboardInput();
					
					mouseInput();
					
					frameInput();
					
					playerStuff();
					playerStuff();
					playerStuff();
					playerStuff();
				}
			}
		);
	}
	
	public abstract void render();
	
	public void renderCursor()
	{
		int bs = (int)tileSize;
		
		int offsetX = getMap().getX() < 0 ? 0 : -bs;
//		int offsetY = map.getX() < 0 ? 1 : 0;
		
		cursorX    = (int)(((Mouse.getX() / bs) * bs)      + (getMap().getX() % bs)) + offsetX;
		cursorX    = Mouse.getX() > cursorX + bs ? cursorX + bs : cursorX;
		
		cursorY    = (int)(((Mouse.getY() / bs) * bs + bs) + (getMap().getY() % bs));// + offsetY;
		cursorY    = Mouse.getY() > cursorY ? cursorY + bs : cursorY;
		cursorY    = Mouse.getY() < cursorY - bs ? cursorY - bs : cursorY;
		
		cursorZ    = 1;
		
		int width  = bs;
		int height = bs;
		
		int thickness = 1;
		
		glPushMatrix();
		{
			glBegin(GL_QUADS);
			{
				glVertex3f(cursorX,                     cursorY - height,             cursorZ);
				glVertex3f(cursorX,                     cursorY,                      cursorZ);
				glVertex3f(cursorX + thickness,         cursorY,                      cursorZ);
				glVertex3f(cursorX + thickness,         cursorY - height,             cursorZ);
				
				glVertex3f(cursorX,                     cursorY - thickness,          cursorZ);
				glVertex3f(cursorX,                     cursorY,                      cursorZ);
				glVertex3f(cursorX + width,             cursorY,                      cursorZ);
				glVertex3f(cursorX + width,             cursorY - thickness,          cursorZ);
				
				glVertex3f(cursorX - thickness + width, cursorY - height,             cursorZ);
				glVertex3f(cursorX - thickness + width, cursorY,                      cursorZ);
				glVertex3f(cursorX + width,             cursorY,                      cursorZ);
				glVertex3f(cursorX + width,             cursorY - height,             cursorZ);
				
				glVertex3f(cursorX,                     cursorY - height, cursorZ);
				glVertex3f(cursorX,                     cursorY + thickness - height,             cursorZ);
				glVertex3f(cursorX + width,             cursorY + thickness - height,             cursorZ);
				glVertex3f(cursorX + width,             cursorY - height, cursorZ);
			}
			glEnd();
		}
		glPopMatrix();
	}
	
	public void playerStuff()
	{
		if (!tdg.getPlayer().getJumping())
		{
			tdg.getPlayer().setAirborn(tdg.getPlayer().move(Actor.DOWN));
			
			if (!tdg.getPlayer().getAirborn())
			{
				tdg.getPlayer().setAlreadyJumped(false);
			}
		}
		else
		{
			tdg.getPlayer().jump();
		}
		
//		map.recalculateTerrain(tdg.getPlayer());
	}
	
	public void keyboardInput()
	{
//		while (Keyboard.next())
//		{
//			int key = Keyboard.getEventKey();
//			
//			System.out.println("You pressed the " + Keyboard.getKeyName(key) + " key.");
//		}
		
		boolean jump  = Keyboard.isKeyDown(Keyboard.KEY_W) || Keyboard.isKeyDown(Keyboard.KEY_UP) || Keyboard.isKeyDown(Keyboard.KEY_SPACE);
		boolean left  = Keyboard.isKeyDown(Keyboard.KEY_A) || Keyboard.isKeyDown(Keyboard.KEY_LEFT);
		boolean right = Keyboard.isKeyDown(Keyboard.KEY_D) || Keyboard.isKeyDown(Keyboard.KEY_RIGHT);
		
		if (jump)
		{
			if (!tdg.getPlayer().getAirborn())
			{
				tdg.getPlayer().jump();
			}
			
			if (!left && !right)
			{
				tdg.getPlayer().setFacing(Actor.FRONT);
			}
		}
		if (left)
		{
			tdg.getPlayer().move(Actor.LEFT);
			
			tdg.getPlayer().setFacing(Actor.LEFT);
		}
		if (right)
		{
			tdg.getPlayer().move(Actor.RIGHT);
			
			tdg.getPlayer().setFacing(Actor.RIGHT);
		}
		
		if (!jump && !left && !right)
		{
			if (tdg.getPlayer().getFacing() != Actor.FRONT)
			{
			 	tdg.getPlayer().setFacing(Actor.FRONT);
			}
		}
	}
	
	public void mouseInput()
	{
		int bs = (int)tileSize;
		
		int offsetX = getMap().getX() < 0 ? 1 : 0;
		int offsetY = getMap().getY() < 0 ? 0 : 1;
		
		if (Mouse.next())
		{
			if (Mouse.isButtonDown(1))
			{
				getMap().addBlock(cursorX / bs - (int)((getMap().getX() / bs)) + offsetX, cursorY / bs - 1 - (int)((getMap().getY() / bs)), Tile.getTile(Tile.LADDER), false);
			}
			if (Mouse.isButtonDown(0))
			{
				getMap().removeBlock(cursorX / bs - (int)((getMap().getX() / bs)) + offsetX, cursorY / bs - offsetY - (int)((getMap().getY() / bs)));
			}
		}
	}
	
	public void frameInput()
	{
		if (org.lwjgl.opengl.Display.wasResized())
		{
			width  = org.lwjgl.opengl.Display.getWidth();
			height = org.lwjgl.opengl.Display.getHeight();
			PrestoGL2D.resetBasicView();
		}
	}
	
	public void updateFps()
	{
		dfps ++;
		
		newTime = System.currentTimeMillis();
		
		if (newTime > oldTime + 1000)
		{
			fps = dfps;
			
			dfps = 0;
			
			oldTime = newTime;
		}
	}
	
	public int getWidth()
	{
		return width;
	}
	
	public int getHeight()
	{
		return height;
	}
	
	public Map getMap()
	{
		return tdg.getMap();
	}
}