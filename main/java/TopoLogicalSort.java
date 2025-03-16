import java.util.*;

public class TopoLogicalSort {
    /**
     * Harry老师topo sort讲座例题1
     *
     * 该选课问题可以model成graph问题。其中preReq的dependencies关系看作edge,每门课看作vertex
     * 对于如下例子建图如下：
     *     1
     *   /   \
     * 0       3
     *   \   /
     *     2
     *
     * 根据题目要求，0没有preReq可以先修。修完0后，1，2也没有其他的preReq，因此可以修1，2。
     * 1,2也完成后3也没有其他preReq了，可以修。最后一共修了4门课，和题目给的numOfCourses相等。说明可以全完成
     * 因此这道题是topologicalSort问题
     *
     * step1: 将preReq建成adjacent list。并用一个int[] inDegree记录所有courses的indegree
     * step2: 扫描inDegree，将入度为0的点全部加入queue
     * step3: run BFS:
     *         expand:
     *              cur = queue.poll()
     *              courseAlreadyTaken++
     *         generate:
     *              遍历cur记录在adjacent list里所有nei，在inDegree数组更新它们的入度
     *              某课程入度 == 0， 加入queue
     * step4: check coursesAlreadyTaken == numOfCourses
     *         相同返回true,不同返回false
     *
     * TC = O(E+V)
     * SC = O(E+V) (邻接表会对所有顶点分配List,然后每条边的终点都存一次)
     *
     * @param numOfCourses
     * @param preReq
     * @return
     */
    public boolean canFinishAllCourses(int numOfCourses, int[][] preReq) {
        int[] inDegree = new int[numOfCourses];
        List<List<Integer>> preReqGraph = new ArrayList<>();
        buildGraph(preReq, preReqGraph, inDegree, numOfCourses);

        Queue<Integer> queue = new LinkedList<>();
        // 初始化
        for (int i=0; i<numOfCourses; i++) {
            if (inDegree[i] == 0) {
                queue.offer(i);
            }
        }

        int coursesAlreadyTaken = 0;
        while (!queue.isEmpty()) {
            int cur = queue.poll();
            coursesAlreadyTaken++;
            for (int nei: preReqGraph.get(cur)) {
                inDegree[nei]--;
                if (inDegree[nei] == 0) {
                    queue.offer(nei);
                }
            }
        }

        return coursesAlreadyTaken == numOfCourses;
    }

    private void buildGraph(int[][] preReq, List<List<Integer>> graph, int[] inDegree, int numOfCourses) {
        for (int i=0; i<numOfCourses; i++) {
            graph.add(new ArrayList<>());
        }

        for (int[] dependency: preReq) {
            int pre = dependency[1];
            int cur = dependency[0];
            graph.get(pre).add(cur);
            inDegree[cur]++;
        }
    }

    /**
     Harry老师topo sort讲座例题2: rich and loud
     richer代表{richer的人， poorer的人},保证一定逻辑正确没有环。 quite表示每个人聒噪程度
     result[i]表示对于每个人而言，所有和他一样富（包括自己）/更富的人里最安静的人

     根据richer输入可知，该题可以model成graph问题(node: 人， edge: 富有关系)。使用adjacent list来建模
     因为result要求从>=当前人富有度的人里面选，因此适合从richer->poorer遍历。方便起见有向图(adj list)也按照该方向初始化

     根据图观察，应该从最富有的人开始BFS,且这些人indegree均为0，因此这道题是topological sort问题
     step1: initiate graph, indegree array
     step2: initiate result, enqueue all people with 0 indegree
     step3: while loop BFS：
        3.1 expand cur node
        3.2 generate node's nei: for each nei:
         注意这一步，当quite[result[cur]] < quite[result[nei]]时，不可以直接result[nei] = cur。
         因为result[cur]可能来自更远的祖先
            if quite[result[cur]] < quite[result[nei]]
                result[nei] = result[cur]
            indegree[nei]--;
            if indegree[nei] == 0
                enqueue

     return result
     nodes = quite.length == m
     edges = richer.length == n
     分析同上题
     TC = O(n+m)
     SC = O(m+n)


     */
    public int[] loudRich(int[][] richer, int[] quite) {
        int edges = richer.length, nodes = quite.length;
        List<List<Integer>> richGraph = new ArrayList<>();
        int[] inDegree = new int[nodes];
        initRichGraph(richer, richGraph, inDegree, nodes);

        int[] result = new int[nodes];
        Queue<Integer> queue = new LinkedList<>();
        for (int i=0; i<nodes; i++) {
            result[i] = i;
            if (inDegree[i] == 0) {
                queue.offer(i);
            }
        }

        while (!queue.isEmpty()) {
            int cur = queue.poll();
            for (int nei: richGraph.get(cur)) {
                if (quite[result[cur]] < quite[result[nei]]) {
                    result[nei] = result[cur];
                }

                inDegree[nei]--;
                if (inDegree[nei] == 0) {
                    queue.offer(nei);
                }
            }
        }

        return result;
    }

    private void initRichGraph(int[][] richer, List<List<Integer>> richGraph, int[] inDegree, int nodes) {
        for (int i=0; i<nodes; i++) {
            richGraph.add(new ArrayList<>());
        }

        for (int[] dependency: richer) {
            int rich = dependency[0];
            int poor = dependency[1];
            richGraph.get(rich).add(poor);
            inDegree[poor]++;
        }
    }

    int[][] DIRS = {{1,0}, {-1,0}, {0,1}, {0,-1}};
    public List<String> findWords(char[][] board, String[] words) {
        int m = board.length, n = board[0].length;
        Trie trie = new Trie();
        for (String word: words) {
            trie.insert(word);
        }

        Set<String> set = new HashSet<>();
        for (int i=0; i<m; i++) {
            for (int j=0; j<n; j++) {
                dfs(board, trie, i, j, set);
            }
        }
        List<String> ans = new ArrayList<>(set);
        return ans;
    }

    private void dfs(char[][] board, Trie curNode, int row, int col, Set<String> set) {
        // base case: if current trie node contains board[row][col]
        char curChar = board[row][col];
        if (curNode.children[curChar-'a'] == null) {
            return;
        }

        curNode = curNode.children[curChar-'a'];
        // base case: in the end all chars of current word are matched
        if (!curNode.word.equals("")) {
            set.add(curNode.word);
            curNode.word = "";

        }

        // recursion rule: dedup current position, check and move on 4 directions
        board[row][col] = '#';
        for (int[] dir: DIRS) {
            int newR = dir[0]+row;
            int newC = dir[1]+col;
            if (newR >= 0 && newR < board.length &&
                    newC >= 0 && newC < board[0].length &&
                    board[newR][newC] != '#') {
                dfs(board, curNode, newR, newC, set);
            }
        }
        board[row][col] = curChar;
    }

    public static void main(String[] args) {
        TopoLogicalSort tls = new TopoLogicalSort();
        /*
        int[][] preRequisites = {{1,0}, {2,0}, {3,1}, {3,2}};
        int numOfCourses = 4;
        System.out.println("Result that if courses can all be completed: "+tls.canFinishAllCourses(numOfCourses, preRequisites));

        int[][] richer = {{1,0},{2,1},{3,1},{3,7},{4,3},{5,3},{6,3}};
        int[] quite = {3,2,5,4,6,1,7,0};
        int[] result = tls.loudRich(richer, quite);
        for (int res: result) {
            System.out.print(res+" ");
        }
        */
        char[][] board = {{'o','a','a','n'},{'e','t','a','e'},{'i','h','k','r'},{'i','f','l','v'}};
        String[] words = {"oath","pea","eat","rain","oathi","oathk","oathf","oate","oathii","oathfi","oathfii"};
        List<String> ans = tls.findWords(board, words);
        for (String s: ans) {
            System.out.print(s+" ");
        }
    }
}
class Trie {
    String word;
    Trie[] children;

    public Trie() {
        this.word = "";
        this.children = new Trie[26];
    }

    public void insert(String word) {
        Trie node = this;
        for (char c: word.toCharArray()) {
            int index = c-'a';
            if (node.children[index] == null) {
                node.children[index] = new Trie();
            }
            node = node.children[index];
        }
        node.word = word;
    }
}