package koller.castlepatrol.model;

/*************************************************************************************************
 * New spell creation cast by GameEntities that implement SpellCaster interface. 
 * 
 * @author Jon Koller
 *************************************************************************************************/

public class ScorchingRaySpell extends Spell
{
	private static final String NAME = "Scorching Ray";
	private static final int LOW_DAMAGE = 18;
	private static final int HIGH_DAMAGE = 26;
	private static final int SPELL_COST = 12;
	
	public ScorchingRaySpell() 
	{
		super(NAME, LOW_DAMAGE, HIGH_DAMAGE, SPELL_COST);;
	}
}
