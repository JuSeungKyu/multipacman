package network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Observable;

import javax.swing.text.View;

import changeUiThread.CloseGame;
import changeUiThread.ScoreUpdate;
import finalPacman.Controller;
import finalPacman.PacManModel;
import javafx.application.Platform;
import javafx.geometry.Point2D;
import util.Util;
import view.MainCotroller;
import view.ManuController;
import view.RoomController;

public class ReadMsg extends Thread {
	
	Socket socket;
	
	public void run() {
		socket = Util.socket;
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));

			String msg;

			while (true) {
				// 수신된 메시지 저장
				msg = br.readLine();
				
//				System.out.println("서버로 부터 받은 메시지 : "+msg);

				String[] splitString = msg.split("@@");
				
				if(splitString[0].equals("Game")) {
					String location[] = splitString[1].split("!!");
					Controller.changePacmanLocation(new Point2D(Double.parseDouble(location[0]),Double.parseDouble(location[1])), splitString[2]);
				} else if(splitString[0].equals("Dot")) {
					String location[] = splitString[1].split("!!");
					PacManModel.dotSynchronize(Integer.parseInt(location[0]), Integer.parseInt(location[1]));
				} else if(splitString[0].equals("Score")) {
					Util.otherScore = Integer.parseInt(splitString[1]);
					RoomController.scoreUpdate();
				} else if(splitString[0].equals("End")) {
					Util.isEnd[1] = true;
					Controller.closeGame();
				} else if(splitString[0].equals("Level")) {
					PacManModel.levelUpdate();
				} else if(splitString[0].equals("Chatting")) {
					RoomController.addChatting(splitString[1]);
				} else if(splitString[0].equals("Setting")) {
					if(splitString[1] == "false") {
						Util.alert("실패");
					} else {
						view.ManuController.enterRoom();
						Util.sleep(1000);
						view.RoomController.changePlayerLable("player 1 : " + Util.id, "player 2 : ");
					}
				} else if(splitString[0].equals("Join")) {
					if(splitString[1] == "false") {
						Util.alert("실패");
					} else {
						view.ManuController.enterRoom();
						Util.sleep(1000);
						view.RoomController.changePlayerLable("player 1 : " + splitString[1], "player 2 : " + Util.id);
					}
				}  else if(splitString[0].equals("명령어 이름 뭐하지")) {
					if(splitString[1] == "false") {
						Util.alert("실패");
					} else {
						view.RoomController.changePlayerLable("player 1 : "  + Util.id, "player 2 : " + splitString[1]);
					}
				} else if(splitString[0].equals("Refresh")) {
					ArrayList<String> roomList = new ArrayList<String>();
					for(int i = 1; i < splitString.length; i++) {
						roomList.add(splitString[i]);
					}
					view.ManuController.setList(roomList);
				} else if(splitString[0].equals("Start")) {
					view.RoomController.enterGame();
					Util.sleep(1000);
				}
			}

		} catch (IOException e) {
			
		}
	}
}

