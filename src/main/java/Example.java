import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Example extends Application {

    private ImageView img;
    private Image image;
    private Scene scene;

    @Override
    public void start(Stage primaryStage) {

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                image = new Image("bird.png");

                img = new ImageView(image);
                double pieceWidth = image.getWidth() / 3;
                double pieceHeight = image.getHeight() / 3;
                img.setViewport(new javafx.geometry.Rectangle2D(col * pieceWidth, row * pieceHeight, pieceWidth, pieceHeight));

                img.setPickOnBounds(true); // allows click on transparent areas
                img.setOnMouseClicked((MouseEvent e) -> {
                    System.out.println("Clicked!"); // change functionality
                });
                scene = new Scene(new StackPane(img));

            }
        }
        primaryStage.setTitle("Image Click Example");
        primaryStage.setScene(scene);
        primaryStage.sizeToScene();
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

//    private void createBoard() {
//        double pieceWidth = image.getWidth() / bounds;
//        double pieceHeight = image.getHeight() / bounds;
//
//        int count = 1;
//        int position = 0;
//        for (int row = 0; row < bounds; row++) {
//            for (int col = 0; col < bounds; col++) {
//                positions[row][col] = position;
//                winningpositions[row][col] = position;
//                Button button = new Button();
//
//                if (count == bounds * bounds) {
//                    button.setGraphic(null);
//                    button.setPrefSize(image.getWidth() / bounds, image.getHeight() / bounds);
//                    button.setMaxSize(150, 150);
//                } else {
//                    ImageView imageView = new ImageView(image);
//                    imageView.setViewport(new javafx.geometry.Rectangle2D(col * pieceWidth, row * pieceHeight, pieceWidth, pieceHeight));
//
//                    imageView.setFitWidth(150);
//                    imageView.setFitHeight(150);
//                    imageView.setPreserveRatio(false);
//                    imageView.setSmooth(true);
//                    imageView.setCache(true);
//
//                    button.setGraphic(imageView);
//                }
//
//                buttons[row][col] = button;
//                button.setPrefSize(image.getWidth() / bounds, image.getHeight() / bounds);
//
//                gridPane.add(button, col, row);
//                gridPane.setAlignment(Pos.CENTER);
//                count++;
//                position++;
//            }
//        }
//    }

}