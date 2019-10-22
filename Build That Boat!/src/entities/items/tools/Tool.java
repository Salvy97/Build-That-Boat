package entities.items.tools;

import static globals.ConstantsCollection.SCREEN_H;
import static globals.ConstantsCollection.SCREEN_W;
import entities.items.EquipableEntity;
import globals.KeysDefiner;

public abstract class Tool extends EquipableEntity
{
	protected static final int WIDTH = SCREEN_W / 64;
	protected static final int HEIGHT = SCREEN_W / 36;
	
	public Tool(int durability) { super(WIDTH, HEIGHT, null, durability); }
	
	public void use(int playerX, int playerY, int direction) 
	{ 
		switch (direction)
		{
			case KeysDefiner.UP:	setHitBox(playerX + SCREEN_W / 70, playerY - SCREEN_H / 50, WIDTH, HEIGHT); 
									break;
			case KeysDefiner.DOWN:	setHitBox(playerX + SCREEN_W / 70, playerY + SCREEN_H / 16, WIDTH, HEIGHT);  
									break;
			case KeysDefiner.LEFT:	setHitBox(playerX - SCREEN_W / 200, playerY + SCREEN_H / 40, WIDTH, HEIGHT); 
									break;
			case KeysDefiner.RIGHT:	setHitBox(playerX + SCREEN_W / 28, playerY + SCREEN_H / 40, WIDTH, HEIGHT); 
									break;				
		}
	}
}