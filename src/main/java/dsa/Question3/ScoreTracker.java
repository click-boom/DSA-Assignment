package dsa.Question3;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ScoreTracker {
    private List<Double> scores;

    // Constructor to initialize the scores list
    public ScoreTracker() {
        scores = new ArrayList<>();
    }

    // Method to add a score to the list
    public void addScore(double score) {
        scores.add(score);
    }

    // Method to calculate and return the median score
    public double getMedianScore() {
        // Create a copy of the scores list
        List<Double> sortedScores = new ArrayList<>(scores);
        // Sort the scores in ascending order
        Collections.sort(sortedScores);
        // Get the size of the list
        int size = sortedScores.size();
        // If the list is empty, return 0
        if (size == 0) {
            return 0;
        }
        // If the size of the list is even, calculate the median by averaging the middle two scores
        else if (size % 2 == 0) {
            int mid = size / 2;
            return (sortedScores.get(mid - 1) + sortedScores.get(mid)) / 2.0;
        }
        // If the size of the list is odd, return the middle score
        else {
            return sortedScores.get(size / 2);
        }
    }

    // Main method to test the ScoreTracker class
    public static void main(String[] args) {
        // Create a ScoreTracker object
        ScoreTracker scoreTracker = new ScoreTracker();
        // Add scores to the tracker
        scoreTracker.addScore(85.5); // Stream: [85.5]
        scoreTracker.addScore(92.3); // Stream: [85.5, 92.3]
        scoreTracker.addScore(77.8); // Stream: [85.5, 92.3, 77.8]
        scoreTracker.addScore(90.1); // Stream: [85.5, 92.3, 77.8, 90.1]
        // Calculate and print the median of the scores
        double median1 = scoreTracker.getMedianScore(); // Output: 87.8
        System.out.println("Median 1: " + median1);

        // Add more scores to the tracker
        scoreTracker.addScore(81.2); // Stream: [85.5, 92.3, 77.8, 90.1, 81.2]
        scoreTracker.addScore(88.7); // Stream: [85.5, 92.3, 77.8, 90.1, 81.2, 88.7]
        // Calculate and print the updated median of the scores
        double median2 = scoreTracker.getMedianScore(); // Output: 87.1
        System.out.println("Median 2: " + median2);
    }
}