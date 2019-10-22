package entities.items.crafting;

import java.util.ArrayList;
import globals.Pair;

public class Recipe 
{
	private ArrayList<Pair<Integer, Integer>> itemsNeeded;
	private int productNumber;
	
	public Recipe(ArrayList<Pair<Integer, Integer>> itemsNeeded, int productNumber) 
	{ 
		this.itemsNeeded = itemsNeeded;
		this.productNumber = productNumber;
	}
	
	public ArrayList<Pair<Integer, Integer>> getRecipe() { return itemsNeeded; }
	public int getProductNumber() { return productNumber; }
	
	public void setRecipe(ArrayList<Pair<Integer, Integer>> itemsNeeded) { this.itemsNeeded = itemsNeeded; }
	public void setProductNumber(int productNumber) { this.productNumber = productNumber; }
	
	public int getRecipeSize() { return itemsNeeded.size(); }
}