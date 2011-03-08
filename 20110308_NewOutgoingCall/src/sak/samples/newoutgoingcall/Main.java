package sak.samples.newoutgoingcall;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Main extends Activity {

    private NotificationManager mNotificationManager;
    private static final int NOTIFICATION_ID = 0x1234;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main);

        mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        Button btnStart = (Button)findViewById(R.id.start);
        btnStart.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(Main.this, MyService.class);
                startService(intent);
                setupNotification();
            }
        });

        Button btnStop = (Button)findViewById(R.id.stop);
        btnStop.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(Main.this, MyService.class);
                stopService(intent);
                clearNotification();
            }
        });

        Button btnDial = (Button)findViewById(R.id.dial);
        btnDial.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                String number = "117";
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + number));
                startActivity(intent);
            }
        });
    }

    private void setupNotification() {
        Notification notification = new Notification(
                R.drawable.stat_sample, "サービスを開始します。", System.currentTimeMillis());
        notification.flags = Notification.FLAG_ONGOING_EVENT;
        Intent intent = new Intent(this, Main.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pi = PendingIntent.getActivity(this, 0, intent, 0);
        notification.setLatestEventInfo(this, "New Outgoing Call", "発信のINTENTをフック中です。", pi);
        mNotificationManager.notify(NOTIFICATION_ID, notification);
    }

    private void clearNotification() {
        mNotificationManager.cancel(NOTIFICATION_ID);
    }
}
