import java.util.LinkedList;
import java.util.Queue;

public class DeserializeParenTree {
    /**圣诞课堂tree思考题
     * step1: 首个元素一定是当前root，先记录再idx++
     * step2: 遇到?直接开始扫描，找到与它配对的那一层:。
     *        recursive call-- :右边直接接到root右边，?和:之间接到root左边
     * step3: return root
     *
     * TC = O(n^2)
     * SC = O(n)
     * @param s
     * @return
     */
    public SerialTreeNode deserializeTriConditionTree(String s) {
        if (s == null) {
            return null;
        }

        return triConditionHelper(s, 0, s.length()-1);
    }

    private SerialTreeNode triConditionHelper(String s, int left, int right) {
        if (left == right) {
            return new SerialTreeNode(s.charAt(left)-'0');
        }

        SerialTreeNode root = new SerialTreeNode(s.charAt(left)-'0');
        left++;

        int colonPos = findMatchColon(s, 1, left+1);
        root.left = triConditionHelper(s, left+1, colonPos-1);
        root.right = triConditionHelper(s, colonPos+1, right);

        return root;
    }

    private int findMatchColon(String s, int counter, int index) {
        while (index < s.length() && counter > 0) {
            if (s.charAt(index) == '?') {
                counter++;
            } else if (s.charAt(index) == ':') {
                counter--;
            }
            index++;
        }
        return index-1;
    }

    public SerialTreeNode deserializeParenthesisTree(String s) {
        if (s == null || s.length() == 0) {
            return null;
        }
        int[] index = new int[1];
        SerialTreeNode root = recurison(s, index);
        return root;
    }

    private SerialTreeNode recurison(String s, int[] index) {
        // base case
        if (index[0] == s.length()) {
            return null;
        }

        // current level: fetch value for current node
        int num = 0;
        while (index[0] < s.length() && Character.isDigit(s.charAt(index[0]))) {
            int digit = s.charAt(index[0]) - '0';
            num = num*10+digit;
            index[0]++;
        }
        SerialTreeNode root = num == 0 ? null : new SerialTreeNode(num);

        // early return
        if (index[0] == s.length()) {
            return root;
        }

        // 不需要额外处理)，在返回上一层后自然会有index++跳过)
        // left child
        if (s.charAt(index[0]) == '(') {
            index[0]++; // bypass the (
            root.left = recurison(s, index);
            index[0]++; // bypass the )
        }

        // right child
        if (s.charAt(index[0]) == '(') {
            index[0]++; // bypass the (
            root.right = recurison(s, index);
            index[0]++; // bypass the )
        }

        return root;
    }

    private void bfsHelper(SerialTreeNode root) {
        Queue<SerialTreeNode> queue = new LinkedList<>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i=0; i<size; i++) {
                SerialTreeNode cur = queue.poll();
                System.out.print(cur.val+" ");
                if (cur.left != null) {
                    queue.offer(cur.left);
                }
                if (cur.right != null) {
                    queue.offer(cur.right);
                }
            }
            System.out.println("");
        }
    }

    private void inOrderHelper(SerialTreeNode root) {
        if (root == null) {
            System.out.print("null, ");
            return;
        }

        inOrderHelper(root.left);
        System.out.print(root.val+", ");
        inOrderHelper(root.right);
    }

    public static void main(String[] args) {
        DeserializeParenTree dpt = new DeserializeParenTree();
        /*
        String s1 = "5(4(2)(3))(6()(7))";
        String s2 = "12(8(2()())(10(9)(11)))(17(14(13)(16))(18()()))";
        SerialTreeNode root = dpt.deserializeParenthesisTree(s2);
        dpt.bfsHelper(root);
        dpt.inOrderHelper(root);
        */


        String s3 = "1?2:3";
        String s4 = "1?2?3:4:5";
        String s5 = "1?2:3?4:5";
        SerialTreeNode root1 = dpt.deserializeTriConditionTree(s3);
        SerialTreeNode root2 = dpt.deserializeTriConditionTree(s4);
        SerialTreeNode root3 = dpt.deserializeTriConditionTree(s5);
        dpt.bfsHelper(root1);
        dpt.inOrderHelper(root1);
        System.out.println("----------------------------------------");
        dpt.bfsHelper(root2);
        dpt.inOrderHelper(root2);
        System.out.println("----------------------------------------");
        dpt.bfsHelper(root3);
        dpt.inOrderHelper(root3);


    }
}

class SerialTreeNode {
    int val;
    SerialTreeNode left;
    SerialTreeNode right;

    SerialTreeNode(int v) {
        this.val = v;
        this.left = null;
        this.right = null;
    }
}