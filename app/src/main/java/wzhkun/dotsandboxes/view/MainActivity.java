package wzhkun.dotsandboxes.view;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

import wzhkun.dotsandboxes.R;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		final Intent double_activity=new Intent(this,DoubleActivity.class);
		final Intent help_activity=new Intent(this,HelpActivity.class);
		final Intent single_activity=new Intent(this,SingleActivity.class);
		final Intent sound_activity=new Intent(this,SoundSettingActivity.class);

		ImageButton singleButton=(ImageButton)findViewById(R.id.imageButton2);
		ImageButton doubleButton=(ImageButton)findViewById(R.id.imageButton3);
		ImageButton helpButton=(ImageButton)findViewById(R.id.imageButton6);
		ImageButton soundButton=(ImageButton)findViewById(R.id.imageButton1);
		
		soundButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				startActivity(sound_activity);

			}

		});
		doubleButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				startActivity(double_activity);

			}

		});
		helpButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				startActivity(help_activity);

			}

		});
		singleButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				startActivity(single_activity);

			}

		});
	}



}
