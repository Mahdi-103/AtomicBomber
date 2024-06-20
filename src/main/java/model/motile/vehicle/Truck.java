package model.motile.vehicle;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import model.Floor;
import model.motile.Falcon;
import view.Main;

import java.util.Objects;

public class Truck extends Vehicle {
	private final static double WIDTH = 60, HEIGHT = 28;
	public Truck(Floor floor) {
		super(floor, WIDTH, HEIGHT);
		this.setSpeed(Falcon.minSpeed);
		setFill(new ImagePattern(new Image(Objects.requireNonNull(Main.class.getResource("/images/nodes/truck.png")).toExternalForm())));
		if(this.hSpeed > 0) setScaleY(-1);
	}
}
