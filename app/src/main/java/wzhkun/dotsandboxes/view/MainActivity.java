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


		final Intent doubleee=new Intent(this,DoubleActivity.class);
		final Intent helpp=new Intent(this,HelpActivity.class);
		final Intent singlee=new Intent(this,SingleActivity.class);
		final Intent sound=new Intent(this,SoundSettingActivity.class);
		ImageButton single=(ImageButton)findViewById(R.id.imageButton2);
		ImageButton doublee=(ImageButton)findViewById(R.id.imageButton3);
		ImageButton help=(ImageButton)findViewById(R.id.imageButton6);
		ImageButton soundd=(ImageButton)findViewById(R.id.imageButton1);
		
		soundd.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				startActivity(sound);
				
			}
			
		});
		
		doublee.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				startActivity(doubleee);
				
			}
			
		});
		help.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				startActivity(helpp);
				
			}
			
		});
		single.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				startActivity(singlee);
				
			}
			
		});
	}



}
