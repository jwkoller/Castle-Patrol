package koller.castlepatrol.model;

import java.util.ArrayList;
import java.util.Random;

/*************************************************************************************************
 * Parent class for all enemies in game. Defines stats, names, and behaviors. Implements
 * GameEntity interface which allows association with the Player class.
 * 
 * @author Jon Koller
 *
 *************************************************************************************************/

public abstract class Enemy implements GameEntity
{
	private final String name;
	protected int strength;
	protected int dexterity;
	protected int intelligence;
	protected int hitPoints;
	protected Item weapon;
	protected int baseArmorClass;
	
	protected ArrayList<Item> inventory = new ArrayList<>();
	
	/**
	 * Constructor creates enemy stats and calls the load inventory method.
	 * 
	 * @param name			Enemy name
	 * @param strength		Enemy strength
	 * @param dexterity		Enemy dexterity
	 * @param intelligence	Enemy intelligence
	 */
	public Enemy(String name, int strength, int dexterity, int intelligence)
	{
		this.name = name;
		this.strength = strength;
		this.dexterity = dexterity;
		this.intelligence = intelligence;

		loadInventory();
	}
	
	/**
	 * Loads the initial inventory of potions enemies will start with. Weapons are set within each 
	 * child of enemy class. Chance for enemies to have 0-2 Health potions and 0-1 Mana potion.
	 */
	public void loadInventory()
	{
		Random rng = new Random();
		
		int numPotions = rng.nextInt(3);
		
		for(int i = 0; i < numPotions; i++)
		{
			inventory.add(new HealthPotion());
		}
		
		numPotions = rng.nextInt(2);
		
		for(int i = 0; i < numPotions; i++)
		{
			inventory.add(new ManaPotion());
		}
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
	public Weapon getWeapon()
	{
		return (Weapon) weapon;
	}
	
	@Override
	public int getInitiative()
	{
		return (dexterity/2) + (intelligence / 2);
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
		return (strength % 4) + ((Weapon) weapon).getWeaponDamage();
	}

	@Override
	public void reduceHealth(int hitPoints) 
	{
		this.hitPoints -= hitPoints;		
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
	public ArrayList<Item> getInventory() 
	{
		return inventory;
	}

}
