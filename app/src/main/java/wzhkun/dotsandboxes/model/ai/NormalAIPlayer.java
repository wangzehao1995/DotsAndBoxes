package wzhkun.dotsandboxes.model.ai;

import java.util.ArrayList;

import wzhkun.dotsandboxes.model.Direction;
import wzhkun.dotsandboxes.model.Game;
import wzhkun.dotsandboxes.model.Line;
import wzhkun.dotsandboxes.model.Player;

public class NormalAIPlayer extends RandomAIPlayer {

    public NormalAIPlayer(String name) {
        super(name);
    }

    protected int getBoxCount() {
        return getGame().getHeight() * getGame().getWidth();
    }

    protected Line nextMove() {
        if (goodLines.size() != 0)
            return goodLines.get((int) ((goodLines.size()) * Math.random()));
        if (safeLines.size() != 0)
            return safeLines.get((int) ((safeLines.size()) * Math.random()));
        else {
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

    protected int getBadDegree(Line line) {
        return new VirtualGame(getGame()).getBadDegree(line);
    }
}
