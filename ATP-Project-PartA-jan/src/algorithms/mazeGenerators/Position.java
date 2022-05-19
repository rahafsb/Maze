package algorithms.mazeGenerators;

public class Position {
    private int x;
    private int y;

    public Position(int i, int j) {
        x = i;
        y = j;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getRowIndex(){
        return x;
    }

    public int getColumnIndex(){
        return y;
    }

    @Override
    public String toString() {
        return "{" + x + "," + y + "}";
    }
}
