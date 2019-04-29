package koller.castlepatrol.model;

import java.util.Random;

/*************************************************************************************************
 * Factory to generate random spell objects for creating MagicStaff weapon objects.
 * 
 * @author Jon Koller
 *
 *************************************************************************************************/
public class SpellFactory 
{
	/**
	 * 
	 * @return Returns a random spell object
	 */
	public static Spell getRandomSpell()
	{
		Random rng = new Random();
		int num = rng.nextInt(6);
		Spell spell;
		
		if(num == 0)
		{
			spell = new FireBallSpell();
		}
		else if(num == 1)
		{
			spell = new MagicMissleSpell();
		}
		else if(num == 2)
		{
			spell = new LightningBoltSpell();
		}
		else if(num == 3)
		{
			spell = new RayOfFrostSpell();
		}
		else if(num == 4)
		{
			spell = new BurningHandsSpell();
		}
		else
		{
			spell = new ScorchingRaySpell();
		}
		
		return spell;
	}
}
