package entities.living.enemies;

import globals.KeysDefiner;
import globals.Pair;
import static globals.ConstantsCollection.*;

import core.audio.AudioIDCollector;
import core.audio.AudioManager;
import entities.Animator;
import entities.items.manipulators.ItemIDCollector;

public class Goblin extends Enemy implements Animator
{
	public Goblin(int x, int y, AudioManager audioManager) 
	{
		super(x, y, GOBLIN_WIDTH, GOBLIN_HEIGHT, 100, 1, GOBLIN_SPRITE_PATH[KeysDefiner.DOWN][0], true, audioManager);
		setHitBox(x + SCREEN_W / 96, y + SCREEN_H / 135, sprite.getWidth() - SCREEN_W / 48, sprite.getHeight() - SCREEN_H / 96);
	}
	
	public int damage() { return 8; }
	public Pair<Integer, Integer> drop() { return  new Pair<Integer, Integer>(ItemIDCollector.GOBLIN_EAR_ID, 2); }
	
	public void animate() 
	{
		if (this.hp > 0)
			sprite.setSprite(GOBLIN_SPRITE_PATH[direction][animation]);
		else if (this.hp <= 0)
		{
			if (!animationResetted)
			{
				animation = 0;
				animationResetted = true;
			}
			sprite.setSprite(GOBLIN_SPRITE_PATH[DEATH][animation]);
		}
		if (animation == 0) animation = 1;
		else if (animation == 1) animation = 0;
	}
	public int[] getAudiosID() 
	{
		int[] audios = { AudioIDCollector.GOBLIN_NOISE_ID, AudioIDCollector.GOBLIN_PAIN_ID, AudioIDCollector.GOBLIN_DEATH_ID };
		return audios;
	}
}