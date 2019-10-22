package entities.items.tools;

import core.audio.AudioIDCollector;
import entities.items.manipulators.EquipableItem;

public class Axe extends Tool implements EquipableItem
{
	public Axe(int durability) { super(durability); }

	public int getDamage() { return 25; }
	public int getVigorUsed() { return 60; }
	public int getItemID() { return 12; }
	public void disable() { hitBox = null; }
	public boolean isDisabled() { return hitBox == null; }
	public int getAudioID() { return AudioIDCollector.AXE_ID; }
}