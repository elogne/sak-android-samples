package sak.samples.stackviewwidget;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

public class StackWidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new StackRemoteViewsFactory(this.getApplicationContext(), intent);
    }
}

/**
 * RemoteViews を作成するファクトリークラス
 */
class StackRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    /*
     * RemoteViews の数
     */
    private static final int mCount = 10;

    /*
     * RemoteViews のリスト
     */
    private List<WidgetItem> mWidgetItems = new ArrayList<WidgetItem>();

    private Context mContext;

    public StackRemoteViewsFactory(Context context, Intent intent) {
        mContext = context;
    }

    @Override
    public void onCreate() {

        /*
         * RemoteViews に情報を付与する。
         */
        for (int i = 0; i < mCount; i++) {
            mWidgetItems.add(new WidgetItem(i + "!"));
        }

        /*
         * 起動で少しためを作る。（無くても問題無し）
         */
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {}
    }

    @Override
    public void onDestroy() {
        mWidgetItems.clear();
    }

    @Override
    public int getCount() {
        return mCount;
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.widget_item);

        /*
         * 表示するテキストを設定する。
         */
        rv.setTextViewText(R.id.widget_item, mWidgetItems.get(position).text);

        /*
         * タップした時に渡す position を設定する。
         */
        Bundle extras = new Bundle();
        extras.putInt(StackWidgetProvider.EXTRA_POSITION, position);

        Intent fillInIntent = new Intent();
        fillInIntent.putExtras(extras);

        rv.setOnClickFillInIntent(R.id.widget_item, fillInIntent);

        /*
         * 各 Remote View の表示前に少しためを作る。（無くても問題無し）
         */
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {}

        return rv;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public void onDataSetChanged() {
        // 何もしない
    }
}