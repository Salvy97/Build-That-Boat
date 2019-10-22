package core;

import entities.Entity;
import entities.items.tools.Tool;
import entities.inanimate.*;
import entities.items.crafting.Crafter;
import entities.items.manipulators.ItemIDCollector;
import entities.items.manipulators.ItemReader;
import entities.items.tools.Axe;
import entities.items.tools.WoodenPickaxe;
import entities.items.tools.Punch;
import entities.items.tools.StonePickaxe;
import entities.items.weapons.Arrow;
import entities.items.weapons.Weapon;
import entities.living.*;
import entities.living.enemies.*;
import globals.KeysDefiner;
import static globals.ConstantsCollection.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import core.audio.AudioIDCollector;
import core.audio.AudioManager;
import graphics.MenuManager;
import graphics.Tile;

public class GameLogicManager 
{
	private Crafter crafter;
	private AudioManager audioManager;
	
	private Boat boat;
	private Tree[] trees;
	private Rock[] rocks;
	private Plant[] plants;
	private Ore[] ores;
	private Enemy[] enemies;
	private Neutral neutral;
	private ArrayList<Integer> deadEnemiesIndex;
	private Player player;
	
	private Timer treesSpawnTimer;
	private Timer treesGrownTimer;
	private Timer enemiesSpawnTimer;
	private Timer rocksSpawnTimer;
	private Timer plantsSpawnTimer;
	private Timer oresSPawnTimer;
	private Timer neutralSpawnTimer;
	
	private long enemySpawnRate;
	
	private boolean inventoryMode;
	private boolean gameEnded;
	
	public GameLogicManager(Object[] gameLogicInfos)
	{
		audioManager = new AudioManager((Boolean)gameLogicInfos[MenuManager.AUDIO_MUTE_INDEX]);
		
		trees = new Tree[DifficultyManager.getTreesNumber((Integer)gameLogicInfos[MenuManager.DIFFICULTY_INDEX])];
		rocks = new Rock[DifficultyManager.getRocksNumber((Integer)gameLogicInfos[MenuManager.DIFFICULTY_INDEX])];
		plants = new Plant[DifficultyManager.getPlantsNumber((Integer)gameLogicInfos[MenuManager.DIFFICULTY_INDEX])];
		ores = new Ore[DifficultyManager.getOresNumber((Integer)gameLogicInfos[MenuManager.DIFFICULTY_INDEX])];
		enemies = new Enemy[MAXIMUM_ENEMY_CAPACITY];
		
		deadEnemiesIndex = new ArrayList<Integer>();
		
		enemySpawnRate = DifficultyManager.getSpawnRate((Integer)gameLogicInfos[MenuManager.DIFFICULTY_INDEX]);
		
		inventoryMode = false;
		gameEnded = false;
		
		initializeMap(); 
	}

	public void initializeMap()
	{
		boat = new Boat();
		
		spawnInitialTrees();
		spawnInitialRocks();
		spawnInitialPlants();
		spawnInitialOres();
		spawnPlayer();
				
		crafter = new Crafter(player.getInventory());
		
		treesSpawnTimer = new Timer();
	    treesSpawnTimer.scheduleAtFixedRate(new TimerTask() { public void run() { spawnTrees(); } }, 10000, TREE_SPAWN_RATE);
	    
	    treesGrownTimer = new Timer();
	    treesGrownTimer.scheduleAtFixedRate(new TimerTask() { public void run() {
	    	for (int i = 0; i < trees.length; i++)
	            if (trees[i] != null)
	            	trees[i].decreaseTimeLeft(); } }, 1, 1000);
	    
	    rocksSpawnTimer = new Timer();
	    rocksSpawnTimer.scheduleAtFixedRate(new TimerTask() { public void run() { spawnRocks(); } }, 10000, ROCK_SPAWN_RATE);
	    
	    plantsSpawnTimer = new Timer();
	    plantsSpawnTimer.scheduleAtFixedRate(new TimerTask() { public void run() { spawnPlants(); } }, 10000, PLANT_SPAWN_RATE);
	    
	    oresSPawnTimer = new Timer();
	    oresSPawnTimer.scheduleAtFixedRate(new TimerTask() { public void run() { spawnOres(); } }, 10000, ORE_SPAWN_RATE);
	    
	    enemiesSpawnTimer = new Timer();
	    enemiesSpawnTimer.scheduleAtFixedRate(new TimerTask() { public void run() { spawnEnemies(); } }, STARTING_ENEMY_SPAWN_TIME, enemySpawnRate);
	
	    neutralSpawnTimer = new Timer();
	    neutralSpawnTimer.scheduleAtFixedRate(new TimerTask() { public void run() { spawnNeutral(); } }, 120000, NEUTRAL_SPAWN_RATE);
	}
	
	public void spawnInitialTrees()
	{
		for(int i = 0; i < trees.length; i++)
		{
			if (trees[i] == null)
			{
				Random random = new Random();
				trees[i] = new Tree(random.nextInt(SCREEN_W - (SCREEN_W / TILE_SCALE) - (SCREEN_W / 8)), random.nextInt(SCREEN_H - (SCREEN_H / 4)), true);	
			}
		}
	}
	
	public void spawnInitialRocks()
	{
		for(int i = 0; i < rocks.length; i++)
		{
			if (rocks[i] == null)
			{
				Random random = new Random();
				rocks[i] = new Rock(random.nextInt(SCREEN_W - (SCREEN_W / TILE_SCALE) - (SCREEN_W / 8)), random.nextInt(SCREEN_H - (SCREEN_H / 4)));	
			}
		}
	}
	
	public void spawnInitialPlants()
	{
		for(int i = 0; i < plants.length; i++)
		{
			if (plants[i] == null)
			{
				Random random = new Random();
				int plantKind = random.nextInt(6) + 1;
				if (plantKind <= 3) plants[i] = new GreenPlant(random.nextInt(SCREEN_W - (SCREEN_W / TILE_SCALE) - (SCREEN_W / 8)), random.nextInt(SCREEN_H - (SCREEN_H / 4)));
				else if (plantKind < 6 && plantKind >= 4) plants[i] = new RedPlant(random.nextInt(SCREEN_W - (SCREEN_W / TILE_SCALE) - (SCREEN_W / 8)), random.nextInt(SCREEN_H - (SCREEN_H / 4)));
				else plants[i] = new YellowPlant(random.nextInt(SCREEN_W - (SCREEN_W / TILE_SCALE) - (SCREEN_W / 8)), random.nextInt(SCREEN_H - (SCREEN_H / 4)));
			} 
		}
	}
	
	public void spawnInitialOres()
	{
		for (int i = 0; i < ores.length; i++)
		{
			if (ores[i] == null)
			{
				Random random = new Random();
				ores[i] = new Ore(random.nextInt(SCREEN_W - (SCREEN_W / TILE_SCALE) - (SCREEN_W / 8)), random.nextInt(SCREEN_H - (SCREEN_H / 4)));
			}
		}
	}
	
	public void spawnTrees()
	{
		boolean spawned = false;
		for(int i = 0; i < trees.length && !spawned; i++)
		{
			if (trees[i] == null)
			{
				Random random = new Random();
				trees[i] = new Tree(random.nextInt(SCREEN_W - (SCREEN_W / TILE_SCALE) - (SCREEN_W / 8)), random.nextInt(SCREEN_H - (SCREEN_H / 4)), false);
				spawned = true;
			}
		}
	}
	
	public void spawnRocks()
	{
		boolean spawned = false;
		for(int i = 0; i < rocks.length && !spawned; i++)
		{
			if (rocks[i] == null)
			{
				Random random = new Random();
				rocks[i] = new Rock(random.nextInt(SCREEN_W - (SCREEN_W / TILE_SCALE) - (SCREEN_W / 8)), random.nextInt(SCREEN_H - (SCREEN_H / 4)));	
				spawned = true;
			}
		}
	}
	
	public void spawnPlants()
	{
		Random random = new Random();
		int plantKind = random.nextInt(7) + 1;
		
		boolean spawned = false;
		
		for(int i = 0; i < rocks.length && !spawned; i++)
		{
			if (plants[i] == null)
			{
				if (plantKind <= 3) plants[i] = new GreenPlant(random.nextInt(SCREEN_W - (SCREEN_W / TILE_SCALE) - (SCREEN_W / 8)), random.nextInt(SCREEN_H - (SCREEN_H / 4)));
				else if (plantKind < 6 && plantKind >= 4) plants[i] = new RedPlant(random.nextInt(SCREEN_W - (SCREEN_W / TILE_SCALE) - (SCREEN_W / 8)), random.nextInt(SCREEN_H - (SCREEN_H / 4)));
				else plants[i] = new YellowPlant(random.nextInt(SCREEN_W - (SCREEN_W / TILE_SCALE) - (SCREEN_W / 8)), random.nextInt(SCREEN_H - (SCREEN_H / 4)));
				spawned = true;
			}
		}
	}
	
	public void spawnOres()
	{
		boolean spawned = false;
		for (int i = 0; i < ores.length && !spawned; i++)
		{
			if (ores[i] == null)
			{
				Random random = new Random();
				ores[i] = new Ore(random.nextInt(SCREEN_W - (SCREEN_W / TILE_SCALE) - (SCREEN_W / 8)), random.nextInt(SCREEN_H - (SCREEN_H / 4)));
				spawned = true;
			}
		}
	}
	
	public void spawnPlayer() { player = new Player(SCREEN_W / 2, SCREEN_H / 2); }
	
	public void spawnNeutral() 
	{ 
		Random random = new Random();
		int border = 90;
		neutral = new Deer(-border, random.nextInt(SCREEN_H / 2) * 2, audioManager);
	}

	public void spawnEnemies()
	{
		int border = 10;
		boolean spawned = false;
		int enemyKind = new Random().nextInt(3) + 1;
		
		for (int i = 0; i < enemies.length && !spawned; i++)
		{
			Random random = new Random();
			if (enemies[i] == null)	
			{
				switch (enemyKind)
				{
					case 1: enemies[i] = new Goblin(random.nextInt(SCREEN_W), SCREEN_H + border, audioManager);
							break;
					case 2: enemies[i] = new Troll(random.nextInt(SCREEN_W), SCREEN_H + border, audioManager);
							break;
					case 3: enemies[i] = new GoldenSnake(random.nextInt(SCREEN_W), SCREEN_H + border, audioManager);
							break;
				}
				spawned = true;
			}
		}
	}
	
	private void removeDeadEnemies()
	{
		for (int i = 0; i < deadEnemiesIndex.size(); i++)
		{
			Timer timer = new Timer();
		    timer.schedule(new TimerTask() 
		    {
		        public void run() 
		        {
		        	for (int i = 0; i < deadEnemiesIndex.size(); i++)
		        	{
		        		enemies[deadEnemiesIndex.get(i)].getTimer().cancel();
		        		enemies[deadEnemiesIndex.get(i)] = null;
		        		if (enemies[deadEnemiesIndex.get(i)] instanceof GoldenSnake)
		        			((GoldenSnake)enemies[deadEnemiesIndex.get(i)]).getSpitTimer().cancel();
		        		deadEnemiesIndex.remove(i);
		        	}
		        }
		    }, 2000); 
		}
	}
	
	public void removeNeutral()
	{
		Timer timer = new Timer();
	    timer.schedule(new TimerTask() 
	    {
	        public void run() 
	        {
	        	neutral = null;
	        }
	    }, 2000); 
	}
	
	public AudioManager getAudioManager() { return audioManager; }
	public Boat getBoat() { return boat; }
	public Tree[] getTrees() { return trees; }
	public Rock[] getRocks() { return rocks; }
	public Plant[] getPlants() { return plants; }
	public Ore[] getOres() { return ores; }
	public Enemy[] getEnemies() { return enemies; }
	public Neutral getNeutral() { return neutral; }
	public Player getPlayer() { return player; }
	public long getEnemySpawnRate() { return enemySpawnRate; }
	public boolean isInventoryMode() { return inventoryMode; }
	public boolean getGameEnded() { return gameEnded; }
	
	public void setGameEnded(boolean gameEnded) { this.gameEnded = gameEnded; }
	
	public void movePlayer(int key)
	{
		if (player.getKeysDefiner().getKeyState(key))
		{
			player.movement(key);
				
			boolean isColliding = false;
			for (int i = 0; i < trees.length; i++)
				if (player.detectCollision(trees[i]))
					isColliding = true;
			for (int i = 0; i < rocks.length; i++)
				if (player.detectCollision(rocks[i]))
					isColliding = true;
			if (player.getY() <= 0) isColliding = true;
				
			if (isColliding)
			{
				switch(key)
				{
					case KeysDefiner.UP: 	player.movement(KeysDefiner.DOWN);
											break;
					case KeysDefiner.DOWN: 	player.movement(KeysDefiner.UP);
					 						break;
					case KeysDefiner.LEFT:	player.movement(KeysDefiner.RIGHT);
						 					break;
					case KeysDefiner.RIGHT: player.movement(KeysDefiner.LEFT);
											break;
				}
			}
		
			if (player.getX() < Tile.calculateXPosition(TILE_SCALE - 1) - SCREEN_W / 50) audioManager.playAudio(AudioIDCollector.GRASS_FOOTSTEPS_ID);
			else audioManager.playAudio(AudioIDCollector.WATER_FOOTSTEPS_ID);
		}
	}
	
	public void attackComponent(InanimateEntity[] entities)
	{
		for (int i = 0; i < entities.length; i++)
		{
			if (((Entity)(player.getInventory().getEquipableItem())).detectCollision((Entity)entities[i]))
			{
				if (player.getInventory().getEquipableItem().getItemID() != -1) player.getInventory().getEquipableItem().decreaseDurability();
				audioManager.playAudioOnce(player.getInventory().getEquipableItem().getAudioID());
				if (player.getInventory().getEquipableItem().getDurability() == 0)
					player.getInventory().setPunch();
				((Entity)entities[i]).setHP(((Entity)entities[i]).getHP() - player.getInventory().getEquipableItem().getDamage());
				if (((Entity)entities[i]).getHP() <= 0) 
				{
					player.getInventory().collectItem(entities[i].drop().getFirstElement(), entities[i].drop().getSecondElement());
					if (ItemReader.isConsumable(entities[i].drop().getFirstElement()))
						player.getInventory().addConsumableItem(entities[i].drop().getFirstElement());
					entities[i] = null;
				}
			}
		}
	}
	
	public void attackEnemies()
	{
		for (int i = 0; i < enemies.length; i++)
		{
			Entity enemy = (Entity)(enemies[i]);
			if (((Entity)(player.getInventory().getEquipableItem())).detectCollision(enemy))
			{
				if (player.getInventory().getEquipableItem().getItemID() != -1) player.getInventory().getEquipableItem().decreaseDurability();
				audioManager.playAudioOnce(player.getInventory().getEquipableItem().getAudioID());
				if (player.getInventory().getEquipableItem().getDurability() == 0)
					player.getInventory().setPunch();
				enemy.setHP(enemy.getHP() - player.getInventory().getEquipableItem().getDamage());
				enemy.push(player.getDirection(), player.getInventory().getEquipableItem().getDamage() * 3);
				enemy.disableMovement();
				if (enemy.getHP() <= 0)
				{		
					audioManager.playAudioOnce(enemies[i].getAudiosID()[Enemy.ENEMY_DEATH]);
					deadEnemiesIndex.add(i);
					player.getInventory().collectItem(enemies[i].drop().getFirstElement(), enemies[i].drop().getSecondElement());
					if (ItemReader.isConsumable(enemies[i].drop().getFirstElement()))
						player.getInventory().addConsumableItem(enemies[i].drop().getFirstElement());
					player.increaseKills();
				}
				else
				{
					audioManager.playAudioOnce(enemies[i].getAudiosID()[Enemy.ENEMY_PAIN]);
					new Timer().schedule(new TimerTask()
					{
						public void run()
						{
							enemy.enableMovement();
						}
					}, 1000);
				}
			}
		}
	}
	
	public void attackNeutral()
	{
		if (neutral != null)
		{
			if (((Entity)(player.getInventory().getEquipableItem())).detectCollision(neutral))
			{
				if (player.getInventory().getEquipableItem().getItemID() != -1) player.getInventory().getEquipableItem().decreaseDurability();
				audioManager.playAudioOnce(player.getInventory().getEquipableItem().getAudioID());
				if (player.getInventory().getEquipableItem().getDurability() == 0)
					player.getInventory().setPunch();
				neutral.setHP(neutral.getHP() - player.getInventory().getEquipableItem().getDamage());
				neutral.push(player.getDirection(), player.getInventory().getEquipableItem().getDamage() * 3);
				neutral.disableMovement();
				if (neutral.getHP() <= 0)
				{		
					audioManager.playAudioOnce(neutral.getAudiosID()[Neutral.NEUTRAL_DEATH]);
					player.getInventory().collectItem(neutral.drop().getFirstElement(), neutral.drop().getSecondElement());
					if (ItemReader.isConsumable(neutral.drop().getFirstElement()))
						player.getInventory().addConsumableItem(neutral.drop().getFirstElement());
					player.increaseKills();	
				}
				else
				{
					audioManager.playAudioOnce(neutral.getAudiosID()[Neutral.NEUTRAL_PAIN]);
					new Timer().schedule(new TimerTask()
					{
						public void run()
						{
							neutral.enableMovement();
						}
					}, 200);
				}
			}
		}
	}
	
	public void playerAttack()
	{
		player.disableMovement();
		
		player.getInventory().getEquipableItem().use(player.getX(), player.getY(), player.getDirection());
		
		if (player.getInventory().getEquipableItem() instanceof Tool)
		{
			if (player.getInventory().getEquipableItem() instanceof Punch) 
				audioManager.playAudioOnce(player.getInventory().getEquipableItem().getAudioID());
			
			if (player.getInventory().getEquipableItem() instanceof Axe || player.getInventory().getEquipableItem() instanceof Punch)
				attackComponent(trees);
			if (player.getInventory().getEquipableItem() instanceof WoodenPickaxe || player.getInventory().getEquipableItem() instanceof StonePickaxe) 
				attackComponent(rocks);		
			if (player.getInventory().getEquipableItem() instanceof StonePickaxe)
				attackComponent(ores);
			attackComponent(plants);
		}
		
		if (player.getInventory().getEquipableItem() instanceof Weapon || player.getInventory().getEquipableItem() instanceof Punch)
		{
			attackEnemies();
			attackNeutral();
		}
		removeDeadEnemies();
		if (neutral != null && neutral.getHP() <= 0) { neutral.getTimer().cancel(); removeNeutral(); }
		
		Timer timer = new Timer();
		timer.schedule(new TimerTask() 
		{
			public void run() 
			{
				player.enableMovement();
				player.getInventory().getEquipableItem().disable();
			}
		}, 1000);
		if (player.getInventory().getEquipableItem().getDurability() <= 0) player.getInventory().setPunch();
	}
	
	public void arrowsCollision()
	{
		ArrayList<Arrow> arrows = player.getInventory().getArrows();
		
		for (int i = 0; i < arrows.size(); i++)
		{
			arrows.get(i).arrowAI();
			boolean needsToBeRemoved = false;
			for (int j = 0; j < trees.length; j++)
				if (arrows.get(i).detectCollision(trees[j]))
					needsToBeRemoved = true;
			for (int j = 0; j < rocks.length; j++)
				if (arrows.get(i).detectCollision(rocks[j]))
					needsToBeRemoved = true;
			for (int j = 0; j < enemies.length; j++)
			{
				Entity enemy = (Entity)(enemies[j]);
				if (arrows.get(i).detectCollision(enemy))
				{
					enemy.setHP(enemy.getHP() - arrows.get(i).getDamage());
					enemy.push(arrows.get(i).getDirection(), arrows.get(i).getDamage() * 3);
					enemy.disableMovement();
					if (enemy.getHP() <= 0)
					{
						enemy.disableMovement();
						audioManager.playAudioOnce(enemies[j].getAudiosID()[Enemy.ENEMY_DEATH]);
						deadEnemiesIndex.add(j);
						player.getInventory().collectItem(enemies[j].drop().getFirstElement(), enemies[j].drop().getSecondElement());
					}
					else
					{
						audioManager.playAudioOnce(enemies[j].getAudiosID()[Enemy.ENEMY_PAIN]);
						new Timer().schedule(new TimerTask()
						{
							public void run()
							{
								enemy.enableMovement();
							}
						}, 1000);
					}
					removeDeadEnemies();
					needsToBeRemoved = true;
				}
			}
			if (arrows.get(i).getX() < 0 || arrows.get(i).getX() > SCREEN_W || arrows.get(i).getY() < 0 || arrows.get(i).getY() > SCREEN_H) needsToBeRemoved = true;
			if (needsToBeRemoved) arrows.remove(i);
		}
	}
	
	public void addWoodToTheBoat()
	{
		if (player.detectCollision(boat) && player.getInventory().getItemQuantity(ItemIDCollector.LOG_ID) > 0)
		{
			boat.setWoodGiven(boat.getWoodGiven() + 1);
			player.getInventory().collectItem(ItemIDCollector.LOG_ID, -1);
			boat.changeSprite();
		}
	}
	
	public void goldenSnakeSpitsCollision()
	{
		GoldenSnake goldenSnake = null;
		for (int i = 0; i < enemies.length; i++)
			if (enemies[i] instanceof GoldenSnake)
				goldenSnake = (GoldenSnake)enemies[i];
		if (goldenSnake != null)
		{
			ArrayList<GoldenSnakeSpit> goldenSnakeSpits = goldenSnake.getSpits();
			for (int i = 0; i < goldenSnakeSpits.size(); i++)
			{
				goldenSnakeSpits.get(i).goldenSnakeSpitAI();
				boolean needsToBeRemoved = false;
				for (int j = 0; j < trees.length; j++)
					if (goldenSnakeSpits.get(i).detectCollision(trees[j]))
						needsToBeRemoved = true;
				for (int j = 0; j < rocks.length; j++)
					if (goldenSnakeSpits.get(i).detectCollision(rocks[j]))
						needsToBeRemoved = true;
				if (goldenSnakeSpits.get(i).detectCollision(player))
				{
					needsToBeRemoved = true;
					player.setHP(player.getHP() - goldenSnakeSpits.get(i).getDamage());
					if (player.getInventory().getArmor() != null) player.getInventory().getArmor().decreaseDurability();
				}
				if (goldenSnakeSpits.get(i).getX() < 0 || goldenSnakeSpits.get(i).getX() > SCREEN_W || goldenSnakeSpits.get(i).getY() < 0 || goldenSnakeSpits.get(i).getY() > SCREEN_H) needsToBeRemoved = true;
				if (needsToBeRemoved) goldenSnakeSpits.remove(i);
			}
		}
	}
	
	public void inventoryMode(boolean inventoryMode) { this.inventoryMode = inventoryMode; }

	public void craftItem(int ID) { crafter.craftItem(ID); }
}