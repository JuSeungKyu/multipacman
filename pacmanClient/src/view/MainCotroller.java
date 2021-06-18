package view;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import network.Client;
import util.Util;

public class MainCotroller implements Initializable {

	private Client client;
	private boolean isSetting = false;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
	}

	@FXML
	TextField idInputField;
	@FXML
	Button loginBtn;

	public void login() {
		String id = idInputField.getText().toString();

		if (id.contains("@")) {
			Util.alert("다시 입력해주세요");
			return;
		}

		Util.id = id;

		Stage newStage = new Stage();
		Stage stage = (Stage) loginBtn.getScene().getWindow();
		try {
			Parent menu = FXMLLoader.load(getClass().getResource("Mainmenu.fxml"));
			Scene sc = new Scene(menu);
			newStage.setScene(sc);
			newStage.show();
			stage.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
