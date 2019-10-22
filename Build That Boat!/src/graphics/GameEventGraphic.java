package graphics;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;
import core.GameLogicManager;
import core.InputManager;
import core.audio.AudioIDCollector;
import entities.Animator;
import entities.Entity;
import globals.Sprite;
import entities.inanimate.*;
import entities.items.weapons.Arrow;
import entities.items.weapons.Weapon;
import entities.living.*;
import entities.living.enemies.Enemy;
import entities.living.enemies.GoldenSnake;
import entities.living.enemies.GoldenSnakeSpit;
import globals.FileManager;
import globals.KeysDefiner;
import globals.Pair;
import static globals.ConstantsCollection.*;

@SuppressWarnings("serial")
public class GameEventGraphic extends JPanel implements ActionListener
{
	private GameLogicManager game;
	private PlayerInventoryManager playerInventoryGraphics;
	private GUI GUI;
	
	private Tile grass;
	private Tile terrain;
	private Tile grass_terrain;
	private Tile water;
	
	private Sprite tallGrass;
	private static final int TALL_GRASS_NUMBER = 48;
	private ArrayList<Pair<Integer, Integer>> tallGrassPositions;
	private boolean initializeGrass;
	
	private Timer generalTimer;
	private Timer animationTimer;
	private Timer deathAnimationTimer;
	
	public long time;
	
	private Font HPFont;
	
	public GameEventGraphic(GameLogicManager game, InputManager inputManager)
	{
		this.game = game;
		playerInventoryGraphics = new PlayerInventoryManager(game, this);
		GUI = new GUI(game);
		addKeyListener(inputManager);
		
		grass = new Tile(GRASS_SPRITE_PATH);
		terrain = new Tile(TERRAIN_SPRITE_PATH);
		grass_terrain = new Tile(GRASS_TERRAIN_SPRITE_PATH);
		water = new Tile(WATER_SPRITE_PATH);
		
		tallGrass = new Sprite(TALL_GRASS_SPRITE_PATH, TALL_GRASS_WIDTH, TALL_GRASS_HEIGHT);
		tallGrassPositions = new ArrayList<Pair<Integer, Integer>>();
		initializeGrass = false;
		
		generalTimer = new Timer(10, this);
		generalTimer.start();
		
		animationTimer = new Timer(200, this);
		animationTimer.start();
		
		deathAnimationTimer = new Timer(1000, this);
		deathAnimationTimer.start();
		
		HPFont = new Font("Arial Black", Font.BOLD, SCREEN_W / 120);
		
		time = System.nanoTime();
	}
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);		

		g.setFont(HPFont);
		g.setColor(Color.RED);
		
		drawTiles(g);
		drawBoat(g);
		drawTallGrass(g);
		drawEquippedItem(g);
		drawPlants(g);
		drawOres(g);
		drawPlayer(g);
		drawNeutral(g);
		drawEnemies(g);
		drawArrows(g);
		drawGoldenSpits(g);
		drawRocks(g);
		drawTrees(g);
		if (!game.getGameEnded())
		{
			GUI.drawGUI(g);
			if (game.isInventoryMode())
				playerInventoryGraphics.drawInventory(g);
			else
			{
				playerInventoryGraphics.resetRecipeID();
				playerInventoryGraphics.searchBox.hideSearchBox();
				playerInventoryGraphics.craftingButton.setVisible(false);
				playerInventoryGraphics.craftingButton.setEnabled(false);
				playerInventoryGraphics.scrollDownButton.setVisible(false);
				playerInventoryGraphics.scrollDownButton.setEnabled(false);
				playerInventoryGraphics.scrollUpButton.setVisible(false);
				playerInventoryGraphics.scrollUpButton.setEnabled(false);
				playerInventoryGraphics.searchButton.setVisible(false);
				playerInventoryGraphics.searchButton.setEnabled(false);
				this.requestFocusInWindow();
			}
		}
		else
		{
			g.setFont(new Font("Arial Black", Font.BOLD, SCREEN_W / 30));
			g.drawString("Game Over! Press SPACE to continue", SCREEN_W / 2 - SCREEN_W / 3, SCREEN_H / 2);
		}
	}
	
	public void drawTiles(Graphics g)
	{
		for (int i = 0; i < TILE_SCALE; i++)
		{
			for (int j = 0; j < TILE_SCALE; j++)
			{
				if (i <= 1)
					g.drawImage(grass.getSprite(), Tile.calculateXPosition(i), Tile.calculateYPosition(j), Tile.calculateWidth(), Tile.calculateHeight(), null);
				else if (i == 2)
					g.drawImage(grass_terrain.getSprite(), Tile.calculateXPosition(i), Tile.calculateYPosition(j), Tile.calculateWidth(), Tile.calculateHeight(), null);
				else if (i >= 3 && i < TILE_SCALE - 1)
					g.drawImage(terrain.getSprite(), Tile.calculateXPosition(i), Tile.calculateYPosition(j), Tile.calculateWidth(), Tile.calculateHeight(), null);
				else if (i == TILE_SCALE - 1)
					g.drawImage(water.getSprite(), Tile.calculateXPosition(i), Tile.calculateYPosition(j), Tile.calculateWidth(), Tile.calculateHeight(), null);
			}
		}
	}
	
	public void drawBoat(Graphics g)
	{
		Boat boat = game.getBoat();
		if (boat.getSpriteManager() != null)
			g.drawImage(boat.getSprite(), boat.getX(), boat.getY(), boat.getWidth(), boat.getHeight(), null);
		if (game.getPlayer().detectCollision(boat) && boat.getWoodGiven() < 100)
		{
			int y = boat.getY();
			for (String line : "Premi R per\naggiungere legna\n alla barca".split("\n"))
			{
				y += g.getFontMetrics().getHeight();
		        g.drawString(line, boat.getX(), y - SCREEN_H / 16);
			}
		}
	}
	
	public void drawTallGrass(Graphics g)
	{
		if (!initializeGrass)
		{
			for (int i = 0; i < TALL_GRASS_NUMBER; i++)
			{
				Random random = new Random();
				int posX = random.nextInt(SCREEN_W - (SCREEN_W / TILE_SCALE) - SCREEN_W / 8);
				int posY = random.nextInt(SCREEN_H - (SCREEN_H / 4));
				tallGrassPositions.add(new Pair<Integer, Integer>(posX, posY));	
			}
			initializeGrass = true;
		}
		if (initializeGrass)
			for (int i = 0; i < tallGrassPositions.size(); i++)
				g.drawImage(tallGrass.getSprite(), tallGrassPositions.get(i).getFirstElement(), tallGrassPositions.get(i).getSecondElement(), tallGrass.getWidth(), tallGrass.getHeight(), null);
	}
	
	public void drawTrees(Graphics g)
	{
		Tree[] trees = game.getTrees();
		for (int i = 0; i < trees.length; i++)
			if (trees[i] != null)
				g.drawImage(trees[i].getSprite(), trees[i].getX(), trees[i].getY(), trees[i].getSpriteManager().getWidth(), trees[i].getSpriteManager().getHeight(), null);
	}
	
	public void drawRocks(Graphics g)
	{
		Rock[] rocks = game.getRocks();
		for (int i = 0; i < rocks.length; i++)
			if (rocks[i] != null)
				g.drawImage(rocks[i].getSprite(), rocks[i].getX(), rocks[i].getY(), rocks[i].getSpriteManager().getWidth(), rocks[i].getSpriteManager().getHeight(), null);
	}
	
	public void drawPlants(Graphics g)
	{
		Plant[] plants = game.getPlants();
		for (int i = 0; i < plants.length; i++)
			if (plants[i] != null)
				g.drawImage(plants[i].getSprite(), plants[i].getX(), plants[i].getY(), plants[i].getSpriteManager().getWidth(), plants[i].getSpriteManager().getHeight(), null);
	}
	
	public void drawOres(Graphics g)
	{
		Ore[] ores = game.getOres();
		for (int i = 0; i < ores.length; i++)
			if (ores[i] != null)
				g.drawImage(ores[i].getSprite(), ores[i].getX(), ores[i].getY(), ores[i].getSpriteManager().getWidth(), ores[i].getSpriteManager().getHeight(), null);
	}
	
	public void drawPlayer(Graphics g)
	{
		Player player = game.getPlayer();
		g.drawImage(player.getSprite(), player.getX(), player.getY(), player.getWidth(), player.getHeight(), null);
	}
	
	public void drawNeutral(Graphics g)
	{
		Neutral neutral = game.getNeutral();
		if (neutral != null)
			g.drawImage(neutral.getSprite(), neutral.getX(), neutral.getY(), neutral.getWidth(), neutral.getHeight(), null);
	}
	
	public void drawEnemies(Graphics g)
	{
		Enemy[] enemies = game.getEnemies();
		for (int i = 0; i < enemies.length; i++)
		{		
			if (enemies[i] != null)
			{
				Entity enemy = (Entity)(enemies[i]);
				g.drawImage(enemy.getSprite(), enemy.getX(), enemy.getY(), enemy.getWidth(), enemy.getHeight(), null);
				g.drawString(Integer.toString(enemy.getHP()), enemy.getX() + SCREEN_W / 70, enemy.getY());
			}
		}	
	}
	
	public void drawEquippedItem(Graphics g)
	{			
		if (!game.getPlayer().getInventory().getEquipableItem().isDisabled())
		{
			Entity equippedItem = (Entity)(game.getPlayer().getInventory().getEquipableItem());
			if (game.getPlayer().getInventory().getEquipableItem() instanceof Weapon)
				g.drawImage(equippedItem.getSprite(), equippedItem.getX(), equippedItem.getY(), equippedItem.getWidth(), equippedItem.getHeight(), null);
		}
	}
	
	public void drawArrows(Graphics g)
	{
		for (int i = 0; i < game.getPlayer().getInventory().getArrows().size(); i++)
		{
			Arrow arrow = game.getPlayer().getInventory().getArrows().get(i);
			g.drawImage(arrow.getSprite(), arrow.getX(), arrow.getY(), arrow.getWidth(), arrow.getHeight(), null);
		}
	}
	
	public void drawGoldenSpits(Graphics g)
	{
		for (int i = 0; i < game.getEnemies().length; i++)
		{
			if (game.getEnemies()[i] instanceof GoldenSnake)
			{
				for (int j = 0; j < ((GoldenSnake)game.getEnemies()[i]).getSpits().size(); j++)
				{
					GoldenSnakeSpit goldenSnakeSpit = ((GoldenSnake)game.getEnemies()[i]).getSpits().get(j);
					g.drawImage(goldenSnakeSpit.getSprite(), goldenSnakeSpit.getX(), goldenSnakeSpit.getY(), goldenSnakeSpit.getWidth(), goldenSnakeSpit.getHeight(), null);
				}
			}
		}
	}

	public void actionPerformed(ActionEvent e) 
	{
		if (e.getSource() == generalTimer) repaint();
		
		game.getAudioManager().playAudio(AudioIDCollector.GAME_MUSIC_ID);
		
		if (e.getSource() == animationTimer)
		{
			game.getPlayer().animate();
			for (int i = 0; i < game.getEnemies().length; i++)
				if (game.getEnemies()[i] != null && game.getEnemies()[i].getHP() > 0)
					((Animator) game.getEnemies()[i]).animate();
			if (game.getNeutral() != null && game.getNeutral().getHP() > 0)
				((Animator)game.getNeutral()).animate();
		}
		
		if (e.getSource() == deathAnimationTimer)
		{
			for (int i = 0; i < game.getEnemies().length; i++)
				if (game.getEnemies()[i] != null && (((Entity) game.getEnemies()[i]).getHP()) <= 0)
					((Animator) game.getEnemies()[i]).animate();
			if (game.getNeutral() != null && game.getNeutral().getHP() <= 0)
				((Animator)game.getNeutral()).animate();
		}

		for (int i = 0; i < KeysDefiner.KEYS_DIMENSION; i++) game.movePlayer(i);
		if (!game.getPlayer().isMoving() || game.isInventoryMode() || !game.getPlayer().canMove())
		{
			game.getAudioManager().stopAudio(AudioIDCollector.GRASS_FOOTSTEPS_ID);
			game.getAudioManager().stopAudio(AudioIDCollector.WATER_FOOTSTEPS_ID);
		}
		
		game.arrowsCollision();
		game.goldenSnakeSpitsCollision();
		
		for (int i = 0; i < game.getEnemies().length; i++)
			if (game.getEnemies()[i] != null)
				game.getEnemies()[i].AI(game.getPlayer(), game.getAudioManager());
		
		if (game.getNeutral() != null)
		{
			game.getNeutral().AI();
			if (game.getNeutral().getLastDestinationReached()) game.removeNeutral();
		}
			
		if (game.getPlayer().getHP() <= 0 || game.getPlayer().getHunger() <= 0) 
		{
			game.getPlayer().disableMovement();
			game.setGameEnded(true);
		}
		else if (game.getBoat().getWoodGiven() >= 100)
		{
			int previousHighScore = Integer.parseInt(FileManager.content("highscore.txt").get(0));
			if (calculateScore() > previousHighScore)
			{
				FileManager.deleteRow("highscore.txt", FileManager.content("highscore.txt").get(0));
				FileManager.writeRow("highscore.txt", Integer.toString((int) calculateScore()));
			}
			game.getPlayer().disableMovement();
			game.setGameEnded(true);
		}
	}
	
	public PlayerInventoryManager getPlayerInventoryManager() { return playerInventoryGraphics; }
	
	public long calculateScore() { return game.getPlayer().getKills() + (1000 - ((System.nanoTime() - time) / 1000000000)); }
}