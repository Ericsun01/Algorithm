public class KnightTour {
    private final int[][] DIRS = {{-2, -1}, {-1, -2}, {1, -2}, {2, -1},
            {2, 1}, {1, 2}, {-1, 2}, {-2, 1}};
    public boolean checkValidGrid(int[][] grid) {
        int n = grid.length;
        boolean[][] visited = new boolean[n][n];
        visited[0][0] = true;
        return dfs(grid, visited, 0, 0, 0);
    }

    private boolean dfs(int[][] grid, boolean[][] visited, int row, int col, int steps) {
        int n = grid.length;
        if (steps == n*n-1) {
            return true;
        }

        steps++;
        for (int[] dir: DIRS) {
            int newRow = row+dir[0];
            int newCol = col+dir[1];
            if (isValid(n, newRow, newCol, visited) && steps == grid[newRow][newCol]) {
                visited[newRow][newCol] = true;
                if (dfs(grid, visited, newRow, newCol, steps)) {
                    return true;
                }
                visited[newRow][newCol] = false;
            }
        }

        return false;
    }

    private boolean isValid(int len, int row, int col, boolean[][] visited) {
        return row >= 0 && row < len && col >= 0 && col < len && !visited[row][col];
    }

    public static void main(String[] args) {
        KnightTour kt = new KnightTour();
        int[][] grid = new int[][] {{0,11,16,5,20},{17,4,19,10,15},{12,1,8,21,6},
                {3,18,23,14,9}, {24,13,2,7,22}};

        System.out.println(kt.checkValidGrid(grid));
    }
}
