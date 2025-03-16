import java.util.*;

public class DFS {
    // 例题1.2
    public List<String> allSubSetII(String input) {
        List<String> ans = new ArrayList<>();
        if (input.length() == 1) {
            ans.add("");
            ans.add(input);
            return ans;
        }

        Set<String> set = new HashSet<>();
        StringBuilder sb = new StringBuilder();
        dfsAllSubSetII(input, 0, sb, set, ans);
        return ans;
    }

    private void dfsAllSubSetII(String input, int index, StringBuilder sb, Set<String> set, List<String> ans) {
        if (index == input.length()) {
            if (!set.contains(sb.toString())) {
                String curAns = new String(sb);
                ans.add(curAns);
                set.add(curAns);
            }
            return;
        }

        sb.append(input.charAt(index));
        dfsAllSubSetII(input, index+1, sb, set, ans);
        sb.deleteCharAt(sb.length()-1);

        dfsAllSubSetII(input, index+1, sb, set, ans);
    }

    public List<String> allSubSetIII(String input) {
        List<String> ans = new ArrayList<>();
        if (input.length() == 1) {
            ans.add("");
            ans.add(input);
            return ans;
        }

        Map<Character, Integer> map = new HashMap<>();
        for (int i=0; i<input.length(); i++) {
            char c = input.charAt(i);
            map.put(c, map.getOrDefault(c, 0)+1);
        }

        StringBuilder sb = new StringBuilder();
        dfsAllSubSetIII(input, 0, sb, map, ans);
        return ans;
    }

    private void dfsAllSubSetIII(String input, int index, StringBuilder sb, Map<Character, Integer> map, List<String> ans) {
        if (index == input.length()) {
            ans.add(new String(sb));
            return;
        }

        char c = input.charAt(index);
        int numOfChar = map.get(c);

        // add
        for (int count = 1; count <= numOfChar; count++) {
            for (int i=1; i<=count; i++) {
                sb.append(c);
            }
            dfsAllSubSetIII(input, index+numOfChar, sb, map, ans);
            for (int i=1; i<=count; i++) {
                sb.deleteCharAt(sb.length()-1);
            }
        }

        // no add
        dfsAllSubSetIII(input, index+numOfChar, sb, map, ans);
    }

    // 例题1.3
    public int minSubSetSumDiff(int[] input) {
        if (input.length == 1) {
            return input[0];
        }

        int total = 0;
        for (int i: input) {
            total += i;
        }
        int[] ans = new int[1];
        ans[0] = Integer.MAX_VALUE;
        List<Integer> subAns = new ArrayList<>();
        dfsMinSubSetSumDiff(input, 0, subAns, total, ans);
        return ans[0];
    }

    private void dfsMinSubSetSumDiff(int[] input, int index, List<Integer> subAns, int total, int[] ans) {
        if (index == input.length) {
            int curSum = 0;
            for (int i: subAns) {
                curSum += i;
            }
            int diff = Math.abs(2*curSum - total);
            ans[0] = Math.min(ans[0], diff);
            return;
        }

        subAns.add(input[index]);
        dfsMinSubSetSumDiff(input, index+1, subAns, total, ans);
        subAns.remove(subAns.size()-1);

        dfsMinSubSetSumDiff(input, index+1, subAns, total, ans);
    }

    // 例题1.4举一反二
    public List<String> allSubSeqOfStrWithDup(String input) {
        List<String> ans = new Stack<>();
        if (input.length() == 1) {
            ans.add("");
            ans.add(input);
            return ans;
        }

        StringBuilder sb = new StringBuilder();
        dfsAllSubSeqOfStrWithDup(input, 0, sb, ans);
        return ans;
    }

    private void dfsAllSubSeqOfStrWithDup(String input, int index, StringBuilder sb, List<String> ans) {
        if (index == input.length()) {
            ans.add(new String(sb));
            return;
        }

        // case1: 加上当前元素
        sb.append(input.charAt(index));
        dfsAllSubSeqOfStrWithDup(input, index+1, sb, ans);
        sb.deleteCharAt(sb.length()-1);

        // case2: 一旦不加，跳过所有该类元素
        while (index+1 < input.length() && input.charAt(index) == input.charAt(index+1)) {
            index++;
        }

        dfsAllSubSeqOfStrWithDup(input, index+1, sb, ans);
    }

    // 例题2.3举一反三
    // input:{'{','}','[',']','(',')'}, number: 3,3,2,2,1,1
    // priority: { > [ > (. valid case: {[()]}, invalid: ([{
    public List<String> allValidParentheseString(int l, int m, int n) {
        Map<Character, Integer> letterPri = new HashMap<>();
        letterPri.put('{',0);
        letterPri.put('<',1);
        letterPri.put('(',2);

        List<String> ans = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        Deque<Character> stack = new ArrayDeque<>();
        int totalLen = 2*(l+m+n);
        char[] signs = {'{', '}', '<', '>', '(', ')'};
        int[] nums = {l,l,m,m,n,n};
        dfsAllValidParentheseString(signs, nums, totalLen, letterPri, 0, stack, sb, ans);
        return ans;
    }

    private void dfsAllValidParentheseString(char[] signs, int[] nums, int totalLen, Map<Character, Integer> map, int index, Deque<Character> stack, StringBuilder sb, List<String> ans) {
        if (index == totalLen) {
            String curAns = new String(sb);
            ans.add(curAns);
            return;
        }

        for (int i=0; i<6; i++) {
            if (i % 2 == 0) {
                if ((stack.isEmpty() ||  map.get(stack.peekFirst()) < map.get(signs[i])) &&
                    nums[i] > 0) {
                    stack.offerFirst(signs[i]);
                    sb.append(signs[i]);
                    nums[i]--;
                    dfsAllValidParentheseString(signs, nums, totalLen, map, index+1, stack, sb, ans);
                    nums[i]++;
                    sb.deleteCharAt(sb.length()-1);
                    stack.pollFirst();
                }
            } else {
                // 必须要有未匹配的左括号才能加右括号
                if (!stack.isEmpty() && stack.peekFirst() == signs[i-1] && nums[i-1] < nums[i]) {
                    stack.pollFirst();
                    sb.append(signs[i]);
                    nums[i]--;
                    dfsAllValidParentheseString(signs, nums, totalLen, map, index+1, stack, sb, ans);
                    nums[i]++;
                    sb.deleteCharAt(sb.length()-1);
                    stack.offerFirst(signs[i-1]);
                }
            }
        }
    }

    // 例题 4.2 举一反一
    /**
     * 要点：每一层共用一个hashMap去重
     * input: abbbc (ab1b2b3c) 返回所有不重复的排列
     */
    public List<String> getAllPermutationsWithDupLetter(String input) {
        List<String> ans = new ArrayList<>();
        char[] s = input.toCharArray();
        dfsAllPermutationsWithDupLetter(s, 0, ans);
        return ans;
    }

    private void dfsAllPermutationsWithDupLetter(char[] input, int index, List<String> ans) {
        if (index == input.length) {
            ans.add(new String(input));
            return;
        }

        Set<Character> set = new HashSet<>();
        for (int i=index; i<input.length; i++) {
            if (!set.contains(input[i])) {
                set.add(input[i]);
                swap(input, index, i);
                dfsAllPermutationsWithDupLetter(input, index+1, ans);
                swap(input, index, i);
            }
        }
    }

    private void swap(char[] input, int x, int y) {
        char tmp = input[x];
        input[x] = input[y];
        input[y] = tmp;
    }



    // 扩展题1：匈牙利算法
    /**
     * step1: 根据choiceList创建邻接表（graph）
     * step2: 匈牙利算法，找bipartite最大匹配。
     * 用一个array match来记录全局匹配情况。对每一个friend Run DFS找是否匹配，每个人都有自己的visited array
     * visited:记录给当前friend DFS时，recursion从上到下的visit cake情况
     *
     * canMatch(friend, graph, match, visited): 一共1到cake数量层，每层存该friend匹配的cake
     * 每层case数：graph[friend].length (试着match list中的每个cake直到第一个match)
     *
     * 时间复杂度分析
     * 最坏情况下，每个朋友需要扫描graph所有人的所有边（比如n号朋友发现当前cake已经被n-1 match,再去n-1那一层发现下一个
     * 对应cake又被n-2 match...）假设graph总边数是E，人数是n，时间复杂度为E*n
     *
     * 空间复杂度分析(取极限情况分析)
     * 朋友数：n
     * 蛋糕数：m(n>m)
     * 每个朋友都喜欢所有的蛋糕。当前所有m个蛋糕都已经匹配，且每个蛋糕都只分配给唯一一个朋友。
     * 那么现在又来一个朋友u试图匹配蛋糕，发现自己的蛋糕被v1匹配。再试图重新分配v1,发现自己蛋糕被v2匹配...
     * 最后一直递归到vm，发现所有蛋糕都被visited，只能触底返回false。因此栈深度为m
     *
     * TC = O(E*n)
     * SC = O(E+cake数量)
     *
     * @param numOfFriends
     * @param choiceList
     * @param numOfRow
     * @param numOfCol
     * @return
     */
    public int maxHappyFriends(int numOfFriends, int[][] choiceList, int numOfRow, int numOfCol) {
        int numOfFruit = numOfCol*numOfRow;
        List<Integer>[] graph = new ArrayList[numOfFriends];
        for (int i=0; i<numOfFriends; i++) {
            graph[i] = new ArrayList<Integer>();
            for (int j=1; j<choiceList[i].length; j++) {
                graph[i].add(choiceList[i][j]-1);
            }
        }

        // 记录每个fruit match的friend id
        int[] matchFriendId = new int[numOfFruit];
        Arrays.fill(matchFriendId, -1);
        int ans = 0;
        for (int friend = 0; friend < numOfFriends; friend++) {
            boolean[] visited = new boolean[numOfFruit]; // 一条直上直下路径共用
            if (canMatch(graph, friend, visited, matchFriendId)) {
                ans++;
            }
        }

        return ans;
    }

    // 是否能为friend找到match fruit，包括能否前向处理好所有之前的friends matching fruits
    private boolean canMatch(List<Integer>[] graph, int friend, boolean[] fruitVisited, int[] matchFriendId) {
        for (int fruit: graph[friend]) {
            if (fruitVisited[fruit]) {
                continue;
            }

            // 对于该朋友的扫描中，fruit还未访问过
            fruitVisited[fruit] = true;
            // 检查全局，这fruit是否未被match || 如果被match了，能否为match该水果的上一个朋友找到新match
            if (matchFriendId[fruit] == -1 || canMatch(graph, matchFriendId[fruit], fruitVisited, matchFriendId)) {
                matchFriendId[fruit] = friend; //说明前向递归成功，为之前所有的friend找到了新的match。就可以安心给当前朋友match了
                return true;
            }
            fruitVisited[fruit] = false; //若需要继续枚举其他fruit，保证吃吐守恒
        }

        return false;
    }



    public static void main(String[] args) {
        DFS sol = new DFS();
        /*
        String input = "abbbc";
        List<String> allSubSet = sol.allSubSetII(input);
        for (String s: allSubSet) {
            System.out.print(s+" ");
        }
        System.out.println("");
        List<String> allSubSeq = sol.allSubSeqOfStrWithDup(input);
        for (String s: allSubSeq) {
            System.out.print(s+" ");
        }

        int[] array1 = {1, 6, 11, 5};
        int[] array2 = {1, 5, 11, 5};
        int min1 = sol.minSubSetSumDiff(array1);
        int min2 = sol.minSubSetSumDiff(array2);
        System.out.println("Min1 is "+min1);
        System.out.println("Min2 is "+min2);
        */
        /*
        int numOfFriends1 = 3;
        int[][] choiceList1 = {
                {3, 1, 2, 3},
                {1, 2},
                {1, 1}
        };
        int numOfRow1 = 2;
        int numOfCol1 = 2;
        int maxFriends1 = sol.maxHappyFriends(numOfFriends1, choiceList1, numOfRow1, numOfCol1);
        System.out.println("Max friends number is "+maxFriends1);

        String input = "abbbbbbc";
        List<String> allSubSet = sol.allSubSetIII(input);
        for (String s: allSubSet) {
            System.out.print(s+" ");
        }

        System.out.println("");

        List<String> allSubSet2 = sol.allSubSeqOfStrWithDup(input);
        for (String s: allSubSet2) {
            System.out.print(s+" ");
        }

        List<String> allParentheseStr = sol.allValidParentheseString(0,1,3);
        System.out.println(allParentheseStr);
        */


        List<String> allPermutationWithDupChar = sol.getAllPermutationsWithDupLetter("abbbc");
        System.out.println(allPermutationWithDupChar);
    }
}
