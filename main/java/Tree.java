import jdk.internal.util.xml.impl.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.WeakHashMap;

public class Tree {
    public void morrisInorder(BSTTreeNode root) {
        BSTTreeNode curr = root;
        while (curr != null) {
            if (curr.left == null) {
                // 左子树为空，直接访问当前节点
                System.out.print(curr.val + " ");
                curr = curr.right; // 移动到右子树
            } else {
                // 找前驱节点
                BSTTreeNode pre = curr.left;
                while (pre.right != null && pre.right != curr) {
                    pre = pre.right;
                }

                if (pre.right == null) {
                    // 建立链接到当前节点
                    pre.right = curr;
                    curr = curr.left; // 进入左子树
                } else {
                    // 解除链接并访问当前节点
                    pre.right = null;
                    System.out.print(curr.val + " ");
                    curr = curr.right; // 进入右子树
                }
            }
        }
    }

    public void printRangeValue(BSTTreeNode root, int k1, int k2) {
        if (root == null) {
            return;
        }

        if (root.val < k1) {
            printRangeValue(root.right, k1, k2);
        } else if (root.val > k2) {
            printRangeValue(root.left, k1, k2);
        } else {
            printRangeValue(root.left, k1, k2);
            System.out.println(root.val);
            printRangeValue(root.right, k1, k2);
        }
    }

    /*
    * Solving two sum question on a BST,
    * No extra data structure allowed(array, set)
    * The input tree can be modified
    * Serialize the tree into a doubly linked list, then solving it with two sum approach
    * */
    public List<List<BSTTreeNode>> twoSumOnBSTDLL(BSTTreeNode root, int target) {
        if (root == null) {
            return new ArrayList<>();
        }

        BSTTreeNode[] prev = new BSTTreeNode[1];
        BSTTreeNode head = getHead(root);
        BSTTreeNode tail = getTail(root);
        inOrderSerilize(root, prev);
        return twoSumHelper(target, head, tail);
    }

    private void inOrderSerilize(BSTTreeNode root, BSTTreeNode[] prev) {
        if (root == null) {
            return;
        }

        BSTTreeNode lchild = root.left;
        BSTTreeNode rchild = root.right;

        inOrderSerilize(lchild, prev);
        if (prev[0] != null) {
            prev[0].right = root;
            root.left = prev[0];
        }
        prev[0] = root;

        inOrderSerilize(rchild, prev);
    }

    private List<List<BSTTreeNode>> twoSumHelper(int target, BSTTreeNode head, BSTTreeNode tail) {
        List<List<BSTTreeNode>> ans = new ArrayList<>();
        BSTTreeNode left = head;
        BSTTreeNode right = tail;
        while (left != right) {
            int sum = left.val + right.val;
            if (sum > target) {
                right = right.left;
            } else if (sum < target) {
                left = left.right;
            } else {
                List<BSTTreeNode> cur = new ArrayList<>(Arrays.asList(left, right));
                ans.add(cur);
                left = left.right;
                right = right.left;
            }
        }
        return ans;
    }

    private BSTTreeNode getHead(BSTTreeNode root) {
        while (root.left != null) {
            root = root.left;
        }
        return root;
    }

    private BSTTreeNode getTail(BSTTreeNode root) {
        while (root.right != null) {
            root = root.right;
        }
        return root;
    }

    public static void main(String[] args) {
        Tree t = new Tree();
        BSTTreeNode root = new BSTTreeNode(1);
        root.left = new BSTTreeNode(2);
        root.right = new BSTTreeNode(3);
        root.left.left = new BSTTreeNode(4);
        root.left.right = new BSTTreeNode(5);

        t.morrisInorder(root); // 输出: 4 2 5 1 3
    }
}

class BSTTreeNode {
    BSTTreeNode left;
    BSTTreeNode right;
    int val;

    public BSTTreeNode(int val) {
        this.left = null;
        this.right = null;
        this.val = val;
    }
}
