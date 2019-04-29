package koller.castlepatrol.model;

/*************************************************************************************************
 * New spell creation cast by GameEntities that implement SpellCaster interface. 
 * 
 * @author Jon Koller
 *************************************************************************************************/

public class MagicMissleSpell extends Spell
{
	private static final String NAME = "Magic Missle";
	private static final int LOW_DAMAGE = 12;
	private static final int HIGH_DAMAGE = 20;
	private static final int SPELL_COST = 6;
	
	public MagicMissleSpell() 
	{
		super(NAME, LOW_DAMAGE, HIGH_DAMAGE, SPELL_COST);;
	}
}
