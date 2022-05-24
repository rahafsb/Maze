package algorithms.search;

import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.Position;

import java.util.ArrayList;

public class SearchableMaze implements ISearchable {
    private Maze original;
    private AState[][] newer;


    public SearchableMaze(Maze original) {
        if (original == null){
            return;
        }
        this.original = original;
        int rows =original.get_rows_cols()[0];
        int cols = original.get_rows_cols()[1];
        this.newer = new AState[rows][cols];
        for(int i=0; i< rows;i++){
            for(int j=0; j< cols;j++){
                Position p = new Position(i,j);
                MazeState m = new MazeState(p);
                newer[i][j] = m;
            }
        }
    }

    @Override
    public AState getStartState() {
        return this.newer[this.original.getStartPosition().getRowIndex()][this.original.getStartPosition().getColumnIndex()];
    }

    @Override
    public AState getGoalState() { ////// nrj3
        return this.newer[this.original.getGoalPosition().getRowIndex()][this.original.getGoalPosition().getColumnIndex()];
    }

    @Override
    public ArrayList<AState> getAllPossibleStates(AState s,int vert, int dio) {
        ArrayList<AState> ret = new ArrayList<AState>();
        MazeState m = (MazeState) s; /////////
        int r = m.getAt().getRowIndex();
        int c = m.getAt().getColumnIndex();
        if (original.get_value(r-1,c)==0){
            if(newer[r-1][c].getCost() == 0){
                this.newer[r-1][c].setCost(vert);
                ret.add(this.newer[r-1][c]);}
            if(original.get_value(r-1,c+1)==0){
                if(newer[r-1][c+1].getCost() == 0){
                    this.newer[r-1][c+1].setCost(dio);
                    ret.add(this.newer[r-1][c+1]);}
            }
        }
        if(original.get_value(r,c+1)==0){

            if(original.get_value(r-1,c+1)==0){
                if(!ret.contains(this.newer[r-1][c+1])){
                    if(newer[r-1][c+1].getCost()==0){
                        this.newer[r-1][c+1].setCost(dio);
                        ret.add(this.newer[r-1][c+1]);}
                }
            }
            if(newer[r][c+1].getCost()==0){
                this.newer[r][c+1].setCost(vert);
                ret.add(this.newer[r][c+1]);}
            if(original.get_value(r+1,c+1)==0){
                if(newer[r+1][c+1].getCost()==0){
                    this.newer[r+1][c+1].setCost(dio);
                    ret.add(this.newer[r+1][c+1]);}
            }
        }
        ///down
        if(original.get_value(r+1,c)==0){

            if(original.get_value(r+1,c+1)==0){
                if(!ret.contains(this.newer[r+1][c+1])){
                    if(newer[r+1][c+1].getCost()==0){
                        this.newer[r+1][c+1].setCost(dio);
                        ret.add(this.newer[r+1][c+1]);}
                }
            }
            if(newer[r+1][c].getCost()==0){
                this.newer[r+1][c].setCost(vert);
                ret.add(this.newer[r+1][c]);}
            if(original.get_value(r+1,c-1)==0){
                if(newer[r+1][c-1].getCost()==0){
                    this.newer[r+1][c-1].setCost(dio);
                    ret.add(this.newer[r+1][c-1]);}

            }
        }
        // left
        if(original.get_value(r,c-1)==0){
            if(original.get_value(r+1,c-1)==0){
                if(!ret.contains(this.newer[r+1][c-1])){
                    if(newer[r+1][c-1].getCost()==0){
                        this.newer[r+1][c-1].setCost(dio);
                        ret.add(this.newer[r+1][c-1]);}

                }
            }
            if(newer[r][c-1].getCost()==0){
                this.newer[r][c-1].setCost(vert);
                ret.add(this.newer[r][c-1]);}
            if(original.get_value(r-1,c-1)==0){
                if(newer[r-1][c-1].getCost()==0){
                    this.newer[r-1][c-1].setCost(dio);
                    ret.add(this.newer[r-1][c-1]);}

            }
        }
        if (original.get_value(r-1,c)==0){
            if(original.get_value(r-1,c-1)==0){
                if(!ret.contains(this.newer[r-1][c-1])){
                    if(newer[r-1][c-1].getCost() == 0){
                        this.newer[r-1][c-1].setCost(dio);
                        ret.add(this.newer[r-1][c-1]);}
                }

            }
        }
        return ret;

    }
    @Override
    public void erase(){
        int rows =original.get_rows_cols()[0];
        int cols = original.get_rows_cols()[1];
        for(int i=0; i< rows;i++){
            for(int j=0; j< cols;j++){
                this.newer[i][j].setVisit(false);
                this.newer[i][j].setCameFrom(null);
                this.newer[i][j].setCost(0);
            }
        }

    }
}
