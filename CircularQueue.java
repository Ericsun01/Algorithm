public class CircularQueue {
    int head;
    int tail;
    int size;
    Integer[] array;

    public CircularQueue(int cap) {
        this.array = new Integer[cap];
        this.head = 0;
        this.tail = 0;
        this.size = 0;
    }

    public Integer poll() {
        // 使用Integer是为了好返回Null
        if (size == 0) {
            return null;
        }

        Integer ans = array[head];
        array[head] = null;
        head = head + 1 == array.length? 0 : head + 1;
        size--;
        return ans;
    }

    public boolean offer(Integer element) {
        if (size == array.length) {
            return false;
        }

        array[tail] = element;
        size++;
        tail = tail+1 == array.length ? 0 : tail+1;
        return true;
    }

    public Integer peek() {
        if (size == 0) {
            return null;
        }

        return array[head];
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
}
