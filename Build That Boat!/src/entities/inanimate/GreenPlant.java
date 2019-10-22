package entities.inanimate;

import entities.items.manipulators.ItemIDCollector;
import globals.Pair;
import java.util.Random;
import static globals.ConstantsCollection.GREEN_PLANT_SPRITE_PATH;

public class GreenPlant extends Plant
{
	public GreenPlant(int x, int y) { super(x, y, GREEN_PLANT_SPRITE_PATH); }

	public Pair<Integer, Integer> drop() { return new Pair<Integer, Integer>(ItemIDCollector.GRASS_LEAF_ID, new Random().nextInt(3) + 1); }
}