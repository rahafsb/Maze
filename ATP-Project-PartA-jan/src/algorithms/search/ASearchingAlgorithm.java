package algorithms.search;

public abstract class ASearchingAlgorithm implements ISearchingAlgorithm {
    private int eval = 0;

    public ASearchingAlgorithm() {
    }

    public void setEval(int e) {
        this.eval = e;
    }

    public abstract Solution solve(ISearchable var1);

    public abstract String getName();

    public int getNumberOfNodesEvaluated() {
        return this.eval;
    }
}