package algorithms.mazeGenerators;

public class EmptyMazeGenerator extends AMazeGenerator{

    public Maze generate(int row, int col) {
        if (row >= 2 && col >= 2) {
            return new Maze(row, col);
        } else {
            return new Maze(10, 10);
        }
    }

    public static void main(String[] args) {
        EmptyMazeGenerator smg = new EmptyMazeGenerator();
        Maze m = smg.generate(1, 4);
        m.print();
    }
}


