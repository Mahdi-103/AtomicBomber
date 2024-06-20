module graphic {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;

    exports view;
    opens view to javafx.fxml;
	exports view.signing;
	opens view.signing to javafx.fxml;
	exports view.main;
	opens view.main to javafx.fxml;
}