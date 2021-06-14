package network;

import java.io.Console;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import network.ClientManager;

public class Sever {
	
	public static ArrayList<PrintWriter> clientList;
	public static ArrayList<Room> room;

	public static void main(String[] args) {

		clientList = new ArrayList<PrintWriter>();
		room = new ArrayList<Room>();
		try {
			// 서버 소켓 설정
			ServerSocket socket = new ServerSocket(8899);
			System.out.println("=== Server start ===");

			while (true) {
				// 클라이언트 받음
				Socket client = socket.accept();
				
				System.out.println("유저 입장");
				// 클라이언트매니저에 소켓 저장
				ClientManager clientManager = new ClientManager();
				clientManager.setSocket(client);
				clientManager.setNum(clientList.size());
				clientManager.setPw(new PrintWriter(client.getOutputStream()));

				// 클라이언트를 리스트에 저장
				clientList.add(clientManager.getPw());

				// 클라이언트매니저 시작
				clientManager.start();
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
