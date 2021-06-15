package network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

public class ClientManager extends Thread {

	private Socket socket;
	private int userNum;
	private String userID;
	private PrintWriter myPw;

	@Override
	public void run() {
		super.run();
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));

			String msg = null;
			myPw.println("접속되었습니다");
			myPw.flush();
			System.out.println(myPw.toString());

			while (true) {
				// 클라이언트로 받은 메시지
				msg = br.readLine();

				// 클라이언트가 나갈 경우
				if (msg == null) {
					break;
				}

//				System.out.println("받은 명령어 : " + msg);

				String[] splitString = msg.split("@@");

				if (splitString[0].equals("Game")) {
					for (int i = 0; i < Sever.room.size(); i++) {
						if (Sever.room.get(i).getRoomID().equals(splitString[2])) {
							try {
								PrintWriter p = (Sever.room.get(i).userNames[0].equals(splitString[1]) ? Sever.room.get(i).userPw[1] :  Sever.room.get(i).userPw[0]);
								p.println("Game@@" + splitString[3]+ "@@"+ splitString[4]);
								p.flush();
							} catch (Exception e) {
								
							}
							break;
						}
					}
				} else if (splitString[0].equals("Chatting")) {
					for (int i = 0; i < Sever.room.size(); i++) {
						if (Sever.room.get(i).getRoomID().equals(splitString[2])) {
							try {
								Sever.room.get(i).userPw[0]
										.println("Chatting@@" + splitString[1] + " : " + splitString[3]);
								Sever.room.get(i).userPw[0].flush();
								Sever.room.get(i).userPw[1]
										.println("Chatting@@" + splitString[1] + " : " + splitString[3]);
								Sever.room.get(i).userPw[1].flush();
							} catch (Exception e) {
								// TODO: handle exception
							}
							break;
						}
					}
				} else if (splitString[0].equals("Setting")) {
					boolean a = true;

					System.out.println("방 만들기(서버) : 1");
					for (int i = 0; i < Sever.room.size(); i++) {
						if (Sever.room.get(i).getRoomID().equals(splitString[2])) {
							a = false;
							break;
						}
					}
					System.out.println("방 만들기(서버) : 2");

					System.out.println(a);
					if (!a) {
						System.out.println("방 만들기 실패 - 메시지전송");
						myPw.println("Setting@@false");
						myPw.flush();
					} else {
						userID = splitString[1];
						Room room = new Room(splitString[2], myPw, userID);
						Sever.room.add(room);
						myPw.println("Setting@@true");
						myPw.flush();
						System.out.println("방 만들기 성공 - 메시지전송");
					}
				} else if (splitString[0].equals("Join")) {
					int point = -1;
					for (int i = 0; i < Sever.room.size(); i++) {
						if (Sever.room.get(i).getRoomID().equals(splitString[2])) {
							point = i;
							break;
						}
					}
					if (point == -1 || Sever.room.get(point).getUserNumber() > 1) {
						myPw.println("Join@@false");
						myPw.flush();
					} else {
						Sever.room.get(point).setUserNumber(2);

						userID = splitString[1];
						Sever.room.get(point).userNames[1] = userID;
						Sever.room.get(point).userPw[1] = myPw;
						System.out.println(myPw + " " + Sever.room.get(point).userPw[1]);
						System.out.println(userID + " " + Sever.room.get(point).userNames[1]);

						Sever.room.get(point).userPw[1].println("Join@@" + Sever.room.get(point).userNames[0]);
						Sever.room.get(point).userPw[0].println("명령어 이름 뭐하지@@" + Sever.room.get(point).userNames[1]);
						Sever.room.get(point).userPw[1].flush();
						Sever.room.get(point).userPw[0].flush();
					}
				} else if (splitString[0].equals("Refresh")) {
					System.out.println("refresh시도");
					String roomList = "Refresh";

					for (int i = 0; i < Sever.room.size(); i++) {
						roomList += "@@" + Sever.room.get(i).getRoomID();
					}

					System.out.println(roomList);
					myPw.println(roomList);
					myPw.flush();
				}  else if (splitString[0].equals("Start")) {
					System.out.println("start 시도");
					
					for (int i = 0; i < Sever.room.size(); i++) {
						if (Sever.room.get(i).getRoomID().equals(splitString[2])) {
							try {
								Sever.room.get(i).userPw[0]
										.println("Start@@" + "true");
								Sever.room.get(i).userPw[0].flush();
								
								Sever.room.get(i).userPw[1]
										.println("Start@@" + "true");
								Sever.room.get(i).userPw[1].flush();
							} catch (Exception e) {
								// TODO: handle exception
							}
							break;
						}
					}
				} else {
					Sever.clientList.remove(new PrintWriter(socket.getOutputStream()));
					socket.close();
					break;
				}
			}

		} catch (

		IOException e) {
			e.printStackTrace();
		}
	}

	public void setNum(int num) {
		this.userNum = num;
	}

	public void setSocket(Socket _socket) {
		socket = _socket;
	}

	public void setPw(PrintWriter pw) {
		this.myPw = pw;
	}

	public PrintWriter getPw() {
		return myPw;
	}
}
