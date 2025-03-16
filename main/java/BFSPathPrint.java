import java.util.*;

public class BFSPathPrint {
    private static final int[][] DIRS = {{-1,0},{-1,1},{0,1},{1,1},
            {1,0},{1,-1},{0,-1},{-1,-1}};
    public List<int[]> findShortestPathBinaryMatrix(int[][] grid) {
        if (grid == null) {
            return null;
        }

        int n = grid.length;
        if (grid[0][0] == 1 || grid[n-1][n-1] == 1) {
            return null;
        }

        // store <self[], parent[]>
        Map<int[], int[]> parentMap = new HashMap<>();
        Queue<int[]> queue = new LinkedList<>();
        queue.offer(new int[]{0,0});
        grid[0][0] = -1;

        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i=0; i<size; i++) {
                int[] curPos = queue.poll();
                if (curPos[0] == n-1 && curPos[1] == n-1) {
                    return constructPath(curPos, parentMap);
                }

                for (int[] dir: DIRS) {
                    int newX = curPos[0]+dir[0];
                    int newY = curPos[1]+dir[1];
                    if (isValid(grid, newX, newY, n)) {
                        int[] newPos = new int[]{newX, newY};
                        queue.offer(newPos);
                        parentMap.put(newPos, curPos);
                        grid[newX][newY] = -1;
                    }
                }
            }
        }
        return null;
    }

    private boolean isValid(int[][] grid, int x, int y, int n) {
        return x >= 0 && x < n && y >= 0 && y < n && grid[x][y] == 0;
    }

    /**
     *在java的hashMap和linkedHashMap中，其实key和value都允许为null.
     * 只不过当value为null时推荐用containsKey,而不是get来判断是否存在该key
     */
    private List<int[]> constructPath(int[] curPos, Map<int[], int[]> parentMap) {
        List<int[]> ans = new ArrayList<>();
        while (curPos != null) {
            ans.add(0, curPos); // 将当前节点插入路径开头
            curPos = parentMap.get(curPos);
        }
        return ans;
    }

    private void printAns(List<int[]> ans) {
        for (int[] pos: ans) {
            System.out.print("("+pos[0]+", "+pos[1]+") ");
        }
        System.out.println("");
    }

    public static void main(String[] args) {
        int[][] grid1 = new int[][]{{0,0,0},{1,1,0},{1,1,0}};
        int[][] grid2 = new int[][]{{0,0,0},{0,0,0},{0,0,0}};
        BFSPathPrint bpp = new BFSPathPrint();
        List<int[]> ans1 = bpp.findShortestPathBinaryMatrix(grid1);
        List<int[]> ans2 = bpp.findShortestPathBinaryMatrix(grid2);
        bpp.printAns(ans1);
        bpp.printAns(ans2);
    }
}
