package dsa.Question4;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

class TreeNode {
    int value;
    TreeNode left, right;

    public TreeNode(int value) {
        this.value = value;
        this.left = this.right = null;
    }
}

public class b_FindNearest {
    public List<Integer> closestValues(TreeNode root, double target, int x) {
        List<Integer> result = new ArrayList<>();
        Stack<Integer> predecessorStack = new Stack<>();
        Stack<Integer> successorStack = new Stack<>();

        // Initialize both stacks during the in-order traversal
        inorderTraversal(root, target, false, predecessorStack);
        inorderTraversal(root, target, true, successorStack);

        // Merge the stacks to find the x closest valueues
        while (x-- > 0) {
            if (predecessorStack.isEmpty()) {
                result.add(successorStack.pop());
            } else if (successorStack.isEmpty()) {
                result.add(predecessorStack.pop());
            } else if (Math.abs(predecessorStack.peek() - target) < Math.abs(successorStack.peek() - target)) {
                result.add(predecessorStack.pop());
            } else {
                result.add(successorStack.pop());
            }
        }

        return result;
    }

    private static void inorderTraversal(TreeNode root, double target, boolean reverse, Stack<Integer> stack) {
        if (root == null) {
            return;
        }

        Stack<TreeNode> nodeStack = new Stack<>();
        TreeNode current = root;

        while (current != null || !nodeStack.isEmpty()) {
            while (current != null) {
                nodeStack.push(current);
                current = (reverse) ? current.right : current.left;
            }

            current = nodeStack.pop();

            if (!reverse && current.value > target) {
                break;
            }
            if (reverse && current.value <= target) {
                break;
            }

            stack.push(current.value);

            current = (reverse) ? current.left : current.right;
        }
    }

    public static void main(String[] args) {
        b_FindNearest valueFinder=new b_FindNearest();
        TreeNode root = new TreeNode(4);
        root.left = new TreeNode(2);
        root.right = new TreeNode(5);
        root.left.left = new TreeNode(1);
        root.left.right = new TreeNode(3);

        double target = 3.8;
        int x = 2;

        List<Integer> closestValues = valueFinder.closestValues(root, target, x);
        closestValues.sort(null);
        System.out.println(closestValues); 
    }
}