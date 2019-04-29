/**
 * Program name: Castle Patrol
 * Players can create, save, and load characters to patrol the lands of the Tridentopia Kingdom. Encountering Troll, Ogres, and more, they must
 * defeat the evil menace while surviving the encounters.
 * 
 * Class: CPT 237
 * Date: Feb 19, 2019
 * 
 * @author Jon Koller
 * All images pulled from public domain and are property of their respective owners.
 */

package koller.castlepatrol.main;

import javafx.application.Application;
import javafx.stage.Stage;
import koller.castlepatrol.model.Game;
import koller.castlepatrol.view.WelcomeWindow;

public class Main extends Application
{

	public static void main(String[] args) 
	{
		Application.launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception 
	{
		Game newGame = Game.getInstance();
		newGame.loadGameFiles(5);
		WelcomeWindow begin = new WelcomeWindow(primaryStage);
		
		begin.show(newGame);
	}
}
