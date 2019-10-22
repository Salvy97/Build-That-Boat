package entities.living;

import static globals.ConstantsCollection.SCREEN_H;
import static globals.ConstantsCollection.SCREEN_W;
import java.util.Random;
import java.util.Timer;
import core.audio.AudioManager;
import entities.Entity;
import globals.KeysDefiner;
import globals.Pair;

public abstract class Neutral extends Entity
{
	public static final int NEUTRAL_NOISE = 0;
	public static final int NEUTRAL_PAIN = 1;
	public static final int NEUTRAL_DEATH = 2;
	
	protected int randomAxis;	
	protected boolean animationResetted;
	private Timer noiseAudioTimer;
	
	protected int xDestination;
	protected int yDestination;
	protected int spotsNumber;
	protected boolean lastDestinationReached;
	
	protected Neutral(int x, int y, int width, int height, int hp, int speed, String spritePath, boolean canMove, AudioManager audioManager) 
	{
		super(x, y, width, height, hp, speed, spritePath, canMove);
		Random random = new Random();
		randomAxis = random.nextInt(2) + 1;
		animationResetted = false;
		noiseAudioTimer = new Timer();
		audioManager.playAudioEveryXSeconds(getAudiosID()[NEUTRAL_NOISE], noiseAudioTimer, 10);
		xDestination = random.nextInt(SCREEN_W / 2) * 2;
		yDestination = random.nextInt(SCREEN_H / 2) * 2;
		spotsNumber = 1;
		lastDestinationReached = false;
	}

	public abstract Pair<Integer, Integer> drop();
	public abstract int[] getAudiosID(); //ritorna null se non ha suono
	
	public void AI()
	{
		Random random = new Random();
		speed = random.nextInt(2) + 1;
		if (canMove)
		{
			if (randomAxis == 1)
			{
				if (xDestination < this.x)
				{
					this.x -= this.speed;
					this.hitBox.addX(-(this.speed));
					direction = KeysDefiner.LEFT;
				}
				else if (xDestination > this.x)
				{
					this.x += this.speed;
					this.hitBox.addX(this.speed);
					direction = KeysDefiner.RIGHT;
				}
				else if (xDestination == this.x)
					randomAxis = 2;
			}
			else if (randomAxis == 2)
			{
				if (yDestination < this.y)
				{
					this.y -= this.speed;
					this.hitBox.addY(-(this.speed));
					direction = KeysDefiner.UP;
				}
				else if (yDestination > this.y)
				{
					this.y += this.speed;
					this.hitBox.addY(this.speed);
					direction = KeysDefiner.DOWN;
				}
				else if (yDestination == this.y)
					randomAxis = 1;
			}
			if (yDestination == this.y && xDestination == this.x)
			{
				if (spotsNumber == 1)
				{
					xDestination = random.nextInt(SCREEN_W / 2) * 2;
					yDestination = random.nextInt(SCREEN_H / 2) * 2;
					spotsNumber++;
				}
				else if (spotsNumber == 2)
				{
					xDestination = -(SCREEN_W / 21);
					yDestination = -(SCREEN_H / 12);
					spotsNumber++;
				}
				else lastDestinationReached = true;
			}
		}
	}
	
	public Timer getTimer() { return noiseAudioTimer; }
	
	public boolean getLastDestinationReached() { return lastDestinationReached; }
}