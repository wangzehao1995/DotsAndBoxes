package wzhkun.dotsandboxes.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import wzhkun.dotsandboxes.model.Direction;
import wzhkun.dotsandboxes.model.Game;
import wzhkun.dotsandboxes.model.HumanPlayer;
import wzhkun.dotsandboxes.model.Line;
import wzhkun.dotsandboxes.model.Player;

@SuppressLint("ClickableViewAccessibility")
public class GameView extends View implements Observer {
    protected static final float radius = (float) 14 / 824;
    protected static final float start = (float) 6 / 824;
    protected static final float stop = (float) 819 / 824;
    protected static final float add1 = (float) 18 / 824;
    protected static final float add2 = (float) 2 / 824;
    protected static final float add3 = (float) 14 / 824;
    protected static final float add4 = (float) 141 / 824;
    protected static final float add5 = (float) 159 / 824;
    protected static final float add6 = (float) 9 / 824;

    protected static final int[] playerColors = new int[]{0xFF6C69FF, 0x88E5004F};
    public static final int GREEY = 0xFF666666;
    protected Game game;
    protected Line move;
    protected Paint paint;
    protected int viewWidth;
    protected int viewHeight;
    protected PlayersStateView playersState;

    public GameView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        paint = new Paint();
        paint.setAntiAlias(true);
        this.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                playersState.playerTouched();
                receiveInput(event);
                return false;
            }
        });
    }

    public Game getGame() {
        return game;
    }

    public void setPlayersState(PlayersStateView playersState) {
        this.playersState = playersState;
    }

    public void startGame(Player firstMover, Player... players) {
        game = new Game(5, 5, firstMover, players);
        game.addObserver(this);
        new Thread() {
            @Override
            public void run() {
                game.start();
            }
        }.start();
        updatePlayersState();
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {

        super.onDraw(canvas);
        if (game == null)
            return;
        canvas.drawColor(0x00FFFFFF);
        viewWidth = this.getWidth();
        viewHeight = this.getHeight();
        int min = Math.min(viewWidth, viewHeight);
        float radius = GameView.radius * min;
        float start = GameView.start * min;
        float stop = GameView.stop * min;
        float add1 = GameView.add1 * min;
        float add2 = GameView.add2 * min;
        float add4 = GameView.add4 * min;
        float add5 = GameView.add5 * min;
        float add6 = GameView.add6 * min;

        //paint direction color
        paint.setColor(playerNowColor());
        float temp = add2 / 2;
        for (int i = 1; i < 6; i++) {
            canvas.drawLine(temp * i, temp * i, min - temp * (i - 1), temp * i, paint);
            canvas.drawLine(temp * i, temp * i, temp * i, min - temp * (i - 1), paint);
            canvas.drawLine(min - temp * (i - 1), temp * i, min - temp * (i - 1), min - temp * (i - 1), paint);
            canvas.drawLine(temp * i, min - temp * (i - 1), min - temp * (i - 1), min - temp * (i - 1), paint);
        }

        //paint board
        paint.setColor(0xFF777777);
        for (int i = 0; i < 6; i++) {
            canvas.drawLine(start + add5 * i, start, start + add5 * i, stop,
                    paint);
            canvas.drawLine(start + add5 * i + add1, start, start + add5 * i
                    + add1, stop, paint);
            canvas.drawLine(start, start + add5 * i, stop, start + add5 * i,
                    paint);
            canvas.drawLine(start, start + add5 * i + add1, stop, start + add5
                    * i + add1, paint);
        }

        //paint lines
        paint.setColor(0xFF000000);
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 5; j++) {
                Line horizontal = new Line(Direction.HORIZONTAL, i, j);
                if (horizontal.equals(game.getLatestLine())) {
                    paint.setColor(0xFFFF7700);
                } else if (game.isLineOccupied(horizontal)) {
                    paint.setColor(0xFF000000);
                } else {
                    paint.setColor(0xFFFFFFFF);
                }
                canvas.drawRect(start + add5 * j + add1, start + add5 * i
                        + add2, start + add5 * (j + 1), start + add5 * i + add1
                        - add2, paint);

                Line vertical = new Line(Direction.VERTICAL, j, i);
                if (vertical.equals(game.getLatestLine())) {
                    paint.setColor(0xFFFF7700);
                } else if (game.isLineOccupied(vertical)) {
                    paint.setColor(0xFF000000);
                } else {
                    paint.setColor(0xFFFFFFFF);
                }
                canvas.drawRect(start + add5 * i + add2, start + add5 * j
                        + add1, start + add5 * i + add1 - add2, start + add5
                        * (j + 1), paint);
            }
        }

        //paint boxes
        for (int i = 0; i < game.getWeigh(); i++) {
            for (int j = 0; j < game.getHeight(); j++) {
                paint.setColor(game.getBoxOccupier(j, i) == null ? Color.TRANSPARENT : playerColors[Player.indexIn(game.getBoxOccupier(j, i), game.getPlayers())]);
                canvas.drawRect(start + add5 * i + add1 + add2, start
                        + add5 * j + add1 + add2, start + add5 * i + add1
                        + add4 - add2, start + add5 * j + add1 + add4
                        - add2, paint);
            }
        }

        //paint points
        paint.setColor(GREEY);
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                canvas.drawCircle(start + add6 + j * add5 + 1, start + add6 + i
                        * add5 + 1, radius, paint);
            }
        }

        invalidate();
    }

    private void receiveInput(MotionEvent event) {
        if (event.getAction() != MotionEvent.ACTION_DOWN)
            return;
        if (!(game.playerNow() instanceof HumanPlayer)) {
            return;
        }
        float touchX = event.getX();
        float touchY = event.getY();
        int min = Math.min(viewWidth, viewHeight);
        float start = GameView.start * min;
        float add1 = GameView.add1 * min;
        float add2 = GameView.add2 * min;
        float add3 = GameView.add3 * min;
        float add5 = GameView.add5 * min;
        int d = -1, a = -1, b = -1;
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 5; j++) {
                if ((start + add5 * j + add1 - add3) <= touchX
                        && touchX <= (start + add5 * (j + 1) + add3)
                        && touchY >= start + add5 * i + add2 - add3
                        && touchY <= start + add5 * i + add1 - add2 + add3) {
                    d = 0;
                    a = i;
                    b = j;
                }
                if (start + add5 * i + add2 - add3 <= touchX
                        && touchX <= start + add5 * i + add1 - add2 + add3
                        && touchY >= start + add5 * j + add1 - add3
                        && touchY <= start + add5 * (j + 1) + add3) {
                    d = 1;
                    a = j;
                    b = i;
                }
            }
        }

        if (a != -1 && b != -1 && d != -1) {
            Direction direction;
            if (d == 0)
                direction = Direction.HORIZONTAL;
            else
                direction = Direction.VERTICAL;
            move = new Line(direction, a, b);
            ((HumanPlayer) game.playerNow()).add(move);

        }
    }

    private void updatePlayersState() {

    }

    @Override
    public void update(Observable observable, Object data) {
        playersState.setPlayerNow(game.playerNow());
        Map<Player, Integer> player_occupyingBoxCount_map = new HashMap<>();
        for (Player player : game.getPlayers()) {
            player_occupyingBoxCount_map.put(player, game.getPlayerOccupyingBoxCount(player));
        }
        playersState.setPlayerOccupyingBoxesCount(player_occupyingBoxCount_map);

        Player[] winners = game.getWinners();
        if (winners != null) {
            playersState.setWinner(winners);
        }
    }

    private int playerNowColor() {
        int playerIndex = Player.indexIn(game.playerNow(), game.getPlayers());
        return playerIndex == -1 ? Color.BLACK : playerColors[playerIndex];
    }
}
