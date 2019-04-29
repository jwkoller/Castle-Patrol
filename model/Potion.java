package koller.castlepatrol.model;

/*************************************************************************************************
 * Interface that allows for association between the HealthPotion and ManaPotion child classes
 * of the Item class.
 * 
 * @author Jon Koller
 *
 *************************************************************************************************/
public interface Potion 
{
	int getLowRefillAmt();
	int getHighRefillAmt();
	int getTotalRefilled();	
}
