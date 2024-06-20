package view.main;

import controller.RankingController;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.Game;
import view.Main;

import java.util.ArrayList;
import java.util.Objects;

public class RankingMenuController extends Application {

	private static final RankingMenuController controller = new RankingMenuController();
	public RadioButton killSorter;
	public RadioButton difficultySorter;
	public RadioButton accuracySorter;
	public VBox list;

	public static void start() {
		try {
			controller.start(Main.appStage);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void start(Stage stage) throws Exception {
		Main.show(stage, Main.class.getResource("/FXML/RankingMenu.fxml"));
	}

	private void setSize(Label label, int rank) {
		label.setMinWidth(80);
		label.setMaxWidth(80);
		label.setMinHeight(16);
		label.setMaxHeight(16);
		label.setAlignment(Pos.CENTER);
		if (rank == 0) label.setTextFill(Color.GOLD);
		if (rank == 1) label.setTextFill(Color.SILVER);
		if (rank == 2) label.setTextFill(Color.SANDYBROWN);
	}

	private HBox show(Game game, int rank) {
		HBox hBox = new HBox();
		Label username = new Label(game.getUser().getUsername());
		setSize(username, rank);
		hBox.getChildren().add(username);
		Label kills = new Label(Objects.toString(game.getKills()));
		setSize(kills, rank);
		hBox.getChildren().add(kills);
		Label difficulty = new Label(Objects.toString(game.getKills() * game.getDifficultyLevel()));
		setSize(difficulty, rank);
		hBox.getChildren().add(difficulty);
		Label accuracy = new Label((int) (game.getAccuracy() * 100) + "%");
		setSize(accuracy, rank);
		hBox.getChildren().add(accuracy);
		Label lastWave = new Label(Objects.toString(game.getWave()));
		setSize(lastWave, rank);
		hBox.getChildren().add(lastWave);
		return hBox;
	}

	private void show(ArrayList<Game> games) {
		if (list.getChildren().size() > 1) {
			list.getChildren().subList(1, list.getChildren().size()).clear();
		}
		for (int i = 0; i < games.size(); i++) {
			list.getChildren().add(show(games.get(i), i));
		}
	}

	@FXML
	private void initialize() {
		ToggleGroup group = new ToggleGroup();
		killSorter.setToggleGroup(group);
		difficultySorter.setToggleGroup(group);
		accuracySorter.setToggleGroup(group);
		group.selectedToggleProperty().addListener((ob, o, n) -> {
			RadioButton radioButton = (RadioButton) group.getSelectedToggle();
			show(RankingController.sortGames(radioButton.getText()));
		});
		killSorter.setSelected(true);
	}

	public void back(ActionEvent actionEvent) {
		MainMenuController.start();
	}
}
