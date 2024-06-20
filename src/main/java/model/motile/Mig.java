package model.motile;

import controller.GameController;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import model.motile.bomb.Mikoyan;
import view.GameLauncher;
import view.Main;

import java.util.GregorianCalendar;
import java.util.Objects;

public class Mig extends MovingObject {
	public static final double WIDTH = 65, HEIGHT = 24;
	private long lastShotTime;

	public Mig(int difficulty) {
		super(WIDTH, HEIGHT, new Point2D(0, Main.random.nextDouble(5, GameLauncher.HEIGHT / 2)));
		if (Main.random.nextBoolean()) {
			setX(-WIDTH + 1);
			setHSpeed(Falcon.minSpeed * difficulty);
		} else {
			setX(GameLauncher.WIDTH - 1);
			setHSpeed(-Falcon.minSpeed * difficulty);
		}
		setFill(new ImagePattern(new Image(Objects.requireNonNull(Main.class.getResource("/images/nodes/mig.png")).toExternalForm())));
		setScaleX(-1);
		if (this.hSpeed > 0) setScaleY(-1);
	}

	@Override
	public void sidePass() {
		if (getY() < -getHeight() || getX() < -getWidth() || getX() > GameLauncher.WIDTH + getWidth())
			GameController.remove(this);
	}

	public Mikoyan shoot(Falcon falcon) {
		lastShotTime = (new GregorianCalendar()).getTimeInMillis();
		return new Mikoyan(this, falcon);
	}

	public long getTimePassedLastShot() {
		return (new GregorianCalendar()).getTimeInMillis() - this.lastShotTime;
	}
}
