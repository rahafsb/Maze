package Model;

import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;

import java.io.File;
import java.util.Observer;

public interface IModel {
    void assignObserver(Observer o);

    public void generateMaze(int dim_x, int dim_y);
    public void solveMaze();
    public Solution returnSolution();
    public Maze getMaze();
    public void saveMaze(File file);
    public void loadMaze(File file);
    public void serversStop();
    public Position getStartPosition();
    public Position getGoalPosition();
    public Position getPlayerPosition();
    void updatePlayerPosition(String s);
    public void exit();
}