import java.util.*;

public class Solution2 {
    public static void main(String[] args) {
        int[] wait = {2, 2, 3, 1};
        int[] result = findRequestsInQueue(wait);
        System.out.println(Arrays.toString(result)); // Output: [4, 2, 1, 0]
    }

    public static int[] findRequestsInQueue(int[] wait) {
        Map<Integer, Integer> map = new LinkedHashMap<>();
        for (int i=0; i< wait.length; i++) {
            map.put(i, wait[i]);
        }

        int len = map.size();
        List<Integer> ans = new ArrayList<>();
        ans.add(len);
        boolean isProcessIndexSet = false;
        int processIndex = 0;
        for (int currentTime = 1; map.size() > 0; currentTime++) {
            map.remove(processIndex);
            len--;
            Iterator<Map.Entry<Integer, Integer>> iterator = map.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<Integer, Integer> pair = iterator.next();
                pair.setValue(pair.getValue()-1);
                if (pair.getValue() == 0) {
                    iterator.remove();
                    len--;
                } else if (!isProcessIndexSet) {
                    processIndex = pair.getKey();
                    isProcessIndexSet = true;
                }
            }
            ans.add(len);
            isProcessIndexSet = false;
        }


        int[] result = new int[ans.size()];
        for (int i=0; i< result.length; i++) {
            result[i] = ans.get(i);
        }
        return result;

        /*
        int requests = wait.length; // # of pending requests
        Map<Integer, Integer> counts = new HashMap<>();
        for (int time : wait) {
            counts.compute(time, (k, v) -> v == null ? 1 : v + 1);
        }

        int n = wait.length;
        int[] result = new int[n];
        int time = 0; // real / running time
        for (int waitingTime : wait) {
            if (waitingTime > time) {
                result[time++] = requests--;
                counts.compute(waitingTime, (k, v) -> v == 1 ? null : v - 1);
            }
            Integer noOfReqExpired = counts.remove(time);
            if (noOfReqExpired != null) {
                requests -= noOfReqExpired;
            }
        }

        return result;
    */
    }
}
