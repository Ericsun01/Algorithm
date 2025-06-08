import java.util.*;

public class Amazon {
    /*
    * 题目1.1
    * Sudo Code:
Idea:
根据观察，相邻且相同元素，仅保留一个。单调区间内仅保留一头一尾。
扫描nums, 用start标定subarray起始，left,right用来检查相邻元素大小关系，单调栈处理区间
start = 0, left = 0, right = 1
while right 未出界
    if num[left] == num[right] // 4 4 4 3
        start = right;
        left = start;
        right++;
        continue;
    else if (num[right] < num[left]) { // 6 4 4 3 3 2 and 5 4 0 3 3 1
        while (right < len && num[right] <= num[left]) {
            right++;
            left++;
        }
    } else { // num[right] > num[left] 3 5 5 5 5 6 7 4 1 10 -> 3 7 1 10
        while (right < len && num[right] >= num[left]) {
            right++;
            left++;
        }
    }

    ans.add(num[start]);
    start = left;

if start < length // 6 4 4 3 3 8
    ans.add(num[start])
    * */
    public List<Integer> getMinElementsSubArray(int[] num) {
        List<Integer> ans = new ArrayList<>();
        if (num.length == 1) {
            ans.add(num[0]);
            return ans;
        }

        int start=0, left=0, right=1;
        while (right < num.length) {
            if (num[left] == num[right]) {
                start = right;
                left = start;
                right++;
                continue;
            } else if (num[right] < num[left]) {
                while (right < num.length && num[right] <= num[left]) {
                    left++;
                    right++;
                }
            } else {
                while (right < num.length && num[right] >= num[left]) {
                    left++;
                    right++;
                }
            }

            ans.add(num[start]);
            start = left;
        }

        if (start < num.length) {
            ans.add(num[start]);
        }

        return ans;
    }

    /*
    题目1.2
    方法1： memorized dfs
    int dfs(inv, index, dis1, dis2, skipLeft, memo): inv从起始元素index开始，还剩skipLeft时的最大credit
    Integer[][] memo表示从i index, 还剩j skip的最大credit

    方法2: dp

    * */
    public int getMaximumCredits(int[] inventory, int dispatch1, int dispatch2, int skips) {
        int N = inventory.length;
        Integer[][] memo = new Integer[N][skips+1];
        return dfs(inventory, 0, dispatch1, dispatch2, skips, memo);
    }

    private int dfs(int[] inv, int index, int dis1, int dis2, int skipLeft, Integer[][] memo) {
        if (index == inv.length) {
            return 0;
        }

        if (memo[index][skipLeft] != null) {
            return memo[index][skipLeft];
        }

        int best = 0;
        int rem = inv[index] % (dis1+dis2);
        if (rem == 0) {
            rem = dis1+dis2;
        }

        // 不skip
        if (rem <= dis1) { // 本轮得分
            best += 1;
        }
        best += dfs(inv, index+1, dis1, dis2, skipLeft, memo);

        // skip
        if (skipLeft > 0) {
            int skipNeed = rem/dis1;
            if (skipNeed <= skipLeft) {
                int curSkipCredit = 1+dfs(inv, index+1, dis1, dis2, skipLeft-skipNeed, memo);
                best = Math.max(best, curSkipCredit);
            }
        }

        memo[index][skipLeft] = best;
        return best;
    }

    /**
     * 计算多个查询下的总连接成本。
     *
     * @param warehouseCapacity  仓库容量数组，0-based，长度为 n，假设已按位置非递减排序
     * @param q                  查询数量（等于 additionalHubs.size()）
     * @param additionalHubs     每个查询的额外配送中心位置（1-based），格式为 List<List<Integer>>，每个里有两个元素 [hubA, hubB]
     * @return                   每个查询的总成本列表，长度为 q
     */
    public List<Long> getMinConnectionCost(
            int n,
            int[] warehouseCapacity,
            int q,
            List<List<Integer>> additionalHubs
    ) {
        // 前缀和数组，prefixSum[i] = sum of warehouseCapacity[0..i]
        long[] prefixSum = new long[n];
        prefixSum[0] = warehouseCapacity[0];
        for (int i = 1; i < n; i++) {
            prefixSum[i] = prefixSum[i - 1] + warehouseCapacity[i];
        }

        List<Long> results = new ArrayList<>();
        for (int idx = 0; idx < q; idx++) {
            List<Integer> hubs = additionalHubs.get(idx);
            int hubA = hubs.get(0) - 1;  // 转 0-based
            int hubB = hubs.get(1) - 1;  // 转 0-based
            long totalCost = 0;

            // 段 1: 仓库 0..hubA 连接 hubA
            if (hubA >= 0) {
                long sumSegment = prefixSum[hubA];
                totalCost += (long) warehouseCapacity[hubA] * (hubA + 1) - sumSegment;
            }

            // 段 2: 仓库 hubA+1..hubB 连接 hubB
            if (hubB > hubA) {
                long sumSegment = prefixSum[hubB] - (hubA >= 0 ? prefixSum[hubA] : 0);
                totalCost += (long) warehouseCapacity[hubB] * (hubB - hubA) - sumSegment;
            }

            // 段 3: 仓库 hubB+1..n-1 连接中央中心（位置 n-1）
            if (n - 1 > hubB) {
                long sumSegment = prefixSum[n - 1] - prefixSum[hubB];
                totalCost += (long) warehouseCapacity[n - 1] * (n - 1 - hubB) - sumSegment;
            }

            results.add(totalCost);
        }

        return results;
    }

    public static int maxEqualElementsOptimized(int[] arr, int k) {
        /**
         * 优化版本：O(n²) 时间复杂度
         * 核心优化：使用滑动窗口思想，动态维护x值统计
         */
        int n = arr.length;
        int maxCount = 0;

        // 预计算"i个元素里的k(前缀)频率"，O(n)
        int[] prefixK = new int[n + 1];
        for (int i = 0; i < n; i++) {
            prefixK[i + 1] = prefixK[i] + (arr[i] == k ? 1 : 0);
        }

        // 枚举起始位置i，O(n²)复杂度
        for (int i = 0; i < n; i++) {
            Map<Integer, Integer> xCount = new HashMap<>();
            int maxXCount = 0;  // 当前起始点能获得的最大x频率

            // 从i开始，逐步扩展终止位置j
            for (int j = i; j < n; j++) {
                // 动态添加arr[j]对应的x值
                int x = k - arr[j];
                int newCount = xCount.getOrDefault(x, 0) + 1;
                xCount.put(x, newCount);

                // 更新最大x频率，O(1)操作
                maxXCount = Math.max(maxXCount, newCount);

                // O(1)计算子数组外k的数量
                int outsideKCount = prefixK[i] + (prefixK[n] - prefixK[j + 1]);

                // 计算总k数量
                int totalKCount = outsideKCount + maxXCount;
                maxCount = Math.max(maxCount, totalKCount);
            }
        }

        return maxCount;
    }

    public static void main(String[] args) {
        Amazon a = new Amazon();
        /*
        int[] test1 = {5,4,0,3,3,1}; // 5 0 3 1
        int[] test2 = {6,4,4,3,3,2}; // 6 2
        int[] test3 = {4,4,4,3}; // 4 3
        int[] test4 = {3,5,5,5,5,6,7,4,1,10}; // 3,7,1,10

        List<Integer> ans1 = a.getMinElementsSubArray(test1);
        for (Integer i: ans1) {
            System.out.print(i+", ");
        }
        System.out.println("");

        List<Integer> ans2 = a.getMinElementsSubArray(test2);
        for (Integer i: ans2) {
            System.out.print(i+", ");
        }
        System.out.println("");

        List<Integer> ans3 = a.getMinElementsSubArray(test3);
        for (Integer i: ans3) {
            System.out.print(i+", ");
        }
        System.out.println("");

        List<Integer> ans4 = a.getMinElementsSubArray(test4);
        for (Integer i: ans4) {
            System.out.print(i+", ");
        }

        int[] inv1 = {5,10,15};
        int[] inv2 = {3,8,9,4};
        int[] inv3 = {1,100,50};
        System.out.println(a.getMaximumCredits(inv1, 2, 3, 2));
        System.out.println(a.getMaximumCredits(inv2, 3, 2, 1));
        System.out.println(a.getMaximumCredits(inv3, 1, 100, 2));
        */

        int[] warehouseCap1 = {3,6,10,15,20};
        List<List<Integer>> queries1 = Arrays.asList(
                Arrays.asList(2, 4)
        );
        List<Long> ans1 = a.getMinConnectionCost(5, warehouseCap1, 1, queries1);
        for (Long l: ans1) {
            System.out.print(l+" ");
        }
        System.out.println("");

        int[] warehouseCap2 = {2,5,9,12,18,30};
        List<List<Integer>> queries2 = new ArrayList<>();
        queries2.add(Arrays.asList(2,5));
        queries2.add(Arrays.asList(1,3));
        List<Long> ans2 = a.getMinConnectionCost(6, warehouseCap2, 2, queries2);
        for (Long l: ans2) {
            System.out.print(l+" ");
        }
    }
}
