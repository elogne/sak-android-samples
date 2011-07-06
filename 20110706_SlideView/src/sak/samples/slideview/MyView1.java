package sak.samples.slideview;

import android.content.Context;
import android.util.AttributeSet;


public class MyView1 extends SlideView {

    public MyView1(Context context, AttributeSet attrs) {
        super(context, attrs);
        super.inflate(context, R.layout.my_view1, this);
    }
}
