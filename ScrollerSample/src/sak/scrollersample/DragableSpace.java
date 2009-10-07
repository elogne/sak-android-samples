package sak.scrollersample;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Scroller;

public class DragableSpace extends ViewGroup {
	
	private Scroller mScroller;
	private VelocityTracker mVelocityTracker;
          
	private int mScrollX = 0;
	private int mCurrentScreen = 0;
     
	private float mLastMotionX;
   
   
    private static final String LOG_TAG = "DragableSpace";
   
    private static final int SNAP_VELOCITY = 1000;
   
    private final static int TOUCH_STATE_REST = 0;
    private final static int TOUCH_STATE_SCROLLING = 1;
   
    private int mTouchState = TOUCH_STATE_REST;
   
    public DragableSpace(Context context, AttributeSet attrs) {
    	super(context, attrs);
    	mScroller = new Scroller(context);

    	this.setLayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT ,
                    ViewGroup.LayoutParams.FILL_PARENT));
    }
               
    public DragableSpace(Context context) {
    	super(context);
    	mScroller = new Scroller(context);

    	this.setLayoutParams(new ViewGroup.LayoutParams(
                   ViewGroup.LayoutParams.WRAP_CONTENT ,
                   ViewGroup.LayoutParams.FILL_PARENT));
    }
              

    @Override
    public boolean onTouchEvent(MotionEvent event) {
               
    	if (mVelocityTracker==null) {
    		mVelocityTracker = VelocityTracker.obtain();
    	}
    	mVelocityTracker.addMovement(event);

    	final int action = event.getAction();
    	final float x = event.getX();
           
    	switch (action) {
    	case MotionEvent.ACTION_DOWN:
//    		Log.i(LOG_TAG,"event : down");
    		/*
    		 * If being flinged and user touches, stop the fling. isFinished
    		 * will be false if being flinged.
    		 */
    		if (!mScroller.isFinished()) {
    			mScroller.abortAnimation();
    		}

    		// Remember where the motion event started
    		mLastMotionX = x;
    		break;
    		
    	case MotionEvent.ACTION_MOVE:
//    		Log.i(LOG_TAG,"event : move");
    		//if (mTouchState == TOUCH_STATE_SCROLLING) {
    		// Scroll to follow the motion event
    		final int deltaX = (int) (mLastMotionX - x);
    		mLastMotionX = x;

    		if (deltaX < 0) {
    			if (mScrollX > 0) {
    				scrollBy(Math.max(-mScrollX, deltaX), 0);
    			}
    		} else if (deltaX > 0) {
    			final int availableToScroll = getChildAt(getChildCount() - 1).getRight() -
    			mScrollX - getWidth();
    			if (availableToScroll > 0) {
    				scrollBy(Math.min(availableToScroll, deltaX), 0);
    			}
    		}
    		//}
    		break;
    	
    	case MotionEvent.ACTION_UP:
//    		Log.i(LOG_TAG,"event : up");
    		//if (mTouchState == TOUCH_STATE_SCROLLING) {
    		final VelocityTracker velocityTracker = mVelocityTracker;
    		velocityTracker.computeCurrentVelocity(1000);
    		int velocityX = (int) velocityTracker.getXVelocity();
    		
    		if (velocityX > SNAP_VELOCITY && mCurrentScreen > 0) {
    			// Fling hard enough to move left
    			snapToScreen(mCurrentScreen - 1);
    		} else if (velocityX < -SNAP_VELOCITY && mCurrentScreen < getChildCount() - 1) {
    			// Fling hard enough to move right
    			snapToScreen(mCurrentScreen + 1);
    		} else {
    			snapToDestination();
    		}

    		if (mVelocityTracker != null) {
    			mVelocityTracker.recycle();
    			mVelocityTracker = null;
    		}
    		//}
    		mTouchState = TOUCH_STATE_REST;
    		break;
    
    	case MotionEvent.ACTION_CANCEL:
//    		Log.i(LOG_TAG,"event : cancle");
    		mTouchState = TOUCH_STATE_REST;
    	}
    	mScrollX = this.getScrollX();

    	return true;
    }
     
    private void snapToDestination() {
    	final int screenWidth = getWidth();
        final int whichScreen = (mScrollX + (screenWidth / 2)) / screenWidth;
        
//        Log.i(LOG_TAG,"from des");

        snapToScreen(whichScreen);
    }
     
    private void snapToScreen(int whichScreen) {       
//    	Log.i(LOG_TAG,"curt to screen:"+ mCurrentScreen);
//    	Log.i(LOG_TAG,"snap to screen:"+ whichScreen);  
    	
    	mCurrentScreen = whichScreen;
    	final int newX = whichScreen * getWidth();
    	final int delta = newX - mScrollX;
    	
//    	Log.i(LOG_TAG,"total chld "+ this.getChildCount());
//    	Log.i(LOG_TAG,"curt X "+ mScrollX);
//    	Log.i(LOG_TAG,"snap to newX "+ newX);
//    	Log.i(LOG_TAG,"delta to scroll "+ delta);
//    	Log.i(LOG_TAG,"duration "+ Math.abs(delta) * 2);
    	
    	mScroller.startScroll(mScrollX, 0, delta, 0, Math.abs(delta) * 2);       
    	invalidate();
    	
//    	Log.i(LOG_TAG,"scroller finished "+mScroller.isFinished());
//    	Log.i(LOG_TAG,"scroller duration "+mScroller.getDuration());
//    	Log.i(LOG_TAG,"scroller finalX "+mScroller.getFinalX());
             
    }


    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
    	int childLeft = 0;

    	final int count = getChildCount();
    	for (int i = 0; i < count; i++) {
    		final View child = getChildAt(i);
    		if (child.getVisibility() != View.GONE) {
    			final int childWidth = child.getMeasuredWidth();
    			child.layout(childLeft, 0, childLeft + childWidth, child.getMeasuredHeight());
    			childLeft += childWidth;
    		}
    	}
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    	super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        final int width = MeasureSpec.getSize(widthMeasureSpec);
        final int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        if (widthMode != MeasureSpec.EXACTLY) {
            throw new IllegalStateException("error mode.");
        }

        final int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        if (heightMode != MeasureSpec.EXACTLY) {
            throw new IllegalStateException("error mode.");
        }

        // The children are given the same width and height as the workspace
        final int count = getChildCount();
        for (int i = 0; i < count; i++) {
            getChildAt(i).measure(widthMeasureSpec, heightMeasureSpec);
        }
        scrollTo(mCurrentScreen * width, 0);      
    }     
     
    @Override
    public void computeScroll() {
    	if (mScroller.computeScrollOffset()) {
    		mScrollX = mScroller.getCurrX();
    		scrollTo(mScrollX, 0);
    		postInvalidate();
//      } else if (mNextScreen != INVALID_SCREEN) {
//          mCurrentScreen = Math.max(0, Math.min(mNextScreen, getChildCount() - 1));
//          mNextScreen = INVALID_SCREEN;
    	}
    }
} 