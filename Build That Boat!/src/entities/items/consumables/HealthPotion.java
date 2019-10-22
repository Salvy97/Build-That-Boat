package entities.items.consumables;

import entities.items.manipulators.ItemIDCollector;
import entities.living.Player;

public class HealthPotion implements ConsumableItem
{
	public void use(Player player) 
	{
		player.setHP(player.getHP() + 40);
		if (player.getHP() > 100)
			player.setHP(player.getHP() - (player.getHP() - 100));
	}
	public int getItemID() { return ItemIDCollector.HEALTH_POTION_ID; }
}