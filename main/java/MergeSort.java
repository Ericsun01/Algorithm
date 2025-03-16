import java.lang.reflect.Array;
import java.util.Arrays;

public class MergeSort {
    public int[] mergeSort(int[] array) {
        if (array == null || array.length <= 1) {
            return array;
        }

        int[] helper = new int[array.length];
        sort(array, helper, 0, array.length-1);
        return array;
    }

    private void sort(int[] array, int[] helper, int left, int right) {
        if (left >= right) {
            return;
        }

        int mid = left + (right - left)/2;
        sort(array, helper, left, mid);
        sort(array, helper, mid+1, right);
        merge(array, helper, left, right);
    }

    private void merge(int[] array, int[] helper, int left, int right) {
        for (int i=left; i<=right; i++) {
            helper[i] = array[i];
        }

        int mid = left + (right - left)/2;
        int leftBound = left;
        int rightBound = mid+1;
        while (leftBound <= mid && rightBound <= right) {
            if (helper[leftBound] <= helper[rightBound]) {
                array[left++] = helper[leftBound++];
            } else {
                array[left++] = helper[rightBound++];
            }
        }

        while (leftBound <= mid) {
            array[left++] = helper[leftBound++];
        }
    }

    public static void main(String[] args) {
        MergeSort ms = new MergeSort();
        int[] array = new int[]{3,4,1,6,8,2,7,5};
        int[] result = ms.mergeSort(array);
        System.out.println(Arrays.toString(result));
    }
}
