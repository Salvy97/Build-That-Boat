package entities.items.crafting;

import java.util.ArrayList;
import core.PlayerInventory;
import entities.items.manipulators.ItemIDCollector;
import entities.items.manipulators.ItemReader;
import globals.Pair;

public class Crafter 
{
	private PlayerInventory playerInventory;
	
	public static final Recipe[] recipes =
	{
		null,
		stoneSwordRecipe(),
		null,
		null,
		null,
		woodenArmorRecipe(),
		null,
		bowRecipe(),
		arrowRecipe(),
		goblinArmorRecipe(),
		workedWoodRecipe(),
		woodenPickaxeRecipe(),
		stoneAxeRecipe(),
		null,
		stonePickaxeRecipe(),
		ironSwordRecipe(),
		null,
		woodenCupRecipe(),
		healthPotionRecipe(),
		null,
		vigorRegenerationPotionRecipe(),
		null
	};
	
	public Crafter(PlayerInventory playerInventory) { this.playerInventory = playerInventory; }
	
	private static final Recipe stoneSwordRecipe()
	{
		ArrayList<Pair<Integer, Integer>> itemsNeeded = new ArrayList<Pair<Integer, Integer>>();
		itemsNeeded.add(new Pair<Integer, Integer>(ItemIDCollector.PEBBLE_ID, 8));
		itemsNeeded.add(new Pair<Integer, Integer>(ItemIDCollector.WORKED_WOOD_ID, 4));
		return new Recipe(itemsNeeded, 1);
	}
	
	private static final Recipe woodenArmorRecipe()
	{
		ArrayList<Pair<Integer, Integer>> itemsNeeded = new ArrayList<Pair<Integer, Integer>>();
		itemsNeeded.add(new Pair<Integer, Integer>(ItemIDCollector.WORKED_WOOD_ID, 16));
		return new Recipe(itemsNeeded, 1);
	}
	
	private static final Recipe bowRecipe()
	{
		ArrayList<Pair<Integer, Integer>> itemsNeeded = new ArrayList<Pair<Integer, Integer>>();
		itemsNeeded.add(new Pair<Integer, Integer>(ItemIDCollector.WORKED_WOOD_ID, 8));
		itemsNeeded.add(new Pair<Integer, Integer>(ItemIDCollector.GRASS_LEAF_ID, 12));
		return new Recipe(itemsNeeded, 1);
	}
	
	private static final Recipe arrowRecipe()
	{
		ArrayList<Pair<Integer, Integer>> itemsNeeded = new ArrayList<Pair<Integer, Integer>>();
		itemsNeeded.add(new Pair<Integer, Integer>(ItemIDCollector.PEBBLE_ID, 4));
		itemsNeeded.add(new Pair<Integer, Integer>(ItemIDCollector.GRASS_LEAF_ID, 4));
		itemsNeeded.add(new Pair<Integer, Integer>(ItemIDCollector.WORKED_WOOD_ID, 4));
		return new Recipe(itemsNeeded, 8);
	}
	
	private static final Recipe goblinArmorRecipe()
	{
		ArrayList<Pair<Integer, Integer>> itemsNeeded = new ArrayList<Pair<Integer, Integer>>();
		itemsNeeded.add(new Pair<Integer, Integer>(ItemIDCollector.GOBLIN_EAR_ID, 16));
		itemsNeeded.add(new Pair<Integer, Integer>(ItemIDCollector.GRASS_LEAF_ID, 12));
		return new Recipe(itemsNeeded, 1);
	}
	
	private static final Recipe workedWoodRecipe()
	{
		ArrayList<Pair<Integer, Integer>> itemsNeeded = new ArrayList<Pair<Integer, Integer>>();
		itemsNeeded.add(new Pair<Integer, Integer>(ItemIDCollector.LOG_ID, 1));
		return new Recipe(itemsNeeded, 2);
	}
	
	private static final Recipe woodenPickaxeRecipe()
	{
		ArrayList<Pair<Integer, Integer>> itemsNeeded = new ArrayList<Pair<Integer, Integer>>();
		itemsNeeded.add(new Pair<Integer, Integer>(ItemIDCollector.WORKED_WOOD_ID, 3));
		itemsNeeded.add(new Pair<Integer, Integer>(ItemIDCollector.GRASS_LEAF_ID, 2));
		itemsNeeded.add(new Pair<Integer, Integer>(ItemIDCollector.LOG_ID, 1));
		return new Recipe(itemsNeeded, 1);
	}
	
	private static final Recipe stoneAxeRecipe()
	{
		ArrayList<Pair<Integer, Integer>> itemsNeeded = new ArrayList<Pair<Integer, Integer>>();
		itemsNeeded.add(new Pair<Integer, Integer>(ItemIDCollector.PEBBLE_ID, 6));
		itemsNeeded.add(new Pair<Integer, Integer>(ItemIDCollector.GRASS_LEAF_ID, 4));
		itemsNeeded.add(new Pair<Integer, Integer>(ItemIDCollector.LOG_ID, 1));
		return new Recipe(itemsNeeded, 1);
	}
	
	private static final Recipe stonePickaxeRecipe()
	{
		ArrayList<Pair<Integer, Integer>> itemsNeeded = new ArrayList<Pair<Integer, Integer>>();
		itemsNeeded.add(new Pair<Integer, Integer>(ItemIDCollector.PEBBLE_ID, 8));
		itemsNeeded.add(new Pair<Integer, Integer>(ItemIDCollector.GRASS_LEAF_ID, 4));
		itemsNeeded.add(new Pair<Integer, Integer>(ItemIDCollector.LOG_ID, 1));
		return new Recipe(itemsNeeded, 1);
	}
	
	private static final Recipe ironSwordRecipe()
	{
		ArrayList<Pair<Integer, Integer>> itemsNeeded = new ArrayList<Pair<Integer, Integer>>();
		itemsNeeded.add(new Pair<Integer, Integer>(ItemIDCollector.IRON_ID, 8));
		itemsNeeded.add(new Pair<Integer, Integer>(ItemIDCollector.WORKED_WOOD_ID, 4));
		return new Recipe(itemsNeeded, 1);
	}
	
	public static final Recipe woodenCupRecipe()
	{
		ArrayList<Pair<Integer, Integer>> itemsNeeded = new ArrayList<Pair<Integer, Integer>>();
		itemsNeeded.add(new Pair<Integer, Integer>(ItemIDCollector.WORKED_WOOD_ID, 4));
		return new Recipe(itemsNeeded, 1);
	}
	
	public static final Recipe healthPotionRecipe()
	{
		ArrayList<Pair<Integer, Integer>> itemsNeeded = new ArrayList<Pair<Integer, Integer>>();
		itemsNeeded.add(new Pair<Integer, Integer>(ItemIDCollector.RED_FLOWER_ID, 4));
		itemsNeeded.add(new Pair<Integer, Integer>(ItemIDCollector.WOODEN_CUP_ID, 1));
		return new Recipe(itemsNeeded, 1);
	}
	
	public static final Recipe vigorRegenerationPotionRecipe()
	{
		ArrayList<Pair<Integer, Integer>> itemsNeeded = new ArrayList<Pair<Integer, Integer>>();
		itemsNeeded.add(new Pair<Integer, Integer>(ItemIDCollector.YELLOW_FLOWER_ID, 4));
		itemsNeeded.add(new Pair<Integer, Integer>(ItemIDCollector.WOODEN_CUP_ID, 1));
		return new Recipe(itemsNeeded, 1);
	}
	
	public boolean isCraftable(int ID)
	{
		if (recipes[ID] == null) return false;
		if (((ItemReader.isEquipable(ID) || ItemReader.armorValue(ID) != 0) && playerInventory.getItemQuantity(ID) == 1) || playerInventory.getEquipableItem().getItemID() == ID || playerInventory.getArmor().getID() == ID)  return false;
		for (int i = 0; i < recipes[ID].getRecipeSize(); i++)
		{
			int IDNeeded = recipes[ID].getRecipe().get(i).getFirstElement();
			int quantityNeeded = recipes[ID].getRecipe().get(i).getSecondElement();
			if (!playerInventory.isIDPresent(IDNeeded) ||  playerInventory.getItemQuantity(IDNeeded) < quantityNeeded) return false;
		}
		return true;
	}
	
	public void craftItem(int ID)
	{
		if (isCraftable(ID)) 
		{
			for (int i = 0; i < recipes[ID].getRecipeSize(); i++)
			{
				int IDNeeded = recipes[ID].getRecipe().get(i).getFirstElement();
				int quantityNeeded = recipes[ID].getRecipe().get(i).getSecondElement();
				playerInventory.addQuantity(IDNeeded, -quantityNeeded);
			}
			if (ItemReader.durability(ID) == -1) playerInventory.collectItem(ID, recipes[ID].getProductNumber());
			else playerInventory.collectEquipableItem(ID, ItemReader.durability(ID));
		}
	}
	
	public static int getCraftableItemsNumber()
	{
		int cont = 0;
		for (int i = 0; i < recipes.length; i++)
			if (recipes[i] != null)
				cont++;
		return cont;
	}
	
	public static Integer transformPosition(int index)
	{
		int tempIndex = index + 1;
		for (int i = 0; i < recipes.length; i++)
		{
			if (recipes[i] != null)
				tempIndex--;
			if (tempIndex == 0)
				return i;
		}
		return null;
	}
	
	public static Integer transformID(int ID)
	{
		for (int i = 0; i < recipes.length; i++)
			if (i == ID)
				return i;
		return null;
	}
	
	public static boolean hasARecipe(int ID) { return recipes[ID] != null; }
}