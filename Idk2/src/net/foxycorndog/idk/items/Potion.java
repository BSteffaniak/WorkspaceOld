package net.foxycorndog.idk.items;

import net.foxycorndog.idk.animatedobject.actors.Actor;

public class Potion extends Item
{
	private static int staticIndex;
	
	public static enum PotionId
	{
		MINOR_HEALING_POTION, HEALING_POTION;
		
		private int index;
		
		private PotionId()
		{
			this.index = staticIndex ++;
		}
		
		public int getIndex()
		{
			return index;
		}
		
		public int getId()
		{
			return index + Item.potionsPosition;
		}
	}
	
	public Potion(String name, String info, int value, Object[] fortifications, boolean removeAfterUse)
	{
		super(name, info, value, fortifications, removeAfterUse);
	}
	
	@Override
	public void use(Actor actor, boolean fortify)
	{
		if (fortify)
		{
			super.use(actor);
		}
	}
}