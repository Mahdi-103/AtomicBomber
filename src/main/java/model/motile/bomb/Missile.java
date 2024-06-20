package model.motile.bomb;

import controller.GameController;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.util.Duration;
import model.Burnable;
import model.Fire;
import model.Game;
import model.construction.Construction;
import model.motile.Falcon;
import view.GameLauncher;
import view.Main;

import java.util.ArrayList;
import java.util.Objects;

public class Missile extends Bomb implements Burnable {
	private static final double WIDTH = 37, HEIGHT = 8;
	private Fire fire;
	public Missile(Falcon falcon) {
		super(falcon.getCoordinates(50, 25), WIDTH, HEIGHT, 2, 3);
		this.giveVAcceleration(0.09);
		this.giveRotation(falcon.getRotate() + 180);
		this.setSpeed(5);
		setFill(new ImagePattern(new Image(Objects.requireNonNull(Main.class.getResource("/images/nodes/missile.png")).toExternalForm())));
		setScaleX(-1);
		fire = null;
	}

	@Override
	public void sidePass() {
		if (getX() < -getWidth() || getX() > GameLauncher.WIDTH + getWidth())
			GameController.remove(this);
	}

	@Override
	public void checkExplosion(Game game) {
		boolean explosion = this.getBoundsInLocal().intersects(game.getFloor().getBoundsInLocal());
		for (Node node : game.getNodes()) {
			if (explosion) break;
			explosion = !(node instanceof Bomb || node instanceof Fire) && node.contains(this.getNose());
		}
		if (explosion) this.explode(game);
	}

	@Override
	public void explode(Game game) {
		ArrayList<Node> gameNodes = new ArrayList<>(game.getNodes());
		int hitCount = 0;
		for (Node gameNode : gameNodes) {
			if (!(gameNode instanceof Bomb || gameNode instanceof Fire) && gameNode.contains(this.getNose())) {
				if (gameNode instanceof Burnable) {
					Fire fire = ((Burnable) gameNode).burn(this.getNose());
					game.getNodes().add(fire);
					GameLauncher.getController().add(fire);
				}
				else GameController.remove(gameNode);
				hitCount++;
			}
		}
		game.addHits(hitCount);
		game.explodeBomb();
		this.burn(this.getCoordinates(WIDTH / 2, HEIGHT / 2));
		game.getNodes().add(this.fire);
		GameLauncher.getController().add(this.fire);
		GameController.remove(this);
	}

	@Override
	public Fire burn(Point2D point2D) {
		if (fire != null) return null;
		fire = new Fire(point2D, 60, 45);
		Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(2)));
		timeline.setCycleCount(1);
		timeline.setOnFinished(actionEvent -> GameController.remove(fire));
		timeline.play();
		return fire;
	}
}
