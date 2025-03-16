import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GraphDFS {
    /*
    int[][] dirs = {{-1,0},{1,0},{0,-1},{0,1}};
    public int uniquePathsIII(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        if (m == 1 && n == 1 && grid[0][0] == -1) {
            return 0;
        }

        int stepLeft = 0;
        int initX = 0, initY = 0;
        for (int i=0; i<m; i++) {
            for (int j=0; j<n; j++) {
                if (grid[i][j] == 1) {
                    initX = i;
                    initY = j;
                    continue;
                }

                if (grid[i][j] == 0) {
                    stepLeft++;
                }
            }
        }
        grid[initX][initY] = -1;
        return dfs(grid, initX, initY, stepLeft);
    }

    private int dfs(int[][] grid, int row, int col, int stepLeft) {
        if (grid[row][col] == 2) {
            if (stepLeft == 0) {
                return 1;
            } else {
                return 0;
            }
        }

        int ans = 0;
        for (int[] dir: dirs) {
            int newRow = row+dir[0];
            int newCol = col+dir[1];
            if (isValid(grid, newRow, newCol)) {
                if (grid[newRow][newCol] == 0) {
                    grid[newRow][newCol] = -1; // visited
                    ans += dfs(grid, newRow, newCol, stepLeft-1);
                    grid[newRow][newCol] = 0;
                } else {// next is destination
                    ans += dfs(grid, newRow, newCol, stepLeft);
                }
            }
        }

        return ans;
    }

    private boolean isValid(int[][] grid, int row, int col) {
        int m = grid.length;
        int n = grid[0].length;
        if (row < 0 || row >= m || col < 0 || col >= n || grid[row][col] == -1) {
            return false;
        }
        return true;
    }
    *
     */
    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        List<List<Integer>> ans = new ArrayList<>();
        int[] cur = new int[candidates.length];
        //List<Integer> cur = new ArrayList<>();
        Arrays.sort(candidates);
        dfs(candidates, target, 0, cur, ans);
        return ans;
    }

    private void dfs(int[] candidates, int valLeft, int index, int[] cur, List<List<Integer>> ans) {
        if (valLeft <= 0) {
            ans.add(process(candidates, cur));
            return;
        }

        if (index == candidates.length) {
            return;
        }

        for (int i=0; i <= valLeft/candidates[index]; i++) {
            cur[index] = i;
            dfs(candidates, valLeft - i * candidates[index], index + 1, cur, ans);
            cur[index] = 0;
        }
    }

    private List<Integer> process(int[] candidates, int[] cur) {
        List<Integer> result = new ArrayList<>();
        for (int i=0; i<candidates.length; i++) {
            for (int j=0; j<cur[i]; j++) {
                result.add(candidates[i]);
            }
        }
        return result;
    }

    public static void main(String[] args) {
        GraphDFS sd = new GraphDFS();
        /*
        int[][] input1 = {{0,1},{2,0}};
        int[][] input2 = {{-1,0,0,0},{0,0,0,0},{0,0,2,-1}};
        int ans = sd.uniquePathsIII(input2);
        */
        int[] candidates = {2,3,6,7};
        List<List<Integer>> ans = sd.combinationSum(candidates, 7);
        for (List<Integer> l: ans) {
            System.out.println(l);
        }

        String s1 = "a";
        String s2 = "b";
        s1 = s2;
        s1 = new String("a");
        System.out.println(s1);
    }
}
