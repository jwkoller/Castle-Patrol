package koller.castlepatrol.model;

import java.util.ArrayList;

/*************************************************************************************************
 * Interface that allows for association between Player and Enemy classes.
 * 
 * @author Jon Koller
 *
 *************************************************************************************************/
public interface GameEntity 
{
	String getName();
	int getHealth();
	int getStr();
	int getInt();
	int getDex();
	int getArmorClass();
	int getToHit();
	int getInitiative();
	int getAttackDamage();
	void reduceHealth(int hitPoints);
	boolean isAlive();
	Weapon getWeapon();
	ArrayList<Item> getInventory();
}
