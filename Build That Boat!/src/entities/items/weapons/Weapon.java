package entities.items.weapons;

import entities.items.EquipableEntity;

public abstract class Weapon extends EquipableEntity
{
	public Weapon(int width, int height, String spritePath, int durability)
	{
		super(width, height, spritePath, durability);
	}
}