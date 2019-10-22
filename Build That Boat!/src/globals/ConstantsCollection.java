package globals;

import java.awt.Toolkit;

public class ConstantsCollection 
{
	public static int SCREEN_W = Toolkit.getDefaultToolkit().getScreenSize().width;
	public static int SCREEN_H = Toolkit.getDefaultToolkit().getScreenSize().height;
		
	public static final int TILE_SCALE = 9;
	
	public static int BOAT_WIDTH;
	public static int BOAT_HEIGHT;
	public static int TALL_GRASS_WIDTH;
	public static int TALL_GRASS_HEIGHT;
	public static int PLAYER_WIDTH;
	public static int PLAYER_HEIGHT;
	public static int TREE_WIDTH;
	public static int TREE_HEIGHT;
	public static int ROCK_WIDTH;
	public static int ROCK_HEIGHT;
	public static int GOBLIN_WIDTH;
	public static int GOBLIN_HEIGHT;
	public static int TROLL_WIDTH;	
	public static int TROLL_HEIGHT;
	public static int GOLDEN_SNAKE_WIDTH;
	public static int GOLDEN_SNAKE_HEIGHT;
	public static int SWORD_WIDTH;
	public static int SWORD_HEIGHT;
	public static int ARROW_WIDTH;
	public static int ARROW_HEIGHT;
	public static int GOLDEN_SNAKE_SPIT_WIDTH;
	public static int GOLDEN_SNAKE_SPIT_HEIGHT;
	public static int DEER_WIDTH;
	public static int DEER_HEIGHT;
	public static int INVENTORY_ITEM_WIDTH;
	public static int INVENTORY_ITEM_HEIGHT;
		
	public static int MAXIMUM_ENEMY_CAPACITY = 8;
	public static int STARTING_ENEMY_SPAWN_TIME = 180 * 1000;
	
	public static final long TREE_SPAWN_RATE = 60 * 1000;
	public static final long ROCK_SPAWN_RATE = 40 * 1000;
	public static final long PLANT_SPAWN_RATE = 60 * 1000;
	public static final long ORE_SPAWN_RATE = 120 * 1000;
	public static final long NEUTRAL_SPAWN_RATE = 120 * 1000;
	
	public static void initializeConstants(int screenW, int screenH)
	{
		SCREEN_W = screenW;
		SCREEN_H = screenH;
		
		BOAT_WIDTH = SCREEN_W / 12;
		BOAT_HEIGHT = SCREEN_W / 18;
		TALL_GRASS_WIDTH = SCREEN_W / 15;
		TALL_GRASS_HEIGHT = SCREEN_H / 15;
		PLAYER_WIDTH = SCREEN_W / 22;
		PLAYER_HEIGHT = SCREEN_H / 12;
		TREE_WIDTH = SCREEN_W / 7;
		TREE_HEIGHT = SCREEN_W / 7;
		ROCK_WIDTH = SCREEN_W / 14;
		ROCK_HEIGHT = SCREEN_H / 13;
	    GOBLIN_WIDTH = SCREEN_W / 22;
		GOBLIN_HEIGHT = SCREEN_H / 12;
		TROLL_WIDTH = SCREEN_W / 22;	
		TROLL_HEIGHT = SCREEN_H / 12;
		GOLDEN_SNAKE_WIDTH = SCREEN_W / 12;
		GOLDEN_SNAKE_HEIGHT = SCREEN_H / 20;
		DEER_WIDTH = SCREEN_W / 24;
		DEER_HEIGHT = SCREEN_H / 12;
		SWORD_WIDTH = SCREEN_W / 68;
		SWORD_HEIGHT = SCREEN_H / 19;
		ARROW_WIDTH = SCREEN_W / 30;
		ARROW_HEIGHT = SCREEN_H / 60;
		GOLDEN_SNAKE_SPIT_WIDTH = SCREEN_W / 30;
		GOLDEN_SNAKE_SPIT_HEIGHT = SCREEN_H / 40;
		INVENTORY_ITEM_WIDTH = SCREEN_W / 26;
		INVENTORY_ITEM_HEIGHT = SCREEN_H / 26;
	}
	
	public static final String BOAT_SPRITE_PATH[] =
	{
		"sprites/boat/boat25.png",
		"sprites/boat/boat50.png",
		"sprites/boat/boat75.png",
		"sprites/boat/boat100.png"
	};
	
	public static final String TREE_SPRITE_PATH[] =
	{
		"sprites/sapling.png",
		"sprites/tree.png"
	};
	
	public static final String ROCK_SPRITE_PATH = "sprites/rock.png";
	public static final String GREEN_PLANT_SPRITE_PATH = "sprites/greenPlant.png";
	public static final String RED_PLANT_SPRITE_PATH = "sprites/redPlant.png";
	public static final String YELLOW_PLANT_SPRITE_PATH = "sprites/yellowPlant.png";
	public static final String IRON_ORE_PATH = "sprites/ironOre.png";
	
	public static final String PLAYER_SPRITE_PATH[][] = 
	{ 
		{ 
			"sprites/player/playerUpStill.png",
			"sprites/player/playerUpAnimation1.png",
			"sprites/player/playerUpAnimation2.png",
		},
		{ 
			"sprites/player/playerDownStill.png",
			"sprites/player/playerDownAnimation1.png",
			"sprites/player/playerDownAnimation2.png",
		},
		{ 
			"sprites/player/playerLeftStill.png",
			"sprites/player/playerLeftAnimation1.png",
			"sprites/player/playerLeftAnimation2.png",
		},
		{ 
			"sprites/player/playerRightStill.png",
			"sprites/player/playerRightAnimation1.png",
			"sprites/player/playerRightAnimation2.png",
		}
	};
	
	public static final int STILL = 0;
	public static final int ANIMATION_1 = 1;
	public static final int ANIMATION_2 = 2;
	public static final int DEATH = 4;

	public static final String GOBLIN_SPRITE_PATH[][] = 
	{
		{ 
			"sprites/goblin/goblinUpAnimation1.png",
			"sprites/goblin/goblinUpAnimation2.png",
		},
		{ 
			"sprites/goblin/goblinDownAnimation1.png",
			"sprites/goblin/goblinDownAnimation2.png",
		},
		{ 
			"sprites/goblin/goblinLeftAnimation1.png",
			"sprites/goblin/goblinLeftAnimation2.png",
		},
		{ 
			"sprites/goblin/goblinRightAnimation1.png",
			"sprites/goblin/goblinRightAnimation2.png",
		},
		{
			"sprites/goblin/goblinDeathAnimation1.png",
			"sprites/goblin/goblinDeathAnimation2.png",
		}
	};
	
	public static final String TROLL_SPRITE_PATH[][] = 
	{
		{ 
			"sprites/troll/trollUpAnimation1.png",
			"sprites/troll/trollUpAnimation2.png",
		},
		{ 
			"sprites/troll/trollDownAnimation1.png",
			"sprites/troll/trollDownAnimation2.png",
		},
		{ 
			"sprites/troll/trollLeftAnimation1.png",
			"sprites/troll/trollLeftAnimation2.png",
		},
		{ 
			"sprites/troll/trollRightAnimation1.png",
			"sprites/troll/trollRightAnimation2.png",
		},
		{
			"sprites/troll/trollDeathAnimation1.png",
			"sprites/troll/trollDeathAnimation2.png",
		}
	};
	
	public static final String GOLDEN_SNAKE_SPRITE_PATH[][] = 
	{
		{ 
			"sprites/golden snake/goldenSnakeUpAnimation1.png",
			"sprites/golden snake/goldenSnakeUpAnimation2.png",
		},
		{ 
			"sprites/golden snake/goldenSnakeDownAnimation1.png",
			"sprites/golden snake/goldenSnakeDownAnimation2.png",
		},
		{ 
			"sprites/golden snake/goldenSnakeLeftAnimation1.png",
			"sprites/golden snake/goldenSnakeLeftAnimation2.png",
		},
		{ 
			"sprites/golden snake/goldenSnakeRightAnimation1.png",
			"sprites/golden snake/goldenSnakeRightAnimation2.png",
		},
		{
			"sprites/golden snake/goldenSnakeDeathAnimation1.png",
			"sprites/golden snake/goldenSnakeDeathAnimation2.png",
		}
	};
	
	public static final String DEER_SPRITE_PATH[][] = 
	{
		{ 
			"sprites/deer/deerUpAnimation1.png",
			"sprites/deer/deerUpAnimation2.png",
		},
		{ 
			"sprites/deer/deerDownAnimation1.png",
			"sprites/deer/deerDownAnimation2.png",
		},
		{ 
			"sprites/deer/deerLeftAnimation1.png",
			"sprites/deer/deerLeftAnimation2.png",
		},
		{ 
			"sprites/deer/deerRightAnimation1.png",
			"sprites/deer/deerRightAnimation2.png",
		},
		{
			"sprites/deer/deerDeathAnimation1.png",
			"sprites/deer/deerDeathAnimation2.png",
		}
	};
	
	public static final String ARROW_SPRITE_PATH[] =
	{
		"sprites/arrow/arrowUp.png",
		"sprites/arrow/arrowDown.png",
		"sprites/arrow/arrowLeft.png",
		"sprites/arrow/arrowRight.png"
	};
	
	public static final String STONE_SWORD_SPRITE_PATH[] = 
	{
		"sprites/swords/stoneSwordUp.png",
		"sprites/swords/stoneSwordDown.png",
		"sprites/swords/stoneSwordLeft.png",
		"sprites/swords/stoneSwordRight.png",			
	};
	
	public static final String IRON_SWORD_SPRITE_PATH[] = 
	{
		"sprites/swords/ironSwordUp.png",
		"sprites/swords/ironSwordDown.png",
		"sprites/swords/ironSwordLeft.png",
		"sprites/swords/sword/ironSwordRight.png",			
	};
	
	public static final String GOLDEN_SNAKE_SPIT_SPRITE_PATH = "sprites/goldenSnakeSpit.png";
	
	public static final String TALL_GRASS_SPRITE_PATH = "sprites/tallGrass.png";

	public static final String GRASS_SPRITE_PATH = "sprites/tiles/grass.png";
	public static final String TERRAIN_SPRITE_PATH = "sprites/tiles/terrain.png";	
	public static final String GRASS_TERRAIN_SPRITE_PATH = "sprites/tiles/grass-terrain.png";
	public static final String TERRAIN_WATER_SPRITE_PATH = "sprites/tiles/terrain-water.png";
	public static final String WATER_SPRITE_PATH = "sprites/tiles/water.png";
	
	public static final String INVENTORY_TILE_SPRITE_PATH = "sprites/tiles/inventoryTile.png";
	public static final String INVENTORY_GUI_SPRITE_PATH = "sprites/inventoryGUI.png";
	public static final String CRAFTING_GUI_SPRITE_PATH = "sprites/craftingGUI.png";
	public static final String CRAFTING_BUTTON_NOT_PRESSING_SPRITE_PATH = "sprites/craftingButtonNotPressing.png";
	public static final String CRAFTING_BUTTON_PRESSING_SPRITE_PATH = "sprites/craftingButtonPressing.png";
	public static final String SCROLL_DOWN_NOT_PRESSING_SPRITE_PATH = "sprites/scrollDownNotPressing.png";
	public static final String SCROLL_UP_NOT_PRESSING_SPRITE_PATH = "sprites/scrollUpNotPressing.png";
	public static final String SCROLL_DOWN_PRESSING_SPRITE_PATH = "sprites/scrollDownPressing.png";
	public static final String SCROLL_UP_PRESSING_SPRITE_PATH = "sprites/scrollUpPressing.png";
	public static final String GUI_INTERFACE_SPRITE_PATH = "sprites/GUIInterface.png";
}