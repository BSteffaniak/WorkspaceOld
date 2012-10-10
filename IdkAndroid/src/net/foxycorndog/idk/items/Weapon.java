package net.foxycorndog.idk.items;

import net.foxycorndog.idk.animatedobject.actors.Actor;
import net.foxycorndog.idk.items.targets.Target;

public class Weapon extends Item
{
	private static int staticIndex;
	
	public static enum WeaponId
	{
		BRONZE_DAGGER;
		
		private int index;
		
		private WeaponId()
		{
			this.index = staticIndex ++;
		}
		
		public int getIndex()
		{
			return index;
		}
		
		public int getId()
		{
			return index + Item.weaponsPosition;
		}
	}
	
	public Weapon(String name, String info, int value, Object[] fortifications, boolean removeAfterUse)
	{
		super(name, info, value, fortifications, removeAfterUse);
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
		
		if (fortify)
		{
			actor.equipItem(this);
		}
	}
}