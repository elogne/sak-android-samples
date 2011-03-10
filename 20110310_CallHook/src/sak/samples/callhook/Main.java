package sak.samples.callhook;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class Main extends Activity {

    protected SharedPreferences mPreference;

    private final String KEY_ENABLED = "enabled";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        mPreference = PreferenceManager.getDefaultSharedPreferences(this);

        /*
         * チェックボックス
         */
        Boolean enabled = mPreference.getBoolean(KEY_ENABLED, false);

        CheckBox cb = (CheckBox)findViewById(R.id.enable);
        cb.setChecked(enabled);
        cb.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton arg0, boolean checked) {
                /*
                 * 設定を保存する
                 */
                Editor editor = mPreference.edit();
                editor.putBoolean(KEY_ENABLED, checked);
                editor.commit();

                Toast.makeText(Main.this, "設定を " + checked + "に変更しました。", Toast.LENGTH_SHORT).show();

                update();
            }
        });

        /*
         * 発信ボタン
         */
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

        /*
         * 初期設定
         */
        update();
    }

    private void update() {
        PackageManager pm = getPackageManager();
        ComponentName componentname = new ComponentName(Main.this, HookActivity.class);
        Boolean enabled = mPreference.getBoolean(KEY_ENABLED, false);

        /*
         * 設定により表示／非表示を制御する。
         */
        pm.setComponentEnabledSetting(componentname,
                enabled ?
                    PackageManager.COMPONENT_ENABLED_STATE_ENABLED :
                    PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                PackageManager.DONT_KILL_APP);
    }
}
