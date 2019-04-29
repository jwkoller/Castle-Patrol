package koller.castlepatrol.model;

import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import javafx.scene.image.Image;

/*************************************************************************************************
 * Primary driver class of game. Singleton class. Handles the creating of players, loading enemies
 * and holding array list of enemy objects, loads dialog and image files, saving and loading 
 * player files, and attack and spell casting.
 * 
 * @author Jon Koller
 *
 *************************************************************************************************/

public class Game 
{
	private static Game instance = new Game();
	private final String KING_IMG_FILE = "file:images//king.jpg";
	private final String TRANSITION_IMG_FILE = "file:images//RidingTransition.gif";
	private final String OGRE_IMG_FILE = "file:images//ogre.jpg";
	private final String OGREMAGE_IMG_FILE = "file:images//ogremage.jpg";
	private final String TROLL_IMG_FILE = "file:images//troll.jpg";
	private final String HUMAN_IMG_FILE = "file:images//humanBandit.jpg";
	private final String BATTLE_VICTORY_IMG_FILE = "file:images//victory.gif";
	private final String PLAYER_DEAD_IMG_FILE = "file:images//gameover.gif";
	private final String PATROL_CLEAR_IMG_FILE = "file:images//forestClear.gif";
	private final String GAME_OVER_IMAGE_FILE = "file:images//gameWinner.gif";
	private final String INTRO_DIALOG_FILE = ".//GameFiles//IntroDialog.txt";
	private final String TRANSITION_DIALOG_FILE = ".//GameFiles//TransitionDialog.txt";
	private Random rng = new Random();
	private ArrayList<Enemy> enemies = new ArrayList<>();
	private ArrayList<String> introDialog = new ArrayList<>();
	private ArrayList<String> transitionDialog = new ArrayList<>();
	private ArrayList<Image> gameImages = new ArrayList<>();
	private int numOfEnemies;
	
	/**
	 * Private constructor for singleton class.
	 */
	private Game(){}
	
	/**
	 * Allows retrieval of single Game object.
	 * 
	 * @return	Returns Game object
	 */
	public static Game getInstance()
	{
		return instance;
	}
	
	/**
	 * Creates a new player based on information passed from UI.
	 * 
	 * @param playerClass	Class player is creating
	 * @param name			Player name
	 * @param strength		Player strength
	 * @param dexterity		Player dexterity
	 * @param intelligence	Player intelligence
	 * 
	 * @return	Returns player object to UI.
	 */
	public Player createPlayer(String playerClass, String name, int strength, int dexterity, int intelligence)
	{
		Player player;
		
		if(playerClass.equals("Fighter"))
		{
			player = new Fighter(name, strength, dexterity, intelligence);
		}
		else
		{
			player = new Wizard(name, strength, dexterity, intelligence);	
		}
		
		return player;
	}
	
	/**
	 * Saves current player to .txt file in Saved Player folder.
	 * 
	 * @param player	Player object to be saved
	 * 
	 * @return	Returns boolean of save success.
	 */
	public boolean savePlayerToFile(Player player)
	{
		try
		{
			PrintWriter printer = new PrintWriter(new FileWriter("./SavedPlayers/" + player.getName() + ".txt", false));
			
			printer.printf("%s,%s,%s,%s,%s,%s", player.getName(), player.getStr(), player.getDex(), player.getInt(), player.getWeapon().getModifier(), player.getHealth());
			
			if(player instanceof Wizard)
			{
				printer.printf(",%s", ((Wizard) player).getSpellPool());
			}
			printer.printf("%n");
			
			for(Item i : player.getInventory())
			{
				if((i instanceof HealthPotion))
				{
					printer.println("HealthPotion");
				}
				else if(i instanceof ManaPotion)
				{
					printer.println("ManaPotion");
				}
				else if(i instanceof Sword)
				{
					printer.printf("%s%n", ((Sword) i).getModifier());
				}
				else
				{
					printer.printf("%s,%s%n", ((MagicStaff) i).getModifier(), ((MagicStaff) i).getStaffSpell().getSpellName());
				}
			}
			printer.close();
		}
		catch(IOException ex)
		{
			return false;
		}

		return true;
	}
	
	/**
	 * Creates a player from a saved file and returns it to UI.
	 * 
	 * @param fileName	File name user wishes to load.
	 * 
	 * @return	Returns player object.
	 */
	public Player loadSavedPlayer(String fileName)
	{
		Player player;
		
		try
		{
			Scanner saveFile = new Scanner(new FileInputStream("./SavedPlayers/" + fileName + ".txt"));
			
			try
			{
				String line = saveFile.nextLine();
				String[] fields = line.split(",");
				
				String name = fields[0];
				int str = Integer.parseInt(fields[1]);
				int dex = Integer.parseInt(fields[2]);
				int intel = Integer.parseInt(fields[3]);
				int weapMod = Integer.parseInt(fields[4]);
				int hp = Integer.parseInt(fields[5]);
				
				if(fields.length > 6)
				{
					int sp = Integer.parseInt(fields[6]);
					player = new Wizard(name, str, dex, intel, weapMod, hp, sp);
				}
				else
				{
					player = new Fighter(name, str, dex, intel, weapMod, hp);
				}
				
				ArrayList<Item> items = new ArrayList<>();
				
				while(saveFile.hasNextLine())
				{
					String[] inventory = saveFile.nextLine().split(",");
					
					if(inventory[0].matches("HealthPotion"))
					{
						items.add(new HealthPotion());
					}
					else if(inventory[0].matches("ManaPotion"))
					{
						items.add(new ManaPotion());
					}
					else
					{
						int mod = Integer.parseInt(inventory[0]);
						
						if(inventory.length > 1)
						{
							String spell = inventory[1];
							if(spell.matches("Fire Ball"))
							{
								items.add(new MagicStaff(mod, new FireBallSpell()));
							}
							else if(spell.matches("Lightning Bolt")) 
							{
								items.add(new MagicStaff(mod, new LightningBoltSpell()));
							}
							else if(spell.matches("Ray of Frost"))
							{
								items.add(new MagicStaff(mod, new RayOfFrostSpell()));
							}
							else if(spell.matches("Scorching Ray")) 
							{
								items.add(new MagicStaff(mod, new ScorchingRaySpell()));
							}
							else if(spell.matches("Magic Missle"))
							{
								items.add(new MagicStaff(mod, new MagicMissleSpell()));
							}
							else
							{
								items.add(new MagicStaff(mod, new BurningHandsSpell()));
							}
						}
						else
						{
							items.add(new Sword(mod));
						}
					}
				}
				
				player.addInventory(items);	
			}
			catch(Exception ex)
			{
				player = null;
			}

			saveFile.close();
		}
		catch(IOException ex)
		{
			player = null;
		}
		
		return player;
	}
	
	/**
	 * Loads necessary game files to start game. Calls method to populate enemies array list,
	 * and calls methods to load UI screen dialog files.
	 * 
	 * @param numOfEnemies	Int number of enemies to populate enemies array list to start game.
	 */
	public void loadGameFiles(int numOfEnemies)
	{
		loadEnemies(numOfEnemies);
		loadIntroDialog();
		loadTransitionDialog();
		loadImages();
	}
	
	/**
	 * Populates enemies array list that player will battle durring game.
	 * 
	 * @param numOfEnemies	Int number of enemies to load
	 */
	public void loadEnemies(int numOfEnemies)
	{
		this.numOfEnemies = numOfEnemies;
		
		for(int i = 0; i < numOfEnemies; i++)
		{
			enemies.add(EnemyFactory.getRandomEnemy());
		}
	}
	
	/**
	 * Returns the whole enemy array list.
	 * 
	 * @return	Returns array list of enemies
	 */
	public ArrayList<Enemy> getEnemies()
	{
		return enemies;
	}
	
	/**
	 * Pulls out and returns a single enemy object from the enemies array list and returns to UI.
	 * If enemies array list is empty returns null.
	 * 
	 * @return	Returns Enemy object
	 */
	public Enemy getOneEnemy()
	{
		if(enemies.size() == 0)
		{
			return null;
		}
		return enemies.remove(enemies.size() - 1);
	}
	
	/**
	 * 
	 * @return	Returns the number of enemies game started with for restart purposes.
	 */
	public int getNumOfEnemies()
	{
		return numOfEnemies;
	}

	/**
	 * Handles the attack of one GameEntity to another. Checks if attack was successful and reduces
	 * hit points of the defender. Called from UI.
	 * 
	 * @param attackRoll	Attack roll GameEntity made.
	 * @param damage		Damage being done 
	 * @param attacker		GameEntity doing the attacking
	 * @param defender		GameEntity being attacked
	 * 
	 * @return	Returns boolean on success of the attack
	 */
	public boolean attack(int attackRoll, int damage, GameEntity attacker, GameEntity defender)
	{
		if(attacker.getToHit() + attackRoll > defender.getArmorClass())
		{
			defender.reduceHealth(damage);
			return true;
		}
		else
		{
			return false;
		}
	}
	
	/**
	 * Handles the casting of a spell from one GameEntity to another. Gets the amount of damage spell does
	 * and reduces defender hit points by that amount. Then returns same amount back to UI for display.
	 * 
	 * @param attacker	GameEntity casting the spell
	 * @param defender	GameEntity on the receiving end of the spell
	 * 
	 * @return	Returns int amount of damage done
	 */
	public int spellAttack(GameEntity attacker, GameEntity defender)
	{
		int spellDamage = ((SpellCaster) attacker).castSpell();
		
		defender.reduceHealth(spellDamage);
		
		return spellDamage;
	}
	
	/**
	 * Calculates a 20 sided dice roll.
	 * 
	 * @return	Returns int value of dice roll
	 */
	public int d20Roll()
	{
		return rng.nextInt(20) + 1;
	}

	/**
	 * Handles the initiative roll for a GameEntity.
	 * 
	 * @param entity	GameEntity doing the initiative roll
	 * 
	 * @return	Returns int value of initiative roll
	 */
	public int rollInitiative(GameEntity entity)
	{
		return d20Roll() + entity.getInitiative();
	}
	
	/**
	 * Loads the text for the inside castle screen dialog from file.
	 */
	public void loadIntroDialog()
	{
		try
		{
			Scanner fileInput = new Scanner(new FileInputStream(INTRO_DIALOG_FILE));
			
			while(fileInput.hasNextLine())
			{
				String line = fileInput.nextLine();
				introDialog.add(line);
			}
			fileInput.close();
		}
		catch(IOException ex)
		{
			introDialog.add("Error loading Intro Dialog file.");
			System.out.println(ex.getMessage());
		}
	}
	
	/**
	 * 
	 * @return	Returns the string array list of inside castle screen dialog loaded from file
	 */
	public ArrayList<String> getIntroDialog()
	{
		return introDialog;
	}
	
	/**
	 * Loads the text for the transition screen dialog from file.
	 */
	public void loadTransitionDialog()
	{
		try
		{
			Scanner fileInput = new Scanner(new FileInputStream(TRANSITION_DIALOG_FILE));
			
			while(fileInput.hasNextLine())
			{
				String line = fileInput.nextLine();
				transitionDialog.add(line);
			}
			fileInput.close();
		}
		catch(IOException ex)
		{
			transitionDialog.add("Error loading Transition dialog file.");
			System.out.println(ex.getMessage());
		}
	}
	
	/**
	 * 
	 * @return	Returns the string array list of dialog for the transition screens
	 */
	public ArrayList<String> getTransitionDialog()
	{
		return transitionDialog;
	}
	
	/**
	 * Loads images needed for UI into array list.
	 */
	public void loadImages()
	{	
		gameImages.add(new Image(KING_IMG_FILE));
		gameImages.add(new Image(TRANSITION_IMG_FILE));
		gameImages.add(new Image(OGRE_IMG_FILE));
		gameImages.add(new Image(OGREMAGE_IMG_FILE));
		gameImages.add(new Image(TROLL_IMG_FILE));
		gameImages.add(new Image(HUMAN_IMG_FILE));
		gameImages.add(new Image(BATTLE_VICTORY_IMG_FILE));
		gameImages.add(new Image(PLAYER_DEAD_IMG_FILE));
		gameImages.add(new Image(PATROL_CLEAR_IMG_FILE));
		gameImages.add(new Image(GAME_OVER_IMAGE_FILE));
	}
	
	/*----------------Returns various images to UI control class for display-----------------------*/
	
	public Image getInsideCastleImage()
	{
		return gameImages.get(0);
	}
	
	public Image getTransitionImage()
	{
		return gameImages.get(1);
	}
	
	public Image getOgreImage()
	{
		return gameImages.get(2);
	}
	
	public Image getOgreShamanImage()
	{
		return gameImages.get(3);
	}
	
	public Image getTrollImage()
	{
		return gameImages.get(4);
	}
	
	public Image getHumanBanditImage()
	{
		return gameImages.get(5);
	}
	
	public Image getBattleVictoryImage()
	{
		return gameImages.get(6);
	}
	
	public Image getPlayerDeadImage()
	{
		return gameImages.get(7);
	}
	
	public Image getPatrolClearImage()
	{
		return gameImages.get(8);
	}
	
	public Image getGameOverImage()
	{
		return gameImages.get(9);
	}
}	

