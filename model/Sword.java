package koller.castlepatrol.model;

import java.util.Random;

/*************************************************************************************************
 * Weapon Item used by all non SpellCaster GameEntities. Child class of Item and implements the 
 * Weapon interface to allow association with the MagicStaff class.
 * 
 * @author Jon Koller
 *
 *************************************************************************************************/
public class Sword extends Item implements Weapon
{
	private static final String SWORD = "Sword";
	private final int TO_HIT = 8;
	private final int LOW_DAMAGE = 10;
	private final int HIGH_DAMAGE = 16;
	private Random rng = new Random();
	private final int modifier;

	/**
	 * Constructor
	 * 
	 * @param modifier	Int modifier for weapon
	 */
	public Sword(int modifier) 
	{
		super(SWORD);
		this.modifier = modifier;
	}
	
	@Override
	public String getItemName()
	{
		if(modifier > 0)
		{
			return "+" + modifier + " " + SWORD;
		}
		else
		{
			return SWORD;
		}
	}

	@Override
	public int getModifier()
	{
		return modifier;
	}
	
	@Override
	public int getArmorBonus()
	{
		return (TO_HIT/2) + modifier;
	}

	@Override
	public int getItemToHit() 
	{
		return TO_HIT + modifier;
	}

	@Override
	public int getWeaponDamage() 
	{
		return getLowDamage() + rng.nextInt(getHighDamage() - getLowDamage() + 1);
	}

	@Override
	public int getLowDamage() 
	{
		return LOW_DAMAGE + modifier;
	}

	@Override
	public int getHighDamage() 
	{
		return HIGH_DAMAGE + modifier;
	}
}
