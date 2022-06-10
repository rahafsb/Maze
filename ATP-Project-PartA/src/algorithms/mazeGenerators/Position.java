package algorithms.mazeGenerators;

import java.io.Serializable;

public class Position implements Serializable {
    private int x;
    private int y;

    public Position(int r, int c){
        this.x = r;
        this.y = c;
    }
    public int getRowIndex(){ return this.x; }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getColumnIndex(){
        return this.y;
    }

    public String toString(){
        String ret = "{";
        ret += String.valueOf(this.x);
        ret += ",";
        ret += String.valueOf(this.y);
        ret += "}";
        return ret;
    }

}
