package changeUiThread;

import java.io.IOException;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class EnterRoom extends Thread{
	private TextField roomName;
	
	public EnterRoom(TextField roomName) {
		this.roomName = roomName;
	}
	
	@Override
	public void run() {
		super.run();
		System.out.println("방 입장 시도");
		
		Platform.runLater(()->{
			Stage newStage = new Stage();
			Stage stage = (Stage) roomName.getScene().getWindow();
			try {
				Parent menu = FXMLLoader.load(getClass().getResource("/view/Room.fxml"));
				Scene sc = new Scene(menu);
				newStage.setScene(sc);
				newStage.show();
				stage.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
		System.out.println("방 입장 성공");
	}
}
