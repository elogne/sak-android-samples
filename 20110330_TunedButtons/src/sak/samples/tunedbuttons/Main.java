package sak.samples.tunedbuttons;

import android.app.TabActivity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.TabHost.TabContentFactory;

public class Main extends TabActivity implements TabContentFactory {

    private static String TAB_1 = "TAB_1";
    private static String TAB_2 = "TAB_2";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        final TabHost tabHost = getTabHost();

        View v1 = new MyView(this, "チューン前");
        View v2 = new MyView(this, "チューン後");

        tabHost.addTab(tabHost.newTabSpec(TAB_1)
                .setIndicator(v1)
                .setContent(this));

        tabHost.addTab(tabHost.newTabSpec(TAB_2)
                .setIndicator(v2)
                .setContent(this));
    }

    @Override
    public View createTabContent(String tag) {
        final TabHost tabHost = getTabHost();

        final int layout;
        if (tag.equals(TAB_1))
            layout = R.layout.before;
        else
            layout = R.layout.after;

        View v = LayoutInflater.from(this).inflate(layout, tabHost.getTabContentView(), false);
        return v;
    }

    private class MyView extends FrameLayout {
        private LayoutInflater inflater;

        public MyView(Context context) {
            super(context);
            inflater = LayoutInflater.from(context);
        }

        public MyView(Context context, String title) {
            this(context);

            View v = inflater.inflate(R.layout.tabwidget, null);

            TextView tv = (TextView) v.findViewById(R.id.text);
            tv.setText(title);

            addView(v);
        }
    }
}