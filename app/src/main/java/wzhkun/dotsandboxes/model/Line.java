package wzhkun.dotsandboxes.model;

public class Line {
    private final Direction direction;
    private final int row;
    private final int column;

    public Line(Direction direction, int row, int column) {
        this.direction = direction;
        this.row = row;
        this.column = column;
    }

    public Direction direction() {
        return direction;
    }

    public int row() {
        return row;
    }

    public int column() {
        return column;
    }

    @Override
    public String toString() {
        return "direction:"+direction().toString()+"row:"+row+"column"+column;
    }
}
