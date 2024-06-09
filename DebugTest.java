public class DebugTest {
    int countRotationUtil(int list[], int low, int high) {
        if (high < low) {
            return 0;
        }
        if (high == low) {
            return low;
        }

        int mid = low+(high-low)/2;

        if (mid < high && list[mid+1]<list[mid]) {
            return (mid+1);
        }

        if (mid > low && list[mid] < list[mid - 1]) {
            return mid;
        }

        if (list[high] > list[mid]) {
            return countRotationUtil(list, low, mid);
        }

        return countRotationUtil(list, mid, high);
    }

    int countRotations(int size, int list[]) {
        int res = countRotationUtil(list, 0, size-1);
        return res;
    }

    public static void main(String[] args) {
        DebugTest d = new DebugTest();
        int list[] = {4,3,2,1};
        int ans = d.countRotations(4,list);
        System.out.print(ans);
    }

}
