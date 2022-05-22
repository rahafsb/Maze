package algorithms.search;

import algorithms.mazeGenerators.Position;

public class MazeState extends AState {
    private Position at;

    public MazeState(Position at) {
        this.at = at;
        this.setAstate(at.toString());
    }

    public MazeState(Position at, AState father, int cost) {
        this.at = at;
        this.setAstate(at.toString());
        this.setCameFrom(father);
        this.setCost(cost);
    }

    public Position getAt() {
        return this.at;
    }
}
