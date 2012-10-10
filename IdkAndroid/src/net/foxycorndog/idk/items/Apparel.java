package net.foxycorndog.idk.items;

import net.foxycorndog.idk.Idk;
import net.foxycorndog.idk.R;
import net.foxycorndog.idk.animatedobject.actors.Actor;
import net.foxycorndog.idk.items.targets.Target;
import net.foxycorndog.jdoogl.GL;
import net.foxycorndog.jdoogl.image.imagemap.SpriteSheet;
import net.foxycorndog.jdoutil.LightBuffer;
import net.foxycorndog.jdoutil.VerticesBuffer;

public class Apparel extends Item
{
	private int    row;
	private int    width, height;
	private int    offset;
	
	private Part   parts[];
	
	private VerticesBuffer verticesBuffer;
	private LightBuffer    texturesBuffer;
	
	private static SpriteSheet apparelSprites;
	
	private static int         staticIndex, staticIndex2;
	
	public static enum ApparelId
	{
		MAGES_ROBE;
		
		private int index;
		
		private ApparelId()
		{
			this.index = staticIndex2 ++;
		}
		
		public int getIndex()
		{
			return index;
		}
		
		public int getId()
		{
			return index + Item.apparelPosition;
		}
	}
	
	public static enum Part
	{
		FRONTALIS, FACE, NECK, TORSO, FOREARMS, WRISTS, WAIST, THIGH, 
		CALFS, FEET;
		
		private int index;
		
		private Part()
		{
			this.index = staticIndex ++;
		}
		
		public int getIndex()
		{
			return this.index;
		}
	}
	
	public Apparel(String name, String info, int value, int row, int width, int height, Part parts[], Object fortifications[], boolean removeAfterUse)
	{
		super(name, info, value, fortifications, removeAfterUse);
		
		this.row    = row;
		this.width  = width;
		this.height = height;
		
		this.parts  = parts;
		
		this.verticesBuffer = new VerticesBuffer(Idk.OBJECT_SIZE * 16, 2);
		this.texturesBuffer = new LightBuffer(Idk.OBJECT_SIZE * 16, 2);
		
		for (int i = 0; i < 16; i ++)
		{
			this.verticesBuffer.addData(GL.addRectVertexArrayf(0, 0, width * 10, height * 10, 0, null));
			this.texturesBuffer.addData(GL.addRectTextureArrayf(apparelSprites, i * width, row, width, height, 0, null));
		}
	}
	
	public static void init()
	{
		apparelSprites = new SpriteSheet(Idk.getResources(), R.drawable.apparel, 49, 30);
	}
	
	/**
	* Set the sprite to the specified direction.
	* 
	* @param direction The direction to set the sprite to.
	*/
	public void setSprite(int offset)
	{
//		float textures[] = texturesBuffer.getElements();
		
//		texturesBuffer.setData(0, GL.addRectTextureArrayf(apparelSprites, offset * width, row, width, height, 0, null));
//		
//		texturesBuffer.refreshData();
		
		this.offset = offset;
	}
	
	public void render()
	{
		GL.beginManipulation();
		{
			GL.scalef(Idk.scale, Idk.scale, Idk.scale);
			GL.translatef(0, 0, .1f);
			
			GL.renderQuads(verticesBuffer, texturesBuffer, apparelSprites, offset, 1);
		}
		GL.endManipulation();
	}
	
	public Part[] getParts()
	{
		return parts;
	}
	
	@Override
	public void use(Actor actor)
	{
		super.use(actor);
		
		actor.equipItem(this);
	}
	
	@Override
	public void use(Actor actor, boolean fortify)
	{
		super.use(actor, fortify);
		
		actor.equipItem(this);
	}
}