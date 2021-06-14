package changeUiThread;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

public class AddChat extends Thread{
	String msg;
	ObservableList<String> chatlist;
	public AddChat(String msg, ObservableList<String> chatlist) {
		this.msg = msg;
		this.chatlist = chatlist;
	}
	
	@Override
	public void run() {
		super.run();
		Platform.runLater(()->{
			chatlist.add(msg);
		});
	}
}
