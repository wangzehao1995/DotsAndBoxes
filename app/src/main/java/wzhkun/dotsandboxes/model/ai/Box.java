package wzhkun.dotsandboxes.model.ai;

/**
 * Created by wangzehao on 2015/7/11.
 */
public class Box {
    boolean left;
    boolean top;
    boolean right;
    boolean bottom;
    boolean occupied;

    Box(boolean l, boolean t, boolean r, boolean b) {
        this.left = l;
        this.top = t;
        this.right = r;
        this.bottom = b;
        if (l && t && r && b)
            this.occupied = true;
        else
            this.occupied = false;
    }

    int occupiedLineCount() {
        int count = 0;
        if (this.left)
            count++;
        if (this.right)
            count++;
        if (this.top)
            count++;
        if (this.bottom)
            count++;
        return count;
    }
}
