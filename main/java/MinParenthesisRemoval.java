import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MinParenthesisRemoval {
    public List<String> removeInvalidParentheses(String s) {
        List<String> ans = new ArrayList<>();
        if (s.length() == 1) {
            if (Character.isLetter(s.charAt(0))) {
                ans.add(s);
            }
            return ans;
        }

        int sizeAfterMinRemoval = countRemove(s);
        StringBuilder sb = new StringBuilder();
        Set<String> set = new HashSet<>();
        dfs(s, sb, set, 0, 0, sizeAfterMinRemoval);

        for (String str: set) {
            ans.add(str);
        }
        return ans;
    }

    private int countRemove(String s) {
        int leftCnt=0, rightCnt = 0;
        int ans = s.length();
        for (int i=0; i<s.length(); i++) {
            char c = s.charAt(i);
            if (c == '(') {
                leftCnt++;
            } else if (c == ')') {
                leftCnt--;
            }

            if (leftCnt < 0) {
                ans--;
                leftCnt = 0;
            }
        }

        for (int j=s.length()-1; j>=0; j--) {
            char c = s.charAt(j);
            if (c == ')') {
                rightCnt++;
            } else if (c == '(') {
                rightCnt--;
            }

            if (rightCnt < 0) {
                ans--;
                rightCnt = 0;
            }
        }

        return ans;
    }

    private void dfs(String s, StringBuilder sb, Set<String> set, int index, int cnt, int lengthLimit) {
        // base case
        if (index == s.length()) {
            if (cnt == 0 && sb.length() == lengthLimit) {
                set.add(new String(sb));
            }
            return;
        }

        // prune
        if (cnt < 0) {
            return;
        }

        // recursion
        char c = s.charAt(index);
        // case0: letter -- must use
        if (Character.isLetter(c)) {
            sb.append(c);
            dfs(s, sb, set, index+1, cnt, lengthLimit);
            sb.deleteCharAt(sb.length()-1);
        } else {
            // case1: use
            sb.append(c);
            if (c == '(') {
                dfs(s, sb, set, index+1, cnt+1, lengthLimit);
            } else {
                dfs(s, sb, set, index+1, cnt-1, lengthLimit);
            }
            sb.deleteCharAt(sb.length()-1);

            // case2: no use
            dfs(s, sb, set, index+1, cnt, lengthLimit);
        }
    }

    public static void main(String[] args) {
        MinParenthesisRemoval mp = new MinParenthesisRemoval();
        String s1 = "()())()";
        List<String> ans = mp.removeInvalidParentheses(s1);
        for (String s: ans) {
            System.out.println(s);
        }
    }
}
