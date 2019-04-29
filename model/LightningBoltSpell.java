package koller.castlepatrol.model;

/*************************************************************************************************
 * New spell creation cast by GameEntities that implement SpellCaster interface. 
 * 
 * @author Jon Koller
 *************************************************************************************************/

public class LightningBoltSpell extends Spell
{
	private static final String NAME = "Lightning Bolt";
	private static final int LOW_DAMAGE = 16;
	private static final int HIGH_DAMAGE = 24;
	private static final int SPELL_COST = 10;
	
	public LightningBoltSpell() 
	{
		super(NAME, LOW_DAMAGE, HIGH_DAMAGE, SPELL_COST);;
	}
}
