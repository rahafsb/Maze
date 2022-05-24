package algorithms.search;

import algorithms.mazeGenerators.Maze;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BestFirstSearchTest {
    @Test
    void getName() {
        BestFirstSearch best = new BestFirstSearch();
        assertEquals("BestFirstSearch", best.getName());
    }

    @Test
    void test1_best_first_search() {
        Maze m = new Maze(-5, -1);
        SearchableMaze sm = new SearchableMaze(m);
        BestFirstSearch bs = new BestFirstSearch();
        Solution s = bs.solve(sm);
        assertEquals(null, s);
    }
}
