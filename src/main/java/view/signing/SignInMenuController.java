package view.signing;

import controller.SigningController;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import view.Main;
import view.StartMenuController;

import java.util.Objects;

public class SignInMenuController extends Application {
	public TextField username;
	public PasswordField password;
	private static final SignInMenuController controller = new SignInMenuController();
	public static void start() {
		try {
			controller.start(Main.appStage);
		} catch (Exception ignored) {
		}
	}

	@Override
	public void start(Stage stage) throws Exception {
		Main.show(stage, Main.class.getResource("/FXML/SignInMenu.fxml"));
	}

	public void back(ActionEvent actionEvent) {
		StartMenuController.start();
	}
	public void login(ActionEvent actionEvent) {
		SigningController.login(username.getText(), password.getText());
	}
}
