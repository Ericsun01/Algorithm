import sun.reflect.generics.tree.Tree;

import java.util.HashMap;

public class PathSum {
    private int k;
    private int count = 0;
    private HashMap<Integer, Integer> prefixSum = new HashMap<>();

    public int pathSum(TreeNode root, int targetSum) {
        k = targetSum;
        preOrder(root, 0);
        return count;
    }

    private void preOrder(TreeNode root, int currSum) {
        if (root == null) {
            return;
        }

        currSum += root.val;
        if (currSum == k) {
            count++;
        }

        count += prefixSum.getOrDefault(currSum - k, 0);

        prefixSum.put(currSum, prefixSum.getOrDefault(currSum, 0)+1);

        preOrder(root.left, currSum);
        preOrder(root.right, currSum);

        prefixSum.put(currSum, prefixSum.get(currSum)-1);
    }

    public static void main (String[] args) {
        TreeNode n7 = new TreeNode(3);
        TreeNode n8 = new TreeNode(-2);
        TreeNode n9 = new TreeNode(1);
        TreeNode n4 = new TreeNode(3, n7, n8);
        TreeNode n5 = new TreeNode(2, null, n9);
        TreeNode n6 = new TreeNode(11);
        TreeNode n2 = new TreeNode(5, n4, n5);
        TreeNode n3 = new TreeNode(-3, null, n6);
        TreeNode n1 = new TreeNode(10, n2, n3);
 /*
                    10
                   /  \
                  5   -3
                 / \    \
                3   2   11
               / \   \
              3  -2   1
 */
        PathSum ps = new PathSum();
        int result = ps.pathSum(n1, 8);
        System.out.println(result);
    }
}

class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;
    public TreeNode () {}
    public TreeNode (int val) {
        this.val = val;
    }
    public TreeNode (int val, TreeNode left, TreeNode right) {
        this.val = val;
        this.left = left;
        this.right = right;
    }
}
