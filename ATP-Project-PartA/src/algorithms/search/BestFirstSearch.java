package algorithms.search;

import java.util.Comparator;
import java.util.PriorityQueue;


public class BestFirstSearch extends BreadthFirstSearch{
    @Override
    public Solution solve(ISearchable domain) {
        return solver(domain, new PriorityQueue<>(new Comperer()));
    }

    @Override
    public String getName() {
        return "BestFirstSearch";
    }
}
class Comperer implements Comparator<AState> {
    public int compare(AState a ,AState b)
    {
        return((int)(a.getCost()-b.getCost()));
    }
}
