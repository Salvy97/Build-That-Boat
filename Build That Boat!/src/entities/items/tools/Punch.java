package entities.items.tools;

import entities.items.manipulators.EquipableItem;
import core.audio.AudioIDCollector;

public class Punch extends Tool implements EquipableItem
{
	public Punch() { super(-1); }

	public int getDamage() { return 10; }
	public int getVigorUsed() { return 40; }
	public int getItemID() { return -1; }
	public void disable() { hitBox = null; }
	public boolean isDisabled() { return hitBox == null; }
	public int getAudioID() { return AudioIDCollector.PUNCH_ID; }
}