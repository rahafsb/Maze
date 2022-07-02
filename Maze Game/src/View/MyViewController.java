package View;
import Model.IModel;
import Model.MyModel;
import algorithms.mazeGenerators.Maze;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import ViewModel.MyViewModel;

import javafx.scene.canvas.Canvas;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class MyViewController implements Initializable, IView {

    public MyViewModel myViewModel;

    @FXML
    public MazeDisplayer mazeDisplayer;


    private Maze maze;


    @FXML
    private MediaView mediaView;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
//        Media media = new Media(new File("resources/intro.mp4").toURI().toString());
//        MediaPlayer mediaPlayer = new MediaPlayer(media);
//        mediaView.setMediaPlayer(mediaPlayer);
//        mediaPlayer.play();
//        mediaPlayer.setOnEndOfMedia(() -> {
//            mediaView.setVisible(false);
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
            this.mazeDisplayer = new MazeDisplayer();
        }
        int rows = 50;
        int cols = 50;
        myViewModel.generateMaze(rows, cols);
        this.maze = myViewModel.getMaze();

        mazeDisplayer.drawMaze(this.maze);
//        mazeDisplayer.requestFocus();
    }

    public void keyPressed(KeyEvent keyEvent) {
        int row = mazeDisplayer.getPlayer().getRowIndex();
        int col = mazeDisplayer.getPlayer().getColumnIndex();

        switch (keyEvent.getCode()) {
            case UP -> row -= 1;
            case DOWN -> row += 1;
            case RIGHT -> col += 1;
            case LEFT -> col -= 1;
        }

        mazeDisplayer.setPosition(row, col);

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


}
