package wzhkun.dotsandboxes.model.ai;

import wzhkun.dotsandboxes.model.Line;

public class NormalAIPlayer extends RandomAIPlayer {

    public NormalAIPlayer(String name) {
        super(name);
    }

    protected int getBoxCount() {
        return getGame().getHeight() * getGame().getWidth();
    }

    protected Line nextMove() {
        if (goodLines.size() != 0)
            return getBestGoodLine();
        if (safeLines.size() != 0)
            return getRandomSafeLine();
        return getBestBadLine();

    }

    protected int getBadDegree(Line line) {
        return new VirtualGame(getGame()).getBadDegree(line);
    }

    protected Line getBestBadLine() {
        Line bestBadMove = null;
        int minValue = getBoxCount() + 1;
        for (Line Line : badLines) {
            int badDegree = getBadDegree(Line);
            if (badDegree < minValue) {
                bestBadMove = Line;
                minValue = badDegree;
            }
        }
        return bestBadMove;
    }
}
