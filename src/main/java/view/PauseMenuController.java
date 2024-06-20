package view;

import controller.GameController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import model.User;
import view.main.MainMenuController;

import java.util.Objects;

public class PauseMenuController {

	private static PauseMenuController controller;
	private static final ImageView icon = new ImageView(new Image(Objects.requireNonNull(Main.class.getResource("/images/pause.png")).toExternalForm()));
	private static Pane pane;
	public Button saver;

	public static void start() {
		icon.setFitHeight(20);
		icon.setFitWidth(20);
		icon.setX(GameLauncher.WIDTH - 30);
		icon.setY(10);
		icon.setOnMouseClicked(mouseEvent -> pause());
		icon.setOpacity(1);
		GameLauncher.getController().getPane().getChildren().add(icon);
	}

	@FXML
	public void initialize() {
		if (GameController.game.getUser().isGuest()) saver.setDisable(true);
	}

	public static void pause() {
		icon.setOnMouseClicked(null);
		GameLauncher.getController().getActions().pause();
		for (Node child : GameLauncher.getController().getPane().getChildren()) {
			child.setOpacity(0.5);
		}
		icon.setOpacity(0);
		pane = null;
		try {
			pane = FXMLLoader.load(Objects.requireNonNull(Main.class.getResource("/FXML/PauseMenu.fxml")));
		} catch (Exception e) {
			e.printStackTrace();
		}
		GameLauncher.getController().getPane().getChildren().add(pane);
	}

	public void resume(ActionEvent actionEvent) {
		GameLauncher.getController().getPane().getChildren().remove(pane);
		for (Node child : GameLauncher.getController().getPane().getChildren()) {
			child.setOpacity(1);
		}
		GameLauncher.getController().getActions().play();
		icon.setOnMouseClicked(mouseEvent -> pause());
		GameController.game.getFalcon().requestFocus();
	}

	public void help(ActionEvent actionEvent) {
	}
	public void save(ActionEvent actionEvent) {
		GameController.saveGame();
		exit(null);
	}

	public void exit(ActionEvent actionEvent) {
		MainMenuController.start();
	}

	public static void setIconOpacity(double v) {
		icon.setOpacity(v);
	}

	public static PauseMenuController getController() {
		return controller;
	}

}
