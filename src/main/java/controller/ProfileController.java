package controller;

import javafx.scene.control.Alert;
import model.User;
import model.Validations;
import view.Main;
import view.main.MainMenuController;
import view.main.ProfileMenuController;

public class ProfileController {
	public static void updateProfile(String username, String password, String newPassword, String confirmNewPassword, String imageUrl) {
		if (!password.equals(User.getLoggedInUser().getPassword())) {
			Main.showAlert(Alert.AlertType.ERROR, "ERROR", "Password doesn't match", "Please enter the correct password");
			return;
		}
		if (!User.getLoggedInUser().getUsername().equals(username)) {
			if (User.getUserByUsername(username) != null) {
				Main.showAlert(Alert.AlertType.ERROR, "ERROR", "Username already used", username + " is already taken. Please choose another one.");
				return;
			}
			if (!Validations.isValidUsername(username)) {
				Main.showAlert(Alert.AlertType.ERROR, "ERROR", "Invalid username", "Username can only contain latin letters, underscore and dot.");
				return;
			}
		}
		if (!newPassword.isEmpty() || !confirmNewPassword.isEmpty()) {
			if (!newPassword.equals(confirmNewPassword)) {
				Main.showAlert(Alert.AlertType.ERROR, "ERROR", "Invalid confirm", "Confirm new password must be the same as new password");
				return;
			}
			User.getLoggedInUser().setPassword(newPassword);
		}
		User.getLoggedInUser().setUsername(username);
		User.getLoggedInUser().setAvatarURL(imageUrl);
		MainMenuController.start();
	}
}
