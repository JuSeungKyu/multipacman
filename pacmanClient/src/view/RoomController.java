package view;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import changeUiThread.AddChat;
import changeUiThread.ChangePlayerLable;
import changeUiThread.GameStart;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import util.Util;

public class RoomController implements Initializable {

	@FXML
	ListView<String> chattingView;
	private static ObservableList<String> itemlist;
	
	@FXML
	TextField chattingField;
	
	@FXML
	Button chattingbtn;
	@FXML
	Button roombtn2;
	@FXML
	Button startBtn;
	
	@FXML
	Label player1Lable;
	static Label staticPlayer1Lable;
	@FXML
	Label player2Lable;
	static Label staticPlayer2Lable;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		this.staticPlayer1Lable = player1Lable;
		this.staticPlayer2Lable = player2Lable;
		
		System.out.println(staticPlayer1Lable + " 라벨 테스트");
		
		itemlist = FXCollections.observableArrayList();
		chattingView.setItems(itemlist);
	}
	
	public void gamestart() {
		Util.client.gameStart();
	}
	
	public void sendChat() {
		Util.client.MsgSend(chattingField.getText());
	}
	
	public static void enterGame() {
		new GameStart(staticPlayer2Lable).start();;
	}
	
	public static void changePlayerLable(String name1, String name2) {
		new ChangePlayerLable(name1, name2, staticPlayer1Lable, staticPlayer2Lable).start();
	}
	
	public static void addChatting(String msg) {
		new AddChat(msg, itemlist).start();
	}
}
