package wzhkun.dotsandboxes.view;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.util.Map;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import wzhkun.dotsandboxes.R;
import wzhkun.dotsandboxes.model.HumanPlayer;
import wzhkun.dotsandboxes.model.Player;

public abstract class TwoPlayerActivity extends Activity implements PlayersStateView {

    protected GameView gameView;
    protected TextView player1state, player2state, player1occupying, player2occupying;
    ImageView playerNowPointer;
    boolean music, touchSoundOn;
    SoundPool soundpool;
    MediaPlayer mediaPlayer;
    Player[] players;
    Integer[] playersOccupying = new Integer[]{0, 0};
    Player playerNow;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        mediaPlayer = MediaPlayer.create(this, R.raw.home);
        soundpool = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);
        soundpool.load(this, R.raw.ben, BIND_ADJUST_WITH_ACTIVITY);
        try {
            FileInputStream fis = openFileInput("sound");
            DataInputStream dis = new DataInputStream(fis);
            music = dis.readBoolean();
            touchSoundOn = dis.readBoolean();
            dis.close();
        } catch (Exception e) {
            music = true;
            touchSoundOn = true;
        }
        if (music) {
            mediaPlayer.start();
        }

        ImageButton returnButton = (ImageButton) findViewById(R.id.returnButton);
        returnButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                showExitDialog();
            }
        });

        ImageButton refresh = (ImageButton) findViewById(R.id.refreshButton);
        refresh.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                showRefreshDialog();
            }
        });

        initPlayersDisplayView();

        gameView = (GameView) findViewById(R.id.gameView);
        gameView.setPlayersState(this);

        new Thread() {
            @Override
            public void run() {
                startGame();
            }
        }.start();

    }

    protected void initPlayersDisplayView() {
        player1state = (TextView) findViewById(R.id.player1state);
        player2state = (TextView) findViewById(R.id.player2state);
        player1occupying = (TextView) findViewById(R.id.player1occupying);
        player2occupying = (TextView) findViewById(R.id.player2occupying);
        playerNowPointer = (ImageView
                ) findViewById(R.id.playerNowPointer);
    }

    public void updateState() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (playerNow == players[0]) {
                    player1state.setText("Thinking");
                    player2state.setText("Waiting");
                    playerNowPointer.setImageResource(R.mipmap.a1);
                } else {
                    player2state.setText("Thinking");
                    player1state.setText("Waiting");
                    playerNowPointer.setImageResource(R.mipmap.a2);
                }
                player1occupying.setText("Occupying " + playersOccupying[0]);
                player2occupying.setText("Occupying " + playersOccupying[1]);
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, @NonNull KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            showExitDialog();
        }
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mediaPlayer.stop();
    }

    private void showExitDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Dots And Boxes")
                .setMessage(
                        "Are you sure you want to quit?\nYou will lose your existing game.")
                .setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int which) {

                                finish();
                            }
                        })
                .setNegativeButton("No",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int which) {
                            }
                        }).show();
    }

    private void showRefreshDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Dots And Boxes")
                .setMessage(
                        "Are you sure you want to clean the board?\nYou will lose your existing game.")
                .setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                recreate();
                            }
                        })
                .setNegativeButton("No",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int which) {
                            }
                        }).show();
    }

    protected abstract Player[] initPlayers() ;

    private int getFirstMover() {
        final int[] firstMoverIndex = new int[]{-1};
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new AlertDialog.Builder(TwoPlayerActivity.this)
                        .setTitle("Dots And Boxes")
                        .setMessage("Please choose one player to move first")
                        .setPositiveButton(players[0].getName(),
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,
                                                        int which) {
                                        synchronized (firstMoverIndex) {
                                            firstMoverIndex[0] = 0;
                                            firstMoverIndex.notify();
                                        }
                                    }
                                })
                        .setNegativeButton(players[1].getName(),
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,
                                                        int which) {
                                        synchronized (firstMoverIndex) {
                                            firstMoverIndex[0] = 1;
                                            firstMoverIndex.notify();
                                        }
                                    }
                                }).show();
            }
        });
        while (firstMoverIndex[0] == -1) {
            synchronized (firstMoverIndex) {
                try {
                    firstMoverIndex.wait();
                } catch (InterruptedException ignored) {
                }
            }
        }
        return firstMoverIndex[0];
    }

    private void startGame() {
        players = initPlayers();
        int indexOfPlayerFirstToMove = getFirstMover();
        playerNow = players[indexOfPlayerFirstToMove];
        gameView.startGame(playerNow, players);
        updateState();
    }

    @Override
    public void setPlayerNow(Player player) {
        playerNow = player;
        updateState();
    }

    @Override
    public void setPlayerOccupyingBoxesCount(Map<Player, Integer> player_occupyingBoxesCount_map) {
        playersOccupying[0] = (player_occupyingBoxesCount_map.get(players[0]));
        playersOccupying[1] = (player_occupyingBoxesCount_map.get(players[1]));
        updateState();
    }

    @Override
    public void playerTouched() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (touchSoundOn)
                    soundpool.play(1, (float) 0.8, (float) 0.8, 0, 0, 1);
            }
        });
    }

    @Override
    public void setWinner(final Player[] winner) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                String winnerNames = "";
                for (Player player : winner) {
                    winnerNames += player.getName();
                    winnerNames += " ";
                }
                new AlertDialog.Builder(TwoPlayerActivity.this)

                        .setTitle("Dots And Boxes")
                        .setMessage(winnerNames + "Win!")
                        .setPositiveButton("Confirm",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,
                                                        int which) {
                                        recreate();
                                    }
                                }).show();
            }
        });

    }
}
