package changeUiThread;

import javafx.application.Platform;
import javafx.scene.control.Label;
import util.Util;

public class ScoreUpdate extends Thread{
	private Label label;
	
	public ScoreUpdate(Label label) {
		this.label = label;
	}

	@Override
	public void run() {
		super.run();
		Platform.runLater(()->{
			label.setText(Util.myScore+":"+Util.otherScore);
		});
	}
}

