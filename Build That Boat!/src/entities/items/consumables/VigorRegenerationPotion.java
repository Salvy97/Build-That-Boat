package entities.items.consumables;

import java.util.Timer;
import java.util.TimerTask;
import entities.items.manipulators.ItemIDCollector;
import entities.living.Player;

public class VigorRegenerationPotion implements ConsumableItem
{
	public void use(Player player) 
	{ 
		player.setVigorRegeneration(25); 
		Timer potionTimer = new Timer();
		potionTimer.schedule(new TimerTask()
		{
			public void run()
			{
				player.setVigorRegeneration(10);
			}
		}, 15 * 1000);
	}
	public int getItemID() { return ItemIDCollector.VIGOR_REGENERATION_POTION_ID; }
}