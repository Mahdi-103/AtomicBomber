package view.signing;

import controller.SigningController;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import view.Main;
import view.StartMenuController;

public class SignUpMenuController extends Application {
	public TextField username;
	public PasswordField password;
	public PasswordField passwordConfirm;
	private static final SignUpMenuController controller = new SignUpMenuController();

	public static void start() {
		try {
			controller.start(Main.appStage);
		} catch (Exception ignored) {
		}
	}

	@Override
	public void start(Stage stage) throws Exception {
		Main.show(stage, Main.class.getResource("/FXML/SignUpMenu.fxml"));
	}


	public void back(ActionEvent actionEvent) {
		StartMenuController.start();
	}

	public void register(ActionEvent actionEvent) {
		SigningController.register(username.getText(), password.getText(), passwordConfirm.getText());
	}
}
