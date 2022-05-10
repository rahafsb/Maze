package algorithms.mazeGenerators;

public class EmptyMazeGenerator extends AMazeGenerator{

    public Maze generate(int row, int col){
        if (row <= 1 && col <= 1){
            try {
                throw new Exception();
            }
            catch (Exception e){
                System.out.println("you have entered an invalid maze, please enter a valid one again");
            }
        }
        Maze m = new Maze(row, col);
        return m;
    }
//    public static void main(String[] args) {
//        EmptyMazeGenerator smg = new EmptyMazeGenerator();
//        Maze m = smg.generate(10, 10);
//    }
}


