import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.util.Arrays;
import java.util.Objects;
import java.util.Random;

public class PuzzleGUI extends Application {

    private MenuPane menuPane;

    //add menu
    //add different modes
    //clean up code
    //add the menu
    //Puzzle GUI should just call menuPane

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Image Puzzle");
        menuPane = new MenuPane();
        Scene scene = new Scene(menuPane, 300, 300);

        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setMaximized(true);
    }
}