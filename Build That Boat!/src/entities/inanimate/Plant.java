package entities.inanimate;

import entities.Entity;
import static globals.ConstantsCollection.*;

public abstract class Plant extends Entity implements InanimateEntity
{
	public static final int PLANT_WIDTH = SCREEN_W / 30;
	public static final int PLANT_HEIGHT = SCREEN_H / 15;
	
	public Plant(int x, int y, String spritePath)
	{
		super(x, y, PLANT_WIDTH, PLANT_HEIGHT, 20, 0, spritePath, false);
		setHitBox(x, y, PLANT_WIDTH, PLANT_HEIGHT);
	}
}