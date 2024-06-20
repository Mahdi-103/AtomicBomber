package view.main;

import controller.ProfileController;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import model.User;
import view.Main;
import view.StartMenuController;

public class ProfileMenuController extends Application {

	public static Scene scene = null;
	public TextField username;
	public PasswordField oldPassword;
	public PasswordField newPassword;
	public PasswordField confirmNewPassword;
	public ImageView avatar;

	private static ProfileMenuController controller = new ProfileMenuController();

	public static void start() {
		try {
			controller.start(Main.appStage);
		} catch (Exception ignored) {
		}
	}


	@FXML
	public void initialize() {
		username.setText(User.getLoggedInUser().getUsername());
		avatar.setImage(new Image(User.getLoggedInUser().getAvatarURL()));
	}

	@Override
	public void start(Stage stage) throws Exception {
		Main.show(stage, Main.class.getResource("/FXML/ProfileMenu.fxml"));
	}

	public void updateAvatar(ActionEvent actionEvent) {
		controller = this;
		scene = Main.appStage.getScene();
		AvatarMenuController.start();
	}

	public void saveChanges(ActionEvent actionEvent) {
		ProfileController.updateProfile(username.getText(), oldPassword.getText(), newPassword.getText(), confirmNewPassword.getText(), avatar.getImage().getUrl());
	}

	public void back(ActionEvent actionEvent) {
		MainMenuController.start();
	}

	public static Scene getScene() {
		return scene;
	}

	public static ProfileMenuController getController() {
		return controller;
	}

	public void deleteAccount(ActionEvent actionEvent) {
		if(Main.showAlert(Alert.AlertType.CONFIRMATION, "Confirmation", "Delete Account", "Are you sure to delete your account permanently?").get().getText().equals("OK")) {
			User.getLoggedInUser().deleteAccount();
			StartMenuController.start();
		}
	}
}
