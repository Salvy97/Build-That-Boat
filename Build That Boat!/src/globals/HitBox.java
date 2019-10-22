package globals;

import java.awt.Rectangle;

public class HitBox
{
	private Rectangle hitBox;
	
	public HitBox(int x, int y, int width, int height)
	{	
		hitBox = new Rectangle(x, y, width, height);
	}
	
	public void addX(int x) { this.hitBox.setLocation((int)(hitBox.getX()) + x, (int)(this.hitBox.getY())); }
	public void addX(double x) { this.hitBox.setLocation((int)((hitBox.getX()) + x), (int)(this.hitBox.getY())); }
	public void addY(int y) { this.hitBox.setLocation((int)(hitBox.getX()), (int)(this.hitBox.getY()) + y); }
	public void addY(double y) { this.hitBox.setLocation((int)(hitBox.getX()), (int)((this.hitBox.getY()) + y)); }
	
	public Rectangle getHitBox() { return hitBox; }
}