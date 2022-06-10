package algorithms.search;

import algorithms.mazeGenerators.Position;

import java.io.Serializable;

public class MazeState extends AState implements Serializable {
    private Position at;

    public MazeState(Position at) {
        this.at = at;
        this.setAstate(at.toString());
    }
    public MazeState(Position at, AState father,double cost) {
        this.at = at;
        this.setAstate(at.toString());
        this.setCameFrom(father);
        this.setCost(cost);
    }

    public Position getAt() {
        return at;
    }
}
