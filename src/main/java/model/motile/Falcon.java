package model.motile;

import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import model.motile.bomb.AtomicBomb;
import model.motile.bomb.Missile;
import view.Main;

import java.util.Objects;

public class Falcon extends MovingObject {
	public static final double minSpeed = 2.5, maxSpeed = 5;
	public static final double WIDTH = 95, HEIGHT = 32;
	public static final double acceleration = 0.07;
	private boolean isRoofCollided;
	private double lastSpeed;

	public Falcon() {
		super(WIDTH, HEIGHT, new Point2D(500, 100));
		isRoofCollided = false;
		setFill(new ImagePattern(new Image(Objects.requireNonNull(Main.class.getResource("/images/nodes/falcon.png")).toExternalForm())));
		setScaleY(-1);
	}

	private void speedControl() {
		if (this.getSpeed() < minSpeed) this.setSpeed(minSpeed);
		if (this.getSpeed() > maxSpeed) this.setSpeed(maxSpeed);
	}

	@Override
	public void sidePass() {
		if (this.getX() > 1000) this.setX(-WIDTH);
		if (this.getX() < -WIDTH) this.setX(1000);
	}

	private void roofCollision() {
		double rotate = Math.toRadians(this.getRotate());
		double minY = this.getY() + HEIGHT / 2 - (Math.abs(Math.sin(rotate) * WIDTH / 2) + Math.abs(Math.cos(rotate) * HEIGHT / 2));
		if (minY < -10) {
			this.setY(this.getY() - minY - 10);
			if (!isRoofCollided) {
				lastSpeed = Math.abs(vSpeed);
				isRoofCollided = true;
				this.giveVAcceleration(lastSpeed / 30);
			}
		} else if (this.vSpeed >= lastSpeed / 3 && isRoofCollided) {
			this.giveVAcceleration(-lastSpeed / 30);
			isRoofCollided = false;
		}
	}

	@Override
	public void move() {
		super.move();
		speedControl();
		roofCollision();
	}

	public Missile launchMissile() {
		return new Missile(this);
	}

	public AtomicBomb launchAtomicBomb() {
		return new AtomicBomb(this);
	}

	@Override
	public boolean intersects(Bounds bounds) {
		int x = 20;
		for (int i = 0; i <= x; i++) {
			if (bounds.contains(this.getCoordinates(WIDTH / x * i, HEIGHT / 6 * 5))) return true;
			if (i < x && bounds.contains(this.getCoordinates(WIDTH / x * i, HEIGHT / 2))) return true;
			if (i <= x / 15 && bounds.contains(this.getCoordinates(WIDTH / x * i, 0))) return true;

		}
		return false;
	}

	@Override
	public boolean contains(Point2D point2D) {
		if (!super.contains(point2D)) return false;
		point2D = this.getCoordinates(point2D.getX() - this.getX(), point2D.getY() - this.getY());
		return point2D.getY() >= this.getY() + HEIGHT / 2 || point2D.getX() <= this.getX() + WIDTH / 15;
	}
}
