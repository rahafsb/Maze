package algorithms.search;
import java.util.ArrayList;
public interface ISearchable {
   AState getStartState();
   AState getGoalState();
   void erase();
   ArrayList<AState> getAllPossibleStates(AState s,int vert, int dio);
}
