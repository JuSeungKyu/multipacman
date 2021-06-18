package view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import changeUiThread.AddRoomList;
import changeUiThread.EnterRoom;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import network.Client;
import util.Util;

public class ManuController implements Initializable {

	private Client client;
	private static ObservableList<String> itemlist;
	@FXML
	private ListView<String> roomList;

	private static TextField _roomName;
	
	@FXML
	private TextField roomName;
	@FXML
	private TextField joinRoomName;

	public void initialize(URL location, ResourceBundle resources) {
		_roomName = roomName;
		
		itemlist = FXCollections.observableArrayList();
		roomList.setItems(itemlist);

		client = new Client();
		client.idSetting(Util.id);
		client.start();

		Util.client = client;
		
		listRefresh();
	}

	public void createRoom() {
		if (roomName.getText().isEmpty()) {
		}
		String name = roomName.getText();

		if (name.contains("@")) {
			Util.alert("@는 입력할 수 없습니다");
			return;
		}

		client.roomSetting(name);
	}

	public void joinRoom() {
		if (joinRoomName.getText().isEmpty()) {
			Util.alert("방 이름을 입력해주세요");
		}
		String name = joinRoomName.getText();

		if (name.contains("@")) {
			Util.alert("@는 입력할 수 없습니다");
			return;
		}

		client.roomJoin(name);
	}

	public void listRefresh() {
		client.refresh();
	}

	public static void setList(ArrayList<String> roomNames) {
		itemlist.clear();
		new AddRoomList(itemlist, roomNames).start();
	}

	public static void enterRoom() {
		new EnterRoom(_roomName).start();
	}
}
