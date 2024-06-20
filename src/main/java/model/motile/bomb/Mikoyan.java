package model.motile.bomb;

import controller.GameController;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import model.Game;
import model.motile.Falcon;
import model.motile.Mig;
import view.Main;

import java.util.Objects;

public class Mikoyan extends Bomb {
	public static final double WIDTH = 24, HEIGHT = 5;

	public Mikoyan(Mig mig, Falcon falcon) {
		super(mig.getCoordinates(Mig.WIDTH / 2, Mig.HEIGHT - 1), WIDTH, HEIGHT, 0, 0);
		Point2D point2D = falcon.getCoordinates(3 * Falcon.WIDTH / 4, 3 * HEIGHT / 5);
		this.setHSpeed(point2D.getX() - this.getX());
		this.setVSpeed(point2D.getY() - this.getY());
		this.setSpeed(5);
		setFill(new ImagePattern(new Image(Objects.requireNonNull(Main.class.getResource("/images/nodes/mikoyan.png")).toExternalForm())));
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
