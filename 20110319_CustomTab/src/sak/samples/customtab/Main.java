package sak.samples.customtab;

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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        final TabHost tabHost = getTabHost();

        View v1 = new MyView(this, "いち", R.drawable.ic_tab_1);
        View v2 = new MyView(this, "にー", R.drawable.ic_tab_2);
        View v3 = new MyView(this, "さん", R.drawable.ic_tab_3);

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

        public MyView(Context context) {
            super(context);
            inflater = LayoutInflater.from(context);
        }

        public MyView(Context context, String title, int icon) {
            this(context);

            View v = inflater.inflate(R.layout.tabwidget, null);

            // アイコン
            ImageView iv = (ImageView) v.findViewById(R.id.imageview);
            iv.setImageResource(icon);

            // テキスト
            TextView tv = (TextView) v.findViewById(R.id.textview);
            tv.setText(title);

            addView(v);
        }
    }
}