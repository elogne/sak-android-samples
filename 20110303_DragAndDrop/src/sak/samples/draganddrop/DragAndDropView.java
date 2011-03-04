package sak.samples.draganddrop;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;

public class DragAndDropView extends GridView {

    private DragAndDropListener mListener;
    private WindowManager mWm;
    private WindowManager.LayoutParams mWindowParams;
    private ImageView mDragView = null;
    private Bitmap mDragBitmap = null;
    private int mFromPosition = AdapterView.INVALID_POSITION;

    public DragAndDropView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);

        mWm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);

        mWindowParams = new WindowManager.LayoutParams();
        mWindowParams.gravity = Gravity.TOP | Gravity.LEFT;
        mWindowParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        mWindowParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        mWindowParams.flags =
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE |
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE |
            WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON |
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS;
        mWindowParams.format = PixelFormat.TRANSLUCENT;
        mWindowParams.windowAnimations = 0;
        mWindowParams.x = 0;
        mWindowParams.y = 0;
    }

    public DragAndDropView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setOnDragnDropListener(DragAndDropListener listener) {
        mListener = listener;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        final int x = (int) event.getX();
        final int y = (int) event.getY();

        int action = event.getAction();

        if (action == MotionEvent.ACTION_DOWN) {
            mFromPosition = pointToPosition(x, y);
            if (mFromPosition == AdapterView.INVALID_POSITION)
                return false;
            startDrag();
            updateLayout(x, y);
            return true;

        } else if (action == MotionEvent.ACTION_MOVE) {
            updateLayout(x, y);
            return true;

        } else if (action == MotionEvent.ACTION_UP) {
            if (mListener != null) {
                mListener.dropped(mFromPosition, (int)event.getRawX(), (int)event.getRawY());    // Callback
            }
            endDrag();
            return true;

        } else if (action == MotionEvent.ACTION_CANCEL || action == MotionEvent.ACTION_OUTSIDE) {
            endDrag();
            return true;
        }

        return super.onTouchEvent(event);
    }

    private void updateLayout(int x, int y) {
        mWindowParams.x = getLeft() - getPaddingLeft() + x;
        mWindowParams.y = getTop() - getPaddingTop() + y;

        mWm.updateViewLayout(mDragView, mWindowParams);
    }

    private void startDrag() {
        View view = getChildByPosition(mFromPosition);
        mDragBitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas();
        canvas.setBitmap(mDragBitmap);
        view.draw(canvas);

        if (mDragView != null) {
            mWm.removeView(mDragView);
        }

        mDragView = new ImageView(getContext());
        mDragView.setImageBitmap(mDragBitmap);

        mWm.addView(mDragView, mWindowParams);
    }

    private void endDrag() {
        mWm.removeView(mDragView);
        mDragView = null;
        mDragBitmap = null;
    }

    private View getChildByPosition(int position) {
        return getChildAt(position - getFirstVisiblePosition());
    }
}
