package application;

import java.net.URL;
import java.util.ResourceBundle;

import game.MoveController;
import game.Player;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import util.Util;

public class MainController implements Initializable {
	@FXML
	private AnchorPane rootPane;
	@FXML
	public ImageView player1Image;
	@FXML
	private KeyCode direction;

	private Player player1;
	private MoveController mc;
	private boolean isMoving = false;
	private boolean isOpenMouth = false;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		player1 = new Player(player1Image);
		mc = new MoveController(player1);
		
		rootPane.setOnKeyPressed(new EventHandler<KeyEvent>() {
			public void handle(KeyEvent arg0) {
				keyController(arg0);
			};
		});
	}

	public void keyController(KeyEvent e) {
		KeyCode keyCode = e.getCode();
        if (keyCode.equals(KeyCode.RIGHT) || keyCode.equals(KeyCode.LEFT) || keyCode.equals(KeyCode.DOWN) || keyCode.equals(KeyCode.UP)) {
        	direction = keyCode;
        	if(isMoving == false) {
        		isMoving = true;
        		
        		new Thread(new Runnable() {
        			@Override
        			public void run() {
        				boolean a = true;
        				while (true) {
        					if(a) {
        						a = false;
        					}else {
            					if (direction.equals(KeyCode.RIGHT)) {
            						mc.changeDirection("right");
            					} else if (direction.equals(KeyCode.LEFT)) {
            						mc.changeDirection("left");
            					} else if (direction.equals(KeyCode.UP)) {
            						mc.changeDirection("top");
            					} else if (direction.equals(KeyCode.DOWN)) {
            						mc.changeDirection("bottom");
            					}
        						a = true;
        					}
        					Util.sleep(10);
        				}
        			}
        		}).start();;
       		}
      	}
    }
}
