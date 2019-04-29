package koller.castlepatrol.view;

import java.util.ArrayList;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import koller.castlepatrol.view.PlayerMain.PlayerMainControl;

/*************************************************************************************************
 * First screen loaded into PlayerMain after moving from load/create player. Contains the initial
 * dialog the players read through and introduction to NPC that sends them on patrol.
 * 
 * @author Jon Koller
 *
 *************************************************************************************************/

public class InsideCastleScreen extends StackPane
{
	private Button continueBtn = new Button("Continue");
	private Button beginPatrolBtn = new Button("Begin your patrol of the surrounding lands");
	private int introDialogCounter = 0;
	
	private PlayerMainControl mainControl;
	private Image img;
	private ArrayList<String> introDialog;
	
	/**
	 * Constructor
	 * 
	 * @param mainControl	Control object that interacts with this pane and PlayerMain window
	 */
	public InsideCastleScreen(PlayerMainControl mainControl)
	{
		this.mainControl = mainControl;
		introDialog = this.mainControl.getIntroDialog();
		
		//	Loads image from file and fixes it to background instead of using image view since it was already 800x600
		img = this.mainControl.getInsideCastleImage();
		BackgroundSize size = new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, false);
		this.setBackground(new Background(new BackgroundImage(img, BackgroundRepeat.NO_REPEAT, 
				BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, size)));
		this.setPrefSize(800, 600);
		
		//	Formats the continue and begin patrol buttons
		continueBtn.setFont(Font.font("Forte", FontWeight.BOLD, 20));
		StackPane.setAlignment(continueBtn, Pos.BOTTOM_RIGHT);
		StackPane.setMargin(continueBtn, new Insets(0, 30, 30, 0));	
		beginPatrolBtn.setFont(Font.font("Forte", FontWeight.BOLD, 20));
		StackPane.setAlignment(beginPatrolBtn, Pos.BOTTOM_CENTER);
		StackPane.setMargin(beginPatrolBtn, new Insets(0, 0, 30, 0));
		
		this.getChildren().add(continueBtn);
		
		//	Continue and begin patrol button handlers
		continueBtn.setOnAction(e -> continueButtonHandler(introDialogCounter));
		beginPatrolBtn.setOnAction(e -> beginPatrolButtonHandler());
	}
	
	/**
	 * Handles the continue button event scrolling through text until it reaches the point of sending players
	 * out on patrol, removes itself and puts in begin patrol button, then continues with text when players 
	 * return from patrol then picks back up when player return if they have successfully defeated all enemies 
	 * in the area. 
	 * 
	 * @param counter	Class attribute pass in that tracks what point of the dialog players are at.
	 */
	private void continueButtonHandler(int counter)
	{
		if(counter < introDialog.size() - 3)
		{
			mainControl.dialogBox(introDialog.get(counter));
			introDialogCounter++;
		}
		else if(counter == introDialog.size() - 3)
		{
			this.getChildren().remove(continueBtn);
			this.getChildren().add(beginPatrolBtn);
			introDialogCounter++;
		}
		else if(counter >= introDialog.size() - 2 && counter < introDialog.size() + 1)
		{
			mainControl.dialogBox(introDialog.get(counter - 1));
			introDialogCounter++;
		}
		else
		{
			mainControl.loadGameOverScreen();
		}
	}
	
	/**
	 * Handles the begin patrol button event. Loads the transition screen when clicked then removes itself
	 * and reload loads in the continue button in preparation for players returning after successfully 
	 * clearing the patrol points.
	 */
	private void beginPatrolButtonHandler()
	{
		mainControl.loadTransitionScreen();
		this.getChildren().remove(beginPatrolBtn);
		this.getChildren().add(continueBtn);
	}
}
