package sak.samples.dividedlayout;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Bbb extends LinearLayout {

    private int count = 0;
    private Handler handler = new Handler();
    private DigitListener listener = null;
    private TextView tvDigit;
    private Ccc ccc;

    public Bbb(Context context, AttributeSet attrs) {
        super(context, attrs);

        LayoutParams params =
            new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
        View child = inflate(context, R.layout.bbb, null);
        addView(child, params);

        tvDigit = (TextView)findViewById(R.id.bbb_digit);

        ccc = (Ccc)findViewById(R.id.bbb_view);
        ccc.setDigitListener(new DigitListener() {
            @Override
            public void updateDigit() {
                count ++;

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        update();
                    }
                });

                if (count > 9) {
                    count = 0;
                    if (listener != null) {
                        listener.updateDigit();
                    }
                }
            }
        });

        update();
    }

    public void setDigitListener(DigitListener listener) {
        this.listener = listener;
    }

    public void reset() {
        ccc.reset();
        count = 0;
        update();
    }

    public void update() {
        tvDigit.setText(String.valueOf(count));
    }
}
