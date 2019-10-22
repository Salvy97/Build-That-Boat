package entities.items.weapons;

import entities.Entity;
import globals.KeysDefiner;
import static globals.ConstantsCollection.*;

public class Arrow extends Entity
{
	private int damage;
	
	public Arrow(int x, int y, int direction)
	{
		super(x, y, ARROW_WIDTH, ARROW_HEIGHT, 0, 8, ARROW_SPRITE_PATH[direction], true);
		this.direction = direction;
		setHitBox(x, y, ARROW_WIDTH, ARROW_HEIGHT);
		damage = 40;
	}
	
	public void arrowAI()
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