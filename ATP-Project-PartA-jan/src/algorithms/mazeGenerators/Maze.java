package algorithms.mazeGenerators;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Maze {

    final int rows;
    final int cols;
    final Position p_start;
    final Position p_end;
    private int [][] maze;

    public Maze(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        maze = new int[rows][cols];
        Position p_end1;
        p_start = pick_rand_start_end();  //start position
        p_end1 = pick_rand_start_end(); //goal position
        while (p_start.getRowIndex() == p_end1.getRowIndex() && p_start.getColumnIndex() == p_end1.getColumnIndex()){
            p_end1 = pick_rand_start_end();
        }

        p_end = p_end1;
        if (rows <= 1 && cols <= 1){
            try {
                throw new Exception();
            }
            catch (Exception e){
                System.out.println("you have entered an invalid maze, please enter a valid one again");
            }
        }
    }

    private Position pick_rand_start_end(){
        Random rand_x = new Random();
        int int_rand_x = rand_x.nextInt(rows);
        Random rand_y = new Random();
        int int_rand_y = rand_y.nextInt(cols);

        return new Position(int_rand_x, int_rand_y);
    }

    public Position getStartPosition(){
        return this.p_start;
    }

    public Position getGoalPosition(){
        return this.p_end;
    }

    public void setMaze(int x, int y, int change_to) {
        this.maze[x][y] = change_to;
    }

    public void print(){
        for (int i = 0 ; i < rows ; i++){
            System.out.print("[");
            for (int j = 0 ; j < cols ; j++){
                if (j == cols-1){
                    if (i == p_start.getRowIndex() && j == p_start.getColumnIndex()){
                        System.out.print("S");
                    }
                    else if (i == p_end.getRowIndex() && j == p_end.getColumnIndex()){
                        System.out.print("E");
                    }
                    else{
                        System.out.print(maze[i][j]);
                    }
                }
                else{
                    if (i == p_start.getRowIndex() && j == p_start.getColumnIndex()){
                        System.out.print("S, ");
                    }
                    else if (i == p_end.getRowIndex() && j == p_end.getColumnIndex()){
                        System.out.print("E, ");
                    }
                    else{
                        System.out.print(maze[i][j] + ", ");
                    }
                }
            }
            System.out.println("]");
        }
    }
}
