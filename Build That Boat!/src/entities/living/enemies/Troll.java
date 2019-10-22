package entities.living.enemies;

import static globals.ConstantsCollection.*;

import core.audio.AudioIDCollector;
import core.audio.AudioManager;
import entities.Animator;
import entities.items.manipulators.ItemIDCollector;
import globals.KeysDefiner;
import globals.Pair;

public class Troll extends Enemy implements Animator
{
	public Troll(int x, int y, AudioManager audioManager)
	{
		super(x, y, TROLL_WIDTH, TROLL_HEIGHT, 200, 1, TROLL_SPRITE_PATH[KeysDefiner.DOWN][0], true, audioManager);
		setHitBox(x + SCREEN_W / 96, y + SCREEN_H / 135, sprite.getWidth() - SCREEN_W / 48, sprite.getHeight() - SCREEN_H / 96);
	}

	public int damage() { return 15; }
	public Pair<Integer, Integer> drop() { return new Pair<Integer, Integer>(ItemIDCollector.TROLL_HORN_ID, 1); }
	
	public void animate() 
	{
		if (this.hp > 0)
			sprite.setSprite(TROLL_SPRITE_PATH[direction][animation]);
		else if (this.hp <= 0)
		{
			if (!animationResetted)
			{
				animation = 0;
				animationResetted = true;
			}
			sprite.setSprite(TROLL_SPRITE_PATH[DEATH][animation]);
		}
		if (animation == 0) animation = 1;
		else if (animation == 1) animation = 0;
	}
	public int[] getAudiosID()
	{
		int[] audios = { AudioIDCollector.TROLL_NOISE_ID, AudioIDCollector.TROLL_PAIN_ID, AudioIDCollector.TROLL_DEATH_ID };
		return audios;
	}
}