package algorithms.mazeGenerators;

public class EmptyMazeGenerator extends AMazeGenerator{

    public Maze generate(int row, int col){
        Maze m = new Maze(row, col);
        return m;
    }

}


