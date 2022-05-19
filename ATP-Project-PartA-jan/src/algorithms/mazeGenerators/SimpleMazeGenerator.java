package algorithms.mazeGenerators;

import java.util.Random;

public class SimpleMazeGenerator extends AMazeGenerator{

    public Maze generate(int row, int col) {
        Maze m = new Maze(row, col);
        for (int i = 0 ; i < row ; i ++){
            for (int j = 0 ; j < col ; j++){
                m.setMaze(i, j, get_rand());
            }
        }
        m.setMaze(m.getStartPosition().getRowIndex(), m.getStartPosition().getColumnIndex(), 0);
        m.setMaze(m.getGoalPosition().getRowIndex(), m.getGoalPosition().getColumnIndex(), 0);
        create_path(m);
        return m;
    }

    private void create_path(Maze m){
        Position mover = new Position(m.getStartPosition().getRowIndex(), m.getStartPosition().getColumnIndex());
        int [] next_move = pick_next_move();
        while (mover.getRowIndex() != m.getGoalPosition().getRowIndex() || mover.getColumnIndex() != m.getGoalPosition().getColumnIndex()){
            int is_legal = is_move_legal(next_move, m.rows, m.cols, mover);
            if (is_legal == 0){//not legal
                next_move = pick_next_move();
            }
            if (is_legal == 1){//legal
                mover.setX(mover.getRowIndex() + next_move[0]);
                mover.setY(mover.getColumnIndex() + next_move[1]);
                m.setMaze(mover.getRowIndex(), mover.getColumnIndex(), 0);
                next_move = pick_next_move();
            }
        }
    }

    private int is_move_legal(int [] next_move, int rows, int cols, Position curr_p){
        if (curr_p.getRowIndex() + next_move[0] < 0 || curr_p.getRowIndex() + next_move[0] > rows - 1 || curr_p.getColumnIndex() + next_move[1] < 0 || curr_p.getColumnIndex() + next_move[1] > cols - 1){
            return 0;
        }
        else{
            return 1;
        }
    }

    private int [] pick_next_move(){
        int [] next_move = new int [2];
        Random rand = new Random();
        int int_rand =  rand.nextInt(4);
        if (int_rand == 0){ //go left
            next_move[0] = -1;
            return next_move;
        }
        else if (int_rand == 1){ //go down
            next_move[1] = 1;
            return next_move;
        }
        else if (int_rand == 2){ //go right
            next_move[0] = 1;
            return next_move;
        }
        else {//go up
            next_move[1] = -1;
            return next_move;
        }
    }

    private int get_rand(){
        Random rand = new Random();
        return rand.nextInt(2);
    }


    public static void main(String[] args) {
        SimpleMazeGenerator smg = new SimpleMazeGenerator();
        Maze m = smg.generate(13, 5);
        m.print();
    }
}
