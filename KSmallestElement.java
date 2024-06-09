import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

public class KSmallestElement {
    // Time Complexity: O(KlogM)
    // Space Complexity: O(k)
    private int[] kSmallestInMSortedArrays(int[][] arrays, int k) {
        int[] result = new int[k];
        int M = arrays.length;
        // base case: empty arrays or k == 0
        if (M <= 0 || k <= 0) {
            return result;
        }

        // use minHeap to sort the elements
        PriorityQueue<Integer[]> minHeap = new PriorityQueue<>(M, new MyComparator());
        for (int i = 0; i < M; i++) {
            // for new array element, a[0]: row, a[1]: col, a[2]: value
            minHeap.offer(new Integer[]{i, 0, arrays[i][0]});
        }

        // the index of result array
        int index = 0;
        // keep polling k times, then the kth element will be there to be polled
        while (index < k) {
            Integer[] tmp = minHeap.poll();
            // Col of current element is less than the length of array containing it minus 1
            // So there will be a next element to offer
            // Otherwise, just give it maxValue to keep it at the bottom of min heap
            if (tmp[1] < arrays[tmp[0]].length - 1) {
                minHeap.offer(new Integer[]{tmp[0], tmp[1]+1, arrays[tmp[0]][tmp[1]+1]});
            } else {
                minHeap.offer(new Integer[]{-1, -1, Integer.MAX_VALUE});
            }
            result[index] = tmp[2];
            index++;
        }
        return result;
    }

    public static void main(String args[]) {
        int[][] arrays = {{1,5,9,13},
                {3,7,11,15,18,21},
                {0,2,6,10,14,17,20,22},
                {4,8,12,16,19}};
        KSmallestElement ke = new KSmallestElement();
        int ans[] = ke.kSmallestInMSortedArrays(arrays, 17);
        System.out.println(Arrays.toString(ans));
    }
}

class MyComparator implements Comparator<Integer[]> {
    @Override
    public int compare(Integer[] x, Integer[] y) {
        if (x[2].equals(y[2])) {
            return 0;
        }
        // -1: higher priority
        // 1: less priority

        return x[2] < y[2] ? -1 : 1;
    }
}
