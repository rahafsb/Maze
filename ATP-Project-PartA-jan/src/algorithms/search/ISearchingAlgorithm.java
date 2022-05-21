package algorithms.search;

public interface ISearchingAlgorithm {
    Solution solve(ISearchable var1);

    int getNumberOfNodesEvaluated();

    String getName();
}
