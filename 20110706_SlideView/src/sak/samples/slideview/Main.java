package sak.samples.slideview;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Main extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        MyView1 view1 = (MyView1)findViewById(R.id.myview1);
        MyView2 view2 = (MyView2)findViewById(R.id.myview2);
        MyView3 view3 = (MyView3)findViewById(R.id.myview3);

        final SlideViewGroup vg = new SlideViewGroup();
        vg.add(view1);     // 0
        vg.add(view2);     // 1
        vg.add(view3);     // 2

        Button b1 = (Button)findViewById(R.id.button1);
        b1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                vg.select(0);
            }
        });

        Button b2 = (Button)findViewById(R.id.button2);
        b2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                vg.select(1);
            }
        });

        Button b3 = (Button)findViewById(R.id.button3);
        b3.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                vg.select(2);
            }
        });
    }
}