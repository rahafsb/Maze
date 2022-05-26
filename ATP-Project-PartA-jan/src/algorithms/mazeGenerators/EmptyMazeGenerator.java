package algorithms.mazeGenerators;

public class EmptyMazeGenerator extends AMazeGenerator{
    public EmptyMazeGenerator() {}
    public Maze generate(int row, int col) {
        return new Maze(row, col);
    }
}


