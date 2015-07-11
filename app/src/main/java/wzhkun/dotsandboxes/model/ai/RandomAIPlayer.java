package wzhkun.dotsandboxes.model.ai;

import java.util.ArrayList;

import wzhkun.dotsandboxes.model.Direction;
import wzhkun.dotsandboxes.model.Game;
import wzhkun.dotsandboxes.model.Line;
import wzhkun.dotsandboxes.model.Player;

public class RandomAIPlayer extends Player{
    protected ArrayList<Line> safeLines;
    protected ArrayList<Line> goodLines;
    protected ArrayList<Line> badLines;

    RandomAIPlayer(Game game) {
        this("");
        this.addToGame(game);
    }

    public RandomAIPlayer(String name) {
        super(name);

        safeLines = new ArrayList<>();
        goodLines = new ArrayList<>();
        badLines = new ArrayList<>();

    }

    protected Line nextMove() {
        if (goodLines.size() != 0)
            return goodLines.get((int) ((goodLines.size()) * Math.random()));
        if (safeLines.size() != 0)
            return safeLines.get((int) ((safeLines.size()) * Math.random()));

        ArrayList<Line> unOccupiedLines = new ArrayList<>();
        for (int i = 0; i < getGame().getHeight(); i++) {
            for (int j = 0; j < getGame().getWidth()+1; j++) {
                if (!getGame().isLineOccupied(Direction.VERTICAL, i, j))
                    unOccupiedLines.add(new Line(Direction.VERTICAL, i, j));
            }
        }
        for (int i = 0; i < getGame().getHeight()+1; i++) {
            for (int j = 0; j < getGame().getWidth(); j++) {
                if (!getGame().isLineOccupied(Direction.HORIZONTAL, i, j))
                    unOccupiedLines.add(new Line(Direction.HORIZONTAL, i, j));
            }
        }
        if(unOccupiedLines.size()==0){
            System.out.println("hw");
            initialise();
        }
        return unOccupiedLines.get((int) ((unOccupiedLines.size()) * Math.random()));
    }

    private void initialise() {
        initialiseSafeLine();
        initialiseGoodLine();
        initialiseBadLine();
    }

    public Line move() {
        initialise();
        return nextMove();
    }

    private void initialiseGoodLine() {
        int counter;
        boolean t1 = false;
        boolean t2 = false;
        goodLines.clear();
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 5; j++) {
                if (!getHorizontalLine(i, j)) {
                    if (i == 0) {
                        counter = 0;
                        if (getBox(i, j).left)
                            counter++;
                        if (getBox(i, j).bottom)
                            counter++;
                        if (getBox(i, j).right)
                            counter++;
                        if (getBox(i, j).occupiedLineCount() == 3)
                            goodLines.add(new Line(Direction.HORIZONTAL, i, j));
                    } else if (i == 5) {

                        counter = 0;
                        if (getBox(i - 1, j).left)
                            counter++;
                        if (getBox(i - 1, j).top)
                            counter++;
                        if (getBox(i - 1, j).right)
                            counter++;
                        if (counter == 3)
                            goodLines.add(new Line(Direction.HORIZONTAL, i, j));
                    } else {
                        counter = 0;
                        if (getBox(i, j).left)
                            counter++;
                        if (getBox(i, j).bottom)
                            counter++;
                        if (getBox(i, j).right)
                            counter++;
                        if (counter == 3)
                            t1 = true;
                        counter = 0;
                        if (getBox(i - 1, j).left)
                            counter++;
                        if (getBox(i - 1, j).top)
                            counter++;
                        if (getBox(i - 1, j).right)
                            counter++;
                        if (counter == 3)
                            t2 = true;
                        if (t1 || t2)
                            goodLines.add(new Line(Direction.HORIZONTAL, i, j));
                        t1 = false;
                        t2 = false;
                    }
                }
                if (!getVerticalLine(j, i)) {
                    if (i == 0) {
                        counter = 0;
                        if (getBox(j, i).right)
                            counter++;
                        if (getBox(j, i).bottom)
                            counter++;
                        if (getBox(j, i).top)
                            counter++;
                        if (counter == 3)
                            goodLines.add(new Line(Direction.VERTICAL, j, i));
                    } else if (i == 5) {
                        counter = 0;
                        if (getBox(j, i - 1).left)
                            counter++;
                        if (getBox(j, i - 1).top)
                            counter++;
                        if (getBox(j, i - 1).bottom)
                            counter++;
                        if (counter == 3)
                            goodLines.add(new Line(Direction.VERTICAL, j, i));
                    } else {
                        counter = 0;
                        if (getBox(j, i).right)
                            counter++;
                        if (getBox(j, i).bottom)
                            counter++;
                        if (getBox(j, i).top)
                            counter++;
                        if (counter == 3)
                            t1 = true;
                        counter = 0;
                        if (getBox(j, i - 1).left)
                            counter++;
                        if (getBox(j, i - 1).top)
                            counter++;
                        if (getBox(j, i - 1).bottom)
                            counter++;
                        if (counter == 3)
                            t2 = true;
                        if (t1 || t2)
                            goodLines.add(new Line(Direction.VERTICAL, j, i));
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
        badLines.clear();
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 5; j++) {
                if (!getHorizontalLine(i, j)) {
                    if (i == 0) {
                        counter = 0;
                        if (getBox(i, j).left)
                            counter++;
                        if (getBox(i, j).bottom)
                            counter++;
                        if (getBox(i, j).right)
                            counter++;
                        if (counter == 2)
                            badLines.add(new Line(Direction.HORIZONTAL, i, j));
                    } else if (i == 5) {

                        counter = 0;
                        if (getBox(i - 1, j).left)
                            counter++;
                        if (getBox(i - 1, j).top)
                            counter++;
                        if (getBox(i - 1, j).right)
                            counter++;
                        if (counter == 2)
                            badLines.add(new Line(Direction.HORIZONTAL, i, j));
                    } else {
                        counter = 0;
                        if (getBox(i, j).left)
                            counter++;
                        if (getBox(i, j).bottom)
                            counter++;
                        if (getBox(i, j).right)
                            counter++;
                        if (counter == 2)
                            t1 = true;
                        counter = 0;
                        if (getBox(i - 1, j).left)
                            counter++;
                        if (getBox(i - 1, j).top)
                            counter++;
                        if (getBox(i - 1, j).right)
                            counter++;
                        if (counter == 2)
                            t2 = true;
                        if (t1 || t2)
                            badLines.add(new Line(Direction.HORIZONTAL, i, j));
                        t1 = false;
                        t2 = false;
                    }
                }
                if (!getVerticalLine(j, i)) {
                    if (i == 0) {
                        counter = 0;
                        if (getBox(j, i).right)
                            counter++;
                        if (getBox(j, i).bottom)
                            counter++;
                        if (getBox(j, i).top)
                            counter++;
                        if (counter == 2)
                            badLines.add(new Line(Direction.VERTICAL, j, i));
                    } else if (i == 5) {
                        counter = 0;
                        if (getBox(j, i - 1).left)
                            counter++;
                        if (getBox(j, i - 1).top)
                            counter++;
                        if (getBox(j, i - 1).bottom)
                            counter++;
                        if (counter == 2)
                            badLines.add(new Line(Direction.VERTICAL, j, i));
                    } else {
                        counter = 0;
                        if (getBox(j, i).right)
                            counter++;
                        if (getBox(j, i).bottom)
                            counter++;
                        if (getBox(j, i).top)
                            counter++;
                        if (counter == 2)
                            t1 = true;
                        counter = 0;
                        if (getBox(j, i - 1).left)
                            counter++;
                        if (getBox(j, i - 1).top)
                            counter++;
                        if (getBox(j, i - 1).bottom)
                            counter++;
                        if (counter == 2)
                            t2 = true;
                        if (t1 || t2)
                            badLines.add(new Line(Direction.VERTICAL, j, i));
                        t1 = false;
                        t2 = false;
                    }
                }
            }
        }
        for (Line a : goodLines) {
            try {
                for (Line b : badLines) {
                    if (a.direction() == b.direction() && a.row() == b.row() && a.column() == b.column()) {
                        badLines.remove(b);
                    }
                }
            } catch (Exception ignored) {
            }
        }
    }

    private void initialiseSafeLine() {
        int counter;
        boolean t1 = false;
        boolean t2 = false;
        safeLines.clear();
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 5; j++) {
                if (!getHorizontalLine(i, j)) {
                    if (i == 0) {
                        counter = 0;
                        if (getBox(i, j).left)
                            counter++;
                        if (getBox(i, j).bottom)
                            counter++;
                        if (getBox(i, j).right)
                            counter++;
                        if (counter < 2)
                            safeLines.add(new Line(Direction.HORIZONTAL, i, j));
                    } else if (i == 5) {

                        counter = 0;
                        if (getBox(i - 1, j).left)
                            counter++;
                        if (getBox(i - 1, j).top)
                            counter++;
                        if (getBox(i - 1, j).right)
                            counter++;
                        if (counter < 2)
                            safeLines.add(new Line(Direction.HORIZONTAL, i, j));
                    } else {
                        counter = 0;
                        if (getBox(i, j).left)
                            counter++;
                        if (getBox(i, j).bottom)
                            counter++;
                        if (getBox(i, j).right)
                            counter++;
                        if (counter < 2)
                            t1 = true;
                        counter = 0;
                        if (getBox(i - 1, j).left)
                            counter++;
                        if (getBox(i - 1, j).top)
                            counter++;
                        if (getBox(i - 1, j).right)
                            counter++;
                        if (counter < 2)
                            t2 = true;
                        if (t1 && t2)
                            safeLines.add(new Line(Direction.HORIZONTAL, i, j));
                        t1 = false;
                        t2 = false;
                    }
                }
                if (!getVerticalLine(j, i)) {
                    if (i == 0) {
                        counter = 0;
                        if (getBox(j, i).right)
                            counter++;
                        if (getBox(j, i).bottom)
                            counter++;
                        if (getBox(j, i).top)
                            counter++;
                        if (counter < 2)
                            safeLines.add(new Line(Direction.VERTICAL, j, i));
                    } else if (i == 5) {
                        counter = 0;
                        if (getBox(j, i - 1).left)
                            counter++;
                        if (getBox(j, i - 1).top)
                            counter++;
                        if (getBox(j, i - 1).bottom)
                            counter++;
                        if (counter < 2)
                            safeLines.add(new Line(Direction.VERTICAL, j, i));
                    } else {
                        counter = 0;
                        if (getBox(j, i).right)
                            counter++;
                        if (getBox(j, i).bottom)
                            counter++;
                        if (getBox(j, i).top)
                            counter++;
                        if (counter < 2)
                            t1 = true;
                        counter = 0;
                        if (getBox(j, i - 1).left)
                            counter++;
                        if (getBox(j, i - 1).top)
                            counter++;
                        if (getBox(j, i - 1).bottom)
                            counter++;
                        if (counter < 2)
                            t2 = true;
                        if (t1 && t2)
                            safeLines.add(new Line(Direction.VERTICAL, j, i));
                        t1 = false;
                        t2 = false;
                    }
                }
            }
        }
    }

    protected Box getBox(int row, int column) {
        return new Box(getVerticalLine(row, column), getHorizontalLine(row, column), getVerticalLine(row, column + 1), getHorizontalLine(row + 1, column));
    }

    protected boolean getHorizontalLine(int row, int column) {
        return getGame().isLineOccupied(Direction.HORIZONTAL, row, column);
    }

    protected boolean getVerticalLine(int row, int column) {
        return getGame().isLineOccupied(Direction.VERTICAL, row, column);
    }

}
