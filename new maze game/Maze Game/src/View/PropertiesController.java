package View;

import Server.Configurations;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class PropertiesController implements Initializable {
    @FXML
    private TextField threadNum;
    @FXML
    private ChoiceBox<String> ChoiceBox1;

    @FXML
    private ChoiceBox<String> ChoiceBox2;

    private String[] generateList = {"Empty Maze Algorithm", "Simple Maze Algorithm", "Prim Algorithm"};
    private String[] solveList = {"Breadth First Search", "Depth First Search", "Best First Search"};

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ChoiceBox1.getItems().addAll(generateList);
        ChoiceBox2.getItems().addAll(solveList);
    }

    public String[] getProperties(){
        String[] properties = new String[3];
        boolean result = threadNum.getText().matches("[0-9]+");
        if (threadNum.getText() == null || ChoiceBox1.getValue() == null || ChoiceBox2.getValue() == null){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Illegal input");
            alert.setContentText("one of the properties is null, please enter all properties");
            alert.show();
            return null;
        }
        if (!result){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Illegal input");
            alert.setContentText("number of threads must be an integer and greater than 0");
            alert.show();
            return null;
        }
        if (Integer.parseInt(threadNum.getText()) < 1){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Illegal input");
            alert.setContentText("number of threads must be greater than 0");
            alert.show();
            return null;
        }
        String p = threadNum.getText();
        properties[0] = p;
        properties[1] = ChoiceBox1.getValue();
        properties[2] = ChoiceBox2.getValue();
        return properties;
    }

    public void saveProperties(ActionEvent actionEvent) throws IOException {
        String[] properties = getProperties();
        if (properties == null){
            return;
        }
        String generate = "";
        String solve = "";
        switch(ChoiceBox1.getValue()){
            case("Empty Maze Algorithm") -> generate = "EmptyMazeGenerator";
            case("Simple Maze Algorithm") -> generate = "SimpleMazeGenerator";
            case("Prim Algorithm") -> generate = "MyMazeGenerator";
        }
        switch(ChoiceBox2.getValue()){
            case("Breadth First Search") -> solve = "BreadthFirstSearch";
            case("Depth First Search") -> solve = "DepthFirstSearch";
            case("Best First Search") -> solve = "BestFirstSearch";
        }

        Configurations.getInstance();
        Configurations.setNumOfThreads(properties[0]);
        Configurations.setMazeGeneratorMethod(generate);
        Configurations.setMazeSolveMethod(solve);
    }
}
