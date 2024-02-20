package dsa.Question2;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class a_MinimumMovesTest {

    @Test
    void minimumMovesToEqualize() {
        int[] machines1 = { 1, 0, 5 };
        int expectedval1=3;
        assertEquals(expectedval1, a_MinimumMoves.minimumMovesToEqualize(machines1));// Expected output: 3
        
        int[] machines2 = { 2, 1, 3, 0, 2 };
        int expectedval2=-1;
        assertEquals(expectedval2, a_MinimumMoves.minimumMovesToEqualize(machines2));// Expected output: -1
    }
}