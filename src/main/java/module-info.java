module group47.cs2212.petgame {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;

    opens group47.cs2212.petgame to javafx.fxml;
    exports group47.cs2212.petgame;
}
