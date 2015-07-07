package wzhkun.dotsandboxes.view;

import java.io.DataInputStream;
import java.io.FileInputStream;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;

import wzhkun.dotsandboxes.R;
import wzhkun.dotsandboxes.model.AIPlayer;
import wzhkun.dotsandboxes.model.HumanPlayer;
import wzhkun.dotsandboxes.model.MyActivity;
import wzhkun.dotsandboxes.model.Player;

public class SingleActivity extends DoubleActivity {
    @Override
    protected Player[] initPlayers() {
        return new Player[]{new HumanPlayer("Player1"),new AIPlayer("Computer",1)};
    }
}
