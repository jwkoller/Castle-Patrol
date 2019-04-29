package koller.castlepatrol.model;

/*************************************************************************************************
 * Defines Player class child Fighter. 
 * 
 * @author Jon Koller
 *
 *************************************************************************************************/

public class Fighter extends Player
{
	private static final int HP_MULTIPLIER = 5;
	
	/**
	 * Secondary constructor called for new player creation.
	 * 
	 * @param name			Player name
	 * @param strength		Player strength
	 * @param dexterity		Player dexterity
	 * @param intelligence	Player intelligence
	 */
	public Fighter(String name, int strength, int dexterity, int intelligence) 
	{
		this(name, strength, dexterity, intelligence,  0, (HP_MULTIPLIER * (strength + intelligence)));
	}
	
	/**
	 * Primary constructor called by secondary constructor and when creating a new player object from 
	 * a saved player file.
	 * 
	 * @param name			Player name
	 * @param strength		Player strength
	 * @param dexterity		Player dexterity
	 * @param intelligence	Player intelligence
	 * @param weaponMod		Mod of weapon player was using at time of save file or 0 for new player
	 * @param hitPoints		Hit points of player at time of save file or calculated for new player
	 */
	public Fighter(String name, int strength, int dexterity, int intelligence, int weaponMod, int hitPoints)
	{
		super(name, strength, dexterity, intelligence);
		baseArmorClass = this.dexterity + (this.dexterity % 4);
		this.hitPoints = hitPoints;
		weapon = new Sword(weaponMod);
		addInventory(new HealthPotion());
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
}
