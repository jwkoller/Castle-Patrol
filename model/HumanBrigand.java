package koller.castlepatrol.model;

import java.util.Random;

/*************************************************************************************************
 * Child class of Enemy creates a new human brigand enemy object. Defines parent class attributes 
 * with preset constants and generates a weapon.
 * 
 * @author Jon Koller
 *
 *************************************************************************************************/

public class HumanBrigand extends Enemy
{
	private static final String NAME = "Human Brigand";
	private static final int STRENGTH = 13;
	private static final int DEXTERITY = 12;
	private static final int INTELLIGENCE = 8;
	private static final int HP_MULTIPLIER = 3;
	
	/**
	 * Constructor sets parent class attributes and calls load weapon method.
	 */
	public HumanBrigand() 
	{
		super(NAME, STRENGTH, DEXTERITY, INTELLIGENCE);	
		hitPoints = (STRENGTH + INTELLIGENCE) * HP_MULTIPLIER;
		baseArmorClass = DEXTERITY + (DEXTERITY % 4);
		loadWeapon();
	}

	/**
	 * Sets the weapon for the enemy object. Has random chance of being +1 or better.
	 */
	public void loadWeapon()
	{
		Random rng = new Random();
		
		int weapModifier = rng.nextInt(6) - rng.nextInt(11);
		
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
