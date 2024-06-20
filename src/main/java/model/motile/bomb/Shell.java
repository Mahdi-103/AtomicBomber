package model.motile.bomb;

import controller.GameController;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import model.Game;
import model.motile.Falcon;
import model.motile.vehicle.Tank;
import view.Main;

import java.util.Objects;

public class Shell extends Bomb {
	private static final double WIDTH = 21, HEIGHT = 6;

	public Shell(Tank tank, Falcon falcon) {
		super(tank.getCoordinates(10, 10), WIDTH, HEIGHT, 0, 0);
		Point2D point2D = falcon.getCoordinates(3 * Falcon.WIDTH / 4, 3 * HEIGHT / 5);
		this.setHSpeed(point2D.getX() - this.getX());
		this.setVSpeed(point2D.getY() - this.getY());
		this.setSpeed(5);
		setFill(new ImagePattern(new Image(Objects.requireNonNull(Main.class.getResource("/images/nodes/shell.png")).toExternalForm())));
		setScaleX(-1);
	}

	@Override
	public void checkExplosion(Game game) {
		Point2D nose = this.getNose();
		if (game.getFalcon().contains(nose) || game.getFloor().contains(nose)) this.explode(game);
	}

	@Override
	public void explode(Game game) {
		if (game.getFalcon().contains(this.getNose())) GameController.falconCollision(this.getNose());
		GameController.remove(this);
	}

}
