package koller.castlepatrol.model;

/*************************************************************************************************
 * Weapon interface for Item child classes GameEntities use as weapons. Allows for association
 * between the various weapon items.
 * 
 * @author Jon Koller
 *
 *************************************************************************************************/
public interface Weapon 
{
	int getItemToHit();
	int getWeaponDamage();
	int getLowDamage();
	int getHighDamage();
	int getModifier();
	int getArmorBonus();
}
