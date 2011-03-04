package sak.samples.viewanimation;

import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewAnimator;

public class Main extends MyActivityBase {

    private static final int TEXT_SIZE = 64;
    private static final int TEXT_COLOR  = Color.argb(0xff, 0xff, 0xff, 0xff);    // 白
    private static final int COLOR_GRAY  = Color.argb(0xff, 0x88, 0x88, 0x88);    // 灰
    private static final int COLOR_RED   = Color.argb(0xff, 0xff, 0x00, 0x00);    // 赤
    private static final int COLOR_GREEN = Color.argb(0xff, 0x00, 0xff, 0x00);    // 緑
    private static final int COLOR_BLUE  = Color.argb(0xff, 0x00, 0x00, 0xff);    // 青

    private Animation nextIn;
    private Animation nextOut;
    private Animation prevIn;
    private Animation prevOut;

    private ViewAnimator va;
    private int page = 0;

    private static int ANIM_MODE = Animation.RELATIVE_TO_PARENT;
    private static int ANIM_DURATION = 300;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!slideUpdown) {
            nextIn  = new TranslateAnimation(
                    ANIM_MODE, 1f, ANIM_MODE, 0f, ANIM_MODE, 0f, ANIM_MODE, 0f);
            nextOut = new TranslateAnimation(
                    ANIM_MODE, 0f, ANIM_MODE, -1f, ANIM_MODE, 0f, ANIM_MODE, 0f);
            prevIn  = new TranslateAnimation(
                    ANIM_MODE, -1f, ANIM_MODE, 0f, ANIM_MODE, 0f, ANIM_MODE, 0f);
            prevOut = new TranslateAnimation(
                    ANIM_MODE, 0f, ANIM_MODE, 1f, ANIM_MODE, 0f, ANIM_MODE, 0f);

        } else {
            nextIn  = new TranslateAnimation(
                    ANIM_MODE, 0f, ANIM_MODE, 0f, ANIM_MODE, 1f, ANIM_MODE, 0f);
            nextOut = new TranslateAnimation(
                    ANIM_MODE, 0f, ANIM_MODE, 0f, ANIM_MODE, 0f, ANIM_MODE, -1f);
            prevIn  = new TranslateAnimation(
                    ANIM_MODE, 0f, ANIM_MODE, 0f, ANIM_MODE, -1f, ANIM_MODE, 0f);
            prevOut = new TranslateAnimation(
                    ANIM_MODE, 0f, ANIM_MODE, 0f, ANIM_MODE, 0f, ANIM_MODE, 1f);
            // リソースを使用する場合はこちらを使用する
        }

        nextIn.setDuration(ANIM_DURATION);
        nextOut.setDuration(ANIM_DURATION);
        prevIn.setDuration(ANIM_DURATION);
        prevOut.setDuration(ANIM_DURATION);

        // リソースを使用する場合はこちらを使用する
//        if (!slideUpdown) {
//            nextIn = AnimationUtils.loadAnimation(this, R.anim.push_left_in);
//            nextOut = AnimationUtils.loadAnimation(this, R.anim.push_left_out);
//            prevIn = AnimationUtils.loadAnimation(this, R.anim.push_right_in);
//            prevOut = AnimationUtils.loadAnimation(this, R.anim.push_right_out);
//
//        } else {
//            nextIn = AnimationUtils.loadAnimation(this, R.anim.push_up_in);
//            nextOut = AnimationUtils.loadAnimation(this, R.anim.push_up_out);
//            prevIn = AnimationUtils.loadAnimation(this, R.anim.push_down_in);
//            prevOut = AnimationUtils.loadAnimation(this, R.anim.push_down_out);
//        }

        va = new ViewAnimator(this);
        va.setLayoutParams(new LayoutParams(
                LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));

        va.addView(createTextView("0", COLOR_GRAY));
        va.addView(createTextView("1", COLOR_RED));
        va.addView(createTextView("2", COLOR_GREEN));
        va.addView(createTextView("3", COLOR_BLUE));

        setContentView(va);
    }

    private TextView createTextView(String text, int background) {
        TextView tv = new TextView(this);
        tv.setLayoutParams(new LayoutParams(
                LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
        tv.setGravity(Gravity.CENTER);
        tv.setText(text);
        tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, TEXT_SIZE);
        tv.setTextColor(TEXT_COLOR);
        tv.setBackgroundColor(background);

        return tv;
    }

    @Override
    protected void next() {
        if (page < va.getChildCount() - 1) {
            va.setInAnimation(nextIn);
            va.setOutAnimation(nextOut);
            va.showNext();
            page ++;
        } else {
            Toast.makeText(this, "最後のページです。", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void back() {
        if (page > 0) {
            va.setInAnimation(prevIn);
            va.setOutAnimation(prevOut);
            va.showPrevious();
            page --;
        } else {
            Toast.makeText(this, "最初のページです。", Toast.LENGTH_SHORT).show();
        }
    }
}
