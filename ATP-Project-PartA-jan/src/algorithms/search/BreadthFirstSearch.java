package algorithms.search;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class BreadthFirstSearch extends ASearchingAlgorithm {
    public BreadthFirstSearch() {
    }

    public Solution solve(ISearchable domain) {
        return this.solver(domain, new LinkedList());
    }

    public Solution solver(ISearchable domain, Queue<AState> Q_state) {
        if (domain == null) {
            return null;
        } else if (domain.getStartState() == null) {
            return null;
        } else {
            Q_state.add(domain.getStartState());
            Stack<AState> up_side = new Stack<>();
            ArrayList<AState> sol = new ArrayList<>();

            label54:
            while(!Q_state.isEmpty()) {
                AState start = (AState)Q_state.peek();
                Q_state.remove();
                start.setVisit(true);
                this.setEval(this.getNumberOfNodesEvaluated() + 1);
                ArrayList<AState> start_childs = domain.getAllPossibleStates(start, 10, 15);

                for (AState start_child : start_childs) {
                    if (!((AState) start_child).isVisit()) {
                        ((AState) start_child).setCost(((AState) start_child).getCost() + start.getCost());
                        Q_state.add(start_child);
                        ((AState) start_child).setCameFrom(start);
                        if (((AState) start_child).equals(domain.getGoalState())) {
                            start = (AState) start_child;
                            break;
                        }
                    }
                }

                if (start.equals(domain.getGoalState())) {
                    up_side.push(start);

                    while(true) {
                        if (domain.getStartState().equals(start)) {
                            break label54;
                        }

                        start = start.getCameFrom();
                        up_side.push(start);
                    }
                }
            }

            int d = up_side.size();

            for(int i = 0; i < d; ++i) {
                sol.add(up_side.pop());
            }

            Solution ss = new Solution(sol);
            domain.erase();
            return ss;
        }
    }

    public String getName() {
        return "BreadthFirstSearch";
    }
}
