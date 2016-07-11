package com.modified.cannon;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.media.AudioManager;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.widget.NumberPicker;
import android.widget.Toast;

import com.modified.cannon.R;
import com.modified.cannon.CannonView;

public class MainActivity extends Activity {
	private GestureDetector gestureDetector; // listens for double taps
	private CannonView cannonView; // custom view to display the game
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main, menu);
		return super.onCreateOptionsMenu(menu);
	}
	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case R.id.action_settings:
			
			cannonView.showSettingsDialog(R.string.change_timer);
			return true;
		}
		return super.onMenuItemSelected(featureId, item);
	}
	
	// called when the app first launches
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState); // call super's onCreate method
		setContentView(R.layout.main); // inflate the layout

		// allow volume keys to set game volume
				setVolumeControlStream(AudioManager.STREAM_MUSIC);
				
		// get the CannonView
		cannonView = (CannonView) findViewById(R.id.cannonView);

		// initialize the GestureDetector
		gestureDetector = new GestureDetector(this, gestureListener);

			
		
	} // end method onCreate

	// when the app is pushed to the background, pause it
	@Override
	public void onPause() {
		super.onPause(); // call the super method
		cannonView.stopGame(); // terminates the game
	} // end method onPause

	// release resources
	@Override
	protected void onDestroy() {
		super.onDestroy();
		cannonView.releaseResources();
	} // end method onDestroy

	// called when the user touches the screen in this Activity
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// get int representing the type of action which caused this event
		int action = event.getAction();

		// the user user touched the screen or dragged along the screen
		if (action == MotionEvent.ACTION_DOWN
				|| action == MotionEvent.ACTION_MOVE) {
			cannonView.alignCannon(event); // align the cannon
		} // end if

		// call the GestureDetector's onTouchEvent method
		return gestureDetector.onTouchEvent(event);
	} // end method onTouchEvent

	// listens for touch events sent to the GestureDetector
	SimpleOnGestureListener gestureListener = new SimpleOnGestureListener() {
		// called when the user double taps the screen
		@Override
		public boolean onDoubleTap(MotionEvent e) {
			cannonView.fireCannonball(e); // fire the cannonball
			return true; // the event was handled
		} // end method onDoubleTap
	}; // end gestureListener
}