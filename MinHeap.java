import java.util.Arrays;
import java.util.NoSuchElementException;

public class MinHeap {
    private int[] array;
    private int size;

    public MinHeap(int[] array) {
        if (array == null || array.length == 0) {
            throw new IllegalArgumentException("input array cannot be null or empty");
        }
        this.array = array;
        size = array.length;
        heapify();
    }

    public MinHeap(int cap) {
        if (cap <= 0) {
            throw new IllegalArgumentException("capacity cannot be <= 0");
        }
        array = new int[cap];
        size = 0;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public boolean isFull() {
        return size == array.length;
    }

    private void heapify() {
        // 此处for循环是将所有有可能需要调整位置的元素全部过一遍
        for (int i = size / 2 - 1; i >= 0; i--) {
            percolateDown(i);
        }
    }

    public void percolateUp(int index) {
        while (index > 0) {
            int parent = index/2 - 1;
            if (array[parent] > array[index]) {
                swap(array, parent, index);
            } else {
                break;
            }
            index = parent;
        }
    }

    public void percolateDown(int index) {
        // 此处while循环是为了将某元素一直percolateDown到合适位置
        while (index <= size/2 - 1) {
            int leftChild = 2*index+1;
            int rightChild = 2*index+2;
            int swapIndex = leftChild;

            if (rightChild <= size - 1 && array[leftChild] > array[rightChild]) {
                swapIndex = rightChild;
            }
            if (array[index] > array[swapIndex]) {
                swap(array, index, swapIndex);
            } else {
                break;
            }
            index = swapIndex;
        }
    }

    private void swap(int[] array,int index, int swapIndex) {
        int tmp = array[index];
        array[index] = array[swapIndex];
        array[swapIndex] = tmp;
    }

    public int poll() {
        if (size == 0) {
            throw new NoSuchElementException("heap is empty");
        }
        int result = array[0];
        array[0] = array[size - 1];
        size--;
        percolateDown(0);
        return result;
    }

    public void offer(int element) {
        if (size == array.length) {
            array = Arrays.copyOf(array, (int)(array.length * 1.5));
        }
        array[size] = element;
        size++;
        percolateUp(size-1);
    }

    public int update(int index, int element) {
        if (index < 0 || index >= size - 1) {
            throw new ArrayIndexOutOfBoundsException("invalid index range");
        }

        int result = array[index];
        array[index] = element;
        if (result > element) {
            percolateUp(index);
        } else {
            percolateDown(index);
        }

        return result;
    }
}
