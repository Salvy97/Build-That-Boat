package entities.items.tools;

import core.audio.AudioIDCollector;
import entities.items.manipulators.EquipableItem;

public class WoodenPickaxe extends Tool implements EquipableItem
{
	public WoodenPickaxe(int durability) { super(durability); }

	public int getDamage() { return 25; }
	public int getVigorUsed() { return 40; }
	public int getItemID() { return 11; }
	public void disable() { hitBox = null; }
	public boolean isDisabled() { return hitBox == null; }
	public int getAudioID() { return AudioIDCollector.PICKAXE_ID; }
}