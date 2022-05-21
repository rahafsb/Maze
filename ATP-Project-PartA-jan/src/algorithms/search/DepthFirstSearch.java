package algorithms.search;

import java.util.ArrayList;
import java.util.Stack;

public class DepthFirstSearch extends ASearchingAlgorithm {
    public DepthFirstSearch() {
    }

    public Solution solve(ISearchable s) {
        Stack<AState> my_stack = new Stack();
        Stack<AState> up_side = new Stack();
        my_stack.push(s.getStartState());

        int i;
        label46:
        while(!my_stack.isEmpty()) {
            AState as = (AState)my_stack.pop();
            if (!as.isVisit()) {
                as.setVisit(true);
                this.setEval(this.getNumberOfNodesEvaluated() + 1);
                ArrayList<AState> child = s.getAllPossibleStates(as, 10, 15);

                for(i = 0; i < child.size(); ++i) {
                    my_stack.push(child.get(i));
                    if (!((AState)child.get(i)).isVisit()) {
                        ((AState)child.get(i)).setCameFrom(as);
                    }
                }
            }

            if (as == s.getGoalState()) {
                while (!my_stack.isEmpty()){
                    my_stack.pop();
                    this.setEval(getNumberOfNodesEvaluated()+1);
                }
                up_side.push(as);

                while(true) {
                    if (s.getStartState().equals(as)) {
                        break label46;
                    }

                    as = as.getCameFrom();
                    up_side.push(as);
                }
            }
        }

        ArrayList<AState> sol = new ArrayList();
        int d = up_side.size();

        for(i = 0; i < d; ++i) {
            sol.add(up_side.pop());
        }

        Solution ss = new Solution(sol);
        s.erase();
        return ss;
    }

    public String getName() {
        return "DepthFirstSearch";
    }
}
