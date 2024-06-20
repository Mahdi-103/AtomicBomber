package controller;

import javafx.scene.control.Alert;
import model.User;
import model.Validations;
import view.Main;
import view.main.MainMenuController;

import java.util.Objects;

public class SigningController {

	public static void register(String username, String password, String passwordConfirm) {
		if (!Validations.isValidUsername(username)) {
			Main.showAlert(Alert.AlertType.ERROR, "ERROR", "Invalid username", "Username can only contain latin letters, underscore and dot.");
			return;
		}
		if (User.getUserByUsername(username) != null) {
			Main.showAlert(Alert.AlertType.ERROR, "ERROR", "Already used username!", username + " is already used. Please try another one.");
			return;
		}
		if (!Validations.isValidPassword(password)) {
			Main.showAlert(Alert.AlertType.ERROR, "ERROR", "Invalid password", "Change the password");
			return;
		}
		if (!password.equals(passwordConfirm)) {
			Main.showAlert(Alert.AlertType.ERROR, "ERROR", "Invalid confirmation", "Confirm password must be the same as password");
			return;
		}
		User.setLoggedInUser(new User(username, password));
		MainMenuController.start();
	}

	public static void login(String username, String password) {
		User user = User.getUserByUsername(username);
		if (user == null) {
			Main.showAlert(Alert.AlertType.ERROR, "ERROR", "Invalid username", "No such username exists");
			return;
		}
		if (!user.getPassword().equals(password)) {
			Main.showAlert(Alert.AlertType.ERROR, "ERROR", "Invalid password", "Password is incorrect. Please try again.");
			return;
		}
		User.setLoggedInUser(user);
		MainMenuController.start();
	}

	public static void enterAsGuest() {
		String guestName;
		for (int i = 0; ; i++) {
			guestName = "guest" + Objects.toString(i);
			if (User.getUserByUsername(guestName) == null) break;
		}
		User.setLoggedInUser(new User(guestName, ""));
		MainMenuController.start();
	}
}
