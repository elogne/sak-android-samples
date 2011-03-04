package sak.samples.overridependingtransaction;

import android.app.Activity;
import android.content.Intent;
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
    boolean slideUpdown = false;

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

    protected void showNext(Intent intent, int flags) {
        flags |= Intent.FLAG_ACTIVITY_NEW_TASK;
        flags |= Intent.FLAG_ACTIVITY_CLEAR_TOP;
        intent.setFlags(flags);
        startActivity(intent);
        if (slideUpdown)
            overridePendingTransition(R.anim.push_up_in, R.anim.push_up_out);
        else
            overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
    }

    protected void showPrev(Intent intent, int flags) {
        finish();
    }

    @Override
    public void finish() {
        super.finish();
        if (slideUpdown)
            overridePendingTransition(R.anim.push_down_in, R.anim.push_down_out);
        else
            overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
    }
}
