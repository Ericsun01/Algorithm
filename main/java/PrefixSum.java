import java.util.HashMap;
import java.util.Map;

public class PrefixSum {
    /* p[j]-p[i] = k -> p[i] = p[j]-k; actually, it means subarray[i+1,j]
     * length of subarray[i+1,j]: j-i
     * step1: create a prefixSum array, also use map<prefixSum, firstOccurrenceIndex> to track them
     * special case: i can start as -1, so <-1,0> needs to be record in the map
     *
     * step2: scan the array
     * if map.containsKey(p[j]-k) : find a valid subarray, update length
     *
     * if !map.containsKey(p[j]): add it to map
     * */
    public int maxSizeSubarraySumEqualsToK(int[] input, int k) {
        if (input == null || input.length == 0) {
            return 0;
        }

        int result = 0;
        int[] prefixSum = new int[input.length];
        prefixSum[0] = input[0];
        // step1
        for (int i=1; i<input.length; i++) {
            prefixSum[i] = prefixSum[i-1]+input[i];
        }

        Map<Integer, Integer> prefixSumIndex = new HashMap<>();
        prefixSumIndex.put(0,-1);

        // step2
        for (int j=0; j< input.length; j++) {
            if (prefixSumIndex.containsKey(prefixSum[j]-k)) {
                result = Math.max(result, j-prefixSumIndex.get(prefixSum[j]-k));
            }

            if (!prefixSumIndex.containsKey(prefixSum[j])) {
                prefixSumIndex.put(prefixSum[j], j);
            }
        }
        return result;
    }

    public static void main(String[] args) {
        PrefixSum PS = new PrefixSum();
        int[] input1 = {-2,0,3,-5,2,-1};
        int[] input2 = {1,-1,5,-2,3};
        int[] input3 = {-2,-1,2,1};
        int ans1 = PS.maxSizeSubarraySumEqualsToK(input1, -1); // 5
        int ans2 = PS.maxSizeSubarraySumEqualsToK(input2, 3); // 4
        int ans3 = PS.maxSizeSubarraySumEqualsToK(input3, 1); // 2
        System.out.println(ans1);
        System.out.println(ans2);
        System.out.println(ans3);
    }
}
