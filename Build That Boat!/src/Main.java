import javax.swing.JFrame;
import core.GameLogicManager;
import core.InputManager;
import globals.ConstantsCollection;
import graphics.GameEventGraphic;
import graphics.MenuManager;
import graphics.PlayerInventoryManager;
import static globals.ConstantsCollection.*;

public class Main 
{	
	public static void main(String[] args) 
	{
		JFrame window = new JFrame("Build That Boat!");
		
		MenuManager menuManager = new MenuManager();
		
		window.setSize(SCREEN_W, SCREEN_H);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setExtendedState(JFrame.MAXIMIZED_BOTH); 
		window.setUndecorated(true);
		window.setVisible(true);
		window.add(menuManager);

		while (true)
		{
			if (!menuManager.isGameStarted())
			{
				try 
				{
					Thread.sleep(1000);
				} 
				catch (InterruptedException e) 
				{
					e.printStackTrace();
				}
			}
			else break;		
		}
	
		if (menuManager.getResolutionInfos()[MenuManager.RESOLUTION_W_INDEX - 2] != null && (Integer)menuManager.getResolutionInfos()[MenuManager.RESOLUTION_H_INDEX - 2] != null)
		{
			ConstantsCollection.initializeConstants((Integer)menuManager.getResolutionInfos()[MenuManager.RESOLUTION_W_INDEX - 2], (Integer)menuManager.getResolutionInfos()[MenuManager.RESOLUTION_H_INDEX - 2]);
			if (window.getSize().width != SCREEN_W && window.getSize().height != SCREEN_H)
			{
				window.setSize(SCREEN_W, SCREEN_H);
				window.setLocationRelativeTo(null);
			}
		}
			
		GameLogicManager gameLogicManager = new GameLogicManager(menuManager.getGameLogicInfos());
			
		window.remove(menuManager);
		menuManager = null;
				
		InputManager inputManager = new InputManager(gameLogicManager);
		GameEventGraphic gameEventGraphics = new GameEventGraphic(gameLogicManager, inputManager);
		PlayerInventoryManager playerInventoryGraphics = gameEventGraphics.getPlayerInventoryManager();
		
		window.add(gameEventGraphics);
		window.addMouseListener(playerInventoryGraphics);
		window.setVisible(true);
		window.requestFocusInWindow();
			
		while (true)
		{
			if (!inputManager.getCanExit())
			{
				try 
				{
					Thread.sleep(1000);
				} 
				catch (InterruptedException e) 
				{
					e.printStackTrace();
				}
			}
			else break;		
		}
		
		System.exit(0);
	}
}