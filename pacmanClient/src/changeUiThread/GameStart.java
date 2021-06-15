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

public class GameStart extends Thread{
	private Label label;
	
	public GameStart(Label label) {
		System.out.println("게임 시작 시도");
		this.label = label;
	}
	
	@Override
	public void run() {
		super.run();
		
		Platform.runLater(()->{
			Stage newStage = new Stage();
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
		System.out.println("게임 시작 성공");
	}
}
