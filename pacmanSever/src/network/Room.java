package network;

import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

public class Room {
	
	private String roomID;
	private int userNumber;
	
	public PrintWriter[] userPw = {null, null};
	public String[] userNames = new String[2];
	
	public Room(String roomID, PrintWriter pw, String name) {
		this.roomID = roomID;
		this.userNumber = 1;
		this.userPw[0] = pw;
		this.userNames[0] = name;
	}

	public String getRoomID() {
		return roomID;
	}
	
	public int getUserNumber() {
		return userNumber;
	}

	public void setUserNumber(int userNumber) {
		this.userNumber = userNumber;
	}
	
}
