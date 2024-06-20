package model.motile.bomb;

import controller.GameController;
import javafx.geometry.Point2D;
import model.Game;
import model.motile.MovingObject;
import view.GameLauncher;

public abstract class Bomb extends MovingObject {

	public Bomb(Point2D point2D, double width, double height, double vSpeed, double hSpeed) {
		super(width, height, point2D);
		this.setVSpeed(vSpeed);
		this.setHSpeed(hSpeed);
	}

	@Override
	public void sidePass() {
		if (getY() < -getHeight() || getX() < -getWidth() || getX() > GameLauncher.WIDTH + getWidth())
			GameController.remove(this);
	}

	public Point2D getNose() {
		return this.getCoordinates(this.getWidth(), this.getHeight() / 2);
	}

	public abstract void checkExplosion(Game game);

	public abstract void explode(Game game);
}
