import java.lang.reflect.Array;
import java.util.*;
import java.util.Scanner;

import java.util.Scanner;

//public class Solution {
//    public int GetNumberOfK(int[] data, int k) {
//        if (data == null) {
//            return -1;
//        }
//
//        int index1 = helper1 (data, k);
//        int index2 = helper2 (data, k);
//        if (index1 == -1 && index2 == -1) {
//            return -1;
//        }
//        return index2 - index1 + 1;
//
//    }
//
//    private int helper1(int[] data, int k) {
//        int left = 0;
//        int right = data.length - 1;
//        int index = 0;
//        while (left < right - 1) {
//            index = (left + right) / 2;
//            if (data[index] < k) {
//                left = index;
//            } else if (data[index] >= k) {
//                right = index;
//            }
//        }
//
//        if (data[left] == k) {
//            return left;
//        } else if (data[right] == k) {
//            return right;
//        }
//        return -1;
//    }
//
//    private int helper2(int[] data, int k) {
//            int left = 0;
//            int right = data.length-1;
//            int index = 0;
//
//            while (left < right-1) {
//                index = (left+right)/2;
//                if (data[index] > k) {
//                    right = index;
//                } else if(data[index] <= k) {
//                    left = index;
//                }
//            }
//            if (data[left] == k) {
//                return left;
//            } else if (data[right] == k) {
//                return right;
//            }
//            return -1;
//    }
//
//    public static void main (String[] args) {
//        Solution sol = new Solution();
//        int[] data = {1,2,3,3,3,3,4,5};
//        int ans = sol.GetNumberOfK(data,6);
//        System.out.println(ans);
//    }
//}
public class Solution{
    public int search(int[] array, int target) {
        // Write your solution here
        if (array.length == 0) {
            return -1;
        }

        if (array.length == 1) {
            if (array[0] == target) {
                return 0;
            } else {
                return -1;
            }
        }

        // step1: find the peak val
        int left = 0, right = array.length-1;
        while (left < right) {
            int midPeak = left+(right-left)/2;
            if (array[midPeak] < array[midPeak+1]) {
                left = midPeak+1;
            } else {
                right = midPeak;
            }
        }

        int peakIndex = left;
        if (array[peakIndex] == target) {
            return peakIndex;
        }

        // step2: binarySearch separately
        int leftResult = binarySearch(array, 0, peakIndex, target, true);
        int rightResult = binarySearch(array, peakIndex, array.length-1, target, false);

        return leftResult != -1 ? leftResult : rightResult;
    }

    private int binarySearch(int[] array, int left, int right, int target, boolean isIncrease) {
        if (isIncrease) {
            while (left < right) {
                int mid = left + (right-left)/2;
                if (array[mid] < target) {
                    left = mid+1;
                } else if (array[mid] > target) {
                    right = mid-1;
                } else {
                    return mid;
                }
            }
        } else {
            while (left < right) {
                int mid = left + (right-left)/2;
                if (array[mid] < target) {
                    right = mid-1;
                } else if (array[mid] > target) {
                    left = mid+1;
                } else {
                    return mid;
                }
            }
        }

        if (array[left] == target) {
            return left;
        }
        return -1;
    }
    public int[][] multiply(int[][] mat1, int[][] mat2) {
        int m = mat1.length;
        int k = mat1[0].length; // mat1 col number == mat2 row number
        int n = mat2[0].length;

        if (m == 1 && k == 1 && n == 1) {
            return new int[][]{{mat1[0][0]*mat2[0][0]}};
        }
        // record valid element's col index for each row(list)
        List<List<Integer>> validIndexRow = new ArrayList<>();
        for (int i=0; i<m; i++) {
            validIndexRow.add(new ArrayList<>());
        }
        List<List<Integer>> validIndexCol = new ArrayList<>();
        for (int i=0; i<n; i++) {
            validIndexCol.add(new ArrayList<>());
        }

        for(int i=0; i<m; i++) {
            for (int j=0; j<k; j++) {
                if (mat1[i][j] != 0) {
                    validIndexRow.get(i).add(j);
                }
            }
        }

        for(int i=0; i<k; i++) {
            for (int j=0; j<n; j++) {
                if (mat2[i][j] != 0) {
                    validIndexCol.get(j).add(i);
                }
            }
        }

        int[][] ans = new int[m][n];
        for (int i=0; i<m; i++) {
            for (int j=0; j<n; j++) {
                int curRowlistSize = validIndexRow.get(i).size();
                int curCollistSize = validIndexCol.get(j).size();
                if (curRowlistSize > curCollistSize) {
                    // 使用较短的col list做遍历
                    for (int index: validIndexCol.get(j)) {
                        ans[i][j] += mat1[i][index]*mat2[index][j];
                    }
                } else {
                    for (int index: validIndexRow.get(i)) {
                        ans[i][j] += mat1[i][index]*mat2[index][j];
                    }
                }
            }
        }
        return ans;
    }

    public static void main(String[] args) {
        Solution sol = new Solution();
        int[] input = {0,1,6,9,10,3,2,-2,-4,-5};
        //int ans = sol.search(input,9);
        //System.out.println(ans);

        int[][] mat1 = {{1,0,0},{-1,0,3}};
        int[][] mat2 = {{7,0,0},{0,0,0},{0,0,1}};
        int[][] ans = sol.multiply(mat1, mat2);
        for (int i=0; i< ans.length; i++) {
            for (int j=0; j< ans[0].length; j++) {
                System.out.print(ans[i][j]);
                System.out.print(" ");
            }
            System.out.println("");
        }
    }
}