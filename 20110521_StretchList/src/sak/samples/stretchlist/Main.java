package sak.samples.stretchlist;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AbsListView.OnScrollListener;

public class Main extends Activity {

    private static String[] sItems = {
        "01: 北海道", "02: 青森県", "03: 岩手県", "04: 宮城県", "05: 秋田県",
        "06: 山形県", "07: 福島県", "08: 茨城県", "09: 栃木県", "10: 群馬県",
        "11: 埼玉県", "12: 千葉県", "13: 東京都", "14: 神奈川県", "15: 新潟県",
        "16: 富山県", "17: 石川県", "18: 福井県", "19: 山梨県", "20: 長野県",
        "21: 岐阜県", "22: 静岡県", "23: 愛知県", "24: 三重県", "25: 滋賀県",
        "26: 京都府", "27: 大阪府", "28: 兵庫県", "29: 奈良県", "30: 和歌山県",
        "31: 鳥取県", "32: 島根県", "33: 岡山県", "34: 広島県", "35: 山口県",
        "36: 徳島県", "37: 香川県", "38: 愛媛県", "39: 高知県", "40: 福岡県",
        "41: 佐賀県", "42: 長崎県", "43: 熊本県", "44: 大分県", "45: 宮崎県",
        "46: 鹿児島県", "47: 沖縄県" };


    private ArrayList<String> mItems = new ArrayList<String>();
    private int current_position = 0;

    private ListView lv;
    private TextView tv;
    private MyAdapter adapter;

    private Handler handler = new Handler();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        adapter = new MyAdapter(this);

        lv = (ListView)findViewById(R.id.list);

        /*
         * Footer の追加は setAdapter の前でする。
         */
        LayoutInflater inflater = LayoutInflater.from(this);
        final View footer = inflater.inflate(R.layout.footer, null);
        lv.addFooterView(footer);

        lv.setAdapter(adapter);

        lv.setOnScrollListener(new OnScrollListener() {
            private int mark = 0;

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

                if (totalItemCount >= sItems.length) {
                    lv.removeFooterView(footer);    // 必要なくなったので消す。
                    return;
                }

                if ((totalItemCount - visibleItemCount) == firstVisibleItem && totalItemCount > mark) {
                    mark = totalItemCount;

                    new MyTask().execute();
                }
            }

            @Override
            public void onScrollStateChanged(AbsListView arg0, int arg1) {
            }
        });

        addItems();
    }

    /*
     * サーバにアクセスしている雰囲気を出すためタメを作る。
     */
    private class MyTask extends AsyncTask<Object, Integer, Long> {

        @Override
        protected synchronized Long doInBackground(Object... arg0) {
            try {
                Thread.sleep(2000);
                addItems();

            } catch (InterruptedException e) {
            }
            return 0L;
        }

        @Override
        protected void onPostExecute(Long count) {
            adapter.notifyDataSetChanged();
        }
    }

    /*
     * 要素を１０個追加する。
     */
    private synchronized void addItems() {
        int start = current_position;
        int index = start;
        for (int i = start; i < start + 10; i++, index ++) {
            if (index >= sItems.length) {
                index = 0;
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        lv.removeFooterView(tv);
                    }
                });
                return;
            }
            mItems.add(sItems[index]);
        }
        current_position = index;
    }

    private class MyAdapter extends BaseAdapter {
        private LayoutInflater mInflater;

        public MyAdapter(Context context) {
            mInflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return mItems.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;

            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.item, null);
                holder = new ViewHolder();
                holder.tvName = (TextView) convertView.findViewById(R.id.name);
                convertView.setTag(holder);

            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            holder.tvName.setText(mItems.get(position));

            return convertView;
        }

        class ViewHolder {
            TextView tvName;
        }
    }
}