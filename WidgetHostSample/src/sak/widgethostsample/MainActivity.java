package sak.widgethostsample;

import java.util.ArrayList;

import android.app.Activity;
import android.appwidget.AppWidgetHostView;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProviderInfo;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	
	private static final int REQUEST_CREATE_APPWIDGET = 5;
	private static final int REQUEST_PICK_APPWIDGET = 9;

	static final int APPWIDGET_HOST_ID = 1024;

	private AppWidgetManager mAppWidgetManager;
	private MyAppWidgetHost mAppWidgetHost;

	private LinearLayout layout;
	private CellLayout cell;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        mAppWidgetManager = AppWidgetManager.getInstance(this);
        
        mAppWidgetHost = new MyAppWidgetHost(this, APPWIDGET_HOST_ID);
        mAppWidgetHost.startListening();

        Button button = (Button)findViewById(R.id.Button01);
        button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// ウェジェットのピックアップ
	        	int appWidgetId = MainActivity.this.mAppWidgetHost.allocateAppWidgetId();

	        	Intent pickIntent = new Intent(AppWidgetManager.ACTION_APPWIDGET_PICK);
	        	pickIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);

	        	ArrayList<AppWidgetProviderInfo> customInfo = new ArrayList<AppWidgetProviderInfo>();
	        	pickIntent.putParcelableArrayListExtra(AppWidgetManager.EXTRA_CUSTOM_INFO, customInfo);
	        	
	        	startActivityForResult(pickIntent, REQUEST_PICK_APPWIDGET);
			}
		});
        
        layout = (LinearLayout)findViewById(R.id.LinearLayout01);

        TextView tv = new TextView(this);
        tv.setText("test test test");
        layout.addView(tv);
        
        cell = (CellLayout)findViewById(R.id.Cell01);
    }

	@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//    	if (resultCode == RESULT_OK && mAddItemCellInfo != null) {
    	if (resultCode == RESULT_OK) {
    		switch (requestCode) {
    		case REQUEST_PICK_APPWIDGET:
    			addAppWidget(data);
    			break;
    		case REQUEST_CREATE_APPWIDGET:
    			completeAddAppWidget(data);
    			break;
    		}
    		
    	} else if (requestCode == REQUEST_PICK_APPWIDGET && 
    				resultCode == RESULT_CANCELED && data != null) {
    	    int appWidgetId = data.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, -1);
    		if (appWidgetId != -1) {
    			mAppWidgetHost.deleteAppWidgetId(appWidgetId);
    		}
    	}
//    	mWaitingForResult = false;
    }
    
	private Handler handler = new Handler();
	
	// ウェジェットの追加
    void addAppWidget(Intent data) {
    	int appWidgetId = data.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, -1);
        
    	final String text = "AppWidgetId: " + appWidgetId;
    	handler.post(new Runnable() {
			@Override
			public void run() {
		    	Toast.makeText(MainActivity.this, text, Toast.LENGTH_LONG).show();
			}
    	});
    	
		AppWidgetProviderInfo appWidget = mAppWidgetManager.getAppWidgetInfo(appWidgetId);
		
		if (appWidget.configure != null) {
			Intent intent = new Intent(AppWidgetManager.ACTION_APPWIDGET_CONFIGURE);
			intent.setComponent(appWidget.configure);
			intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
			
			startActivityForResult(intent, REQUEST_CREATE_APPWIDGET);

		} else {
			onActivityResult(REQUEST_CREATE_APPWIDGET, Activity.RESULT_OK, data);
		}
    }
     
    private void completeAddAppWidget(Intent data) {
    	Bundle extras = data.getExtras();
    	int appWidgetId = extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID, -1);
    	
    	AppWidgetProviderInfo appWidgetInfo = mAppWidgetManager.getAppWidgetInfo(appWidgetId);
    	
    	AppWidgetHostView v = mAppWidgetHost.createView(this, appWidgetId, appWidgetInfo);
    	v.setAppWidget(appWidgetId, appWidgetInfo);
    	
    	int[] spans = cell.rectToCell(appWidgetInfo.minWidth, appWidgetInfo.minHeight);

    	int x = 1;
    	int y = 2;
    	int spanX = spans[0];
    	int spanY = spans[1];

//    	CellLayout.CellInfo cellinfo = new CellLayout.CellInfo();
//    	cellinfo.valid = true;
//    	cellinfo.screen = 0;
//    	cellinfo.cellX = 0;
//    	cellinfo.cellY = 0;
//    	cellinfo.spanX = spans[0];
//    	cellinfo.spanY = spans[1];
//    	
//    	int[] xy = new int[2];
//    	
//    	if (!findSlot(cellinfo, xy, spanX, spanY)) 
//    		return;

    	CellLayout.LayoutParams lp = new CellLayout.LayoutParams(x, y, spanX, spanY);

    	cell.addView(v, 0, lp);
    }
}