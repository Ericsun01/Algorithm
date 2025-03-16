import sun.reflect.generics.tree.Tree;

import java.lang.reflect.Array;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Playground {
    public long kth(int k) {
        // Write your solution here
        PriorityQueue<Long> minHeap = new PriorityQueue<>(k);
        Set<Long> visited = new HashSet<>();
        long head = 3*5*7;
        minHeap.offer(head);
        visited.add( head);

        while (k > 1) {
            Long cur = minHeap.poll();
            // generate neighbor: x+1,y,z
            if (visited.contains(cur*3)) {
                minHeap.offer(cur*3);
                visited.add(cur*3);
            }
            // generate neighbor: x,y+1,z
            if (visited.contains(cur*5)) {
                minHeap.offer(cur*5);
                visited.add(cur*5);
            }
            // generate neighbor: x,y,z+1
            if (visited.contains(cur*7)) {
                minHeap.offer(cur*7);
                visited.add(cur*7);
            }
            k--;
        }

        return minHeap.poll();
    }

    public static void main(String[] args) {
        Playground p = new Playground();
        int k = 3;
        Long res = p.kth(k);
        System.out.println(res);
    }
}

class Cell {
    int x;
    int y;
    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
    }
}






