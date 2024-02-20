package dsa.Question3;

import java.util.ArrayList;
import java.util.List;

class PriorityQueue<T extends Comparable<T>> {
    private List<T> heap;

    public PriorityQueue() {
        heap = new ArrayList<>();
    }

    public void insert(T elem) {
        heap.add(elem);
        int index = heap.size() - 1;
        while (index > 0) {
            int parentIndex = (index - 1) / 2;
            if (heap.get(index).compareTo(heap.get(parentIndex)) >= 0) {
                break;
            }
            swap(index, parentIndex);
            index = parentIndex;
        }
    }

    public T extractMin() {
        if (heap.isEmpty()) {
            return null;
        }

        T min = heap.get(0);
        heap.set(0, heap.get(heap.size() - 1));
        heap.remove(heap.size() - 1);

        int index = 0;
        while (true) {
            int leftChildIdx = 2 * index + 1;
            int rightChildIdx = 2 * index + 2;
            int smallest = index;

            if (leftChildIdx < heap.size() && heap.get(leftChildIdx).compareTo(heap.get(smallest)) < 0) {
                smallest = leftChildIdx;
            }
            if (rightChildIdx < heap.size() && heap.get(rightChildIdx).compareTo(heap.get(smallest)) < 0) {
                smallest = rightChildIdx;
            }
            if (smallest != index) {
                swap(index, smallest);
                index = smallest;
            } else {
                break;
            }
        }

        return min;
    }

    private void swap(int i, int j) {
        T temp = heap.get(i);
        heap.set(i, heap.get(j));
        heap.set(j, temp);
    }

    public boolean isEmpty() {
        return heap.isEmpty();
    }
}

public class b_PriorityQueue {
    public static void main(String[] args) {
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        pq.insert(5);
        pq.insert(3);
        pq.insert(8);
        pq.insert(1);

        while (!pq.isEmpty()) {
            System.out.print(pq.extractMin() + " ");
        }
    }
}
