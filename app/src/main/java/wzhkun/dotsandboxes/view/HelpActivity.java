package wzhkun.dotsandboxes.view;

import wzhkun.dotsandboxes.R;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

public class HelpActivity extends Activity {
	private Intent mainActivity;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_help);
	
		
		mainActivity = new Intent(this, MainActivity.class);
		ImageButton returnn = (ImageButton) findViewById(R.id.imageButtonReturn2);
		returnn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				startActivity(mainActivity);
				finish();


			}

		});
	}

	

}
