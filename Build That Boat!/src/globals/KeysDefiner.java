package globals;

public class KeysDefiner 
{
	public static final int KEYS_DIMENSION = 4;
	private static boolean gameKeys[] = new boolean[KEYS_DIMENSION];
	
	public static final int UP = 0;
	public static final int DOWN = 1;
	public static final int LEFT = 2;
	public static final int RIGHT = 3;
	
	public void setKeyState(int key, boolean value) { gameKeys[key] = value; }
	
	public boolean getKeyState(int key) { return gameKeys[key]; }
}