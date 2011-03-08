package sak.samples.newoutgoingcall;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class HookActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hook);

        Intent intent = getIntent();
        final String number = intent.getStringExtra("number");

        TextView tv = (TextView)findViewById(R.id.number);
        tv.setText(number);

        Button btnCall = (Button)findViewById(R.id.ok);
        btnCall.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                finish();
            }
        });
    }
}
