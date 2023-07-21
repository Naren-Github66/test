package test;

public class HashUtils {
    /**
     * A python built-in hash function implementation
     *
     * @param s string to hash
     * @return hash of s
     */
    public static long hash(String s) {
        if (s == null) {
            return 587058L;
        }
        if (s.isEmpty()) {
            return 0L;
        }

        long res = s.charAt(0) << 7;
        for (int i = 0; i < s.length(); ++i) {
            res = (1000003 * res) ^ s.charAt(i);
        }
        res ^= s.length();
        if (res == -1L) {
            res = -2L;
        }
        return res;
    }

    /**
     * A python built-in hash function implementation
     *
     * @param s byte array to hash
     * @return hash of s
     */
    public static long hash(byte[] s) {
        if (s == null) {
            return 587058L;
        }
        if (s.length == 0) {
            return 0L;
        }
        long b = (s[0] < 0 ? 256 + s[0] : s[0]);
        long res = b << 7;
        for (int i = 0; i < s.length; ++i) {
            b = (s[i] < 0 ? 256 + s[i] : s[i]);
            res = (1000003 * res) ^ b;
        }
        res ^= s.length;
        if (res == -1L) {
            res = -2L;
        }
        return res;
    }

    public static int mod(long a, int b) {
        int res = (int) (a % b);
        res = res < 0 ? res + b : res;
        return res;
    }

    public static int hashMod(String s, int b) {
        return mod(hash(s), b);
    }

    public static long hash(String... strs){
        if(strs == null || strs.length == 0){
            return 0L;
        }
        String ss = strs[0];
        long res = ss.charAt(0) << 7;
        int len = 0;
        for(String s : strs){
            len += s.length();
            for (int i = 0; i < s.length(); ++i) {
                res = (1000003 * res) ^ s.charAt(i);
            }
        }
        res ^= len;
        if (res == -1L) {
            res = -2L;
        }
        return res;
    }

    public static long imohash(String key) {
        long h = 0;
        key = key != null ? key : "";

        for (char x : key.toCharArray()) {
            h = ((x + h) & 0xFFFFFFFFL);
            h = ((h << 10) + h) & 0xFFFFFFFFL;
            h ^= (h >>> 6);
        }
        h = ((h << 3) + h) & 0xFFFFFFFFL;
        h ^= (h >>> 11);
        h = ((h << 15) + h) & 0xFFFFFFFFL;
        return h;
    }

    public static int imohashMod(String s, int b) {
        return mod(imohash(s), b);
    }
}
