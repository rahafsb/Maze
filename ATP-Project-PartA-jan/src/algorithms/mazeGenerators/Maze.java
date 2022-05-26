package algorithms.mazeGenerators;

import java.util.Random;

public class Maze {
    private int rows;
    private int cols;
    private Position p_start;
    private Position p_end;
    private int[][] maze;

    public Maze(int rows, int cols) {
        if (rows < -1) {
            rows = -1 * rows;
        }

        if (cols < -1) {
            cols = -1 * cols;
        }

        if (rows == 0 || rows == 1 || rows == -1) {
            rows = 2;
        }

        if (cols == 0 || cols == 1 || cols == -1) {
            cols = 2;
        }

        this.rows = rows;
        this.cols = cols;
        this.maze = new int[rows][cols];
        this.p_start = this.pick_rand_start_end(); /////////////////////////////////

        Position p_end1;
        p_end1 = pick_rand_start_end(); //goal position
        while (p_start.getRowIndex() == p_end1.getRowIndex() && p_start.getColumnIndex() == p_end1.getColumnIndex()){
            p_end1 = pick_rand_start_end();
        }

        this.p_end = p_end1;
    }

    public int[] get_rows_cols() {
        return new int[]{this.rows, this.cols};
    }

    public int zero_one() {
        Random rand_st = new Random();
        return rand_st.nextInt(2);
    }

    private Position pick_rand_start_end() {
        int x = this.zero_one();
        int int_rand_x;
        int int_rand_y;
        Random rand_x;
        Random rand_y;
        int y;
        rand_x = new Random();
        if (x == 0) {
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

    public Position getStartPosition() {
        return this.p_start;
    }

    public Position getGoalPosition() {
        return this.p_end;
    }

    public void setMaze(int x, int y, int change_to) {
        this.maze[x][y] = change_to;
    }

    public int get_value(int x, int y) {
        return x < this.rows && x >= 0 && y < this.cols && y >= 0 ? this.maze[x][y] : -1;
    }

    public void print() {
        for(int i = 0; i < this.rows; ++i) {
            System.out.print("[");

            for(int j = 0; j < this.cols; ++j) {
                if (j == this.cols - 1) {
                    if (i == this.p_start.getRowIndex() && j == this.p_start.getColumnIndex()) {
                        System.out.print("S");
                    } else if (i == this.p_end.getRowIndex() && j == this.p_end.getColumnIndex()) {
                        System.out.print("E");
                    } else {
                        System.out.print(this.maze[i][j]);
                    }
                } else if (i == this.p_start.getRowIndex() && j == this.p_start.getColumnIndex()) {
                    System.out.print("S, ");
                } else if (i == this.p_end.getRowIndex() && j == this.p_end.getColumnIndex()) {
                    System.out.print("E, ");
                } else {
                    System.out.print(this.maze[i][j] + ", ");
                }
            }

            System.out.println("]");
        }

    }
}
