package net.neonseal.idk.maps.buildings;

import static net.neonseal.idk.Idk.OBJECT_SIZE;
import static net.neonseal.idk.Idk.VERTEX_SIZE;
import static net.neonseal.idk.Idk.tileSize;
import net.foxycorndog.jdoogl.GL;
import net.foxycorndog.jdoogl.image.imagemap.SpriteSheet;
import net.foxycorndog.jdoogl.util.Intersection;
import net.foxycorndog.jdoogl.util.Intersects;
import net.foxycorndog.jdoogl.util.LightBuffer;
import net.neonseal.idk.Idk;
import net.neonseal.idk.animatedobject.actors.Actor;
import net.neonseal.idk.animatedobject.actors.Rogue;
import net.neonseal.idk.animatedobject.actors.player.Player;

public class Building
{
//	private byte        spriteX, spriteY;
	private byte        width, height;
	
	private float       x, y;
	
	private LightBuffer      verticesBuffer, texturesBuffer;
	
	private BuildingMap map;
	
	private String      name;
	
	private static SpriteSheet buildings;
	
	/**
	* Creates a new Building used for holding a map, and the
	* coordinates of it in the sprite-sheet. Along with its
	* relative width and height.
	* 
	* @param spriteX The x position of the house on the sprite-sheet
	* 		in pixels divided by 10.
	* @param spriteY The y position of the house on the sprite-sheet
	* 		in pixels divided by 10.
	* @param width The houses width in pixels divided by 10.
	* @param height The houses height in pixels divided by 10.
	* @param map The map that the house is located in.
	*/
	public Building(byte spriteX, byte spriteY, byte width, byte height, BuildingMap map)
	{
//		this.spriteX  = spriteX;
//		this.spriteY  = spriteY;
		this.width    = width;
		this.height   = height;
		
		this.map = map;
		
		init(spriteX, spriteY, width, height);
	}
	
	/**
	* The initialization method for the Building class. Creates
	* the visual aspects of the building, such as the background
	* and foreground objects (if any).
	* 
	* @param spriteX The x position of the house on the sprite-sheet
	* 		in pixels divided by 10.
	* @param spriteY The y position of the house on the sprite-sheet
	* 		in pixels divided by 10.
	* @param width The houses width in pixels divided by 10.
	* @param height The houses height in pixels divided by 10.
	*/
	private void init(byte spriteX, byte spriteY, byte width, byte height)
	{
		verticesBuffer = new LightBuffer(OBJECT_SIZE);
//		verticesBuffer.setBuffer(BufferUtils.createFloatBuffer(OBJECT_SIZE));
//		verticesBuffer.init();
		
		texturesBuffer = new LightBuffer(OBJECT_SIZE);
//		texturesBuffer.setBuffer(BufferUtils.createFloatBuffer(OBJECT_SIZE));
//		texturesBuffer.init();
		
//		float vertices[] = verticesBuffer.getElements();
//		float textures[] = texturesBuffer.getElements();
		
		verticesBuffer.addData(GL.addRectVertexArrayf(0, 0, -8, width * tileSize, height * tileSize, 0, null));
		texturesBuffer.addData(GL.addRectTextureArrayf(buildings, spriteX, spriteY, width, height, 0, null));
		
		verticesBuffer.refreshData();
		texturesBuffer.refreshData();
		
//		map = new BuildingMap(this, idk);
	}
	
	/**
	* The static initialization method. Used to create the sprite-sheet
	* that is used in every building.
	* 
	* Must be called before any Building objects are made!
	*/
	public static void init()
	{
		buildings = new SpriteSheet(Idk.prefix + "res/images/map/buildings/Buildings.png", "PNG", 50, 50, true);
	}
	
	/**
	* Method used for rendering the outside of the building.
	* Renders at the set position of the building.
	*/
	public void render()
	{
		float dimensions[] = Idk.getViewDimensions();
		
		float xo  = dimensions[0];
		float yo  = dimensions[1];
		
		float wid = dimensions[2];
		float hei = dimensions[3];
		
		if (!Intersects.rectangles((int)x, (int)y, (int)(width * tileSize), (int)(height * tileSize), (int)xo, (int)yo, (int)wid, (int)hei))
		{
			return;
		}
		
		GL.beginManipulation();
		{
			GL.translatef(x, y, 0);
			
			GL.beginTextureDraw(texturesBuffer.getId(), 2);
			GL.beginVertexDraw (verticesBuffer.getId(), VERTEX_SIZE);
			
			buildings.bind();
			
			GL.drawQuads(0, 1);
			
			GL.endVertexDraw();
			GL.endTextureDraw();
		}
		GL.endManipulation();
	}
	
	/**
	* Checks whether if there would be a collision of the actor
	* is to move the specified amount. If there is a collision, then
	* it returns the amount that the player is able to move without
	* colliding, or else it just returns null.
	* 
	* @param actor The actor to test collisions on.
	* @param dx The amount of x position the actor is trying to move.
	* @param dy The amount of y position the actor is trying to move.
	* @return if there is a collision, then the amount that the actor
	* 		is able to move, or else it will return null.
	*/
	public float[] checkCollision(Actor actor, float dx, float dy)
	{
		float ats = dy + actor.getAbsoluteY();
//		float abs = dy + actor.getAbsoluteY();
//		float ars = dx + actor.getAbsoluteX() + actor.getWidth();
		float als = dx + actor.getAbsoluteX();
		float aw  = actor.getScaledWidth();
		float ah  = actor.getScaledHeight();
		
		float bts = 0;
//		float bbs = 0;
//		float brs = 0;
		float bls = 0;
		float bw  = 0;
		float bh  = 0;
		
//		int   xx  = 0;
//		int   yy  = 0;
//		
//		byte  id  = 0;
		
//		System.out.println(als + ", " + ats);
		
		if (actor instanceof Player || actor instanceof Rogue)
		{
			ah /= 2;
		}
		
		bts = y - tileSize;
		bls = x - tileSize;
		bw  = getScaledWidth();
		bh  = getScaledHeight();
		
		if (Intersects.rectangles((int)als, (int)ats, (int)aw, (int)ah, (int)bls, (int)bts, (int)bw, (int)bh))
		{
			float intersections[] = Intersection.rectangles((int)als, (int)ats, (int)aw, (int)ah, (int)bls, (int)bts, (int)bw, (int)bh);
			
			float xo = intersections[2];
			float yo = intersections[3];
			
			actor.stopRecoil();
			
			return new float[] { xo, yo };
		}
		
		return null;
	}
	
	/**
	* Method for getting the x position in the Map.
	* 
	* @return The x position on the Map.
	*/
	public float getX()
	{
		return x;
	}
	
	/**
	* Method for setting the x position in the Map.
	* 
	* @param x The x position in the Map to set.
	*/
	public void setX(float x)
	{
		this.x = x;
	}
	
	/**
	* Method for getting the y position in the Map.
	* 
	* @return The y position on the Map.
	*/
	public float getY()
	{
		return y;
	}
	
	/**
	* Method for setting the y position in the Map.
	* 
	* @param y The y position in the Map to set.
	*/
	public void setY(float y)
	{
		this.y = y;
	}
	
	/**
	* Method for getting the width of the building.
	* 
	* @return The width of the building in pixels divided by 10.
	*/
	public byte getWidth()
	{
		return width;
	}
	
	/**
	* Method for getting the height of the building.
	* 
	* @return The height of the building in pixels divided by 10.
	*/
	public byte getHeight()
	{
		return height;
	}
	
	/**
	* Method for getting the visible (scaled) width of the building.
	* Instead of being divided by 10, it remains the same, and then
	* is put into scale according to the rest of the Map.
	* 
	* @return The scaled width in pixels.
	*/
	public float getScaledWidth()
	{
		return width * tileSize;
	}
	
	/**
	* Method for getting the visible (scaled) height of the building.
	* Instead of being divided by 10, it remains the same, and then
	* is put into scale according to the rest of the Map.
	* 
	* @return The scaled height in pixels.
	*/
	public float getScaledHeight()
	{
		return height * tileSize;
	}
	
	/**
	* Method used for explicitely setting the x and y (location)
	* of the building on the Map.
	* 
	* @param x The x position.
	* @param y The y position.
	*/
	public void setLocation(float x, float y)
	{
		this.x = x;
		this.y = y;
	}
	
	/**
	* Get the BuildingMap that is connected with this building.
	* 
	* @return The BuildingMap associated with this object.
	*/
	public BuildingMap getMap()
	{
		return map;
	}
}