package koller.castlepatrol.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import koller.castlepatrol.view.PlayerMain.PlayerMainControl;

/*************************************************************************************************
 * Screen displays once players have successfully defeated all enemies in area and returned to 
 * the castle for final dialog. Allows players to exit game or restart.
 * 
 * @author Jon Koller
 *
 *************************************************************************************************/

public class GameOverScreen extends StackPane
{
	private Button restartBtn = new Button("Restart Game");
	private Button quitBtn = new Button("Quit Game");
	
	private Stage stage;
	private Image img;

	/**
	 * Constructor
	 * 
	 * @param mainControl	Control object that interacts with this pane and PlayerMain window
	 * @param stage			Stage passed form main control window. Currently is new stage so new window displayed.
	 */
	public GameOverScreen(PlayerMainControl mainControl, Stage stage)
	{
		this.stage = stage;
		Scene scene = new Scene(this, 800, 600);
		this.stage.setScene(scene);
		this.stage.setTitle("You win!");
		
		//	Loads image from file and places in image view to display in background of pane
		img = mainControl.getGameOverImage();
		ImageView imgView = new ImageView(img);
		imgView.setPreserveRatio(true);
		imgView.setFitWidth(800);
		imgView.setCache(true);
		
		//	Creates and formats subtitle text at bottom of screen, also sends to main control object to load in dialog box
		Text subTitle = new Text("You have won the game!");
		subTitle.setFont(Font.font("Forte", FontWeight.BOLD, 34));
		subTitle.setFill(Color.ANTIQUEWHITE);
		mainControl.dialogBox(subTitle.getText());
		
		//	Sets background to black for image view to be placed over
		this.setBackground(new Background(new BackgroundFill(Color.BLACK, null, null)));
		StackPane.setAlignment(subTitle, Pos.BOTTOM_CENTER);
		StackPane.setMargin(subTitle, new Insets(0, 0, 20, 0));
		
		//	HBox that holds the restart and quit buttons
		HBox buttonBox = new HBox(80);
		buttonBox.setAlignment(Pos.BOTTOM_CENTER);
		buttonBox.setPadding(new Insets(0, 0, 90, 0));
		buttonBox.getChildren().addAll(restartBtn, quitBtn);
		
		//	Formats the restart and quit buttons
		restartBtn.setFont(Font.font("Forte", 24));
		restartBtn.setPrefWidth(180);
		quitBtn.setFont(Font.font("Forte", 24));
		quitBtn.setPrefWidth(180);
		
		this.getChildren().addAll(imgView, subTitle, buttonBox);
		
		//	Restart and quit button handlers
		restartBtn.setOnAction(e -> mainControl.restartGame());
		quitBtn.setOnAction(e -> mainControl.quitGame());
		
		this.stage.show();
	}
}
