import com.sun.org.apache.bcel.internal.generic.SWAP;

import java.util.Scanner;
import java.util.*;
public class Sol {
    public int position(String input) {
        // Return the minimum number
        // of swaps required to sort the array
        if (input == null || input.length() == 0) {
            return 0;
        }
        int ans=0;
        String[] s1 = input.split(",");
        int[] array = new int[s1.length];
        int[] helper = new int[s1.length];
        for (int i=0; i<s1.length;i++) {
            array[i] = Integer.valueOf(s1[i]);
            helper[i] = array[i];
        }
        Arrays.sort(helper);
        // Hashmap which stores the
        // indexes of the input array
        Map<Integer, Integer> map =new HashMap<>();
        // O(1)时间即可找到需要的index

        for (int j=0; j< array.length; j++) {
            map.put(array[j],j);
        }
        for (int k=0; k< array.length; k++) {
            // This is checking whether
            // the current element is
            // at the right place or not
            if (array[k]!=helper[k]) {
                ans++;
                int init = array[k];
                // If not, swap this element
                // with the index of the
                // element which should come here
                swap(array, k, map.get(helper[k]));
                // Update the indexes in
                // the hashmap accordingly
                // 保证下一次swap可以找到需要交换元素的正确index
                map.put(init, map.get(helper[k]));
                map.put(helper[k],k);
            }
        }
        return ans;
    }

    private void swap(int[] array, int left, int right) {
        int temp = array[left];
        array[left] = array[right];
        array[right] = temp;
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String input = in.nextLine();
        Sol s = new Sol();
        int ans = s.position(input);
        System.out.println(ans);
    }
}
