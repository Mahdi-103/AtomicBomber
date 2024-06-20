package model.construction;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import model.Floor;
import view.Main;

import java.util.Objects;

public class Building extends Construction {
	public static final double WIDTH = 50, HEIGHT = 30;

	public Building(Floor floor, double X) {
		super(floor, X, WIDTH, HEIGHT);
		setFill(new ImagePattern(new Image(Objects.requireNonNull(Main.class.getResource("/images/nodes/building.png")).toExternalForm())));
	}
}
