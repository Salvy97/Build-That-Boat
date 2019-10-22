package entities.inanimate;

import entities.Entity;
import entities.items.manipulators.ItemIDCollector;
import globals.Pair;

import java.util.Random;
import static globals.ConstantsCollection.*;

public class Rock extends Entity implements InanimateEntity
{
	private int pebbles;
	
	public Rock(int x, int y)
	{
		super(x, y, ROCK_WIDTH, ROCK_HEIGHT, 75, 0, ROCK_SPRITE_PATH, false);
		Random random = new Random();
		pebbles = random.nextInt(6) + 1;
		setHitBox(this.x, this.y, ROCK_WIDTH, ROCK_HEIGHT);
	}
	
	public Pair<Integer, Integer> drop() { return new Pair<Integer, Integer>(ItemIDCollector.PEBBLE_ID, pebbles); }
	
	public void setPebbles(int pebbles) { this.pebbles = pebbles; }
}