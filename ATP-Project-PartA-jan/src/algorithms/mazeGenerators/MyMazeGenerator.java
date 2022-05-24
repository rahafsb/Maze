package algorithms.mazeGenerators;
import java.util.*;

public class MyMazeGenerator extends AMazeGenerator {

    public Maze Make_Walls(int row, int col){
        if (row <2 || col < 2){
            return null;
        }
        Maze ret = new Maze(row,col);
        for (int i=0; i< row; i++){
            for(int j=0; j< col; j++){
                ret.setMaze(i,j,1);
            }
        }
        return ret;
    }
    public Position[] get_neighbours(Position at_position, Maze at_maze, int num){  // 0 up, 1 left, 2 down, 3 right
        Position[] ret = new Position[5];
        for(int i=0; i<5;i++){
            ret[i]=null;
        }
        int limit_row = at_maze.get_rows_cols()[0];
        int limit_col = at_maze.get_rows_cols()[1];
        if(at_position.getRowIndex() - num >= 0 ){
            Position p = new Position(at_position.getRowIndex() - num,at_position.getColumnIndex());
            ret[0] = p;
        }
        if (at_position.getColumnIndex() - num >=0 ){
            Position p_2 = new Position(at_position.getRowIndex(),at_position.getColumnIndex() - num);
            ret[1] = p_2;
        }
        if(at_position.getRowIndex() + num < limit_row ){
            Position p_ = new Position(at_position.getRowIndex() + num,at_position.getColumnIndex());
            ret[2] = p_;
        }
        if (at_position.getColumnIndex() + num < limit_col ){
            Position p__2 = new Position(at_position.getRowIndex(),at_position.getColumnIndex() + num);
            ret[3] = p__2;
        }
        return ret;

    }

    public int [] random_connect(Position p, Maze m){          // 0 up, 1 left, 2 down, 3 right
        Position[] nei_1 = get_neighbours(p,m,1);
        Position[] nei_2 = get_neighbours(p,m,2);
        int size = 0;
        for(int i=0; i<4;i++){
            if(nei_1[i] != null && nei_2[i] != null && m.get_value(nei_2[i].getRowIndex(),nei_2[i].getColumnIndex()) == 0 && m.get_value(nei_1[i].getRowIndex(),nei_1[i].getColumnIndex()) == 1) {
                size++;
            }
        }
        int[] ret = new int[size];
        int at =0;
        for(int j=0; j<4;j++){
            if(nei_1[j] != null && nei_2[j] != null && m.get_value(nei_2[j].getRowIndex(),nei_2[j].getColumnIndex()) == 0 && m.get_value(nei_1[j].getRowIndex(),nei_1[j].getColumnIndex()) == 1) {
                ret[at] =j;
                at++;
            }
        }
        return ret;
    }

    public int random_pick_number(int[] from){
        if (from.length > 0) {
            Random rand = new Random();
            int ret_ = rand.nextInt(from.length);
            return from[ret_];
        }
        return -1;
    }
    public void compass(int to,Maze my_maze,Position p){
        if(to == 0){
            my_maze.setMaze(p.getRowIndex()-1, p.getColumnIndex(),0);
        }
        if(to == 1){
            my_maze.setMaze(p.getRowIndex(), p.getColumnIndex()-1,0);
        }
        if(to == 2){
            my_maze.setMaze(p.getRowIndex()+1, p.getColumnIndex(),0);
        }
        if(to == 3){
            my_maze.setMaze(p.getRowIndex(), p.getColumnIndex()+1,0);
        }

    }

    @Override
    public Maze generate(int row, int col) {
        if( row < 2 || col < 2 ){  // s6r 2w 3amud
            return null;
        }
        Maze ret_Maze = Make_Walls(row,col);
        LinkedList<Position> neighbours =new LinkedList<>();
        Position[] to_add = get_neighbours(ret_Maze.getStartPosition(),ret_Maze,2);
        for(int i = 0; i < 4; i++){
            if(to_add[i] != null){
                neighbours.add(to_add[i]);
            }
        }
        ret_Maze.setMaze(ret_Maze.getStartPosition().getRowIndex(),ret_Maze.getStartPosition().getColumnIndex(),0);
        while(neighbours.size()!=0){
            Random randomized = new Random();
            int random = randomized.nextInt(neighbours.size()); //-1?
            Position at_pos = neighbours.get(random);
            ret_Maze.setMaze(at_pos.getRowIndex(),at_pos.getColumnIndex(),0);
            neighbours.remove(random);
            int connect_to = random_pick_number(random_connect(at_pos, ret_Maze));
            if(connect_to != -1){
                compass(connect_to,ret_Maze,at_pos);
            }
            Position[] to_add_ = get_neighbours(at_pos,ret_Maze,2);
            for(int i = 0; i < 4; i++){ /// !0
                if(to_add_[i] != null && ret_Maze.get_value(to_add_[i].getRowIndex(),to_add_[i].getColumnIndex() ) == 1){
                    neighbours.add(to_add_[i]);
                }
            }
        }
        ret_Maze.setMaze(ret_Maze.getGoalPosition().getRowIndex(),ret_Maze.getGoalPosition().getColumnIndex(),0);
        if(row == col && row == 2){
            if(ret_Maze.getStartPosition().getRowIndex() != ret_Maze.getGoalPosition().getRowIndex() && ret_Maze.getStartPosition().getColumnIndex() != ret_Maze.getGoalPosition().getColumnIndex()){
                Random pick = new Random();
                int point = pick.nextInt(2);
                if(point == 0){
                    ret_Maze.setMaze(ret_Maze.getGoalPosition().getRowIndex(),ret_Maze.getStartPosition().getColumnIndex(),0);
                }
                else{
                    ret_Maze.setMaze(ret_Maze.getStartPosition().getRowIndex(),ret_Maze.getGoalPosition().getColumnIndex(),0);
                }
            }
        }
        return ret_Maze;
    }
    public static void main(String[] args) {
//           System.out.println("------ 10x10 ------");
        MyMazeGenerator smg = new MyMazeGenerator();
        Maze m1 = smg.generate(2, 2);
        m1.print();
        System.out.println("------ 13x7  ------");
        MyMazeGenerator smg2 = new MyMazeGenerator();
        Maze m = smg2.generate(13, 25);
        m.print();
        System.out.println("------ 4x4 ------");
        MyMazeGenerator smg3 = new MyMazeGenerator();
        Maze m3 = smg2.generate(2, 13);
        m3.print();
        System.out.println("------ 9x9  ------");
        MyMazeGenerator smg32 = new MyMazeGenerator();
        Maze m32 = smg32.generate(9, 9);
        m32.print();
        System.out.println("------ 3x7  ------");
        MyMazeGenerator s = new MyMazeGenerator();
        // Maze mm = s.generate(1000, 1000);
        //    mm.print();
        //  System.out.println(s.measureAlgorithmTimeMillis(1000,1000));
        System.out.println("------------");

    }
}
