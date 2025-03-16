public class DeCompress {
    // this version can handle any digit of letter occurrence number
    public String decompress(String input) {
        // Write your solution here
        if (input.length() == 0) {
            return input;
        }

        char[] array = input.toCharArray();
        // step1: scan from left to right, calculate the lens of ans
        int fast = 0;
        int lenPlus = 0;
        int count = 0;
        for (; fast < array.length; fast++) {
            if (array[fast] >= '0' && array[fast] <= '9') {
                count = 10*count + (array[fast] - '0');
            } else {
                if (fast > 0) {
                    lenPlus += count - 2;
                    count = 0;
                }
            }
        }

        // post processing for the last count num
        lenPlus += count - 2;

        char[] ans = new char[array.length+lenPlus];
        int slow = ans.length-1;
        int digitEnd = 0;
        int digitStart = 0;
        // step2: scan from right to left, fill in the ans array
        for (fast = array.length-1; fast >= 0; fast--) {
            if (array[fast] >= '0' && array[fast] <= '9') {
                digitEnd = fast;
                while (array[fast] >= '0' && array[fast] <= '9') {
                    fast--;
                }
                digitStart = fast + 1;
                count = digitTransfer(array, digitStart, digitEnd);
                fast++; // the for loop will deduct fast pointer, need extra correction
            } else {
                while (count > 0) {
                    ans[slow--] = array[fast];
                    count--;
                }
            }
        }

        return new String(ans, slow+1, ans.length - slow - 1);
    }

    private int digitTransfer(char[] array, int start, int end) {
        int num = 0;
        while (start <= end) {
            num = num*10 + (array[start]-'0');
            start++;
        }
        return num;
    }

    public static void main(String[] args) {
        DeCompress dc = new DeCompress();
        String input = "z1x0a12b0c13d8e10";
        String ans = dc.decompress(input);
        System.out.println(ans);
    }
}
