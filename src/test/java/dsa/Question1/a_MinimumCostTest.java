package dsa.Question1;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class a_MinimumCostTest {

    @Test
    void minimumCostfinder() {
        a_MinimumCost testObj = new a_MinimumCost();
        // Test case 1
        int[][] costs1 = {
                { 1, 5, 3 },
                { 2, 9, 4 }
        };
        int expectedMinimumCost1 = 5;
        assertEquals(expectedMinimumCost1, testObj.minimumCostfinder(costs1)); // Expected output: 5

        // Test case 2
        int[][] costs2 = {
                { 1, 3, 2 },
                { 4, 6, 8 },
                { 3, 1, 5 }
        };
        int expectedMinimumCost2 = 7;
        assertEquals(expectedMinimumCost2, testObj.minimumCostfinder(costs2));// Expected output: 7

    }

}
