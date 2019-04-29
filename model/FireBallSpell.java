package koller.castlepatrol.model;

/*************************************************************************************************
 * New spell creation cast by GameEntities that implement SpellCaster interface. 
 * 
 * @author Jon Koller
 *************************************************************************************************/

public class FireBallSpell extends Spell
{
	private static final String NAME = "Fire Ball";
	private static final int LOW_DAMAGE = 20;
	private static final int HIGH_DAMAGE = 30;
	private static final int SPELL_COST = 14;
	
	public FireBallSpell() 
	{
		super(NAME, LOW_DAMAGE, HIGH_DAMAGE, SPELL_COST);;
	}
}
