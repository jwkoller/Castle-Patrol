package koller.castlepatrol.view;

import java.util.ArrayList;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import koller.castlepatrol.model.*;
import koller.castlepatrol.view.PlayerMain.PlayerMainControl;

/*************************************************************************************************
 * Battle screen that loads for each enemy in enemies list in Game class and handles the combat
 * sequences.
 * 
 * @author Jon Koller
 *
 *************************************************************************************************/

public class BattleScreen extends StackPane
{
	private HBox hBoxBottomFighter = new HBox(100);
	private HBox hBoxBottomWizard = new HBox(100);
	private Button rollInitiativeBtn = new Button("Roll Initiative");
	private Button fighterAttackBtn = new Button("Attack");
	private Button wizardAttackBtn = new Button("Attack");
	private Button castSpellBtn = new Button("Cast Spell");
	
	private Image img;
	private Game game;
	private Player player;
	private Enemy currentEnemy;
	private PlayerMainControl mainControl;

	/**
	 * Constructor
	 * 
	 * @param mainControl	Control object that interacts with this pane and PlayerMain window
	 */
	public BattleScreen(PlayerMainControl mainControl)
	{
		this.mainControl = mainControl;
		
		//	Sets background to black for image files that are not 800x600
		this.setBackground(new Background(new BackgroundFill(Color.BLACK, null, null)));
		
		//	Formats and places the roll initiative button at bottom of pane
		rollInitiativeBtn.setFont(Font.font("Forte", FontWeight.BOLD, 20));
		StackPane.setMargin(rollInitiativeBtn, new Insets(0, 0, 25, 0));
		StackPane.setAlignment(rollInitiativeBtn, Pos.BOTTOM_CENTER);
		
		//	Formats the attack button for fighters, the attack button for wizards, and the cast spell button
		fighterAttackBtn.setFont(Font.font("Forte", FontWeight.BOLD, 20));
		fighterAttackBtn.setPrefWidth(150);		
		wizardAttackBtn.setFont(Font.font("Forte", FontWeight.BOLD, 20));
		wizardAttackBtn.setPrefWidth(150);	
		castSpellBtn.setFont(Font.font("Forte", FontWeight.BOLD, 20));
		castSpellBtn.setPrefWidth(150);
		
		//	The Fighter HBox just holds the fighter attack button while the Wizard HBox holds it's attack button and the 
		//	cast spell button
		hBoxBottomFighter.getChildren().add(fighterAttackBtn);
		hBoxBottomWizard.getChildren().addAll(wizardAttackBtn, castSpellBtn);

		this.getChildren().add(rollInitiativeBtn);
		
		//	Various button handlers
		rollInitiativeBtn.setOnAction(e -> rollInitiativeButtonHandler());
		fighterAttackBtn.setOnAction(e -> attack(player, currentEnemy));
		wizardAttackBtn.setOnAction(e -> attack(player, currentEnemy));
		castSpellBtn.setOnAction(e -> castSpell(player, currentEnemy));
	}
	
	/**
	 * Finishes populating the battle screen pane by passing the necessary Game, Player and Enemy object so as not
	 * to pass them to the constructor and thus maintaining MCV separation. Formats the HBoxes that hold the attack 
	 * buttons at bottom of screen since the current enemy is now defined.
	 * 
	 * @param game		Game object
	 * @param player	Player object
	 * @param enemy		Enemy object
	 */
	public void populateBattleScreen(Game game, Player player, Enemy enemy)
	{
		this.game = game;
		this.player = player;
		currentEnemy = enemy;
		mainControl.dialogBox("A " + currentEnemy.getName() + " blocks your path! Roll for initiative!");
		setBackgroundImage();
		getBottomHBox().setAlignment(Pos.BOTTOM_CENTER);
		getBottomHBox().setPadding(new Insets(0, 0, 25, 0));

	}
	
	/**
	 * Pulls appropriate background image based on the current enemy and sets it in background.
	 */
	public void setBackgroundImage()
	{
		img = getBackgroundImage();
		ImageView imgView = new ImageView(img);
		imgView.setPreserveRatio(true);
		imgView.setFitWidth(800);
		imgView.setCache(true);
		this.getChildren().add(imgView);
		imgView.toBack();
	}
	/**
	 * Loads the appropriate HBox that holds the attack buttons based on player class. 
	 * 
	 * @return	Returns HBox
	 */
	private HBox getBottomHBox()
	{
		if(player instanceof Fighter)
		{
			return hBoxBottomFighter;
		}
		else
		{
			return hBoxBottomWizard;
		}
	}
	
	/**
	 * Loads the correct image based on the Enemy type from game class.
	 * 
	 * @return	Returns the correct image
	 */
	private Image getBackgroundImage()
	{
		Image img;
		
		if(currentEnemy instanceof Troll)
		{
			img = mainControl.getTrollImage();
		}
		else if(currentEnemy instanceof OgreShaman)
		{
			img = mainControl.getOgreShamanImage();
		}
		else if(currentEnemy instanceof Ogre)
		{
			img = mainControl.getOgreImage();
		}
		else
		{
			img = mainControl.getHumanBanditImage();
		}
		
		return img;
	}
	
	/**
	 * Handles the roll initiative button event by getting an initiative roll for player and current enemy,
	 * displaying the values in the text flow box of the PlayerMain window, then beginning the attack if
	 * the current enemy won the roll. Finally removes itself and loads the buttons in the appropriate HBox.
	 */
	private void rollInitiativeButtonHandler()
	{
		int playerRoll = game.rollInitiative(player);
		int enemyRoll = game.rollInitiative(currentEnemy);
		
		mainControl.dialogBox(player.getName() + "'s initiative roll is " + playerRoll + ".");
		mainControl.dialogBox("The " + currentEnemy.getName() + "'s roll is " + enemyRoll + ".");
		
		if(playerRoll >= enemyRoll)
		{
			mainControl.dialogBox(player.getName() + " wins the roll and attacks first!");
		}
		else
		{
			mainControl.dialogBox("The " + currentEnemy.getName() + " wins the roll and attacks first!");
			attack(currentEnemy, player);
		}
		
		this.getChildren().remove(rollInitiativeBtn);
		this.getChildren().add(getBottomHBox());
	}
	
	/**
	 * Handles the attacks by player and current enemy. If player is attacking, checks if enemy defender is alive and and then allows 
	 * enemy to attack back. If enemy is a spell caster it sends the current enemy object to the cast spell method to complete it's 
	 * turn if it has enough spell points to cast the spell from it's staff.
	 * 
	 * @param attacker	GameEntity of either player or enemy depending on whose turn
	 * @param defender	GameEntity of either player or enemy depending on whose turn
	 */
	private void attack(GameEntity attacker, GameEntity defender)
	{		
		if(attacker instanceof Enemy && attacker instanceof SpellCaster && ((SpellCaster) attacker).getSpellPool() >= ((MagicStaff) attacker.getWeapon()).getStaffSpell().getSpellCost())
		{
			castSpell(attacker, defender);
		}
		else
		{
			int roll = game.d20Roll();
			int damage = attacker.getAttackDamage();
			
			if(game.attack(roll, damage, attacker, defender))
			{
				attackSuccessText(attacker.getName(), roll, attacker.getToHit(), defender.getName(), defender.getArmorClass());
				damageText(attacker.getName(), damage, defender.getName());
			}
			else
			{
				attackMissedText(attacker.getName(), roll, attacker.getToHit(), defender.getName(), defender.getArmorClass());
			}
		}
		
		if(!defender.isAlive())
		{
			deadDefender(defender);
		}
		
		if(defender instanceof Enemy && defender.isAlive())
		{
			attack(defender, attacker);
		}
		
		mainControl.playerVitals();
	}
	
	/**
	 * Handles the attacks for spell casting enemies or or players choosing to cast spell.  Checks staff spell cost
	 * against available spell pool. If spell pool is not large enough it informs player with an alert, ends the flow with a return
	 * statement and allows the Player to take a different action so as not to lose turn.
	 * 
	 * @param attacker	GameEntity of either player or enemy depending on whose turn
	 * @param defender	GameEntity of either player or enemy depending on whose turn
	 */
	private void castSpell(GameEntity attacker, GameEntity defender)
	{	
		if(!(attacker.getWeapon() instanceof MagicStaff))
		{
			String line = "You can't cast a spell without a Magic Staff equipped!";
			Alert err = new Alert(AlertType.ERROR, line);
			mainControl.dialogBox(line);
			err.showAndWait();
		}
		else
		{
			if(((SpellCaster) attacker).getSpellPool() >= ((MagicStaff) attacker.getWeapon()).getStaffSpell().getSpellCost())
			{
				int spellDamage = game.spellAttack(attacker, defender);
				String spell = ((MagicStaff) attacker.getWeapon()).getStaffSpell().getSpellName();
				castSpellText(attacker.getName(), spell, spellDamage, defender.getName());
			}
			else
			{
				String line = "You don't have enough mana to cast that spell! Drink a potion to refill or make a physical attack.";
				Alert err = new Alert(AlertType.WARNING, line);
				err.show();
				mainControl.dialogBox(line);
				return;
			}
			
			if(!defender.isAlive())
			{
				deadDefender(defender);
			}
			
			if(defender instanceof Enemy && defender.isAlive())
			{
				attack(defender, attacker);
			}
			
			mainControl.playerVitals();
		}
	}
	
	/**
	 * Handles the text formating for successful combat attacks and sends the string to the dialog box via the main window
	 * controller.
	 * 
	 * @param atkName	Attacker name
	 * @param roll		Attacker's to-hit roll
	 * @param toHit		Attacker's total to-hit score
	 * @param defName	Defender name
	 * @param defArmor	Defender armor class
	 */
	private void attackSuccessText(String atkName, int roll, int toHit, String defName, int defArmor )
	{
		mainControl.dialogBox(String.format("%s rolls a %d plus their to-hit bonus of %d beats %s's armor class of %d for a hit!", 
				atkName, roll, toHit, defName, defArmor));
	}
	
	/**
	 * Handles the text formating for the damage amount applied and sends the string to the dialog box via the main window controller.
	 * 
	 * @param atkName	Attacker name
	 * @param damage	Damage amount
	 * @param defName	Defender name
	 */
	private void damageText(String atkName, int damage, String defName)
	{
		mainControl.dialogBox(String.format("%s deals %d damage to %s!", atkName, damage, defName));
	}
	
	/**
	 * Handles the text formating for unsuccessful combat attacks and sends the string to the dialog box via the main window
	 * controller.
	 * 
	 * @param atkName	Attacker name
	 * @param roll		Attacker's to-hit roll
	 * @param toHit		Attacker's total to-hit score
	 * @param defName	Defender name
	 * @param defArmor	Defender armor class
	 */
	private void attackMissedText(String atkName, int roll, int toHit, String defName, int defArmor)
	{
		mainControl.dialogBox(String.format("%s rolls a %d plus their to-hit bonus of %d doesn't get passed %s's armor class of %d! The attack missed!", 
				atkName, roll, toHit, defName, defArmor));
	}
	
	/**
	 * Handles the text formating for casting a spell and sends the string to the dialog box via the main window controller.
	 * 
	 * @param atkName		Attacker name
	 * @param spellName		Spell name cast
	 * @param damage		Damage amount from spell
	 * @param defName		Defender name
	 */
	private void castSpellText(String atkName, String spellName, int damage, String defName)
	{
		mainControl.dialogBox(String.format("%s casts %s and does %d damage to %s!", atkName, spellName, damage, defName));
	}
	
	/**
	 * Formats the text of the inventory that's passed from an enemy to player after the enemy has been defeated, then sends the 
	 * text to the dialog box in the main window via the Controller.
	 * 
	 * @param inventory		ArrayList of items from slain enemy
	 */
	private void addInventoryText(ArrayList<Item> inventory)
	{
		String line = "You have looted ";
		
		if(inventory.size() == 1)
		{
			line += ("a " + inventory.get(0) + ".");
		}
		else
		{
			for(int i = 0; i < inventory.size(); i++)
			{
				if(i != inventory.size() - 1)
				{
					line += ("a " + inventory.get(i) + ", ");
				}
				else
				{
					line += ("and a " + inventory.get(i) + ".");
				}
			}
		}

		mainControl.dialogBox(line);
	}
	
	/**
	 * Handles the action when a defender dies. If defender is a player it loads the player dead screen and informs player their
	 * game is over. If defender is an enemy it displays the dialog, loads the victory screen and transfers the enemy 
	 * inventory to the player. Then updates the inventory list in the main window.
	 * 
	 * @param defender	GameEntity object that was defeated
	 */
	private void deadDefender(GameEntity defender)
	{
		if(defender instanceof Player)
		{
			mainControl.loadPlayerDeadScreen();
		}
		else
		{
			mainControl.dialogBox("You have defeated a " + currentEnemy.getName() + " and made these lands a little safer.");
			mainControl.loadVictoryScreen();
			ArrayList<Item> inventory = defender.getInventory();
			addInventoryText(inventory);
			player.addInventory(inventory);
			mainControl.playerInventory();
		}
	}
}
