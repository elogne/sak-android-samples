package sak.samples.slideview;

import android.content.Context;
import android.util.AttributeSet;


public class MyView3 extends SlideView {

    public MyView3(Context context, AttributeSet attrs) {
        super(context, attrs);
        super.inflate(context, R.layout.my_view3, this);
    }
}
