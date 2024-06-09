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

}