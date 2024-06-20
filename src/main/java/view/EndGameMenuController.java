package view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import view.main.MainMenuController;

import java.util.Objects;

public class EndGameMenuController {
	private static EndGameMenuController controller;
	public Label status;
	public Label wave;
	public Label kills;
	public Label accuracy;

	public static void start() {
		Pane pane = null;
		try {
			pane = FXMLLoader.load(Objects.requireNonNull(Main.class.getResource("/FXML/EndGame.fxml")));
		} catch (Exception e) {
			e.printStackTrace();
		}
		GameLauncher.getController().getActions().stop();
		GameLauncher.getController().getPane().getChildren().add(pane);
	}

	@FXML
	public void initialize() {
		controller = this;
	}

	public static EndGameMenuController getController() {
		return controller;
	}

	public void back(ActionEvent actionEvent) {
		MainMenuController.start();
	}
}
