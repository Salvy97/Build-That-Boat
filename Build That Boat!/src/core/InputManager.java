package core;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import globals.KeysDefiner;

public class InputManager implements KeyListener
{
	private GameLogicManager game;
	private KeysDefiner keysDefiner;
	
	private boolean canExit;
	
	public InputManager(GameLogicManager game)
	{
		this.game = game;
		keysDefiner = game.getPlayer().getKeysDefiner();
		canExit = false;
	}
	
	public void keyPressed(KeyEvent key) 
	{
		if (!game.getPlayer().isMoving())
		{
			switch (key.getKeyCode())
			{
				case KeyEvent.VK_W: keysDefiner.setKeyState(KeysDefiner.UP, true);
									break;
				case KeyEvent.VK_S: keysDefiner.setKeyState(KeysDefiner.DOWN, true);
									break;
				case KeyEvent.VK_A: keysDefiner.setKeyState(KeysDefiner.LEFT, true);						
									break;
				case KeyEvent.VK_D: keysDefiner.setKeyState(KeysDefiner.RIGHT, true);									
									break;
			}
		}
	}

	public void keyReleased(KeyEvent key) 
	{
		switch (key.getKeyCode())
		{
			case KeyEvent.VK_W: keysDefiner.setKeyState(KeysDefiner.UP, false);
								break;
			case KeyEvent.VK_S: keysDefiner.setKeyState(KeysDefiner.DOWN, false);	
								break;
			case KeyEvent.VK_A: keysDefiner.setKeyState(KeysDefiner.LEFT, false);
								break;
			case KeyEvent.VK_D: keysDefiner.setKeyState(KeysDefiner.RIGHT, false);
								break;
		}
	}

	public void keyTyped(KeyEvent key)
	{
		char keyChar = key.getKeyChar();
		switch (keyChar)
		{
			case 'q': 		if (game.getPlayer().canMove() && game.getPlayer().getVigor() >= game.getPlayer().getInventory().getEquipableItem().getVigorUsed())
							{
								game.playerAttack();
								game.getPlayer().removeVigor();
							}
							break;
			case 'e':   	if (game.getPlayer().canMove() || game.isInventoryMode())
							{
								if (!game.isInventoryMode()) { game.inventoryMode(true); game.getPlayer().disableMovement(); }
								else { game.inventoryMode(false); game.getPlayer().enableMovement(); }
							}
							break;
			case 'r':   	game.addWoodToTheBoat();
							break;
			case (char)27: 	System.exit(0); //Escape key
							break;
			case (char)32:  if (game.getGameEnded()) canExit = true;
							break;
		}	
	}
	
	public boolean getCanExit() { return canExit; }
}