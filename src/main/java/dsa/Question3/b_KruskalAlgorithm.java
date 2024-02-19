package dsa.Question3;
import java.util.*;

class Edge implements Comparable<Edge> {
    int source, destination, weight;

    public Edge(int source, int destination, int weight) {
        this.source = source;
        this.destination = destination;
        this.weight = weight;
    }

    @Override
    public int compareTo(Edge compareEdge) {
        return this.weight - compareEdge.weight;
    }
}

class Graph {
    int V, E;
    Edge[] edges;

    public Graph(int V, int E) {
        this.V = V;
        this.E = E;
        edges = new Edge[E];
        for (int i = 0; i < E; i++)
            edges[i] = new Edge(0, 0, 0);
    }

    public void kruskalMST() {
        Edge[] result = new Edge[V];
        int e = 0;
        int i = 0;
        for (i = 0; i < V; ++i)
            result[i] = new Edge(0, 0, 0);

        Arrays.sort(edges);

        DisjointSet ds = new DisjointSet(V);

        i = 0;

        while (e < V - 1) {
            Edge next_edge = edges[i++];

            int x = ds.find(next_edge.source);
            int y = ds.find(next_edge.destination);

            if (x != y) {
                result[e++] = next_edge;
                ds.union(x, y);
            }
        }

        for (i = 0; i < e; ++i)
            System.out.println(result[i].source + " -- " + result[i].destination + " == " + result[i].weight);
    }
}

class DisjointSet {
    int[] parent, rank;
    int n;

    public DisjointSet(int n) {
        this.n = n;
        parent = new int[n];
        rank = new int[n];
        for (int i = 0; i < n; i++) {
            parent[i] = i;
            rank[i] = 0;
        }
    }

    int find(int u) {
        if (u != parent[u])
            parent[u] = find(parent[u]);
        return parent[u];
    }

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

public class b_KruskalAlgorithm {
    public static void main(String[] args) {
        int V = 4; // Number of vertices in graph
        int E = 5; // Number of edges in graph
        Graph graph = new Graph(V, E);

        // add edge 0-1
        graph.edges[0] = new Edge(0, 1, 10);
        // add edge 0-2
        graph.edges[1] = new Edge(0, 2, 6);
        // add edge 0-3
        graph.edges[2] = new Edge(0, 3, 5);
        // add edge 1-3
        graph.edges[3] = new Edge(1, 3, 15);
        // add edge 2-3
        graph.edges[4] = new Edge(2, 3, 4);

        graph.kruskalMST();
    }
}
