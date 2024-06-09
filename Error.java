public class Error {
    static final int MOD = 1000000007;

    public static int minimumErrors(String s, int x, int y) {
        int n = s.length();

        long[][] dp = new long[n + 1][2]; // dp[i][j] 表示到位置 i 时以 j 为最后一个字符（0 或 1）的最小错误数

        // 初始化 dp 数组
        dp[0][0] = 0;
        dp[0][1] = 0;

        for (int i = 1; i <= n; i++) {
            char c = s.charAt(i - 1);
            if (c == '0') {
                dp[i][0] = dp[i - 1][0];
                dp[i][1] = dp[i - 1][0] + y; // '0' 后面接着 '1'，增加 y 个错误数
            } else if (c == '1') {
                dp[i][0] = dp[i - 1][1] + x; // '1' 后面接着 '0'，增加 x 个错误数
                dp[i][1] = dp[i - 1][1];
            } else if (c == '!') {
                dp[i][0] = Math.min(dp[i - 1][0], dp[i - 1][1] + y); // '!' 可以替换为 '0'，或者根据规则产生更多错误数
                dp[i][1] = Math.min(dp[i - 1][1], dp[i - 1][0] + x); // '!' 可以替换为 '1'，或者根据规则产生更多错误数
            }
            dp[i][0] %= MOD;
            dp[i][1] %= MOD;

        }

        return (int) Math.min(dp[n][0], dp[n][1]);
    }

    public static void main(String[] args) {
        String s = "1010!1!01";
        int x = 2;
        int y = 1;
        System.out.println(minimumErrors(s, x, y));
    }
}
