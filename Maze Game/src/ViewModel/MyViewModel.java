package ViewModel;

import Model.IModel;
import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.input.KeyCode;

import java.io.File;
import java.util.Observer;

public class MyViewModel {
    private IModel model;
    private Position player;
    public StringProperty player_x = new SimpleStringProperty();
    public StringProperty player_y = new SimpleStringProperty();

    public MyViewModel(IModel model){
        this.model = model;
    }

    public void generateMaze(int row, int col){
        model.generateMaze(row, col);
    }

    public void updateRowCol(){
        player.setY(this.model.getPlayerPosition().getColumnIndex());
        player.setX(this.model.getPlayerPosition().getRowIndex());
        player_x.set(player.getRowIndex()+"");
        player_y.set(player.getColumnIndex()+"");
    }

    public void setPlayer_x(Integer x){
        player_x.setValue(x.toString());
    }

    public void setPlayer_y(Integer y){
        player_y.setValue(y.toString());
    }

    public Maze getMaze(){
        return this.model.getMaze();
    }

    public void stopServers(){
        this.model.serversStop();
    }

    public void soleMaze(){
        this.model.solveMaze();
    }

    public void movePlayer(KeyCode mover){
        this.model.playerMoveLogic(mover);
    }

    public Position getPlayerPosition(){
        return this.model.getPlayerPosition();
    }

    public Solution getSolution(){
        return this.model.returnSolution();
    }

    public void saveGame(File file){
        this.model.saveMaze(file);
    }

    public void loadGame(File file){
        this.model.loadMaze(file);
    }
}
