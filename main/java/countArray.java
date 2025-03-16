import java.util.Arrays;

public class countArray {
    public int[] countarray(int[] array) {
        int[] indexArray = initialIndexArray(array); //记录此轮sort之后index的位置，即当前sort结果存在这里
        int[] countArray = new int[array.length]; // 记录答案
        int[] helper = new int[array.length]; //记录此轮初始index位置。在循环中作为array, countArray的Index来源
        mergeSort(array, indexArray, countArray, helper, 0, array.length-1);
        return countArray;
    }

    private int[] initialIndexArray(int[] array) {
        int[] indices = new int[array.length];
        for (int i=0; i<array.length; i++) {
            indices[i] = i;
        }
        return indices;
    }

    private void mergeSort(int[] array, int[] indexArray, int[] countArray, int[] helper, int left, int right) {
        if (left >= right) {
            return;
        }
        int mid = (left + right)/2;
        mergeSort(array, indexArray, countArray, helper, left, mid);
        mergeSort(array, indexArray, countArray, helper, mid+1, right);
        merge(array, indexArray, countArray, helper, left, mid, right);
    }

    /*
    * 根据画图方便理解：
    * array, countArray index:  helper
    * helper                 :  l, mid, r
    * indexArray             :  cur
    * */
    private void merge(int[] array, int[] indexArray, int[] countArray, int[] helper, int left, int mid, int right) {
        copyArray(indexArray, helper, left, right);
        int l = left;
        int r = mid + 1;
        int cur = left;
        while(l <= mid) {
            /*
            * case1: r从右半边出界 或者 左边元素 小于 右边元素
            * 根据谁小移谁，此时肯定得是左边元素记录进indexArray，不论上述哪种情况这均是此次merge call里最后一次处理该元素。
            * 因此需要结算对于左元素而言，所有比它小的右元素数量。并且记录进countArray（画图理解）
            * 对于r-mid-1的r未出界可以这样理解：此时右元素是第一个大于左元素的数字，那么右半边所有在右元素之前的元素均小于左元素。(r-mid-1)
            * */
            if (r > right || array[helper[l]] <= array[helper[r]]) {
                countArray[helper[l]] += (r-mid-1);
                indexArray[cur++] = helper[l++];
            } else {
                /*
                * case2: 此时r未出界且左元素大于右元素。更新当前sort后的index即可。不用管countArray
                * 尽管countArray[helper[l]]在增加，但尚未到结算的时候
                * */
                indexArray[cur++] = helper[r++];
            }
        }
    }

    private void copyArray(int[] indexArray, int[] helper, int left, int right) {
        for (int i = left; i <= right; i++) {
            helper[i] = indexArray[i];
        }
    }

    public static void main(String[] args) {
        countArray s = new countArray();
        int[] array = {4,2,7,1};
        int[] ans = s.countarray(array);
        System.out.println(Arrays.toString(ans));
    }
}
