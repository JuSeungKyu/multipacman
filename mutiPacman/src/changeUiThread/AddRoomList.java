package changeUiThread;

import java.util.ArrayList;

import javafx.application.Platform;
import javafx.collections.ObservableList;

public class AddRoomList extends Thread{
	private ObservableList<String> itemlist;
	private ArrayList<String> roomNames;
	public AddRoomList(ObservableList<String> itemlist, ArrayList<String> roomNames) {
		this.itemlist = itemlist;
		this.roomNames = roomNames;
	}
	
	@Override
	public void run() {
		super.run();
		Platform.runLater(()->{
			for(int i = 0; i < roomNames.size(); i++) {
				itemlist.add(roomNames.get(i));
			}
		});
	}
}
