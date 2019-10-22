package entities.items.manipulators;

public interface EquipableItem 
{
	public abstract int getDamage();
	public abstract int getVigorUsed();
	public abstract void decreaseDurability();
	public abstract int getDurability();
	public abstract int getItemID();
	public abstract void use(int playerX, int playerY, int direction);
	public abstract void disable();
	public abstract boolean isDisabled();
	public abstract int getAudioID();
}