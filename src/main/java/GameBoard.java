import javafx.animation.PauseTransition;
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
import javafx.util.Duration;

import java.util.*;

import javafx.animation.PauseTransition;
import javafx.util.Duration;

public class GameBoard extends GridPane {

    private Button[][] buttons;
    private Integer[][] positions;
    private Integer[][] winningpositions;

    Node currentButton;
    Integer currentPosition;
    double width;
    double height;

    private ImageView winningPicture;

    private int bounds;
    private List<String> imageUrls;
    private int currentImageIndex = 0;

    private Image image;
    private String mode;

    private final PauseTransition pauseTransition = new PauseTransition(Duration.seconds(1));

    public GameBoard(Integer bounds, String mode, Image customImage) {
        this.mode = mode;
        this.bounds = bounds;
        this.image = customImage;

        switch (mode) {
            case "windWaker" -> imageUrls = new ArrayList<String>() {{
                add("Zill1.png");
                add("Aryll2.png");
                add("Tingle3.png");
                add("Salvatore4.png");
                add("Tetra5.png");
                add("Makar6.png");
                add("Medli7.png");
                add("Lions8.png");
                add("Orca9.png");
                add("Grandmother10.png");
                add("Bandam11.png");
                add("Quill12.png");
                add("Niko13.png");
                add("Beedle14.png");
                add("Moblin15.png");
                add("Helmaroc_King16.png");
            }};
            case "birds" -> {
                imageUrls = new ArrayList<>();
                imageUrls.add("bird.png");
            }
            case "numbers" -> imageUrls = new ArrayList<>();
            case "custom" -> System.out.println("custom mode");
        }

        buttons = new Button[bounds][bounds];
        positions = new Integer[bounds][bounds];
        winningpositions = new Integer[bounds][bounds];

        this.setHgap(5);
        this.setVgap(5);

        if(mode.equals("numbers")) {
            createNumberBoard();
        } else {
            createBoard();
        }

        shufflePieces();
        registerListeners();

        setOnKeyPressed(this::handleKeyPress);
        setFocusTraversable(true);
    }

    private void createBoard() {
        Image image = new Image(imageUrls.get(currentImageIndex));
        double pieceWidth = image.getWidth() / bounds;
        double pieceHeight = image.getHeight() / bounds;

        int count = 1;
        int position = 0;
        for (int row = 0; row < bounds; row++) {
            for (int col = 0; col < bounds; col++) {
                positions[row][col] = position;
                winningpositions[row][col] = position;
                Button button = new Button();

                if(count == bounds && mode.equals("windWaker")) {
                    winningPicture = new ImageView(image);
                    createPuzzlePiece(pieceWidth, pieceHeight, row, col, winningPicture);

                    button.setGraphic(null);
                    button.setPrefSize(image.getWidth() / bounds, image.getHeight() / bounds);
                    button.setMaxSize(150, 150);
                } else if (count == bounds * bounds && !mode.equals("windWaker")) {
                    winningPicture = new ImageView(image);
                    createPuzzlePiece(pieceWidth, pieceHeight, row, col, winningPicture);

                    button.setGraphic(null);
                    button.setPrefSize(image.getWidth() / bounds, image.getHeight() / bounds);
                    button.setMaxSize(150, 150);
                } else {
                    ImageView imageView = new ImageView(image);
                    createPuzzlePiece(pieceWidth, pieceHeight, row, col, imageView);

                    button.setGraphic(imageView);
                }

                buttons[row][col] = button;
                button.setPadding(Insets.EMPTY);
                this.add(button, col, row);
                this.setAlignment(Pos.CENTER);
                count++;
                position++;
            }
        }
    }

    private void createNumberBoard() {
        int count = 1;
        int position = 0;
        for (int row = 0; row < bounds; row++) {
            for (int col = 0; col < bounds; col++) {
                positions[row][col] = position;
                winningpositions[row][col] = position;
                Button button = new Button();

                //need to add the check if empty
                if (count == bounds * bounds) {
                    button.setText(null);
                } else {
                    button.setText(String.valueOf(count));
                }

                buttons[row][col] = button;
                buttons[row][col].setPrefSize(50, 50);
                this.add(button, col, row);
                this.setAlignment(Pos.CENTER);
                count++;
                position++;
            }
        }
    }

    private void createPuzzlePiece(double pieceWidth, double pieceHeight, int row, int col, ImageView winningPicture) {
        winningPicture.setViewport(new javafx.geometry.Rectangle2D(col * pieceWidth, row * pieceHeight, pieceWidth, pieceHeight));

        winningPicture.setFitWidth(150);
        winningPicture.setFitHeight(150);
        winningPicture.setPreserveRatio(false);
        winningPicture.setSmooth(true);
        winningPicture.setCache(true);
    }

    private void shufflePieces() {
        Random random = new Random();

        for (int i = bounds * bounds - 2; i >= 0; i--) {
            int row1 = i / bounds;
            int col1 = i % bounds;

            int row2 = random.nextInt(row1 + 1);
            int col2 = random.nextInt(col1 + 1);

            Button temp = buttons[row1][col1];
            buttons[row1][col1] = buttons[row2][col2];
            buttons[row2][col2] = temp;

            int temp2 = positions[row1][col1];
            positions[row1][col1] = positions[row2][col2];
            positions[row2][col2] = temp2;
        }

        redrawGrid();
    }

    private void redrawGrid() {
        this.getChildren().clear();

        for (int row = 0; row < bounds; row++) {
            for (int col = 0; col < bounds; col++) {
                this.add(buttons[row][col], col, row);
            }
        }
    }

    private void registerListeners() {
        // Register each button in the grid
        for (int row = 0; row < bounds; row++) {
            for (int col = 0; col < bounds; col++) {
                buttons[row][col].setOnAction(new ButtonClick(row, col));
            }
        }
    }

    private class ButtonClick implements EventHandler<ActionEvent> {
        private int row;
        private int col;

        public ButtonClick(int row, int col) {
            this.row = row;
            this.col = col;
        }

        @Override
        public void handle(ActionEvent event) {
            String currentButtonString = null;
            Node currentButton = null;

            if(mode.equals("numbers")) {
                currentButtonString = buttons[row][col].getText();
            } else {
                currentButton = buttons[row][col].getGraphic();
            }

            Integer currentPosition = positions[row][col];

            double width = buttons[row][col].getWidth();
            double height = buttons[row][col].getHeight();

            if (isEmpty(row, col, 0, 1)) {
                if(mode.equals("numbers")) {
                    buttons[row][col + 1].setText(currentButtonString);
                    buttons[row][col].setText(null);
                    buttons[row][col].setPrefSize(width, height);
                } else {
                    buttons[row][col + 1].setGraphic(currentButton);
                    buttons[row][col].setGraphic(null);
                    buttons[row][col].setPrefSize(width, height);
                }
                int temp = positions[row][col + 1];
                positions[row][col + 1] = currentPosition;
                positions[row][col] = temp;
                checkWin();
            } else if (isEmpty(row, col, 0, -1)) {
                if(mode.equals("numbers")) {
                    buttons[row][col - 1].setText(currentButtonString);
                    buttons[row][col].setText(null);
                    buttons[row][col].setPrefSize(width, height);
                } else {
                    buttons[row][col - 1].setGraphic(currentButton);
                    buttons[row][col].setGraphic(null);
                    buttons[row][col].setPrefSize(width, height);
                }
                int temp = positions[row][col - 1];
                positions[row][col - 1] = currentPosition;
                positions[row][col] = temp;
                checkWin();
            } else if (isEmpty(row, col, -1, 0)) {
                if(mode.equals("numbers")) {
                    buttons[row - 1][col].setText(currentButtonString);
                    buttons[row][col].setText(null);
                    buttons[row][col].setPrefSize(width, height);
                } else {
                    buttons[row - 1][col].setGraphic(currentButton);
                    buttons[row][col].setGraphic(null);
                    buttons[row][col].setPrefSize(width, height);
                }
                int temp = positions[row - 1][col];
                positions[row - 1][col] = currentPosition;
                positions[row][col] = temp;
                checkWin();
            } else if (isEmpty(row, col, 1, 0)) {
                if(mode.equals("numbers")) {
                    buttons[row + 1][col].setText(currentButtonString);
                    buttons[row][col].setText(null);
                    buttons[row][col].setPrefSize(width, height);
                } else {
                    buttons[row + 1][col].setGraphic(currentButton);
                    buttons[row][col].setGraphic(null);
                    buttons[row][col].setPrefSize(width, height);
                }
                int temp = positions[row + 1][col];
                positions[row + 1][col] = currentPosition;
                positions[row][col] = temp;
                checkWin();
            }
        }
    }

    private void handleKeyPress(KeyEvent e) {
        KeyCode keyCode = e.getCode();
        int blankRow = 0;
        int blankCol = 0;

        for (int row = 0; row < bounds; row++) {
            for (int col = 0; col < bounds; col++) {
                Node graphic = buttons[row][col].getGraphic();
                String text = buttons[row][col].getText();
                if(mode.equals("numbers")) {
                    if(text == null) {
                        blankRow = row;
                        blankCol = col;
                    }
                } else if (graphic == null) {
                    blankRow = row;
                    blankCol = col;
                }
            }
        }

        double width = buttons[blankRow][blankCol].getWidth();
        double height = buttons[blankRow][blankCol].getHeight();

        int newRow = blankRow;
        int newCol = blankCol;

        if (keyCode == KeyCode.D || keyCode == KeyCode.KP_RIGHT) {
            newCol = blankCol - 1;
        } else if (keyCode == KeyCode.A || keyCode == KeyCode.KP_LEFT) {
            newCol = blankCol + 1;
        } else if (keyCode == KeyCode.W || keyCode == KeyCode.KP_UP) {
            newRow = blankRow + 1;
        } else if (keyCode == KeyCode.S || keyCode == KeyCode.KP_DOWN) {
            newRow = blankRow - 1;
        }

        if (isValidMove(newRow, newCol)) {
            if (mode.equals("numbers")) {
                String string = buttons[newRow][newCol].getText();
                System.out.println(string);
                buttons[blankRow][blankCol].setText(string);
                buttons[newRow][newCol].setText(null);
                buttons[newRow][newCol].setPrefSize(width, height);
            } else {
                Node newImage = buttons[newRow][newCol].getGraphic();
                buttons[blankRow][blankCol].setGraphic(newImage);
                buttons[newRow][newCol].setGraphic(null);
                buttons[newRow][newCol].setPrefSize(width, height);
            }

            int currentPosition = positions[blankRow][blankCol];
            int temp = positions[newRow][newCol];
            positions[newRow][newCol] = currentPosition;
            positions[blankRow][blankCol] = temp;

            checkWin();
        }
    }

    private boolean isValidMove(int row, int col) {
        return row >= 0 && row < bounds && col >= 0 && col < bounds;
    }

    private void movePiece(int row, int col, int rowChange, int colChange) {
        buttons[row + rowChange][col + colChange].setGraphic(currentButton);
        buttons[row][col].setGraphic(null);
        buttons[row][col].setPrefSize(width, height);

        int temp = positions[row + rowChange][col + colChange];
        positions[row + rowChange][col + colChange] = currentPosition;
        positions[row][col] = temp;
        checkWin();
    }

    private boolean isEmpty(int row, int col, int rowChange, int colChange) {
        int newRow = row + rowChange;
        int newCol = col + colChange;

        if (newRow < 0 || newRow >= bounds) {
            return false;
        }
        if (newCol < 0 || newCol >= bounds) {
            return false;
        }

        return buttons[newRow][newCol].getGraphic() == null && buttons[newRow][newCol].getText() == null;
    }

    private boolean isWin() {
        for (int i = 0; i < positions.length; i++) {
            for (int j = 0; j < winningpositions[i].length; j++) {
                if (!Objects.equals(positions[i][j], winningpositions[i][j])) {
                    return false;
                }
            }
        }
        return true;
    }

    private void checkWin() {
        if (isWin()) {
            if (mode.equals("windWaker")) {
                if (currentImageIndex < imageUrls.size() - 1) {
                    currentImageIndex++;
                    buttons[0][3].setGraphic(winningPicture); //animate it eventually
                    pauseTransition.setOnFinished(event -> {
                        createBoard();
                        shufflePieces();
                        redrawGrid();
                    });
                    pauseTransition.play();
                } else {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Congratulations!");
                    alert.setHeaderText("You have won!");
                    alert.setContentText("You completed the game. Well done!");
                    alert.showAndWait();
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Congratulations!");
                alert.setHeaderText("You have won!");
                alert.setContentText("You completed the game. Well done!");
                alert.showAndWait();
            }
        }
    }
}
