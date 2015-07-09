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

public class SingleActivity extends DoubleActivity {
    private AIPlayer computer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TextView player2name = (TextView) findViewById(R.id.player2name);
        player2name.setText("Computer");
        ImageView player2picture = (ImageView) findViewById(R.id.player2picture);
        player2picture.setImageResource(R.mipmap.computer);

        showChooseAIDifficultyDialog();
    }

    @Override
    protected Player[] initPlayers() {
        computer = new AIPlayer("Computer");
        return new Player[]{new HumanPlayer("Player1"), computer};
    }

    private void showChooseAIDifficultyDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Dots And Boxes")
                .setMessage("Please choose difficulty")
                .setPositiveButton("Normal",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                computer.setDifficulty(1);
                            }
                        })
                .setNeutralButton("Hard", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        computer.setDifficulty(2);
                    }
                })
                .setNegativeButton("Ultra",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                computer.setDifficulty(3);
                            }
                        }).show();
    }

}
