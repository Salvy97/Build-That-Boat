package entities.items.manipulators;

import java.awt.image.BufferedImage;

import globals.Sprite;

public class Item 
{
	private int ID;
	private Sprite sprite;
	private String description;
	private int durability;
	private int quantity;
	
	public Item(int ID, Sprite sprite, String description, int quantity)
	{
		this.ID = ID;
		this.sprite = sprite;
		this.description = description;
		this.quantity = quantity;
		durability = -1;
	}
	
	public Item(int ID, Sprite sprite, String description, int quantity, int durability)
	{
		this.ID = ID;
		this.sprite = sprite;
		this.description = description;
		this.quantity = quantity;
		this.durability = durability;
	}
	
	public int getID() { return ID; }
	public BufferedImage getSprite() { return sprite.getSprite(); }
	public String getDescription() { return description; }
	public int getDurability() { return durability; }
	public int getQuantity() { return quantity; }
	
	public void setID(int ID) { this.ID = ID; }
	public void setSprite(Sprite sprite) { this.sprite = sprite; }
	public void setDescription(String description) { this.description = description; }
	public void setDurability(int durability) { this.durability = durability; }
	public void setQuantity(int quantity) { this.quantity = quantity; }
}