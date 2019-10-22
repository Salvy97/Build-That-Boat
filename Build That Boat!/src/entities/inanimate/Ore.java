package entities.inanimate;

import entities.Entity;
import entities.items.manipulators.ItemIDCollector;
import globals.Pair;
import static globals.ConstantsCollection.*;

public class Ore extends Entity implements InanimateEntity
{
	public static final int WIDTH = SCREEN_W / 30;
	public static final int HEIGHT = SCREEN_H / 30;
	
	public Ore(int x, int y) 
	{ 
		super(x, y, WIDTH, HEIGHT, 150, 0, IRON_ORE_PATH, false); 
		setHitBox(x, y, WIDTH, HEIGHT);
	}
	
	public Pair<Integer, Integer> drop() { return new Pair<Integer, Integer>(ItemIDCollector.IRON_ID, 2); }
}
