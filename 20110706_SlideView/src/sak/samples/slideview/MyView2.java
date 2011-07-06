package sak.samples.slideview;

import android.content.Context;
import android.util.AttributeSet;


public class MyView2 extends SlideView {

    public MyView2(Context context, AttributeSet attrs) {
        super(context, attrs);
        super.inflate(context, R.layout.my_view2, this);
    }
}
