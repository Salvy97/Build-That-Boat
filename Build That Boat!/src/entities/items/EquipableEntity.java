package entities.items;

import entities.Entity;

public abstract class EquipableEntity extends Entity
{
	protected int durability;
	
	public EquipableEntity(int width, int height, String spritePath, int durability)
	{
		super(0, 0, width, height, 0, 0, spritePath, false);
		this.durability = durability;
	}
	
	public int getDurability() { return durability; }
	
	public void setDurability(int durability) { this.durability = durability; }
	
	public void decreaseDurability() { durability--; }
	
	public abstract int getAudioID();
}