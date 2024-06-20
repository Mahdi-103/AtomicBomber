package model;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import view.Main;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

public class Fire extends Rectangle {
	private final Timeline timeline;

	public Fire(Point2D point, double width, double height) {
		super(width, height);
		setX(point.getX() - width / 2);
		setY(point.getY() - height / 2);
		AtomicInteger i = new AtomicInteger(1);
		timeline = new Timeline(new KeyFrame(Duration.millis(10), actionEvent -> {
			setFill(new ImagePattern(new Image(Objects.requireNonNull(Main.class.getResource("/images/effects/fire" + i + ".png")).toExternalForm())));
			i.incrementAndGet();
			if (i.get() == 4) i.set(1);
		}));
		timeline.setCycleCount(-1);
		timeline.play();
	}

	public Timeline getTimeline() {
		return timeline;
	}
}
