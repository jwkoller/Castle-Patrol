package koller.castlepatrol.model;

/*************************************************************************************************
 * Defines Player class child Wizard. Implements SpellCaster interface and defines those methods. 
 * 
 * @author Jon Koller
 *
 *************************************************************************************************/

public class Wizard extends Player implements SpellCaster
{
	private static final int HP_MULTIPLIER = 4;
	private static final int SP_MULTIPLIER = 6;
	private int spellPool;
	
	/**
	 * Secondary constructor called when new Wizard player object is created.
	 * 
	 * @param name			Player name
	 * @param strength		Player strength
	 * @param dexterity		Player dexterity
	 * @param intelligence	Player intelligence
	 */
	public Wizard(String name, int strength, int dexterity, int intelligence) 
	{
		this(name, strength, dexterity, intelligence, 0, ((strength + intelligence) * HP_MULTIPLIER), intelligence * SP_MULTIPLIER);
	}
	
	/**
	 * Primary constructor called from secondary constructor and when new Wizard player object is 
	 * loaded from save file.
	 * 
	 * @param name			Player name
	 * @param strength		Player strength
	 * @param dexterity		Player dexterity
	 * @param intelligence	Player intelligence
	 * @param weaponMod		Mod of weapon player was using at time of save file or 0 for new player
	 * @param hitPoints		Hit points of player at time of save file or calculated for new player
	 * @param spellPoints	Spell points of player at time of save file or calculated for new player
	 */
	public Wizard(String name, int strength, int dexterity, int intelligence, int weaponMod, int hitPoints, int spellPoints)
	{
		super(name, strength, dexterity, intelligence);
		baseArmorClass = this.dexterity;
		this.hitPoints = hitPoints;
		weapon = new MagicStaff(weaponMod);
		spellPool = spellPoints;
		addInventory(new ManaPotion());
		addInventory(new HealthPotion());
	}
	
	/**
	 * Max hit points player can have.
	 * 
	 * @return	Returns int amount of hit points
	 */
	public int getMaxHitPoints()
	{
		return (strength + intelligence) * HP_MULTIPLIER;
	}

	@Override
	public int getSpellPool() 
	{
		return spellPool;
	}

	/**
	 * Allows player implementing SpellCaster interface to drink a mana potion.
	 * 
	 * @param potion	Mana potion object player is using
	 * 
	 * @return			Returns int amount of mana refilled by potion
	 * 
	 */
	@Override
	public int drinkManaPotion(Potion potion) 
	{
		int sp = potion.getTotalRefilled();
		spellPool += sp;
		
		return sp;
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
}
