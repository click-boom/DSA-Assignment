package dsa.Question3;

import java.util.ArrayList;
import java.util.List;

// Priority Queue implementation using a min-heap
class PriorityQueue<T extends Comparable<T>> {
    private List<T> heap;

    // Constructor to initialize the priority queue
    public PriorityQueue() {
        heap = new ArrayList<>();
    }

    // Method to insert an element into the priority queue
    public void insert(T elem) {
        heap.add(elem);
        int index = heap.size() - 1;
        while (index > 0) {
            int parentIndex = (index - 1) / 2;
            // If the current element is greater than or equal to its parent, break
            if (heap.get(index).compareTo(heap.get(parentIndex)) >= 0) {
                break;
            }
            // Otherwise, swap the current element with its parent and update the index
            swap(index, parentIndex);
            index = parentIndex;
        }
    }

    // Method to extract the minimum element from the priority queue
    public T extractMin() {
        if (heap.isEmpty()) {
            return null;
        }

        // Extract the minimum element (root of the heap)
        T min = heap.get(0);
        // Replace the root with the last element of the heap
        heap.set(0, heap.get(heap.size() - 1));
        heap.remove(heap.size() - 1);

        // Heapify down to maintain the heap property
        int index = 0;
        while (true) {
            int leftChildIdx = 2 * index + 1;
            int rightChildIdx = 2 * index + 2;
            int smallest = index;

            // Find the smallest child of the current node
            if (leftChildIdx < heap.size() && heap.get(leftChildIdx).compareTo(heap.get(smallest)) < 0) {
                smallest = leftChildIdx;
            }
            if (rightChildIdx < heap.size() && heap.get(rightChildIdx).compareTo(heap.get(smallest)) < 0) {
                smallest = rightChildIdx;
            }
            // If the smallest child is smaller than the current node, swap them
            if (smallest != index) {
                swap(index, smallest);
                index = smallest;
            } else {
                break;
            }
        }

        return min;
    }

    // Method to swap two elements in the heap
    private void swap(int i, int j) {
        T temp = heap.get(i);
        heap.set(i, heap.get(j));
        heap.set(j, temp);
    }

    // Method to check if the priority queue is empty
    public boolean isEmpty() {
        return heap.isEmpty();
    }
}

// Main class to test the priority queue implementation
public class b_PriorityQueue {
    public static void main(String[] args) {
        // Create a priority queue of integers
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        // Insert elements into the priority queue
        pq.insert(5);
        pq.insert(3);
        pq.insert(8);
        pq.insert(1);

        // Extract and print the minimum elements until the priority queue is empty
        while (!pq.isEmpty()) {
            System.out.print(pq.extractMin() + " ");
        }
    }
}
