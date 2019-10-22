package entities.items.weapons;

import entities.items.manipulators.EquipableItem;
import entities.items.manipulators.ItemIDCollector;
import globals.KeysDefiner;
import static globals.ConstantsCollection.*;

public class StoneSword extends Sword implements EquipableItem
{	
	public StoneSword(int durability) { super(SWORD_WIDTH, SWORD_HEIGHT, STONE_SWORD_SPRITE_PATH[KeysDefiner.UP], durability); }
	
	public int getDamage() { return 25; }
	public int getVigorUsed() { return 50; }
	public int getItemID() { return ItemIDCollector.STONE_SWORD_ID; }
	public String getSprite(int direction)
	{
		switch(direction)
		{
			case KeysDefiner.UP: 	return STONE_SWORD_SPRITE_PATH[KeysDefiner.UP];
			case KeysDefiner.LEFT: 	return STONE_SWORD_SPRITE_PATH[KeysDefiner.LEFT];
			case KeysDefiner.RIGHT: return STONE_SWORD_SPRITE_PATH[KeysDefiner.RIGHT];
		}
		return STONE_SWORD_SPRITE_PATH[KeysDefiner.DOWN];
	}
}