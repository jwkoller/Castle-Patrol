package koller.castlepatrol.view;

import java.io.File;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import koller.castlepatrol.model.*;

/*************************************************************************************************
 * Window that allows players to load a saved player from file.
 * 
 * @author Jon Koller
 *
 *************************************************************************************************/

public class LoadSavedPlayer
{
	private final File FOLDER = new File(".//SavedPlayers");
	private final String IMG_FILE = "file:images//castle2.jpg";
	private Button[] buttons = new Button[3];
	private ListView<String> savedGameList = new ListView<>();
	
	private Player player;
	private Game game;
	private Stage stage;
	private Image backgroundImg;
	
	/**
	 * Constructor
	 * 
	 * @param passedStage	Stage passed from Welcome Window
	 */
	public LoadSavedPlayer(Stage passedStage)
	{
		stage = passedStage;
		BorderPane pane = new BorderPane();
		HBox hBox = new HBox(50);
		Scene scene = new Scene(pane, 800, 600);
		stage.setScene(scene);
		stage.setTitle("Load Saved Player");
		
		//	Creates and formats buttons for cancel, new player, and load player
		buttons[0] = new Button("Cancel");
		buttons[1] = new Button("New Character");
		buttons[2] = new Button("Load Character");		
		for(Button b : buttons)
		{
			b.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, null, null)));
			b.setTextFill(Color.ANTIQUEWHITE);
			b.setFont(Font.font("Castellar", FontWeight.BOLD, 16));
			b.setPrefSize(250, 50);
			b.setOnMouseEntered(e -> b.setTextFill(Color.DARKGRAY));
			b.setOnMouseExited(e -> b.setTextFill(Color.ANTIQUEWHITE));
		}
		
		//	Formats HBox that holds the three buttons
		hBox.setAlignment(Pos.CENTER);
		BorderPane.setMargin(hBox, new Insets(0, 0, 50, 0));
		hBox.getChildren().addAll(buttons[0], buttons[1], buttons[2]);
		
		//	Loads image from file and sets it as background of pane
		backgroundImg = getImage(IMG_FILE);
		BackgroundSize size = new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, false);
		pane.setBackground(new Background(new BackgroundImage(backgroundImg, BackgroundRepeat.NO_REPEAT, 
				BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, size)));
		
		//	Sets nodes in appropriate areas of pane
		pane.setCenter(savedGameList);
		pane.setBottom(hBox);
		
		//	Formats listView objects and cells inside
		savedGameList.setMaxSize(300, 200);
		savedGameList.setCellFactory(param -> new ListCell<String>() 
		{
			@Override
			public void updateItem(String fileName, boolean empty)
			{
				super.updateItem(fileName, empty);

				if (empty) 
				{
					setText(null);
					setBackground(new Background(new BackgroundFill(Color.BLACK, null, null)));
				} 
				else 
				{
					setText(fileName);
					setFont(Font.font("Castellar", FontWeight.BOLD, 18));
					setAlignment(Pos.CENTER);
					setBackground(new Background(new BackgroundFill(Color.GRAY, null, null)));
				}
			}
		});
		
		//	Loads list of saved players
		loadSaveList();
		
		//	Button handlers
		buttons[0].setOnAction(e -> cancelButtonHandler());
		buttons[1].setOnAction(e -> createButtonHandler());
		buttons[2].setOnAction(e -> loadPlayer());
	}
	
	/**
	 * Displays window
	 * 
	 * @param game	Game object
	 */
	public void show(Game game)
	{
		this.game = game;
		stage.show();
	}
	
	/**
	 * Attempts to load image from file to display in background of pane.
	 * 
	 * @param fileName	Name of image file location from class constant.
	 * 
	 * @return			Returns image if found or null if not.
	 */
	private Image getImage(String fileName)
	{
		Image img;
		
		try
		{
			img = new Image(fileName);
		}
		catch(Exception ex)
		{
			img = null;
		}
		
		return img;
	}
	
	/**
	 * Loads the saved game listView object with the saved game files. Calls the method in Game object
	 * to retrieve them.
	 */
	private void loadSaveList()
	{
		File[] fileList = FOLDER.listFiles();
		
		if(fileList.length == 0)
		{
			savedGameList.getItems().add("No Saved Characters");
		}
		else
		{
			for(File f : fileList)
			{
				savedGameList.getItems().add(f.getName().replaceAll(".txt", ""));
			}
		}
	}
	
	/**
	 * Handles the load player button event. Checks if saved player is highlighted and retrieves the player object 
	 * from Game class. Loads PlayerMain window.
	 */
	private void loadPlayer()
	{
		if(savedGameList.getSelectionModel().getSelectedItem() == null || savedGameList.getFocusModel().getFocusedItem().equals("No Saved Characters"))
		{
			Alert err = new Alert(AlertType.ERROR, "You must select a saved Character to load or create a new Character.");
			err.showAndWait();
		}
		else
		{
			player = game.loadSavedPlayer(savedGameList.getFocusModel().getFocusedItem());
			PlayerMain mainWindow = new PlayerMain();
			mainWindow.show(game, player);
			stage.close();
			
		}
	}
	
	/**
	 * Handles cancel button action event by creating another Welcome window and pasing current stage to it.
	 */
	private void cancelButtonHandler()
	{
		WelcomeWindow goBack = new WelcomeWindow(stage);
		goBack.show(game);
	}
	
	/**
	 * Handles the create new player button event by creating a new Create New Player window and passing 
	 * current state to it.
	 */
	private void createButtonHandler()
	{
		CreateNewPlayer create = new CreateNewPlayer(stage);
		create.show(game);
	}
}
