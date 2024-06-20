package model.motile.bomb;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import model.Game;
import model.motile.Falcon;
import view.Main;

import java.util.Objects;

public class AtomicBomb extends Bomb {
	private static final double WIDTH = 25, HEIGHT = 15;

	public AtomicBomb(Falcon falcon) {
		super(falcon.getCoordinates(50, 25), WIDTH, HEIGHT, 2, 0);
		setFill(new ImagePattern(new Image(Objects.requireNonNull(Main.class.getResource("/images/nodes/atomicBomb.png")).toExternalForm())));
		setScaleX(-1);
	}

	@Override
	public void checkExplosion(Game game) {

	}

	@Override
	public void explode(Game game) {

	}
}
