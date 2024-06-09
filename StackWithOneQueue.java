import java.util.ArrayDeque;
import java.util.Queue;

public class StackWithOneQueue {
    Queue<Integer> q = new ArrayDeque<>();

    public void push(int x) {
        // get previous size of queue
        int size = q.size();
        // Add current element
        q.offer(x);

        // Pop (or Dequeue) all previous
        // elements and put them after current
        // element
        for (int i=0; i<size; i++) {
            // this will add front element into
            // rear of queue
            int cur = q.poll();
            q.offer(cur);
        }
    }

    // Removes the top element
    public int pop() {
        if (q.isEmpty()) {
            return -1;
        }

        return q.poll();
    }

    // Returns top of stack
    public int top() {
        if (q.isEmpty()) {
            return -1;
        }

        return q.peek();
    }

    // Returns true if Stack is empty else false
    public boolean isEmpty() {
        return q.isEmpty();
    }

    // Driver program to test above methods
    public static void main(String[] args)
    {
        StackWithOneQueue  s = new StackWithOneQueue ();
        s.push(10);
        s.push(20);
        System.out.println("Top element :" + s.top());
        s.pop();
        s.push(30);
        s.pop();
        System.out.println("Top element :" + s.top());
    }
}
