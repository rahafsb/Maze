package algorithms.mazeGenerators;

public abstract class AMazeGenerator implements IMazeGenerator{

    public long measureAlgorithmTimeMillis(int row, int col){
        long first = System.currentTimeMillis();
        generate(row, col);
        long sec = System.currentTimeMillis();
        return sec - first;
    }
}

