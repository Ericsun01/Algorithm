public class Grep {
    // 字符串类问题边界用实际index来表示，不要用offset!
    public void grep(String[] input, int n, String keyword) {
        if (input == null || input.length == 0) {
            return;
        }

        // mark = -1 保证第一个元素能被打印
        int mark = -1;
        for (int i=0; i<input.length; i++) {
            if (input[i].equals(keyword)) {
                mark = printGrep(i, n, mark, input);
            }
        }
    }

    private int printGrep(int index, int n, int mark, String[] input) {
        int upperBound = Math.max(index-n, mark+1);
        int lowerBound = Math.min(index+n, input.length-1);

        int j=0;
        for (j= upperBound; j<=lowerBound; j++) {
            System.out.println(input[j]);
        }

        return j-1;
    }

    public static void main(String[] args) {
        Grep g = new Grep();
        String[] input1 = new String[]{"s", "s", "world", "s", "s", "world", "s", "s"};
        String[] input2 = new String[]{"world", "s", "s", "s", "world"};
        String[] input3 = new String[]{"world", "world", "world"};
        //g.grep(input1, 2, "world");
        //g.grep(input2, 2, "world");
        g.grep(input3, 2, "world");
    }
}
