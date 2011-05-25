package sak.samples.progresswithcancel;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class Main extends Activity {

    private ProgressDialog dialog;
    private Handler handler = new Handler();
    private MyThread thread = null;

    private final static int REQ_PROGRESS_DIALOG = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        Button btnStart = (Button)findViewById(R.id.btnStart);
        btnStart.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                showDialog(REQ_PROGRESS_DIALOG);

                thread = new MyThread();
                thread.start();
            }
        });
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
        case REQ_PROGRESS_DIALOG:
            dialog = new ProgressDialog(this);
            dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            dialog.setMessage("処理中");
            dialog.setButton(DialogInterface.BUTTON_NEGATIVE, "中止", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    thread.stop_();
                }
            });
            return dialog;
        }

        return super.onCreateDialog(id);
    }

    private class MyThread extends Thread {
        private boolean running = true;
        private boolean interupted = true;

        @Override
        public void run() {
            int counter = 10;
            try {
                while (running) {
                    Thread.sleep(500);
                    counter --;
                    if (counter == 0) {
                        stop_();
                        interupted = false;
                    }
                }

            } catch (InterruptedException e) {
            }

            handler.post(new Runnable() {
                @Override
                public void run() {
                    dialog.dismiss();
                    Toast.makeText(Main.this,
                            interupted ? "中止しました" : "終了しました", Toast.LENGTH_SHORT).show();
                }
            });
        }

        public void stop_() {
            running = false;
        }
    }
}