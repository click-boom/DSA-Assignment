package dsa.Question1;

public class a_MinimumCost {
    public int minimumCostfinder(int[][] costs) {
        // Check if the input array is null or empty.
        if (costs == null || costs.length == 0)
            return 0;

        int n = costs.length; // Number of venues
        int k = costs[0].length;// Number of themes
        int[][] dp = new int[n][k];// Dynamic programming array to store minimum costs.


        // Initialize the first row of dp with the costs of themes the first venue.
        for (int j = 0; j < k; j++) {
            dp[0][j] = costs[0][j];
        }
        
        // Fill in the dp array for corresponding venues.
        for (int i = 1; i < n; i++) {
            for (int j = 0; j < k; j++) {
                dp[i][j] = Integer.MAX_VALUE;// Initialize to a large value.
                for (int l = 0; l < k; l++) {
                    if (j == l)
                        continue;// Skip the same themes as the previous venue.
                        // Update the minimum cost for painting the current venue with theme j.
                    dp[i][j] = Math.min(dp[i][j], dp[i - 1][l] + costs[i][j]);
                }
            }
        }
        // Find the minimum cost for painting the last venue.
        int minimumcost = Integer.MAX_VALUE;
        for (int j = 0; j < k; j++) {
            minimumcost = Math.min(minimumcost, dp[n - 1][j]);
        }

        return minimumcost;
    }

    public static void main(String[] args) {
        a_MinimumCost object=new a_MinimumCost();
        int[][] input = {
            { 1, 3, 2 },
            { 4, 6, 8 },
            { 3, 1, 5 }
    };
    System.out.println("Minimum cost: "+object.minimumCostfinder(input));
    }
}