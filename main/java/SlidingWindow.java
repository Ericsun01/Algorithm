import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class SlidingWindow {
    public String minWindow(String s, String t) {
        int m = s.length();
        int n = t.length();
        if (m == 1 && n == 1) {
            if (s.charAt(0) == t.charAt(0)) {
                return s;
            } else {
                return new String("");
            }
        }

        Map<Character, Integer> cnt = new HashMap<>();
        Map<Character, Integer> cns = new HashMap<>();
        for (char ct: t.toCharArray()) {
            cnt.put(ct, cnt.getOrDefault(ct,0)+1);
        }

        int i=0, j=0;
        int ansL=-1, ansR=m;
        int less = cnt.size();
        char[] sa = s.toCharArray();

        while (j < m) {
            cns.put(sa[j], cns.getOrDefault(sa[j],0)+1);
            if (cnt.containsKey(sa[j]) && cns.get(sa[j]) == cnt.get(sa[j])) {
                less--;
            }
            j++;

            while (less == 0) {
                if (j-i < ansR-ansL) {
                    ansL = i;
                    ansR = j;
                }
                int curFreq = cns.get(sa[i]);
                if (cnt.containsKey(sa[i]) && cnt.get(sa[i]) == curFreq) {
                    less++;
                }
                cns.put(sa[i], curFreq-1);
                i++;
            }
        }

        if (ansL == -1) {
            return new String("");
        }

        return s.substring(ansL, ansR);
    }

    public static void main(String[] args) {
        SlidingWindow sw = new SlidingWindow();
        String s = "ADOBECODEBANC";
        String t = "ABC";
        //System.out.println(sw.minWindow(s,t));
        LinkedHashMap<String, String> map = new LinkedHashMap<>();
        map.put("k1","v1");
        map.put("k2","v2");
        for (Map.Entry e: map.entrySet()) {
            System.out.println(e);
        }

        System.out.println("After change: /n");
        String v = map.remove("k1");
        map.put("k3",v);
        for (Map.Entry e: map.entrySet()) {
            System.out.println(e);
        }
    }
}
