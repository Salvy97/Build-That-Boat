package core;

import entities.items.Armor;
import entities.items.consumables.ConsumableItem;
import entities.items.consumables.DeerMeat;
import entities.items.consumables.HealthPotion;
import entities.items.consumables.VigorRegenerationPotion;
import entities.items.manipulators.EquipableItem;
import entities.items.manipulators.Item;
import entities.items.manipulators.ItemIDCollector;
import entities.items.manipulators.ItemReader;
import entities.items.manipulators.ItemsManager;
import entities.items.tools.Axe;
import entities.items.tools.WoodenPickaxe;
import entities.items.tools.Punch;
import entities.items.tools.StonePickaxe;
import entities.items.weapons.Arrow;
import entities.items.weapons.Bow;
import entities.items.weapons.IronSword;
import entities.items.weapons.StoneSword;
import entities.living.Player;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class PlayerInventory 
{
	public static final int MAXIMUM_CAPACITY = 20;
	public static final int ORIZONTAL_SLOTS = 7;
	public static final int VERTICAL_SLOTS = 3;
	public static final int SLOTS = ORIZONTAL_SLOTS * VERTICAL_SLOTS;
	
	private ItemsManager inventory;
	private EquipableItem itemBeingEquipped;
	private Armor armor;
	private ArrayList<Arrow> arrows;
	private ArrayList<ConsumableItem> consumableItems;

	public PlayerInventory() 
	{ 
		inventory = new ItemsManager(); 
		setPunch();
		armor = new Armor(-1, 0, -1);
		arrows = new ArrayList<Arrow>();
		consumableItems = new ArrayList<ConsumableItem>();
	}
		
	private void addItem(int ID, String itemSpritePath, String itemDescription) { inventory.addItem(ID, itemSpritePath, itemDescription); }
	private void addItem(int ID, String itemSpritePath, String itemDescription, int durability) { inventory.addItem(ID, itemSpritePath, itemDescription, durability); }
	public void addQuantity(int ID, int quantity) { inventory.addQuantity(ID, quantity); }
	public boolean isIDPresent(int ID) { return inventory.isIDPresent(ID); }
	public int getItemQuantity(int ID) 
	{ 
		if (inventory.getItem(ID) != null) return inventory.getItem(ID).getQuantity();
		else return 0;
	}
	public String getItemDescription(int ID)
	{
		if (inventory.getItem(ID) != null) return inventory.getItem(ID).getDescription();
		else return null;
	}
	public BufferedImage getItemSprite(int ID)
	{
		if (inventory.getItem(ID) != null) return inventory.getItem(ID).getSprite();
		else return null;
	}
	public ArrayList<Item> getItems() { return inventory.getItems(); }
	public Item getItem(int ID) 
	{ 
		if (inventory.isIDPresent(ID)) 
			for (int i = 0; i < inventory.getItems().size(); i++)
				if (inventory.getItems().get(i).getID() == ID)
					return inventory.getItems().get(i);
		return null;
	}
	
	public int inventorySize() { return inventory.getInventorySize(); }
	
	public void setItemBeingEquipped(int ID, int durability) 
	{ 
		if (ID == ItemIDCollector.STONE_SWORD_ID) itemBeingEquipped = new StoneSword(durability);
		else if (ID == ItemIDCollector.BOW_ID) itemBeingEquipped = new Bow(this, durability);
		else if (ID == ItemIDCollector.WOODEN_PICKAXE_ID) itemBeingEquipped = new WoodenPickaxe(durability);
		else if (ID == ItemIDCollector.STONE_AXE_ID) itemBeingEquipped = new Axe(durability);
		else if (ID == ItemIDCollector.STONE_PICKAXE_ID) itemBeingEquipped = new StonePickaxe(durability);
		else if (ID == ItemIDCollector.IRON_SWORD_ID) itemBeingEquipped = new IronSword(durability);
	}
	
	public void setPunch() { itemBeingEquipped = new Punch(); }
	
	public EquipableItem getEquipableItem() { return itemBeingEquipped; }
	
	public void collectItem(int ID, int quantity)
	{
		if (!inventory.isIDPresent(ID))
		{
			addItem(ID, ItemReader.getImagePath(ID), ItemReader.getItemDescription(ID));
			addQuantity(ID, quantity);
		}
		else addQuantity(ID, quantity);
	}
	
	public void collectEquipableItem(int ID, int durability)
	{
		if (!inventory.isIDPresent(ID))
		{
			addItem(ID, ItemReader.getImagePath(ID), ItemReader.getItemDescription(ID), durability);
			addQuantity(ID, 1);
		}
	}
	
	public void setArmor(int ID, int durability)
	{
		if (ItemReader.armorValue(ID) != 0)
			armor = new Armor(ID, ItemReader.armorValue(ID), durability);
	}
	
	public void removeArmor() { armor = new Armor(-1, 0, -1); }
	
	public Armor getArmor() { return armor; }
	public ArrayList<Arrow> getArrows() { return arrows; }
	public ArrayList<ConsumableItem> getConsumableItems() { return consumableItems; }
	
	public void addConsumableItem(int ID) 
	{ 
		if (ID == ItemIDCollector.HEALTH_POTION_ID) consumableItems.add(new HealthPotion()); 
		else if (ID == ItemIDCollector.VIGOR_REGENERATION_POTION_ID) consumableItems.add(new VigorRegenerationPotion());
		else if (ID == ItemIDCollector.DEER_MEAT_ID) consumableItems.add(new DeerMeat());
	}
	public void useConsumable(int ID, Player player)
	{
		boolean used = false;
		for (int i = 0; i < consumableItems.size() && !used; i++)
		{
			if (consumableItems.get(i).getItemID() == ID)
			{
				consumableItems.get(i).use(player);
				consumableItems.remove(i);
				used = true;
			}
		}
	}
}