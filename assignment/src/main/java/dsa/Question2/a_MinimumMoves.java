package dsa.Question2;

public class a_MinimumMoves {
    
    // Method to calculate the minimum moves required to equalize the number of dresses in machines
    static int minimumMovesToEqualize(int[] machines) {
        // Initialize a variable to store the total number of dresses across all machines
        int total = 0;
        // Get the number of machines
        int n = machines.length;

        // Calculate the total number of dresses
        for (int machine : machines) {
            total += machine;
        }

        // If the total number of dresses cannot be evenly distributed among the machines,
        // return -1 indicating it's not possible to equalize the number of dresses
        if (total % n != 0) {
            return -1;
        }

        // Calculate the average number of dresses each machine should have after equalization
        int avg = total / n;
        
        // Initialize variables to keep track of moves and difference in dresses between each machine and the average
        int moves = 0;
        int diff = 0;
        
        // Iterate through each machine
        for (int i = 0; i < n; i++) {
            // Calculate the difference between the current machine's dresses and the average
            diff += machines[i] - avg;
            // Update the moves required to equalize (considering the maximum difference so far)
            moves = Math.max(moves, Math.abs(diff));
            // Set the current machine's dresses to the average
            machines[i] = avg;
        }
        
        // Return the minimum moves required to equalize the number of dresses
        return moves;
    }
    
    // Main method to test the minimumMovesToEqualize function
    public static void main(String[] args) {
        // Example array representing the number of dresses in each machine
        int[] machines = {1, 0, 5};
        // Print the minimum number of moves required to equalize dresses
        System.out.println("Minimum number of moves to equalize dresses: " + minimumMovesToEqualize(machines));
    }
}
