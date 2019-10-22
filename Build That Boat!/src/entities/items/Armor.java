package entities.items;

public class Armor 
{
	private int ID;
	private int armorClass;
	private int durability;
	
	public Armor(int ID, int armorClass, int durability)
	{
		this.ID = ID;
		this.armorClass = armorClass;
		this.durability = durability;
	}
	
	public int getID() { return ID; }
	public int getArmorClass() { return armorClass; }
	public int getDurability() { return durability; }
	
	public void decreaseDurability() { durability--; }
}