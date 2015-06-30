package wzhkun.dotsandboxes;

import wzhkun.dotsandboxes.R;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);


		final Intent doubleee=new Intent(this,Doublee.class);
		final Intent helpp=new Intent(this,Help.class);
		final Intent singlee=new Intent(this,Single.class);
		final Intent sound=new Intent(this,Sound.class);
		ImageButton single=(ImageButton)findViewById(R.id.imageButton2);
		ImageButton doublee=(ImageButton)findViewById(R.id.imageButton3);
		ImageButton help=(ImageButton)findViewById(R.id.imageButton6);
		ImageButton soundd=(ImageButton)findViewById(R.id.imageButton1);
		
		soundd.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				startActivity(sound);
				// TODO Auto-generated method stub
				
			}
			
		});
		
		doublee.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				startActivity(doubleee);
				// TODO Auto-generated method stub
				
			}
			
		});
		help.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				startActivity(helpp);
				// TODO Auto-generated method stub
				
			}
			
		});
		single.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				startActivity(singlee);
				// TODO Auto-generated method stub
				
			}
			
		});
	}



}
