package wzhkun.dotsandboxes.model.ai;

import java.util.ArrayList;

import wzhkun.dotsandboxes.model.Line;

public class HardAIPlayer extends NormalAIPlayer {
    public HardAIPlayer(String name) {
        super(name);
    }

    @Override
    protected Line nextMove() {
        if (safeLines.size() != 0) {
            if (goodLines.size() != 0)
                return getBestGoodLine();
            else
                return getRandomSafeLine();
        }
        if (goodLines.size() == 0) {
            return getBestBadLine();
        }
        if (badLines.size() == 0) {
            return getBestGoodLine();
        }

        ArrayList<Line> bad = new ArrayList<>();
        ArrayList<Line> bad2 = new ArrayList<>();
        ArrayList<Line> good = new ArrayList<>();
        ArrayList<Line> good2 = new ArrayList<>();
        int badValue = getBoxCount() + 1;
        int bad2Value = getBoxCount() + 1;
        int goodValue = 0;
        int goodValue2 = 0;
        for (Line Line : badLines) {
            int badDegree = getBadDegree(Line);
            if (badDegree < badValue) {
                bad2.clear();
                for (Line m : bad) {
                    bad2.add(m);
                }
                bad2Value = badValue;
                bad.clear();
                bad.add(Line);
                badValue = badDegree;
            } else if (badDegree == badValue) {
                bad.add(Line);
            }
        }
        for (Line Line : goodLines) {
            int goodDegree = getGoodDegree(Line);
            if (goodDegree > goodValue) {
                good2.clear();
                for (Line m : good) {
                    good2.add(m);
                }
                goodValue2 = goodValue;
                good.clear();
                good.add(Line);
                goodValue = goodDegree;
            } else if (goodDegree == goodValue) {
                good.add(Line);
            }
        }

        if (goodValue == 2 && badValue == 2 && bad.size() == 1
                && bad2.size() != 0 && bad2Value > 6) {
            if (goodValue2 > 2) {
                return good2.get((int) ((good2.size()) * Math.random()));
            }
            for (Line Line : good) {
                if (getCanOccupyBoxCount(Line) == 2) {
                    return Line;
                }
            }
            return bad.get(0);
        } else {
            return good.get((int) ((good.size()) * Math.random()));
        }
    }

    protected int getCanOccupyBoxCount(Line line) {
        VirtualGame sandbox=new VirtualGame(getGame());
        int countBefore=sandbox.getOccupiedBoxCount();
        sandbox.addMove(line);
        int countAfter=sandbox.getOccupiedBoxCount();
        return countAfter-countBefore;
    }

    protected int getGoodDegree(Line line) {
        return new VirtualGame(getGame()).getGoodDegree(line);
    }
}
