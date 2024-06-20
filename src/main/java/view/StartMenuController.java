package view;

import controller.SigningController;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import view.signing.SignInMenuController;
import view.signing.SignUpMenuController;

import java.util.Objects;

public class StartMenuController extends Application {

	private static final StartMenuController controller = new StartMenuController();

	public static void start() {
		try {
			controller.start(Main.appStage);
		} catch (Exception ignored) {
		}
	}

	@Override
	public void start(Stage stage) throws Exception {
		Main.show(stage, Main.class.getResource("/FXML/StartMenu.fxml"), new Image(Objects.requireNonNull(Main.class.getResource("/images/backgrounds/StartMenu.jpg")).toString()));
	}

	public void signUp(ActionEvent actionEvent) {
		SignUpMenuController.start();
	}

	public void signIn(ActionEvent actionEvent) {
		SignInMenuController.start();
	}

	public void enterGuest(ActionEvent actionEvent) {
		SigningController.enterAsGuest();
	}

}
