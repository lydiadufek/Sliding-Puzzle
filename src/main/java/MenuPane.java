import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.awt.*;

public class MenuPane extends BorderPane {

//    private GameBoard gameBoard;

    private Button windWaker;
    private Button birds;
    private Button numbers;
    private Button custom;
    private Button startButton;

    int bounds;
    private String mode;

    //different modes now

    //windwaker --> 4X4 and preset images
    //birds or something
    //numbers
    //custom --> choose image and bounds
    public MenuPane() {
        this.setPrefHeight(600);
        this.setPrefWidth(500);

        initializePanel();
        handleListeners();
    }

    private void initializePanel() {
        mode = "";
        Label winningLabel = new Label("Sliding Puzzle Game Yippee!");
        Label difficultyLabel = new Label("Choose Your Mode");
        Label startLabel = new Label("Start The Game!");

        windWaker = new Button("Wind Waker Mode");
        birds = new Button("Birds");
        numbers = new Button("Number Mode");
        custom = new Button("Custom Mode");

        windWaker.setFont(Font.font("Monospace", 20));
        birds.setFont(Font.font("Monospace", 20));
        numbers.setFont(Font.font("Monospace", 20));
        custom.setFont(Font.font("Monospace", 20));

        Label chooseImageLabel = new Label("Choose Image:");

        startButton = new Button("Start Game");
        startButton.setFont(Font.font("Monospace", 20));

        // Create a ChoiceBox for selecting an image
        ChoiceBox<String> imageChoiceBox = new ChoiceBox<>();
        imageChoiceBox.getItems().addAll("Image 1", "Image 2", "Image 3"); // Add image choices

        HBox winningLabelBox = new HBox();
        winningLabel.setFont(Font.font("Monospace", 20));
        winningLabelBox.getChildren().add(winningLabel);
        winningLabelBox.setAlignment(Pos.CENTER);

        HBox emptyBox = new HBox();
        emptyBox.getChildren().add(new Label(" "));
        emptyBox.setAlignment(Pos.CENTER);

        HBox difficultyLabelBox = new HBox();
        difficultyLabel.setFont(Font.font("Monospace", 20));
        difficultyLabelBox.getChildren().add(difficultyLabel);
        difficultyLabelBox.setAlignment(Pos.CENTER);

        HBox startLabelBox = new HBox();
        startLabel.setFont(Font.font("Monospace", 20));
        startLabelBox.getChildren().add(startLabel);
        startLabelBox.setAlignment(Pos.CENTER);

        HBox startButtonBox = new HBox();
        startButtonBox.getChildren().add(startButton);
        startButtonBox.setAlignment(Pos.CENTER);

        HBox chooseImageBox = new HBox();
        chooseImageBox.getChildren().addAll(chooseImageLabel, imageChoiceBox);
        chooseImageBox.setAlignment(Pos.CENTER);

        VBox buttonVBox = new VBox(10);
        buttonVBox.setAlignment(Pos.CENTER);
        buttonVBox.getChildren().addAll(windWaker, birds, numbers, custom);

        VBox labelAndButtonVBox = new VBox(20);
        labelAndButtonVBox.setAlignment(Pos.CENTER);
        labelAndButtonVBox.getChildren().addAll(
                winningLabelBox, emptyBox, difficultyLabelBox, buttonVBox, startLabelBox, startButtonBox, chooseImageBox
        );

        this.setCenter(labelAndButtonVBox);
    }

    private void handleListeners() {
        windWaker.setOnAction(event -> {
            mode = "windWaker";
            bounds = 4;
        });

        birds.setOnAction(event -> {
            mode = "birds";
            bounds = 6;
        });

        numbers.setOnAction(event -> {
            mode = "numbers";
            bounds = 5;
        });

        custom.setOnAction(event -> {
            mode = "custom";
            bounds = 11;
        });

        startButton.setOnAction(event -> {
            GameBoard gameBoard = new GameBoard(bounds, mode, null);

            Screen screen = Screen.getPrimary();

            // Get the visual bounds of the primary screen
            double screenWidth = screen.getVisualBounds().getWidth();
            double screenHeight = screen.getVisualBounds().getHeight();

            Scene gameScene = new Scene(gameBoard, screenWidth, screenHeight);
            Stage currentStage = (Stage) getScene().getWindow();
            currentStage.setScene(gameScene);
            currentStage.setMaximized(true);
        });
    }

    public int getBounds() {
        return bounds;
    }
}
