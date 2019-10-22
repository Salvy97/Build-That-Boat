package graphics;

import java.awt.Font;
import java.awt.Graphics;
import core.GameLogicManager;
import globals.Sprite;

import static globals.ConstantsCollection.*;

public class GUI 
{
	private GameLogicManager game;
	
	private Sprite GUIInterface;
	
	public GUI(GameLogicManager game)
	{
		this.game = game;
		GUIInterface = new Sprite(GUI_INTERFACE_SPRITE_PATH, SCREEN_W / 8, SCREEN_H / 4);
	}
	
	public void drawGUI(Graphics g)
	{
		g.drawImage(GUIInterface.getSprite(), SCREEN_W - (SCREEN_W / 7), SCREEN_H - SCREEN_H / 4 - SCREEN_H / 36, GUIInterface.getWidth(), GUIInterface.getHeight(), null);
		g.setFont(new Font("Arial Black", Font.BOLD, SCREEN_W / 53));
		g.drawString(Integer.toString(game.getPlayer().getHP()), SCREEN_W - (SCREEN_W / 13), SCREEN_H - SCREEN_H / 5);
		g.drawString(Integer.toString(game.getPlayer().getVigor()),SCREEN_W - (SCREEN_W / 13), SCREEN_H - SCREEN_H / 7);
		g.drawString(Integer.toString(game.getPlayer().getHunger()),SCREEN_W - (SCREEN_W / 13), SCREEN_H - SCREEN_H / 14);
	}
}