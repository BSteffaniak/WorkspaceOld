package net.foxycorndog.idk.items;

import net.foxycorndog.idk.Idk;
import net.foxycorndog.idk.animatedobject.actors.Actor;
import net.foxycorndog.idk.items.Apparel.ApparelId;
import net.foxycorndog.idk.items.Apparel.Part;
import net.foxycorndog.idk.items.Potion.PotionId;
import net.foxycorndog.idk.items.Weapon.WeaponId;
import net.foxycorndog.idk.items.targets.Self;
import net.foxycorndog.idk.items.targets.SurroundingActors;
import net.foxycorndog.idk.items.targets.Target;
import net.foxycorndog.jdoogl.components.ToolTipText;

public abstract class Item
{
	private boolean removeAfterUse;
	
	private int    id;
	private int    value;
	
	private String name;
	private String info;
	
	private Object fortifications[];
	
	private ToolTipText toolTipText;
	
	private static int dynamicId;
	
	private static Item[]    items;
	private static Potion[]  potions;
	private static Apparel[] apparel;
	private static Weapon[]  weapons;
	
	private static int       staticIndex;
	
	public  static int       potionsPosition, apparelPosition, weaponsPosition;
	
	static
	{
		int d= Apparel.weaponsPosition;
		potions = new Potion [PotionId.values().length];
		apparel = new Apparel[ApparelId.values().length];
		weapons = new Weapon [WeaponId.values().length];
		
		items   = new Item   [potions.length + apparel.length + weapons.length];
		
		potions[PotionId.MINOR_HEALING_POTION.getIndex()] = new Potion("Minor Healing Potion", "A potion that is used to replinish 15 health points.", 10,
				new Object[]
				{
					Fortification.HEALTH, 15, new Self()
				}, true);
		
		potions[PotionId.HEALING_POTION.getIndex()]       = new Potion("Healing Potion", "A potion that is used to replinish 30 health points.", 45,
				new Object[]
				{
					Fortification.HEALTH, 30, new Self()
				}, true);
		
		apparel[ApparelId.MAGES_ROBE.getIndex()]          = new Apparel("Mages Robe", "A mage should use this.", 35, 0, 3, 5,
				new Part[]
				{
					Part.TORSO,
					Part.FOREARMS,
					Part.THIGH,
					Part.CALFS,
					Part.WAIST
				},
				new Object[]
				{
					Fortification.DEFENSE, 3, new Self()
				}, false);
		
		weapons[WeaponId.BRONZE_DAGGER.getIndex()]        = new Weapon("Bronze Dagger", "Used for stabbin the crap out of stuff.", 15,
				new Object[]
				{
					Fortification.ATTACK, 3, new SurroundingActors(80)
				}, false);
		
		potionsPosition = 0;
		
		for (int i = 0; i < potions.length; i ++)
		{
			items[i + potionsPosition] = potions[i];
		}
		
		apparelPosition = potions.length;
		
		for (int i = 0; i < apparel.length; i ++)
		{
			items[i + apparelPosition] = apparel[i];
		}
		
		weaponsPosition = apparelPosition + apparel.length;
		
		for (int i = 0; i < weapons.length; i ++)
		{
			items[i + weaponsPosition] = weapons[i];
		}
	}
	
	public enum Fortification
	{
		HEALTH("Health"), ATTACK("Attack"), DEFENSE("Defense");
		
		private String string;
		
		private Fortification(String string)
		{
			this.string = string;
		}
		
		public void enable(Object variable, Target target, Actor actor)
		{
			if (target instanceof Self)
			{
				enable(variable, (Self)target, actor);
			}
		}
		
		public void enable(Object variable, Self target, Actor actor)
		{
			if (variable instanceof Integer)
			{
				enable((Integer)variable, actor);
			}
		}
		
		public void enable(int amount, Actor actor)
		{
			if (this == HEALTH)
			{
				actor.addHealth(amount);
			}
			if (this == ATTACK)
			{
				actor.addAttack(amount);
			}
			if (this == DEFENSE)
			{
				actor.addDefense(amount);
			}
		}
		
		public void disable(Object variable, Target target, Actor actor)
		{
			if (target instanceof Self)
			{
				disable(variable, (Self)target, actor);
			}
		}
		
		public void disable(Object variable, Self target, Actor actor)
		{
			if (variable instanceof Integer)
			{
				disable((Integer)variable, actor);
			}
		}
		
		public void disable(int amount, Actor actor)
		{
			if (this == HEALTH)
			{
				actor.removeHealth(amount, 0, false);
			}
			if (this == ATTACK)
			{
				actor.removeAttack(amount);
			}
			if (this == DEFENSE)
			{
				actor.removeDefense(amount);
			}
		}
		
		public String toString()
		{
			return string;
		}
	}
	
	public Item(String name, String info, int value, Object fortifications[], boolean removeAfterUse)
	{
		if (items == null)
		{
			throw new RuntimeException("You must statically initialize this class before you can use it.");
		}
		
		this.value          = value;
		
		this.name           = name;
		this.info           = info;
		
		this.fortifications = fortifications;
		
		this.removeAfterUse = removeAfterUse;
		
		toolTipText = new ToolTipText(getFortificationText(), Idk.getFont());
		
		this.id             = dynamicId ++;
	}
	
	public void use(Actor actor)
	{
		for (int i = 0; i < fortifications.length; i += 3)
		{
			Fortification fortification = ((Fortification)fortifications[i]);
			
			fortification.enable(fortifications[i + 1], (Target)fortifications[i + 2], actor);
		}
	}
	
	public void use(Actor actor, boolean fortify)
	{
		if (fortify)
		{
			for (int i = 0; i < fortifications.length; i += 3)
			{
				Fortification fortification = ((Fortification)fortifications[i]);
				
				fortification.enable(fortifications[i + 1], (Target)fortifications[i + 2], actor);
			}
		}
		else
		{
			for (int i = 0; i < fortifications.length; i += 3)
			{
				Fortification fortification = ((Fortification)fortifications[i]);
				
				fortification.disable(fortifications[i + 1], (Target)fortifications[i + 2], actor);
			}
		}
	}
	
	public boolean isRemovedAfterUse()
	{
		return removeAfterUse;
	}
	
	public String getName()
	{
		return name;
	}
	
	public String getInfo()
	{
		return info;
	}
	
	public int getValue()
	{
		return value;
	}
	
	public int getId()
	{
		return id;
	}
	
	public static Item getRandomItem()
	{
		int rand = (int)(Math.random() * items.length);
		
		return items[rand];
	}
	
	public static Item getItemById(int id)
	{
		return items[id];
	}
	
	public String getFortificationText()
	{
		String text = "";
		
		for (int i = 0; i < fortifications.length; i += 3)
		{
			Fortification fortification = ((Fortification)fortifications[i]);
			int           amount        = ((Integer)fortifications[i + 1]);
			Target        target        = ((Target)fortifications[i + 2]);
			
			text += fortification.toString() + " ";
			text += (amount >= 0 ? "+" : "") + amount + " ";
			text += "on " + target.toString().toLowerCase() + (target instanceof SurroundingActors ? " within the radius of " + ((SurroundingActors)target).getRadius() + " tiles" : "") + ".";
			
			text += "\n";
		}
		
		return text;
	}
	
	public ToolTipText getToolTipText()
	{
		return toolTipText;
	}
}