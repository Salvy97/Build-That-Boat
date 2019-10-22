package entities.inanimate;

import java.util.Random;
import entities.items.manipulators.ItemIDCollector;
import globals.Pair;
import static globals.ConstantsCollection.YELLOW_PLANT_SPRITE_PATH;

public class YellowPlant extends Plant
{	
	public YellowPlant(int x, int y) { super(x, y, YELLOW_PLANT_SPRITE_PATH); }
	
	public Pair<Integer, Integer> drop() { return new Pair<Integer, Integer>(ItemIDCollector.YELLOW_FLOWER_ID, new Random().nextInt(3) + 1); }
}
