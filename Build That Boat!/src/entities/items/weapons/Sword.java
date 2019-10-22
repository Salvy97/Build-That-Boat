package entities.items.weapons;

import static globals.ConstantsCollection.SCREEN_H;
import static globals.ConstantsCollection.SCREEN_W;
import static globals.ConstantsCollection.SWORD_HEIGHT;
import static globals.ConstantsCollection.SWORD_WIDTH;
import core.audio.AudioIDCollector;
import globals.KeysDefiner;
import globals.Sprite;

public abstract class Sword extends Weapon
{
	public Sword(int width, int height, String spritePath, int durability) { super(width, height, spritePath, durability); }
	
	public void use(int playerX, int playerY, int direction)
	{
		switch (direction)
		{
			case KeysDefiner.UP:	this.sprite = new Sprite(getSprite(direction), SWORD_WIDTH, SWORD_HEIGHT);
									this.setWidth(SWORD_WIDTH);
									this.setHeight(SWORD_HEIGHT);
									this.x = playerX + SCREEN_W / 273;
									this.y = playerY - SCREEN_H / 154;
									break;
			case KeysDefiner.DOWN: 	this.sprite = new Sprite(getSprite(direction), SWORD_WIDTH, SWORD_HEIGHT);
									this.setWidth(SWORD_WIDTH);
									this.setHeight(SWORD_HEIGHT);
									this.x = playerX + SCREEN_W / 273;
									this.y = playerY + SCREEN_H / 20;
								  	break;
			case KeysDefiner.LEFT: 	this.sprite = new Sprite(getSprite(direction), SWORD_HEIGHT, SWORD_WIDTH);
									this.setWidth(SWORD_HEIGHT);
									this.setHeight(SWORD_WIDTH);
									this.x = playerX - SCREEN_W / 88;
									this.y = playerY + SCREEN_H / 26;
		  							break;
			case KeysDefiner.RIGHT: this.sprite = new Sprite(getSprite(direction), SWORD_HEIGHT, SWORD_WIDTH);
									this.setWidth(SWORD_HEIGHT);
									this.setHeight(SWORD_WIDTH);
									this.x = playerX + SCREEN_W / 34;
									this.y = playerY + SCREEN_H / 24;
		  							break;
		}
		this.setHitBox(this.getX(), this.getY(), this.getWidth(), this.getHeight());
	}
	public void disable()
	{
		this.sprite = null;
		this.hitBox = null;
	}
	public boolean isDisabled() { return sprite == null && hitBox == null; }
	public int getAudioID() { return AudioIDCollector.SWORD_ID; }
	
	public abstract String getSprite(int direction);
}
