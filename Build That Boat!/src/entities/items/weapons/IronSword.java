package entities.items.weapons;

import entities.items.manipulators.EquipableItem;
import entities.items.manipulators.ItemIDCollector;
import globals.KeysDefiner;
import static globals.ConstantsCollection.*;

public class IronSword extends Sword implements EquipableItem
{	
	public IronSword(int durability) { super(SWORD_WIDTH, SWORD_HEIGHT, IRON_SWORD_SPRITE_PATH[KeysDefiner.UP], durability); }
	
	public int getDamage() { return 60; }
	public int getVigorUsed() { return 50; }
	public int getItemID() { return ItemIDCollector.IRON_SWORD_ID; }
	public String getSprite(int direction)
	{
		switch(direction)
		{
			case KeysDefiner.UP: 	return IRON_SWORD_SPRITE_PATH[KeysDefiner.UP];
			case KeysDefiner.LEFT: 	return IRON_SWORD_SPRITE_PATH[KeysDefiner.LEFT];
			case KeysDefiner.RIGHT: return IRON_SWORD_SPRITE_PATH[KeysDefiner.RIGHT];
		}
		return IRON_SWORD_SPRITE_PATH[KeysDefiner.DOWN];
	}
}