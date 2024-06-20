package model.construction;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import model.Floor;
import view.Main;

import java.util.Objects;

public class House extends Construction {
	public static final double WIDTH = 40, HEIGHT = 35;

	public House(Floor floor, double X) {
		super(floor, X, WIDTH, HEIGHT);
		setFill(new ImagePattern(new Image(Objects.requireNonNull(Main.class.getResource("/images/nodes/house.png")).toExternalForm())));
	}
}
