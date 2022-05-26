package algorithms.mazeGenerators;

import java.util.LinkedList;
import java.util.Random;

public class MyMazeGenerator extends AMazeGenerator {
    public MyMazeGenerator() {
    }

    public Maze Make_Walls(int row, int col) {
        Maze ret = new Maze(row, col);

        for(int i = 0; i < row; ++i) {
            for(int j = 0; j < col; ++j) {
                ret.setMaze(i, j, 1);
            }
        }

        return ret;
    }

    public Position[] get_neighbours(Position at_position, Maze at_maze, int num) {
        Position[] ret = new Position[5];

        int limit_row;
        for(limit_row = 0; limit_row < 5; ++limit_row) {
            ret[limit_row] = null;
        }

        limit_row = at_maze.get_rows_cols()[0];
        int limit_col = at_maze.get_rows_cols()[1];
        Position p__2;
        if (at_position.getRowIndex() - num >= 0) {
            p__2 = new Position(at_position.getRowIndex() - num, at_position.getColumnIndex());
            ret[0] = p__2;
        }

        if (at_position.getColumnIndex() - num >= 0) {
            p__2 = new Position(at_position.getRowIndex(), at_position.getColumnIndex() - num);
            ret[1] = p__2;
        }

        if (at_position.getRowIndex() + num < limit_row) {
            p__2 = new Position(at_position.getRowIndex() + num, at_position.getColumnIndex());
            ret[2] = p__2;
        }

        if (at_position.getColumnIndex() + num < limit_col) {
            p__2 = new Position(at_position.getRowIndex(), at_position.getColumnIndex() + num);
            ret[3] = p__2;
        }

        return ret;
    }

    public int[] random_connect(Position p, Maze m) {
        Position[] nei_1 = this.get_neighbours(p, m, 1);
        Position[] nei_2 = this.get_neighbours(p, m, 2);
        int size = 0;

        for(int i = 0; i < 4; ++i) {
            if (nei_1[i] != null && nei_2[i] != null && m.get_value(nei_2[i].getRowIndex(), nei_2[i].getColumnIndex()) == 0 && m.get_value(nei_1[i].getRowIndex(), nei_1[i].getColumnIndex()) == 1) {
                ++size;
            }
        }

        int[] ret = new int[size];
        int at = 0;

        for(int j = 0; j < 4; ++j) {
            if (nei_1[j] != null && nei_2[j] != null && m.get_value(nei_2[j].getRowIndex(), nei_2[j].getColumnIndex()) == 0 && m.get_value(nei_1[j].getRowIndex(), nei_1[j].getColumnIndex()) == 1) {
                ret[at] = j;
                ++at;
            }
        }

        return ret;
    }

    public int random_pick_number(int[] from) {
        if (from.length > 0) {
            Random rand = new Random();
            int ret_ = rand.nextInt(from.length);
            return from[ret_];
        } else {
            return -1;
        }
    }

    public void compass(int to, Maze my_maze, Position p) {
        if (to == 0) {
            my_maze.setMaze(p.getRowIndex() - 1, p.getColumnIndex(), 0);
        }

        if (to == 1) {
            my_maze.setMaze(p.getRowIndex(), p.getColumnIndex() - 1, 0);
        }

        if (to == 2) {
            my_maze.setMaze(p.getRowIndex() + 1, p.getColumnIndex(), 0);
        }

        if (to == 3) {
            my_maze.setMaze(p.getRowIndex(), p.getColumnIndex() + 1, 0);
        }

    }

    public Maze generate(int row, int col) {
        Maze ret_Maze = this.Make_Walls(row, col);
        LinkedList<Position> neighbours = new LinkedList<>();
        Position[] to_add = this.get_neighbours(ret_Maze.getStartPosition(), ret_Maze, 2);

        for(int i = 0; i < 4; ++i) {
            if (to_add[i] != null) {
                neighbours.add(to_add[i]);
            }
        }

        ret_Maze.setMaze(ret_Maze.getStartPosition().getRowIndex(), ret_Maze.getStartPosition().getColumnIndex(), 0);

        int point;
        Random pick;
        while(neighbours.size() != 0) {
            pick = new Random();
            point = pick.nextInt(neighbours.size());
            Position at_pos = (Position)neighbours.get(point);
            ret_Maze.setMaze(at_pos.getRowIndex(), at_pos.getColumnIndex(), 0);
            neighbours.remove(point);
            int connect_to = this.random_pick_number(this.random_connect(at_pos, ret_Maze));
            if (connect_to != -1) {
                this.compass(connect_to, ret_Maze, at_pos);
            }

            Position[] to_add_ = this.get_neighbours(at_pos, ret_Maze, 2);

            for(int i = 0; i < 4; ++i) {
                if (to_add_[i] != null && ret_Maze.get_value(to_add_[i].getRowIndex(), to_add_[i].getColumnIndex()) == 1) {
                    neighbours.add(to_add_[i]);
                }
            }
        }

        ret_Maze.setMaze(ret_Maze.getGoalPosition().getRowIndex(), ret_Maze.getGoalPosition().getColumnIndex(), 0);
        if (row == col && row == 2 && ret_Maze.getStartPosition().getRowIndex() != ret_Maze.getGoalPosition().getRowIndex() && ret_Maze.getStartPosition().getColumnIndex() != ret_Maze.getGoalPosition().getColumnIndex()) {
            pick = new Random();
            point = pick.nextInt(2);
            if (point == 0) {
                ret_Maze.setMaze(ret_Maze.getGoalPosition().getRowIndex(), ret_Maze.getStartPosition().getColumnIndex(), 0);
            } else {
                ret_Maze.setMaze(ret_Maze.getStartPosition().getRowIndex(), ret_Maze.getGoalPosition().getColumnIndex(), 0);
            }
        }

        return ret_Maze;
    }

    public static void main(String[] args) {
        MyMazeGenerator smg = new MyMazeGenerator();
        Maze m1 = smg.generate(1, -5);
        m1.print();
        System.out.println("------ 13x7  ------");
        MyMazeGenerator smg2 = new MyMazeGenerator();
        Maze m = smg2.generate(13, 25);
        m.print();
        System.out.println("------ 4x4 ------");
        new MyMazeGenerator();
        Maze m3 = smg2.generate(2, 13);
        m3.print();
        System.out.println("------ 9x9  ------");
        MyMazeGenerator smg32 = new MyMazeGenerator();
        Maze m32 = smg32.generate(9, 9);
        m32.print();
        System.out.println("------ 3x7  ------");
        new MyMazeGenerator();
        System.out.println("------------");
    }
}
