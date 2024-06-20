package model.motile.vehicle;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import model.Floor;
import model.motile.Falcon;
import model.motile.bomb.Shell;
import view.Main;

import java.util.GregorianCalendar;
import java.util.Objects;

public class Tank extends Vehicle {
	public final static double WIDTH = 70, HEIGHT = 30;
	private long lastShotTime;

	public Tank(Floor floor, int difficulty) {
		super(floor, WIDTH, HEIGHT);
		this.setSpeed(Falcon.minSpeed * difficulty);
		setFill(new ImagePattern(new Image(Objects.requireNonNull(Main.class.getResource("/images/nodes/tank.png")).toExternalForm())));
		setScaleX(-1);
		if (this.hSpeed > 0) setScaleY(-1);
	}

	public Shell shoot(Falcon falcon) {
		lastShotTime = (new GregorianCalendar()).getTimeInMillis();
		return new Shell(this, falcon);
	}

	public long getTimePassedLastShot() {
		return (new GregorianCalendar()).getTimeInMillis() - this.lastShotTime;
	}
}
