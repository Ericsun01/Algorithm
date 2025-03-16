import java.util.*;

public class MostVisitedPattern {
    public List<String> mostVisitedPattern(String[] username, int[] timestamp, String[] website) {
        int len = username.length;
        // step1: record all tuples
        Tuple[] tuples = new Tuple[len];
        for (int i = 0; i < len; i++) {
            tuples[i] = new Tuple(username[i], timestamp[i], website[i]);
        }

        // step2: sort all tuples based on time sequence
        Arrays.sort(tuples, new Comparator<Tuple>(){
            @Override
            public int compare(Tuple t1, Tuple t2) {
                return t1.time - t2.time;
            }
        });

        // step3: for each user, record all their visited websites
        Map<String, ArrayList<Tuple>> userRecord = new LinkedHashMap<>();
        for (int i=0; i<len; i++) {
            Tuple tuple = tuples[i];
            if (!userRecord.containsKey(tuple.name)) {
                userRecord.put(tuple.name, new ArrayList<>());
            }
            userRecord.get(tuple.name).add(tuple);
        }

        // step4: Create pattern for each users and calculate the final points,
        // store in patternScores map
        Map<Pattern, Integer> patternScores = new LinkedHashMap<>();
        for (Map.Entry<String, ArrayList<Tuple>> entry : userRecord.entrySet()) {
            ArrayList<Tuple> tupleList = entry.getValue();
            int tupleListLen = tupleList.size();
            if (tupleListLen < 3) {
                continue;
            }

            //同一个人反复访问的同一种pattern不能计入评分，题目问的是最多不同的人访问得分
            Set<Pattern> dedup = new HashSet<>(); // collect all pattern for each user, without duplication!!!
            for (int i=0; i<tupleListLen-2; i++) {
                for (int j=i+1; j<tupleListLen-1; j++) {
                    for (int k=j+1; k<tupleListLen; k++) {
                        Pattern pattern = new Pattern(tupleList.get(i).web,
                                tupleList.get(j).web, tupleList.get(k).web);

                        dedup.add(pattern);
                    }
                }
            }

            for (Pattern pattern: dedup) {
                if (!patternScores.containsKey(pattern)) {
                    patternScores.put(pattern, 0);
                }
                patternScores.put(pattern, patternScores.get(pattern)+1);
            }
        }

        // step 5: scan patternScores and record the pattern with the highest score
        Pattern bestPattern = new Pattern("","","");;
        int maxScores = -1;
        for (Map.Entry<Pattern, Integer> entry : patternScores.entrySet()) {
            Pattern curPattern = entry.getKey();
            Integer curScore = entry.getValue();
            if (maxScores < curScore || maxScores == curScore && curPattern.compareTo(bestPattern) < 0) {
                maxScores = curScore;
                bestPattern = curPattern;
            }
        }

        // step 6: translate pattern into output List<String>
        return Arrays.asList(bestPattern.web1, bestPattern.web2, bestPattern.web3);
    }

    public static void main(String args[]) {
        MostVisitedPattern mvp = new MostVisitedPattern();
        String[] username = {"joe","joe","joe","james","james","james","james","mary","mary","mary"};
        int[] timestamp = {1,2,3,4,5,6,7,8,9,10};
        String[] website = {"home","about","career","home","cart","maps","home","home","about","career"};
        List<String> ans = mvp.mostVisitedPattern(username, timestamp, website);
        System.out.println(ans);
    }
}

class Tuple {
    public String name;
    public int time;
    public String web;

    public Tuple(String _name, int _time, String _web) {
        this.name = _name;
        this.time = _time;
        this.web = _web;
    }
}

class Pattern implements Comparable<Pattern>{
    public String web1;
    public String web2;
    public String web3;

    public Pattern(String _web1, String _web2, String _web3) {
        this.web1 = _web1;
        this.web2 = _web2;
        this.web3 = _web3;
    }

    // equals和hashCode必须同时override
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }

        Pattern pattern = (Pattern) o;
        return web1.equals(pattern.web1) &&
                web2.equals(pattern.web2) &&
                web3.equals(pattern.web3);
    }

    // 保证相同元素有相同的hashCode
    @Override
    public int hashCode() {
        return Objects.hash(web1, web2, web3);
    }

    // 需要针对新class object做排序时才override compareTo
    @Override
    public int compareTo(Pattern other) {
        if (other == null) {
            return -1;
        }

        int cmpResult1 = web1.compareTo(other.web1);
        int cmpResult2 = web2.compareTo(other.web2);
        int cmpResult3 = web3.compareTo(other.web3);
        if (cmpResult1 != 0) {
            return cmpResult1;
        } else if (cmpResult2 != 0) {
            return cmpResult2;
        } else if (cmpResult3 != 0) {
            return cmpResult3;
        } else {
            return 0;
        }
    }
}
