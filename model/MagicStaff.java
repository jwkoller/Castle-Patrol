package koller.castlepatrol.model;

import java.util.Random;

/*************************************************************************************************
 * Weapon used by spell casters in game.
 * 
 * @author Jon Koller
 *
 *************************************************************************************************/
public class MagicStaff extends Item implements Weapon
{
	private static final String STAFF = "Magic Staff";
	private final int TO_HIT = 4;
	private final int LOW_DAMAGE = 4;
	private final int HIGH_DAMAGE = 8;
	private Random rng = new Random();
	private final int modifier;
	private Spell staffSpell;

	/**
	 * Secondary constructor called when creating for new GameEntities.
	 * 
	 * @param modifier	Int modifier of weapon
	 */
	public MagicStaff(int modifier) 
	{
		this(modifier, SpellFactory.getRandomSpell());
	}
	
	/**
	 * Primary constructor called by secondary constructor and when creating weapon for loading
	 * SpellCaster player objects from file.
	 * 
	 * @param modifier		Int modifier of weapon
	 * @param staffSpell	Spell stored on weapon
	 */
	public MagicStaff(int modifier, Spell staffSpell)
	{
		super(STAFF);
		this.modifier = modifier;
		this.staffSpell = staffSpell;
	}
	
	@Override
	public String getItemName()
	{
		if(modifier > 0)
		{
			return "+" + modifier + " " + STAFF + " - " + staffSpell.getSpellName();
		}
		else
		{
			return STAFF + " - " + staffSpell.getSpellName();
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
		return TO_HIT + (modifier * 2);
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
	
	/**
	 * 
	 * @return	Returns spell object of spell saved on Staff
	 */
	public Spell getStaffSpell()
	{
		return staffSpell;
	}
}
