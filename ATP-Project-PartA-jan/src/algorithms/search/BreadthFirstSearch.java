package algorithms.search;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;
public class BreadthFirstSearch extends ASearchingAlgorithm {
    @Override
    public Solution solve(ISearchable domain) {
        return solver(domain, new LinkedList<>());
    }
    public Solution solver (ISearchable domain, Queue<AState>Q_state){
        Q_state.add(domain.getStartState()); /// domain/StartState is null!!
        Stack<AState> up_side = new Stack<>();
        ArrayList<AState> sol = new ArrayList<>();
        while(!Q_state.isEmpty()){
            AState start = Q_state.peek();
            Q_state.remove();
            start.setVisit(true);
            setEval(getNumberOfNodesEvaluated()+1);
            ArrayList<AState> start_childs = domain.getAllPossibleStates(start,10,15);
            for(int i = 0; i < start_childs.size(); i++){
                if (!start_childs.get(i).isVisit()){///// && start_childs.get(i).getCameFrom() ==null){ //////////
                    start_childs.get(i).setCost(start_childs.get(i).getCost()+start.getCost());//////
                    Q_state.add(start_childs.get(i));
                    start_childs.get(i).setCameFrom(start);
                    if(start_childs.get(i).equals( domain.getGoalState())){
                        start = start_childs.get(i);
                        break;
                    }
                }
            }
            if(start.equals( domain.getGoalState())){
                up_side.push(start);
                while(!domain.getStartState().equals(start)){
                    start = start.getCameFrom();
                    up_side.push(start);
                }

                break;
            }
        }

        int d = up_side.size();
        for(int i =0; i< d;i++){
            sol.add(up_side.pop());
        }
        Solution ss = new Solution(sol);
        domain.erase();
        return ss;
    }

    @Override
    public String getName() {
        return "BreadthFirstSearch";
    }
}
