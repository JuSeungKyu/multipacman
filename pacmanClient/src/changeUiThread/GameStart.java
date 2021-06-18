package changeUiThread;

import java.io.IOException;

import finalPacman.Controller;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import util.Util;

public class GameStart extends Thread{
	private Label label;
	
	public GameStart(Label label) {
		this.label = label;
	}
	
	@Override
	public void run() {
		super.run();
		
		Platform.runLater(()->{
			Stage newStage = new Stage();
	        Util.myScore = 0;
	        Util.otherScore = 0;
	        Util.isEnd[0] = false;
	        Util.isEnd[1] = false;
			try {
		        FXMLLoader loader = new FXMLLoader(getClass().getResource("/finalPacman/pacman.fxml"));
		        Parent root = loader.load();
		        newStage.setTitle("PacMan");
		        Controller controller = loader.getController();
		        root.setOnKeyPressed(controller);
		        double sceneWidth = controller.getBoardWidth() + 20.0;
		        double sceneHeight = controller.getBoardHeight() + 100.0;
		        newStage.setScene(new Scene(root, sceneWidth, sceneHeight));
		        newStage.show();
		        root.requestFocus();
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
	}
}
