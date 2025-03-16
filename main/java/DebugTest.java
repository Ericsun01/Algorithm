import com.sun.org.apache.xerces.internal.util.SynchronizedSymbolTable;

import java.util.*;

public class DebugTest {
    private final static int INF = Integer.MAX_VALUE;
    private final static int[][] DIRS = {{-1,0}, {1,0}, {0,-1}, {0,1}};
    public void minDisFromZero(int[][] input) {
        Queue<int[]> queue = new LinkedList<>();
        for (int i=0; i<input.length; i++) {
            for (int j = 0; j < input[0].length; j++) {
                if (input[i][j] == 0) {
                    queue.offer(new int[]{i,j});
                }
            }
        }

        int step = 0;
        while (!queue.isEmpty()) {
            int size = queue.size();
            step++;
            for (int i=0; i<size; i++) {
                int[] cur = queue.poll();
                for (int[] dir: DIRS) {
                    int newRow = cur[0]+dir[0];
                    int newCol = cur[1]+dir[1];
                    if (isValid(input, newRow, newCol)) {
                        queue.offer(new int[] {newRow,newCol});
                        input[newRow][newCol] = step;
                    }
                }
            }
        }
    }

    private boolean isValid(int[][] input, int newRow, int newCol) {
        if (newRow < 0 || newRow >= input.length || newCol < 0 || newCol >= input[0].length) {
            return false;
        }

        if (input[newRow][newCol] != INF) {
            return false;
        }

        return true;
    }

    public static void main(String[] args) {
        DebugTest d = new DebugTest();
        int[][] input = {{INF, -1, 0, INF},{INF, INF, INF, -1},{INF, -1, INF, -1},{0, -1, INF, INF}};
        d.minDisFromZero(input);
        for (int i=0; i<input.length; i++) {
            for (int j = 0; j < input[0].length; j++) {
                System.out.print(input[i][j]+" ");
            }
            System.out.println("");
        }
    }
}

class Entry {
    TreeNode root;
    int col;

    public Entry(TreeNode root, int col) {
        this.root = root;
        this.col = col;
    }
}