package algorithms.mazeGenerators;

public abstract class AMazeGenerator implements IMazeGenerator{
    public AMazeGenerator() {}
    public long measureAlgorithmTimeMillis(int row, int col){
        long first = System.currentTimeMillis();
        this.generate(row, col);
        long sec = System.currentTimeMillis();
        return sec - first;
    }
}

