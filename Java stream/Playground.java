import sun.reflect.generics.tree.Tree;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Playground {
    public int[] Merge(int[] a, int[] b) {
        if (a == null && b == null) {
            return new int[0];
        }

        int[] ans = new int[a.length+b.length];
        int i = 0;
        int j = 0;
        int k = 0;
        while (i < a.length && j < b.length) {
            if (a[i] < b[j]) {
                ans[k++] = a[i++];
            } else {
                ans[k++] = b[j++];
            }
        }

        while (i < a.length) {
            ans[k++] = a[i++];
        }
        while (j < b.length) {
            ans[k++] = b[j++];
        }

        return ans;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        String[] s1 = sc.nextLine().split(", ");  // 输入为{1, 2, 2, 3}这种，根据分割符改变split参数
        int a[] = new int[s1.length];
        for (int i=0; i<a.length; i++) {
            a[i] = Integer.valueOf(s1[i]);
        }

        String[] s2 = sc.nextLine().split(", ");
        int b[] = new int[s2.length];
        for (int i=0; i<b.length; i++) {
            b[i] = Integer.valueOf(s2[i]);
        }

        Playground p = new Playground();
        int[] ans = p.Merge(a,b);
        System.out.println("array a is "+Arrays.toString(a));
        System.out.println("array b is "+Arrays.toString(b));
        System.out.println(Arrays.toString(ans));
    }
}


