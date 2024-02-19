package dsa.Question5;

import java.util.*;

public class b_ISP {

    public List<Integer> findNodesWithOnlyTargetAsParent(int[][] edges, int target) {
        Map<Integer, List<Integer>> graph = new HashMap<>();
        Map<Integer, Integer> inDegree = new HashMap<>();

        // Calculate in-degree of all the nodes
        for (int[] edge : edges) {
            int from = edge[0];
            int to = edge[1];
            graph.putIfAbsent(from, new ArrayList<>());
            graph.get(from).add(to);
            inDegree.put(to, inDegree.getOrDefault(to, 0) + 1);
        }

        // Start DFS from target node
        List<Integer> result = new ArrayList<>();
        dfs(graph, inDegree, target, target, result);

        return result;
    }

    private void dfs(Map<Integer, List<Integer>> graph, Map<Integer, Integer> inDegree, int node, int target,
            List<Integer> result) {
        // If a node has only incoming edge from target node, store it in result
        if (inDegree.getOrDefault(node, 0) == 1 && graph.get(target).contains(node)) {
            result.add(node);
            // Add child nodes recursively
            addChildren(graph, node, result);
        }

        //Explore children of the current node recursively 
        if (graph.containsKey(node)) {
            for (int child : graph.get(node)) {
                dfs(graph, inDegree, child, target, result);
            }
        }
    }

    private void addChildren(Map<Integer, List<Integer>> graph, int node, List<Integer> result) {
        if (graph.containsKey(node)) {
            for (int child : graph.get(node)) {
                result.add(child);
                addChildren(graph, child, result); //Add children of the result nodes recursively
            }
        }
    }

    public static void main(String[] args) {
        int[][] edges = { { 0, 1 }, { 0, 2 }, { 1, 3 }, { 1, 6 }, { 2, 4 }, { 4, 6 }, { 4, 5 }, { 5, 7 } };
        int target = 4;
        b_ISP nodeDriver=new b_ISP();
        List<Integer> nodesEffected = nodeDriver.findNodesWithOnlyTargetAsParent(edges, target);
        System.out.print("Nodes affected because of node " + target + ": ");
        System.out.println(nodesEffected);
    }
}