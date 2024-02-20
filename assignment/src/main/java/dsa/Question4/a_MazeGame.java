package dsa.Question4;

import java.util.*;

public class a_MazeGame {
    // A class to keep track of the current state in the maze
    static class StateChecker {
        int i; // Current row position in the maze
        int j; // Current column position in the maze
        int keys; // The keys collected so far

        StateChecker(int i, int j, int keys) {
            this.i = i;
            this.j = j;
            this.keys = keys;
        }
    }

    public int shortestPath(char[][] mazeGrid) {
        // Directions array to move up, down, left, and right
        int[][] directions = { { 0, 1 }, { 1, 0 }, { 0, -1 }, { -1, 0 } };
        int m = mazeGrid.length; // Number of rows in the maze
        int n = mazeGrid[0].length; // Number of columns in the maze
        int keysCount = getKeysCount(mazeGrid); // Total number of keys in the maze
        int keyChecker = (1 << keysCount) - 1; // Bitmask to check if all keys are collected
        int[] start = getStart(mazeGrid); // Starting position in the maze
        int result = 0; // The shortest path length
        Queue<StateChecker> currentState = new LinkedList<>(); // Queue to keep track of the current state
        currentState.offer(new StateChecker(start[0], start[1], 0)); // Add the starting state to the queue
        boolean[][][] seen = new boolean[m][n][keyChecker]; // 3D array to keep track of visited states
        seen[start[0]][start[1]][0] = true; // Mark the starting state as visited

        // BFS to find the shortest path
        while (!currentState.isEmpty()) {
            result++;
            int size = currentState.size();
            for (int i = 0; i < size; i++) {
                StateChecker current = currentState.poll();
                int onX = current.i;
                int onY = current.j;
                int keys = current.keys;
                for (int[] dir : directions) {
                    int newX = onX + dir[0];
                    int newY = onY + dir[1];
                    // If the new position is out of bounds or is a wall, skip it
                    if (newX < 0 || newX >= m || newY < 0 || newY >= n || mazeGrid[newX][newY] == 'W')
                        continue;
                    char c = mazeGrid[newX][newY];
                    // If the current cell is a key, add it to the keys
                    int newKeys = ('a' <= c && c <= 'f') ? (keys | (1 << (c - 'a'))) : keys;
                    // If all keys are collected, return the result
                    if (newKeys == keyChecker)
                        return result;
                    // If the current state is already visited, skip it
                    if (seen[newX][newY][newKeys])
                        continue;
                    // If the current cell is a door and the corresponding key is not collected, skip it
                    if ('A' <= c && c <= 'F' && ((keys >> (c - 'A')) & 1) == 0)
                        continue;
                    // Add the new state to the queue and mark it as visited
                    currentState.offer(new StateChecker(newX, newY, newKeys));
                    seen[newX][newY][newKeys] = true;
                }
            }
        }

        // If no path is found, return -1
        return -1;
    }

    // Function to count the total number of keys in the maze
    public int getKeysCount(char[][] mazeGrid) {
        int count = 0;
        for (char[] row : mazeGrid) {
            for (char c : row) {
                if (c >= 'a' && c <= 'f') {
                    count++;
                }
            }
        }
        return count;
    }

    // Function to find the starting position in the maze
    public int[] getStart(char[][] mazeGrid) {
        for (int i = 0; i < mazeGrid.length; i++) {
            for (int j = 0; j < mazeGrid[0].length; j++) {
                if (mazeGrid[i][j] == 'S') {
                    return new int[] { i, j };
                }
            }
        }
        throw new IllegalArgumentException("Start point not found.");
    }

    public static void main(String[] args) {
        char[][] mazeGrid = {
                { 'S', 'P', 'a', 'P', 'P' },
                { 'W', 'W', 'W', 'P', 'W' },
                { 'b', 'P', 'A', 'P', 'B' }
        };

        a_MazeGame game = new a_MazeGame();
        int shortestPathLength = game.shortestPath(mazeGrid);
        if (shortestPathLength != -1) {
            System.out.println("Shortest path length: " + shortestPathLength);
        } else {
            System.out.println("No path found to reach the destination.");
        }
    }
}
