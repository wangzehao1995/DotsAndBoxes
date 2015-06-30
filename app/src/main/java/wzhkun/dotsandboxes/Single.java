package wzhkun.dotsandboxes;

import java.io.DataInputStream;
import java.io.FileInputStream;

import wzhkun.dotsandboxes.R;

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

public class Single extends Activity {
	public static int mover;
	public static int difficuty = 0;
	private int occupiedBoxes;
	private int winner = 0;
	private Intent mainActivity;
	private Single myself;
	
	boolean music,touch;
	SoundPool soundpool;
	MediaPlayer player;

	private SBoard board;
	TextView player1name, player2name, player1state, player2state,
			player1occupying, player2occupying;
	ImageView a, p1, p2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_single);
		
		player=MediaPlayer.create(this, R.raw.home);
		soundpool=new SoundPool(5,AudioManager.STREAM_MUSIC,0 );
		soundpool.load(this, R.raw.ben, BIND_ADJUST_WITH_ACTIVITY);
			try {
				FileInputStream fis = openFileInput("sound");
				DataInputStream dis = new DataInputStream(fis);
				music = dis.readBoolean();
				touch = dis.readBoolean();
				dis.close();
			} catch (Exception e) {
				music = true;
				touch = true;
			}
			if (music) {
				player.start();
			}
			
		myself = this;
		mainActivity = new Intent(this, MainActivity.class);
		board = (SBoard) findViewById(R.id.sview1);
		player1name = (TextView) findViewById(R.id.splayer1name);
		player2name = (TextView) findViewById(R.id.splayer2name);
		player1state = (TextView) findViewById(R.id.splayer1state);
		player2state = (TextView) findViewById(R.id.splayer2state);
		player1occupying = (TextView) findViewById(R.id.splayer1occupying);
		player2occupying = (TextView) findViewById(R.id.splayer2occupying);
		a = (ImageView) findViewById(R.id.simageView3);
		p1 = (ImageView) findViewById(R.id.simageView1);
		p2 = (ImageView) findViewById(R.id.simageView2);

		new AlertDialog.Builder(this)
				.setTitle("Dots And Boxes")
				.setMessage("Please choose one player to move first")
				.setPositiveButton(R.string.Player1,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								// TODO Auto-generated method stub
								mover = 1;
								new AlertDialog.Builder(myself)
										.setTitle("Dots And Boxes")
										.setMessage(
												"Please choose the difficuty")
										.setPositiveButton(
												R.string.Normal,
												new DialogInterface.OnClickListener() {
													public void onClick(
															DialogInterface dialog,
															int which) {
														// TODO Auto-generated
														// method stub
														difficuty = 1;
														board.startGame(myself);
													}
												})
										.setNeutralButton(
												R.string.Hard,
												new DialogInterface.OnClickListener() {
													public void onClick(
															DialogInterface dialog,
															int which) {
														// TODO Auto-generated
														// method stub
														difficuty = 2;
														board.startGame(myself);
													}
												})
										.setNegativeButton(
												R.string.Ultra,
												new DialogInterface.OnClickListener() {
													public void onClick(
															DialogInterface dialog,
															int which) {
														// TODO Auto-generated
														// method stub
														difficuty = 3;
														board.startGame(myself);
													}
												}).show();
							}
						})
				.setNegativeButton(R.string.Computer,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								// TODO Auto-generated method stub
								mover = 2;
								new AlertDialog.Builder(myself)
										.setTitle("Dots And Boxes")
										.setMessage(
												"Please choose the difficuty")
										.setPositiveButton(
												R.string.Normal,
												new DialogInterface.OnClickListener() {
													public void onClick(
															DialogInterface dialog,
															int which) {
														// TODO Auto-generated
														// method stub
														difficuty = 1;
														board.startGame(myself);
													}
												})
										.setNeutralButton(
												R.string.Hard,
												new DialogInterface.OnClickListener() {
													public void onClick(
															DialogInterface dialog,
															int which) {
														// TODO Auto-generated
														// method stub
														difficuty = 2;
														board.startGame(myself);
													}
												})
										.setNegativeButton(
												R.string.Ultra,
												new DialogInterface.OnClickListener() {
													public void onClick(
															DialogInterface dialog,
															int which) {
														// TODO Auto-generated
														// method stub
														difficuty = 3;
														board.startGame(myself);
													}
												}).show();
							}
						}).show();

		ImageButton returnn = (ImageButton) findViewById(R.id.simageButton5);
		returnn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				new AlertDialog.Builder(myself)
						.setTitle("Dots And Boxes")
						.setMessage(
								"Are you sure you want to quit?\nYou will lose your existing board.")
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

				// TODO Auto-generated method stub

			}

		});
	
		ImageButton refresh = (ImageButton) findViewById(R.id.imageButtonRefresh2);
		refresh.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				new AlertDialog.Builder(myself)
				.setTitle("Dots And Boxes")
				.setMessage(
						"Are you sure you want to clean the board?\nYou will lose your existing board.")
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
				// TODO Auto-generated method stub
				
			}
			
		});
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			new AlertDialog.Builder(this)
					.setTitle("Dots And Boxes")
					.setMessage(
							"Are you sure you want to quit?\nYou will lose your existing board.")
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

		return false;

	}

	public void setDifficuty(int difficuty) {
		Single.difficuty = difficuty;
	}

	public void start(int difficuty) {
		board.setVisibility(View.VISIBLE);
		player1state.setVisibility(View.VISIBLE);
		player2state.setVisibility(View.VISIBLE);
		player1occupying.setVisibility(View.VISIBLE);
		player1occupying.setVisibility(View.VISIBLE);
		player1name.setVisibility(View.VISIBLE);
		player2name.setVisibility(View.VISIBLE);
		p1.setVisibility(View.VISIBLE);
		p2.setVisibility(View.VISIBLE);

	}

	public void checkWinner() {

		int counter1 = 0;
		int counter2 = 0;
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				if (SBoard.occupied[i][j] == 1)
					counter1++;
				if (SBoard.occupied[i][j] == 2)
					counter2++;
			}

		}
		player1occupying.setText("Occupying " + Integer.toString(counter1));
		player2occupying.setText("Occupying " + Integer.toString(counter2));
		occupiedBoxes = counter1 + counter2;
		if (occupiedBoxes == 25) {
			if (counter1 > counter2)
				winner = 1;
			else
				winner = 2;
		} else {
			winner = 0;
		}
		if (winner != 0) {
			String mes;
			if (winner == 1) {
				mes = "You Win";
			} else {
				mes = "Computer Win";
			}
			new AlertDialog.Builder(this)
					.setTitle("Game Finished")
					.setMessage(mes)
					.setPositiveButton("Confirmed",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int which) {

									
									finish();
								}
							})
							.setNegativeButton("Again",new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int which) {
									recreate();
								}
							})
					.show();

		}

	}

	public void updateState() {
		if (board.playernow == 1) {
			player1state.setText("Thinking");
			player2state.setText("Waiting");
			a.setImageResource(R.mipmap.a1);
		} else {
			player2state.setText("Thinking");
			player1state.setText("Waiting");
			a.setImageResource(R.mipmap.a2);
			
		}
	}
	
	void touch(){
		if(touch)
		soundpool.play(1, (float)0.8, (float)0.8, 0, 0, 1);
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		player.stop();
	}


}
