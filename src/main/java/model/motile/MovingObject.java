package model.motile;

import javafx.geometry.Point2D;
import javafx.scene.shape.Rectangle;

public abstract class MovingObject extends Rectangle {
	protected double vSpeed, hSpeed;
	protected double vAcceleration, hAcceleration;

	public MovingObject(double width, double height, Point2D point) {
		super(width, height);
		this.setX(point.getX());
		this.setY(point.getY());
		this.vSpeed = 0;
		this.hSpeed = 0;
		this.vAcceleration = 0;
		this.hAcceleration = 0;
	}

	public abstract void sidePass();

	public void move() {
		vSpeed += vAcceleration;
		hSpeed += hAcceleration;
		this.setX(this.getX() + hSpeed);
		this.setY(this.getY() + vSpeed);
		if (hSpeed < 0) this.setRotate(Math.toDegrees(Math.atan(vSpeed / hSpeed)));
		else this.setRotate(Math.toDegrees(Math.atan(vSpeed / hSpeed)) + 180);
		sidePass();
	}

	public double getSpeed() {
		return Math.sqrt(hSpeed * hSpeed + vSpeed * vSpeed);
	}

	public void setVSpeed(double vSpeed) {
		this.vSpeed = vSpeed;
	}

	public void setHSpeed(double hSpeed) {
		this.hSpeed = hSpeed;
	}

	public void setSpeed(double speed) {
		if (this.getSpeed() == 0) this.hSpeed = speed;
		this.vSpeed *= speed / this.getSpeed();
		this.hSpeed *= speed / this.getSpeed();
	}

	public void giveVAcceleration(double vAcceleration) {
		this.vAcceleration += vAcceleration;
	}

	public void giveHAcceleration(double hAcceleration) {
		this.hAcceleration += hAcceleration;
	}

	public void giveRotation(double degrees) {
		double sin = Math.sin(Math.toRadians(degrees));
		double cos = Math.cos(Math.toRadians(degrees));
		double hSpeed = this.hSpeed * cos - this.vSpeed * sin;
		double vSpeed = this.hSpeed * sin + this.vSpeed * cos;
		this.setHSpeed(hSpeed);
		this.setVSpeed(vSpeed);
	}


	public Point2D getCoordinates(double x, double y) {
		double sin = Math.sin(Math.toRadians(this.getRotate() + 180));
		double cos = Math.cos(Math.toRadians(this.getRotate() + 180));
		x -= this.getWidth() / 2;
		y -= this.getHeight() / 2;
		double X = x * cos - y * sin + this.getX() + this.getWidth() / 2;
		double Y = x * sin + y * cos +  this.getY() + this.getHeight() / 2;
		return new Point2D(X, Y);
	}

	public Point2D getCenter() {
		return getCoordinates(this.getWidth() / 2, this.getHeight() / 2);
	}

}
