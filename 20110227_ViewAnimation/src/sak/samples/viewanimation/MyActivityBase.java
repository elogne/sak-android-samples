package sak.samples.viewanimation;

import android.app.Activity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.GestureDetector.SimpleOnGestureListener;

abstract public class MyActivityBase extends Activity {

    private static final int THRESHOLD_DISTANCE = 120;
    private static final int THRESHOLD_OPPOSITION_AXIS = 250;
    private static final int THRESHOLD_VELOCITY = 200;

    private GestureDetector mGestureDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mGestureDetector = new GestureDetector(new MyGestureListener());
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (mGestureDetector.onTouchEvent(event))
            return true;
        else
            return false;
    }

    /*
     * true なら上下方向、false なら左右方向の画面遷移をする。
     */
    protected boolean slideUpdown = true;

    private class MyGestureListener extends SimpleOnGestureListener {
        @Override
        public boolean onFling(
                MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {

            float dX = e2.getX() - e1.getX();
            float dY = e2.getY() - e1.getY();

            float velocity = Math.abs(slideUpdown ? velocityY : velocityX);
            float diff = slideUpdown ? dY : dX;
            float opposition_axis = Math.abs(slideUpdown ? dX : dY);

            if (opposition_axis > THRESHOLD_OPPOSITION_AXIS)
                return false;

            if (-diff > THRESHOLD_DISTANCE && velocity > THRESHOLD_VELOCITY)
                next();
            else if (diff > THRESHOLD_DISTANCE && velocity > THRESHOLD_VELOCITY)
                back();

            return false;
        }
    }

    abstract protected void next();

    protected void back() {
        finish();
    }
}
