package View;

import Model.IModel;
import Model.MyModel;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


import java.util.Objects;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("MyView.fxml")));

        primaryStage.setTitle("Hello maze");
        Scene scene = new Scene(root, 2000, 1400);
        scene.getStylesheets().add(getClass().getResource("css1.css").toExternalForm());

        primaryStage.setScene(scene);
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }


}