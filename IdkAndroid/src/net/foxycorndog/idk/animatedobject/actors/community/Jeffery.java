package net.foxycorndog.idk.animatedobject.actors.community;

import net.foxycorndog.idk.items.Item;
import net.foxycorndog.idk.items.Apparel.ApparelId;
import net.foxycorndog.idk.maps.Map;

public class Jeffery extends StickFigure
{
	public Jeffery(Map map)
	{
		super(map);
		
		addPhrase("Tip o' de' marnin to y'all!", Mood.HAPPY);
		addPhrase("All you yungsters bettr get off o' me yard!!", Mood.ANNOYED);
		addPhrase("Well hello there!", Mood.HAPPY);
		addPhrase("How is you today?", Mood.HAPPY);
		
		getInventory().addItem(Item.getItemById(ApparelId.MAGES_ROBE.getId()));
		
		getInventory().getItem(0).use(this);
	}
}