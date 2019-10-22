package graphics;

import javax.swing.JPanel;
import javax.swing.JTextField;

import entities.items.crafting.Crafter;
import entities.items.manipulators.ItemIDCollector;
import entities.items.manipulators.ItemReader;

import static globals.ConstantsCollection.SCREEN_W;

import java.util.ArrayList;

import static globals.ConstantsCollection.SCREEN_H;

public class SearchBox 
{
	private JTextField searchBox;
	
	public SearchBox(JPanel panel) 
	{ 
		searchBox = new JTextField(); 
		panel.add(searchBox);
	}
	
	public void displaySearchBox() 
	{
		searchBox.setSize(SCREEN_W / 19, SCREEN_H / 22);
		searchBox.setLocation(SCREEN_W / 11, SCREEN_H - SCREEN_H / 4 + SCREEN_H / 72);
		searchBox.setEnabled(true);
		searchBox.setVisible(true);
	}
	
	public void hideSearchBox()
	{
		searchBox.setEnabled(false);
		searchBox.setVisible(false);
	}
	
	public void focus() 
	{ 
		searchBox.setFocusable(true);
		searchBox.requestFocusInWindow(); 
	}
	
	public ArrayList<Integer> getSearchedRecipes()
	{
		ArrayList<Integer> recipesAvaliable = new ArrayList<Integer>();
		String value = searchBox.getText();
		if (value == null) 
			for (int i = 0; i < Crafter.getCraftableItemsNumber(); i++)
				recipesAvaliable.add(Crafter.transformPosition(i)); 
		else
			for (int i = 0; i < ItemIDCollector.ITEMS_NUMBER; i++)
				if (ItemReader.getItemName(i).contains(value))
					if (Crafter.hasARecipe(i))
						recipesAvaliable.add(i);
		return recipesAvaliable;
	}
}