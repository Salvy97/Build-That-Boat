package entities.items.consumables;

import entities.items.manipulators.ItemIDCollector;
import entities.living.Player;

public class DeerMeat implements ConsumableItem
{
	public void use(Player player) 
	{
		player.decreaseHunger(40);
		if (player.getHunger() > 100)
			player.decreaseHunger(-(player.getHunger() - 100));
	}
	public int getItemID() { return ItemIDCollector.DEER_MEAT_ID; }
}