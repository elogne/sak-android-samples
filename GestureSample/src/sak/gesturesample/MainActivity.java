package sak.gesturesample;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends Activity
{
    private static final int SWIPE_MIN_DISTANCE = 120;
    private static final int SWIPE_MAX_OFF_PATH = 250;
    private static final int SWIPE_THRESHOLD_VELOCITY = 200;
    
    @Override
	public boolean onTouchEvent(MotionEvent event) {
		Log.d("debug", "@@@@ onTouchEvent was detected.");
		
		if (gestureDetector.onTouchEvent(event)) 
			return true;

		return super.onTouchEvent(event);
	}

    private GestureDetector gestureDetector;
    
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        gestureDetector = new GestureDetector(new MyGestureDetector());

        ImageView imageView = (ImageView)findViewById(R.id.ImageView01);
        // setOnTouchListener を機能させるためには setOnClickListener がなぜか必要？
        // もしくは Activity#onTouchEvent の中で gestureDetector.onTouchEvent を呼ぶ。
//        imageView.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				Log.d("debug", "@@@@ onClick was detected.");
//			}
//        	
//        }); 
        imageView.setOnTouchListener(new OnTouchListener() {
			@Override
            public boolean onTouch(View v, MotionEvent event) {
				Log.d("debug", "++++++ onTouch was detected.");
                if (gestureDetector.onTouchEvent(event)) {
                    return true;
                }
                return false;
            }
        });
            
    }

    class MyGestureDetector extends SimpleOnGestureListener {
	    @Override
	    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
			Log.d("debug", "**** onFling was detected.");
	        try {
	            if (Math.abs(e1.getY() - e2.getY()) > SWIPE_MAX_OFF_PATH)
	                return false;
	            
	            // right to left swipe
	            if (e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
	                Toast.makeText(MainActivity.this, "Left Swipe", Toast.LENGTH_SHORT).show();
	            }  
	            else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
	                Toast.makeText(MainActivity.this, "Right Swipe", Toast.LENGTH_SHORT).show();
	            }
	            
	        } catch (Exception e) {
	            // nothing
	        }
	        return false;
	    }

		@Override
		public boolean onDoubleTap(MotionEvent e) {
			Log.d("debug", "**** onDoubleTap was detected.");
			return false;
		}

		@Override
		public boolean onDoubleTapEvent(MotionEvent e) {
			Log.d("debug", "onDoubleTapEvent was detected.");
			return false;
		}

		@Override
		public boolean onDown(MotionEvent e) {
			Log.d("debug", "onDown was detected.");
			return false;
		}

		@Override
		public void onLongPress(MotionEvent e) {
			Log.d("debug", "onLongPress was detected.");
		}

		@Override
		public boolean onScroll(MotionEvent e1, MotionEvent e2,
				float distanceX, float distanceY) {
			Log.d("debug", "onScroll was detected.");
			return false;
		}

		@Override
		public void onShowPress(MotionEvent e) {
			Log.d("debug", "onShowPress was detected.");
		}

		@Override
		public boolean onSingleTapConfirmed(MotionEvent e) {
			Log.d("debug", "onSingleTapConfirmed was detected.");
			return false;
		}

		@Override
		public boolean onSingleTapUp(MotionEvent e) {
			Log.d("debug", "onSingleTapUp was detected.");
			return false;
		}
	    
	}
}