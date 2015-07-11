package wzhkun.dotsandboxes.model;

/**
 * Created by wangzehao on 2015/7/11.
 */
public class AIHard extends AIPlayer{
    public AIHard(String name) {
        super(name);
    }

    @Override
    public int getDifficulty() {
        return 2;
    }
}
