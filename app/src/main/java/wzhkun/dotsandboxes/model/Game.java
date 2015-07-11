package wzhkun.dotsandboxes.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import wzhkun.dotsandboxes.Util;

public class Game extends Observable {
    private Player[] players;
    private int playerNowIndex;
    private int weigh;
    private int height;
    private Player[][] occupied;
    private boolean[][] horizontalLines;
    private boolean[][] verticalLines;
    private Line latestLine;

    public Game(int weigh, int height, Player firstMover, Player... players) {
        assertGameBoardSizeRight(weigh, height);
        assertPlayersNotNull(players);
        assertPlayerCountRight(players);

        this.weigh = weigh;
        this.height = height;
        this.players = players;

        occupied = new Player[height][weigh];
        horizontalLines = new boolean[height + 1][weigh];
        verticalLines = new boolean[height][weigh + 1];

        addPlayersToGame(players);
        initFirstMover(firstMover, players);
    }

    public boolean[][] getHorizontalLines() {
        return Util.deepClone(horizontalLines);
    }

    public boolean[][] getVerticalLines() {
        return Util.deepClone(verticalLines);
    }

    private void assertPlayerCountRight(Player[] players) {
        if (players.length == 0)
            throw new IllegalArgumentException("No Player");
    }

    private void assertGameBoardSizeRight(float weigh, float height) {
        if (weigh < 1 || height < 1)
            throw new IllegalArgumentException("Size Too Small");
    }

    public Player[] getPlayers() {
        return players;
    }

    public int getWeigh() {
        return weigh;
    }

    public int getHeight() {
        return height;
    }

    public Line getLatestLine() {
        return latestLine;
    }

    private void assertPlayersNotNull(Player[] players) {
        for (Player player : players) {
            if (player == null) {
                throw new IllegalArgumentException("Player Is Null");
            }
        }
    }

    private void initFirstMover(Player firstMover, Player[] players) {
        for (int i = 0; i < players.length; i++) {
            if (players[i] == firstMover) {
                playerNowIndex = i;
            }
        }
    }

    private void addPlayersToGame(Player[] players) {
        for (Player player : players) {
            player.addToGame(this);
        }
    }

    public void start() {
        while (!isGameFinished()) {
            addMove(playerNow().move());
            setChanged();
            notifyObservers();
        }
    }

    public void addMove(Line move) {
        if (isLineOccupied(move)) {
            return;
        }
        boolean newBoxOccupied = tryToOccupyBox(move);
        setLineOccupied(move);
        latestLine = move;

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

    public Player getBoxOccupier(int row, int column) {
        return occupied[row][column];
    }

    public int getPlayerOccupyingBoxCount(Player player) {
        int count = 0;
        for (int i = 0; i < occupied.length; i++) {
            for (int j = 0; j < occupied[0].length; j++) {
                if (getBoxOccupier(i, j) == player)
                    count++;
            }
        }
        return count;
    }

    private boolean tryToOccupyBox(Line move) {
        boolean rightOccupied = tryToOccupyRightBox(move);
        boolean underOccupied = tryToOccupyUnderBox(move);
        boolean upperOccupied = tryToOccupyUpperBox(move);
        boolean leftOccupied = tryToOccupyLeftBox(move);
        return leftOccupied || rightOccupied || upperOccupied || underOccupied;
    }

    private void setLineOccupied(Line line) {
        switch (line.direction()) {
            case HORIZONTAL:
                horizontalLines[line.row()][line.column()] = true;
                break;
            case VERTICAL:
                verticalLines[line.row()][line.column()] = true;
                break;
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
        if (move.direction() != Direction.HORIZONTAL || move.row() >= (height))
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
        if (move.direction() != Direction.VERTICAL || move.column() >= (weigh))
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
        for (Player[] anOccupied : occupied) {
            for (int j = 0; j < occupied[0].length; j++) {
                if (anOccupied[j] == null)
                    return false;
            }
        }
        return true;
    }

    public Player[] getWinners() {
        if (!isGameFinished()) {
            return null;
        }

        int[] playersOccupyingBoxCount = new int[players.length];
        for (int i = 0; i < players.length; i++) {
            playersOccupyingBoxCount[i] = getPlayerOccupyingBoxCount(players[i]);
        }

        int maxOccupyingCount = 0;
        for (int thisPlayerOccupyingBoxCount : playersOccupyingBoxCount) {
            maxOccupyingCount = Math.max(maxOccupyingCount, thisPlayerOccupyingBoxCount);
        }

        List<Player> winners = new ArrayList<>();
        for (int i = 0; i < players.length; i++) {
            if (playersOccupyingBoxCount[i] == maxOccupyingCount)
                winners.add(players[i]);
        }

        return winners.toArray(new Player[0]);
    }

}
