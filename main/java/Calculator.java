import java.util.*;

public class Calculator {
    public int calculate(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }

        Deque<Integer> stack = new ArrayDeque<>();
        int ans = 0;
        int num = 0;
        char preOp = '+'; // 预设第一个元素的preOp是+
        for (int i=0; i<s.length(); i++) {
            char c = s.charAt(i);
            if (Character.isDigit(c)) { // 数字时
                num = num*10 + c-'0';
            }
            if (!Character.isDigit(c) && c != ' ' || i == s.length()-1) { // 符号时。先入栈，再更新preOp和num。当最后一个c时立刻操作+入栈
                switch (preOp) {
                    case '+':
                        stack.offerFirst(num);
                        break;
                    case '-':
                        stack.offerFirst(-num);
                        break;
                    case '*':
                        stack.offerFirst(stack.pollFirst()*num);
                        break;
                    default:
                        stack.offerFirst(stack.pollFirst()/num);
                }
                preOp = c;
                num = 0;
            }
        }

        while (stack.size() > 0) {
            ans += stack.pollFirst();
        }
        return ans;
    }

    int minP;
    public int groupPlayers(List<Integer> skills, int minPlayer, int minSkill, int maxSkill) {
        Collections.sort(skills);
        List<Integer> cur = new ArrayList<>();
        int left = findLeft(skills, minSkill);
        int right = findRight(skills, maxSkill);
        int[] ans = new int[1];
        this.minP = minPlayer;
        dfs(skills, left, right, cur, ans);
        return ans[0];
    }

    private int findLeft(List<Integer> skills, int minSkill) {
        for (int i = 0; i < skills.size(); i++) {
            int curE = skills.get(i);
            if (curE >= minSkill) {
                return i;
            }
        }
        return -1;
    }

    private int findRight(List<Integer> skills, int maxSkill) {
        int prev = -1;
        for (int i=0; i<skills.size(); i++) {
            int curE = skills.get(i);
            if (curE < maxSkill) {
                prev = i;
            } else {
                if (curE == maxSkill) {
                    return i;
                }
                break;
            }
        }
        return prev;
    }

    private void dfs(List<Integer> skills, int l, int r, List<Integer> cur, int[] ans) {
        if (l > r) {
            if (cur.size() >= minP) {
                ans[0]++;
                System.out.println(cur);
            }
            return;
        }

        cur.add(skills.get(l));
        dfs(skills, l+1, r, cur, ans);
        cur.remove(cur.size()-1);

        dfs(skills, l+1, r, cur, ans);
    }

    public static void main(String[] args) {
        Calculator c = new Calculator();
        //System.out.println(c.calculate("42"));

        List<Integer> skills = new ArrayList<>();
        skills.add(12);
        skills.add(4);
        skills.add(6);
        skills.add(13);
        skills.add(5);
        skills.add(10);

        System.out.println(c.groupPlayers(skills, 3, 4, 10));
    }
}
