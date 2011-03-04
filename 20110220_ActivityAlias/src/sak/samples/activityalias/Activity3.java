package sak.samples.activityalias;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.ViewGroup.LayoutParams;
import android.widget.TextView;

public class Activity3 extends Activity {

	private static final String TEXT = "3";
	private static final int TEXT_SIZE = 64;
	private static final int TEXT_COLOR = Color.argb(0xff, 0xff, 0xff, 0xff);	// 白
	private static final int BACK_COLOR = Color.argb(0xff, 0x00, 0x00, 0xff);	// 青

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
}
