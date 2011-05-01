package sak.samples.stackviewwidget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.RemoteViews;
import android.widget.Toast;

public class StackWidgetProvider extends AppWidgetProvider {

    /*
     * タップしたときの ACTION
     */
    public static final String ACTION_0 = "sak.samples.stackviewwidget.ACTION_0";

    /*
     * タップしたリストの位置情報
     */
    public static final String EXTRA_POSITION = "sak.samples.stackviewwidget.EXTRA_POSITION";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(ACTION_0)) {
            int viewIndex = intent.getIntExtra(EXTRA_POSITION, 0);
            Toast.makeText(context, viewIndex + " 番目のビューをタッチしましたね。", Toast.LENGTH_SHORT).show();
        }
        super.onReceive(context, intent);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

        for (int i = 0; i < appWidgetIds.length; ++i) {
            Intent intent = new Intent(context, StackWidgetService.class);
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetIds[i]);
            intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));

            RemoteViews rv = new RemoteViews(context.getPackageName(), R.layout.widget_layout);

            /*
             * スタック表示を設定する。
             */
            rv.setRemoteAdapter(appWidgetIds[i], R.id.stack_view, intent);

            /*
             * 空データの表示を設定する。
             */
            rv.setEmptyView(R.id.stack_view, R.id.empty_view);

            /*
             * タップ時の動作を設定する。（トーストを表示させる。）
             */
            Intent toastIntent = new Intent(context, StackWidgetProvider.class);
            toastIntent.setAction(ACTION_0);
            toastIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetIds[i]);

            PendingIntent toastPendingIntent =
                PendingIntent.getBroadcast(context, 0, toastIntent, PendingIntent.FLAG_UPDATE_CURRENT);

            rv.setPendingIntentTemplate(R.id.stack_view, toastPendingIntent);

            appWidgetManager.updateAppWidget(appWidgetIds[i], rv);
        }

        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }
}