package entities.items.manipulators;

import java.util.ArrayList;

import globals.Sprite;

import static globals.ConstantsCollection.*;

public class ItemsManager
{
	private ArrayList<Item> items;
	
	public ItemsManager() { items = new ArrayList<Item>(); }
	
	public void addItem(int ID, String itemSpritePath, String itemDescription)
	{
		if (!isIDPresent(ID))
			items.add(new Item(ID, new Sprite(itemSpritePath, INVENTORY_ITEM_WIDTH, INVENTORY_ITEM_HEIGHT), itemDescription, 0));
	}
	
	public void addItem(int ID, String itemSpritePath, String itemDescription, int durability)
	{
		if (!isIDPresent(ID))
			items.add(new Item(ID, new Sprite(itemSpritePath, INVENTORY_ITEM_WIDTH, INVENTORY_ITEM_HEIGHT), itemDescription, 0, durability));
	}
	
	public Item getItem(int ID) 
	{ 
		for (int i = 0; i < items.size(); i++)
			if (items.get(i).getID() == ID)
				return items.get(i);
		return null;
	}
	
	public void addQuantity(int ID, int quantity)
	{
		for (int i = 0; i < items.size(); i++)
		{
			if (items.get(i).getID() == ID)
			{
				items.get(i).setQuantity(items.get(i).getQuantity() + quantity);
				if (items.get(i).getQuantity() == 0)
					items.remove(i);
			}
		}
	}
	
	public boolean isIDPresent(int ID)
	{
		for (int i = 0; i < items.size(); i++)
			if (items.get(i).getID() == ID)
				return true;
		return false;
	}
	
	public int getInventorySize()
	{
		int inventorySize = 0;
		for (int i = 0; i < items.size(); i++)
			inventorySize += items.get(i).getQuantity();
		return inventorySize;
	}
	
	public ArrayList<Item> getItems() { return items; }
}