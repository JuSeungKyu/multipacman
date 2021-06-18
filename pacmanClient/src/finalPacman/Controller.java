/**
 * @author Jessie Baskauf and Ellie Mamantov
 * The Controller handles user input and coordinates the updating of the model and the view with the help of a timer.
 */

package finalPacman;

import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.stage.Window;
import network.Client;
import util.Util;
import javafx.application.Platform;
import java.util.Timer;
import java.util.TimerTask;

import finalPacman.PacManModel.Direction;

public class Controller implements EventHandler<KeyEvent> {
	final private static double FRAMES_PER_SECOND = 5.0;

	@FXML
	private Label scoreLabel;
	private static Label staticScoreLabel;
	
	@FXML
	private Label levelLabel;
	@FXML
	private Label gameOverLabel;
	@FXML
	private PacManView pacManView;
	private static PacManModel pacManModel;
	private static final String[] levelFiles = { "src/levels/level1.txt", "src/levels/level2.txt",
			"src/levels/level3.txt" };

	private Timer timer;
	private static TimerTask timerTask;
	private static int ghostEatingModeCounter;
	public static boolean paused;

	public Controller() {
		this.paused = false;
	}

	/**
	 * Initialize and update the model and view from the first txt file and starts
	 * the timer.
	 */
	public void initialize() {
		staticScoreLabel = scoreLabel;
		String file = this.getLevelFile(0);
		this.pacManModel = new PacManModel();
		this.update(PacManModel.Direction.NONE);
		ghostEatingModeCounter = 25;
		this.startTimer();
	}

	/**
	 * Schedules the model to update based on the timer.
	 */
	private void startTimer() {
		this.timer = new java.util.Timer();
		timerTask = new TimerTask() {
			public void run() {
				Platform.runLater(new Runnable() {
					public void run() {
						update(pacManModel.getCurrentDirection());
					}
				});
			}
		};

		long frameTimeInMilliseconds = (long) (1000.0 / FRAMES_PER_SECOND);
		this.timer.schedule(timerTask, 0, frameTimeInMilliseconds);
	}

	/**
	 * Steps the PacManModel, updates the view, updates score and level, displays
	 * Game Over/You Won, and instructions of how to play
	 * 
	 * @param direction the most recently inputted direction for PacMan to move in
	 */
	private void update(PacManModel.Direction direction) {
		
		this.pacManModel.step(direction);
		
		this.pacManView.update(pacManModel);

		this.scoreLabel.setText(String.format("Score: %d", this.pacManModel.getScore()));
		this.levelLabel.setText(String.format("Level: %d", this.pacManModel.getLevel()));
		
		if (pacManModel.isGameOver()) {
			this.gameOverLabel.setText(String.format("GAME OVER"));
			pause();
			Util.client.end();
			Util.isEnd[0] = true;
			closeGame();
		} else {
			this.gameOverLabel.setText(String.format(""));
		}
		
		if (pacManModel.isYouWon()) {
			this.gameOverLabel.setText(Util.myScore > Util.otherScore ? String.format("YOU WON!")
					: Util.myScore == Util.otherScore ? String.format("DREW") : String.format("YOU'RE A LOSER"));
		}
		
		if (pacManModel.isGhostEatingMode()) {
			ghostEatingModeCounter--;
		}
		
		if (ghostEatingModeCounter == 0 && pacManModel.isGhostEatingMode()) {
			pacManModel.setGhostEatingMode(false);
		}
	}

	/**
	 * Takes in user keyboard input to control the movement of PacMan and start new
	 * games
	 * 
	 * @param keyEvent user's key click
	 */
	@Override
	public void handle(KeyEvent keyEvent) {
		boolean keyRecognized = true;
		KeyCode code = keyEvent.getCode();
		PacManModel.Direction direction = PacManModel.Direction.NONE;
		if(paused) {
			direction = PacManModel.Direction.NONE;
		} else if (code == KeyCode.LEFT) {
			direction = PacManModel.Direction.LEFT;
		} else if (code == KeyCode.RIGHT) {
			direction = PacManModel.Direction.RIGHT;
		} else if (code == KeyCode.UP) {
			direction = PacManModel.Direction.UP;
		} else if (code == KeyCode.DOWN) {
			direction = PacManModel.Direction.DOWN;
		} else if (code == KeyCode.G) {
			pause();
			this.pacManModel.startNewGame();
			this.gameOverLabel.setText(String.format(""));
			paused = false;
			this.startTimer();
		} else {
			keyRecognized = false;
		}
		if (keyRecognized) {
			keyEvent.consume();
			pacManModel.setCurrentDirection(direction);
		}
	}

	/**
	 * Pause the timer
	 */
	public void pause() {
		paused = true;
	}

	public double getBoardWidth() {
		return PacManView.CELL_WIDTH * this.pacManView.getColumnCount();
	}

	public double getBoardHeight() {
		return PacManView.CELL_WIDTH * this.pacManView.getRowCount();
	}

	public static void setGhostEatingModeCounter() {
		ghostEatingModeCounter = 25;
	}

	public static int getGhostEatingModeCounter() {
		return ghostEatingModeCounter;
	}

	public static String getLevelFile(int x) {
		return levelFiles[x];
	}

	public boolean getPaused() {
		return paused;
	}

	public static void changePacmanLocation(Point2D point, String str) {
		try {
			new Thread(() -> {
				Platform.runLater(() -> {
					PacManModel.setLocation2(point);
					PacManModel.setLastDirection2(
							str.equals("UP") ? Direction.UP
							: str.equals("DOWN") ? Direction.DOWN
							: str.equals("LEFT") ? Direction.LEFT
							: str.equals("RIGHT") ? Direction.RIGHT
							: Direction.NONE);
				});
			}).start();
		} catch (Exception e) {
		
		}
	}
	
	public static void closeGame() {
		if(Util.isEnd[0] && Util.isEnd[1]) {
			new changeUiThread.CloseGame(staticScoreLabel).start();
			timerTask.cancel();
		}
	}
}