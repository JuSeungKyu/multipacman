package game;

import application.MainController;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import util.Util;
import application.MainController;

public class MoveController {
	private int[][] map = new int[30][30];

	final private static Image pac1 = new Image("img/pacman-1.png");
	final private static Image pac2a = new Image("img/pacman-2-1.png");
	final private static Image pac2b = new Image("img/pacman-2-2.png");
	final private static Image pac2c = new Image("img/pacman-2-3.png");
	final private static Image pac2d = new Image("img/pacman-2-4.png");

	private Player player1 = null;

	public MoveController(Player player1) {
		this.player1 = player1;
		mapLoading();
		changePlayerImage();
		changePlayerLocation();
	}

	public void changeDirection(String direction) {
		if (direction == player1.getDirection()) {
			return;
		} else {
			if (direction == "top") {
				if (map[player1.getLocation()[0]][player1.getLocation()[1] + 1] == 0) {
					player1.setDirection("top");
				} else {
					changeNextDirection(direction);
				}
			}
			if (direction == "right") {
				if (map[player1.getLocation()[0] + 1][player1.getLocation()[1]] == 0) {
					player1.setDirection("right");
				} else {
					changeNextDirection(direction);
				}
			}
			if (direction == "bottom") {
				if (map[player1.getLocation()[0]][player1.getLocation()[1] - 1] == 0) {
					player1.setDirection("bottom");
				} else {
					changeNextDirection(direction);
				}
			}
			if (direction == "left") {
				if (map[player1.getLocation()[0]][player1.getLocation()[1]] == 0) {
					player1.setDirection("left");
				} else {
					changeNextDirection(direction);
				}
			}
		}
	}

	private void changeNextDirection(String direction) {
		if (direction != player1.getDirection()) {
			player1.setDirection(direction);
		}
	}

	private void changePlayerImage() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				boolean a = true;
				while (true) {
					if (a) {
						player1.setImage(pac1);
						a = false;
					} else {
						if (player1.getDirection() == "top") {
							player1.setImage(pac2d);
						} else if (player1.getDirection() == "right") {
							player1.setImage(pac2a);
						} else if (player1.getDirection() == "bottom") {
							player1.setImage(pac2b);
						} else if (player1.getDirection() == "left") {
							player1.setImage(pac2c);
						}
						a = true;
					}
					Util.sleep(100);
				}
			}
		}).start();
	}

	private void changePlayerLocation() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				boolean a = true;
				while (true) {
					String direction = player1.getDirection();
					if (direction == "top") {
						if (map[player1.getLocation()[0]][player1.getLocation()[1] + 1] == 0) {
							for (int i = 0; i < Util.ONE_BLOCK; i++) {
								move(0, -1);
								Util.sleep(20);
							}
						} else {
							turn(player1.getNextDirection());
						}
					} else if (direction == "right") {
						if (map[player1.getLocation()[0] + 1][player1.getLocation()[1]] == 0) {
							for (int i = 0; i < Util.ONE_BLOCK; i++) {
								move(1, 0);
								Util.sleep(20);
							}
						} else {
							turn(player1.getNextDirection());
						}
					} else if (direction == "bottom") {
						if (map[player1.getLocation()[0]][player1.getLocation()[1] - 1] == 0) {
							for (int i = 0; i < Util.ONE_BLOCK; i++) {
								move(0, 1);
								Util.sleep(20);
							}
						} else {
							turn(player1.getNextDirection());
						}
					} else if (direction == "left") {
						if (map[player1.getLocation()[0] - 1][player1.getLocation()[1]] == 0) {
							for (int i = 0; i < Util.ONE_BLOCK; i++) {
								move(-1, 0);
								Util.sleep(20);
							}
						} else {
							turn(player1.getNextDirection());
						}
					}
					Util.sleep(10);
				}
			}
		}).start();
	}

	private void move(double x, double y) {
		player1.getImage().setX(player1.getImage().getX() + x);
		player1.getImage().setY(player1.getImage().getY() + y);
	}

	private void turn(String direction) {
		if (direction == "top") {
			if (map[player1.getLocation()[0]][player1.getLocation()[1] + 1] == 0) {
				player1.setDirection("top");
			}
		} else if (direction == "right") {
			if (map[player1.getLocation()[0] + 1][player1.getLocation()[1]] == 0) {
				player1.setDirection("right");
			}
		} else if (direction == "bottom") {
			if (map[player1.getLocation()[0]][player1.getLocation()[1] - 1] == 0) {
				player1.setDirection("bottom");
			}
		} else if (direction == "left") {
			if (map[player1.getLocation()[0] - 1][player1.getLocation()[1]] == 0) {
				player1.setDirection("top");
			}
		} else {
			if (map[player1.getLocation()[0]][player1.getLocation()[1] + 1] == 0) {
				player1.setDirection("top");
			} else if (map[player1.getLocation()[0] + 1][player1.getLocation()[1]] == 0) {
				player1.setDirection("right");
			} else if (map[player1.getLocation()[0]][player1.getLocation()[1] - 1] == 0) {
				player1.setDirection("bottom");
			} else if (map[player1.getLocation()[0] - 1][player1.getLocation()[1]] == 0) {
				player1.setDirection("left");
			}
		}
	}
	
	private void mapLoading() {
		//테두리 작성
		for(int i = 0; i < map.length; i++) {
			map[i][0] = 1;
		}
		for(int i = 1; i < map.length; i++) {
			map[0][i] = 1;
		}
		for(int i = 1; i < map.length; i++) {
			map[i][map.length-1] = 1;
		}
		for(int i = 0; i < map.length; i++) {
			map[map.length-1][i] = 1;
		}
	}

	// map
	// □ - 길 0
	// ■ - 벽 1
	// ● - 포인트 2
	//■ ■ ■ ■ ■ ■ ■ ■ ■ ■ ■ ■ ■ ■ ■ ■ ■ ■ ■ ■ ■ ■ ■ ■ ■ ■ ■ ■ ■ ■
	//■ □ □ □ ■ ■ ■ ■ ■ ■ □ □ □ □ □ ■ ■ ■ ■ ■ ■ ■ ■ ■ ■ ■ ■ ■ ■ ■
	//■ ■ ■ □ ■ ■ ■ □ □ □ □ ■ ■ ■ □ ■ ■ ■ ■ ■ ■ ■ ■ ■ ■ ■ ■ ■ ■ ■
	//■ □ ■ □ ■ ■ ■ □ ■ ■ ■ ■ ■ ■ □ ■ ■ ■ ■ ■ ■ ■ ■ ■ ■ ■ ■ ■ ■ ■
	//■ □ □ □ ■ ■ ■ □ ■ ■ ■ ■ ■ ■ □ ■ ■ ■ ■ ■ ■ ■ ■ ■ ■ ■ ■ ■ ■ ■
	//■ ■ □ ■ ■ ■ ■ □ ■ ■ ■ ■ ■ ■ □ ■ ■ ■ ■ ■ ■ ■ ■ ■ ■ ■ ■ ■ ■ ■
	//■ ■ □ ■ □ □ □ □ □ □ □ □ □ □ □ □ □ ■ ■ ■ ■ ■ ■ ■ ■ ■ ■ ■ ■ ■
	//■ ■ □ □ □ ■ ■ □ ■ ■ ■ ■ ■ ■ □ ■ ■ ■ ■ ■ ■ ■ ■ ■ ■ ■ ■ ■ ■ ■
	//■ ■ ■ □ ■ ■ ■ □ ■ ■ ■ ■ ■ ■ □ ■ ■ ■ ■ ■ ■ ■ ■ ■ ■ ■ ■ ■ ■ ■
//10//■ ■ ■ □ ■ ■ ■ □ □ □ ■ ■ ■ ■ □ ■ ■ ■ ■ ■ ■ ■ ■ ■ ■ ■ ■ ■ ■ ■
	//■ □ □ □ ■ ■ ■ ■ ■ ■ □ □ □ □ □ □ □ ■ ■ ■ ■ ■ ■ ■ ■ ■ ■ ■ ■ ■
	//■ □ ■ ■ ■ ■ ■ ■ ■ ■ □ ■ ■ ■ ■ ■ □ ■ ■ ■ ■ ■ ■ ■ ■ ■ ■ ■ ■ ■
	//■ □ ■ ■ □ □ □ ■ ■ ■ □ ■ □ □ □ ■ □ ■ ■ ■ ■ ■ ■ ■ ■ ■ ■ ■ ■ ■
	//■ □ ■ ■ □ ■ □ ■ ■ ■ □ ■ □ □ □ ■ □ ■ ■ ■ ■ ■ ■ ■ ■ ■ ■ ■ ■ ■
	//■ □ □ □ □ ■ □ ■ ■ ■ □ ■ □ □ □ ■ □ ■ ■ ■ ■ ■ ■ ■ ■ ■ ■ ■ ■ ■
	//■ ■ ■ □ ■ ■ □ ■ ■ ■ □ ■ ■ □ ■ ■ □ ■ ■ ■ ■ ■ ■ ■ ■ ■ ■ ■ ■ ■
	//■ ■ ■ □ ■ ■ □ ■ ■ ■ □ □ □ □ □ □ □ □ □ □ ㅍ ■ ■ ■ ■ ■ ■ ■ ■ ■
	//■ ■ ■ □ ■ ■ □ □ □ □ □ ■ ■ ■ ■ □ ■ ■ ■ ■ □ ■ ■ ■ ■ ■ ■ ■ ■ ■
	//■ ■ ■ □ ■ ■ ■ ■ ■ ■ □ ■ ■ ■ ■ □ □ □ ■ ■ □ ■ ■ ■ ■ ■ ■ ■ ■ ■
//10//■ ■ ■ □ ■ ■ ■ ■ ■ ■ □ ■ ■ ■ ■ ■ ■ □ □ □ □ ■ ■ ■ ■ ■ ■ ■ ■ ■
	//■ ■ ■ □ ■ ■ ■ ■ ■ ■ □ ■ ■ □ □ □ □ □ ■ ■ □ ■ ■ ■ ■ ■ ■ ■ ■ ■
	//■ ■ ■ □ ■ ■ ■ ■ ■ ■ □ ■ ■ □ ■ ■ ■ ■ ■ ■ □ ■ ■ ■ ■ ■ ■ ■ ■ ■
	//■ ■ ■ □ ■ ■ ■ ■ ■ ■ □ ■ ■ □   ■ ■ ■ ■ ■ ■ □ ■ ■ ■ ■ ■ ■ ■ ■
	//■ ■ □ □ □ □ □ □ □ □ □ □ □ □ □ □ □ □ □ □ □ ■ ■ ■ ■ ■ ■ ■ ■ ■
	//■ ■ □ ■ ■ ■ ■ □ ■ ■ □ ■ ■ ■ ■ ■ ■ ■ ■ ■ □ □ □ □ □ □ □ □ □ ■
	//■ ■ □ ■ ■ ■ ■ □ □ □ □ ■ ■ ■ ■ ■ ■ ■ ■ ■ □ ■ ■ ■ ■ □ ■ ■ □ ■ 
	//■ □ □ ■ ■ ■ ■ □ ■ ■ □ □ □ □ □ □ □ □ □ ■ □ ■ ■ ■ ■ □ ■ ■ □ ■ 
	//■ □ ■ ■ ■ ■ ■ □ ■ ■ ■ ■ ■ ■ ■ ■ ■ ■ □ ■ ■ ■ ■ ■ ■ □ ■ ■ □ ■
	//■ □ □ □ □ □ □ □ ■ ■ ■ ■ ■ ■ ■ ■ ■ ■ □ □ □ □ □ □ □ □ □ □ □ ■
//10//■ ■ ■ ■ ■ ■ ■ ■ ■ ■ ■ ■ ■ ■ ■ ■ ■ ■ ■ ■ ■ ■ ■ ■ ■ ■ ■ ■ ■ ■
}

