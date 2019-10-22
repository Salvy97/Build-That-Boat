package entities.living.enemies;

import java.util.Random;
import java.util.Timer;

import core.audio.AudioIDCollector;
import core.audio.AudioManager;
import entities.Entity;
import entities.living.Player;
import globals.KeysDefiner;
import globals.Pair;

public abstract class Enemy extends Entity
{
	public static final int ENEMY_NOISE = 0;
	public static final int ENEMY_PAIN = 1;
	public static final int ENEMY_DEATH = 2;
	
	protected int randomAxis;	
	protected boolean animationResetted;
	private Timer noiseAudioTimer;
	
	public Enemy(int x, int y, int width, int height, int hp, int speed, String spritePath, boolean canMove, AudioManager audioManager) 
	{
		super(x, y, width, height, hp, speed, spritePath, canMove);
		Random random = new Random();
		randomAxis = random.nextInt(2) + 1;
		animationResetted = false;
		noiseAudioTimer = new Timer();
		audioManager.playAudioEveryXSeconds(getAudiosID()[ENEMY_NOISE], noiseAudioTimer, 10);
	}
	
	public abstract int damage();
	public abstract Pair<Integer, Integer> drop();
	public abstract int[] getAudiosID(); //ritorna null se non ha suono
	
	public void AI(Player player, AudioManager audioManager) 
	{
		if (canMove)
		{
			if (randomAxis == 1)
			{
				int playerXPos = player.getX();
				if (playerXPos < this.x)
				{
					this.x -= this.speed;
					this.hitBox.addX(-(this.speed));
					direction = KeysDefiner.LEFT;
				}
				else if (playerXPos > this.x)
				{
					this.x += this.speed;
					this.hitBox.addX(this.speed);
					direction = KeysDefiner.RIGHT;
				}
				else if (playerXPos == this.x || this.x == playerXPos + 1)
					randomAxis = 2;
			}
			else if (randomAxis == 2)
			{
				int playerYPos = player.getY();
				if (playerYPos < this.y)
				{
					this.y -= this.speed;
					this.hitBox.addY(-(this.speed));
					direction = KeysDefiner.UP;
				}
				else if (playerYPos > this.y)
				{
					this.y += this.speed;
					this.hitBox.addY(this.speed);
					direction = KeysDefiner.DOWN;
				}
				else if (playerYPos == this.y || this.y == playerYPos + 1)
					randomAxis = 1;
			}
		}	
		if (this.detectCollision(player))
		{	
			audioManager.playAudioOnce(AudioIDCollector.PLAYER_HITTED_ID);
			switch (this.direction)
			{
				case KeysDefiner.UP: 	this.push(KeysDefiner.DOWN, 60);										
								 		break;
				case KeysDefiner.DOWN: 	this.push(KeysDefiner.UP, 60);
		 								break;
				case KeysDefiner.LEFT: 	this.push(KeysDefiner.RIGHT, 60);
		 								break;
				case KeysDefiner.RIGHT: this.push(KeysDefiner.LEFT, 60);
		 								break;
			}		
			player.push(this.direction, 60);
			player.setHP(player.getHP() - (this.damage() - (player.calculateArmorValue(this.damage()))));
			if (player.getInventory().getArmor() != null) 
			{
				player.getInventory().getArmor().decreaseDurability();
				if (player.getInventory().getArmor().getDurability() == 0)
					player.getInventory().removeArmor();
			}
		}
	}
	
	public Timer getTimer() { return noiseAudioTimer; }
}