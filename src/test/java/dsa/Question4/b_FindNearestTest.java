package dsa.Question4;

import org.junit.jupiter.api.Test;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class b_FindNearestTest {

    @Test
    void closestKValues() {
        b_FindNearest testObj = new b_FindNearest();

        // Defining tree for input cases
        TreeNode testRoot = new TreeNode(4);
        testRoot.left = new TreeNode(2);
        testRoot.right = new TreeNode(5);
        testRoot.left.left = new TreeNode(1);
        testRoot.left.right = new TreeNode(3);

        // Test case 1:
        double target1 = 3.8;
        int quantity1 = 2;
        List<Integer> actualValues1 = testObj.closestValues(testRoot, target1, quantity1);
        actualValues1.sort(null);
        assertEquals(List.of(3, 4), actualValues1); // Expected output: [3, 4]

        // Test case 2:
        double target2 = 1.5;
        int quantity2 = 3;
        List<Integer> actualValues2 = testObj.closestValues(testRoot, target2, quantity2);
        actualValues2.sort(null);
        assertEquals(List.of(1, 2, 3), actualValues2);// Expected output: [1, 2, 3]

    }
}