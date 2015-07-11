package wzhkun.dotsandboxes.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

public class AIPlayer extends Player {
    protected Vector<Line> safeLine;
    protected Vector<Line> goodLine;
    protected Vector<Line> badLine;
    protected HashMap<Line, Integer> goodLineValue;
    protected HashMap<Line, Integer> badLineValue;
    protected HashMap<Line, Integer> goodLineType;

    private AIPlayer(Game game) {
        this("");
        this.addToGame(game);
    }

    public AIPlayer(String name) {
        super(name);

        safeLine = new Vector<>();
        goodLine = new Vector<>();
        badLine = new Vector<>();
        badLineValue = new HashMap<>();
        goodLineValue = new HashMap<>();
        goodLineType = new HashMap<>();

    }

    public Line move() {
        initialiseSafeLine();
        initialiseGoodLine();
        initialiseBadLine();

        if (getDifficulty() <= 1) {
            return normal();
        } else if (getDifficulty() == 2) {
            return hard();
        } else if (getDifficulty() >= 3) {
            return ultra();
        } else
            return random();
    }

    private Line Line(VBoard vboard) {
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 5; j++) {
                getHorizontalLines()[i][j] = vboard.horizontal[i][j];
                getVerticalLines()[j][i] = vboard.vertical[j][i];
            }
        }
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                getBoxes()[i][j] = new Box(getVerticalLines()[i][j], getHorizontalLines()[i][j],
                        getVerticalLines()[i][j + 1], getHorizontalLines()[i + 1][j]);
            }
        }
        initialiseSafeLine();
        initialiseGoodLine();
        initialiseBadLine();

        if (getDifficulty() == 1) {
            return normal();
        } else if (getDifficulty() == 2) {
            return hard();
        } else if (getDifficulty() == 3) {
            return ultra();
        } else
            return random();
    }

    private Line normal() {
        if (goodLine.size() != 0)
            return goodLine.get((int) ((goodLine.size()) * Math.random()));
        if (safeLine.size() != 0)
            return safeLine.get((int) ((safeLine.size()) * Math.random()));
        else {
            Line min = null;
            int minValue = 26;
            initialiseBadLineValue();
            for (Line Line : badLine) {
                if (badLineValue.get(Line) < minValue) {
                    min = Line;
                    minValue = badLineValue.get(Line);
                }
            }
            return min;
        }
    }

    private Line hard() {
        if (safeLine.size() != 0) {
            if (goodLine.size() != 0)
                return goodLine.get((int) ((goodLine.size()) * Math.random()));
            else
                return safeLine.get((int) ((safeLine.size()) * Math.random()));
        } else if (goodLine.size() != 0) {
            if (badLine.size() == 0)
                return goodLine.get((int) ((goodLine.size()) * Math.random()));
            initialiseGoodLineValue();
            initialiseBadLineValue();

            ArrayList<Line> bad = new ArrayList<>();
            ArrayList<Line> bad2 = new ArrayList<>();
            ArrayList<Line> good = new ArrayList<>();
            ArrayList<Line> good2 = new ArrayList<>();
            int badValue = 26;
            int bad2Value = 26;
            int goodValue = 0;
            int goodValue2 = 0;
            for (Line Line : badLine) {
                if (badLineValue.get(Line) < badValue) {
                    bad2.clear();
                    for (Line m : bad) {
                        bad2.add(m);
                    }
                    bad2Value = badValue;
                    bad.clear();
                    bad.add(Line);
                    badValue = badLineValue.get(Line);
                } else if (badLineValue.get(Line) == badValue) {
                    bad.add(Line);
                }
            }
            for (Line Line : goodLine) {
                if (goodLineValue.get(Line) > goodValue) {
                    good2.clear();
                    for (Line m : good) {
                        good2.add(m);
                    }
                    goodValue2 = goodValue;
                    good.clear();
                    good.add(Line);
                    goodValue = goodLineValue.get(Line);
                } else if (goodLineValue.get(Line) == goodValue) {
                    good.add(Line);
                }
            }

            if (goodValue == 2 && badValue == 2 && bad.size() == 1
                    && bad2.size() != 0 && bad2Value > 6) {
                if (goodValue2 > 2) {
                    return good2.get((int) ((good2.size()) * Math.random()));
                }
                initialiseGoodLineType();
                for (Line Line : good) {
                    if (goodLineType.get(Line) == 2) {
                        return Line;
                    }
                }
                return bad.get(0);
            } else
                return good.get((int) ((good.size()) * Math.random()));

        } else {
            Line min = null;
            int minValue = 26;
            initialiseBadLineValue();
            for (Line Line : badLine) {
                if (badLineValue.get(Line) < minValue) {
                    min = Line;
                    minValue = badLineValue.get(Line);
                }
            }
            return min;
        }

    }

    private Line ultra() {
        if (safeLine.size() != 0) {
            if (goodLine.size() != 0)
                return goodLine.get((int) ((goodLine.size()) * Math.random()));
            else
                return safeLine.get((int) ((safeLine.size()) * Math.random()));
        } else if (goodLine.size() != 0) {
            if (badLine.size() == 0)
                return goodLine.get((int) ((goodLine.size()) * Math.random()));
            initialiseGoodLineValue();
            initialiseBadLineValue();

            ArrayList<Line> bad = new ArrayList<>();
            ArrayList<Line> bad2 = new ArrayList<>();
            ArrayList<Line> good = new ArrayList<>();
            ArrayList<Line> good2 = new ArrayList<>();
            int badValue = 26;
            int bad2Value = 26;
            int goodValue = 0;
            int goodValue2 = 0;
            for (Line Line : badLine) {
                if (badLineValue.get(Line) < badValue) {
                    bad2.clear();
                    for (Line m : bad) {
                        bad2.add(m);
                    }
                    bad2Value = badValue;
                    bad.clear();
                    bad.add(Line);
                    badValue = badLineValue.get(Line);
                } else if (badLineValue.get(Line) == badValue) {
                    bad.add(Line);
                }
            }
            for (Line Line : goodLine) {
                if (goodLineValue.get(Line) > goodValue) {
                    good2.clear();
                    for (Line m : good) {
                        good2.add(m);
                    }
                    goodValue2 = goodValue;
                    good.clear();
                    good.add(Line);
                    goodValue = goodLineValue.get(Line);
                } else if (goodLineValue.get(Line) == goodValue) {
                    good.add(Line);
                }
            }

            if (goodValue == 2 && badValue == 2 && bad.size() == 1
                    && bad2.size() != 0 && bad2Value > 6) {
                if (goodValue2 > 2) {
                    return good2.get((int) ((good2.size()) * Math.random()));
                }
                initialiseGoodLineType();
                for (Line Line : good) {
                    if (goodLineType.get(Line) == 2) {
                        return Line;
                    }
                }
                return bad.get(0);
            } else
                return good.get((int) ((good.size()) * Math.random()));

        } else {
            Line min = null;
            int minValue = 26;
            initialiseBadLineValue();
            for (Line Line : badLine) {
                if (badLineValue.get(Line) < minValue) {
                    min = Line;
                    minValue = badLineValue.get(Line);
                }
            }
            return min;
        }

    }

    private Line random() {
        if (goodLine.size() != 0)
            return goodLine.get((int) ((goodLine.size()) * Math.random()));
        if (safeLine.size() != 0)
            return safeLine.get((int) ((safeLine.size()) * Math.random()));
        Vector<Line> temp = new Vector<>();
        for (int i = 0; i < 6; i++)
            for (int j = 0; j < 5; j++) {
                if (!getHorizontalLines()[i][j])
                    temp.add(new Line(Direction.HORIZONTAL, i, j));
                if (!getVerticalLines()[j][i])
                    temp.add(new Line(Direction.VERTICAL, j, i));
            }
        return temp.get((int) ((temp.size()) * Math.random()));
    }

    private void initialiseGoodLine() {
        int counter;
        boolean t1 = false;
        boolean t2 = false;
        goodLine.clear();
        Box[][] boxes=getBoxes();
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 5; j++) {
                if (!getHorizontalLines()[i][j]) {
                    if (i == 0) {
                        counter = 0;
                        if (boxes[i][j].left)
                            counter++;
                        if (boxes[i][j].bottom)
                            counter++;
                        if (boxes[i][j].right)
                            counter++;
                        if (boxes[i][j].occupiedLineCount() == 3)
                            goodLine.add(new Line(Direction.HORIZONTAL, i, j));
                    } else if (i == 5) {

                        counter = 0;
                        if (boxes[i - 1][j].left)
                            counter++;
                        if (boxes[i - 1][j].top)
                            counter++;
                        if (boxes[i - 1][j].right)
                            counter++;
                        if (counter == 3)
                            goodLine.add(new Line(Direction.HORIZONTAL, i, j));
                    } else {
                        counter = 0;
                        if (boxes[i][j].left)
                            counter++;
                        if (boxes[i][j].bottom)
                            counter++;
                        if (boxes[i][j].right)
                            counter++;
                        if (counter == 3)
                            t1 = true;
                        counter = 0;
                        if (boxes[i - 1][j].left)
                            counter++;
                        if (boxes[i - 1][j].top)
                            counter++;
                        if (boxes[i - 1][j].right)
                            counter++;
                        if (counter == 3)
                            t2 = true;
                        if (t1 || t2)
                            goodLine.add(new Line(Direction.HORIZONTAL, i, j));
                        t1 = false;
                        t2 = false;
                    }
                }
                if (!getVerticalLines()[j][i]) {
                    if (i == 0) {
                        counter = 0;
                        if (boxes[j][i].right)
                            counter++;
                        if (boxes[j][i].bottom)
                            counter++;
                        if (boxes[j][i].top)
                            counter++;
                        if (counter == 3)
                            goodLine.add(new Line(Direction.VERTICAL, j, i));
                    } else if (i == 5) {
                        counter = 0;
                        if (boxes[j][i - 1].left)
                            counter++;
                        if (boxes[j][i - 1].top)
                            counter++;
                        if (boxes[j][i - 1].bottom)
                            counter++;
                        if (counter == 3)
                            goodLine.add(new Line(Direction.VERTICAL, j, i));
                    } else {
                        counter = 0;
                        if (boxes[j][i].right)
                            counter++;
                        if (boxes[j][i].bottom)
                            counter++;
                        if (boxes[j][i].top)
                            counter++;
                        if (counter == 3)
                            t1 = true;
                        counter = 0;
                        if (boxes[j][i - 1].left)
                            counter++;
                        if (boxes[j][i - 1].top)
                            counter++;
                        if (boxes[j][i - 1].bottom)
                            counter++;
                        if (counter == 3)
                            t2 = true;
                        if (t1 || t2)
                            goodLine.add(new Line(Direction.VERTICAL, j, i));
                        t1 = false;
                        t2 = false;
                    }
                }
            }
        }

    }

    private void initialiseBadLine() {
        int counter;
        boolean t1 = false;
        boolean t2 = false;
        badLine.clear();
        Box[][] boxes=getBoxes();
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 5; j++) {
                if (!getHorizontalLines()[i][j]) {
                    if (i == 0) {
                        counter = 0;
                        if (boxes[i][j].left)
                            counter++;
                        if (boxes[i][j].bottom)
                            counter++;
                        if (boxes[i][j].right)
                            counter++;
                        if (counter == 2)
                            badLine.add(new Line(Direction.HORIZONTAL, i, j));
                    } else if (i == 5) {

                        counter = 0;
                        if (boxes[i - 1][j].left)
                            counter++;
                        if (boxes[i - 1][j].top)
                            counter++;
                        if (boxes[i - 1][j].right)
                            counter++;
                        if (counter == 2)
                            badLine.add(new Line(Direction.HORIZONTAL, i, j));
                    } else {
                        counter = 0;
                        if (boxes[i][j].left)
                            counter++;
                        if (boxes[i][j].bottom)
                            counter++;
                        if (boxes[i][j].right)
                            counter++;
                        if (counter == 2)
                            t1 = true;
                        counter = 0;
                        if (boxes[i - 1][j].left)
                            counter++;
                        if (boxes[i - 1][j].top)
                            counter++;
                        if (boxes[i - 1][j].right)
                            counter++;
                        if (counter == 2)
                            t2 = true;
                        if (t1 || t2)
                            badLine.add(new Line(Direction.HORIZONTAL, i, j));
                        t1 = false;
                        t2 = false;
                    }
                }
                if (!getVerticalLines()[j][i]) {
                    if (i == 0) {
                        counter = 0;
                        if (boxes[j][i].right)
                            counter++;
                        if (boxes[j][i].bottom)
                            counter++;
                        if (boxes[j][i].top)
                            counter++;
                        if (counter == 2)
                            badLine.add(new Line(Direction.VERTICAL, j, i));
                    } else if (i == 5) {
                        counter = 0;
                        if (boxes[j][i - 1].left)
                            counter++;
                        if (boxes[j][i - 1].top)
                            counter++;
                        if (boxes[j][i - 1].bottom)
                            counter++;
                        if (counter == 2)
                            badLine.add(new Line(Direction.VERTICAL, j, i));
                    } else {
                        counter = 0;
                        if (boxes[j][i].right)
                            counter++;
                        if (boxes[j][i].bottom)
                            counter++;
                        if (boxes[j][i].top)
                            counter++;
                        if (counter == 2)
                            t1 = true;
                        counter = 0;
                        if (boxes[j][i - 1].left)
                            counter++;
                        if (boxes[j][i - 1].top)
                            counter++;
                        if (boxes[j][i - 1].bottom)
                            counter++;
                        if (counter == 2)
                            t2 = true;
                        if (t1 || t2)
                            badLine.add(new Line(Direction.VERTICAL, j, i));
                        t1 = false;
                        t2 = false;
                    }
                }
            }
        }
        for (Line a : goodLine) {
            try {
                for (Line b : badLine) {
                    if (a.direction() == b.direction() && a.row() == b.row() && a.column() == b.column()) {
                        badLine.remove(b);
                    }
                }
            } catch (Exception e) {
            }
        }
    }

    private void initialiseSafeLine() {
        int counter;
        boolean t1 = false;
        boolean t2 = false;
        safeLine.clear();
        Box[][] boxes=getBoxes();
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 5; j++) {
                if (!getHorizontalLines()[i][j]) {
                    if (i == 0) {
                        counter = 0;
                        if (boxes[i][j].left)
                            counter++;
                        if (boxes[i][j].bottom)
                            counter++;
                        if (boxes[i][j].right)
                            counter++;
                        if (counter < 2)
                            safeLine.add(new Line(Direction.HORIZONTAL, i, j));
                    } else if (i == 5) {

                        counter = 0;
                        if (boxes[i - 1][j].left)
                            counter++;
                        if (boxes[i - 1][j].top)
                            counter++;
                        if (boxes[i - 1][j].right)
                            counter++;
                        if (counter < 2)
                            safeLine.add(new Line(Direction.HORIZONTAL, i, j));
                    } else {
                        counter = 0;
                        if (boxes[i][j].left)
                            counter++;
                        if (boxes[i][j].bottom)
                            counter++;
                        if (boxes[i][j].right)
                            counter++;
                        if (counter < 2)
                            t1 = true;
                        counter = 0;
                        if (boxes[i - 1][j].left)
                            counter++;
                        if (boxes[i - 1][j].top)
                            counter++;
                        if (boxes[i - 1][j].right)
                            counter++;
                        if (counter < 2)
                            t2 = true;
                        if (t1 && t2)
                            safeLine.add(new Line(Direction.HORIZONTAL, i, j));
                        t1 = false;
                        t2 = false;
                    }
                }
                if (!getVerticalLines()[j][i]) {
                    if (i == 0) {
                        counter = 0;
                        if (boxes[j][i].right)
                            counter++;
                        if (boxes[j][i].bottom)
                            counter++;
                        if (boxes[j][i].top)
                            counter++;
                        if (counter < 2)
                            safeLine.add(new Line(Direction.VERTICAL, j, i));
                    } else if (i == 5) {
                        counter = 0;
                        if (boxes[j][i - 1].left)
                            counter++;
                        if (boxes[j][i - 1].top)
                            counter++;
                        if (boxes[j][i - 1].bottom)
                            counter++;
                        if (counter < 2)
                            safeLine.add(new Line(Direction.VERTICAL, j, i));
                    } else {
                        counter = 0;
                        if (boxes[j][i].right)
                            counter++;
                        if (boxes[j][i].bottom)
                            counter++;
                        if (boxes[j][i].top)
                            counter++;
                        if (counter < 2)
                            t1 = true;
                        counter = 0;
                        if (boxes[j][i - 1].left)
                            counter++;
                        if (boxes[j][i - 1].top)
                            counter++;
                        if (boxes[j][i - 1].bottom)
                            counter++;
                        if (counter < 2)
                            t2 = true;
                        if (t1 && t2)
                            safeLine.add(new Line(Direction.VERTICAL, j, i));
                        t1 = false;
                        t2 = false;
                    }
                }
            }
        }
    }



    private void initialiseGoodLineValue() {
        if (goodLine.isEmpty())
            return;
        goodLineValue.clear();
        for (Line Line : goodLine) {
            VBoard vboard = new VBoard(getHorizontalLines(), getVerticalLines());
            goodLineValue.put(Line, vboard.getGoodLineValue(Line));
        }
    }

    private void initialiseBadLineValue() {
        if (badLine.isEmpty())
            return;
        badLineValue.clear();
        for (Line Line : badLine) {
            VBoard vboard = new VBoard(getHorizontalLines(), getVerticalLines());
            badLineValue.put(Line, vboard.getBadLineValue(Line));
        }
    }

    private void initialiseGoodLineType() {
        if (goodLine.isEmpty())
            return;
        goodLineType.clear();
        Box[][] boxes=getBoxes();
        for (Line Line : goodLine) {
            if (Line.direction() == Direction.HORIZONTAL) {
                if (Line.row() == 0)
                    goodLineType.put(Line, 1);
                else if (Line.row() == 5)
                    goodLineType.put(Line, 1);
                else {
                    int counter = 0;
                    if (boxes[Line.row()][Line.column()].occupiedLineCount() == 3)
                        counter++;
                    if (boxes[Line.row() - 1][Line.column()].occupiedLineCount() == 3)
                        counter++;
                    goodLineType.put(Line, counter);
                }
            } else {
                if (Line.column() == 0)
                    goodLineType.put(Line, 1);
                else if (Line.column() == 5)
                    goodLineType.put(Line, 1);
                else {
                    int counter = 0;
                    if (boxes[Line.row()][Line.column()].occupiedLineCount() == 3)
                        counter++;
                    if (boxes[Line.row()][Line.column() - 1].occupiedLineCount() == 3)
                        counter++;
                    goodLineType.put(Line, counter);
                }

            }

        }
    }

    public int getDifficulty() {
        return 0;
    }

    protected Box[][] getBoxes() {
        Box[][] boxes=new Box[getGame().getHeight()][getGame().getWeigh()];
        boolean[][] verticalLines=getVerticalLines();
        boolean[][] horizontalLines=getHorizontalLines();
            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 5; j++) {
                    boxes[i][j] = new Box(verticalLines[i][j], horizontalLines[i][j],
                            verticalLines[i][j + 1], horizontalLines[i + 1][j]);
                }
            }
        return boxes;
    }

    protected boolean[][] getHorizontalLines() {
        return getGame().getHorizontalLines();
    }

    protected boolean[][] getVerticalLines() {
        return getGame().getVerticalLines();
    }

    private static class Box {
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

    class VBoard {
        boolean[][] horizontal;
        boolean[][] vertical;
        boolean[][] occupied;
        boolean keepgoing;
        private Box[][] box;

        VBoard(boolean[][] h, boolean[][] v) {
            horizontal = new boolean[6][5];
            vertical = new boolean[5][6];
            occupied = new boolean[5][5];
            box = new Box[5][5];
            for (int i = 0; i < 6; i++) {
                for (int j = 0; j < 5; j++) {
                    horizontal[i][j] = h[i][j];
                    vertical[j][i] = v[j][i];
                }
            }

        }

        void ini() {
            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 5; j++) {
                    box[i][j] = new Box(vertical[i][j], horizontal[i][j],
                            vertical[i][j + 1], horizontal[i + 1][j]);
                    if (box[i][j].occupied)
                        occupied[i][j] = true;
                }
            }
        }

        void add(Line Line) {
            Direction type = Line.direction();
            int a = Line.row();
            int b = Line.column();
            keepgoing = false;

            switch (type) {
                case HORIZONTAL:
                    if (!horizontal[a][b]) {
                        horizontal[a][b] = true;
                        if (a == 0) {
                            if (horizontal[a + 1][b] && vertical[a][b]
                                    && vertical[a][b + 1]) {
                                occupied[a][b] = true;
                                keepgoing = true;
                            }
                        } else if (a == 5) {
                            if (horizontal[a - 1][b] && vertical[a - 1][b]
                                    && vertical[a - 1][b + 1]) {
                                occupied[a - 1][b] = true;
                                keepgoing = true;
                            }
                        } else {
                            if (horizontal[a + 1][b] && vertical[a][b]
                                    && vertical[a][b + 1]) {
                                occupied[a][b] = true;
                                keepgoing = true;
                            }
                            if (horizontal[a - 1][b] && vertical[a - 1][b]
                                    && vertical[a - 1][b + 1]) {
                                occupied[a - 1][b] = true;
                                keepgoing = true;
                            }
                        }
                    } else {
                        keepgoing = true;
                    }
                    break;
                case VERTICAL:
                    if (!vertical[a][b]) {
                        vertical[a][b] = true;
                        if (b == 0) {
                            if (vertical[a][b + 1] && horizontal[a][b]
                                    && horizontal[a + 1][b]) {
                                occupied[a][b] = true;
                                keepgoing = true;
                            }
                        } else if (b == 5) {
                            if (vertical[a][b - 1] && horizontal[a][b - 1]
                                    && horizontal[a + 1][b - 1]) {
                                occupied[a][b - 1] = true;
                                keepgoing = true;
                            }
                        } else {
                            if (vertical[a][b + 1] && horizontal[a][b]
                                    && horizontal[a + 1][b]) {
                                occupied[a][b] = true;
                                keepgoing = true;
                            }
                            if (vertical[a][b - 1] && horizontal[a][b - 1]
                                    && horizontal[a + 1][b - 1]) {
                                occupied[a][b - 1] = true;
                                keepgoing = true;
                            }
                        }
                    } else {
                        keepgoing = true;
                    }
                    break;
            }
        }

        int getOccupiedNumber() {
            int counter = 0;
            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 5; j++) {
                    if (occupied[i][j])
                        counter++;
                }
            }
            return counter;
        }

        int getBadLineValue(Line Line) {
            int start, end;
            if (Line.direction() == Direction.HORIZONTAL)
                this.horizontal[Line.row()][Line.column()] = true;
            else
                this.vertical[Line.row()][Line.column()] = true;
            AIPlayer ai = new AIPlayer(getGame());
            ini();
            start = this.getOccupiedNumber();
            keepgoing = true;
            while (keepgoing) {
                add(ai.Line(this));
                if (this.getOccupiedNumber() == 25)
                    break;
            }
            end = this.getOccupiedNumber();
            return (end - start);
        }

        int getGoodLineValue(Line Line) {
            int start, end;
            if (Line.direction() == Direction.HORIZONTAL)
                this.horizontal[Line.row()][Line.column()] = true;
            else
                this.vertical[Line.row()][Line.column()] = true;
            AIPlayer ai = new AIPlayer(getGame());
            ini();
            start = this.getOccupiedNumber();
            keepgoing = true;
            while (keepgoing) {
                add(ai.Line(this));
                if (this.getOccupiedNumber() == 25)
                    break;
            }
            end = this.getOccupiedNumber() + 1;
            return (end - start);
        }

        int getCanWinValue(Line Line) {
            ini();
            int ocpdAlready = getOccupiedNumber();
            if (Line.direction() == Direction.HORIZONTAL)
                this.horizontal[Line.row()][Line.column()] = true;
            else
                this.vertical[Line.row()][Line.column()] = true;
            int He = 0;
            int Me = 0;
            ini();
            Me = getOccupiedNumber() - ocpdAlready;
            AIPlayer ai = new AIPlayer(getGame());
            int playerNow;
            if (Me == 1)
                playerNow = 0;
            else
                playerNow = 1;

            while (this.getOccupiedNumber() != 25) {
                if (playerNow == 0) {
                    int temp = getOccupiedNumber();
                    keepgoing = true;
                    while (keepgoing) {
                        add(ai.Line(this));
                        if (this.getOccupiedNumber() == 25)
                            break;
                    }
                    temp = getOccupiedNumber() - temp;
                    Me = Me + temp;
                    playerNow = 1;
                } else {
                    int temp = getOccupiedNumber();
                    keepgoing = true;
                    while (keepgoing) {
                        add(ai.Line(this));
                        if (this.getOccupiedNumber() == 25)
                            break;
                    }
                    temp = getOccupiedNumber() - temp;
                    He = He + temp;
                    playerNow = 0;
                }
            }
            return Me - He;
        }
    }

}
