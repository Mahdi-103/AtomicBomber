package model.motile.vehicle;

import controller.GameController;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Point2D;
import javafx.util.Duration;
import model.Burnable;
import model.Fire;
import model.Floor;
import model.motile.MovingObject;
import view.GameLauncher;
import view.Main;

import java.util.ArrayList;

public abstract class Vehicle extends MovingObject implements Burnable {
	private final ArrayList<Fire> fires;

	public Vehicle(Floor floor, double width, double height) {
		super(width, height, new Point2D(0, floor.getY() - height));
		if (Main.random.nextBoolean()) {
			setX(-width + 1);
			setHSpeed(1);
		} else {
			setX(GameLauncher.WIDTH - 1);
			setHSpeed(-1);
		}
		fires = new ArrayList<>();
	}

	@Override
	public void sidePass() {
		if (this.getX() > 1000) this.setX(-this.getWidth());
		if (this.getX() < -this.getWidth()) this.setX(1000);
	}

	public boolean isBurning() {
		return !fires.isEmpty();
	}

	@Override
	public void move() {
		if (!isBurning()) super.move();
	}

	@Override
	public Fire burn(Point2D point2D) {
		Fire fire = new Fire(point2D, 20, 20);
		if (!this.isBurning()) {
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
