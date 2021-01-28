import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class dict {
    public int diction(Map<String, Integer> dic, String input, int cut) {
       if (input == null || input.length() == 0) {
           return -1;
       }

       int[] ans = new int[1];
       DFS(dic, input, cut, ans, 0, 0);
       return ans[0];
    }

    private void DFS(Map<String, Integer> dic, String input, int cut, int[] ans, int index, int sum) {
        if (index == input.length()) {
            if (cut == 0) {
                ans[0] = Math.max(ans[0], sum);
                System.out.println("ans is "+ans[0]);
            }
            return;
        }

        for (int i=index; i<=input.length(); i++) {  // substring右边界不包含i ！要想substring包含最后一个字母，必须让i出界
            if (dic.containsKey(input.substring(index, i))) {
                sum += dic.get(input.substring(index, i));
                System.out.println("sum is "+sum);
                DFS(dic, input, cut-1, ans, i, sum);   // substring右边界不包含i ！传参的时候直接传i就是下一个要处理的index
                sum -= dic.get(input.substring(index, i));
            }
        }
    }


    public static void main(String[] args) {
        Map<String, Integer> dic = new HashMap<>();
        dic.put("abc",1);
        dic.put("bcd",2);
        dic.put("f",7);
        dic.put("abcb",14);
        dic.put("cd",11);
        dic.put("abcbcd",20);
        String input = "abcbcdf";
        dict s = new dict();
        int ans = s.diction(dic, input, 3);
        System.out.println(ans);
    }
}
