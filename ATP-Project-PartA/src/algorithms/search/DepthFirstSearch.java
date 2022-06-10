package algorithms.search;

import java.util.ArrayList;
import java.util.Stack;
public class DepthFirstSearch extends ASearchingAlgorithm {
    @Override
    public Solution solve(ISearchable s) {
        if(s == null){ return null; }
        if (s.getStartState() == null || s.getGoalState() == null) { return null;}
        Stack<AState> my_stack = new Stack<>();
        Stack<AState> up_side = new Stack<>();
        my_stack.push(s.getStartState());
        int count =1;///
        while(!my_stack.isEmpty()){
            AState as = my_stack.peek();
            my_stack.pop();
            if(!as.isVisit()){
                as.setVisit(true);
                ArrayList<AState> child = s.getAllPossibleStates(as,10,15);
                for(int i=0; i< child.size();i++){
                    my_stack.push(child.get(i));
                    if(!child.get(i).isVisit()){
                        count++;
                        child.get(i).setCameFrom(as);
                    }
                    if (child.get(i) == s.getGoalState()){
                        as = child.get(i);
                        break;
                    }
                }
            }
            if(as ==  s.getGoalState()){
                up_side.push(as);
                setEval(count);
                while(!s.getStartState().equals(as)){
                    as = as.getCameFrom();
                    up_side.push(as);
                }

                break;
            }


        }
        ArrayList<AState> sol = new ArrayList<>();
        int d = up_side.size();
        for(int i =0; i< d;i++){
            sol.add(up_side.pop());
        }
        Solution ss = new Solution(sol);
        s.erase();
        return ss;


    }


    @Override
    public String getName() {
        return "DepthFirstSearch";
    }
}
