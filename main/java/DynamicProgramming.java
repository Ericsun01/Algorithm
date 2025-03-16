import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DynamicProgramming {
    // 圣诞课堂Longest Common Substring. subsequence 见lc
    public String LongestCommonSubstring(String word1, String word2) {
        int len1=word1.length(), len2=word2.length();
        if (len1 == 0 || len2 == 0) {
            return new String("");
        }

        /**
         * M[i][j]表示
         * word1前i个letter(包含第i个)，word2前j个letter(包含第j个)的最长common substring(连续)长度
         * base case: i==0 || j==0 : M[i][j] = 0
         *
         * induction rule:
         * word1[i-1] == word2[j-1] : M[i][j] = M[i-1][j-1]+1
         *                    else  : 0
         *
         * 并使用globalMax来记录LCS长度，globalRight记录word1中LCS的右边界(exclusive)
         * 最后返回word1.substring(globalRight-globalMax, globalRight)
         *
         * TC = O(mn)
         * SC = O(mn)
         */
        int[][] M = new int[len1+1][len2+1];
        for (int i=0; i<=len1; i++) {
            M[i][0] = 0;
        }
        for (int j=0; j<=len2; j++) {
            M[0][j] = 0;
        }

        int globalMax = 0, globalRight = 0;
        for (int i=1; i<=len1; i++) {
            for (int j=1; j<=len2; j++) {
                if (word1.charAt(i-1) == word2.charAt(j-1)) {
                    M[i][j] = M[i-1][j-1]+1;
                    if (M[i][j] > globalMax) {
                        globalMax = M[i][j];
                        globalRight = i;
                    }
                } else {
                    M[i][j] = 0;
                }
            }
        }

        return word1.substring(globalRight-globalMax, globalRight);
    }

    // 圣诞课堂minCut
    public int minCut(String s, List<String> wordDict) {
        Set<String> dict = buildSet(wordDict);
        int len = s.length();
        int[] dp = new int[len+1];
        dp[0] = 0;
        for (int i=1; i<=len; i++) {
            dp[i] = Integer.MAX_VALUE;
        }

        for (int i=1; i<=len; i++) {
            for (int j=0; j<i; j++) {
                if (dp[j] != Integer.MAX_VALUE && dict.contains(s.substring(j, i))) {
                    dp[i] = Math.min(dp[i], dp[j]+1);
                }
            }
        }

        return dp[len];
    }

    private Set<String> buildSet(List<String> wordDict) {
        Set<String> dict = new HashSet<>();
        for (String s: wordDict) {
            dict.add(s);
        }

        return dict;
    }

    /**
     * 思考题Q3.4 wildcard pattern matching
     * @param input
     * @param pattern
     * @return
     */
    public boolean wildPatternMatch(String input, String pattern) {
        int len1 = input.length(), len2 = pattern.length();
        if (len2 == 0) {
            if (len1 == 0) {
                return true;
            } else {
                return false;
            }
        }

        /**
         * M[i][j]: input的前i个字母(包括input[i-1])，pattern的前j个字母(包括pattern[j-1])能否匹配上
         *
         * base case:
         *  M[0][0] = true;
         *  j == 0, i > 0: M[i][0] = false
         *  i == 0, j > 0: 如果从j==1开始就是*，把相连的M[0][j] = true
         *                  else: M[0][j] = false
         *
         * induction case:
         * pattern[j-1] is letter:
         *  input[i-1] == pattern[j-1] : M[i][j] = M[i-1][j-1]
         *                        else : M[i][j] = false
         *
         * pattern[j-1] is '?': M[i][j] = M[i-1][j-1]
         *                 '*': M[i][j-1] || M[i-1][j-1] || ... || M[0][j-1]
         *
         * return M[len1][len2]
         *
         * TC = O(mn)
         * SC = O(mn)
         */
        boolean[][] M = new boolean[len1+1][len2+1];
        M[0][0] = true;
        for (int i=1; i<=len1; i++) {
            M[i][0] = false;
        }
        for (int j=1; j<=len2; j++) {
            if (j == 1 && pattern.charAt(j-1) == '*') {
                M[0][j++] = true;
                while (j <= len2 && pattern.charAt(j-1) == '*') {
                    M[0][j++] = true;
                }
            }
            M[0][j] = false;
        }

        for (int i=1; i<=len1; i++) {
            for (int j=1; j<=len2; j++) {
                char inputCur = input.charAt(i-1), patternCur = pattern.charAt(j-1);
                if (Character.isLetter(patternCur)) {
                    if (inputCur == patternCur) {
                        M[i][j] = M[i-1][j-1];
                    } else {
                        M[i][j] = false;
                    }
                } else if (patternCur == '?') {
                    M[i][j] = M[i-1][j-1];
                } else { // patternCur is '*'
                    M[i][j] = false;
                    int curRow = i;
                    while (curRow >= 0) {
                        if (M[curRow][j-1]) {
                            M[i][j] = true;
                            break;
                        }
                        curRow--;
                    }
                }
            }
        }

        return M[len1][len2];
    }

    public static void main(String[] args) {
        DynamicProgramming DP = new DynamicProgramming();
        String s = "bobcatrob";
        List<String> wordDict = new ArrayList<>();
        wordDict.add("bob");
        wordDict.add("cat");
        wordDict.add("rob");
        wordDict.add("bobcat");

        //System.out.println(DP.minCut(s, wordDict));

        String word1 = "dwedxfgkhpda";
        String word2 = "swenden";
        //System.out.println(DP.LongestCommonSubstring(word1, word2));

        String input = "baaabab";
        String pattern1 = "ba*a?"; // true
        String pattern2 = "*****ba*****ab"; // true
        String pattern3 = "baaa?ab"; // true
        String pattern4 = "a*ab"; // false

        System.out.println("The input is "+input+", the pattern is "+pattern1+". Result is "+DP.wildPatternMatch(input, pattern1));
        System.out.println("The input is "+input+", the pattern is "+pattern2+". Result is "+DP.wildPatternMatch(input, pattern2));
        System.out.println("The input is "+input+", the pattern is "+pattern3+". Result is "+DP.wildPatternMatch(input, pattern3));
        System.out.println("The input is "+input+", the pattern is "+pattern4+". Result is "+DP.wildPatternMatch(input, pattern4));
    }
}
