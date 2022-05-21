package algorithms.mazeGenerators;

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
    }

    public int[] get_rows_cols() {
        int[] ret = new int[]{this.rows, this.cols};
        return ret;
    }

    public int zero_one() {
        Random rand_st = new Random();
        int int_rand_st = rand_st.nextInt(2);
        return int_rand_st;
    }

    private Position pick_rand_start_end() {
        int x = this.zero_one();
        int int_rand_x;
        int int_rand_y;
        Random rand_x;
        Random rand_y;
        int y;
        if (x == 0) {
            rand_x = new Random();
            int_rand_x = rand_x.nextInt(this.rows);
            if (int_rand_x > 0 && int_rand_x < this.rows - 1) {
                y = this.zero_one();
                if (y == 0) {
                    int_rand_y = 0;
                } else {
                    int_rand_y = this.cols - 1;
                }
            } else {
                rand_y = new Random();
                int_rand_y = rand_y.nextInt(this.cols);
            }
        } else {
            rand_x = new Random();
            int_rand_y = rand_x.nextInt(this.cols);
            if (int_rand_y > 0 && int_rand_y < this.cols - 1) {
                y = this.zero_one();
                if (y == 0) {
                    int_rand_x = 0;
                } else {
                    int_rand_x = this.rows - 1;
                }
            } else {
                rand_y = new Random();
                int_rand_x = rand_y.nextInt(this.rows);
            }
        }

        return new Position(int_rand_x, int_rand_y);
    }

    public Position getStartPosition(){ return this.p_start; }

    public Position getGoalPosition(){
        return this.p_end;
    }

    public void setMaze(int x, int y, int change_to) {
        this.maze[x][y] = change_to;
    }

    public int get_value(int x, int y) {
        return x < this.rows && x >= 0 && y < this.cols && y >= 0 ? this.maze[x][y] : -1;
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
