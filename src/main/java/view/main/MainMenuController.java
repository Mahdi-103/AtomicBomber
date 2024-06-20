package view.main;

import controller.GameController;
import controller.MainController;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import model.Game;
import model.User;
import view.GameLauncher;
import view.Main;
import view.StartMenuController;

public class MainMenuController extends Application {

	private static final MainMenuController controller = new MainMenuController();
	public ImageView avatar;
	public Label username;
	public Button profileButton;
	public Button resumeButton;

	public static void start() {
		try {
			controller.start(Main.appStage);
		} catch (Exception ignored) {
		}
	}

	@FXML
	public void initialize() {
		avatar.setImage(new Image(User.getLoggedInUser().getAvatarURL()));
		username.setText(User.getLoggedInUser().getUsername());
		if (User.getLoggedInUser().isGuest()) profileButton.setDisable(true);
		if (User.getLoggedInUser().getIncompleteGame() == null) resumeButton.setDisable(true);
	}

	@Override
	public void start(Stage stage) throws Exception {
		Main.show(stage, Main.class.getResource("/FXML/MainMenu.fxml"));
	}


	public void newGame(ActionEvent actionEvent) {
		GameController.game = null;
		GameLauncher.start();
	}

	public void resume(ActionEvent actionEvent) {
		GameController.game = User.getLoggedInUser().getIncompleteGame();
		User.getLoggedInUser().setIncompleteGame(null);
		GameLauncher.start();
	}

	public void goToProfileMenu(ActionEvent actionEvent) {
		ProfileMenuController.start();
	}

	public void showRankings(ActionEvent actionEvent) {
		RankingMenuController.start();
	}

	public void goToSettings(ActionEvent actionEvent) {
		SettingMenuController.start();
	}

	public void logout(ActionEvent actionEvent) {
		MainController.logout();
		StartMenuController.start();
	}
}
