package dsa.Question4;

import java.util.*;

public class a_MazeGame {
    static class StateChecker {
        int i;
        int j;
        int keys;

        StateChecker(int i, int j, int keys) {
            this.i = i;
            this.j = j;
            this.keys = keys;
        }
    }

    public int shortestPath(char[][] mazeGrid) {
        int[][] directions = { { 0, 1 }, { 1, 0 }, { 0, -1 }, { -1, 0 } };
        int m = mazeGrid.length;
        int n = mazeGrid[0].length;
        int keysCount = getKeysCount(mazeGrid);
        int keyChecker = (1 << keysCount) - 1;
        int[] start = getStart(mazeGrid);
        // System.out.println(start);
        int result = 0;
        Queue<StateChecker> currentState = new LinkedList<>();
        currentState.offer(new StateChecker(start[0], start[1], 0));
        boolean[][][] seen = new boolean[m][n][keyChecker];
        seen[start[0]][start[1]][0] = true;

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
                    if (newX < 0 || newX >= m || newY < 0 || newY >= n || mazeGrid[newX][newY] == 'W')
                        continue;
                    char c = mazeGrid[newX][newY];
                    int newKeys = ('a' <= c && c <= 'f') ? (keys | (1 << (c - 'a'))) : keys;
                    if (newKeys == keyChecker)
                        return result;
                    if (seen[newX][newY][newKeys])
                        continue;
                    if ('A' <= c && c <= 'F' && ((keys >> (c - 'A')) & 1) == 0) // Door is locked and key is missing
                        continue;
                    currentState.offer(new StateChecker(newX, newY, newKeys));
                    seen[newX][newY][newKeys] = true;
                }
            }
        }

        return -1;
    }

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

    public int[] getStart(char[][] mazeGrid) {
        for (int i = 0; i < mazeGrid.length; i++) {
            for (int j = 0; j < mazeGrid[0].length; j++) {
                if (mazeGrid[i][j] == 'S') {
                    System.out.println(i+"    "+j);
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
