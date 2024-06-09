import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

public class BinarySearch {
    private int kth(int[] a, int aleft, int[] b, int bleft, int k) {
        // base case1: a's length is too short
        if (aleft >= a.length) {
            return b[bleft + k - 1];
        }

        // base case2: b's length is too short
        if (bleft >= b.length) {
            return a[aleft + k - 1];
        }

        // base case3: k == 1
        if (k == 1) {
            return Math.min(a[aleft], b[bleft]);
        }

        // the index of element to be compared
        int amid = aleft + k/2 - 1;
        int bmid = bleft + k/2 - 1;

        // the element value to be compared
        // if amid exceed the length of array, make av large
        // so we can continue doing comparing on array b
        int av = amid >= a.length ? Integer.MAX_VALUE : a[amid];
        int bv = bmid >= b.length ? Integer.MAX_VALUE : b[bmid];

        // compare a[amid] with b[bmid]
        if (av >= bv) {
            return kth(a, aleft, b, bmid+1, k-k/2);
        } else {
            return kth(a, amid+1, b, bleft, k-k/2);
        }
    }

    // leetcode 378
    public int kthSmallestInMatrix(int[][] matrix, int k) {
        // Or use min heap, with time complexity KlogN
        int lo = matrix[0][0], hi = matrix[matrix.length - 1][matrix[0].length - 1] + 1;//[lo, hi)
        while(lo < hi) {
            int mid = lo + (hi - lo) / 2;
            int count = 0,  j = matrix[0].length - 1;
            for(int i = 0; i < matrix.length; i++) {
                while(j >= 0 && matrix[i][j] > mid) j--;
                count += (j + 1);
            }
            if(count < k) lo = mid + 1;
            else hi = mid;
        }
        return lo;
    }
    // time complexity: N*Nlog(maxValue - minValue) ~ 32N^2   Since max-min can be as large as 2^32
    // space complexity: O(1)


    public static void main(String args[]) {
        BinarySearch bs = new BinarySearch();
        int[] a = {1,3,5,7,9,11,13,15,17};
        int[] b = {2,4,6,8,10,12,14,16,18,20,22};

        int result = bs.kth(a, 0, b, 0, 20);
        //System.out.println(result);

        int[][] Matrix = {{1, 5, 10},
                {9, 11, 13},
                {12, 13, 15}};

        result = bs.kthSmallestInMatrix(Matrix, 6);
        System.out.println(result);
    }
}


