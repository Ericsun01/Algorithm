import com.sun.org.apache.xerces.internal.util.SynchronizedSymbolTable;

import java.util.ArrayList;
import java.util.List;

public class IPV4 {
    public List<String> getValidAddr(String s) {
        List<String> ans = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        dfs(s, 0, 0, sb, ans);
        return ans;
    }

    private void dfs(String s, int ipIdx, int strIdx, StringBuilder sb, List<String> ans) {
        if (ipIdx == 3) {
            String finalSub = s.substring(strIdx, s.length());
            if (isValid(finalSub)) {
                sb.append(finalSub);
                ans.add(new String(sb));
                sb.delete(sb.length()-finalSub.length(), sb.length());
            }
            return;
        }
        for (int rightBound = strIdx; rightBound < Math.min(strIdx+3, s.length()); rightBound++) {
            String curSub = s.substring(strIdx, rightBound+1);
            if (isValid(curSub) && s.length()-rightBound-1 >= 3-ipIdx) {
                sb.append(curSub);
                sb.append(".");
                dfs(s, ipIdx+1, rightBound+1, sb, ans);
                sb.delete(sb.length()-curSub.length()-1, sb.length());
            }
        }
    }

    private boolean isValid(String s) {
        if ((s.length() > 1 && s.charAt(0) == '0') || s.length() > 3) {
            return false;
        }

        int sum = 0;
        for (int i=0; i<s.length(); i++) {
            sum = sum*10 + (s.charAt(i)-'0');
        }

        return sum <= 255;
    }

    public static void main(String[] input) {
        IPV4 ip = new IPV4();
        String input1 = "12311";
        String input2 = "196168112";
        String input3 = "148203015";
        List<String> res1 = ip.getValidAddr(input1);
        List<String> res2 = ip.getValidAddr(input2);
        List<String> res3 = ip.getValidAddr(input3);
        for (String s: res1) {
            System.out.print(s+" ");
        }

        System.out.println("");

        for (String s: res2) {
            System.out.print(s+" ");
        }

        System.out.println("");

        for (String s: res3) {
            System.out.print(s+" ");
        }
    }
}
