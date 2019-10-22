package entities;

import java.awt.image.BufferedImage;

import globals.HitBox;
import globals.KeysDefiner;
import globals.Sprite;

public class Entity 
{
	protected int hp;
	protected int x;
	protected int y;
	protected Sprite sprite;
	protected HitBox hitBox;
	protected boolean canMove;
	protected int speed;
	protected int direction;
	protected int animation;

	protected Entity(int x, int y, int width, int height, int hp, int speed, String spritePath, boolean canMove)
	{
		this.x = x;
		this.y = y;
		if (spritePath != null)
			this.sprite = new Sprite(spritePath, width, height);
		else
			this.sprite = null;
		this.hp = hp;
		this.canMove = canMove;
		this.speed = speed;
		this.direction = KeysDefiner.DOWN;
		this.animation = 1;
	}
	
	public int getX() { return x; }
	public int getY() { return y; }
	public int getHP() { return hp; }
	public int getWidth() { return this.sprite.getWidth(); }
	public int getHeight() { return this.sprite.getHeight(); }
	public Sprite getSpriteManager() { return sprite; }
	public BufferedImage getSprite() { return sprite.getSprite(); }
	public boolean canMove() { return canMove; }
	public int getSpeed() { return speed; }
	public int getDirection() { return direction; }	
	
	public void setHP(int hp) { this.hp = hp; }
	public void setX(int x) { this.x = x; }
	public void setY(int y) { this.y = y; }
	public void setWidth(int width) { this.sprite.setWidth(width); }
	public void setHeight(int height) { this.sprite.setHeight(height); }
	public void setHitBox(int x, int y, int width, int height) { hitBox = new HitBox(x, y, width, height); }
	public void setSpeed(int speed) { this.speed = speed; }
	public void setDirection(int direction) { this.direction = direction; }
	
	public boolean hasHitBox()
	{
		if (hitBox == null) return false;
		return true;
	}
	
	public HitBox getHitBox() { return hitBox; }
	
	public boolean detectCollision(Entity entity) 
	{  
		if (hitBox != null && entity != null && entity.hasHitBox())
			return hitBox.getHitBox().intersects(entity.getHitBox().getHitBox());
		else
			return false;
	}
	
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
	}
	
	public void disableMovement() { canMove = false; }
	public void enableMovement() { canMove = true; }
}