package graphics;

import core.GameLogicManager;
import core.PlayerInventory;
import core.audio.AudioIDCollector;
import entities.items.crafting.Crafter;
import entities.items.manipulators.Item;
import entities.items.manipulators.ItemReader;
import globals.Sprite;

import static globals.ConstantsCollection.*;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

/**Gestisce e disegna il menu inventario e crafting*/
public class PlayerInventoryManager implements MouseListener
{
	//Variabili di supporto di gioco
	private GameLogicManager game;
	private PlayerInventory playerInventory;
	private JPanel panel;
	
	public ArrayList<Integer> recipesAvaliable; //Tiene le ricette possibili cercate dalla searchBox
	public int[] recipesToDisplay; //Le ricette "displayate" a schermo
	//Questi due indici tengono traccia della posizione iniziale e finale dell'array di ricette
	public int arrayFirstPosition;
	public int arrayLastPosition;
	
	//Sprites del menu inventario e crafting
	private static Sprite inventoryGUI;
	private static Sprite inventoryTile;
	private static Sprite craftingGUI;
	private static Sprite scrollDown;
	private static Sprite scrollUp;
	private static Sprite craftingSprite;
	
	//Le descrizioni degli items e i loro ID
	private String[][] descriptionsDisplayed;
	private int[][] IDs;
	private String descriptionToDisplay;
	private String durability;
	
	//La barra di ricerca
	public SearchBox searchBox;
	
	//I bottoni della parte crafting
	public JButton scrollDownButton;
	public JButton scrollUpButton;
	public JButton craftingButton;
	public JButton searchButton;
	
	//IDs della ricetta, oggetto equipaggiabile e armatura selezionati
	private int recipeID;
	private int equipableItemID;
	private int armorID;
	
	public PlayerInventoryManager(GameLogicManager game, JPanel panel)
	{
		this.game = game;
		playerInventory = game.getPlayer().getInventory();
		this.panel = panel;
		
		searchBox = new SearchBox(panel);
		
		recipesAvaliable = new ArrayList<Integer>();
		for (int i = 0; i < Crafter.getCraftableItemsNumber(); i++)
			recipesAvaliable.add(Crafter.transformPosition(i));
		
		recipesToDisplay = new int[9];
		for (int i = 0; i < 9; i++)
			recipesToDisplay[i] = recipesAvaliable.get(i);
		arrayFirstPosition = 0;
		arrayLastPosition = 8;
		
		inventoryGUI = new Sprite(INVENTORY_GUI_SPRITE_PATH, SCREEN_W / 3, SCREEN_H / 2);
		inventoryTile = new Sprite(INVENTORY_TILE_SPRITE_PATH, SCREEN_W / 25, SCREEN_H / 14);
		craftingGUI = new Sprite(CRAFTING_GUI_SPRITE_PATH, SCREEN_W / 5, SCREEN_H);
		
		craftingSprite = new Sprite(CRAFTING_BUTTON_NOT_PRESSING_SPRITE_PATH, SCREEN_W / 13, SCREEN_H / 20);
		craftingButton = new JButton(new ImageIcon(craftingSprite.getSprite()));
		panel.add(craftingButton);
		craftingButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				if (recipeID != -1)
					game.craftItem(recipeID);
				playerInventory.addConsumableItem(recipeID);		
			}
		});
		scrollDown = new Sprite(SCROLL_DOWN_NOT_PRESSING_SPRITE_PATH, SCREEN_W / 40, SCREEN_H / 25);
		scrollDownButton = new JButton(new ImageIcon(scrollDown.getSprite()));
		panel.add(scrollDownButton);
		scrollDownButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				if (recipesAvaliable.size() > 9)
				{
					int lastPosition = -1;
					if (arrayLastPosition + 1 < recipesAvaliable.size())
						lastPosition = recipesAvaliable.get(arrayLastPosition + 1);
					if (lastPosition != -1)
					{
						arrayFirstPosition++;
						arrayLastPosition++;
						int index = arrayFirstPosition;
						for (int i = 0; i < 9; i++)
						{
							recipesToDisplay[i] = recipesAvaliable.get(index);
							index++;
						}	
					}
				}
			}
		});
		scrollUp = new Sprite(SCROLL_UP_NOT_PRESSING_SPRITE_PATH, SCREEN_W / 40, SCREEN_H / 25);
		scrollUpButton = new JButton(new ImageIcon(scrollUp.getSprite()));
		panel.add(scrollUpButton);
		scrollUpButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				if (recipesAvaliable.size() > 9 && recipesToDisplay[0] != recipesAvaliable.get(0))
				{
					arrayFirstPosition--;
					arrayLastPosition--;
					int index = arrayFirstPosition;
					for (int i = 0; i < 9; i++)
					{
						recipesToDisplay[i] = recipesAvaliable.get(index);
						index++;
					}	
				}
			}
		});
		searchButton = new JButton("=");
		panel.add(searchButton);
		searchButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				panel.requestFocusInWindow();
				recipesAvaliable = searchBox.getSearchedRecipes();
				if (recipesAvaliable.size() >= 9)
				{
					recipesToDisplay = new int[9];
					for (int i = 0; i < 9; i++)
						recipesToDisplay[i] = recipesAvaliable.get(i);
					arrayLastPosition = 8;
				}
				else
				{
					recipesToDisplay = new int[recipesAvaliable.size()];
					for (int i = 0; i < recipesAvaliable.size(); i++)
						recipesToDisplay[i] = recipesAvaliable.get(i);
					arrayLastPosition = recipesAvaliable.size() - 1;
				}
				arrayFirstPosition = 0;
			}
		});
		
		descriptionsDisplayed = new String[PlayerInventory.VERTICAL_SLOTS][PlayerInventory.ORIZONTAL_SLOTS];
		IDs = new int[PlayerInventory.VERTICAL_SLOTS][PlayerInventory.ORIZONTAL_SLOTS];
		recipeID = -1;
		
		equipableItemID = playerInventory.getEquipableItem().getItemID();
		armorID = -1;		
	}
	
	public void resetRecipeID() { recipeID = -1; }
	
	private static int calculateInventoryXPosition(int pos) { return (inventoryTile.getWidth() * pos) + (SCREEN_W / PlayerInventory.VERTICAL_SLOTS) + (SCREEN_W / 35); }
	private static int calculateInventoryYPosition(int pos) { return (inventoryTile.getHeight() * pos) + (SCREEN_H / 2) - (SCREEN_H / 31); }
	private static int calculateCraftingXPosition() { return craftingGUI.getWidth() - (craftingGUI.getWidth() - inventoryTile.getWidth() / 2); } 
	private static int calculateCraftingYPosition(int pos) { return (inventoryTile.getHeight() * pos) + SCREEN_H / 7 ; }
	
	/**Disegna l'inventario e il menu di crafting*/
	public void drawInventory(Graphics g)
	{
		ArrayList<Item> items = new ArrayList<Item>(playerInventory.getItems());
		int itemsIndex = 0;
		equipableItemID = playerInventory.getEquipableItem().getItemID();
		armorID = playerInventory.getArmor().getID();
		
		searchBox.displaySearchBox();
		craftingButton.setVisible(true);
		craftingButton.setEnabled(true);
		craftingButton.setSize(craftingSprite.getWidth(), craftingSprite.getHeight());
		craftingButton.setLocation(SCREEN_W / 11, SCREEN_H - (SCREEN_H / 7));
		scrollDownButton.setVisible(true);
		scrollDownButton.setEnabled(true);
		scrollDownButton.setSize(scrollDown.getWidth(), scrollDown.getHeight());
		scrollDownButton.setLocation(SCREEN_W / 35, SCREEN_H - (SCREEN_H / 5));
		scrollUpButton.setVisible(true);
		scrollUpButton.setEnabled(true);
		scrollUpButton.setSize(scrollUp.getWidth(), scrollUp.getHeight());
		scrollUpButton.setLocation(SCREEN_W / 35, SCREEN_H / 11);
		searchButton.setVisible(true);
		searchButton.setEnabled(true);
		searchButton.setSize(SCREEN_W / 40, SCREEN_H / 25);
		searchButton.setLocation(SCREEN_W / 7 + SCREEN_W / 330, SCREEN_H - SCREEN_H / 4 + SCREEN_H / 28 - SCREEN_H / 54);
		
		g.setFont(new Font("Arial Black", Font.BOLD, SCREEN_W / 120));
		
		g.drawImage(inventoryGUI.getSprite(), SCREEN_W / 2 - (inventoryGUI.getWidth() / 2), SCREEN_H / 2 - (inventoryGUI.getHeight() / 2), inventoryGUI.getWidth(), inventoryGUI.getHeight(), null);
		for (int i = 0; i < PlayerInventory.VERTICAL_SLOTS; i++)
			for (int j = 0; j < PlayerInventory.ORIZONTAL_SLOTS; j++)
				g.drawImage(inventoryTile.getSprite(), calculateInventoryXPosition(j), calculateInventoryYPosition(i), inventoryTile.getWidth(), inventoryTile.getHeight(), null);
		
		for (int i = 0; i < descriptionsDisplayed.length; i++)
		{
			for (int j = 0; j < descriptionsDisplayed[i].length; j++)
			{
				if (itemsIndex < items.size())
				{
					g.drawImage(items.get(itemsIndex).getSprite(), calculateInventoryXPosition(j), calculateInventoryYPosition(i) + SCREEN_H / 54, INVENTORY_ITEM_WIDTH, INVENTORY_ITEM_HEIGHT, null);
					g.drawString(Integer.toString(items.get(itemsIndex).getQuantity()), calculateInventoryXPosition(j) + SCREEN_W / 33, calculateInventoryYPosition(i) + SCREEN_H / 16);
					descriptionsDisplayed[i][j] = new String(items.get(itemsIndex).getDescription());
					IDs[i][j] = items.get(itemsIndex).getID();
					itemsIndex++;
				}
			}
		}
		
		g.setFont(new Font("Times New Roman", Font.BOLD + Font.ITALIC, SCREEN_W / 128));
		
		if (descriptionToDisplay != null) drawDescription(g, descriptionToDisplay, SCREEN_W / 2 + (inventoryGUI.getWidth() / 12) + (inventoryGUI.getWidth() / 400), SCREEN_H / 2 - (inventoryGUI.getHeight() / 4) + (inventoryGUI.getHeight() / 120));
		if (durability != null) g.drawString(durability, 300, 400);
		
		g.drawImage(craftingGUI.getSprite(), 0, 0, craftingGUI.getWidth(), craftingGUI.getHeight(), null);

		for (int i = 0; i < recipesToDisplay.length; i++)
		{
			g.drawImage(inventoryTile.getSprite(), calculateCraftingXPosition(), calculateCraftingYPosition(i), inventoryTile.getWidth(), inventoryTile.getHeight(), null);
			g.drawImage(new Sprite(ItemReader.getImagePath(recipesToDisplay[i]), INVENTORY_ITEM_WIDTH, INVENTORY_ITEM_HEIGHT).getSprite(), calculateCraftingXPosition(), calculateCraftingYPosition(i) + SCREEN_H / 54, INVENTORY_ITEM_WIDTH, INVENTORY_ITEM_HEIGHT, null);
		}
		
		if (recipeID != -1)
		{
			displayRecipe(g, recipeID);
			g.setFont(new Font("Arial", Font.BOLD, SCREEN_W / 120));
			g.drawString(ItemReader.getItemName(recipeID), SCREEN_W / 11, SCREEN_H / 7);
			g.setFont(new Font("Times New Roman", Font.BOLD + Font.ITALIC, SCREEN_W / 128));
			
			drawDescription(g, descriptionToDisplay, SCREEN_W / 2 + (inventoryGUI.getWidth() / 12) + (inventoryGUI.getWidth() / 400), SCREEN_H / 2 - (inventoryGUI.getHeight() / 4) + (inventoryGUI.getHeight() / 120));
		}
		if (equipableItemID != -1) drawEquipableItem(g);
		if (armorID != -1) drawArmor(g);
	}
	
	public void mouseClicked(MouseEvent click) 
	{
		int mouseX = click.getX();
		int mouseY = click.getY();

		//Controllo se sono su uno slot specifico e, in caso positivo, restituisco e richiamo l'azione da effettuare per quel particolare tipo di item
		for (int i = 0; i < descriptionsDisplayed.length; i++)
		{
			for (int j = 0; j < descriptionsDisplayed[i].length; j++)
			{
				if (isOnAnItem(mouseX, mouseY, i, j))
				{
					descriptionToDisplay = descriptionsDisplayed[i][j];
					if (playerInventory.isIDPresent(IDs[i][j]))
					{
						if (ItemReader.isEquipable(IDs[i][j]))
						{
							descriptionToDisplay += "/Durabilità: " + playerInventory.getItem(IDs[i][j]).getDurability();
							if (playerInventory.getEquipableItem().getItemID() != -1)	
								playerInventory.collectEquipableItem(playerInventory.getEquipableItem().getItemID(), playerInventory.getEquipableItem().getDurability());	
							playerInventory.setItemBeingEquipped(IDs[i][j], playerInventory.getItem(IDs[i][j]).getDurability());
							playerInventory.collectItem(IDs[i][j], -1);
							descriptionsDisplayed[i][j] = null;
							
						}
						else if (ItemReader.armorValue(IDs[i][j]) != 0)
						{
							descriptionToDisplay += "/Durabilità: " + playerInventory.getItem(IDs[i][j]).getDurability();
							if (playerInventory.getArmor().getID() != -1)	
								playerInventory.collectEquipableItem(playerInventory.getArmor().getID(), playerInventory.getArmor().getDurability());	
							playerInventory.setArmor(IDs[i][j], playerInventory.getItem(IDs[i][j]).getDurability());
							playerInventory.collectItem(IDs[i][j], -1);
							descriptionsDisplayed[i][j] = null;
						}
						else if (ItemReader.isConsumable(IDs[i][j]))
						{
							game.getAudioManager().playAudioOnce(AudioIDCollector.CONSUMABLE_ID);
							playerInventory.useConsumable(IDs[i][j], game.getPlayer());		
							playerInventory.collectItem(IDs[i][j], -1);
						}
					}
				}
			}
		}
		
		for (int i = 0; i < 9; i++)
		{
			if (isOnACraftingRecipe(mouseX, mouseY, i, 0))
			{
				recipeID = recipesToDisplay[i];	
				descriptionToDisplay = ItemReader.getItemDescription(recipeID);
			}
		}

		if (isOnEquipableItem(mouseX, mouseY) && playerInventory.getEquipableItem().getItemID() != -1)
		{
			playerInventory.collectEquipableItem(playerInventory.getEquipableItem().getItemID(), playerInventory.getEquipableItem().getDurability());
			playerInventory.setPunch();
		}
		
		if (isOnArmor(mouseX, mouseY) && armorID != -1)
		{
			playerInventory.collectEquipableItem(playerInventory.getArmor().getID(), playerInventory.getArmor().getDurability());
			playerInventory.removeArmor();
		}
		System.out.println((calculateCraftingYPosition(0)));
		panel.requestFocusInWindow();
	}
	
	public static void drawDescription(Graphics g, String description, int x, int y) 
	{
		if (description != null)
			for (String line : description.split("/"))
				g.drawString(line, x, y += g.getFontMetrics().getHeight());
	}
	
	public void displayRecipe(Graphics g, int ID)
	{
		for (int i = 0; i < Crafter.recipes[ID].getRecipeSize(); i++)
		{
			g.drawImage(new Sprite(ItemReader.getImagePath(Crafter.recipes[ID].getRecipe().get(i).getFirstElement()), INVENTORY_ITEM_WIDTH, INVENTORY_ITEM_HEIGHT).getSprite(), calculateCraftingXPosition() + inventoryTile.getWidth() * 2 - (inventoryTile.getWidth()  / 4), calculateCraftingYPosition(i) + inventoryTile.getHeight() / 4, INVENTORY_ITEM_WIDTH, INVENTORY_ITEM_HEIGHT, null);
			g.setFont(new Font("Arial", Font.BOLD, SCREEN_W / 87));
			g.drawString("x " + Integer.toString(Crafter.recipes[ID].getRecipe().get(i).getSecondElement()), calculateCraftingXPosition() + inventoryTile.getWidth() * 3, calculateCraftingYPosition(i) + inventoryTile.getHeight() / 2 + (inventoryTile.getHeight() / 12));
		}
	}
	
	public void mouseEntered(MouseEvent e) { }
	public void mouseExited(MouseEvent e) { }
	public void mousePressed(MouseEvent e) { }
	public void mouseReleased(MouseEvent e) { }
	
	public void drawEquipableItem(Graphics g)
	{
		g.drawImage(new Sprite(ItemReader.getImagePath(equipableItemID), INVENTORY_ITEM_WIDTH, INVENTORY_ITEM_HEIGHT).getSprite(), SCREEN_W / 3 + SCREEN_W / 21, SCREEN_H / 3 + SCREEN_H / 35, INVENTORY_ITEM_WIDTH, INVENTORY_ITEM_HEIGHT, null);
	}
	
	public void drawArmor(Graphics g)
	{
		g.drawImage(new Sprite(ItemReader.getImagePath(armorID), INVENTORY_ITEM_WIDTH, INVENTORY_ITEM_HEIGHT).getSprite(), SCREEN_W / 2 - SCREEN_W / 20 + SCREEN_W / 600, SCREEN_H / 3 + SCREEN_H / 35, INVENTORY_ITEM_WIDTH, INVENTORY_ITEM_HEIGHT, null);
	}
	
	private boolean isOnAnItem(int mouseX, int mouseY, int posI, int posJ)
	{
		if (mouseX < (calculateInventoryXPosition(posJ) + (INVENTORY_ITEM_WIDTH + SCREEN_W / 192)) && mouseX > (calculateInventoryXPosition(posJ) + (INVENTORY_ITEM_WIDTH / 8)))
			if (mouseY < (calculateInventoryYPosition(posI)  + SCREEN_H / 10) && mouseY > (calculateInventoryYPosition(posI)  + SCREEN_H / 33))
				return true;
		return false;
	}
	private boolean isOnACraftingRecipe(int mouseX, int mouseY, int posI, int posJ)
	{
		if (mouseX < (calculateCraftingXPosition() + (INVENTORY_ITEM_WIDTH + SCREEN_W / 192)) && mouseX > (calculateCraftingXPosition() + (INVENTORY_ITEM_WIDTH / 8)))
			if (mouseY < (calculateCraftingYPosition(posI)  + SCREEN_H / 15) && mouseY > (calculateCraftingYPosition(posI)))
				return true;
		return false;
	}
	private boolean isOnEquipableItem(int mouseX, int mouseY)
	{
		if (mouseX < (SCREEN_W / 3 + SCREEN_W / 11 + SCREEN_W / 274) && mouseX > (SCREEN_W / 3 + SCREEN_W / 20))
			if (mouseY < (SCREEN_H / 2 - SCREEN_H / 22) && mouseY > (SCREEN_H / 3 + SCREEN_H / 41))
				return true;
		return false;
	}
	private boolean isOnArmor(int mouseX, int mouseY)
	{
		if (mouseX < (SCREEN_W / 2 + SCREEN_W / 320) && mouseX > (SCREEN_W / 3 + SCREEN_W / 8 + SCREEN_W / 160))
			if (mouseY < (SCREEN_H / 3 + SCREEN_H / 8 - SCREEN_W / 360) && mouseY > (SCREEN_H / 3 + SCREEN_H / 38))
				return true;
		return false;
	}
}