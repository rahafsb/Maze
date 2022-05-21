package algorithms.search;

import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.Position;
import java.util.ArrayList;

public class SearchableMaze implements ISearchable {
    private Maze original;
    private AState[][] newer;

    public SearchableMaze(Maze original) {
        this.original = original;
        int rows = original.get_rows_cols()[0];
        int cols = original.get_rows_cols()[1];
        this.newer = new AState[rows][cols];

        for(int i = 0; i < rows; ++i) {
            for(int j = 0; j < cols; ++j) {
                Position p = new Position(i, j);
                MazeState m = new MazeState(p);
                this.newer[i][j] = m;
            }
        }

    }

    public AState getStartState() {
        return this.newer[this.original.getStartPosition().getRowIndex()][this.original.getStartPosition().getColumnIndex()];
    }

    public AState getGoalState() {
        return this.newer[this.original.getGoalPosition().getRowIndex()][this.original.getGoalPosition().getColumnIndex()];
    }

    public ArrayList<AState> getAllPossibleStates(AState s, int vert, int dio) {
        ArrayList<AState> ret = new ArrayList();
        MazeState m = (MazeState)s;
        int r = m.getAt().getRowIndex();
        int c = m.getAt().getColumnIndex();
        if (this.original.get_value(r - 1, c) == 0) {
            this.newer[r - 1][c].setCost((double)vert);
            ret.add(this.newer[r - 1][c]);
            if (this.original.get_value(r - 1, c + 1) == 0) {
                this.newer[r - 1][c + 1].setCost((double)dio);
                ret.add(this.newer[r - 1][c + 1]);
            }
        }

        if (this.original.get_value(r, c + 1) == 0) {
            if (this.original.get_value(r - 1, c + 1) == 0 && !ret.contains(this.newer[r - 1][c + 1])) {
                this.newer[r - 1][c + 1].setCost((double)dio);
                ret.add(this.newer[r - 1][c + 1]);
            }

            ret.add(this.newer[r][c + 1]);
            this.newer[r][c + 1].setCost((double)vert);
            if (this.original.get_value(r + 1, c + 1) == 0) {
                this.newer[r + 1][c + 1].setCost((double)dio);
                ret.add(this.newer[r + 1][c + 1]);
            }
        }

        if (this.original.get_value(r + 1, c) == 0) {
            if (this.original.get_value(r + 1, c + 1) == 0 && !ret.contains(this.newer[r + 1][c + 1])) {
                this.newer[r + 1][c + 1].setCost((double)dio);
                ret.add(this.newer[r + 1][c + 1]);
            }

            ret.add(this.newer[r + 1][c]);
            this.newer[r + 1][c].setCost((double)vert);
            if (this.original.get_value(r + 1, c - 1) == 0) {
                ret.add(this.newer[r + 1][c - 1]);
                this.newer[r + 1][c - 1].setCost((double)dio);
            }
        }

        if (this.original.get_value(r, c - 1) == 0) {
            if (this.original.get_value(r + 1, c - 1) == 0 && !ret.contains(this.newer[r + 1][c - 1])) {
                ret.add(this.newer[r + 1][c - 1]);
                this.newer[r + 1][c - 1].setCost((double)dio);
            }

            ret.add(this.newer[r][c - 1]);
            this.newer[r][c - 1].setCost((double)vert);
            if (this.original.get_value(r - 1, c - 1) == 0) {
                ret.add(this.newer[r - 1][c - 1]);
                this.newer[r - 1][c - 1].setCost((double)dio);
            }
        }

        if (this.original.get_value(r - 1, c) == 0 && this.original.get_value(r - 1, c - 1) == 0 && !ret.contains(this.newer[r - 1][c - 1])) {
            ret.add(this.newer[r - 1][c - 1]);
            this.newer[r - 1][c - 1].setCost((double)dio);
        }

        return ret;
    }

    public void erase() {
        int rows = this.original.get_rows_cols()[0];
        int cols = this.original.get_rows_cols()[1];

        for(int i = 0; i < rows; ++i) {
            for(int j = 0; j < cols; ++j) {
                this.newer[i][j].setVisit(false);
                this.newer[i][j].setCameFrom((AState)null);
                this.newer[i][j].setCost(0.0);
            }
        }

    }
}
