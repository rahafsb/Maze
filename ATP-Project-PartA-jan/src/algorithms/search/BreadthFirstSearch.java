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
        Q_state.add(domain.getStartState());
        Stack<AState> up_side = new Stack();
        ArrayList<AState> sol = new ArrayList();

        label44:
        while(!Q_state.isEmpty()) {
            AState start = (AState)Q_state.remove();
            start.setVisit(true);
            this.setEval(this.getNumberOfNodesEvaluated() + 1);
            ArrayList<AState> start_childs = domain.getAllPossibleStates(start, 10, 15);

            for(int i = 0; i < start_childs.size(); ++i) {
                if (!((AState)start_childs.get(i)).isVisit()) {
                    Q_state.add(start_childs.get(i));
                    ((AState)start_childs.get(i)).setCameFrom(start);
                    start_childs.get(i).setVisit(true);
                }
                if (start_childs.get(i).equals(domain.getGoalState())) {
                    AState looper = start_childs.get(i);
                    up_side.push(looper);

                    while(!Q_state.isEmpty()){
                        Q_state.remove();
                        this.setEval(this.getNumberOfNodesEvaluated() + 1);
                    }
                    while(true) {
                        if (domain.getStartState().equals(looper)){
                            break label44;
                        }

                        looper = looper.getCameFrom();
                        up_side.push(looper);
                    }
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

    public String getName() {
        return "BreadthFirstSearch";
    }
}
