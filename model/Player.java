package koller.castlepatrol.model;

import java.util.ArrayList;
import java.util.Collections;

/*************************************************************************************************
 * Abstract parent class for all user/player objects. Defines stats, names and behaviors. 
 * Implements the GameEntity interface which allows association with the Enemy Class.
 * 
 * @author Jon Koller
 *
 *************************************************************************************************/

public abstract class Player implements GameEntity
{
	private final String name;
	protected int strength;
	protected int dexterity;
	protected int intelligence;
	protected int hitPoints;
	protected ArrayList<Item> inventory = new ArrayList<>();
	protected Item weapon;
	protected int baseArmorClass;
	
	/**
	 * Constructor
	 * 
	 * @param name			Player name
	 * @param strength		Player strength
	 * @param dexterity		Player dexterity
	 * @param intelligence	Player intelligence
	 */
	public Player(String name, int strength, int dexterity, int intelligence)
	{
		this.name = name;
		this.strength = strength;
		this.dexterity = dexterity;
		this.intelligence = intelligence;
	}
	
	@Override
	public String getName() 
	{
		return name;
	}
	
	@Override
	public int getHealth() 
	{
		return hitPoints;
	}

	@Override
	public boolean isAlive() 
	{
		if(hitPoints > 0)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	@Override
	public Weapon getWeapon()
	{
		return (Weapon) weapon;
	}

	@Override
	public int getStr() 
	{
		return strength;
	}

	@Override
	public int getInt() 
	{
		return intelligence;
	}

	@Override
	public int getDex() 
	{
		return dexterity;
	}
	
	@Override
	public int getInitiative()
	{
		return (dexterity/2) + (intelligence / 2);
	}
	
	@Override
	public ArrayList<Item> getInventory()
	{
		return inventory;
	}
	
	/**
	 * Adds array list of items to player's current inventory. Called when player defeats and enemy and 
	 * the enemy inventory is passed to player.
	 * 
	 * @param items	List of item objects player is being passed.
	 */
	public void addInventory(ArrayList<Item> items)
	{
		inventory.addAll(items);
		Collections.sort(inventory);
	}
	
	/**
	 * Adds individual item to player inventory. Called when player is first created by child classes when loading
	 * starting inventory.
	 * 
	 * @param item	Item class object being passed to inventory.
	 */
	public void addInventory(Item item)
	{
		inventory.add(item);
		Collections.sort(inventory);
	}
	
	/**
	 * Reduces the inventory by the item being passed, whether by equipping a weapon thus removing it from the 
	 * inventory or drinking a potion.
	 * 
	 * @param item	Item object to be removed from inventory.
	 */
	public void reduceInventory(Item item)
	{
		inventory.remove(item);
	}
	
	@Override
	public void reduceHealth(int hitPoints) 
	{
		this.hitPoints -= hitPoints;
	}
	
	@Override
	public int getArmorClass()
	{
		return baseArmorClass + ((Weapon) weapon).getArmorBonus();
	}
	
	@Override
	public int getToHit()
	{
		return (strength % 3) + ((Weapon) weapon).getItemToHit();
	}
	
	@Override
	public int getAttackDamage()
	{
		return (strength % 3) + ((Weapon) weapon).getWeaponDamage();
	}
	
	/**
	 * Allows player to swap weapons. Adds the player's current weapon to inventory, sets the current weapon
	 * to the one being passed, them removes the passed weapon from the inventory.
	 * 
	 * @param weapon Item object being passed player is going to use.
	 */
	public void useNewWeapon(Item weapon)
	{
		addInventory(this.weapon);
		this.weapon = weapon;
		reduceInventory(weapon);
	}
	
	/**
	 * Defines the means for a player to drink a potion and add to their hit points. Gets hp amount of potion,
	 * checks to see if adding that amount will put player's current hit points over max amount and sets the 
	 * player's hit points as needed.
	 * 
	 * @param potion	Health potion object
	 * 
	 * @return	Returns int amount that potion refilled of players hitpoints
	 */
	public int drinkPotion(Potion potion)
	{
		int addedPoints;
		
		if(potion instanceof ManaPotion)
		{
			addedPoints = ((SpellCaster) this).drinkManaPotion(potion);
		}
		else
		{
			addedPoints = potion.getTotalRefilled();
			
			if(hitPoints + addedPoints > getMaxHitPoints())
			{
				addedPoints = getMaxHitPoints() - hitPoints;
			}
			
			hitPoints += addedPoints;
		}

		return addedPoints;
	}

	/**
	 * Returns the max hit points of player object by checking whether its fighter or wizard and calling the 
	 * Overridden method in the child class.
	 * 
	 * @return	Returns int amount of maximum hit points player can have.
	 */
	public int getMaxHitPoints()
	{
		if(this instanceof Fighter)
		{
			return ((Fighter) this).getMaxHitPoints();
		}
		else
		{
			return ((Wizard) this).getMaxHitPoints();
		}
	}
	
	@Override
	public String toString()
	{
		if(this instanceof Fighter)
		{
			return "Fighter";
		}
		else
		{
			return "Wizard";
		}
	}
}
