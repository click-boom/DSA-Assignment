package dsa.Question5;

import java.util.Arrays;
import java.util.Random;

public class a_TSP {

    private int[][] distances; // Matrix of distances between cities
    private int numCities; // Number of cities
    private int numAnts; // Number of ants
    private double[][] pheromones; // Matrix of pheromone levels between cities
    private double alpha; // Alpha parameter controlling the importance of pheromones
    private double beta; // Beta parameter controlling the importance of distances
    private double evaporationRate; // Evaporation rate of pheromones
    private Random random; // Random number generator

    public a_TSP(int[][] distances, int numAnts, double alpha, double beta, double evaporationRate) {
        this.distances = distances;
        this.numCities = distances.length;
        this.numAnts = numAnts;
        this.alpha = alpha;
        this.beta = beta;
        this.evaporationRate = evaporationRate;
        this.random = new Random();
        initializePheromones();
    }

    // Initialize pheromones uniformly
    private void initializePheromones() {
        pheromones = new double[numCities][numCities];
        double initialPheromone = 1.0 / (numCities * numCities);
        for (int i = 0; i < numCities; i++) {
            for (int j = 0; j < numCities; j++) {
                pheromones[i][j] = initialPheromone;
            }
        }
    }

    // Find the shortest path using ant colony optimization
    public int[] findShortestPath() {
        int[] shortestPath = null;
        double shortestDistance = Double.POSITIVE_INFINITY;

        // Run iterations
        for (int iteration = 0; iteration < 1000; iteration++) {
            int[][] antPaths = new int[numAnts][numCities];
            double[] antDistances = new double[numAnts];

            // Construct solutions using ant paths
            for (int ant = 0; ant < numAnts; ant++) {
                int startCity = random.nextInt(numCities);
                antPaths[ant][0] = startCity;
                boolean[] visited = new boolean[numCities];
                visited[startCity] = true;
                double distance = 0;

                // Build ant path
                for (int step = 1; step < numCities; step++) {
                    int currentCity = antPaths[ant][step - 1];
                    int nextCity = selectNextCity(currentCity, visited);
                    antPaths[ant][step] = nextCity;
                    visited[nextCity] = true;
                    distance += distances[currentCity][nextCity];
                }

                antDistances[ant] = distance;
            }

            // Update pheromones based on ant paths
            updatePheromones(antPaths, antDistances);

            // Find the shortest path among ant paths
            for (int ant = 0; ant < numAnts; ant++) {
                if (antDistances[ant] < shortestDistance) {
                    shortestDistance = antDistances[ant];
                    shortestPath = Arrays.copyOf(antPaths[ant], numCities);
                }
            }
        }

        return shortestPath;
    }

    // Select next city based on pheromone levels and distances
    private int selectNextCity(int currentCity, boolean[] visited) {
        double[] probabilities = new double[numCities];
        double sum = 0;

        // Calculate probabilities for unvisited cities
        for (int city = 0; city < numCities; city++) {
            if (!visited[city]) {
                probabilities[city] = Math.pow(pheromones[currentCity][city], alpha) * Math.pow(1.0 / distances[currentCity][city], beta);
                sum += probabilities[city];
            }
        }

        // Select next city probabilistically
        double r = random.nextDouble() * sum;
        double partialSum = 0;
        for (int city = 0; city < numCities; city++) {
            if (!visited[city]) {
                partialSum += probabilities[city];
                if (partialSum >= r) {
                    return city;
                }
            }
        }

        throw new RuntimeException("Should not happen");
    }

    // Update pheromones based on ant paths
    private void updatePheromones(int[][] antPaths, double[] antDistances) {
        // Evaporate pheromones
        for (int i = 0; i < numCities; i++) {
            for (int j = 0; j < numCities; j++) {
                pheromones[i][j] *= (1 - evaporationRate);
            }
        }

        // Update pheromones based on ant paths
        for (int ant = 0; ant < numAnts; ant++) {
            for (int i = 0; i < numCities - 1; i++) {
                int city1 = antPaths[ant][i];
                int city2 = antPaths[ant][i + 1];
                pheromones[city1][city2] += 1.0 / antDistances[ant];
                pheromones[city2][city1] += 1.0 / antDistances[ant];
            }
        }
    }

    public static void main(String[] args) {
        int[][] cityDistances = {
            {0, 12, 25, 18},
            {12, 0, 35, 30},
            {25, 35, 0, 10},
            {18, 30, 10, 0}
        };
        int numAnts = 10;
        double alpha = 1.0;
        double beta = 5.0;
        double evaporationRate = 0.5;

        a_TSP aco = new a_TSP(cityDistances, numAnts, alpha, beta, evaporationRate);
        int[] shortestPath = aco.findShortestPath();

        System.out.println("Shortest path found:");
        for (int city : shortestPath) {
            System.out.print(city + " ");
        }
        System.out.println();
    }
}
