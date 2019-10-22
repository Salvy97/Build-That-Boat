package entities.living;

import static globals.ConstantsCollection.*;
import core.audio.AudioIDCollector;
import core.audio.AudioManager;
import entities.Animator;
import entities.items.manipulators.ItemIDCollector;
import globals.KeysDefiner;
import globals.Pair;

public class Deer extends Neutral implements Animator
{
	public Deer(int x, int y, AudioManager audioManager)
	{
		super(x, y, DEER_WIDTH, DEER_HEIGHT, 75, 2, DEER_SPRITE_PATH[KeysDefiner.DOWN][0], true, audioManager);
		setHitBox(x + SCREEN_W / 96, y + SCREEN_H / 135, sprite.getWidth() - SCREEN_W / 48, sprite.getHeight() - SCREEN_H / 96);
	}

	public void animate() 
	{
		if (this.hp > 0)
			sprite.setSprite(DEER_SPRITE_PATH[direction][animation]);
		else if (this.hp <= 0)
		{
			if (!animationResetted)
			{
				animation = 0;
				animationResetted = true;
			}
			sprite.setSprite(DEER_SPRITE_PATH[DEATH][animation]);
		}
		if (animation == 0) animation = 1;
		else if (animation == 1) animation = 0;
	}

	public Pair<Integer, Integer> drop() { return new Pair<Integer, Integer>(ItemIDCollector.DEER_MEAT_ID, 1); }
	public int[] getAudiosID() 
	{ 
		int audios[] = { AudioIDCollector.DEER_NOISE_ID, AudioIDCollector.DEER_PAIN_ID, AudioIDCollector.DEER_DEATH_ID }; 
		return audios;
	}
}