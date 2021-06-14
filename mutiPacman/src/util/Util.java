package util;

import java.io.BufferedReader;
import java.net.Socket;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import network.Client;

public class Util {
	public static Client client;
	public static Socket socket;
	public static String id;
	
	public static BufferedReader br;
	
	public static void sleep(int time) {
		try {
			Thread.sleep(time);
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	public static void alert(String str) {
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle("알림");
		alert.setHeaderText(str);
		alert.setContentText("");

		alert.showAndWait();
	}
}
