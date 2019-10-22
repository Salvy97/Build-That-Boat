package entities.living.enemies;

import entities.Entity;
import globals.KeysDefiner;
import static globals.ConstantsCollection.*;

public class GoldenSnakeSpit extends Entity
{
	private int damage;
	
	public GoldenSnakeSpit(int x, int y, int direction)
	{
		super(x, y, GOLDEN_SNAKE_SPIT_WIDTH, GOLDEN_SNAKE_SPIT_HEIGHT, 0, 8, GOLDEN_SNAKE_SPIT_SPRITE_PATH, true);
		this.direction = direction;
		setHitBox(x, y, GOLDEN_SNAKE_SPIT_WIDTH, GOLDEN_SNAKE_SPIT_HEIGHT);
		damage = 20;
	}
	
	public void goldenSnakeSpitAI()
	{
		switch (direction)
		{
			case KeysDefiner.UP:	this.y -= this.speed;
									this.hitBox.addY(-(this.speed));
									break;
			case KeysDefiner.DOWN:	this.y += this.speed;
									this.hitBox.addY(this.speed);
									break;
			case KeysDefiner.LEFT:	this.x -= this.speed;
									this.hitBox.addX(-(this.speed));
									break;
			case KeysDefiner.RIGHT:	this.x += this.speed;
									this.hitBox.addX(this.speed);
									break;
		}
	}
	
	public int getDamage() { return damage; }
	
	public void setDamage(int damage) { this.damage = damage; }
}