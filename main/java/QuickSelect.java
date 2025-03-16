import java.util.Arrays;

public class QuickSelect {
    public int findKthSmallest(int[] nums, int k, int low, int high) {
        if (nums == null || nums.length == 0) {
            return 0;
        }

        if (k < 1 || k >nums.length) {
            System.out.println("invalid k value");
            return 0;
        }

        int partition = partition(nums, low, high);
        if (partition == k-1) {
            return nums[partition];
        }

        if (partition < k-1) {
            return findKthSmallest(nums, k, partition+1, high);
        }

        if (partition > k-1) {
            return findKthSmallest(nums, k, low, partition-1);
        }

        return nums[low];
    }

    public int partition(int[] nums, int low, int high) {
        int pivot = low + ((int)Math.random()*(high-low+1));
        swap(nums, pivot, high);

        int left = low;
        int right = high - 1;
        while (left <= right) {
            if (nums[left] <= nums[high]) {
                left++;
            } else if (nums[left] > nums[high]) {
                swap(nums, left, right);
                right--;
            }
        }

        swap(nums, left, high);
        return left;
    }

    public int findKthLargest(int[] nums, int k) {
        if (nums.length == 1) {
            return nums[0];
        }

        return quickSelect(nums, 0, nums.length-1, nums.length-k);
    }

    private int quickSelect(int[] nums, int left, int right, int targetIndex) {
        int pivotIndex = left+(int)(Math.random()*(right-left+1));
        int pivot = nums[pivotIndex];
        swap(nums, pivotIndex, right);

        int i = left, j = right-1;
        while (i <= j) {
            if (nums[i] <= pivot) {
                i++;
            } else if (nums[i] > pivot) {
                swap(nums, i, j);
                j--;
            }
        }

        swap(nums, i, right);
        if (i == targetIndex) {
            return pivot;
        } else if (i < targetIndex) {
            return quickSelect(nums, i+1, right, targetIndex-i);
        } else {
            return quickSelect(nums, left, i-1, targetIndex);
        }
    }

    public void swap (int[] nums, int low, int high) {
        int temp = nums[high];
        nums[high] = nums[low];
        nums[low] = temp;
    }

    public static void main(String args[]) {
        QuickSelect q = new QuickSelect();
        /*
        int[] arr1 = {10,4,5,8,6,11,26};
        int ans = q.findKthSmallest(arr1,2,0,arr1.length-1);
        System.out.println(ans);
        System.out.println(Arrays.toString(arr1));
        */

        int[] arr2 = {3,2,3,1,2,4,5,5,6};
        int ans2 = q.findKthLargest(arr2, 4);
        System.out.println(ans2);
        System.out.println(Arrays.toString(arr2));
    }
}
