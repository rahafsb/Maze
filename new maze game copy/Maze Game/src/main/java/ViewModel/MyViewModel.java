package ViewModel;

import Model.IModel;
import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;

import java.io.File;
import java.util.Observable;
import java.util.Observer;

public class MyViewModel extends Observable implements Observer {
    private IModel model;

    public MyViewModel(IModel model){
        this.model = model;
        this.model.assignObserver(this);
    }

    public void generateMaze(int row, int col){
        model.generateMaze(row, col);
    }

    public Maze getMaze(){
        return this.model.getMaze();
    }

    public void soleMaze(){
        this.model.solveMaze();
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


    @Override
    public void update(Observable o, Object arg) {
        if(o instanceof IModel)
        {
            setChanged();
            notifyObservers(arg);
        }
    }

    public void movePlayer(String s){
        model.updatePlayerPosition(s);
    }

    public void movePlayerMouse(int newPlayerRow, int newPlayerCol, int rowIndex, int columnIndex) {
        String direction;
        int row = newPlayerRow - rowIndex;
        int col = newPlayerCol-columnIndex;
        if(row > 0){
            if (col < 0){
                direction = "DOWNLEFT";
            }
            else if (col == 0){
                direction = "DOWN";
            }
            else{
                direction = "DOWNRIGHT";
            }
        }
        else if (row == 0){
            if (col > 0){
                direction = "RIGHT";
            } else if (col < 0) {
                direction = "LEFT";
            }
            else{
                return;
            }
        }
        else{
            if (col > 0){
                direction = "UPRIGHT";
            }
            else if (col < 0){
                direction = "UPLEFT";
            }
            else{
                direction = "UP";
            }
        }
        model.updatePlayerPosition(direction);
    }

    public void exit(){
        this.model.exit();
    }
}
