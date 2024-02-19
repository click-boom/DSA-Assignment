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
            curCity = 0;
            nextCity = -1;
            pathIndex = 1;
            tabu = new int[a_TSP.numCities];
            path = new int[a_TSP.numCities];
            tourLength = 0;
            tabu[curCity] = 1;
        }

        public void selectNextCity(double[][] pheromone){
            double[] p = new double[a_TSP.numCities];
            double sum = 0;
            for(int i=0; i<a_TSP.numCities; i++){
                if(tabu[i] == 0){
                    p[i] = Math.pow(pheromone[curCity][i], a_TSP.alpha) * Math.pow(1.0/a_TSP.distance[curCity][i], a_TSP.beta);
                    sum += p[i];
                }
            }
            double[] prob = new double[a_TSP.numCities];
            for(int i=0; i<a_TSP.numCities; i++){
                if(tabu[i] == 0){
                    prob[i] = p[i]/sum;
                }
            }
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
            path[pathIndex++] = nextCity;
            tabu[nextCity] = 1;
            tourLength += a_TSP.distance[curCity][nextCity];
            curCity = nextCity;
        }

        public void updatePheromone(double[][] pheromone){
            for(int i=0; i<a_TSP.numCities-1; i++){
                int from = path[i];
                int to = path[i+1];
                pheromone[from][to] += a_TSP.Q/tourLength;
                pheromone[to][from] = pheromone[from][to];
            }
            int from = path[a_TSP.numCities-1];
            int to = path[0];
            pheromone[from][to] += a_TSP.Q/tourLength;
            pheromone[to][from] = pheromone[from][to];
        }
    }

    static int numCities = 5;
    static int numAnts = 10;
    static int numIterations = 100;
    static double alpha = 1;
    static double beta = 5;
    static double rho = 0.5;
    static double Q = 100;
    static double[][] distance;
    static double[][] pheromone;

    public static void main(String[] args){
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
        pheromone = new double[numCities][numCities];
        for(int i=0; i<numCities; i++){
            for(int j=0; j<numCities; j++){
                pheromone[i][j] = 1.0/numCities;
            }
        }
        Ant[] ants = new Ant[numAnts];
        for(int i=0; i<numAnts; i++){
            ants[i] = new Ant();
        }
        for(int iter=0; iter<numIterations; iter++){
            for(Ant ant : ants){
                for(int i=0; i<numCities-1; i++){
                    ant.selectNextCity(pheromone);
                    ant.move();
                }
                ant.updatePheromone(pheromone);
            }
            for(int i=0; i<numCities; i++){
                for(int j=0; j<numCities; j++){
                    pheromone[i][j] *= rho;
                }
            }
        }
        double bestTourLength = Double.MAX_VALUE;
        int[] bestTour = new int[numCities];
        for(Ant ant : ants){
            if(ant.tourLength < bestTourLength){
                bestTourLength = ant.tourLength;
                bestTour = ant.path.clone();
            }
        }
        System.out.println("Best tour length: " + bestTourLength);
        System.out.print("Best tour order: ");
        for(int i : bestTour){
            System.out.print(i + " ");
        }
        System.out.println();
    }
}
