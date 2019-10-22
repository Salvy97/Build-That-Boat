package graphics;

import static globals.ConstantsCollection.*;

import globals.Sprite;

public class Tile extends Sprite
{
	public Tile(String spritePath) { super(spritePath, calculateWidth(), calculateHeight()); }
	
	public static int calculateXPosition(int pos) { return (SCREEN_W / TILE_SCALE) * pos; }
	public static int calculateYPosition(int pos) { return (SCREEN_H / TILE_SCALE) * pos; }
	public static int calculateWidth() { return SCREEN_W / TILE_SCALE; }
	public static int calculateHeight() { return SCREEN_H / TILE_SCALE; }
}