package wzhkun.dotsandboxes.model;

import java.util.ArrayList;
import java.util.Observable;

/**
 * Created by wangzehao on 2015/7/6.
 */
public class Game extends Observable {
    private Player[] players;
    private int playerNowIndex;
    private int weigh;
    private int height;
    private Player[][] occupied = new Player[height][weigh];
    private boolean[][] horizontalLines = new boolean[height + 1][weigh];
    private boolean[][] verticalLines = new boolean[height][weigh + 1];

    public Game(Player firstMover, Player... players) {
        this(5, 5, firstMover, players);
    }

    public Game(int weigh, int height, Player firstMover, Player... players) {
        this.weigh = weigh;
        this.height = height;
        this.players = players;

        this.playerNowIndex = 0;
        for (int i = 0; i < players.length; i++) {
            if (players[i] == null) {
                throw new java.lang.IllegalArgumentException("Player Is Null");
            }
            if (players[i] == firstMover) {
                playerNowIndex = i;
                break;
            }
        }

        if (players.length == 0)
            throw new java.lang.IllegalArgumentException("No Player");
        if (weigh < 1 || height < 1)
            throw new java.lang.IllegalArgumentException("Size Too Small");
    }

    public void start() {
        while (!isGameFinished()) {
            addMove(playerNow().move());
            notifyObservers();
        }
        notifyObservers(getWinners());
    }

    public void addMove(Line move) {
        if (isLineOccupied(move)) {
            return;
        }
        boolean newBoxOccupied = tryToOccupyBox(move);
        setLineOccupied(move);

        if (!newBoxOccupied)
            toNextPlayer();
    }

    public Player playerNow() {
        return players[playerNowIndex];
    }

    public boolean isLineOccupied(Direction direction, int row, int column) {
        return isLineOccupied(new Line(direction, row, column));
    }

    public boolean isLineOccupied(Line line) {
        switch (line.direction()) {
            case HORIZONTAL:
                return horizontalLines[line.row()][line.column()];
            case VERTICAL:
                return verticalLines[line.row()][line.column()];
        }
        throw new java.lang.IllegalArgumentException(line.direction().toString());
    }

    public int getPlayerOccupyingBoxCount(Player player) {
        return 0;
    }

    private boolean tryToOccupyBox(Line move) {
        return tryToOccupyLeftBox(move)
                || tryToOccupyRightBox(move)
                || tryToOccupyUnderBox(move)
                || tryToOccupyUpperBox(move);
    }

    private void setLineOccupied(Line line) {
        switch (line.direction()) {
            case HORIZONTAL:
                horizontalLines[line.row()][line.column()] = true;
            case VERTICAL:
                verticalLines[line.row()][line.column()] = true;
        }
    }

    private void setBoxOccupied(int row, int column, Player player) {
        occupied[row][column] = player;
    }

    private boolean tryToOccupyUpperBox(Line move) {
        if (move.direction() != Direction.HORIZONTAL || move.row() <= 0)
            return false;
        if (isLineOccupied(Direction.HORIZONTAL, move.row() - 1, move.column())
                && isLineOccupied(Direction.VERTICAL, move.row() - 1, move.column())
                && isLineOccupied(Direction.VERTICAL, move.row() - 1, move.column() + 1)) {
            setBoxOccupied(move.row() - 1, move.column(), playerNow());
            return true;
        } else {
            return false;
        }
    }

    private boolean tryToOccupyUnderBox(Line move) {
        if (move.direction() != Direction.HORIZONTAL || move.row() >= (height + 1))
            return false;
        if (isLineOccupied(Direction.HORIZONTAL, move.row() + 1, move.column())
                && isLineOccupied(Direction.VERTICAL, move.row(), move.column())
                && isLineOccupied(Direction.VERTICAL, move.row(), move.column() + 1)) {
            setBoxOccupied(move.row(), move.column(), playerNow());
            return true;
        } else {
            return false;
        }
    }

    private boolean tryToOccupyLeftBox(Line move) {
        if (move.direction() != Direction.VERTICAL || move.column() <= 0)
            return false;
        if (isLineOccupied(Direction.VERTICAL, move.row(), move.column() - 1)
                && isLineOccupied(Direction.HORIZONTAL, move.row(), move.column() - 1)
                && isLineOccupied(Direction.HORIZONTAL, move.row() + 1, move.column() - 1)) {
            setBoxOccupied(move.row(), move.column() - 1, playerNow());
            return true;
        } else {
            return false;
        }
    }

    private boolean tryToOccupyRightBox(Line move) {
        if (move.direction() != Direction.VERTICAL || move.column() >= (weigh + 1))
            return false;
        if (isLineOccupied(Direction.VERTICAL, move.row(), move.column() + 1)
                && isLineOccupied(Direction.HORIZONTAL, move.row(), move.column())
                && isLineOccupied(Direction.HORIZONTAL, move.row() + 1, move.column())) {
            setBoxOccupied(move.row(), move.column(), playerNow());
            return true;
        } else {
            return false;
        }
    }

    private void toNextPlayer() {
        playerNowIndex = (playerNowIndex + 1) % players.length;
    }

    private boolean isGameFinished() {
        for (int i = 0; i < occupied.length; i++) {
            for (int j = 0; j < occupied[0].length; j++) {
                if (occupied[i][j] == null)
                    return false;
            }
        }
        return true;
    }

    private Player[] getWinners() {
        int playerCount = players.length;
        int[] occupyingBoxCount = new int[playerCount];
        int maxOccupyingBoxCount=0;
        for (int player_i = 0; player_i < playerCount; player_i++) {
            occupyingBoxCount[player_i]=getPlayerOccupyingBoxCount(players[player_i]);
            if(occupyingBoxCount[player_i]>maxOccupyingBoxCount)
                maxOccupyingBoxCount=occupyingBoxCount[player_i];
        }
        ArrayList<Player> winners=new ArrayList<>();
        for (int player_i = 0; player_i < playerCount; player_i++) {
            if(occupyingBoxCount[player_i]==maxOccupyingBoxCount){
                winners.add(players[player_i]);
            }
        }
        return winners.toArray(new Player[0]);
    }
}
