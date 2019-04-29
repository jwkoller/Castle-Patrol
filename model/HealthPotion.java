package koller.castlepatrol.model;

import java.util.Random;

/*************************************************************************************************
 * Allows players to refill hit points. Child of Item class and implements potion interface to 
 * allow association with the ManaPotion class.
 * 
 * @author Jon Koller
 *
 *************************************************************************************************/
public class HealthPotion extends Item implements Potion
{
	private static final String NAME = "Health Potion";
	private static final int LOW_HEAL_AMT = 28;
	private static final int HIGH_HEAL_AMT = 40;
	private Random rng = new Random();

	public HealthPotion() 
	{
		super(NAME);
	}

	@Override
	public int getLowRefillAmt() 
	{
		return LOW_HEAL_AMT;
	}

	@Override
	public int getHighRefillAmt() 
	{
		return HIGH_HEAL_AMT;
	}

	@Override
	public int getTotalRefilled() 
	{
		return getLowRefillAmt() + rng.nextInt(getHighRefillAmt() - getLowRefillAmt()) + 1;
	}
}
