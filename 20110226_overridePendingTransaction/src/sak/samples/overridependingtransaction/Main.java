package sak.samples.overridependingtransaction;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.ViewGroup.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

public class Main extends MyActivityBase {

    private static final String TEXT = "0";
    private static final int TEXT_SIZE = 64;
    private static final int TEXT_COLOR = Color.argb(0xff, 0xff, 0xff, 0xff);    // 白
    private static final int BACK_COLOR = Color.argb(0xff, 0x88, 0x88, 0x88);    // 灰

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TextView tv = new TextView(this);
        tv.setLayoutParams(new LayoutParams(
                LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
        tv.setGravity(Gravity.CENTER);
        tv.setText(TEXT);
        tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, TEXT_SIZE);
        tv.setTextColor(TEXT_COLOR);
        tv.setBackgroundColor(BACK_COLOR);
        setContentView(tv);
    }

    @Override
    protected void next() {
        Intent intent = new Intent(this, Activity1.class);
        super.showNext(intent, 0);
    }

    @Override
    protected void back() {
        Toast.makeText(this, "最初のページです。", Toast.LENGTH_SHORT).show();
    }
}
