package sak.samples.newoutgoingcall;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Binder;
import android.os.IBinder;
import android.widget.Toast;

public class MyService extends Service {

    private MyReceiver mReceiver;

    private final IBinder mBinder = new LocalBinder();

    public class LocalBinder extends Binder {
        MyService getService() {
            return MyService.this;
        }
    }

    @Override
    public IBinder onBind(Intent arg0) {
        return mBinder;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        mReceiver = new MyReceiver();
        registerReceiver(mReceiver, new IntentFilter(Intent.ACTION_NEW_OUTGOING_CALL));

        Toast.makeText(MyService.this, "サービスを開始します。", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mReceiver);

        Toast.makeText(MyService.this, "サービスを終了します。", Toast.LENGTH_SHORT).show();
    }

    private class MyReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(Intent.ACTION_NEW_OUTGOING_CALL)) {
                String number = intent.getStringExtra(Intent.EXTRA_PHONE_NUMBER);
//                String number = getResultData();    // こちらでも OK

                setResultData(null);

                Intent i = new Intent(context, HookActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.putExtra("number", number);
                context.startActivity(i);
            }
        }
    }
}
