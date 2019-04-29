package koller.castlepatrol.view;

import java.util.ArrayList;

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
import javafx.scene.text.Text;
import koller.castlepatrol.view.PlayerMain.PlayerMainControl;

/*************************************************************************************************
 * Transition screen displayed after initially leaving castle on patrol and between each battle.
 * 
 * @author Jon Koller
 *
 *************************************************************************************************/

public class TransitionScreen extends StackPane
{
	private Button continueBtn = new Button("Continue");
	private int textCounter = 0;
	private Image img;
	private PlayerMainControl mainControl;
	private Text subTitle;
	
	private ArrayList<String> transitionDialog = new ArrayList<>();

	/**
	 * Constructor
	 * 
	 * @param mainControl	Control object that interacts with this pane and PlayerMain window
	 */
	public TransitionScreen(PlayerMainControl mainControl)
	{
		transitionDialog = mainControl.getTransitionDialog();
		this.mainControl = mainControl;

		this.setBackground(new Background(new BackgroundFill(Color.BLACK, null, null)));
		this.setPrefSize(800, 600);
		
		//	Loads and sets up image for display in background of bane
		img = mainControl.getTransitionImage();
		ImageView imgView = new ImageView(img);
		imgView.setPreserveRatio(true);
		imgView.setFitWidth(800);
		imgView.setCache(true);
		
		//	Creates and formats subtitle text at bottom of screen 
		subTitle = new Text(getSubTitleText());
		subTitle.setFont(Font.font("Baskerville Old Face", 18));
		subTitle.setFill(Color.ANTIQUEWHITE);
		
		//	Sets alignment of image view and subtitle text
		StackPane.setAlignment(imgView, Pos.CENTER);
		StackPane.setAlignment(subTitle, Pos.BOTTOM_CENTER);
		StackPane.setMargin(subTitle, new Insets(0, 0, 25, 0));

		//	Formats the continue button and aligns it within screen
		continueBtn.setFont(Font.font("Forte", 14));
		StackPane.setAlignment(continueBtn, Pos.BOTTOM_RIGHT);
		StackPane.setMargin(continueBtn, new Insets(0, 20, 55, 0));
		
		this.getChildren().addAll(imgView, subTitle, continueBtn);
		
		//	Continue button action handler
		continueBtn.setOnAction(e -> continueBtnHandler());
	}
	
	/**
	 * Pulls subtitle text for pane based on text counter.
	 * 
	 * @return	Returns string text line
	 */
	private String getSubTitleText()
	{	
		String text = transitionDialog.get(textCounter);
		
		if(textCounter < transitionDialog.size() - 1)
		{
			textCounter++;
		}
		
		return text;
	}
	
	/**
	 * Handles continue button action event, loading new text in the dialog box of main window.
	 */
	private void continueBtnHandler()
	{
		mainControl.loadBattleScreen();
		subTitle.setText(getSubTitleText());
	}
}


