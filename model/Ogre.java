package koller.castlepatrol.model;

import java.util.Random;

/*************************************************************************************************
 * Child class of Enemy creates a new Ogre enemy object. Defines parent class attributes with 
 * preset constants and generates a weapon.
 * 
 * @author Jon Koller
 *
 *************************************************************************************************/
public class Ogre extends Enemy
{
	private static final String NAME = "Ogre";
	private static final int STRENGTH = 19;
	private static final int DEXTERITY = 8;
	private static final int INTELLIGENCE = 5;
	private static final int HP_MULTIPLIER = 4;
	
	/**
	 * Constructor sets the parent class attributes and calls the load weapon method.
	 */
	public Ogre() 
	{
		super(NAME, STRENGTH, DEXTERITY, INTELLIGENCE);
		hitPoints = (STRENGTH + INTELLIGENCE) * HP_MULTIPLIER;
		baseArmorClass = DEXTERITY * 2;
		loadWeapon();
	}

	/**
	 * Sets the weapon for the enemy object. Has random chance of being +1 or better.
	 */
	public void loadWeapon()
	{
		Random rng = new Random();
		
		int weapModifier = rng.nextInt(6) - rng.nextInt(10);
		
		if(weapModifier < 0)
		{
			weapon = new Sword(0);
		}
		else
		{
			weapon = new Sword(weapModifier);
		}
		inventory.add(weapon);
	}
}
