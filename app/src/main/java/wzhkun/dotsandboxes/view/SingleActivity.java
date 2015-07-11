package wzhkun.dotsandboxes.view;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.app.AlertDialog;
import android.content.DialogInterface;

import wzhkun.dotsandboxes.R;
import wzhkun.dotsandboxes.model.AIPlayer;
import wzhkun.dotsandboxes.model.HumanPlayer;
import wzhkun.dotsandboxes.model.Player;

public class SingleActivity extends TwoPlayerActivity {

    @Override
    protected void initPlayersDisplayView() {
        super.initPlayersDisplayView();
        TextView player2name = (TextView) findViewById(R.id.player2name);
        player2name.setText("Computer");
        ImageView player2picture = (ImageView) findViewById(R.id.player2picture);
        player2picture.setImageResource(R.mipmap.computer);
    }

    @Override
    protected Player[] initPlayers() {
        AIPlayer computer = new AIPlayer("Computer",getAIDifficulty());
        return new Player[]{new HumanPlayer("Player1"), computer};
    }

    private int getAIDifficulty() {
        final int[] aiDifficulty=new int[]{-1};
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new AlertDialog.Builder(SingleActivity.this)
                        .setTitle("Dots And Boxes")
                        .setMessage("Please choose difficulty")
                        .setPositiveButton("Normal",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,
                                                        int which) {
                                        synchronized (aiDifficulty) {
                                            aiDifficulty[0] = 1;
                                            aiDifficulty.notify();
                                        }
                                    }
                                })
                        .setNeutralButton("Hard", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                synchronized (aiDifficulty) {
                                    aiDifficulty[0] = 2;
                                    aiDifficulty.notify();
                                }
                            }
                        })
                        .setNegativeButton("Ultra",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,
                                                        int which) {
                                        synchronized (aiDifficulty) {
                                            aiDifficulty[0] = 3;
                                            aiDifficulty.notify();
                                        }
                                    }
                                }).show();
            }
        });
        while(aiDifficulty[0]==-1){
            synchronized (aiDifficulty){
                try {
                    aiDifficulty.wait();
                } catch (InterruptedException ignored) {
                }
            }
        }
        return aiDifficulty[0];
    }

}
