package util;

import java.io.BufferedReader;
import java.net.Socket;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import network.Client;
import view.ManuController;
import view.RoomController;

public class Util {
	public static Client client;
	public static Socket socket;
	public static String id;
	
	public static BufferedReader br;
	public static int myScore;
	public static int otherScore;
	
	public static boolean[] isEnd = new boolean[2];
	
	public static void sleep(int time) {
		try {
			Thread.sleep(time);
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	public static void setScore(int score) {
		myScore = score;
		client.sendScore();
		RoomController.scoreUpdate();
	}
	
	public static void alert(String str) {
		new Thread(()->{
			Platform.runLater(()->{
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("알림");
				alert.setHeaderText(str);
				alert.setContentText("");

				alert.showAndWait();
			});
		});
	}
}
