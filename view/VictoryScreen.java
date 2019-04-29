package koller.castlepatrol.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import koller.castlepatrol.view.PlayerMain.PlayerMainControl;

/*************************************************************************************************
 * Victory screen if player has successfully defeated enemy.
 * 
 * @author Jon Koller
 *
 *************************************************************************************************/

public class VictoryScreen extends StackPane
{
	private Button continueBtn = new Button("Move to the next patrol area");
	
	private Image img;

	/**
	 * Constructor
	 * 
	 * @param mainControl	Control object that interacts with this pane and PlayerMain window
	 */
	public VictoryScreen(PlayerMainControl mainControl)
	{		
		//	Loads image from file and sets in an image view to display in background
		img = mainControl.getBattleVictoryImage();
		ImageView imgView = new ImageView(img);
		imgView.setPreserveRatio(true);
		imgView.setFitWidth(800);
		imgView.setCache(true);
		
		//	Creates and formats the subtitle text at bottom of window
		Text subTitle = new Text("Your are victorious in battle!");
		subTitle.setFont(Font.font("Forte", FontWeight.BOLD, 30));
		subTitle.setFill(Color.ANTIQUEWHITE);	
		StackPane.setAlignment(subTitle, Pos.BOTTOM_CENTER);
		StackPane.setMargin(subTitle, new Insets(0, 0, 20, 0));
		mainControl.dialogBox(subTitle.getText());
		
		this.setBackground(new Background(new BackgroundFill(Color.BLACK, null, null)));

		//	Formats continue button and aligns it in pane
		continueBtn.setFont(Font.font("Forte", 14));
		StackPane.setAlignment(continueBtn, Pos.BOTTOM_RIGHT);
		StackPane.setMargin(continueBtn, new Insets(0, 15, 55, 0));
		
		this.getChildren().addAll(imgView, subTitle, continueBtn);
		
		//	Continue button event handler
		continueBtn.setOnAction(e -> mainControl.loadTransitionScreen());
	}
}
