package wzhkun.dotsandboxes.model;

/**
 * Created by wangzehao on 2015/7/11.
 */
public class AINormal extends AIPlayer{
    public AINormal(String name) {
        super(name);
    }

    @Override
    public int getDifficulty() {
        return 1;
    }
}
