package wzhkun.dotsandboxes;

/**
 * Created by wangzehao on 2015/7/11.
 */
public class Util {
    public static boolean[][] deepClone(boolean[][] array) {
        boolean[][] result = new boolean[array.length][];
        for (int i = 0; i < array.length; i++) {
            result[i] = array[i].clone();
        }
        return result;
    }
}
