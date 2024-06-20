package view.main;

import controller.SettingController;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;
import model.User;
import view.Main;

public class SettingMenuController extends Application {

	private static final SettingMenuController controller = new SettingMenuController();
	public Button controlKeys;
	public Button color;
	public ChoiceBox difficulty;

	public static void start() {
		try {
			controller.start(Main.appStage);
		} catch (Exception ignored) {
		}
	}


	@FXML
	public void initialize() {
		difficulty.setValue(difficulty.getItems().get(User.getLoggedInUser().getDifficultyLevel() - 1));
		controlKeys.setText(User.getLoggedInUser().getControlKeys().toString());
		if (User.getLoggedInUser().isNoir()) color.setText("Noir");
		else color.setText("Colorful");
	}

	@Override
	public void start(Stage stage) throws Exception {
		Main.show(stage, Main.class.getResource("/FXML/SettingMenu.fxml"));
	}

	public void changeColor(ActionEvent actionEvent) {
		color.setText(SettingController.updateColor());
	}

	public void changeControlKeys(ActionEvent actionEvent) {
		controlKeys.setText(SettingController.updateControlKeys());
	}

	public void back(ActionEvent actionEvent) {
		SettingController.setDifficulty((String) difficulty.getValue());
		MainMenuController.start();
	}
}
