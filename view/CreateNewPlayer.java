package koller.castlepatrol.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import koller.castlepatrol.model.*;

/*************************************************************************************************
 * Window to allow user to create a new player character by choosing stats, entering a name
 * and choosing to make either a fighter or a wizard.
 * 
 * @author Jon Koller
 *
 *************************************************************************************************/

public class CreateNewPlayer 
{
	private final String IMG_FILE = "file:images//castle2.jpg";
	private final int MAX_STAT = 20;
	private final int MIN_STAT = 8;
	private Button[] windowButtons = new Button[2];
	private Button[] pointBtns = new Button[6];
	private RadioButton fighterRadio = new RadioButton();
	private RadioButton wizardRadio = new RadioButton();
	private ToggleGroup toggle = new ToggleGroup();
	private Text[] pointFields = new Text[3];
	private Text[] statDescriptions = new Text[5];
	private Text availPts = new Text();
	private TextField nameInput = new TextField();
	private int points = 14;
	
	private Game game;
	private Player player;
	private Stage stage;
	private Image backgroundImg;
	
	/**
	 * Constructor
	 * 
	 * @param passedStage	Stage passed from previous window
	 */
	public CreateNewPlayer(Stage passedStage)
	{
		stage = passedStage;
		BorderPane pane = new BorderPane();
		Scene scene = new Scene(pane, 800, 600);
		stage.setScene(scene);
		stage.setTitle("Create New Player");
		pane.setPadding(new Insets(10));
		
		HBox hBoxBottom = new HBox(100);
		HBox hBoxTop = new HBox(10);
		HBox hBoxCenter = new HBox(12);
		VBox[] centerBoxes = new VBox[3];
		VBox vBoxTop = new VBox();
		VBox vBoxLeft = new VBox(20);
		VBox vBoxCenter = new VBox(22);
		VBox vBoxRight = new VBox(22);

		//	Loads image from file and sets as background
		backgroundImg = getImage(IMG_FILE);
		BackgroundSize size = new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, false);
		pane.setBackground(new Background(new BackgroundImage(backgroundImg, BackgroundRepeat.NO_REPEAT, 
				BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, size)));
		
		//	Creates and formats everything for top of window including title, radio buttons, and radio button lables
		Text title = new Text("Create Your Player");
		title.setFont(Font.font("Castellar", FontWeight.BOLD, 60));
		title.setFill(Color.BLACK);
		fighterRadio.setToggleGroup(toggle);
		wizardRadio.setToggleGroup(toggle);
		toggle.selectToggle(fighterRadio);
		HBox.setMargin(fighterRadio, new Insets(0, 80, 0, 0));
		Label[] labelsTop = new Label[2];
		labelsTop[1] = new Label("Wizard");
		labelsTop[0] = new Label("Fighter");
		hBoxTop.setAlignment(Pos.CENTER);
		hBoxTop.setPadding(new Insets(80, 0, 0, 0));
		hBoxTop.getChildren().addAll(labelsTop[0], fighterRadio, labelsTop[1], wizardRadio);
		vBoxTop.setAlignment(Pos.CENTER);
		vBoxTop.getChildren().addAll(title, hBoxTop);
		for(Label l : labelsTop)
		{
			l.setFont(Font.font("Castellar", 30));
			l.setTextFill(Color.rgb(219, 95, 32));
		}

		//	Creates and formats player stat labels for left side of window
		Label[] labelsLeft = new Label[5];
		labelsLeft[0] = new Label("Available Points: ");
		labelsLeft[1] = new Label("Name: ");
		labelsLeft[2] = new Label("Strength: ");
		labelsLeft[3] = new Label("Dexterity: ");
		labelsLeft[4] = new Label("Intelligence: ");
		vBoxLeft.setPadding(new Insets(30, 0, 0, 0));
		vBoxLeft.setAlignment(Pos.BASELINE_RIGHT);
		vBoxLeft.setPrefWidth(250);
		for(Label l : labelsLeft)
		{
			l.setFont(Font.font("Castellar", FontWeight.BOLD, 20));
			l.setTextFill(Color.ANTIQUEWHITE);
			vBoxLeft.getChildren().add(l);
			VBox.setMargin(l, new Insets(0, 10, 0, 0));
		}

		//	Creates and formats everything for center of window including the stat increase and decrease buttons, labels
		//	and player name text box
		vBoxCenter.setPadding(new Insets(40, 0, 0, 0));
		for(int i = 0; i < centerBoxes.length; i++)
		{
			centerBoxes[i] = new VBox(22);
			pointFields[i] = new Text(String.valueOf(MIN_STAT));
			pointFields[i].setFont(Font.font("Castellar", FontWeight.BOLD, 20));
			pointFields[i].setFill(Color.ANTIQUEWHITE);
			centerBoxes[0].getChildren().add(pointFields[i]);
		}
		centerBoxes[0].setMinWidth(30);
		for(int i = 0; i < pointBtns.length - 1; i++)
		{	
			pointBtns[i] = new Button("+");
			pointBtns[i].setFont(Font.font("", FontWeight.BOLD, 12));
			pointBtns[i].setMinWidth(40);
			pointBtns[i + 1] = new Button("-");
			pointBtns[i + 1].setFont(Font.font("", FontWeight.BOLD, 12));
			pointBtns[i + 1].setMinWidth(40);

			centerBoxes[1].getChildren().add(pointBtns[i]);
			centerBoxes[2].getChildren().add(pointBtns[i + 1]);
			i++;
		}
		hBoxCenter.getChildren().addAll(centerBoxes[0], centerBoxes[1], centerBoxes[2]);
		nameInput.setMaxWidth(135);
		availPts.setText(String.valueOf(points));
		availPts.setFont(Font.font("Castellar", FontWeight.BOLD, 20));
		availPts.setFill(Color.ANTIQUEWHITE);
		vBoxCenter.setMaxWidth(150);
		vBoxCenter.getChildren().addAll(availPts, nameInput, hBoxCenter);
		
		
		//	Creates and formats stat text descriptions for right side of window
		vBoxRight.setPadding(new Insets(40, 0, 0, 0));
		for(int i = 0; i < statDescriptions.length; i++)
		{
			statDescriptions[i] = new Text();
			statDescriptions[i].setText(getStatDescriptions(i));
			statDescriptions[i].setFont(Font.font("Castellar"));
			statDescriptions[i].setFill(Color.ANTIQUEWHITE);
			statDescriptions[i].setWrappingWidth(300);
			vBoxRight.getChildren().add(statDescriptions[i]);
		}

		//	Creates and formats cancel and create player buttons at bottom of window
		windowButtons[0] = new Button("Cancel");
		windowButtons[1] = new Button("Create Player");
		for(Button b : windowButtons)
		{
			b.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, null, null)));
			b.setTextFill(Color.ANTIQUEWHITE);
			b.setFont(Font.font("Castellar", FontWeight.BOLD, 16));
			b.setMinSize(250, 50);
			b.setOnMouseEntered(e -> b.setTextFill(Color.DARKGRAY));
			b.setOnMouseExited(e -> b.setTextFill(Color.ANTIQUEWHITE));
		}
		hBoxBottom.setAlignment(Pos.CENTER);
		BorderPane.setMargin(hBoxBottom, new Insets(25, 0, 25, 0));
		hBoxBottom.getChildren().addAll(windowButtons[0], windowButtons[1]);
		
		//	Sets finished panes into appropriate areas of window
		pane.setBottom(hBoxBottom);
		pane.setTop(vBoxTop);
		pane.setLeft(vBoxLeft);
		pane.setRight(vBoxRight);
		pane.setCenter(vBoxCenter);
		
		//	Event handlers for buttons at bottom of window
		windowButtons[0].setOnAction(e -> cancelButtonHandler());
		windowButtons[1].setOnAction(e -> createButtonHandler());
		
		//	Event handlers for stat increase and decrease buttons
		pointBtns[0].setOnAction(e -> changeStatPoints(0, 0));
		pointBtns[1].setOnAction(e -> changeStatPoints(0, 1));
		pointBtns[2].setOnAction(e -> changeStatPoints(1, 2));
		pointBtns[3].setOnAction(e -> changeStatPoints(1, 3));
		pointBtns[4].setOnAction(e -> changeStatPoints(2, 4));
		pointBtns[5].setOnAction(e -> changeStatPoints(2, 5));
	}
	
	/**
	 * Handles the create player button. Checks to make sure all points are spend and name field is not
	 * empty. Takes value of stat and name fields and passes to Game object which returns a new player.
	 * Creates a new PlayerMain window to continue game and passes newly created player and Game
	 * object to new window.
	 */
	private void createButtonHandler()
	{
		
		int str = Integer.parseInt(pointFields[0].getText());
		int dex = Integer.parseInt(pointFields[1].getText());
		int intel = Integer.parseInt(pointFields[2].getText());
		
		if(points > 0)
		{
			Alert err = new Alert(AlertType.WARNING, "You still have stat points available to spend.");
			err.showAndWait();
		}
		else
		{
			if(nameInput.getText().isEmpty())
			{
				Alert err = new Alert(AlertType.WARNING, "You must name your Character.");
				err.showAndWait();
			}
			else
			{
				String name = nameInput.getText();
				if(fighterRadio.isSelected())
				{
					player = game.createPlayer("Fighter", name, str, dex, intel);
				}
				else
				{
					player = game.createPlayer("Wizard", name, str, dex, intel);
				}
				
				PlayerMain mainWindow = new PlayerMain();
				mainWindow.show(game, player);
				stage.close();
			}
		}
	}
	
	/**
	 * Handles the player stat increase and decrease buttons by checking against the max and min allowed stat
	 * and subtracting or adding to available points as necessary.
	 * 
	 * @param stat		Index of the pointField array user is adjusting
	 * @param button	Index of the button array player is using. Plus points are odd, minus points are even
	 */
	private void changeStatPoints(int stat, int button)
	{
		if(button % 2 == 0)
		{
			if(points > 0)
			{
				if(Integer.parseInt(pointFields[stat].getText()) < MAX_STAT)
				{
					pointFields[stat].setText(String.valueOf(Integer.parseInt(pointFields[stat].getText()) + 1));
					points--;
					availPts.setText(String.valueOf(points));
				}
			}
		}
		else
		{
			if(Integer.parseInt(pointFields[stat].getText()) > MIN_STAT)
			{
				pointFields[stat].setText(String.valueOf(Integer.parseInt(pointFields[stat].getText()) - 1));
				points++;
				availPts.setText(String.valueOf(points));
			}
		}
	}
	
	/**
	 * Displays window and sets local instance of Game class to passed Game object.
	 * 
	 * @param game	Game object
	 */
	public void show(Game game)
	{
		this.game = game;
		stage.show();
	}
	
	/**
	 * Descriptions of purpose of stat points. Passes a string back to for loop in constructor
	 * for the various labels. Just to cut down on lines in constructor.
	 * 
	 * @param num	Index of for loop passed to know which stat description to pass back to label
	 * 
	 * @return 		Returns string label description to set text.
	 */
	private String getStatDescriptions(int num)
	{
		String string = new String();; 
		
		if(num == 0)
		{
			string = "The amount of points remaining to increase your stats.";
		}
		else if(num == 1)
		{
			string = "The name of your Character.";
		}
		else if(num == 2)
		{
			string = "Strength affects hit points and damage. Important for Fighters.";
		}
		else if(num == 3)
		{
			string = "Dexterity affects your defenses and your initiative. Important for both.";
		}
		else if(num == 4)
		{
			string = "Intelligence affects your initiative, hit points, and spell points. Important for Wizards.";
		}
		return string;
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
	 * Handles the cancel button event. Creates and shows a new welcome window and passes current stage to it.
	 */
	private void cancelButtonHandler()
	{
		WelcomeWindow goBack = new WelcomeWindow(stage);
		goBack.show(game);
	}
}
