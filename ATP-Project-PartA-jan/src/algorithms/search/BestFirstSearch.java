package algorithms.search;

import java.util.Comparator;
import java.util.PriorityQueue;

public class BestFirstSearch extends BreadthFirstSearch {
    public BestFirstSearch() {
    }

    public Solution solve(ISearchable domain) {
        return this.solver(domain, new PriorityQueue(new Comperer()));
    }

    public String getName() {
        return " BestFirstSearch";
    }
}
class Comperer implements Comparator<AState> {
    Comperer() {
    }

    public int compare(AState a, AState b) {
        return (a.getCost() - b.getCost());
    }
}