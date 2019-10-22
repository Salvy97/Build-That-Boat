package entities.living.enemies;

import static globals.ConstantsCollection.*;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import core.audio.AudioIDCollector;
import core.audio.AudioManager;
import entities.Animator;
import entities.items.manipulators.ItemIDCollector;
import entities.living.Player;
import globals.KeysDefiner;
import globals.Pair;

public class GoldenSnake extends Enemy implements Animator
{
	private ArrayList<GoldenSnakeSpit> goldenSnakeSpits;
	private Timer goldenSnakeSpitTimer;
	
	public GoldenSnake(int x, int y, AudioManager audioManager)
	{
		super(x, y, GOLDEN_SNAKE_WIDTH, GOLDEN_SNAKE_HEIGHT, 150, 1, GOLDEN_SNAKE_SPRITE_PATH[KeysDefiner.DOWN][0], true, audioManager);
		setHitBox(x, y, GOLDEN_SNAKE_WIDTH, GOLDEN_SNAKE_HEIGHT);
		goldenSnakeSpits = new ArrayList<GoldenSnakeSpit>();
		
		goldenSnakeSpitTimer = new Timer();
		goldenSnakeSpitTimer.scheduleAtFixedRate(new TimerTask()
		{
			public void run()
			{
				spit();
			}
		}, 1, 5000);
	}
	
	public int damage() { return 10; }
	public Pair<Integer, Integer> drop() { return new Pair<Integer, Integer>(ItemIDCollector.ARROW_ID, 4); }
	
	public void animate()
	{
		if (this.hp > 0)
			sprite.setSprite(GOLDEN_SNAKE_SPRITE_PATH[direction][animation]);
		else if (this.hp <= 0)
		{
			if (!animationResetted)
			{
				animation = 0;
				animationResetted = true;
			}
			sprite.setSprite(GOLDEN_SNAKE_SPRITE_PATH[DEATH][animation]);
		}
		if (animation == 0) animation = 1;
		else if (animation == 1) animation = 0;
	}
	
	public int[] getAudiosID()
	{
		int[] audios = { AudioIDCollector.GOLDEN_SNAKE_NOISE_ID, AudioIDCollector.GOLDEN_SNAKE_PAIN_ID, AudioIDCollector.GOLDEN_SNAKE_DEATH_ID };
		return audios;
	}
	
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
			player.setHP(player.getHP() - (this.damage() - player.calculateArmorValue(this.damage())));
		}
	}
	
	public void spit()
	{
		GoldenSnakeSpit spit = new GoldenSnakeSpit(this.x, this.y, KeysDefiner.DOWN);
		switch (direction)
		{
			case KeysDefiner.UP: 	spit = new GoldenSnakeSpit(this.x, this.y, KeysDefiner.UP);
									break;
			case KeysDefiner.LEFT: 	spit = new GoldenSnakeSpit(this.x, this.y + SCREEN_H / 27, KeysDefiner.LEFT);
									break;
			case KeysDefiner.RIGHT: spit = new GoldenSnakeSpit(this.x, this.y + SCREEN_H / 27, KeysDefiner.RIGHT);
									break;
		}
		goldenSnakeSpits.add(spit);
	}
	
	public ArrayList<GoldenSnakeSpit> getSpits() { return goldenSnakeSpits; }
	public Timer getSpitTimer() { return goldenSnakeSpitTimer; }
}