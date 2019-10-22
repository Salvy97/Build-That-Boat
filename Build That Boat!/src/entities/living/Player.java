package entities.living;

import entities.Animator;
import entities.Entity;
import globals.KeysDefiner;
import static globals.ConstantsCollection.*;
import java.util.Timer;
import java.util.TimerTask;
import core.PlayerInventory;

public class Player extends Entity implements Animator
{	
	private KeysDefiner keysDefiner;
	private PlayerInventory inventory;
	
	private int vigor;
	private int vigorRegenerationRate;
	private Timer vigorRegeneration;
	
	private int hunger;
	private Timer hungerIncrease;
	
	private int kills;
	
	public Player(int x, int y) 
	{
		super(x, y, PLAYER_WIDTH, PLAYER_HEIGHT, 100, 8, PLAYER_SPRITE_PATH[KeysDefiner.DOWN][STILL], true);
		keysDefiner = new KeysDefiner();
		inventory = new PlayerInventory();
		setHitBox(x + SCREEN_W / 75, y + SCREEN_H / 35, sprite.getWidth() - SCREEN_W / 40, sprite.getHeight() - SCREEN_H / 20);
		vigor = 100;
		vigorRegenerationRate = 10;
		vigorRegeneration = new Timer();
		vigorRegeneration.scheduleAtFixedRate(new TimerTask() 
		{ 
			public void run() 
			{ 
				if (vigor < 100 && vigor + vigorRegenerationRate <= 100) 
					vigor += vigorRegenerationRate; 
				else
					vigor = 100;
			} 
		}, 1, 1000);
		hunger = 100;
		hungerIncrease = new Timer();
		hungerIncrease.scheduleAtFixedRate(new TimerTask() 
		{ 
			public void run() 
			{ 
				if (hunger > 0) 
					hunger--; 
			} 
		}, 5 * 1000, 5 * 1000);
		kills = 0;
	}
	
	public KeysDefiner getKeysDefiner() { return keysDefiner; }
	public PlayerInventory getInventory() { return inventory; }
	public int getVigor() { return vigor; }
	public int getHunger() { return hunger; }
	public int getKills() { return kills; }
	
	public void setVigor(int vigor) { this.vigor = vigor; }
	public void decreaseHunger(int hunger) { this.hunger += hunger; }
	public void increaseKills() { kills++; }
	
	public void movement(int direction)
	{
		if (canMove)
		{
			switch (direction)
			{
				case KeysDefiner.UP:    this.y -= this.speed;
									    this.hitBox.addY(-speed);
								        break;
				case KeysDefiner.DOWN:	this.y += this.speed;
										this.hitBox.addY(speed);
										break;
				case KeysDefiner.LEFT:	this.x -= this.speed;
										this.hitBox.addX(-speed);
										break;
				case KeysDefiner.RIGHT: this.x += this.speed;
										this.hitBox.addX(speed);
										break;
			}
		}
	}
	
	public boolean isMoving()
	{
		boolean isMoving = false;
		for (int i = 0; i < 4; i++)
			if (keysDefiner.getKeyState(i))
				isMoving = true;
		return isMoving;
	}

	public void animate() 
	{
		if (canMove)
		{
			for (int i = 0; i < 4; i++)
				if (keysDefiner.getKeyState(i))
					direction = i;
			if (isMoving())
			{
				sprite.setSprite(PLAYER_SPRITE_PATH[direction][animation]);
				if (animation == 1) animation = 2;
				else if (animation == 2) animation = 1;
			}
			else
				sprite.setSprite(PLAYER_SPRITE_PATH[direction][STILL]);
		}
		else
			sprite.setSprite(PLAYER_SPRITE_PATH[direction][STILL]);
	}
	
	public void removeVigor() { this.vigor -= inventory.getEquipableItem().getVigorUsed(); }
	
	public void push(int direction, int value)
	{
		switch (direction)
		{
			case KeysDefiner.UP:	this.y -= value;
								 	this.hitBox.addY(-value);					 	
								 	break;
			case KeysDefiner.DOWN:  this.y += value;
		 							this.hitBox.addY(value);
		 							break;
			case KeysDefiner.LEFT:  this.x -= value;
									this.hitBox.addX(-value);
									break;
			case KeysDefiner.RIGHT: this.x += value;
									this.hitBox.addX(value);
									break;
		}
		if (inventory.getEquipableItem().getItemID() != -1 && !inventory.getEquipableItem().isDisabled())
		{
			switch (direction)
			{
				case KeysDefiner.UP:	((Entity)inventory.getEquipableItem()).setY(((Entity)inventory.getEquipableItem()).getY() - value);				 	
									 	break;
				case KeysDefiner.DOWN:  ((Entity)inventory.getEquipableItem()).setY(((Entity)inventory.getEquipableItem()).getY() + value);	
			 							break;
				case KeysDefiner.LEFT:  ((Entity)inventory.getEquipableItem()).setX(((Entity)inventory.getEquipableItem()).getX() - value);	
										break;
				case KeysDefiner.RIGHT: ((Entity)inventory.getEquipableItem()).setX(((Entity)inventory.getEquipableItem()).getX() + value);	
										break;
			}
		}
	}
	
	public int calculateArmorValue(int damageTaken)
	{
		float damageReduction = (inventory.getArmor().getArmorClass() * damageTaken) / 100.0f;
		return (int)(Math.round(damageReduction));
	}
	
	public void setVigorRegeneration(int vigorRegenerationRate) { this.vigorRegenerationRate = vigorRegenerationRate; }
}