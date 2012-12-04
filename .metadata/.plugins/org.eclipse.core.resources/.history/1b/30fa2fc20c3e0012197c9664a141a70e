package net.foxycorndog.shootcrap.map;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import net.foxycorndog.jdoogl.GL;
import net.foxycorndog.jdoogl.image.Image;
import net.foxycorndog.jdoutil.LightBuffer;
import net.foxycorndog.jdoutil.VerticesBuffer;
import net.foxycorndog.shootcrap.actor.Actor;
import net.foxycorndog.shootcrap.tile.Tile;

public class Map
{
	private int              width, height;
	
	private float            x, y;
	
	private LightBuffer      backgroundTextures, foregroundTextures, actorTextures;
	
	private VerticesBuffer   backgroundVertices, foregroundVertices, actorVertices;
	
	private int              pixels[];
	
	private Tile             tiles[];
	
	private Actor            actors[];
	
	public Map(String location)
	{
		BufferedImage bImg = null;
		
		try
		{
			bImg = ImageIO.read(new File(location));
		}
		catch (IOException e)
		{
			e.printStackTrace();
			System.exit(1);
		}
		
		BufferedImage map = new BufferedImage(bImg.getWidth(), bImg.getHeight(), BufferedImage.TYPE_INT_RGB);
		
		Graphics2D g = map.createGraphics();
		g.drawImage(bImg, 0, 0, null);
		g.dispose();
		
		this.width  = map.getWidth();
		this.height = map.getHeight();
		
		pixels = ((DataBufferInt)map.getRaster().getDataBuffer()).getData();
		
		tiles  = new Tile[width * height];
		
		actors = new Actor[1000];
		
		backgroundVertices = new VerticesBuffer(4 * 2 * width * height, 2);
		foregroundVertices = new VerticesBuffer(4 * 2 * width * height, 2);
		
		actorVertices      = new VerticesBuffer(4 * 2 * actors.length, 2);
		actorTextures      = new VerticesBuffer(4 * 2 * actors.length, 2);
		
		backgroundTextures = new LightBuffer(4 * 2 * width * height);
		foregroundTextures = new LightBuffer(4 * 2 * width * height);
		
		for (int y = 0; y < height; y ++)
		{
			for (int x = 0; x < width; x ++)
			{
				Tile tile = Tile.getTile(pixels[x + (height - y - 1) * map.getWidth()]);
				
				tiles[x + y * map.getWidth()] = tile;
				
				if (tile != null)
				{
					backgroundVertices.addData(tile.getVertices(x, y));
					backgroundTextures.addData(tile.getTextureCoords());
				}
			}
		}
		
		backgroundVertices.genIndices(GL.QUADS, null);
		actorVertices.genIndices(GL.QUADS, null);
	}
	
	public float getX()
	{
		return x;
	}
	
	public float getY()
	{
		return y;
	}
	
	public void move(float dx, float dy)
	{
		x += dx;
		y += dy;
	}
	
	public void addActor(Actor actor)
	{
		actorVertices.addData(actor.getVertices());
		actorTextures.addData(actor.getTextures());
		
		actors[actor.getId()] = actor;
	}
	
	public void render()
	{
		GL.beginManipulation();
		{
			GL.translatef(x, y, 0);
			
			renderBackground();
			renderActors();
			renderForeground();
		}
		GL.endManipulation();
	}
	
	public void renderBackground()
	{
		GL.renderQuads(backgroundVertices, backgroundTextures, null, null, Tile.getSprites(), 0, width * height, null);
	}
	
	public void renderForeground()
	{
		
	}
	
	public void renderActors()
	{
		synchronized (actors)
		{
			for (int i = 0; i < actors.length; i ++)
			{
				synchronized (actors)
				{
					Actor actor = actors[i];
					
					if (actor != null)
					{
						actor.render();
					}
				}
			}
		}
	}
}
