import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MicrosoftSol {
    public int numOfRecipes(String[] recipes, String ingredients) {
        /* step1: count all char occurrences in ingredients, store them in an int[26] arr
           step2: For each recipe in recipes[]:
                  count char occurrences, store in an int[26] chars
                  compare all elements: for any i: chars[i] > arr[i]: break
                  otherwise, ans++
           return ans
        */
        if (ingredients.length() == 0) {
            return 0;
        }

        int[] ingreChars = new int[26];
        fillArray(ingreChars, ingredients);

        int ans = 0;
        int[] reciChars = new int[26];
        for (String recipe: recipes) {
            fillArray(reciChars, recipe);
            if (canMakeRecipe(reciChars, ingreChars)) {
                ans++;
            }
        }

        return ans;
    }

    private void fillArray(int[] array, String s) {
        Arrays.fill(array, 0);
        for (int i=0; i<s.length(); i++) {
            char cur = s.charAt(i);
            int index = cur - 'a';
            array[index]++;
        }
    }

    private boolean canMakeRecipe(int[] recipe, int[] ingredient) {
        for (int i=0; i<26; i++) {
            if (recipe[i] > ingredient[i]) {
                return false;
            }
        }
        return true;
    }

    public int validTime(int A, int B, int C, int D) {
        /* DFS+backtracking
        * use int[4] timeArray to represent current time, index represent the digit position being processed
        * int dfs(index, timeArray): return the numOfValidTime to parent layer
        *
        * Base case:  index == 4, processed all digits successfully, return 1
        * Recursion rule: for loop iterate i from index+1 to 3, swapping index and i and check if it can be pruned,
        * If not, gather numOfValidTime += dfs(index+1, timeArray); and return it after loop
        * */
        int[] timeArray = new int[4];
        timeArray[0] = A;
        timeArray[1] = B;
        timeArray[2] = C;
        timeArray[3] = D;
        Set<String> deduplicate = new HashSet<>();
        return dfs(0, timeArray, deduplicate);
    }

    private int dfs(int index, int[] timeArray, Set<String> set) {
        if (index == 4) {
            if (isUnique(timeArray, set)) {
                return 1;
            } else {
                return 0;
            }
        }

        int numOfValidTime = 0;
        for (int i=index; i<timeArray.length; i++) {
            swapElement(timeArray, index, i);
            boolean firstIndexNotQualified = index == 0 && timeArray[index] > 2;
            boolean secondIndexNotQualified = index == 1 && timeArray[index-1] == 2 && timeArray[index] > 3;
            boolean thirdIndexNotQualified = index == 2 && timeArray[index] > 5;
            if (firstIndexNotQualified || secondIndexNotQualified || thirdIndexNotQualified) {
                swapElement(timeArray, index, i);
                continue;
            }

            numOfValidTime += dfs(index+1, timeArray, set);
            swapElement(timeArray, index, i);
        }

        return numOfValidTime;
    }

    private void swapElement(int[] array, int left, int right) {
        int tmp = array[right];
        array[right] = array[left];
        array[left] = tmp;
    }

    private boolean isUnique(int[] array, Set<String> set) {
        String curTimeString = Arrays.toString(array);
        if (!set.contains(curTimeString)) {
            set.add(curTimeString);
            return true;
        } else {
            return false;
        }
    }

    public int allSymmetric(String[] grid) {
        if (grid == null || grid.length == 0) {
            return 0;
        }

        int N = grid.length;
        int M = grid[0].length();
        int xAxis = N/2;
        int yAxis = M/2;
        int ans = 0;
        // check points not on Axis: anti clock-wise
        for (int i=0; i < xAxis; i++) { // row
            for (int j=0; j < yAxis; j++) { // col
                int numOfB = 0;
                // top left
                if (grid[i].charAt(j) == 'B') {
                    numOfB++;
                }
                // down left
                if (grid[N-1-i].charAt(j) == 'B') {
                    numOfB++;
                }
                // down right
                if (grid[N-1-i].charAt(M-1-j) == 'B') {
                    numOfB++;
                }
                // top right
                if (grid[i].charAt(M-1-j) == 'B') {
                    numOfB++;
                }

                if (numOfB >= 2) {
                    ans += 4-numOfB;
                } else {
                    ans += numOfB;
                }
            }
        }

        // check points on Axis: anti clock-wise
        // has points on XAxis:
        if (N % 2 != 0) {
            for (int j=0; j<yAxis; j++) {
                // horizontally
                if (grid[xAxis].charAt(j) != grid[xAxis].charAt(M-1-j)) {
                    ans++;
                }
            }
        }

        // also has points on YAxis:
        if (M % 2 != 0) {
            for (int i=0; i<xAxis; i++) {
                // vertically
                if (grid[i].charAt(yAxis) != grid[N-1-i].charAt(yAxis)) {
                    ans++;
                }
            }
        }

        return ans;
    }

    public int spikeLen(int[] input) {
        if (input == null || input.length == 0) {
            return 0;
        }

        if (input.length == 1) {
            return 1;
        }

        Arrays.sort(input);
        int numOfFreqNoSmallerThanOne = 1;
        int numOfFreqNoSmallerThanTwo = 0;
        int slow = 0, fast = 1;
        while (fast < input.length) {
            if (input[slow] != input[fast]) {
                numOfFreqNoSmallerThanOne++;
                slow = fast;
                fast++;
            } else {
                while (fast < input.length && input[fast] == input[slow]) {
                    fast++;
                }
            }
        }

        slow = 0;
        fast = 1;
        while (fast < input.length) {
            if (input[slow] != input[fast]) {
                slow = fast;
                fast++;
            } else {
                numOfFreqNoSmallerThanTwo++;
                while (fast < input.length && input[slow] == input[fast]) {
                    fast++;
                }
            }
        }

        // 出现过一次的元素肯定能用一次+出现至少两次的元素可以为下山再贡献一次
        int ans = numOfFreqNoSmallerThanOne+numOfFreqNoSmallerThanTwo;
        if (input[input.length-1] == input[input.length-2]) { //注意最大值（山峰）是否可能有重复
            ans--;
        }
        return ans;
    }

    public static void main(String args[]) {
        MicrosoftSol ms = new MicrosoftSol();
        /*
        // test for numOfRecipes
        String[] A1 = {"toast", "bread", "breada", "cheese"};
        String S1 = "abcdeeeghrs";
        String[] A2 = {"az", "azz", "zza", "zzz", "zzzz"};
        String S2 = "azzz";

        int ans1 = ms.numOfRecipes(A1, S1);
        int ans2 = ms.numOfRecipes(A2, S2);
        System.out.println(ans1);
        System.out.println(ans2);
        assert (ans1 == 2);
        assert (ans2 == 4);


        // test for validTime
        int a1=1, b1=8, c1=3, d1=2;
        int a2=2, b2=3, c2=3, d2=2;
        int a3=6, b3=2, c3=4, d3=7;

        int ans3 = ms.validTime(a1,b1,c1,d1);
        int ans4 = ms.validTime(a2,b2,c2,d2);
        int ans5 = ms.validTime(a3,b3,c3,d3);

        assert (ans3 == 6);
        assert (ans4 == 3);
        assert (ans5 == 0);
        System.out.println(ans3);
        System.out.println(ans4);
        System.out.println(ans5);


        // test for allSymmetric
        String[] grid = {"BBWWB","WWWBW","BWWWW"};
        int ans6 = ms.allSymmetric(grid);
        System.out.println(ans6);

         */

        // test for spikeLen
        int[] input1 = {1,2};
        int[] input2 = {2,2,3,1,4,5};
        int[] input3 = {2,3,3,3,2,2,2,1};
        int spLen1 = ms.spikeLen(input1);
        int spLen2 = ms.spikeLen(input2);
        int spLen3 = ms.spikeLen(input3);
        assert (spLen1 == 2);
        assert (spLen2 == 6);
        assert (spLen3 == 4);
        System.out.println(spLen1);
        System.out.println(spLen2);
        System.out.println(spLen3);
    }
}
