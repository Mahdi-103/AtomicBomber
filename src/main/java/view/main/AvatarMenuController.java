package view.main;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.User;
import view.Main;

import java.io.File;
import java.util.Objects;

public class AvatarMenuController extends Application {
	private static final AvatarMenuController controller = new AvatarMenuController();
	public ImageView imageView1;
	public ImageView imageView2;
	public ImageView imageView3;
	public ImageView imageView4;
	public ImageView choice;

	public static void start() {
		try {
			controller.start(Main.appStage);
		} catch (Exception ignored) {
		}
	}

	@Override
	public void start(Stage stage) throws Exception {
		//Main.show(stage, Main.class.getResource("/FXML/AvatarMenu.fxml"));
		Pane root = FXMLLoader.load(Objects.requireNonNull(Main.class.getResource("/FXML/AvatarMenu.fxml")));
		Main.setBackground(root);

		Platform.runLater(root::requestFocus);
		stage.setScene(new Scene(root));
		stage.show();
		Main.setColor();
	}

	private boolean isImage(File file) {
		String url;
		try {
			url = file.toURI().toURL().toString();
		} catch (Exception ignored) {
			return false;
		}
		return (new Image(url)).getWidth() != 0;
	}

	@FXML
	private void initialize() {
		choice.setImage(new Image(User.getLoggedInUser().getAvatarURL()));
		choice.setOnDragDetected(event -> {
			choice.startDragAndDrop(TransferMode.ANY);
			event.consume();
		});
		choice.setOnDragOver(event -> {
			if (event.getDragboard().hasString() && isImage(event.getDragboard().getFiles().get(0)))
				event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
			event.consume();
		});
	}

	public void setAvatar(MouseEvent mouseEvent) {
		ImageView imageView = (ImageView) mouseEvent.getTarget();
		choice.setImage(imageView.getImage());
	}

	public void save(ActionEvent actionEvent) {
		ProfileMenuController.getController().avatar.setImage(choice.getImage());
		this.back(null);
	}

	public void back(ActionEvent actionEvent) {
		Main.appStage.setScene(ProfileMenuController.getScene());
		Main.appStage.show();
	}

	public void drag(DragEvent dragEvent) {
		if (!dragEvent.getDragboard().hasFiles() || dragEvent.getDragboard().getFiles().size() > 1) return;
		try {
			Image image = new Image(dragEvent.getDragboard().getFiles().get(0).toURI().toURL().toString());
			choice.setImage(image);
		} catch (Exception ignored) {
		}
	}

	public void chooseFile(ActionEvent actionEvent) {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Choose avatar image");
		FileChooser.ExtensionFilter imageFilter = new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.png", "*.jpeg");
		fileChooser.getExtensionFilters().add(imageFilter);
		File file = fileChooser.showOpenDialog(Main.appStage);
		Image image;
		try {
			image = new Image(file.toURI().toURL().toString());
		} catch (Exception e) {
			return;
		}
		choice.setImage(image);
	}
}
