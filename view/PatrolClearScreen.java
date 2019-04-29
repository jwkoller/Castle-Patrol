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
 * Screen displayed in PlayerMain window after all enemies from array list in Game class have
 * been defeated. 
 * 
 * @author Jon Koller
 *
 *************************************************************************************************/

public class PatrolClearScreen extends StackPane
{
	private Button returnToCastleBtn = new Button("Return to the Castle");
	private Image img;
	
	/**
	 * Constructor
	 * 
	 * @param mainControl	Control object that interacts with this pane and PlayerMain window
	 */
	public PatrolClearScreen(PlayerMainControl mainControl)
	{		
		//	Loads image from file and sets up for display as background of pane
		img = mainControl.getPatrolClearImage();
		ImageView imgView = new ImageView(img);
		imgView.setPreserveRatio(true);
		imgView.setFitWidth(800);
		imgView.setCache(true);
		
		//	Creates and formats subtitle test at bottom of screen
		Text subTitle = new Text("The last patrol point is clear. Your work here is done! Time to head back to the Castle.");
		subTitle.setFont(Font.font("Forte", FontWeight.BOLD, 20));
		subTitle.setFill(Color.ANTIQUEWHITE);
		mainControl.dialogBox(subTitle.getText());

		//	Sets background color to black and aligns subtitle text
		this.setBackground(new Background(new BackgroundFill(Color.BLACK, null, null)));
		StackPane.setAlignment(subTitle, Pos.BOTTOM_CENTER);
		StackPane.setMargin(subTitle, new Insets(0, 0, 20, 0));
		
		//	Formats return to castle button
		returnToCastleBtn.setFont(Font.font("Forte", 14));
		StackPane.setAlignment(returnToCastleBtn, Pos.BOTTOM_RIGHT);
		StackPane.setMargin(returnToCastleBtn, new Insets(0, 20, 60, 0));
		
		this.getChildren().addAll(imgView, subTitle, returnToCastleBtn);
		
		//	Return to castle button handler
		returnToCastleBtn.setOnAction(e -> mainControl.loadInsideCastleScreen());
	}
}
