import java.util.Arrays;

public class QuickSelect {
    public int[] kSmallest(int[] array, int k) {
        if (array.length == 0 || k == 0) {
            return new int[0];
        }

        // quickselect to find kth smallest element
        // after calling this method, the first k elements in the array are the k smallest ones
        // but not sorted
        quickSelect(array, 0,array.length-1, k-1);
        // copy out the first k elements and sort them
        int[] ans = Arrays.copyOf(array, k);
        Arrays.sort(ans);
        return ans;
    }

    public void quickSelect(int[] array, int left, int right, int target) {
        // like quick sort, we need to do the partition using pivot value
        int temp = partition(array, left, right);
        // unlike quick sort, we only need to do quickselect on at most one partition
        // if the pivot is already the kth smallest element, we can directly return.
        if (temp == target) {
            return;
        } else if (temp < target) {
            // only need to recursively call quick select on the left partition
            // if the kth smallest one is in the left partition
            quickSelect(array, temp+1, right, target);
        } else {
            // only need to recursively call quick select on the right partition
            // if the kth smallest one is in the right partition
            quickSelect(array, left, temp-1, target);
        }
    }

    public int partition(int[] array, int left, int right) {
        int pivot = array[right];
        int i = 0;
        int j = right - 1;
        while (i <= j) {
            if (array[i] < pivot) {
                i++;
            } else if (array[j] >= pivot){
                j--;
            } else if (array[i] >= pivot){
                swap(array, i, j--);
            } else if (array[j] < pivot) {
                swap(array, i++, j);
            }
        }
        swap(array, i, right);
        return i;
    }

    public void swap(int[] array, int left, int right) {
        int cur = array[left];
        array[left] = array[right];
        array[right] = cur;
    }

    public static void main(String[] args) {
        QuickSelect qs = new QuickSelect();
        int[] input = new int[]{12, 32, 99, 4, 52, 21};
        int[] result = qs.kSmallest(input, 4);
        System.out.println(Arrays.toString(result));
    }
}
