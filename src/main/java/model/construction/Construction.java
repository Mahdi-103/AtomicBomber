package model.construction;

import controller.GameController;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Point2D;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import model.Burnable;
import model.Fire;
import model.Floor;
import view.Main;

import java.util.ArrayList;

public abstract class Construction extends Rectangle implements Burnable {
	private final ArrayList<Fire> fires;

	public Construction(Floor floor, double X, double width, double height) {
		super(width, height);
		setX(X);
		setY(floor.getY() - height);
		fires = new ArrayList<>();
	}

	@Override
	public Fire burn(Point2D point2D) {
		Fire fire = new Fire(point2D, 30, 30);
		if (fires.isEmpty()) {
			Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(2)));
			timeline.setCycleCount(1);
			timeline.setOnFinished(actionEvent -> {
				for (Fire fire1 : fires) {
					fire1.getTimeline().stop();
					GameController.remove(fire1);
				}
				GameController.remove(this);
			});
			timeline.play();
		}
		fires.add(fire);
		return fire;
	}
}
