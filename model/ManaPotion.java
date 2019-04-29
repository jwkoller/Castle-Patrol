package koller.castlepatrol.model;

import java.util.Random;

/*************************************************************************************************
 * Allows players to refill spell points for Player classes implementing the SpellCaster 
 * interface. Child of Item class and implements potion interface to allow association with the
 * HealthPotion class.
 * 
 * @author Jon Koller
 *
 *************************************************************************************************/

public class ManaPotion extends Item implements Potion
{
	private static final String NAME = "Mana Potion";
	private static final int LOW_MANA_AMT = 45;
	private static final int HIGH_MANA_AMT = 60;
	private Random rng = new Random();

	public ManaPotion() 
	{
		super(NAME);
	}

	@Override
	public int getLowRefillAmt() 
	{
		return LOW_MANA_AMT;
	}

	@Override
	public int getHighRefillAmt() 
	{
		return HIGH_MANA_AMT;
	}

	@Override
	public int getTotalRefilled() 
	{
		return getLowRefillAmt() + rng.nextInt(getHighRefillAmt() - getLowRefillAmt()) + 1;
	}
}
