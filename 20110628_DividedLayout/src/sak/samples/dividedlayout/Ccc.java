package sak.samples.dividedlayout;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Ccc extends LinearLayout {

    private int count = 0;
    private Handler handler = new Handler();
    private DigitListener listener = null;

    private TextView tvDigit;

    public Ccc(Context context, AttributeSet attrs) {
        super(context, attrs);

        LayoutParams params =
            new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
        View child = inflate(context, R.layout.ccc, null);
        addView(child, params);

        tvDigit = (TextView)findViewById(R.id.ccc_digit);

        update();

        new MyThread().start();
    }

    public void setDigitListener(DigitListener listener) {
        this.listener = listener;
    }

    public void reset() {
        count = 0;
        update();
    }

    private void update() {
        tvDigit.setText(String.valueOf(count));
    }

    private class MyThread extends Thread {
        @Override
        public void run() {
            while (true) {
                try {
                    Thread.sleep(100);
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

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
