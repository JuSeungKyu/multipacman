package game;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Player {

	private String direction = null;
	private String nextDirection = null;

	private int[] Location = { 3, 3 };
	private ImageView image;

	public Player(ImageView imageView) {
		this.image = imageView;
	}

	public void setImage(Image img) {
		image.setImage(img);
	}
	
	public ImageView getImage() {
		return image;
	}
	
	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}

	public String getNextDirection() {
		return nextDirection;
	}

	public void setNextDirection(String nextDirection) {
		this.nextDirection = nextDirection;
	}

	public int[] getLocation() {
		return Location;
	}

	public void setLocation(int[] location) {
		Location = location;
	}

}
