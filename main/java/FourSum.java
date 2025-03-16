import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FourSum {
    public List<List<Integer>> fourSum(int[] nums, int target) {
        Arrays.sort(nums);
        List<List<Integer>> ans = new ArrayList<>();
        int n = nums.length;

        for (int a = 0; a < n-3; a++) {
            long aSumLowerBound = (long)nums[a]+nums[a+1]+nums[a+2]+nums[a+3];
            long aSumUpperBound = (long)nums[a]+nums[n-3]+nums[n-2]+nums[n-1];
            if (aSumLowerBound > target) break;
            if (aSumUpperBound < target) continue;
            if (a > 0 && nums[a] == nums[a-1]) continue;

            for (int b = a+1; b < n-2; b++) {
                long bSumLowerBound = (long)nums[a]+nums[b]+nums[b+1]+nums[b+2];
                long bSumUpperBound = (long)nums[a]+nums[b]+nums[n-2]+nums[n-1];
                if (bSumLowerBound > target) break;
                if (bSumUpperBound < target) continue;
                if (b > a+1 && nums[b] == nums[b-1]) continue;

                int c = b+1, d = n-1;
                long remainTarget = (long)target-nums[a]-nums[b];
                while (c < d) {
                    long cdSum = (long)nums[c]+nums[d];
                    if (cdSum < remainTarget) {
                        c++;
                    } else if (cdSum > remainTarget) {
                        d--;
                    } else {
                        ans.add(Arrays.asList(nums[a], nums[b], nums[c], nums[d]));
                        c++;
                        d--;
                        while (c < d && nums[c] == nums[c-1]) c++;
                        while (c < d && nums[d] == nums[d+1]) d--;
                        // 反证法得出每获得一个答案，c,d必须都移动:
                        /*
                        假设nums[c]，nums[c+1]不同，nums[d-1], nums[d]不同。只移动一个变量
                        c不动，d变成d-1还是答案。说明nums[d-1] == nums[d], 与假设互斥
                         */
                    }
                }
            }
        }

        return ans;
    }

    public static void main(String args[]) {
        FourSum fs = new FourSum();
        int[] nums = {0,0,0,1000000000,1000000000,1000000000,1000000000};
        int target = 1000000000;
        List<List<Integer>> ans = fs.fourSum(nums,target);
        System.out.println(ans);
    }
}
