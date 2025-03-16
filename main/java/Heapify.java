public class Heapify {
    // 堆化操作
    public static void heapify(int[] array) {
        int n = array.length;

        // 从最后一个非叶子节点开始向上进行堆化
        for (int i = n / 2 - 1; i >= 0; i--) {
            heapifyUtil(array, n, i);
        }
    }

    // 辅助方法，用于调整堆
    public static void heapifyUtil(int[] array, int n, int i) {
        int largest = i; // 假设父节点是最大的
        int left = 2 * i + 1; // 左子节点
        int right = 2 * i + 2; // 右子节点

        // 如果左子节点比父节点大，则更新最大值
        if (left < n && array[left] > array[largest]) {
            largest = left;
        }

        // 如果右子节点比父节点大，则更新最大值
        if (right < n && array[right] > array[largest]) {
            largest = right;
        }

        // 如果最大值不是父节点，则交换父节点和最大值节点，并递归调用堆化
        if (largest != i) {
            int temp = array[i];
            array[i] = array[largest];
            array[largest] = temp;

            heapifyUtil(array, n, largest);
        }
    }

    // 打印数组
    public static void printArray(int[] array) {
        for (int i = 0; i < array.length; i++) {
            System.out.print(array[i] + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        int[] array = {1, 2, 3, 4, 5, 6};

        System.out.println("Original array:");
        printArray(array);

        heapify(array);

        System.out.println("Heapified array:");
        printArray(array);
    }
}
