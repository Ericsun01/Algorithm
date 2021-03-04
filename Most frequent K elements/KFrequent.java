import java.util.*;

// leetcode 692
// method 1: heap with entry element:
public class KFrequent {
    public List<String> topKFrequent(String[] words, int k) {
        Map<String, Integer> map = new HashMap<String, Integer>();
        for (String word: words) {
            map.put(word, map.getOrDefault(word, 0)+1);
        }

        // s2.compareTo(s1)：这里保证字母值小的字符串向下，字母值大的向上
        PriorityQueue<Map.Entry<String, Integer>> minHeap = new PriorityQueue<Map.Entry<String, Integer>>(k, new Comparator<Map.Entry<String, Integer>>() {
            @Override
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                if (o1.getValue().equals(o2.getValue())) {
                    return o2.getKey().compareTo(o1.getKey());
                }
                return o1.getValue() - o2.getValue();
            }
            });


        for (Map.Entry<String, Integer> entry: map.entrySet()) {
            minHeap.offer(entry);
            if (minHeap.size() > k) {
                minHeap.poll();
            }
        }


        List<String> ans = new ArrayList<String>();
        while (!minHeap.isEmpty()) {
            ans.add(minHeap.poll().getKey());
        }
        Collections.reverse(ans);
        return ans;
    }

    // method 2: heap with String element:
    
    public List<String> topKFrequent(String[] words, int k) {
        Map<String, Integer> map = new HashMap<String, Integer>();
        for (String word: words) {
            map.put(word, map.getOrDefault(word, 0)+1);
        }

        // s2.compareTo(s1)：这里保证字母值小的字符串向下，字母值大的向上
        PriorityQueue<String> minHeap = new PriorityQueue<String>(k, (s1, s2) -> map.get(s1).equals(map.get(s2)) ? s2.compareTo(s1) : map.get(s1) - map.get(s2));


        for (String word: map.keySet()) {
            minHeap.offer(word);
            if (minHeap.size() > k) minHeap.poll();
        }

        // eg: ["i", "love", "leetcode", "i", "love", "coding"] , k = 3
        // 输出：["i","love","coding"]
        // 这里由于题目限制，"如果不同的单词有相同出现频率，按字母顺序排序。"因此要保证最后结果中一定要有coding而不是leetcode。按传统方法做可能会导致后来的leetcode把原本在堆顶的coding挤掉。因此此题算变种，务必将所有元素都进堆做排序。
        // for (String str: map.keySet()) {
        //     if (minHeap.size() < k) {
        //         minHeap.offer(str);
        //         continue;
        //     }

        //     if (map.get(str) >= map.get(minHeap.peek())) {
        //         minHeap.poll();
        //         minHeap.offer(str);
        //     }
        // }


        List<String> ans = new ArrayList<String>();
        while (!minHeap.isEmpty()) {
            ans.add(minHeap.poll());
        }
        Collections.reverse(ans);
        return ans;
    }
    
}
