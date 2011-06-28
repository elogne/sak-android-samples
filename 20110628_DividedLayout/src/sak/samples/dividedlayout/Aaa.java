package sak.samples.dividedlayout;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

public class Aaa extends FrameLayout {

    private int count = 0;
    private Handler handler = new Handler();

    private Bbb bbb;
    private TextView tvDigit;
    private Button btnReset;

    public Aaa(Context context, AttributeSet attrs) {
        super(context, attrs);

        LayoutParams params =
            new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
        View child = inflate(context, R.layout.aaa, null);
        addView(child, params);

        tvDigit = (TextView)findViewById(R.id.aaa_digit);

        bbb = (Bbb)findViewById(R.id.aaa_view);
        bbb.setDigitListener(new DigitListener() {
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
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            reset();
                        }
                    });
                }
            }
        });

        /*
         * リセットボタン
         */
        btnReset = (Button)findViewById(R.id.aaa_reset);
        btnReset.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                reset();
            }
        });

        update();
    }

    private void reset() {
        bbb.reset();
        count = 0;
        update();
    }

    public void update() {
        tvDigit.setText(String.valueOf(count));
    }
}
