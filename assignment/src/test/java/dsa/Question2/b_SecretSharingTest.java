package dsa.Question2;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class b_SecretSharingTest {

    @Test
    void secretKeepers() {

        // Test Case 1:
        int numberOfPeople1 = 5;
        int[][] intervals1 = { { 0, 2 }, { 1, 3 }, { 2, 4 } };
        int firstPerson1 = 0;

        List<Integer> result1 = b_SecretSharing.secretKeepers(numberOfPeople1, intervals1, firstPerson1);
        assertEquals(List.of(0, 1, 2, 3, 4), result1); // Expected result:

        // Test Case 2:
        int numberOfPeople2 = 6;
        int[][] intervals2 = { { 3, 5 }, { 0, 1 } };
        int firstPerson2 = 3;

        List<Integer> result2 = b_SecretSharing.secretKeepers(numberOfPeople2, intervals2, firstPerson2);
        assertEquals(List.of(3, 4, 5), result2); // Expected result:[3, 4, 5]
    }
}