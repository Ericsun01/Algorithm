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

    public void swap (int[] nums, int low, int high) {
        int temp = nums[high];
        nums[high] = nums[low];
        nums[low] = temp;
    }

    public static void main(String args[]) {
        QuickSelect q = new QuickSelect();
        int[] arr = {10,4,5,8,6,11,26};
        int ans = q.findKthSmallest(arr,2,0,arr.length-1);
        System.out.println(ans);
        System.out.println(Arrays.toString(arr));
    }
}


// T avg = O(n), T worst = O(n^2)
