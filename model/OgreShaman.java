package koller.castlepatrol.model;

import java.util.Random;

/*************************************************************************************************
 * Child class of Enemy creates a new Ogre Shaman enemy object. Defines parent class attributes with 
 * preset constants and generates a weapon. Implements SpellCaster interface and defines methods
 * for that.
 * 
 * @author Jon Koller
 *
 *************************************************************************************************/

public class OgreShaman extends Enemy implements SpellCaster
{
	private static final String NAME = "Ogre Shaman";
	private static final int SP_MULTIPLIER = 6;
	private static final int STRENGTH = 10;
	private static final int DEXTERITY = 9;
	private static final int INTELLIGENCE = 18;
	private static final int HP_MULTIPLIER = 2;
	
	private int spellPool;
	
	/**
	 * Constructor defines parent class attributes as well as spell points for this object. Calls the 
	 * load weapon method.
	 */
	public OgreShaman() 
	{
		super(NAME, STRENGTH, DEXTERITY, INTELLIGENCE);	
		spellPool = intelligence * SP_MULTIPLIER;
		hitPoints = (STRENGTH + INTELLIGENCE) * HP_MULTIPLIER;
		baseArmorClass = DEXTERITY;
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
			weapon = new MagicStaff(0);
		}
		else
		{
			weapon = new MagicStaff(weapModifier);
		}
		inventory.add(weapon);
	}

	@Override
	public int getSpellPool() 
	{
		return spellPool;
	}

	@Override
	public void reduceSpellPool(Spell spell) 
	{
		spellPool -= spell.getSpellCost();
	}

	@Override
	public int castSpell() 
	{
		reduceSpellPool(((MagicStaff) weapon).getStaffSpell());
		
		return ((MagicStaff) weapon).getStaffSpell().getTotalSpellDamage() + ((MagicStaff) weapon).getModifier();
	}

	@Override
	public int drinkManaPotion(Potion potion) 
	{
		int sp = potion.getTotalRefilled();
		spellPool += sp;
		
		return sp;
	}
}
