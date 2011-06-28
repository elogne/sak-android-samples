package sak.samples.customdialog;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Main extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        Button b1 = (Button)findViewById(R.id.button1);
        b1.setOnClickListener(new OnMyClickListener(R.layout.dialog1, R.style.AnimDialog));

        Button b2 = (Button)findViewById(R.id.button2);
        b2.setOnClickListener(new OnMyClickListener(R.layout.dialog1, R.style.AnimToast));

        Button b3 = (Button)findViewById(R.id.button3);
        b3.setOnClickListener(new OnMyClickListener(R.layout.dialog1, R.style.AnimWallPaper));

        Button b4 = (Button)findViewById(R.id.button4);
        b4.setOnClickListener(new OnMyClickListener(R.layout.dialog1, R.style.AnimSubmenu));

        Button b5 = (Button)findViewById(R.id.button5);
        b5.setOnClickListener(new OnMyClickListener(R.layout.dialog1, R.style.AnimInputMethod));

        Button b6 = (Button)findViewById(R.id.button6);
        b6.setOnClickListener(new OnMyClickListener(R.layout.dialog1, R.style.AnimInputMethodFancy));

        Button b7 = (Button)findViewById(R.id.button7);
        b7.setOnClickListener(new OnMyClickListener(R.layout.dialog2, R.style.AnimOptionsPanel));

        Button b8 = (Button)findViewById(R.id.button8);
        b8.setOnClickListener(new OnMyClickListener(R.layout.dialog3, R.style.AnimStatusBar));
    }

    private class OnMyClickListener implements OnClickListener {
        private int layout;
        private int style;

        public OnMyClickListener(int layout, int style) {
            this.layout = layout;
            this.style = style;
        }

        @Override
        public void onClick(View arg0) {
            final Dialog dialog = new Dialog(Main.this, style);
            dialog.setContentView(layout);

            // OK ボタン
            Button btnOk = (Button)dialog.findViewById(R.id.ok);
            btnOk.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View arg0) {
                    dialog.dismiss();
                }
            });

            // Cancel ボタン
            Button btnCancel = (Button)dialog.findViewById(R.id.cancel);
            btnCancel.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View arg0) {
                    dialog.cancel();
                }
            });

            dialog.show();
        }
    }
}