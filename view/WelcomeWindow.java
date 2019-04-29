package koller.castlepatrol.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import koller.castlepatrol.model.*;

/*************************************************************************************************
 * Initial opening window when game is loaded. 
 * 
 * @author Jon Koller
 *
 *************************************************************************************************/
public class WelcomeWindow 
{
	private final String IMG_FILE = "file:images//castle2.jpg";
	private Button buttons[] = new Button[2];
	
	private Game game;
	private Stage stage;
	private Image backgroundImg;
	
	/**
	 * Constructor
	 * 
	 * @param primaryStage	Primary stage passed from Main class
	 */
	public WelcomeWindow(Stage primaryStage)
	{
		stage = primaryStage;
		StackPane pane = new StackPane();
		Scene scene = new Scene(pane, 800, 600);
		stage.setScene(scene);
		stage.setTitle("Castle Patrol");
		
		//	Creates and formats the create new player and load saved player buttons
		buttons[0] = new Button("Create new Player");
		buttons[1] = new Button("Load Saved Player");
		for(Button b : buttons)
		{
			b.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, null, null)));
			b.setTextFill(Color.ANTIQUEWHITE);
			b.setFont(Font.font("Castellar", FontWeight.BOLD, 16));
			b.setPrefSize(250, 50);
			b.setOnMouseEntered(e -> b.setTextFill(Color.DARKGRAY));
			b.setOnMouseExited(e -> b.setTextFill(Color.ANTIQUEWHITE));
		}
		
		//	Loads and sets background image
		backgroundImg = getImage(IMG_FILE);
		BackgroundSize size = new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, false);
		pane.setBackground(new Background(new BackgroundImage(backgroundImg, BackgroundRepeat.NO_REPEAT, 
				BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, size)));
		
		//	Creates and formats title text
		Label title = new Label("Castle Patrol");
		title.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, null, null)));
		title.setTextFill(Color.ANTIQUEWHITE);
		title.setFont(Font.font("Castellar", FontWeight.BOLD, 65));
		
		//	Sets margins for title text and buttons
		StackPane.setMargin(title, new Insets(250, 0, 0, 0));
		StackPane.setMargin(buttons[0], new Insets(0, 0, 50, 50));
		StackPane.setMargin(buttons[1], new Insets(0, 50, 50, 0));
		
		//	Add title and buttons to pane, then sets alignment for each
		pane.getChildren().addAll(title, buttons[0], buttons[1]);
		StackPane.setAlignment(title, Pos.TOP_CENTER);
		StackPane.setAlignment(buttons[0], Pos.BOTTOM_LEFT);
		StackPane.setAlignment(buttons[1], Pos.BOTTOM_RIGHT);
		
		//	Button handlers
		buttons[1].setOnAction(e -> loadSavedPlayer());
		buttons[0].setOnAction(e -> createNewPlayer());
	}
	
	/**
	 * Shows window sets current game object from one passed in main
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
	public Image getImage(String fileName)
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
	 * Handles the load saved player button action event by creating a new load saved player
	 * window.
	 */
	public void loadSavedPlayer()
	{
		LoadSavedPlayer load = new LoadSavedPlayer(stage);
		load.show(game);
	}
	
	/**
	 * Handles the create new player button action event by creating a new create new player
	 * window.
	 */
	public void createNewPlayer()
	{
		CreateNewPlayer newPlayer = new CreateNewPlayer(stage);
		newPlayer.show(game);
	}
}
