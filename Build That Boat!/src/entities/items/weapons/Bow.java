package entities.items.weapons;

import java.util.ArrayList;
import core.PlayerInventory;
import core.audio.AudioIDCollector;
import entities.items.manipulators.EquipableItem;
import entities.items.manipulators.ItemIDCollector;

import static globals.ConstantsCollection.*;

public class Bow extends Weapon implements EquipableItem
{
	private PlayerInventory playerInventory;
	
	public Bow(PlayerInventory playerInventory, int durability)
	{
		super(0, 0, null, durability);
		this.playerInventory = playerInventory;
		setHitBox(0, 0, 0, 0);
	}

	public int getDamage() { return 1; }
	public int getVigorUsed() { return 60; }
	public int getItemID() { return ItemIDCollector.BOW_ID; }
	public void use(int playerX, int playerY, int direction) 
	{
		if (playerInventory.getItemQuantity(ItemIDCollector.ARROW_ID) > 0)
		{
			ArrayList<Arrow> arrows = playerInventory.getArrows();
			arrows.add(new Arrow(playerX + SCREEN_W / 120, playerY + SCREEN_W / 60, direction));
			playerInventory.collectItem(ItemIDCollector.ARROW_ID, -1);
			decreaseDurability();
		}
	}
	public void disable() { }
	public boolean isDisabled() { return true; }
	public int getAudioID() { return AudioIDCollector.PUNCH_ID; }
}