package sak.samples.saksamplesexplorer;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import android.app.ListActivity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

public class Main extends ListActivity {

    private final static String MY_CATEGORY = "android.intent.category.SAK_SAMPLES";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        List<Action> data = getData();

        ListAdapter adapter =
                new ArrayAdapter<Action>(this, android.R.layout.simple_list_item_1, data);

        setListAdapter(adapter);

        getListView().setTextFilterEnabled(true);
    }

    protected List<Action> getData() {
        List<Action> data = new ArrayList<Action>();

        Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
        mainIntent.addCategory(MY_CATEGORY);

        PackageManager pm = getPackageManager();
        List<ResolveInfo> infos = pm.queryIntentActivities(mainIntent, 0);
        if (null == infos)
            return data;

        for (ResolveInfo info : infos) {
            /*
             * アプリの名称（label で設定したもの）を取得する。
             */
            CharSequence labelSeq = info.loadLabel(pm);
            String label = (labelSeq != null) ? labelSeq.toString() : info.activityInfo.name;

            String[] labelPath = label.split("/");
            String title = labelPath[labelPath.length-1];

            Intent intent = new Intent();
            intent.setClassName(info.activityInfo.applicationInfo.packageName, info.activityInfo.name);

            data.add(new Action(title, intent));
        }

        /*
         * ラベルで表示をソートする。
         */
        Collections.sort(data, sMyComparator);

        return data;
    }

    @Override
    protected void onListItemClick(ListView lv, View v, int position, long id) {
        Action action = (Action) lv.getItemAtPosition(position);
        startActivity(action.intent);
    }

    /*
     * タイトルと INTENT のペア
     */
    private class Action {
        String title;
        Intent intent;
        Action(String title, Intent intent) {
            this.title = title;
            this.intent = intent;
        }
        @Override
        public String toString() {
            return title;
        }
    }

    private final static Comparator<Action> sMyComparator = new Comparator<Action>() {
        private final Collator collator = Collator.getInstance();
        public int compare(Action map1, Action map2) {
            return collator.compare(map1.title, map2.title);
        }
    };
}
