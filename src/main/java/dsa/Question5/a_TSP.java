package dsa.Question5;

public class a_TSP {
    
    static class City {
        int x;
        int y;
        public City(int x, int y){
            this.x = x;
            this.y = y;
        }
    }

    static class Ant {
        int curCity;
        int nextCity;
        int pathIndex;
        int[] tabu;
        int[] path;
        double tourLength;

        public Ant(){
            // Initialize the ant with starting values
            curCity = 0;
            nextCity = -1;
            pathIndex = 1;
            tabu = new int[a_TSP.numCities];
            path = new int[a_TSP.numCities];
            tourLength = 0;
            tabu[curCity] = 1; // Mark the current city as visited
        }

        public void selectNextCity(double[][] pheromone){
            // Calculate probabilities of selecting each city as the next city
            double[] p = new double[a_TSP.numCities];
            double sum = 0;
            for(int i=0; i<a_TSP.numCities; i++){
                if(tabu[i] == 0){
                    // Calculate the probability based on pheromone level and distance
                    p[i] = Math.pow(pheromone[curCity][i], a_TSP.alpha) * Math.pow(1.0/a_TSP.distance[curCity][i], a_TSP.beta);
                    sum += p[i];
                }
            }
            // Normalize probabilities
            double[] prob = new double[a_TSP.numCities];
            for(int i=0; i<a_TSP.numCities; i++){
                if(tabu[i] == 0){
                    prob[i] = p[i]/sum;
                }
            }
            // Choose the next city based on probability
            double r = Math.random();
            double s = 0;
            for(int i=0; i<a_TSP.numCities; i++){
                if(tabu[i] == 0){
                    s += prob[i];
                    if(s >= r){
                        nextCity = i;
                        break;
                    }
                }
            }
        }

        public void move(){
            // Move to the next city
            path[pathIndex++] = nextCity;
            tabu[nextCity] = 1; // Mark the next city as visited
            tourLength += a_TSP.distance[curCity][nextCity]; // Update the tour length
            curCity = nextCity;
        }

        public void updatePheromone(double[][] pheromone){
            // Update pheromone levels on the path taken by the ant
            for(int i=0; i<a_TSP.numCities-1; i++){
                int from = path[i];
                int to = path[i+1];
                pheromone[from][to] += a_TSP.Q/tourLength; // Add pheromone inversely proportional to the tour length
                pheromone[to][from] = pheromone[from][to]; // Update pheromone on both directions (symmetric)
            }
            int from = path[a_TSP.numCities-1];
            int to = path[0];
            pheromone[from][to] += a_TSP.Q/tourLength; // Update pheromone for returning to the starting city
            pheromone[to][from] = pheromone[from][to];
        }
    }

    static int numCities = 5; // Number of cities in the problem
    static int numAnts = 10; // Number of ants in the colony
    static int numIterations = 100; // Number of iterations
    static double alpha = 1; // Pheromone importance factor
    static double beta = 5; // Visibility (distance) importance factor
    static double rho = 0.5; // Pheromone evaporation rate
    static double Q = 100; // Pheromone constant
    static double[][] distance; // Distance matrix between cities
    static double[][] pheromone; // Pheromone matrix

    public static void main(String[] args){
        // Define the cities and calculate the distances between them
        City[] cities = new City[numCities];
        cities[0] = new City(60, 200);
        cities[1] = new City(180, 200);
        cities[2] = new City(80, 180);
        cities[3] = new City(140, 180);
        cities[4] = new City(20, 160);
        distance = new double[numCities][numCities];
        for(int i=0; i<numCities-1; i++){
            for(int j=i+1; j<numCities; j++){
                double dx = cities[i].x - cities[j].x;
                double dy = cities[i].y - cities[j].y;
                double d = Math.sqrt(dx*dx + dy*dy);
                distance[i][j] = d;
                distance[j][i] = d;
            }
        }
        // Initialize pheromone levels on each edge
        pheromone = new double[numCities][numCities];
        for(int i=0; i<numCities; i++){
            for(int j=0; j<numCities; j++){
                pheromone[i][j] = 1.0/numCities; // Initialize with equal amounts of pheromone
            }
        }
        // Initialize ants
        Ant[] ants = new Ant[numAnts];
        for(int i=0; i<numAnts; i++){
            ants[i] = new Ant();
        }
        // Perform iterations
        for(int iter=0; iter<numIterations; iter++){
            // Move each ant
            for(Ant ant : ants){
                for(int i=0; i<numCities-1; i++){
                    ant.selectNextCity(pheromone); // Select next city for the ant
                    ant.move(); // Move to the next city
                }
                ant.updatePheromone(pheromone); // Update pheromone levels on the path taken
            }
            // Evaporate pheromone
            for(int i=0; i<numCities; i++){
                for(int j=0; j<numCities; j++){
                    pheromone[i][j] *= rho; // Evaporate pheromone on each edge
                }
            }
        }
        // Find the best tour found by the ants
        double bestTourLength = Double.MAX_VALUE;
        int[] bestTour = new int[numCities];
        for(Ant ant : ants){
            if(ant.tourLength < bestTourLength){
                bestTourLength = ant.tourLength;
                bestTour = ant.path.clone();
            }
        }
        // Print the results
        System.out.println("Best tour length: " + bestTourLength);
        System.out.print("Best tour order: ");
        for(int i : bestTour){
            System.out.print(i + " ");
        }
        System.out.println();
    }
}
