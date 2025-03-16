public class MostSprintScores {
    public int mostPoints(int[] sprints, int k) {
        int len = sprints.length;
        if (len == 0 || k == 0) {
            return 0;
        }

        /*
        * leftVal, nextVal: leftmost value, next value to be included of the sliding window
        * startInd, endInd: start sprint element and end sprint element of sliding window
        * curSum, maxSum: current sum of scores, and the answer
        * */
        int leftVal = 0, nextVal = 0, startInd = 0, endInd = 0, curSum = 0, maxSum = 0;
         // step1: set an initial window of size k
        int numOfdays = 0;
        while (endInd < len) {
            if (numOfdays >= k) {
                break;
            }
            curSum += curSprintScores(1, sprints[endInd]);
            numOfdays+=sprints[endInd++];
        }

        endInd--;
        leftVal = 1;
        nextVal = k-(numOfdays-sprints[endInd])+1;
        if (nextVal > sprints[endInd]) { // this case means current window reaches the rightmost of current sprint
            nextVal = 1;
        } else { // remove all values from nextVal(inclusive) to the end of current sprint score(inclusive)
            curSum -= curSprintScores(nextVal, sprints[endInd]);
        }
        maxSum = curSum;

         // step2: iterate through sprints
        boolean windowReachEdge = false; // when nextVal == 1, this indicates sliding window reach right edge of a sprint
        while (endInd < len) {
            // update curSum, maxSum
            curSum = curSum-leftVal+nextVal;
            maxSum = Math.max(maxSum, curSum);

            // move sliding window
            if (leftVal + 1 <= sprints[startInd]) {
                leftVal++;
            } else {
                startInd++;
                leftVal = 1;
            }

            if (windowReachEdge) {
                windowReachEdge = false;
                endInd++;
            }

            if (nextVal+1<=sprints[endInd]) {
                nextVal++;
            } else {
                windowReachEdge = true;
                nextVal = 1;
                if (endInd == len-1) { // already reach righmost, nextVal shouldn't exist
                    break;
                }
            }
        }

        return maxSum;
    }

    private int curSprintScores(int first, int last) {
        if (first == last) {
            return first;
        }

        return ((first+last)*((last-first)+1))/2;
    }

    public static void main(String args[]) {
    int[] sprints1 = {2,3,2};
    int[] sprints2 = {2,5,5};
    int[] sprints3 = {1,1,1};
    int[] sprints4 = {8888,10000,9999,9988};
    int k1 = 4;
    int k2 = 6;
    int k3 = 3;
    int k4 = 1;
    MostSprintScores mp = new MostSprintScores();
    int ans1 = mp.mostPoints(sprints1, k1);
    int ans2 = mp.mostPoints(sprints2, k2);
    int ans3 = mp.mostPoints(sprints3, k3);
    int ans4 = mp.mostPoints(sprints4, k4);
    assert (ans1 == 8);
    assert (ans2 == 20);
    assert (ans3 == 3);
    assert (ans4 == 10000);
    System.out.println(ans1);
    System.out.println(ans2);
    System.out.println(ans3);
    System.out.println(ans4);
    }
}
