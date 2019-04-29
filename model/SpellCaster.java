package koller.castlepatrol.model;

/*************************************************************************************************
 * Interface that creates the methods for GameEntity classes to cast spells.
 * 
 * @author Jon Koller
 *
 *************************************************************************************************/
public interface SpellCaster 
{
	int getSpellPool();
	void reduceSpellPool(Spell spell);
	int castSpell();
	int drinkManaPotion(Potion potion);
}
