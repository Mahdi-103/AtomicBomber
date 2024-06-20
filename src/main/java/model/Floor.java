package model;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import model.motile.Falcon;
import view.GameLauncher;

public class Floor extends Rectangle {
	public Floor(double height) {
		super(GameLauncher.WIDTH * 3 , height);
		this.setX(-GameLauncher.WIDTH);
		this.setY(GameLauncher.HEIGHT - height);
		this.setFill(Color.SANDYBROWN);
	}
}
