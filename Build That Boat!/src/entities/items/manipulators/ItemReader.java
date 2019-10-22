package entities.items.manipulators;

import globals.FileManager;

public class ItemReader extends FileManager
{
	private static final int ITEM_NAME_ROW = 0;
	private static final int ITEM_DESCRIPTION_ROW = 1;
	private static final int IS_EQUIPABLE_ROW = 2;
	private static final int IS_CONSUMABLE_ROW = 3;
	private static final int ARMOR_VALUE_ROW = 4;
	private static final int DURABILITY_ID = 5;
	
	private static String convertID(int ID) { return Integer.toString(ID); }
	public static String getImagePath(int ID) { return "items/" + convertID(ID) + ".png"; }
	public static String getItemPath(int ID) { return "items/" + convertID(ID) + ".txt"; }
	
	public static String getRowValue(String row)
	{
		int pos = 0;
		for (int i = 0; i < row.length(); i++)
		{
			if (row.charAt(i) == '=')
			{
				pos = i;
		 		break;
			}
		}
		String value = ""; 
		for (int i = pos + 1; i < row.length(); i++)
			value += row.charAt(i);
		return value;
	}
	
	public static String getItemName(int ID)
	{
		 String row = content(getItemPath(ID)).get(ITEM_NAME_ROW);
		 return getRowValue(row);	 
	}
	
	public static String getItemDescription(int ID)
	{
		String row = content(getItemPath(ID)).get(ITEM_DESCRIPTION_ROW);
		return getRowValue(row);
	}
	
	public static boolean isEquipable(int ID)
	{
		String row = content(getItemPath(ID)).get(IS_EQUIPABLE_ROW);
		if (Integer.parseInt(getRowValue(row)) == 0)
			return false;
		return true;		
	}
	
	public static boolean isConsumable(int ID)
	{
		String row = content(getItemPath(ID)).get(IS_CONSUMABLE_ROW);
		if (Integer.parseInt(getRowValue(row)) == 0)
			return false;
		return true;
	}
	
	public static int armorValue(int ID)
	{
		String row = content(getItemPath(ID)).get(ARMOR_VALUE_ROW);
		return Integer.parseInt(getRowValue(row));
	}
	
	public static int durability(int ID)
	{
		String row = content(getItemPath(ID)).get(DURABILITY_ID);
		return Integer.parseInt(getRowValue(row));
	}
}