package dsa.Question3;
import java.util.*;

// Class representing an edge in the graph
class Edge implements Comparable<Edge> {
    int source, destination, weight;

    // Constructor to initialize the edge
    public Edge(int source, int destination, int weight) {
        this.source = source;
        this.destination = destination;
        this.weight = weight;
    }

    // Method to compare edges based on their weights
    @Override
    public int compareTo(Edge compareEdge) {
        return this.weight - compareEdge.weight;
    }
}

// Class representing the graph
class Graph {
    int V, E;
    Edge[] edges;

    // Constructor to initialize the graph
    public Graph(int V, int E) {
        this.V = V;
        this.E = E;
        edges = new Edge[E];
        for (int i = 0; i < E; i++)
            edges[i] = new Edge(0, 0, 0);
    }

    // Method to find the Minimum Spanning Tree using Kruskal's algorithm
    public void kruskalMST() {
        Edge[] result = new Edge[V];
        int e = 0;
        int i = 0;
        for (i = 0; i < V; ++i)
            result[i] = new Edge(0, 0, 0);

        // Sort the edges in ascending order of weight
        Arrays.sort(edges);

        // Create a disjoint set
        DisjointSet ds = new DisjointSet(V);

        i = 0;

        // Iterate through sorted edges
        while (e < V - 1) {
            Edge next_edge = edges[i++];

            // Find the parents of the source and destination vertices
            int x = ds.find(next_edge.source);
            int y = ds.find(next_edge.destination);

            // If including this edge does not form a cycle, include it in the result
            if (x != y) {
                result[e++] = next_edge;
                ds.union(x, y);
            }
        }

        // Print the result
        for (i = 0; i < e; ++i)
            System.out.println(result[i].source + " -- " + result[i].destination + " == " + result[i].weight);
    }
}

// Class representing a disjoint set
class DisjointSet {
    int[] parent, rank;
    int n;

    // Constructor to initialize the disjoint set
    public DisjointSet(int n) {
        this.n = n;
        parent = new int[n];
        rank = new int[n];
        for (int i = 0; i < n; i++) {
            parent[i] = i;
            rank[i] = 0;
        }
    }

    // Method to find the parent of a vertex
    int find(int u) {
        if (u != parent[u])
            parent[u] = find(parent[u]);
        return parent[u];
    }

    // Method to perform union operation
    void union(int x, int y) {
        int xRoot = find(x), yRoot = find(y);

        if (rank[xRoot] < rank[yRoot])
            parent[xRoot] = yRoot;
        else if (rank[xRoot] > rank[yRoot])
            parent[yRoot] = xRoot;
        else {
            parent[yRoot] = xRoot;
            rank[xRoot]++;
        }
    }
}

// Main class to test Kruskal's algorithm
public class b_KruskalAlgorithm {
    public static void main(String[] args) {
        int V = 4; // Number of vertices in graph
        int E = 5; // Number of edges in graph
        Graph graph = new Graph(V, E);

        // Add edges to the graph
        graph.edges[0] = new Edge(0, 1, 10); // add edge 0-1
        graph.edges[1] = new Edge(0, 2, 6);  // add edge 0-2
        graph.edges[2] = new Edge(0, 3, 5);  // add edge 0-3
        graph.edges[3] = new Edge(1, 3, 15); // add edge 1-3
        graph.edges[4] = new Edge(2, 3, 4);  // add edge 2-3

        // Find and print the Minimum Spanning Tree using Kruskal's algorithm
        graph.kruskalMST();
    }
}
