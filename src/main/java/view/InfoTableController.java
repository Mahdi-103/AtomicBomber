package view;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import java.util.Objects;

public class InfoTableController {
	private static InfoTableController controller;
	public Label wave;
	public Label hp;
	public Label kills;
	public Label accuracy;
	public Label radioActive;
	public Label cluster;
	public Label migAlert;
	private boolean isAlertHiding;
	private Timeline timeline;
	private static Pane pane;

	public static void start() {
		pane = null;
		try {
			pane = FXMLLoader.load(Objects.requireNonNull(Main.class.getResource("/FXML/InfoTable.fxml")));
		} catch (Exception e) {
			e.printStackTrace();
		}
		GameLauncher.getController().getPane().getChildren().add(pane);
	}

	@FXML
	public void initialize() {
		controller = this;
	}

	public void showMigAlert(double seconds) {
		if (timeline != null) timeline.stop();
		isAlertHiding = true;
		timeline = new Timeline(new KeyFrame(Duration.millis(10), actionEvent -> updateMigAlert()));
		int cnt = (int) (seconds * 100 + migAlert.getOpacity() * 50);
		timeline.setCycleCount(cnt);
		timeline.play();
	}

	private void updateMigAlert() {
		if (migAlert.getOpacity() <= 0.01) isAlertHiding = false;
		if (migAlert.getOpacity() >= 0.99) isAlertHiding = true;
		if (isAlertHiding) migAlert.setOpacity(migAlert.getOpacity() - 0.02);
		else migAlert.setOpacity(migAlert.getOpacity() + 0.02);
	}

	public void setOpacity(double v) {
		for (Node child : pane.getChildren()) {
			child.setOpacity(v);
		}
	}

	public static InfoTableController getController() {
		return controller;
	}
}
