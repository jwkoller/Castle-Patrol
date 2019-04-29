package koller.castlepatrol.view;

import java.util.ArrayList;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import koller.castlepatrol.model.*;

/************************************************************************************************************************
 * Main window that player interacts with after creating or loading character. Contains the dialog box, player info pane,
 * and central window that displays the various screens throughout the course of the game. Contains an inner class that 
 * controls the central window screens and any attributes in main window that need to be changed by sub window screens.
 * 
 * @author Jon Koller
 *
*************************************************************************************************************************/
public class PlayerMain 
{
	private Text[] playerStats = new Text[6];
	private Text currentWeaponName = new Text();
	private BorderPane mainViewPort = new BorderPane();
	private TextFlow dialogBox = new TextFlow();
	private ListView<Item> inventory = new ListView<>();
	private Button[] inventoryBtn = new Button[2];
	private Button savePlayerBtn = new Button("Save Player to file");
	private ScrollPane dialogHolder = new ScrollPane();
		
	private PlayerMainControl mainControl = new PlayerMainControl();
	
	private InsideCastleScreen insideCastleScreen;
	private TransitionScreen transitionScreen;
	
	private Game game;
	private Player player;
	private Stage stage;

	/**
	 * Constructor
	 */
	public PlayerMain()
	{
		stage = new Stage();
		Scene scene = new Scene(mainViewPort);
		stage.setScene(scene);
		stage.setTitle("Castle Patrol");
		
		//	Sets up various boxes to hold nodes within window
		VBox playerInfo = new VBox(10);
		playerInfo.setPrefHeight(600);
		
		playerInfo.setBackground(new Background(new BackgroundFill(Color.rgb(35, 39, 45), null, null)));
		VBox playerStatInfoBox = new VBox(15);
		VBox playerStatsBox = new VBox(16);
		VBox currentWeaponBox = new VBox(5);
		HBox[] playerHBox = new HBox[4];

		//	Creates and formats labels for right side of window player pane
		Label[] playerStatLbl = new Label[6];
		for(int i = 0; i < playerStatLbl.length; i++)
		{
			playerStatLbl[i] = new Label(getPlayerStatLabel(i));
			playerStatLbl[i].setFont(Font.font("Baskerville Old Face", FontWeight.BOLD, 16));
			playerStatLbl[i].setTextFill(Color.ANTIQUEWHITE);
			playerStatInfoBox.getChildren().add(playerStatLbl[i]);
		}
		//	Creates and formats text boxes that hold the current Player stat info 
		for(int i = 0; i < playerStats.length; i++)
		{
			playerStats[i] = new Text();
			playerStats[i].setFont(Font.font("Baskerville Old Face", 18));
			playerStats[i].setFill(Color.ANTIQUEWHITE);
			playerStatsBox.getChildren().add(playerStats[i]);
		}
		playerStatsBox.setAlignment(Pos.BASELINE_RIGHT);
		playerStatsBox.setPrefWidth(155);
		
		//	Creates and formats the HBox that holds the two VBoxes for player info labels and stats
		playerHBox[0] = new HBox();
		playerHBox[0].setPadding(new Insets(5));
		playerHBox[0].getChildren().addAll(playerStatInfoBox, playerStatsBox);
		
		playerHBox[0].setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, null, null)));
		
		//	Creates and formats the box to label and display current weapon player object is using
		Label weaponLbl = new Label("Current Weapon:");
		weaponLbl.setFont(Font.font("Baskerville Old Face", FontWeight.BOLD, 18));
		weaponLbl.setTextFill(Color.ANTIQUEWHITE);
		currentWeaponName.setFont(Font.font("Baskerville Old Face", FontWeight.BOLD, 16));
		currentWeaponName.setFill(Color.ANTIQUEWHITE);
		currentWeaponBox.getChildren().addAll(weaponLbl, currentWeaponName);
		currentWeaponBox.setAlignment(Pos.CENTER);
		
		//	Creates and formats HBox and label for the inventory box header
		playerHBox[1] = new HBox();
		playerHBox[1].setPadding(new Insets(4));
		playerHBox[1].setAlignment(Pos.CENTER);
		Label inventoryTitle = new Label("Inventory");
		inventoryTitle.setFont(Font.font("Baskerville Old Face", FontWeight.BOLD, 14));
		inventoryTitle.setTextFill(Color.ANTIQUEWHITE);
		playerHBox[1].getChildren().add(inventoryTitle);
		
		inventory.setMaxHeight(190);
		
		//	Creates and formats the buttons and HBox that holds them for the Drink Potion and Equip weapon buttons
		playerHBox[2] = new HBox(30);
		playerHBox[2].setAlignment(Pos.CENTER);
		inventoryBtn[0] = new Button("Equip Selected Weapon");
		inventoryBtn[1] = new Button("Drink Selected Potion");
		for(Button b : inventoryBtn)
		{
			b.setMaxWidth(100);
			b.setWrapText(true);
			b.setTextAlignment(TextAlignment.CENTER);
			playerHBox[2].getChildren().add(b);
		}
		
		//	Creates and formats the HBox and Save Player info button
		playerHBox[3] = new HBox();
		playerHBox[3].setPadding(new Insets(10));
		playerHBox[3].getChildren().add(savePlayerBtn);
		playerHBox[3].setAlignment(Pos.CENTER);
		
		//	Creates and formats the dialog box at bottom of main border pain window
		dialogHolder.setFitToWidth(true);
		dialogHolder.setPrefHeight(100);
		dialogHolder.setContent(dialogBox);
		dialogBox.setBackground(new Background(new BackgroundFill(Color.rgb(35, 39, 45), null, null)));
		dialogBox.setPadding(new Insets(3));

		playerInfo.getChildren().addAll(playerHBox[0], currentWeaponBox, playerHBox[1], inventory, playerHBox[2], playerHBox[3]);
		
		mainViewPort.setRight(playerInfo);
		mainViewPort.setBottom(dialogHolder);
		
		//	Button action events
		inventoryBtn[0].setOnAction(e -> equipWeaponButtonHandler());
		inventoryBtn[1].setOnAction(e -> drinkPotionButtonHandler());
		savePlayerBtn.setOnAction(e -> savePlayerButtonHandler());
	}
	
	/**
	 * Displays main window user interacts with after player creation/load and populates the player info 
	 * side of window. 
	 * 
	 * @param game		Instance of Game class passed from Main method.
	 * @param player	Current Player object passed from either load player window or create player window.
	 */
	public void show(Game game, Player player)
	{
		this.game = game;
		this.player = player;
		mainControl.loadInsideCastleScreen();
		loadPlayerStats();
		loadInventory();
		showCurrentWeapon();
		addToDialogBox("");
		addToDialogBox("");
		addToDialogBox("");
		addToDialogBox("Welcome to Tridentopia " + player.getName() + ".");
		stage.show();
	}
	
	/**
	 * Handles the initial load of player info into the player pane labels with info from current Player object.
	 */
	private void loadPlayerStats()
	{
		playerStats[0].setText(player.getName());
		playerStats[1].setText(String.valueOf(player.getStr()));
		playerStats[2].setText(String.valueOf(player.getDex()));
		playerStats[3].setText(String.valueOf(player.getInt()));
		setPlayerHealth();
		if(player instanceof Wizard)
		{
			setPlayerSpellPoints();
		}
		else
		{
			playerStats[5].setText("0");
		}
	}
	
	/**
	 * Updates the text in the Player pane with the new value of player health points called from Player class
	 */
	private void setPlayerHealth()
	{
		playerStats[4].setText(String.valueOf(player.getHealth()));
	}
	
	/**
	 * Updates the text in the Player pane with the new value of player Spell points called from Player class..
	 */
	private void setPlayerSpellPoints()
	{
		playerStats[5].setText(String.valueOf(((Wizard) player).getSpellPool()));
	}
	
	/**
	 * Updates the player pane health and spell point displays with new information by calling them from the 
	 * individual methods for each.
	 */
	private void updatePlayerVitals()
	{
		setPlayerHealth();
		if(player instanceof SpellCaster)
		{
			setPlayerSpellPoints();
		}
	}
	
	/**
	 * Sets the list view with the inventory array list in the Player class.
	 */
	private void loadInventory()
	{
		inventory.getItems().setAll(player.getInventory());
	}
	
	/**
	 * Simply used to clean up labeling process for player panel labels in constructor. Allows for use of for loop.
	 * 
	 * @param num	Integer marker for which string is needed to fill the label text.
	 * 
	 * @return		Returns label text
	 */
	private String getPlayerStatLabel(int num)
	{
		String label = new String();
		
		if(num == 0)
		{
			label = "Name: ";
		}
		else if(num == 1)
		{
			label = "Strength: ";
		}
		else if(num == 2)
		{
			label = "Dexterity: ";
		}
		else if(num == 3)
		{
			label = "Intelligence: ";
		}
		else if(num == 4)
		{
			label = "Hit Points";
		}
		else
		{
			label = "Spell Points";
		}
		
		return label;
	}
	
	/**
	 * Sets font, color, and size of any text passed to it, then adds that text to the text flow inside a scroll pane.
	 * 
	 * @param dialog	String that is to be formated and then displayed in text flow for user.
	 */
	private void addToDialogBox(String dialog)
	{
		Text nextLine = new Text(dialog + "\n");
		nextLine.setFill(Color.ANTIQUEWHITE);
		nextLine.setFont(Font.font("Baskerville Old Face", 20));
		dialogBox.getChildren().add(nextLine);
		dialogHolder.setVvalue(1.0);
	}
	
	/**
	 * Loads the center section of main border pane with whatever sub window the controller class needs.
	 * 
	 * @param pane	Any type of pane passed that then gets set inside the center section of the main border pane.
	 */
	private void loadMainWindow(Pane pane)
	{
		mainViewPort.setCenter(pane);
	}
	
	/**
	 * Handles the equip weapon button action event. If weapon is highlighted in list view, passes weapon to the weapon
	 * swap method in player class and updates the listView inventory and the current weapon display in the player panel.
	 */
	private void equipWeaponButtonHandler()
	{
		String line;
		Item item = inventory.getFocusModel().getFocusedItem();
		
		if(item instanceof Weapon)
		{
			player.useNewWeapon(item);
			line = "You have equiped a " + item + ".";
			loadInventory();
			showCurrentWeapon();
		}
		else
		{
			line = "You must select a weapon to equip.";
			Alert err = new Alert(AlertType.ERROR, line);
			err.show();
		}
		
		addToDialogBox(line);
	}
	
	/**
	 * Handles the drink potion button action event. Checks if potion is selected in listView then calls the appropriate drink
	 * potion method in the Player class. Display error alert if no potion is highlighted in listView.
	 */
	private void drinkPotionButtonHandler()
	{
		String line;
		Item item = inventory.getFocusModel().getFocusedItem();;
		
		if(item instanceof HealthPotion)
		{
			int hp = player.drinkPotion((HealthPotion) item);
			player.reduceInventory(item);
			loadInventory();
			setPlayerHealth();
			line = "Drinking a health potion recovers " + String.valueOf(hp) + " hit points.";
		}
		else if(item instanceof ManaPotion)
		{
			if(player instanceof Wizard)
			{
				int sp = player.drinkPotion((ManaPotion) item);
				player.reduceInventory(item);
				loadInventory();
				setPlayerSpellPoints();
				line = "Drinking a mana potion recovers " + String.valueOf(sp) + " spell points.";
			}
			else
			{
				line = "Bad things happen when non-wizards drink mana potions. Best to avoid that.";
				Alert err = new Alert(AlertType.WARNING, line);
				err.show();
			}
		}
		else
		{
			line = "You must select a potion to drink.";
			Alert err = new Alert(AlertType.WARNING, line);
			err.show();
		}
		
		addToDialogBox(line);
	}
	
	/**
	 * Handles the save player button action event. Passes current player object to Game class and attempts to save info to file,
	 * if it fails error alert is shown, otherwise an information alert is shown to confirm player was saved.
	 */
	private void savePlayerButtonHandler()
	{
		String line;
		
		if(!game.savePlayerToFile(player))
		{
			line = "There was a problem saving your character. Please try again in a few minutes or contact technical support.";
			Alert err = new Alert(AlertType.ERROR, line);
			err.show();
		}
		else
		{
			line = "Your current hit points, stats, and inventory have been saved for player " + player.getName() + ".";
			Alert confirm = new Alert(AlertType.INFORMATION, line);
			confirm.show();
		}
		
		addToDialogBox(line);
	}
	
	/**
	 * Calls current weapon from current player object and sets text in player panel of window.
	 */
	private void showCurrentWeapon()
	{
		currentWeaponName.setText(player.getWeapon().toString());
	}
	

	/*************************************************************************************************************************
	 * Control inner class for swapping sub window screens within center of main player window and acting as buffer between 
	 * the main window and others.
	 * 
	 * @author Jon Koller
	 *
	 *************************************************************************************************************************/
	public class PlayerMainControl
	{
		/**
		 * Passes string generated from other sub windows to the method in PlayerMain that displays it the text flow box.
		 * 
		 * @param dialog	Passed string dialog generated from other window.
		 */
		public void dialogBox(String dialog)
		{
			addToDialogBox(dialog);
		}
		
		/**
		 * Calls method that updates player health and spell points (if applicable) after a change is generated from sub windows.
		 */
		public void playerVitals()
		{
			updatePlayerVitals();
		}
		
		/**
		 * Calls method that updates player inventory after a change is generated from sub windows (BattleScreen window is only one).
		 */
		public void playerInventory()
		{
			loadInventory();
		}

		/**
		 * Loads the sub window with the inside castle screen view. Initializes if first time, otherwise loads to center pane 
		 * of PlayerMain border pane.
		 */
		public void loadInsideCastleScreen()
		{
			if(insideCastleScreen == null)
			{
				insideCastleScreen = new InsideCastleScreen(mainControl);
			}
			
			loadMainWindow(insideCastleScreen);
		}
		
		/**
		 * Loads the sub window with the transition screen view that plays between fight scenes. Initializes if first time, otherwise loads to 
		 * center pane of PlayerMain border pane.
		 */
		public void loadTransitionScreen()
		{
			if(transitionScreen == null)
			{
				transitionScreen = new TransitionScreen(mainControl);
			}
			
			loadMainWindow(transitionScreen);
		}

		/**
		 * Loads the sub window with the battle screen view and passes it one Enemy object obtained from the Game class. If the list of enemies 
		 * from Game class is empty, loads the patrol cleared screen which then allows player to return to castle.
		 */
		public void loadBattleScreen()
		{
			Enemy newEnemy = game.getOneEnemy();
			
			if(newEnemy != null)
			{
				BattleScreen battle = new BattleScreen(mainControl);
				battle.populateBattleScreen(game, player, newEnemy);
				loadMainWindow(battle);
			}
			else
			{
				loadMainWindow(new PatrolClearScreen(mainControl));
			}
		}
		
		/**
		 * Loads the sub window with the battle victory screen view. New screen generated after each battle victory.
		 */
		public void loadVictoryScreen()
		{
			loadMainWindow(new VictoryScreen(mainControl));
		}
		
		/**
		 * Loads the sub window with the player has died screen view if the player has gone below 0 hit ponts.
		 */
		public void loadPlayerDeadScreen()
		{
			loadMainWindow(new PlayerDeadScreen(mainControl));
		}
		
		/**
		 * Loads the sub window with the game over screen view. Once player has eliminated all enemies in list from Game class and returned to castle
		 * for final talk with NPC there.
		 */
		public void loadGameOverScreen()
		{
			new GameOverScreen(mainControl, stage);
		}
		
		/**
		 * Loads a new instance of the welcome window players first see and resets the castle window and transition windows. Reloads the enemies lis
		 * in game class as well. Called once the current game is over and user wishes to restart game.
		 */
		public void restartGame()
		{
			insideCastleScreen = null;
			transitionScreen = null;
			game.loadEnemies(game.getNumOfEnemies());
			WelcomeWindow restart = new WelcomeWindow(new Stage());
			restart.show(game);
			
			stage.close();
		}
		
		/**
		 * Closes the current stage and effectively exits the game.
		 */
		public void quitGame()
		{
			stage.close();
		}
		
		/*----------------Pulls game files and images from Game class to pass to sub window class panes to maintain separation of concern-----------------------*/
		
		public ArrayList<String> getTransitionDialog()
		{
			return game.getTransitionDialog();
		}
		
		public ArrayList<String> getIntroDialog()
		{
			return game.getIntroDialog();
		}
		
		public Image getInsideCastleImage()
		{
			return game.getInsideCastleImage();
		}
		
		public Image getTransitionImage()
		{
			return game.getTransitionImage();
		}
		
		public Image getOgreImage()
		{
			return game.getOgreImage();
		}
		
		public Image getOgreShamanImage()
		{
			return game.getOgreShamanImage();
		}
		
		public Image getTrollImage()
		{
			return game.getTrollImage();
		}
		
		public Image getHumanBanditImage()
		{
			return game.getHumanBanditImage();
		}
		
		public Image getBattleVictoryImage()
		{
			return game.getBattleVictoryImage();
		}
		
		public Image getPlayerDeadImage()
		{
			return game.getPlayerDeadImage();
		}
		
		public Image getPatrolClearImage()
		{
			return game.getPatrolClearImage();
		}
		
		public Image getGameOverImage()
		{
			return game.getGameOverImage();
		}
	}
}

