package changeUiThread;

import javafx.application.Platform;
import javafx.scene.control.Label;

public class ChangePlayerLable extends Thread{
	private String name1;
	private String name2;
	private Label label1;
	private Label lable2;
	
	public ChangePlayerLable(String name1, String name2, Label label1, Label lable2) {
		super();
		this.name1 = name1;
		this.name2 = name2;
		this.label1 = label1;
		this.lable2 = lable2;
	}

	@Override
	public void run() {
		super.run();
		Platform.runLater(()->{
			label1.setText(name1);
			label1.setText(name1);
		});
	}
}
