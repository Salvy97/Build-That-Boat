package entities.items.tools;

import core.audio.AudioIDCollector;
import entities.items.manipulators.EquipableItem;

public class StonePickaxe extends Tool implements EquipableItem
{
	public StonePickaxe(int durability) { super(durability); }

	public int getDamage() { return 40; }
	public int getVigorUsed() { return 60; }
	public int getItemID() { return 14; }
	public void disable() { hitBox = null; }
	public boolean isDisabled() { return hitBox == null; }
	public int getAudioID() { return AudioIDCollector.PICKAXE_ID; }
}