package View;
import Model.IModel;
import Model.MyModel;
import algorithms.mazeGenerators.Maze;
import algorithms.search.Solution;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import ViewModel.MyViewModel;

import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;
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

    private boolean dragPlayerBool = false;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Media media = new Media(new File("resources/intro.mp4").toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        mediaView.setMediaPlayer(mediaPlayer);
        mediaPlayer.play();
        mediaPlayer.setOnEndOfMedia(() -> {
            mediaView.setVisible(false);
        });

        Media music = new Media(new File("resources/music.mp3").toURI().toString());
        mediaPlayer = new MediaPlayer(music);
        mediaPlayer.setAutoPlay(musicStatus);
        mediaPlayer.setOnEndOfMedia(new Runnable() {
            @Override
            public void run() {
                mediaPlayer.seek(Duration.ZERO);
            }
        });
    }

    public void generateMaze(ActionEvent actionEvent) {
        if (mazeDisplayer == null || this.myViewModel == null){
            IModel iModel = new MyModel();
            this.myViewModel = new MyViewModel(iModel);
            this.myViewModel.addObserver(this);
        }
        try{
            int rows = Integer.parseInt(textField_mazeRows.getText());
            int cols = Integer.parseInt(textField_mazeCoulmns.getText());
            if (rows < 3 || cols < 3){
                NumberFormatException nfe = new NumberFormatException();
                throw nfe;
            }
            myViewModel.generateMaze(rows, cols);
            this.maze = myViewModel.getMaze();
        }
        catch (NumberFormatException e){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Illegal input");
            alert.setContentText("please insert positive numbers and bigger than 1 for both rows and columns");
            alert.show();
        }


        mazeDisplayer.drawMaze(this.maze);
        mazeDisplayer.requestFocus();

        if (!musicStatus){
            mediaPlayer.play();
            musicStatus = !musicStatus;
        }
    }

    public void solveMaze(ActionEvent actionEvent) {
        if (myViewModel == null){
            return;
        }
        myViewModel.soleMaze();
        mazeDisplayer.requestFocus();
    }

    public void keyPressed(KeyEvent keyEvent) {
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
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Text Files", "*.txt"));
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            myViewModel.saveGame(selectedFile);
            this.maze = myViewModel.getMaze();
        }
    }

    @Override
    public void loadGame(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Text Files", "*.txt"));
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null){
            if (mazeDisplayer == null || this.myViewModel == null){
                IModel iModel = new MyModel();
                this.myViewModel = new MyViewModel(iModel);
                this.myViewModel.addObserver(this);
            }
            this.myViewModel.loadGame(selectedFile);
            this.maze = myViewModel.getMaze();
            this.mazeDisplayer.drawMaze(this.maze);
        }

    }

    @Override
    public void muteUnmute(ActionEvent actionEvent) {
        if (musicStatus){
            mediaPlayer.pause();
        }
        else{
            mediaPlayer.play();
        }
        musicStatus = !musicStatus;
    }


    @Override
    public void update(Observable o, Object arg) {
        if(o instanceof MyViewModel) {
            String change = (String)arg;
            switch (change) {
                case "player moved" -> mazeDisplayer.setPosition(myViewModel.getPlayerPosition().getRowIndex(), myViewModel.getPlayerPosition().getColumnIndex());
                case "maze solved" -> drawSolution();
                case "goal reached" -> goalReached();
            }
        }
    }

    public void drawSolution(){
        Solution solution = myViewModel.getSolution();
        mazeDisplayer.drawSolution(solution);
    }

    public void goalReached() {
        if (musicStatus){
            mediaPlayer.pause();
            musicStatus = !musicStatus;
        }
        String musicFile = "resources/aychest.mp3";
        AudioClip audioClip = new AudioClip(new File(musicFile).toURI().toString());
        audioClip.play();
    }

    public void dragShip(MouseEvent mouseEvent) {
        if(!dragPlayerBool){
            return;
        }
        double mouseCol = mouseEvent.getX();
        double mouseRow = mouseEvent.getY();
        int newPlayerRow = (int)(mouseRow/mazeDisplayer.getCellHeight());
        int newPlayerCol = (int)(mouseCol/mazeDisplayer.getCellWidth());
        int diffRows = Math.abs(mazeDisplayer.getPlayer().getRowIndex()-newPlayerRow);
        int diffCols = Math.abs(mazeDisplayer.getPlayer().getColumnIndex()-newPlayerCol);
        if(diffRows <= 1 && (diffRows!=0||diffCols!=0)){
            myViewModel.movePlayerMouse(newPlayerRow, newPlayerCol, mazeDisplayer.getPlayer().getRowIndex(), mazeDisplayer.getPlayer().getColumnIndex());
        }
        mouseEvent.consume();
    }

    public void startDrag(MouseEvent mouseEvent) {
        double mouseRow = mouseEvent.getY();
        double mouseCol = mouseEvent.getX();
        int playerRow = (int)(mouseRow/mazeDisplayer.getCellHeight());
        int playerCol = (int)(mouseCol/mazeDisplayer.getCellWidth());
        if(mazeDisplayer.getPlayer().getRowIndex() == playerRow && mazeDisplayer.getPlayer().getColumnIndex() == playerCol){
            dragPlayerBool = true;
        }
    }

    public void stopDrag(MouseEvent mouseEvent) {
        dragPlayerBool=false;
    }

    public void zoomInOut(ScrollEvent scrollEvent) {
        if (this.maze == null){
            return;
        }
        if (scrollEvent.isControlDown()){
            double mazeWidth = mazeDisplayer.getWidth();
            double mazeHeight = mazeDisplayer.getHeight();
            double zoomFactor = 1.05;
            if (scrollEvent.getDeltaY()<0){
                zoomFactor = 0.95;
            }

            if (mazeHeight*mazeWidth > 2800000 && zoomFactor == 1.05){
                return;
            }

            mazeDisplayer.setWidth(mazeWidth*zoomFactor);
            mazeDisplayer.setHeight(mazeHeight*zoomFactor);
            mazeDisplayer.drawMaze(this.maze);
        }
        scrollEvent.consume();
    }

    public void mouseClicked(MouseEvent mouseEvent) {
        this.mazeDisplayer.requestFocus();
    }

    public void newMaze(ActionEvent actionEvent) {
        textField_mazeRows.setText("10");
        textField_mazeCoulmns.setText("10");
        if (mazeDisplayer == null || this.myViewModel == null){
            IModel iModel = new MyModel();
            this.myViewModel = new MyViewModel(iModel);
            this.myViewModel.addObserver(this);
        }
        int rows = 10;
        int cols = 10;

        myViewModel.generateMaze(rows, cols);
        this.maze = myViewModel.getMaze();

        mazeDisplayer.drawMaze(this.maze);
        mazeDisplayer.requestFocus();

        if (!musicStatus){
            mediaPlayer.play();
            musicStatus = !musicStatus;
        }
    }


    public void setProperties(ActionEvent actionEvent) {
        try{
            Stage stage = new Stage();
            stage.setTitle("Properties");

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Properties.fxml"));
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root, 500, 500);
            stage.setScene(scene);
            stage.show();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public void help_i() {
        Alert alrt = new Alert(Alert.AlertType.INFORMATION);
        alrt.setTitle("Info");
        String str = "press ^ or 8 to move upwards\npress v or 2 to move downwards\npress > or 6 to move to the right\npress < or 4 to move to the left\npress 9 to move up-right\npress 7 to move up-left\npress 3 to move dow-right\npress 1 to move down-left\npress ctrl and move the mouse wheel to zoom in/out\npress solve maze to get a hint";
        alrt.setContentText(str);
        alrt.show();
    }

    @Override
    public void aboutGame_i() {
        Alert alrt1 = new Alert(Alert.AlertType.INFORMATION);
        alrt1.setTitle("Info1");
        String str1 = "Find your way to the treasure through the seas!\n" +
                " navigate captain Jack Sparrow's ship on it's voyage to find the treasure hidden in the Rum Runner Isle Island.\n" +
                "Weigh an anchor Matey!!";
        alrt1.setContentText(str1);
        alrt1.show();
    }

    @Override
    public void aboutProducers_i() {
        Alert alrt2 = new Alert(Alert.AlertType.INFORMATION);
        alrt2.setTitle("Info2");
        String str2 = "Contributors\nJohn Zatraa,producers.\nAn undergraduate Studies in the System and Software Engineering Department in Ben-Gurion University.\n" +
                "Rahaf Sbaih,producer\n"+"An undergraduate Studies in the System and Software Engineering Department in Ben-Gurion University.\n";
        alrt2.setContentText(str2);
        alrt2.show();
    }

    @Override
    public void exitMaze_i() {
        if (myViewModel != null){
            myViewModel.exit();
        }
        Platform.exit();
    }


    public void help(ActionEvent actionEvent) {
        help_i();
    }

    public void aboutGame(ActionEvent actionEvent) {
        aboutGame_i();
    }

    public void aboutProducers(ActionEvent actionEvent) {
        aboutProducers_i();
    }

    public void exitMaze(ActionEvent actionEvent) {
        exitMaze_i();
    }
}
