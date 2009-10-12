package sak.homesample;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends Activity 
implements AdapterView.OnItemClickListener { 
    private ArrayList<AppInfo> appList;
    private final BroadcastReceiver appReceiver = new AppReceiver();
    private GridView gridView;
        
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        // レイアウトの生成
        LinearLayout layout = new LinearLayout(this);
        layout.setBackgroundResource(R.drawable.bg);
        layout.setOrientation(LinearLayout.VERTICAL);
        setContentView(layout); 

        // グリッドビューの生成
        gridView = new GridView(this);
        gridView.setNumColumns(4);
        gridView.setGravity(Gravity.CENTER);
        gridView.setOnItemClickListener(this);
        setLLParams(gridView,
        	LinearLayout.LayoutParams.FILL_PARENT,
        	LinearLayout.LayoutParams.FILL_PARENT);
        layout.addView(gridView);
        
        // アプリリストの読み込み
        loadAppList();     

        // アプリ登録解除レシーバーの開始
        IntentFilter filter = new IntentFilter(Intent.ACTION_PACKAGE_ADDED);
        filter.addAction(Intent.ACTION_PACKAGE_REMOVED);
        filter.addAction(Intent.ACTION_PACKAGE_CHANGED);
        filter.addDataScheme("package");
        registerReceiver(appReceiver, filter);        
    }   
    
    private void loadAppList() {

        Intent intent = new Intent(Intent.ACTION_MAIN, null);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);        
        
        PackageManager manager = getPackageManager();
        List<ResolveInfo> apps = manager.queryIntentActivities(intent, 0);
        Collections.sort(apps, new ResolveInfo.DisplayNameComparator(manager));

        // アクティビティ情報の取得
        appList = new ArrayList<AppInfo>();
        if (apps == null) 
        	return;
        
        for (int i=0; i<apps.size(); i++) {
            AppInfo appInfo = new AppInfo();
            ResolveInfo info = apps.get(i);
            appInfo.title = info.loadLabel(manager);
            appInfo.setActivity(
                info.activityInfo.applicationInfo.packageName,
                info.activityInfo.name);
//            appInfo.icon = resizeIcon(info.activityInfo.loadIcon(manager));
            appInfo.icon = info.activityInfo.loadIcon(manager);
            appList.add(appInfo);
        }
        
        // グリッドの更新
        gridView.setAdapter(new GridAdapter(this));
    } 
    
    // アイコンサイズの変更
    private Drawable resizeIcon(Drawable icon) {
        // 標準アイコンサイズの取得
        Resources res=getResources();
        int width  = (int)res.getDimension(android.R.dimen.app_icon_size);
        int height = (int)res.getDimension(android.R.dimen.app_icon_size);
        
        // 現在のアイコンサイズの取得
        int iconWidth  = icon.getIntrinsicWidth();
        int iconHeight = icon.getIntrinsicHeight();

        //アイコンサイズの変更
        if (width>0 && height>0 && 
            (width<iconWidth || height<iconHeight)) {
            
            //変換後のアイコンサイズの計算
            float ratio = (float)iconWidth/iconHeight;
            if (iconWidth>iconHeight) {
                height=(int)(width/ratio);
            } else if (iconHeight>iconWidth) {
                width=(int)(height*ratio);
            }

            // 動的キャンバスの生成
            Bitmap.Config c = (icon.getOpacity()!=PixelFormat.OPAQUE)?
            					Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565;
            Bitmap thumb=Bitmap.createBitmap(width,height,c);
            Canvas canvas=new Canvas(thumb);
            canvas.setDrawFilter(new PaintFlagsDrawFilter(Paint.DITHER_FLAG,0));

            // 動的キャンバスへのアイコン描画
            Rect oldBounds=new Rect();
            oldBounds.set(icon.getBounds());
            icon.setBounds(0,0,width,height);
            icon.draw(canvas);
            icon.setBounds(oldBounds);
            
            // キャンバスをDrawableオブジェクトに変換
            icon = new BitmapDrawable(thumb);
        }        
        return icon;
    }

    // グリッドアイテムのクリックイベント処理
    public void onItemClick(AdapterView<?> parent,View v,int position,long id) {
        // アクティビティの起動
        AppInfo appInfo=(AppInfo)parent.getItemAtPosition(position);
        startActivity(appInfo.intent);
    }    

    // アプリの解放
    @Override
    public void onDestroy() {
        super.onDestroy();
        
        //アプリ登録解除レシーバーの解放
        unregisterReceiver(appReceiver);
    }    
    
    // BACKキーの無効化
    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getAction()==KeyEvent.ACTION_DOWN) {
            switch (event.getKeyCode()) {
                case KeyEvent.KEYCODE_BACK:
                    return true;
            }
        }
        return super.dispatchKeyEvent(event);
    }

    // グリッドアダプタの生成
    public class GridAdapter extends BaseAdapter {
        private Context context;
        
        public GridAdapter(Context c) {
            context = c;
        }

        public int getCount() {
            return appList.size();
        }

        public Object getItem(int position) {
            return appList.get(position);
        }

        public long getItemId(int position) {
            return position;
        }

        public View getView(int position,View convertView,ViewGroup parent) {
            TextView textView = new TextView(context);
            textView.setWidth(78);
            textView.setHeight(65);
            textView.setSingleLine(true);
            textView.setTextSize(12.0f);
            textView.setGravity(Gravity.CENTER_HORIZONTAL);
            textView.setTextColor(Color.rgb(0,0,0));
            textView.setCompoundDrawablesWithIntrinsicBounds(null,
                appList.get(position).icon,
                null,null);
            textView.setText(appList.get(position).title);
            return textView;
        }
    }

    private class AppReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context,Intent intent) {
            loadAppList(); 
        }
    }

    private static void setLLParams(View view, int w, int h) {
        view.setLayoutParams(new LinearLayout.LayoutParams(w, h));
    }
}