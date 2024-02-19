package dsa.Question4;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class a_MazeGameTest {

    @Test
    void shortestPathTest() {
        // Object instantiation:
        a_MazeGame mazeSolver = new a_MazeGame();

        // Test Case 1:
        char[][] mazeGrid1 = {
                { 'S', 'S', 'a', 'P', 'P' },
                { 'W', 'W', 'W', 'P', 'W' },
                { 'b', 'P', 'A', 'P', 'B' }
        };

        // Unit testing modular function1
        int[] start1 = mazeSolver.getStart(mazeGrid1);
        assertEquals(0, start1[0]); // Expected x-axis result=0;
        assertEquals(0, start1[1]); // Expected x-axis result=0;

        int expectedMinimumSteps1 = 8;
        assertEquals(expectedMinimumSteps1, mazeSolver.shortestPath(mazeGrid1)); // Expected result= 8

        // Test Case 2:
        char[][] mazeGrid2 = {
                { 'P', 'S', 'a', 'P', 'P' },
                { 'W', 'W', 'W', 'P', 'W' },
                { 'b', 'P', 'A', 'P', 'B' }
        };

        // Unit testing modular function1
        int[] start2 = mazeSolver.getStart(mazeGrid2);
        assertEquals(0, start2[0]); // Expected x-axis result=0;
        assertEquals(1, start2[1]); // Expected x-axis result=0;

        // Unit testing modular function2
        int keysCount = mazeSolver.getKeysCount(mazeGrid2);
        int expectedKeyCount1 = 2;
        assertEquals(expectedKeyCount1, keysCount); // Expected keyCount= 2

        int expectedMinimumSteps2 = 7;
        assertEquals(expectedMinimumSteps2, mazeSolver.shortestPath(mazeGrid2)); // Expected result= 7
    }
}
