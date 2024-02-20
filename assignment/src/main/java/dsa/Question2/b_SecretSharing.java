package dsa.Question2;

import java.util.*;

public class b_SecretSharing {

    // Method to determine who knows the secret based on the intervals and the first person
    public static List<Integer> secretKeepers(int n, int[][] intervals, int firstPerson) {
        // Array to keep track of who knows the secret
        boolean[] knowsSecret = new boolean[n];
        // Initially, mark the first person as knowing the secret
        knowsSecret[firstPerson] = true;

        // Iterate through each interval
        for (int[] interval : intervals) {
            // Check each position in the interval
            for (int i = interval[0]; i <= interval[1]; i++) {
                // If a person in the interval knows the secret
                if (knowsSecret[i]) {
                    // Mark all persons in the interval as knowing the secret
                    for (int j = interval[0]; j <= interval[1]; j++) {
                        knowsSecret[j] = true;
                    }
                    // Move to the next interval
                    break;
                }
            }
        }

        // Store the indices of persons who know the secret
        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            if (knowsSecret[i]) {
                result.add(i);
            }
        }

        // Return the list of persons who know the secret
        return result;
    }

    // Main method to test the secretKeepers function
    public static void main(String[] args) {
        // Example parameters
        int n = 5;
        int[][] intervals = {{0, 2}, {1, 3}, {2, 4}};
        int firstPerson = 0;

        // Find who knows the secret
        List<Integer> result = secretKeepers(n, intervals, firstPerson);
        // Print the result
        System.out.println(result);
    }
}
