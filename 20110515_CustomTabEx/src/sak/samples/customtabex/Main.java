package sak.samples.customtabex;

import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

public class Main extends TabActivity {

    private TabHost tabHost;
    private MyView v1;
    private MyView v2;
    private MyView v3;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        tabHost = getTabHost();

        v1 = new MyView(this, "いち", R.drawable.ic_tab_1);
        v2 = new MyView(this, "にー", R.drawable.ic_tab_2);
        v3 = new MyView(this, "さん", R.drawable.ic_tab_3);

        // いち
        tabHost.addTab(tabHost.newTabSpec("TAB_1")
                .setIndicator(v1)
                .setContent(new Intent(this, Tab1.class)));

        // にー
        tabHost.addTab(tabHost.newTabSpec("TAB_2")
                .setIndicator(v2)
                .setContent(new Intent(this, Tab2.class)));

        // さん
        tabHost.addTab(tabHost.newTabSpec("TAB_3")
                .setIndicator(v3)
                .setContent(new Intent(this, Tab3.class)));

        tabHost.setCurrentTab(0);
    }

    private class MyView extends FrameLayout {
        private LayoutInflater inflater;
        private View indicator;
        private TabHost tabHost;

        public MyView(Context context) {
            super(context);
            inflater = LayoutInflater.from(context);
            tabHost = ((TabActivity)context).getTabHost();
        }

        public MyView(final Context context, String title, int icon) {
            this(context);

            indicator = inflater.inflate(R.layout.tabwidget, null);

            // アイコン
            ImageView iv = (ImageView) indicator.findViewById(R.id.icon);
            iv.setImageResource(icon);

            // テキスト
            TextView tv = (TextView) indicator.findViewById(R.id.text);
            tv.setText(title);

            indicator.setFocusable(true);
            indicator.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (v == v1.getIndicator()) {
                        tabHost.setCurrentTab(0);
                    } else if (v == v2.getIndicator()) {
                        tabHost.setCurrentTab(1);
                    } else if (v == v3.getIndicator()) {
                        tabHost.setCurrentTab(2);
                    }
                 }
            });

            addView(indicator);
        }

        public View getIndicator() {
            return indicator;
        }
    }
}