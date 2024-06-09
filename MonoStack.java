import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

/*
单调栈：在数组中对每一个数找“第一个满足某条件的数”，就可以使用单调栈
存放元素为Index, 初始化加入-1便于后续计算宽度
当栈顶元素k的heights[k] 小于或等于 当前元素i的heights[i]， i入栈
反之：
    1. 说明到达当前右边界，栈顶元素k出栈并且以它对应heights[k]值作为高度，当前i值和当前新栈顶index值
为左开右开区间（均不包含）计算宽度，结算以此heights[k]为高度的最大面积
    2. 新元素i入栈

处理完成整个heights数组后检查stack中是否还有非-1元素，若有：
以len为右开区间，重复上述第二步计算

TC = O(n) 每个元素最多一进一出各一次
SC = O(n)

https://leetcode.cn/problems/largest-rectangle-in-histogram/
*/

public class MonoStack {
    public int largestRectangleArea(int[] heights) {
        if (heights.length == 1) {
            return heights[0];
        }

        Deque<Integer> monoStack = new ArrayDeque<>();
        monoStack.offerFirst(-1);
        int maxArea = 0;
        for (int i=0; i<heights.length; i++) {
            while (monoStack.peekFirst() != -1 && heights[monoStack.peekFirst()] > heights[i]) {
                int pollIndex = monoStack.pollFirst();
                int height = heights[pollIndex];
                int width = i - monoStack.peekFirst() - 1;
                maxArea = Math.max(maxArea, height*width);
            }
            monoStack.offerFirst(i);
        }

        while (monoStack.peekFirst() != -1) {
            int pollIndex = monoStack.pollFirst();
            int height = heights[pollIndex];
            int width = heights.length - monoStack.peekFirst() - 1;
            maxArea = Math.max(maxArea, height*width);
        }

        return maxArea;
    }

    public static void main(String[] args) {
        int[] rec= {2,1,5,6,2,3};
        MonoStack ms = new MonoStack();
        int result = ms.largestRectangleArea(rec);
        System.out.println(result);
    }
}
