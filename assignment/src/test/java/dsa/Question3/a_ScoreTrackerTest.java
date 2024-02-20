package dsa.Question3;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class a_ScoreTrackerTest {

    @Test
    public void testGetMedianScore() {
        // Object Instantiation:
        ScoreTracker testScoreTracker = new ScoreTracker();

        // Generating testInput:
        testScoreTracker.addScore(85.5);
        testScoreTracker.addScore(92.3);
        testScoreTracker.addScore(77.8);
        testScoreTracker.addScore(90.1);

        double median1 = testScoreTracker.getMedianScore();
        // Additional parameter delta to manage acceptable floating point difference in
        // float comparision
        assertEquals(87.8, median1, 0.01); // Expected result: 87.8

        testScoreTracker.addScore(81.2);
        testScoreTracker.addScore(88.7);
        double median2 = testScoreTracker.getMedianScore();
        // Additional parameter delta to manage acceptable floating point difference in
        // float comparision
        assertEquals(87.1, median2, 0.01);// Expected result: 87.1
    }

    @Test
    public void emptyCase() {
        ScoreTracker testScoreTracker = new ScoreTracker();
        double median = testScoreTracker.getMedianScore();
        // Additional parameter delta to manage acceptable floating point difference in
        // float comparision
        assertEquals(0.0, median, 0.01);// Expected result: 0.o
    }
}