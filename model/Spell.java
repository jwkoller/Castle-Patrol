package koller.castlepatrol.model;

import java.util.Random;

/*************************************************************************************************
 * Abstract parent class of all spells in game.
 * 
 * @author Jon Koller
 *
 *************************************************************************************************/

public abstract class Spell
{
	private String spellName;
	private int lowDamage;
	private int highDamage;
	private int spellPoolCost;
	private Random rng = new Random();
	
	/**
	 * Constructor for spell.
	 * 
	 * @param name				Spell name
	 * @param lowDamage			Minimum damage spell can do
	 * @param highDamage		Maximum damage spell can do
	 * @param spellPoolCost		Spell pool cost to cast spell
	 */
	public Spell(String name, int lowDamage, int highDamage, int spellPoolCost)
	{
		spellName = name;
		this.lowDamage = lowDamage;
		this.highDamage = highDamage;
		this.spellPoolCost = spellPoolCost;
	}
	
	public String getSpellName() 
	{
		return spellName;
	}
	public int getLowDamage() 
	{
		return lowDamage;
	}
	public int getHighDamage() 
	{
		return highDamage;
	}
	
	public int getSpellCost()
	{
		return spellPoolCost;
	}
	
	public int getTotalSpellDamage()
	{
		return getLowDamage() + rng.nextInt(getHighDamage()  - getLowDamage() + 1);
	}
}
