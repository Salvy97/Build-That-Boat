package entities.inanimate;

import java.util.Random;
import entities.items.manipulators.ItemIDCollector;
import globals.Pair;
import static globals.ConstantsCollection.RED_PLANT_SPRITE_PATH;

public class RedPlant extends Plant
{
	public RedPlant(int x, int y) { super(x, y, RED_PLANT_SPRITE_PATH); }

	public Pair<Integer, Integer> drop() { return new Pair<Integer, Integer>(ItemIDCollector.RED_FLOWER_ID, new Random().nextInt(3) + 1); }
}