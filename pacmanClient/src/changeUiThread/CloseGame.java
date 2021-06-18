package changeUiThread;

import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class CloseGame extends Thread{
	Label label;
	
	public CloseGame(Label label) {
		this.label = label;
	}
	
	@Override
	public void run() {
		super.run();
		Platform.runLater(()->{
			Stage stage = (Stage)label.getScene().getWindow();
			stage.close();
		});
	}
}
