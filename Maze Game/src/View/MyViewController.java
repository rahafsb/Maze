package View;
import Model.IModel;
import Model.MyModel;
import algorithms.mazeGenerators.Maze;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import ViewModel.MyViewModel;

import javafx.scene.control.TextField;

import java.net.URL;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;

public class MyViewController implements Initializable, IView, Observer {

    public MyViewModel myViewModel;

    @FXML
    public MazeDisplayer mazeDisplayer;

    @FXML
    public TextField textField_mazeRows;

    @FXML
    public TextField textField_mazeCoulmns;

    private Maze maze;

    @FXML
    private MediaView mediaView;

    private MediaPlayer mediaPlayer;

    private static boolean musicStatus = true;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
//        Media media = new Media(new File("resources/intro.mp4").toURI().toString());
//        mediaPlayer = new MediaPlayer(media);
//        mediaView.setMediaPlayer(mediaPlayer);
//        mediaPlayer.play();
//        mediaPlayer.setOnEndOfMedia(() -> {
//            mediaView.setVisible(false);
//        });
//
//        Media music = new Media(new File("resources/music.mp3").toURI().toString());
//        mediaPlayer = new MediaPlayer(music);
//        mediaPlayer.setAutoPlay(musicStatus);
//        mediaPlayer.setOnEndOfMedia(new Runnable() {
//            @Override
//            public void run() {
//                mediaPlayer.seek(Duration.ZERO);
//            }
//        });
    }


    StringProperty updatePlayerRow = new SimpleStringProperty();
    StringProperty updatePlayerCol = new SimpleStringProperty();

    public String getUpdatePlayerRow() {
        return updatePlayerRow.get();
    }

    public void setUpdatePlayerRow(int updatePlayerRow) {
        this.updatePlayerRow.set(updatePlayerRow + "");
    }

    public String getUpdatePlayerCol() {
        return updatePlayerCol.get();
    }

    public void setUpdatePlayerCol(int updatePlayerCol) {
        this.updatePlayerCol.set(updatePlayerCol + "");
    }





    public void generateMaze(ActionEvent actionEvent) {
        if (mazeDisplayer == null || this.myViewModel == null){
            IModel iModel = new MyModel();
            this.myViewModel = new MyViewModel(iModel);
        }
        try{
            int rows = Integer.parseInt(textField_mazeRows.getText());
            int cols = Integer.parseInt(textField_mazeCoulmns.getText());
            myViewModel.generateMaze(rows, cols);
            this.maze = myViewModel.getMaze();
//            this.maze.print();
        }
        catch (NumberFormatException e){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Illegal input");
            alert.setContentText("please insert positive numbers for both row and column");
            alert.show();
        }


        mazeDisplayer.drawMaze(this.maze);
        mazeDisplayer.requestFocus();
    }

    public void keyPressed(KeyEvent keyEvent) {
//        int row = mazeDisplayer.getPlayer().getRowIndex();
//        int col = mazeDisplayer.getPlayer().getColumnIndex();
        String direction;

        switch (keyEvent.getCode()) {
            case NUMPAD1,DIGIT1 -> direction = "DOWNLEFT";
            case NUMPAD2,DIGIT2,DOWN -> direction = "DOWN";
            case NUMPAD3,DIGIT3 -> direction = "DOWNRIGHT";
            case NUMPAD4,DIGIT4,LEFT -> direction = "LEFT";
            case NUMPAD6,DIGIT6,RIGHT -> direction = "RIGHT";
            case NUMPAD7,DIGIT7 -> direction = "UPLEFT";
            case NUMPAD8,DIGIT8,UP -> direction = "UP";
            case NUMPAD9,DIGIT9 -> direction = "UPRIGHT";
            default -> {return;}
        }
        this.myViewModel.movePlayer(direction);
        keyEvent.consume();
    }






    @Override
    public void saveGame(ActionEvent actionEvent) {

    }

    @Override
    public void loadGame(ActionEvent actionEvent) {

    }

    @Override
    public void properties(ActionEvent actionEvent) {

    }

    @Override
    public void help(MouseEvent mouseEvent) {

    }

    @Override
    public void about(MouseEvent mouseEvent) {

    }

    @Override
    public void exit() {

    }

    @Override
    public void muteUnmute(ActionEvent actionEvent) {

    }


    @Override
    public void update(Observable o, Object arg) {
        if(o instanceof MyViewModel) {
            String change = (String)arg;
            switch (change) {
//                case "maze generated"-> mazeGenerated();
                case "player moved" -> mazeDisplayer.setPosition(myViewModel.getPlayerPosition().getRowIndex(), myViewModel.getPlayerPosition().getColumnIndex());
//                case "maze solved" -> mazeSolved();
//                case "goal reached" ->goalReached();
            }
        }
    }
}
