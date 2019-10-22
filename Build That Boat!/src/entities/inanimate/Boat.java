package entities.inanimate;

import entities.Entity;
import globals.Sprite;
import graphics.Tile;
import static globals.ConstantsCollection.*;

public class Boat extends Entity
{
	private int woodGiven;
	
	public Boat()
	{
		super(Tile.calculateXPosition(TILE_SCALE - 1), Tile.calculateYPosition(TILE_SCALE / 2), BOAT_WIDTH, BOAT_HEIGHT, 0, 0, null, false);
		setHitBox(x, y, BOAT_WIDTH, BOAT_HEIGHT);
		woodGiven = 0;
	}
	
	public void changeSprite()
	{
		if (woodGiven >= 25 && woodGiven < 50)
			this.sprite = new Sprite(BOAT_SPRITE_PATH[0], BOAT_WIDTH, BOAT_HEIGHT);
		else if (woodGiven >= 50 && woodGiven < 75)
			this.getSpriteManager().setSprite(BOAT_SPRITE_PATH[1]);
		else if (woodGiven >= 75 && woodGiven < 100)
			this.getSpriteManager().setSprite(BOAT_SPRITE_PATH[2]);
		else if (woodGiven >= 100)
			//this.getSpriteManager().setSprite(BOAT_SPRITE_PATH[3]);
			this.sprite = new Sprite(BOAT_SPRITE_PATH[3], BOAT_WIDTH, BOAT_HEIGHT);
	}
	
	public int getWoodGiven() { return woodGiven; }
	
	public void setWoodGiven(int woodGiven) { this.woodGiven = woodGiven; }
}