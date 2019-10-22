package entities.inanimate;

import entities.Entity;
import entities.items.manipulators.ItemIDCollector;
import globals.Pair;
import java.util.Random;
import static globals.ConstantsCollection.*;

public class Tree extends Entity implements InanimateEntity
{
	private int logs;
	public enum GrowState { SAPLING, ALMOST_GROWN, FULL_GROWN }
	private GrowState growState;
	private long secondsLeftUntilNextState;
	
	public final static int SAPLING_INDEX = 0;
	public final static int FULL_GROWN_INDEX = 1;

	public Tree(int x, int y, boolean startingTree)
	{
		super(x, y, TREE_WIDTH / 4, TREE_HEIGHT / 4, 40, 0, TREE_SPRITE_PATH[SAPLING_INDEX], false);
		if (startingTree)
			setFullGrownState();
		else
			growState = GrowState.SAPLING;
		Random random = new Random();
		logs = random.nextInt(3) + 1;
		secondsLeftUntilNextState = 30;
	}

	public Pair<Integer, Integer> drop() { return new Pair<Integer, Integer>(ItemIDCollector.LOG_ID, logs); }
	
	public void setLogs(int logs) {	this.logs = logs; }
	
	public GrowState getGrowState() { return growState; }
	
	@SuppressWarnings("incomplete-switch")
	public void nextState() 
	{
		switch (growState)
		{
			case SAPLING: 		growState = GrowState.ALMOST_GROWN;
								this.setWidth(TREE_WIDTH / 4);
								this.setHeight(TREE_HEIGHT / 4);
								this.getSpriteManager().setSprite(TREE_SPRITE_PATH[FULL_GROWN_INDEX]);
					   			break;
			case ALMOST_GROWN:	growState = GrowState.FULL_GROWN;		
								this.setWidth(TREE_WIDTH);
								this.setHeight(TREE_HEIGHT);
								this.setX(this.getX() - (TREE_WIDTH / 3));
								this.setY(this.getY() - (TREE_HEIGHT / 2));
   								this.getSpriteManager().setSprite(TREE_SPRITE_PATH[FULL_GROWN_INDEX]);
   								this.setHitBox(this.x + SCREEN_W / 17, this.y + SCREEN_H / 7, this.getWidth() - SCREEN_W / 8, this.getHeight() - SCREEN_H / 6);
   								break;
		}
	}
	
	public void setFullGrownState()
	{
		growState = GrowState.FULL_GROWN;
		this.getSpriteManager().setWidth(TREE_WIDTH);
		this.getSpriteManager().setHeight(TREE_HEIGHT);
		this.getSpriteManager().setSprite(TREE_SPRITE_PATH[FULL_GROWN_INDEX]);
		this.setHitBox(this.x + SCREEN_W / 17, this.y + SCREEN_H / 7, this.getWidth() - SCREEN_W / 8, this.getHeight() - SCREEN_H / 6);
	}
	
	public void decreaseTimeLeft() 
	{ 
		secondsLeftUntilNextState--; 
		if (secondsLeftUntilNextState <= 0)
		{
			this.nextState();
			secondsLeftUntilNextState = 10;
		}
	}
}