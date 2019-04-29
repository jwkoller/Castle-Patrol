package koller.castlepatrol.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
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
import koller.castlepatrol.view.PlayerMain.PlayerMainControl;

/*************************************************************************************************
 * Screen displayed after player has been reduced to zero hit points and their game is over
 * 
 * @author Jon Koller
 *
 *************************************************************************************************/

public class PlayerDeadScreen extends StackPane
{
	private Button restartBtn = new Button("Restart Game");
	private Button quitBtn = new Button("Quit Game");
	
	private Image img;

	/**
	 * Constructor
	 * 
	 * @param mainControl	Control object that interacts with this pane and PlayerMain window
	 */
	public PlayerDeadScreen(PlayerMainControl mainControl)
	{	
		//	Loads and sets image in an image view to display as background of pane
		img = mainControl.getPlayerDeadImage();
		ImageView imgView = new ImageView(img);
		imgView.setPreserveRatio(true);
		imgView.setFitWidth(800);
		imgView.setCache(true);
		
		//	Creates and formats sub title text at bottom of pane
		Text subTitle = new Text("You have been defeated in battle. The shame you must feel...");
		subTitle.setFont(Font.font("Forte", FontWeight.BOLD, 30));
		subTitle.setFill(Color.ANTIQUEWHITE);
		mainControl.dialogBox(subTitle.getText());
		
		//	Sets background to black with image view laid over top of it
		this.setBackground(new Background(new BackgroundFill(Color.BLACK, null, null)));
		StackPane.setAlignment(subTitle, Pos.BOTTOM_CENTER);
		StackPane.setMargin(subTitle, new Insets(0, 0, 20, 0));
		
		//	Creates and formats HBox to hold the restart and quit buttons display in bottom area of pane
		HBox buttonBox = new HBox(80);
		buttonBox.setAlignment(Pos.BOTTOM_CENTER);
		buttonBox.setPadding(new Insets(0, 0, 70, 0));
		buttonBox.getChildren().addAll(restartBtn, quitBtn);
		
		//	Formats the restart and quit buttons
		restartBtn.setFont(Font.font("Forte", 24));
		restartBtn.setPrefWidth(180);
		quitBtn.setFont(Font.font("Forte", 24));
		quitBtn.setPrefWidth(180);
		
		this.getChildren().addAll(imgView, subTitle, buttonBox);
		
		//	Restart and quit button action handlers
		restartBtn.setOnAction(e -> mainControl.restartGame());
		quitBtn.setOnAction(e -> mainControl.quitGame());
	}
}
