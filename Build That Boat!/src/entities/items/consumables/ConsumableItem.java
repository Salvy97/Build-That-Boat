package entities.items.consumables;

import entities.living.Player;

public interface ConsumableItem 
{
	public abstract void use(Player player);
	public abstract int getItemID();
}