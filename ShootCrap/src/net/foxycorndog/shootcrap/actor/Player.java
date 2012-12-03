package net.foxycorndog.shootcrap.actor;

import net.foxycorndog.jdoogl.GL;
import net.foxycorndog.jdoutil.LightBuffer;
import net.foxycorndog.jdoutil.VerticesBuffer;

public class Player extends Actor
{
	private static LightBuffer    textures;
	
	private static VerticesBuffer vertices;
	
	private static boolean        initialized;
	
	static
	{
		vertices = new VerticesBuffer(4 * 2 * 1, 2);
		
		textures = new LightBuffer(4 * 2 * 1);
		
		vertices.genIndices(GL.QUADS, null);
		
		
	}
	
	public Player()
	{
		super(0, 1, 1);
		
		init();
		
	}
	
	private void init()
	{
		vertices = new VerticesBuffer(4 * 2 * 1, 2);
		vertices.addData(GL.addRectVertexArrayf(0, 0, getSpriteSheetColWidth(), getSpriteSheetRowHeight(), 0, null));
		
		textures = new LightBuffer(4 * 2 * 1);
		textures.addData(GL.addRectTextureArrayf(Actor.getSprites(), 0, getSpriteRow(), getSpriteWidth(), getSpriteHeight(), 0, null));
		
		vertices.genIndices(GL.QUADS, null);
	}

	@Override
	public VerticesBuffer getVerticesBuffer()
	{
		return null;
	}

	@Override
	public LightBuffer getTexturesBuffer()
	{
		return null;
	}
}