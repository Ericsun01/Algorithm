import java.util.*;

// 判断点是否在三角形内， 边界时其中一个子三角形是0
// 另一题：线段随机取两点，构成三角形概率的数学推导
// https://www.geeksforgeeks.org/probability-of-cutting-a-rope-into-three-pieces-such-that-the-sides-form-a-triangle/
public class Triangle {
    static double area(int x1, int y1, int x2, int y2,
                       int x3, int y3)
    {
        return Math.abs((x1*(y2-y3) + x2*(y3-y1)+
                x3*(y1-y2))/2.0);
    }

    /* A function to check whether point P(x, y) lies
       inside the triangle formed by A(x1, y1),
       B(x2, y2) and C(x3, y3) */
    static boolean isInside(int x1, int y1, int x2,
                            int y2, int x3, int y3, int x, int y)
    {
        /* Calculate area of triangle ABC */
        double A = area (x1, y1, x2, y2, x3, y3);

        /* Calculate area of triangle PBC */
        double A1 = area (x, y, x2, y2, x3, y3);

        /* Calculate area of triangle PAC */
        double A2 = area (x1, y1, x, y, x3, y3);

        /* Calculate area of triangle PAB */
        double A3 = area (x1, y1, x2, y2, x, y);

        /* Check if sum of A1, A2 and A3 is same as A */
        return (A == A1 + A2 + A3);
    }

    /* Driver program to test above function */
    public static void main(String[] args)
    {
        /* Let us check whether the point P(10, 15)
           lies inside the triangle formed by
           A(0, 0), B(20, 0) and C(10, 30) */
        if (isInside(0, 0, 20, 0, 10, 30, 10, 15))
            System.out.println("Inside");
        else
            System.out.println("Not Inside");

    }
}
