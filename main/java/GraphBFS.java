import java.util.*;

public class GraphBFS {
    public List<String> removeInvalidParentheses(String s) {
        List<String> result = new ArrayList<>();
        if (s == null || s.length() <= 1) {
            return result;
        }

        int minNumOfRemoval = invalidNum(s);
        Queue<String> queue = new LinkedList<>();
        Set<String> set = new HashSet<>();
        queue.offer(s);
        set.add(s);
        while (!queue.isEmpty()) {
            int levelSize = queue.size();
            minNumOfRemoval--;
            for (int i=0; i<levelSize; i++) {
                String curStr = queue.poll();
                for (int j=0; j<curStr.length(); j++) {
                    if (curStr.charAt(j) != '(' && curStr.charAt(j) != ')') {
                        continue;
                    }

                    String neiStr = curStr.substring(0, j) + curStr.substring(j + 1);
                    if (isValidString(neiStr) && minNumOfRemoval == 0 && !set.contains(neiStr)) {
                        result.add(neiStr);
                    }

                    if (set.add(neiStr) && minNumOfRemoval > 0) {
                        queue.offer(neiStr);
                    }
                }
            }
        }

        return result;
    }

    private boolean isValidString(String s) {
        int numUnmatchLeft = 0;
        for (int i=0; i<s.length(); i++) {
            if (s.charAt(i) == '(') {
                numUnmatchLeft++;
            } else if (s.charAt(i) == ')') {
                if (numUnmatchLeft > 0) {
                    numUnmatchLeft--;
                } else {
                    return false;
                }
            }
        }

        return numUnmatchLeft == 0;
    }

    private int invalidNum(String s) {
        int unMatchLeft = 0;
        int unMatchRight = 0;
        for (int i=0; i<s.length(); i++) {
            if (s.charAt(i) == '(') {
                unMatchLeft++;
            } else if (s.charAt(i) == ')') {
                if (unMatchLeft > 0) {
                    unMatchLeft--;
                } else {
                    unMatchRight++;
                }
            }
        }

        return unMatchLeft+unMatchRight;
    }

    public static void main(String[] args) {
        GraphBFS gb = new GraphBFS();
        String s1 = new String("()())()");
        String s2 = new String("(a)())()");
        String s3 = new String("((())");
        String s4 = new String(")(");
        List<String> ans1 = gb.removeInvalidParentheses(s1);
        List<String> ans2 = gb.removeInvalidParentheses(s2);
        List<String> ans3 = gb.removeInvalidParentheses(s3);
        List<String> ans4 = gb.removeInvalidParentheses(s4);
        System.out.println(ans1);
        System.out.println(ans2);
        System.out.println(ans3);
        System.out.println(ans4);
    }
}
