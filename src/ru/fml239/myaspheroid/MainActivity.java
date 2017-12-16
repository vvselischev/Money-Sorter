package ru.fml239.myaspheroid;

import com.example.moneysorter.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager;

public class MainActivity extends Activity
{
	private SpheroidView spheroidView;
 	private GestureDetector gestureDetector;

	@Override
    public void onCreate(Bundle savedInstanceState)
	{
        super.onCreate(savedInstanceState);
		// hide head-line
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		// hide uvedomlenia:
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
							 WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.main);

		spheroidView = (SpheroidView)findViewById(R.id.spheroidView);
		gestureDetector = new GestureDetector(this, new MyGestureListener());
    }
	@Override
	public void onPause()
	{
		super.onPause(); // call the super method
		spheroidView.stopGame(); // terminates the game
	} // end method onPause

	// release resources
	@Override
	protected void onDestroy()
	{
		super.onDestroy();
		spheroidView.releaseResources();
	} // end method onDestroy

	@Override
	public boolean onTouchEvent(MotionEvent event)
	{
		int action = event.getAction();
		switch (action)
		{
			case MotionEvent.ACTION_DOWN:
				//log += "D,";
				spheroidView.motionEvent(SpheroidView.ACTION_DOWN, event);
				break;
			case MotionEvent.ACTION_UP:
				spheroidView.motionEvent(SpheroidView.ACTION_UP, event);
				//log += "U,";
				break;
			case MotionEvent.ACTION_MOVE:				
				spheroidView.motionEvent(SpheroidView.ACTION_MOVE, event);
				//log += "M,";
				break;
		}
		
		gestureDetector.onTouchEvent(event);
		return true;
	}

	class MyGestureListener extends GestureDetector.SimpleOnGestureListener
	{
		@Override
		public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY)
		{
			spheroidView.motionScrollEvent(e1, e2, distanceX, distanceY);
			//log += "SCR,";
			return true;
		}

		@Override
		public boolean onDoubleTap(MotionEvent event)
		{
			spheroidView.motionEvent(SpheroidView.ACTION_DOUBLE_TAP, event);
			//log += "DT,";
			return true;
		}
	}
}
