package globals;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Sprite
{
	private int width;
	private int height;
	private BufferedImage sprite;
	private String path;
	
	public Sprite(String path, int width, int height)
	{
        try 
        {
			sprite = ImageIO.read(new File(path));
		} 
        catch (IOException e) 
        {
			e.printStackTrace();
		}
        this.width = width;
        this.height = height;
        this.path = path;
	}
	
	public int getWidth() { return width; }
	public int getHeight() { return height; }
	public BufferedImage getSprite() { return sprite; }
	public String getSpritePath() { return path; }
	
	public void setWidth(int width) { this.width = width; }
	public void setHeight(int height) { this.height = height; }
	public void setSprite(String newSpritePath)
	{
		 try 
	     {
			sprite = ImageIO.read(new File(newSpritePath));
	     } 
	     catch (IOException e) 
	     {
			e.printStackTrace();
	     }
		 this.path = newSpritePath;
	}
}