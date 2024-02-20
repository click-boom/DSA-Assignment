package dsa.Question1;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class b_buildEngineTest {

    @Test
    void minTimeToBuildAllEngines() {
        
        b_buildEngine one_b = new b_buildEngine();
        
        // Test case 1:
        int[] inputEngines1 = {3, 4, 5, 2};
        int splitCost1 = 2;
        int expectedMinTime1 = 9;
        assertEquals(expectedMinTime1, one_b.minTimeToBuildAllEngines(inputEngines1, splitCost1));// Expected output: 9
        
        // Test case 2:
        int[] inputEngines2 = {1, 2, 3};
        int splitCost2 = 1;
        int expectedMinTime2 = 4;
        assertEquals(expectedMinTime2, one_b.minTimeToBuildAllEngines(inputEngines2, splitCost2)); // Expected output: 4
    }
}