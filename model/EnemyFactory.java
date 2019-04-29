package koller.castlepatrol.model;

import java.util.Random;

/*************************************************************************************************
 * Factory generates a random enemy to fill the enemies array list in the Game class to start.
 * 
 * @author Jon Koller
 *
 *************************************************************************************************/

public class EnemyFactory 
{
	public static Enemy getRandomEnemy()
	{
		Random rng = new Random();
		int num = rng.nextInt(4);
		Enemy enemy;
		
		if(num == 0)
		{
			enemy = new Ogre();
		}
		else if (num == 1)
		{
			enemy = new HumanBrigand();
		}
		else if (num == 2)
		{
			enemy = new Troll();
		}
		else
		{
			enemy = new OgreShaman();
		}
		
		return enemy;
	}
}
