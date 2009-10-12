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

        // ���C�A�E�g�̐���
        LinearLayout layout = new LinearLayout(this);
        layout.setBackgroundResource(R.drawable.bg);
        layout.setOrientation(LinearLayout.VERTICAL);
        setContentView(layout); 

        // �O���b�h�r���[�̐���
        gridView = new GridView(this);
        gridView.setNumColumns(4);
        gridView.setGravity(Gravity.CENTER);
        gridView.setOnItemClickListener(this);
        setLLParams(gridView,
        	LinearLayout.LayoutParams.FILL_PARENT,
        	LinearLayout.LayoutParams.FILL_PARENT);
        layout.addView(gridView);
        
        // �A�v�����X�g�̓ǂݍ���
        loadAppList();     

        // �A�v���o�^�������V�[�o�[�̊J�n
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

        // �A�N�e�B�r�e�B���̎擾
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
        
        // �O���b�h�̍X�V
        gridView.setAdapter(new GridAdapter(this));
    } 
    
    // �A�C�R���T�C�Y�̕ύX
    private Drawable resizeIcon(Drawable icon) {
        // �W���A�C�R���T�C�Y�̎擾
        Resources res=getResources();
        int width  = (int)res.getDimension(android.R.dimen.app_icon_size);
        int height = (int)res.getDimension(android.R.dimen.app_icon_size);
        
        // ���݂̃A�C�R���T�C�Y�̎擾
        int iconWidth  = icon.getIntrinsicWidth();
        int iconHeight = icon.getIntrinsicHeight();

        //�A�C�R���T�C�Y�̕ύX
        if (width>0 && height>0 && 
            (width<iconWidth || height<iconHeight)) {
            
            //�ϊ���̃A�C�R���T�C�Y�̌v�Z
            float ratio = (float)iconWidth/iconHeight;
            if (iconWidth>iconHeight) {
                height=(int)(width/ratio);
            } else if (iconHeight>iconWidth) {
                width=(int)(height*ratio);
            }

            // ���I�L�����o�X�̐���
            Bitmap.Config c = (icon.getOpacity()!=PixelFormat.OPAQUE)?
            					Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565;
            Bitmap thumb=Bitmap.createBitmap(width,height,c);
            Canvas canvas=new Canvas(thumb);
            canvas.setDrawFilter(new PaintFlagsDrawFilter(Paint.DITHER_FLAG,0));

            // ���I�L�����o�X�ւ̃A�C�R���`��
            Rect oldBounds=new Rect();
            oldBounds.set(icon.getBounds());
            icon.setBounds(0,0,width,height);
            icon.draw(canvas);
            icon.setBounds(oldBounds);
            
            // �L�����o�X��Drawable�I�u�W�F�N�g�ɕϊ�
            icon = new BitmapDrawable(thumb);
        }        
        return icon;
    }

    // �O���b�h�A�C�e���̃N���b�N�C�x���g����
    public void onItemClick(AdapterView<?> parent,View v,int position,long id) {
        // �A�N�e�B�r�e�B�̋N��
        AppInfo appInfo=(AppInfo)parent.getItemAtPosition(position);
        startActivity(appInfo.intent);
    }    

    // �A�v���̉��
    @Override
    public void onDestroy() {
        super.onDestroy();
        
        //�A�v���o�^�������V�[�o�[�̉��
        unregisterReceiver(appReceiver);
    }    
    
    // BACK�L�[�̖�����
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

    // �O���b�h�A�_�v�^�̐���
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