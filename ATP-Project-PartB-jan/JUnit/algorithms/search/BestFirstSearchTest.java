package algorithms.search;

import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.MyMazeGenerator;
import algorithms.mazeGenerators.Position;
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
        assertNotNull(s);
        assertEquals(m.get_rows_cols()[0], 5);
        assertEquals(m.get_rows_cols()[1], 2);
    }

    @Test
    void test2_best_first_search() {
        Maze m = null;
        SearchableMaze sm = new SearchableMaze(m);
        BestFirstSearch bs = new BestFirstSearch();
        Solution s = bs.solve(sm);
        assertNotNull(s);
    }

    @Test
    void test3_best_first_search() {
        MyMazeGenerator myMaze = new MyMazeGenerator();
        Maze m = myMaze.generate(1, -10);
        SearchableMaze sm = new SearchableMaze(m);
        BestFirstSearch bs = new BestFirstSearch();
        Solution s = bs.solve(sm);
        assertNotNull(s);
    }

    @Test
    void test4_best_first_search() {
        SearchableMaze sm = null;
        BestFirstSearch bs = new BestFirstSearch();
        Solution s = bs.solve(sm);
        assertNull(s);
    }
}
