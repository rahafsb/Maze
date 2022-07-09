package View;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;


import java.util.Objects;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/MyView.fxml")));

        primaryStage.setTitle("Maze Game");
        Scene scene = new Scene(root, 2000, 1400);
        scene.getStylesheets().add(getClass().getResource("/css1.css").toExternalForm());

        Image icon = new Image("images/icon.png");
        primaryStage.getIcons().add(icon);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }


}