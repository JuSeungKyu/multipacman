package network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import util.Util;

public class Client {

	public static String userID;
	public static String roomID;
	static Socket socket;

	private BufferedReader br;
	private static PrintWriter out;
	
	public void start() {
		try {
			socket = new Socket("127.0.0.1", 8899);
			System.out.println("=== Client start ===");
			Util.socket = socket;
			
			ReadMsg rm = new ReadMsg();
			rm.start();
			
			out = new PrintWriter(socket.getOutputStream());
		} catch (UnknownHostException e) {
			System.out.println("서버에 연결할 수 없습니다");
		} catch (IOException e) {
			System.out.println("서버에 연결할 수 없습니다");
			e.printStackTrace();
		}
	}
	
	public void idSetting(String ID) {
		System.out.print("ID : "+ ID);
		userID = ID;
	}
	
	public void MsgSend(String str) {
		out.println("Chatting@@" + userID + "@@" + roomID + "@@" + str);
		out.flush();
	}

	public void move(String str) {
		out.println("Game@@" + userID + "@@" + roomID + "@@" + str);
		out.flush();
	}

	public void roomSetting(String roomID) {
		System.out.println("roomID : " + roomID);
		this.roomID = roomID;

		out.println("Setting@@" + userID + "@@" + roomID);
		out.flush();
		
	}

	public void roomJoin(String roomID) {
		System.out.print("roomID : " + roomID);
		this.roomID = roomID;

		out.println("Join@@" + userID + "@@" + roomID);
		out.flush();
	}

	public void refresh() {
		out.println("Refresh@@" + userID + "@@" + roomID);
		out.flush();
	}
	
	public void gameStart() {
		out.println("Start@@" + userID + "@@" + roomID);
		out.flush();
	}
	
}
