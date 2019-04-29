package koller.castlepatrol.model;

/*************************************************************************************************
 * Parent class of all inventory items in game.
 * 
 * @author Jon Koller
 *
 *************************************************************************************************/
public class Item implements Comparable<Item>
{
	private String name;

	/**
	 * Constructor
	 * 
	 * @param name	Name of item
	 */
	public Item(String name)
	{
		this.name = name;
	}
	
	/**
	 * 
	 * @return Returns item name
	 */
	public String getItemName()
	{
		return name;
	}

	/**
	 * Compare method to sort player inventory for display in UI.
	 * 
	 * @Override
	 */
	public int compareTo(Item nextItem) 
	{
		return this.getItemName().compareTo(nextItem.getItemName());
	}
	
	@Override
	public String toString()
	{
		return getItemName();
	}
}
