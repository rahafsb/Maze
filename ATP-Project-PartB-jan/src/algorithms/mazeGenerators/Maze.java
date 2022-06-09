package algorithms.mazeGenerators;


import java.io.Serializable;
import java.util.Random;

public class Maze implements Serializable {

    private int rows;
    private int cols;
    private Position p_start;
    private Position p_end;
    private int[][] maze;
    // if rows/cols requested is negative we made it positive and if the maze is smaller than 2x2 we returned a 2x2 maze
    public Maze(int rows, int cols) {
        if(rows < -1){rows = -1*rows;}
        if(cols < -1){cols = -1*cols;}
        if(rows == 0 || rows == 1 || rows ==-1){rows =2;}
        if(cols == 0 || cols == 1 || cols ==-1){cols =2;}
        this.rows = rows;
        this.cols = cols;
        maze = new int[rows][cols];
        Position p_end1;
        p_start = pick_rand_start_end();  //start position
        p_end1 = pick_rand_start_end(); //goal position
        while (p_start.getRowIndex() == p_end1.getRowIndex() && p_start.getColumnIndex() == p_end1.getColumnIndex()) {
            p_end1 = pick_rand_start_end();
        }
        p_end = p_end1;

    }
    void change_start_end(Position p, int n){ // n=0 => start   n=1 => end
        if(n == 0){this.p_start = p;}
        else{ this.p_end = p ;}
    }

    public int get_poin(int i,int j, int v,byte[] b){
        int ret = 0;
        for(int i_ = i; i_ >= j; i_--){
            ret += b[i_]*Math.pow(10, i_-v);
        }
        return ret;
    }
    public Maze(byte[] b){   // [rows,cols,p_start,p_end,maze] byte[8*3]=byte[24]+|maze|
        if(b.length > 24){
            int rows = get_poin(3,0,0,b);
            this.rows = rows;
            int cols = get_poin(7,4,4,b);
            this.cols = cols;
            this.maze = new int[rows][cols];
            int start_row = get_poin(11,8,8,b);
            int start_col = get_poin(15,12,12,b);
            Position p_start = new Position(start_row,start_col);
            int end_row = get_poin(19,16,16,b);
            int end_col = get_poin(23,20,20,b);
            Position p_end = new Position(end_row,end_col);  //  this.change_start_end(p_start,0);
            this.p_start=p_start;
            this.p_end = p_end; //  this.change_start_end(p_end,1);
            int last = b.length;
            int r =0;
            int c=0;
            for(int i = 24; i< last;i++){
                this.maze[r][c] = b[i];
                c++;
                if(c == cols){c=0;}
                if(c == 0){r++;}
              /*  if(c == cols){
                   // this.maze[r][c] = b[i];
                    c = 0;
                    r++;
                }
                else{*/

                // }

            }
        }



    }


    public int[] get_rows_cols(){
        int[] ret = new int[2];
        ret[0] = rows;
        ret[1]=cols;
        return ret;
    }
    public int zero_one(){
        Random rand_st = new Random();
        int int_rand_st = rand_st.nextInt(2);
        return int_rand_st;

    }


    private Position pick_rand_start_end(){
        int int_rand_x;
        int int_rand_y;
        int x = zero_one();
        if (x == 0){
            Random rand_x = new Random();
            int_rand_x = rand_x.nextInt(rows);
            if(int_rand_x >0 && int_rand_x <rows-1 ){
                int y = zero_one();
                if(y==0){
                    int_rand_y=0;
                }
                else{
                    int_rand_y = cols-1;
                }
            }
            else{
                Random rand_y = new Random();
                int_rand_y = rand_y.nextInt(cols);
            }
        }
        else{
            Random rand_y = new Random();
            int_rand_y = rand_y.nextInt(cols);
            if(int_rand_y >0 && int_rand_y<cols-1 ){
                int x_2 = zero_one();
                if(x_2==0){
                    int_rand_x=0;
                }
                else{
                    int_rand_x = rows-1;
                }
            }
            else{
                Random rand_x = new Random();
                int_rand_x = rand_x.nextInt(rows);
            }

        }

        return new Position(int_rand_x, int_rand_y);
    }

    public Position getStartPosition(){
        return this.p_start;
    }

    public Position getGoalPosition(){
        return this.p_end;
    }

    public void setMaze(int x, int y, int change_to) { this.maze[x][y] = change_to; }

    public int get_value(int x, int y ){
        if (x >= rows || x < 0 || y >= cols || y < 0 ){
            return -1;
        }
        return maze[x][y];
    }

    public void print(){
        System.out.print("{");
        for (int i = 0 ; i < rows ; i++){
            System.out.print("{");
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
            if(i +1 != rows){ System.out.println("}"); }
            else{ System.out.println("}}"); }

        }

    }
    public int[] digits(int num){
        int[] ret = new int[4];
        ret[0] = (num)%10;
        ret[1] = (num/10)%10;
        ret[2] = (num/100)%10;
        ret[3] = (num/1000)%10;
        return ret;
    }
    public byte[] toByteArray(){    //[rows,cols,p_start,p_end,maze] byte[8*3]=byte[24]+|maze|
        if(this.p_end == null || this.p_start == null ){ // drop
            return null;
        }
        int bits = 24 + this.rows*cols;
        byte[] ret = new byte[bits];
        int[] row = digits(this.rows); //1
        for(int i=0; i<4;i++){
            ret[i] = (byte) row[i];
        }
        int[] col = digits(this.cols); //2
        for(int i =4; i<8; i++){
            ret[i] =(byte) col[i-4];
        }
        int[] s_row = digits(this.p_start.getRowIndex());
        for(int i=8; i<12; i++){
            ret[i] = (byte) s_row[i-8];
        }
        int[] s_col = digits(this.p_start.getColumnIndex());
        for(int i=12; i<16; i++){
            ret[i] = (byte) s_col[i-12];
        }
        int[] e_row = digits(this.p_end.getRowIndex());
        for(int i=16; i<20; i++){
            ret[i] = (byte) e_row[i-16];
        }
        int[] e_col = digits(this.p_end.getColumnIndex());
        for(int i=20; i<24; i++){
            ret[i] = (byte) e_col[i-20];
        }
        int count = 24;
        for(int i=0; i<this.rows;i++){
            for(int j=0; j< this.cols;j++){
                ret[count] = (byte)this.get_value(i,j);
                count++;
            }
        }

        return ret;

    }

    public static void main(String[] args) {
        int n=37;
        System.out.println((byte)n);
    }
}